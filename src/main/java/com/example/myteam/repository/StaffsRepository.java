package com.example.myteam.repository;
import com.example.myteam.entity.Staffs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface StaffsRepository extends MongoRepository<Staffs, String> {

    @Query("{'FirstName' : ?0}")
    List<Staffs> findByFirstName(String firstName);

    @Query("{'LastName' : ?0}")
    List<Staffs> findByLastName(String lastName);

    @Query("{'Area' : ?0}")
    List<Staffs> findByArea(String area);

    long count();
    @Query(value = "{}", fields = "{'FirstName': 1, '_id': 0}")
    List<Staffs> findDistinctFirstNames();

    @Query("{'Area': ?0}")
    long countByArea(String area);

    long countByFirstNameAndVisitor(String firstName, String visitor);
    @Query(value = "{'FirstName': {$ne: null, $ne: ''}}", fields = "{'FirstName': 1, '_id': 0}")
    List<String> findDistinctFirstNamess();




}
