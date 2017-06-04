job('Clusterview-code-analysis-G') {
    description('In the shell script the gradle tasks do the following:\n' +
            '- clean: Make sure we start with a clean workspace\n' +
            '- check: Runs PMD, Findbugs and JDepend\n' +
            '- test: Runs the tests (needs to be run before Jacoco)\n' +
            '- jacocoTestReport: Runs JaCoCo\n' +
            '- javadoc: Runs Javadoc\n' +
            '- dependencyCheck: Runs OWASP dependency check')

    logRotator(1, 5)

    scm {
        git('git@github.com:mahanhz/clusterview.git', '*/master')
    }

    throttleConcurrentBuilds {
        maxTotal(1)
    }

    triggers {
        cron('H */2 * * *')
    }

    steps {
        shell('./gradlew clean check test jacocoTestReport javadoc')
    }

    wrappers {
        preBuildCleanup()
    }

    publishers {
        analysisCollector {
            checkstyle()
            dry()
            findbugs()
            pmd()
            tasks()
            warnings()
        }
        archiveJavadoc {
            javadocDir('build/docs/javadoc')
        }
        dependencyCheck('**/dependency-check-report.xml')
        findbugs('**/findbugs/*.xml', false)
        jacocoCodeCoverage {
            classPattern('**/classes')
            execPattern('**/**.exec')
            sourcePattern('**/src/main/java')
        }
        pmd('**/pmd/*.xml')
        tasks('**/*.java', '', 'FIXME', 'TODO', 'LOW', true)
    }
}