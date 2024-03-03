package com.bej.product.repository;
import com.bej.product.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends MongoRepository<User, String> {
    User findByUserId(String userId);
}
