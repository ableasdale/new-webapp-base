import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

/* For debugging Jetty - adding detailed logging back in
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
*/

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

// without JUL bridge:
//root(WARN, ["STDOUT", "ROLLING"])  // prod
//root(DEBUG, ["STDOUT", "FILE"])  // dev

// with JUL bridge: (workaround: see links above)
def rootLvl = INFO
root(INFO, ["STDOUT"])
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

logger("org.glassfish", TRACE)
logger("com.example", DEBUG)

scan("30 seconds")  // reload/apply-on-change config every x sec