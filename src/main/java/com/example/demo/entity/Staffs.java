package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "staffs")
public class Staffs {

    @Id
    private String id;
    private String OrganizationName;
    private String EventID;
    private String TokenAPI;
    private String CameraID;
    private String SessionID;
    private String UniversalID;
    private String Area;
    private String Label;
    private String PictureURL;


    private String Timestamp;

    private String  Hour;
    private String  Day;
    private String DayName;
    private String  Week;
    private String  Month;
    private String  Year;
    private String HourName;
    private String MonthName;
    private String Visitor;
    private String Out;
    private String StayDuration;
    private String FirstName;
    private String LastName;
    private String Trigger;
    private String EventType;
    private Date Created;
    private int __v;

    // Getters and setters...
}
