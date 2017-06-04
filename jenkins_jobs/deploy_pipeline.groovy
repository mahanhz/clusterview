// NOTE: This job requires credentials to be set once generated

pipelineJob('Clusterview-deploy-PRODUCTION-pipeline') {
    logRotator(1, 3)

    definition {
        cpsScm {
            scm {
                git('https://github.com/mahanhz/clusterview.git', '*/master')
            }

            scriptPath('Jenkinsfile-deploy-production')
        }
    }

    wrappers {
        preBuildCleanup()
    }
}