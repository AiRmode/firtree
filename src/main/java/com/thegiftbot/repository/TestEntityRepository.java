package com.thegiftbot.repository;

import com.thegiftbot.entity.TestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestEntityRepository extends MongoRepository<TestEntity, Long> {
}
