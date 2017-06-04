// NOTE: This job requires credentials to be set once generated

job('Clusterview-deploy-PRODUCTION-cloudfoundry') {
    logRotator(1, 3)

    parameters {
        stringParam('NEXUS_URL')
        stringParam('REPO')
        stringParam('GROUP')
        stringParam('ARTIFACT')
        stringParam('VERSION')
    }

    scm {
        git('https://github.com/mahanhz/clusterview.git', '*/master')
    }

    throttleConcurrentBuilds {
        maxTotal(1)
    }

    wrappers {
        preBuildCleanup()
    }

    steps {
        shell('wget -qO $ARTIFACT.jar "$NEXUS_URL?r=$REPO&g=$GROUP&a=$ARTIFACT&v=$VERSION"')
    }

    configure { Node project ->
        project / publishers << 'com.hpe.cloudfoundryjenkins.CloudFoundryPushPublisher' {
            target 'https://api.run.pivotal.io'
            organization 'clusterview'
            cloudSpace 'production'
            credentialsId '******'
            pluginTimeout '120'
            manifestChoice {
                value 'manifestFile'
                manifestFile 'manifest-production.yml'
            }
        }
    }
}