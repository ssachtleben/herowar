# HeroWar Webapplication 0.1-ALPHA - Framework/Engine

**Important:**
In sources its not legal to have non utf-8 characters.
To scan for, type the following in source folder after clean:
find . "" -exec grep -I -H --color='auto' -P -n "[\x80-\xFF]" {} \; 


## TODOS:

### New

- Remove comment out of match result in me api show method
- Update depedencies:
  "org.hibernate" % "hibernate-entitymanager" % "5.0.3.Final",
  "org.postgresql" % "postgresql" % "9.4-1204-jdbc4",
  "commons-beanutils" % "commons-beanutils" % "1.9.2",
  "commons-io" % "commons-io" % "2.4",
  "org.apache.httpcomponents" % "httpclient" % "4.5.1",
  "com.ssachtleben" %% "play-auth-plugin" % "4.0",
  "com.ssachtleben" %% "play-compress-plugin" % "4.0",
  "com.ssachtleben" %% "play-cron-plugin" % "4.0",
  "com.ssachtleben" %% "play-event-plugin" % "4.0",
  "com.ssachtleben" %% "play-json-plugin" % "4.0",
  "com.ardor3d" % "ardor3d-core" % "0.9"

### Old

#### Section: building and transforming resources

#### Section: Editor

1. Implement textures, geometries and image loading cases in preloader

#### Section: Site

1. Style gritter message (remove image background and use css3)
2. Add Confirm Email to signup
3. Fix Email regex pattern in js since two double points break the signup
4. Save newsletter boolean during signup


#### Section: How to build project

**Intial project import**

1. Go to the application folder
2. Run play, the SBT prompt should show up
3. Type compile to pre-compile the app
4. Then type eclipse to generate the eclipse project files
5. Open eclipse
6. Select File -> Import… -> Existing projects into Workspace
7. Pick your application folder and click Finish.
8. export SBT_OPTS="-Xmx2G -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=2G -Xss2M  -Duser.timezone=GMT"

**Fix compilation issues**

1. Right click on the project, select Properties then Java Build Path
2. In the Libraries tab, click Add Class Folder…
3. Check target/scala-2.9.1/classes and target/scala-2.9.1/classes_managed
4. Back in Java Build Path click Add external JARs…
5. Select play\repository\local\org.scala-lang\scala-library\2.9.1\jars\scala-library.jar

**Updated dependencies**

1. Go to the application folder
2. Run play, the SBT prompt should show up
3. Then type eclipse to generate the eclipse project files


#### Section: Json API

The json api is located under /api and allows the to get informations from database or put new records.

These api errors could occur:

**80000 - Form validation failed**

The form validation failed for some reason, more detailed information will be send in body object.

**80010 - Username/Password is incorrect**

This error can be caused by an incorrect username or password.


#### Section: page/pagedesign

#### Section: Database
All entities shouldnt us List's, on table generation with Hibernate important indices are missing (List's allows multiple references of same object!)
The third party lib "deadbolt" uses interfaces with list, this must be changed too


#### Section: Three.js webgl-client engine


#### Section: Map-Editor


#### Section: Network (websocket)


#### Section: Server game engine
