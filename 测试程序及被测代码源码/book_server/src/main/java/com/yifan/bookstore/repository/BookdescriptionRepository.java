package com.yifan.bookstore.repository;

import com.yifan.bookstore.entry.Bookdescription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookdescriptionRepository extends MongoRepository<Bookdescription,Integer> {
    Bookdescription findByBookId(Integer bookId);
}
