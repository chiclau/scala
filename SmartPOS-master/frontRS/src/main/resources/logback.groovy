import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP
import static ch.qos.logback.classic.Level.INFO

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}
appender("base_log", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${logPath}/%d{yyyyMMdd}/base_log.%i.log"
        maxHistory = 35
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            MaxFileSize = "400MB"
        }
    }
}
logger("org.jboss.resteasy", INFO)
root(INFO, ["STDOUT", "base_log"])