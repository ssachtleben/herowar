name := """herowar"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.0.3.Final",
  "org.postgresql" % "postgresql" % "9.4-1204-jdbc42",
  "commons-beanutils" % "commons-beanutils" % "1.9.2",
  "commons-io" % "commons-io" % "2.4",
  "org.apache.httpcomponents" % "httpclient" % "4.5.1",
  "com.typesafe.play" %% "play-mailer" % "4.0.0-M1",
  "com.ssachtleben" %% "play-auth-plugin" % "4.1",
  "com.ssachtleben" %% "play-compress-plugin" % "4.0",
  "com.ssachtleben" %% "play-cron-plugin" % "4.1",
  "com.ssachtleben" %% "play-event-plugin" % "4.0",
  "com.ssachtleben" %% "play-json-plugin" % "4.0",
  "com.ardor3d" % "ardor3d-core" % "0.9"
)

resolvers += Resolver.url("ssachtleben repository (snapshots)", url("http://ssachtleben.github.io/play-plugins/repository/snapshots/"))(Resolver.ivyStylePatterns)

resolvers += "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/"
resolvers += "Theatr.us repository" at "http://repo.theatr.us/"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

// Allow jpa: https://www.playframework.com/documentation/2.4.x/JavaJPA
PlayKeys.externalizeResources := false

JsTaskKeys.timeoutPerSource := new scala.concurrent.duration.DurationInt(2).hours

// Live pipeline
pipelineStages := Seq(define,filter,concat,uglify,digest,gzip)

// Debug pipeline
pipelineStages in Assets := Seq(define,filter,concat)

includeFilter in filter := "*.coffee" || "*.less" || ".tmpl"

includeFilter in (Assets, LessKeys.less) := "admin.less" | "editor.less" | "game.less" | "site.less"

includeFilter in uglify := "*app.js"

LessKeys.sourceMap := false

UglifyKeys.sourceMap := false

LessKeys.compress := true

CoffeeScriptKeys.sourceMap := false

CoffeeScriptKeys.bare := true

HandlebarsKeys.sourceMap := false

HandlebarsKeys.bare := true

Concat.srcDirs := Seq(
  (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value,
  (resourceManaged in define in Assets).value,
  (resourceDirectory in Assets).value)

Concat.groups := Seq(
  "javascripts/as.js" -> group(
    (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "require.js" +++
      (resourceManaged in define in Assets).value / "javascripts" / "shared" ** "*.js" +++
      (resourceManaged in define in Assets).value / "javascripts" / "admin" ** "*.js" +++
      (resourceManaged in define in Assets).value / "templates" / "admin" ** "*.js" +++
      (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "start.js"
  ),
  "javascripts/es.js" -> group(
    (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "require.js" +++
      (resourceManaged in define in Assets).value / "javascripts" / "engine" ** "*.js" +++
      (resourceManaged in define in Assets).value / "javascripts" / "editor" ** "*.js" +++
      (resourceManaged in define in Assets).value / "templates" / "editor" ** "*.js" +++
      (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "start.js"
  ),
  "javascripts/gs.js" -> group(
    (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "require.js" +++
      (resourceManaged in define in Assets).value / "javascripts" / "engine" ** "*.js" +++
      (resourceManaged in define in Assets).value / "javascripts" / "game" ** "*.js" +++
      (resourceManaged in define in Assets).value / "templates" / "game" ** "*.js" +++
      (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "start.js"
  ),
  "javascripts/ss.js" -> group(
    (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "require.js" +++
      (resourceManaged in define in Assets).value / "javascripts" / "shared" ** "*.js" +++
      (resourceManaged in define in Assets).value / "javascripts" / "site" ** "*.js" +++
      (resourceManaged in define in Assets).value / "templates" / "site" ** "*.js" +++
      (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "start.js"
  ),
  "javascripts/al.min.js" -> group(
    (resourceDirectory in Assets).value / "javascripts" / "libs" / "admin" ** "*.js"
  ),
  "javascripts/el.min.js" -> group(
    (resourceDirectory in Assets).value / "javascripts" / "libs" / "editor" ** "*.js"
  ),
  "javascripts/gl.min.js" -> group(
    (resourceDirectory in Assets).value / "javascripts" / "libs" / "game" ** "*.js"
  ),
  "javascripts/sl.min.js" -> group(
    (resourceDirectory in Assets).value / "javascripts" / "libs" / "site" ** "*.js"
  )
)