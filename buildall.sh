#!/bin/sh

# A convenience build script that performs 2-step build of ProofProcess.
#
# 2-step build is necessary to build ProofProcess library (non-Eclipse) modules
# that are needed for the main plug-ins. Only after the `lib` is built, the
# OSGI-fied library plug-ins can be located by ProofProcess Eclipse dependency
# resolution.
#
# See http://wiki.eclipse.org/Tycho/How_Tos/Dependency_on_pom-first_artifacts

# Set the heap space needed for core builds
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=512m"

# In case of Maven errors, terminate the full build as well
# (This is to ensure step 2 does not run if step 1 fails)
set -e

LOCALREPO=/tmp/proofprocess-lib.localrepo

# Step 1: Build the `lib` module
mvn -f lib/pom.xml clean install -Dmaven.repo.local=$LOCALREPO

# Step 2: Build the ProofProcess Eclipse plug-ins
mvn clean install -Dmaven.repo.local=$LOCALREPO
