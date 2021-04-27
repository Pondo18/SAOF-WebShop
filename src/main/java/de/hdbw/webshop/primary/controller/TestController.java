package de.hdbw.webshop.primary.controller;

import de.hdbw.webshop.logging.repository.LoggingRepository;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.Date;

@RestController
//@EnableMongoRepositories(basePackages = { "de.hdbw.webshop.logging.repository" })
public class TestController {

    private final LoggingRepository loggingRepository;
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public TestController(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    @GetMapping("/test")
    public void addNewTest() {
////        Test test = new Test("TestName");
////        return loggingRepository.save(test);
//        return loggingRepository.count();
        logger.info("Class instance created at {}",
                DateFormat.getInstance().format(new Date()));
    }
}
