#!/bin/bash
set -ex

if [ -e gh-pages ]; then
  echo "Error: gh-pages already exists." 1>&2
  exit 1
fi

sbt demo/fullOptJS
mkdir gh-pages

cp \
  demo/target/scala-2.11/plotly-demo-opt.js \
  demo/target/scala-2.11/plotly-demo-opt.js.map \
  demo/target/scala-2.11/plotly-demo-jsdeps.js \
  demo/target/scala-2.11/plotly-demo-jsdeps.min.js \
  gh-pages

cat demo/target/scala-2.11/classes/index.html | \
  sed 's@\.\./plotly-demo-jsdeps\.js@plotly-demo-jsdeps.min.js@' | \
  sed 's@\.\./plotly-demo-fastopt\.js@plotly-demo-opt.js@' | \
  cat > gh-pages/index.html
