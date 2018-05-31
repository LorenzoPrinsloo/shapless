import sbt.Resolver

val ivyLocal = Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

lazy val `shapeless-resolvers`: Def.Setting[Seq[Resolver]] =
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots"),
    ivyLocal
  )

lazy val `common-dependencies` = {
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % "2.12.4",
    "com.chuusai" %% "shapeless" % "2.3.3"
  )
}

lazy val global = {
  Seq(
    `shapeless-resolvers`, `common-dependencies`,
    name := "shapeless",
    version := "0.0.4",
    organization := "lorenzo.experimental",
    scalaVersion := "2.12.4"
  )
}


//SHAPELESS

val `shapeless-dependencies` = Seq(
)

lazy val `shapeless` = (project in file("./shapeless"))
  .settings(global: _*)
  .settings(libraryDependencies ++= `shapeless-dependencies`)
  .settings(name := "shapeless", publishArtifact := true)

//SHAPELESS-UTILS

val `shapeless-utils-dependencies` = Seq(
  "org.scala-lang" % "scala-reflect" % "2.12.4"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

lazy val `shapeless-utils` = (project in file("./shapeless-utils"))
  .settings(global: _*)
  .settings(libraryDependencies ++= `shapeless-utils-dependencies`)
  .settings(name := "shapeless-utils", publishArtifact := true)
  .settings(addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6"))
  .dependsOn(`shapeless`)

//ROOT

lazy val root = (project in file("."))
  .settings(global: _*)
  .settings(publishArtifact := false)
  .aggregate(`shapeless`, `shapeless-utils`)