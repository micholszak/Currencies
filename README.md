# Revolut Rates
## Overview
Lists all currencies downloaded from endpoint. 
Each row has an input where the user can enter value in order to calculate the value of other currencies.
## Build types
#### Debug
For application development
- disabled R8
- disabled shrinking
- debuggable
#### ReleasePreprod
For application testing
- with full R8 configuration
- debuggable
#### Release
- full R8 configuration
- non-debuggable
## Building
#### Build flavors
 - Mock, feeds controlled internally and used for automated testing.
 - Production, regular version of the feeds, uses endpoint.
## Additional Information
Debug buildType has attached logger that logs network traffic, logger is not present on any other build type.

Github Actions has been used for CI, it's running for every commit that goes into master branch. It checks the style of code and complexity with Ktlint and Detekt tools before trying to assemble the build.

Automated Tests are setup on mock version of the application, in order to run them please use the following command:

*./gradlew connectedMockDebugAndroidTest*
