import sbtcrossproject.{CrossType, crossProject}
import com.typesafe.sbt.less.Import.LessKeys

/**Resolving a snapshot version. It's going to be slow unless you use `updateOptions := updateOptions.value.withLatestSnapshots(false)` options* */
updateOptions := updateOptions.value.withLatestSnapshots(false)

lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    scalacOptions ++= Seq("-Ypartial-unification"),
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    LessKeys.compress in Assets := true,
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    //pipelineStages := Seq(digest, gzip),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.1.1",
      "com.typesafe.akka" %% "akka-stream" % "2.5.19",
      "com.vmunier" %% "scalajs-scripts" % "1.1.2",
    ),
    WebKeys.packagePrefix in Assets := "public/",
    managedClasspath in Runtime += (packageBin in Assets).value,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0-M4"),
    javaOptions in run += "-Xms4G -Xmx8G",    //-XX:MaxPermSize=1024M,
  )
  .enablePlugins(SbtWeb, SbtTwirl, WebScalaJSBundlerPlugin, JavaAppPackaging ).
  dependsOn(sharedJvm)


lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    npmDependencies in Compile += "snabbdom" -> "0.7.3",
    version in webpack := "4.8.1",
    version in startWebpackDevServer := "3.1.4",
    scalaJSUseMainModuleInitializer := true,
    useYarn := true,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    webpackBundlingMode := BundlingMode.Application,
    scalaJSModuleKind := ModuleKind.CommonJSModule, // configure Scala.js to emit a JavaScript module instead of a top-level script
    webpackConfigFile in fastOptJS := Some(baseDirectory.value / "webpack.config.dev.js"),
    skip in packageJSDependencies := false,
    libraryDependencies ++= Seq(
      "io.github.outwatch" %%% "outwatch" % "0.11.1-SNAPSHOT",
      "com.clovellytech" %%% "outwatch-router" % "0.0.7",
      "com.chuusai" %%% "shapeless" % "2.3.3",
    ),
    emitSourceMaps := false,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0-M4"),
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb, ScalaJSBundlerPlugin)
  .dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.4.0",
      "com.typesafe.akka" %% "akka-actor" % "2.5.19",
    )
  )
  .jsConfigure(_ enablePlugins ScalaJSWeb)
  .jsConfigure(_ enablePlugins ScalaJSBundlerPlugin)
  .jsConfigure(_ enablePlugins SbtWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.5",
  organization := "xiimbal maak"
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}
