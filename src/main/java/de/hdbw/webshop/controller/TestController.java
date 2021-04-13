package de.hdbw.webshop.controller;

import de.hdbw.webshop.model.Test;
import de.hdbw.webshop.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/testDB")
    public List<Test> getTests() {
        return testService.getTests();
    }

}
