package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member, String> {
    List<Member> findByFirstNameContainingIgnoreCase(String firstName);
    List<Member> findByLastNameContainingIgnoreCase(String lastName);
    List<Member> findByGender(String gender);
    List<Member> findByZone(String zone);
    List<Member> findByEmail(String email);
    // Add more custom queries if needed
}
