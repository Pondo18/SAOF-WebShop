package de.hdbw.webshop.service;

import de.hdbw.webshop.model.Test;
import de.hdbw.webshop.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<Test> getTests() {
        return testRepository.findAll();
    }


}
