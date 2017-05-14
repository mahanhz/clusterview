# Run load-test submodule tasks
./gradlew :load-test:clean :load-test:build

# Example usage

./gradlew gatlingRun-org.amhzing.clusterview.loadtest.ClusterviewLoadTest

# Example usage with specific conf

./gradlew gatlingRun-org.amhzing.clusterview.loadtest.ClusterviewLoadTest -Dconfig.resource=test.conf