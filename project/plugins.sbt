//addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.15.0-0.6")
addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.13.1")

// If you prefer using Scala.js 1.x, uncomment the following plugins instead:
// addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.0.8")
// addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.0.0-M3")

// fast development turnaround when using sbt ~reStart
addSbtPlugin("io.spray"                  % "sbt-revolver"              % "0.9.1")
addSbtPlugin("com.eed3si9n"              % "sbt-assembly"              % "0.14.7")
addSbtPlugin("com.typesafe.sbt"          % "sbt-native-packager"       % "1.3.5")
addSbtPlugin("com.typesafe.sbt"          % "sbt-twirl"                 % "1.3.15")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "0.5.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.3")
addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2")
