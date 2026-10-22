#!/bin/bash
set -ex

if [ -e gh-pages ]; then
  echo "Error: gh-pages already exists." 1>&2
  exit 1
fi

./mill demo.fullSite
mkdir gh-pages

cp -R "${MILL_OUTPUT_DIR:-out}/demo/fullSite.dest/." gh-pages/
