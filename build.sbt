name := "Blacklisting bulk IP addresses at scale using Redis Cluster"
version := "1.0"

ThisBuild / scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
	"redis.clients" % "jedis" % "4.3.1",
	"org.scalatest" %% "scalatest" % "3.0.8" % "test"
)
scalacOptions := Seq("-unchecked", "-deprecation")
