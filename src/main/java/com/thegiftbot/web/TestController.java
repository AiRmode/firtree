package com.thegiftbot.web;

import com.thegiftbot.entity.TestEntity;
import com.thegiftbot.exception.NoTestEntityException;
import com.thegiftbot.repository.TestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestEntityRepository testEntityRepository;

    @GetMapping("/entities")
    public List<TestEntity> retrieveAllEntities() {
        return testEntityRepository.findAll();
    }

    @GetMapping("/entities/{id}")
    public TestEntity retrieveEntity(@PathVariable long id) {
        var entity = testEntityRepository.findById(id);
        if (entity.isEmpty()) {
            throw new NoTestEntityException("id" + id);
        }
        return entity.get();
    }

    @DeleteMapping("/entities/{id}")
    public void deleteEntity(@PathVariable long id) {
        testEntityRepository.deleteById(id);
    }

    @PostMapping("/entities")
    public ResponseEntity<Object> createEntity(@RequestBody TestEntity entity) {
        var savedEntity= testEntityRepository.save(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedEntity.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/entities/{id}")
    public ResponseEntity<Object> updateEntity(@RequestBody TestEntity newEntity, @PathVariable long id) {
        var entityOptional = testEntityRepository.findById(id);
        if (entityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        newEntity.setId(id);
        testEntityRepository.save(newEntity);
        return ResponseEntity.noContent().build();
    }
}
