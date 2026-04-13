#!/bin/sh
# Maven Wrapper script
set -e
MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(cd "$(dirname "$0")" && pwd)}"
MAVEN_WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

# Extract distribution URL from properties
DIST_URL=$(grep 'distributionUrl' "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)

# Maven installation directory
MAVEN_HOME="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/dists"

if [ -d "$MAVEN_HOME" ] && [ -f "$MAVEN_HOME/bin/mvn" ]; then
  exec "$MAVEN_HOME/bin/mvn" "$@"
fi

echo "Downloading Maven distribution..."
mkdir -p "$MAVEN_HOME"
DIST_FILE="$MAVEN_HOME/maven.zip"

if command -v curl > /dev/null 2>&1; then
  curl -fsSL -o "$DIST_FILE" "$DIST_URL"
elif command -v wget > /dev/null 2>&1; then
  wget -q -O "$DIST_FILE" "$DIST_URL"
else
  echo "Error: curl or wget not found. Cannot download Maven."
  exit 1
fi

# Extract Maven
unzip -q -o "$DIST_FILE" -d "$MAVEN_HOME"
# Move extracted contents up (Maven zips contain a top-level directory)
EXTRACTED_DIR=$(find "$MAVEN_HOME" -mindepth 1 -maxdepth 1 -type d -name "apache-maven-*" | head -1)
if [ -n "$EXTRACTED_DIR" ]; then
  mv "$EXTRACTED_DIR"/* "$MAVEN_HOME"/
  rmdir "$EXTRACTED_DIR"
fi
rm -f "$DIST_FILE"

exec "$MAVEN_HOME/bin/mvn" "$@"
