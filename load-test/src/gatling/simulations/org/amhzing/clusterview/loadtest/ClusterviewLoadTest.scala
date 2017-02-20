package org.amhzing.clusterview.loadtest

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import com.typesafe.config._

class ClusterviewLoadTest extends Simulation {

	val config = ConfigFactory.load()

  val env = config.getString("load-test.env")
  println("The env is: " + env)

	val baseUrl = config.getString("load-test.baseUrl")

	val httpProtocol = http
		.baseURL(baseUrl)
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_5 = Map("Accept" -> "*/*")

  val regionAndClusterFeed = csv("region_cluster.csv").random

	val scn = scenario("ClusterviewLoadTest")
		.feed(regionAndClusterFeed)
		.exec(http("1_Enter_Application")
			.get("/login")
			.headers(headers_0)
			.check(
            	regex("""<input type="hidden" name="_csrf" value="([^"]*)" />""")
            	.saveAs("loginCsrfToken")))
		.pause(4)
		.exec(http("2_Perform_Login")
			.post("/login")
			.headers(headers_0)
			.formParam("username", "admin@example.com")
			.formParam("password", "admin123")
			.formParam("_csrf", "${loginCsrfToken}"))
		.pause(2)
		.exec(http("3_Go_To_${region}_Region")
			.get("/clusterview/se/${region}")
			.headers(headers_0))
		.pause(2)
		.exec(http("4_Go_To_${cluster}_Cluster")
			.get("/clusterview/se/${region}/${cluster}")
			.headers(headers_0))
		.pause(2)
		.exec(http("5_Go_To_New_Group")
			.get("/clusteredit/se/${region}/${cluster}/newgroup")
			.headers(headers_0)
			.resources(http("request_5")
			.get("/js/group.js")
			.headers(headers_5))
			.check(
            	regex("""<input type="hidden" name="_csrf" value="([^"]*)" />""")
            	.saveAs("loggedInCsrfToken")))
		.pause(5)
		.exec(http("6_Create_Group")
			.post("/clusteredit/se/${region}/${cluster}/creategroup")
			.headers(headers_0)
			.formParam("obfuscatedId", "")
			.formParam("location.coordX", "298")
			.formParam("location.coordY", "229")
			.formParam("coreActivities[0].id", "cc")
			.formParam("coreActivities[0].name", "CC")
			.formParam("coreActivities[0].quantity", "1")
			.formParam("coreActivities[0].totalParticipants", "12")
			.formParam("coreActivities[0].communityOfInterest", "3")
			.formParam("coreActivities[1].id", "dm")
			.formParam("coreActivities[1].name", "DM")
			.formParam("coreActivities[1].quantity", "0")
			.formParam("coreActivities[1].totalParticipants", "0")
			.formParam("coreActivities[1].communityOfInterest", "0")
			.formParam("coreActivities[2].id", "jyg")
			.formParam("coreActivities[2].name", "JYG")
			.formParam("coreActivities[2].quantity", "0")
			.formParam("coreActivities[2].totalParticipants", "0")
			.formParam("coreActivities[2].communityOfInterest", "0")
			.formParam("coreActivities[3].id", "sc")
			.formParam("coreActivities[3].name", "SC")
			.formParam("coreActivities[3].quantity", "0")
			.formParam("coreActivities[3].totalParticipants", "0")
			.formParam("coreActivities[3].communityOfInterest", "0")
			.formParam("members[0].name.firstName", "Harry")
			.formParam("members[0].name.lastName", "Gunn")
			.formParam("members[0].capability.activities", "cct")
			.formParam("members[0].capability.activities", "dmh")
			.formParam("members[0].commitment.activities", "cct")
			.formParam("_csrf", "${loggedInCsrfToken}"))
		.pause(5)
		.exec(http("7_Logout")
			.post("/logout")
			.headers(headers_0)
			.formParam("_csrf", "${loggedInCsrfToken}"))

	setUp(scn.inject(rampUsers(50) over (30 seconds))).protocols(httpProtocol)
}