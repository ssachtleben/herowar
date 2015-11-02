name := """herowar"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.10.Final",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "commons-beanutils" % "commons-beanutils" % "1.9.2",
  "commons-io" % "commons-io" % "2.4",
  "org.apache.httpcomponents" % "httpclient" % "4.4",
  "com.ssachtleben" %% "play-auth-plugin" % "4.0",
  "com.ssachtleben" %% "play-event-plugin" % "4.0",
  "com.ssachtleben" %% "play-json-plugin" % "4.0",
  "com.ssachtleben" %% "play-compress-plugin" % "4.0",
  "com.ardor3d" % "ardor3d-core" % "0.9"
)

resolvers += Resolver.url("ssachtleben repository (snapshots)", url("http://ssachtleben.github.io/play-plugins/repository/snapshots/"))(Resolver.ivyStylePatterns)

resolvers += "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/"
// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

JsTaskKeys.timeoutPerSource := new scala.concurrent.duration.DurationInt(2).hours

// live pipeline with uglify ... pipelineStages in Assets := Seq(define,filter,concat,uglify,digest,gzip)
pipelineStages in Assets := Seq(define,filter,concat,digest)

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
//  "javascripts/as.js" -> group(
//    (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "require.js" +++
//      (resourceManaged in define in Assets).value / "javascripts" / "shared" ** "*.js" +++
//      (resourceManaged in define in Assets).value / "javascripts" / "admin" ** "*.js" +++
//      (resourceManaged in define in Assets).value / "templates" / "admin" ** "*.js" +++
//      (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "start.js"
//  ),
//  "javascripts/es.js" -> group(
//    (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "require.js" +++
//      (resourceManaged in define in Assets).value / "javascripts" / "engine" ** "*.js" +++
//      (resourceManaged in define in Assets).value / "javascripts" / "editor" ** "*.js" +++
//      (resourceManaged in define in Assets).value / "templates" / "editor" ** "*.js" +++
//      (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "start.js"
//  ),
//  "javascripts/gs.js" -> group(
//    (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "require.js" +++
//      (resourceManaged in define in Assets).value / "javascripts" / "engine" ** "*.js" +++
//      (resourceManaged in define in Assets).value / "javascripts" / "game" ** "*.js" +++
//      (resourceManaged in define in Assets).value / "templates" / "game" ** "*.js" +++
//      (resourceManaged in CoffeeScriptKeys.coffeescript in Assets).value / "javascripts" / "start.js"
//  ),
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

fork in run := true

fork in run := true

fork in run := true