package de.hdbw.webshop.logging.repository;

import de.hdbw.webshop.logging.model.LogRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends MongoRepository<LogRecord, String> {

}
