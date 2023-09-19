package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "members")
public class Member {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthday;
    private String address;
    private String email;
    private Number phone;
    private String imageName;
    private String zone;
    private String hireDate;
    @DBRef
    private Staffs staff;
    // Constructors...
    // Getters and setters...

    // Constructors...

    public Member() {
    }

    public Member(String firstName, String lastName, String gender, String birthday, String address, String email, Number phone, String  imageName, String zone, String hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.imageName = imageName;
        this.zone = zone;
        this.hireDate = hireDate;
    }

    public Member(String firstName, String lastName, String gender, String birthday, String address, String email, Number phone, String  imageName, String zone, String hireDate, Staffs staff) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.imageName = imageName;
        this.zone = zone;
        this.hireDate = hireDate;
        this.staff = staff;
    }
}
