// NOTE: This job requires credentials to be set once generated

multibranchPipelineJob('Clusterview-BUILD-mb-pipeline') {
    branchSources {
        github {
            repoOwner('mahanhz')
            scanCredentialsId('mahanhz')
            repository('clusterview')
        }
    }

    orphanedItemStrategy {
        discardOldItems {
            daysToKeep(1)
            numToKeep(5)
        }
    }
}