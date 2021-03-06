name := "WaccCompiler"

version := "1.0"

scalaVersion := "2.11.6"

scalacOptions += "-target:jvm-1.8"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}

fork in run := true

scalacOptions := Seq("-unchecked", "-deprecation")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
