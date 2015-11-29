import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import org.slf4j.bridge.SLF4JBridgeHandler
import ch.qos.logback.classic.jul.LevelChangePropagator

//logback.debug=true

// for debug: just to see it in case something is logging/initialized before
System.out.println( 'my myapp logback.groovy is loading' )

// see also: http://logback.qos.ch/manual/configuration.html#LevelChangePropagator
// performance speedup for redirected JUL loggers
def lcp = new LevelChangePropagator()
lcp.context = context
lcp.resetJUL = true
context.addListener(lcp)

// needed only for the JUL bridge: http://stackoverflow.com/a/9117188/1915920
java.util.logging.LogManager.getLogManager().reset()
SLF4JBridgeHandler.removeHandlersForRootLogger()
SLF4JBridgeHandler.install()
java.util.logging.Logger.getLogger( "global" ).setLevel( java.util.logging.Level.FINEST )

def logPattern = "%date |%.-1level| [%thread] %20.20logger{10}|  %msg%n"

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = logPattern
    }
}

/*// outcommenting in dev will not create dummy empty file
appender("ROLLING", RollingFileAppender) {  // prod
    encoder(PatternLayoutEncoder) {
        Pattern = "%date %.-1level [%thread] %20.20logger{10}  %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${WEBAPP_DIR}/log/orgv-fst-gwt-%d{yyyy-MM-dd}.zip"
    }
}
*/
/*
appender("FILE", FileAppender) {  // dev

    // log to myapp/tmp (independent of running in dev/prod or junit mode:

    //System.out.println( 'DEBUG: WEBAPP_DIR env prop:  "."='+new File('.').absolutePath+',  \${WEBAPP_DIR}=${WEBAPP_DIR},  env=' + System.getProperty( "WEBAPP_DIR" ))
    String webappDirName = "war"
    if ( new File( "./../"+webappDirName ).exists() )  // we are not running within a junit test
        file = "../tmp/myapp.log"
    else  // junit test
        file = "tmp/myapp-junit-tests.log"

    encoder(PatternLayoutEncoder) { pattern = logPattern }
}*/

// without JUL bridge:
//root(WARN, ["STDOUT", "ROLLING"])  // prod
//root(DEBUG, ["STDOUT", "FILE"])  // dev

// with JUL bridge: (workaround: see links above)
def rootLvl = DEBUG
root(TRACE, ["STDOUT"])
// I manually added all "root package dirs" I know my libs are based on to apply
// the root level to the second "package dir level" at least
// depending on your libs used you could remove entries, but I would recommend
// to add common entries instead (feel free to edit this post if you like to
// enhance it anywhere)
/*logger( "antlr", rootLvl )
logger( "de", rootLvl )
logger( "ch", rootLvl )
logger( "com", rootLvl )
logger( "java", rootLvl )
logger( "javassist", rootLvl )
logger( "javax", rootLvl )
logger( "junit", rootLvl )
logger( "groovy", rootLvl )
logger( "net", rootLvl )
logger( "org", rootLvl )
logger( "sun", rootLvl )

*/
logger( "org.glassfish", rootLvl )
// my logger setup

logger( "com.example", DEBUG )


//logger( "org.hibernate.SQL", DEBUG )  // debug: log SQL statements in DEBUG mode
//logger( "org.hibernate.type", TRACE )  // debug: log JDBC parameters in TRACE mode
//logger( "org.hibernate.type.BasicTypeRegistry", WARN )  // uninteresting

scan("30 seconds")  // reload/apply-on-change config every x sec