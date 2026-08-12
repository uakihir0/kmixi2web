#!/usr/bin/env bash
cd "$(dirname "$0")" || exit
BASE_PATH=$(pwd)
BUILD_PATH=../all/build

# Make Repository
cd "$BASE_PATH" || exit
mkdir -p $BUILD_PATH/cocoapods/repository/debug
mkdir -p $BUILD_PATH/cocoapods/repository/release

# Copy Podspec
cd "$BASE_PATH" || exit
cd $BUILD_PATH/cocoapods/publish/debug || exit
cp kmixi2web.podspec ../../repository/kmixi2web-debug.podspec
cd ../../repository/ || exit
sed -i -e "s|'kmixi2web'|'kmixi2web-debug'|g" kmixi2web-debug.podspec
sed -i -e "s|'kmixi2web.xcframework'|'debug/kmixi2web.xcframework'|g" kmixi2web-debug.podspec
rm -f ./*.podspec-e
cd "$BASE_PATH" || exit
cd $BUILD_PATH/cocoapods/publish/release || exit
cp kmixi2web.podspec ../../repository/kmixi2web-release.podspec
cd ../../repository/ || exit
sed -i -e "s|'kmixi2web'|'kmixi2web-release'|g" kmixi2web-release.podspec
sed -i -e "s|'kmixi2web.xcframework'|'release/kmixi2web.xcframework'|g" kmixi2web-release.podspec
rm -f ./*.podspec-e

# Copy Framework
cd "$BASE_PATH" || exit
cd $BUILD_PATH/cocoapods/publish/debug || exit
cp -r kmixi2web.xcframework ../../repository/debug/kmixi2web.xcframework
cd "$BASE_PATH" || exit
cd $BUILD_PATH/cocoapods/publish/release || exit
cp -r kmixi2web.xcframework ../../repository/release/kmixi2web.xcframework

# Copy README
cd "$BASE_PATH" || exit
cd ../ || exit
cp ./LICENSE ./all/build/cocoapods/repository/LICENSE
cp ./docs/pods/README.md ./all/build/cocoapods/repository/README.md
cp ./docs/pods/README_ja.md ./all/build/cocoapods/repository/README_ja.md
