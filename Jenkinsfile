#!groovy

COMMIT_ID = ""
FALLBACK_RELEASE_VERSION = ""
SELECTED_SEMANTIC_VERSION_UPDATE = ""
DAYS_TO_KEEP_BUILDS = "1"
NUMBER_OF_BUILDS_TO_KEEP = "10"
REPOSITORY_URL="git@github.com:mahanhz/clusterview.git"

properties([[$class: 'BuildDiscarderProperty', strategy:
            [$class: 'LogRotator', artifactDaysToKeepStr: '',
             artifactNumToKeepStr: '', daysToKeepStr: DAYS_TO_KEEP_BUILDS, numToKeepStr: NUMBER_OF_BUILDS_TO_KEEP]],
            [$class: 'ThrottleJobProperty', categories: [], limitOneJobWithMatchingParams: false,
             maxConcurrentPerNode: 0, maxConcurrentTotal: 0, paramsToUseForLimit: '',
             throttleEnabled: false, throttleOption: 'project']])

stage ('Build') {
    node {
        timeout(time: 10, unit: 'MINUTES') {
            try {
                checkout scm

                gradle 'clean test assemble'

                stash excludes: 'build/', includes: '**', name: 'source'

                // Obtaining commit id like this until JENKINS-26100 is implemented
                // See http://stackoverflow.com/questions/36304208/jenkins-workflow-checkout-accessing-branch-name-and-git-commit
                sh 'git rev-parse HEAD > commit'
                COMMIT_ID = readFile('commit').trim()

                // Custom environment variable (e.g. for display in Spring Boot manage info page)
                env.GIT_COMMIT_ID = COMMIT_ID

                FALLBACK_RELEASE_VERSION = releaseVersion()
            } catch(err) {
                junit '**/build/test-results/*.xml'
                throw err
            }
        }
    }
}

if (!isMasterBranch()) {
    // one at a time!
    lock('lock-merge') {
        stage ('Merge') {
            node {
                timeout(time: 1, unit: 'MINUTES') {
                    checkout scm: [$class: 'GitSCM',
                                   branches: [[name: '*/master']],
                                   doGenerateSubmoduleConfigurations: false,
                                   extensions: [[$class: 'LocalBranch', localBranch: 'master'], [$class: 'WipeWorkspace']],
                                   submoduleCfg: [],
                                   userRemoteConfigs: [[url: REPOSITORY_URL]]]

                    sh "git merge ${COMMIT_ID}"
                    sh "git push origin master"

                    deleteDir() // Wipe out the workspace
                }
            }
        }
    }
}

if (isMasterBranch()) {

    stage ('Acceptance test') {
        node {
            timeout(time: 10, unit: 'MINUTES') {
                try {
                    unstash 'source'

                    grantExecutePermission 'gradlew'

                    gradle 'acceptanceTest'
                } catch(err) {
                    junit '**/build/test-results/*.xml'
                    throw err
                }
            }
        }
    }

    // one at a time!
    lock('lock-publish-snapshot') {
        stage ('Publish snapshot') {
            node {
                timeout(time: 5, unit: 'MINUTES') {
                    unstash 'source'

                    grantExecutePermission 'gradlew'

                    gradle 'assemble uploadArchives'
                }
            }
        }
    }

    stage ('Approve RC?') {
        timeout(time: 1, unit: 'DAYS') {
            SELECTED_SEMANTIC_VERSION_UPDATE =
                    input message: 'Publish release candidate?',
                            parameters: [[$class: 'ChoiceParameterDefinition',
                                          choices: 'patch\nminor\nmajor',
                                          description: 'Determine the semantic version to release',
                                          name: '']]
        }
    }

    // one at a time!
    lock('lock-publish-release-candidate') {
        stage ('Publish RC') {
            node {
                timeout(time: 10, unit: 'MINUTES') {
                    sh "git branch -a -v --no-abbrev"

                    checkout scm: [$class: 'GitSCM',
                                   branches: [[name: '*/master']],
                                   doGenerateSubmoduleConfigurations: false,
                                   extensions: [[$class: 'LocalBranch', localBranch: 'master'], [$class: 'WipeWorkspace']],
                                   submoduleCfg: [],
                                   userRemoteConfigs: [[url: REPOSITORY_URL]]]

                    stash includes: 'gradle.properties', name: 'masterProperties'

                    unstash 'source'
                    unstash 'masterProperties'

                    grantExecutePermission 'gradlew'

                    def script = "scripts/release/clusterview_release.sh"
                    grantExecutePermission script

                    sh "./" + script + " ${SELECTED_SEMANTIC_VERSION_UPDATE} ${FALLBACK_RELEASE_VERSION}"
                }

                deleteDir() // Wipe out the workspace
            }
        }
    }
}

def releaseVersion() {
    def props = readProperties file: 'gradle.properties'
    def version = props['version']

    if (version.contains('-SNAPSHOT')) {
        version = version.replaceFirst('-SNAPSHOT', '')
    }

    return version
}

def isMasterBranch() {
    return env.BRANCH_NAME == "master"
}

void gradle(String tasks, String switches = null) {
    String gradleCommand = "";
    gradleCommand += './gradlew '
    gradleCommand += tasks

    if(switches != null) {
        gradleCommand += ' '
        gradleCommand += switches
    }

    sh gradleCommand.toString()
}

// fixes unstash overwrite bug ... https://issues.jenkins-ci.org/browse/JENKINS-33126
void grantExecutePermission(String fileOrDir, boolean recursive = false) {
    String permissionCommand = "chmod ";

    if (recursive) {
        permissionCommand += '-R '
    }

    permissionCommand += 'u+x '
    permissionCommand += fileOrDir

    sh permissionCommand.toString()
}