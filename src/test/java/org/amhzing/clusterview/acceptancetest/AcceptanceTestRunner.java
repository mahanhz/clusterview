package org.amhzing.clusterview.acceptancetest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/acceptancetest" },
                 glue = { "org.amhzing.clusterview.acceptancetest.steps",
                          "cucumber.api.spring" },
                 format = { "json:build/reports/cucumber/cucumber.json",
                            "html:build/reports/cucumber/cucumber.html",
                            "pretty" })
public class AcceptanceTestRunner {
}
