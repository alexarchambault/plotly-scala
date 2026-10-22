#!/usr/bin/env bash
set -euo pipefail

# Runs the Mill tasks passed as arguments.
#
# On pull requests, only runs those affected by the changes of the PR, using Mill
# selective execution (https://mill-build.org/mill/large/selective-execution.html).
# This expects HEAD to be the PR merge commit, with its first parent (the base
# branch) available (fetch-depth: 2 with actions/checkout).

if [ "${GITHUB_EVENT_NAME:-}" = "pull_request" ]; then
  head="$(git rev-parse HEAD)"

  git checkout --quiet HEAD^1
  if ! ./mill --no-daemon selective.prepare "$@"; then
    echo "selective.prepare failed on the base commit, running all tasks" 1>&2
    out_dir="${MILL_OUTPUT_DIR:-out}"
    mkdir -p "$out_dir"
    # an empty metadata file makes selective.run run all tasks
    : > "$out_dir/mill-selective-execution.json"
  fi
  git checkout --quiet "$head"
  # the plotly-documentation submodule may have been fetched on the base commit
  git submodule update

  ./mill --no-daemon selective.resolve "$@" + selective.run "$@"
else
  ./mill --no-daemon "$@"
fi
