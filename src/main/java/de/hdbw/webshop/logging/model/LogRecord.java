package de.hdbw.webshop.logging.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@Document
public class LogRecord {
    @Id
    private String id;
    private String level;
    private String logger;
    private String thread;
    private String message;
    private String exception;
    private List<String> stacktrace;
    private Date timestamp;

    public LogRecord(String level, String logger, String thread, String message, String exception, List<String> stacktrace, Date timestamp) {
        this.level = level;
        this.logger = logger;
        this.thread = thread;
        this.message = message;
        this.exception = exception;
        this.stacktrace = stacktrace;
        this.timestamp = timestamp;
    }
}
