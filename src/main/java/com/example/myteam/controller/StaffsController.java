package com.example.myteam.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.myteam.service.StaffsService;
import com.example.myteam.entity.Staffs;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("api")
public class StaffsController {
@Autowired
    private StaffsService staffsService;

        @GetMapping("/all")
  public List<Staffs> getStaffs()
    { return staffsService.getStaffs();}


    @GetMapping("/all/{id}")
    public Staffs getStaff(@PathVariable String id)
    { return staffsService.getStaffsById(id);}

  @GetMapping("/ByFN/{firstName}")
  public List<Staffs> getStaffsByFirstName(@PathVariable String firstName) {
    return staffsService.getStaffsByFirstName(firstName);
  }
  @GetMapping("/ByLN/{lastName}")
  public List<Staffs> getStaffByLastName(@PathVariable String lastName) {
    return staffsService.getStaffsByLastName(lastName);
  }
  @GetMapping("/ByArea/{area}")
  public List<Staffs> getStaffByArea(@PathVariable String area) {
    return staffsService.getStaffsByArea(area);
  }

  @GetMapping("/totalEmployees")
  public long getTotalNumberOfEmployees() {
    return staffsService.getTotalNumberOfEmployees();
  }
  @GetMapping("/totalEmployeesOutByHourName")
  public Map<String, Long> getTotalNumberOfEmployeesByHourName() {
    return staffsService.getTotalNumberOfEmployeesByHourName();
  }
  @GetMapping("/totalEmployeesInStoreByHourName/{hourName}")
  public long getTotalNumberOfEmployeesInStoreByHourName(@PathVariable String hourName) {
    return staffsService.getTotalNumberOfEmployeesInStoreByHourName(hourName);
  }
  @GetMapping("/totalEmployeesInStoreByMonthName/{monthName}")
  public long getTotalNumberOfEmployeesInStoreByMonthName(@PathVariable String monthName) {
    return staffsService.getTotalNumberOfEmployeesInStoreByMonthName(monthName);
  }
  @GetMapping("/totalEmployeesInStoreByDayName/{dayName}")
  public long getTotalNumberOfEmployeesInStoreByDayName(@PathVariable String dayName) {
    return staffsService.getTotalNumberOfEmployeesInStoreByDayName(dayName);
  }
  @GetMapping("/totalEmployeesInStoreByDay/{day}")
  public long getTotalNumberOfEmployeesInStoreByDay(@PathVariable String day) {
    return staffsService.getTotalNumberOfEmployeesInStoreByDayName(day);
  }
  @GetMapping("/totalAttendance")
  public long getTotalNumberOfAttendance() {
    return staffsService.getTotalNumberOfAttendance();
  }



  @GetMapping("/totalEmployeesByArea")
  public Map<String, Long> getTotalNumberOfEmployeesByArea() {
    return staffsService.getTotalNumberOfEmployeesByArea();
  }
  @GetMapping("/totalStaffOut")
  public long getTotalNumberOfStaffOut() {
    return staffsService.getTotalNumberOfStaffOut();
  }
  @GetMapping("/currentDateTimeFormatted")
  public String getCurrentDateTimeFormatted() {
    return staffsService.getCurrentDateTimeFormatted();
  }
  @GetMapping("/staffsInfo")
  public ResponseEntity<Map<String, Object>> getStaffsInfo() {
    String currentDateTime = staffsService.getCurrentDateTimeFormatted();
    long totalStaffOut = staffsService.getTotalNumberOfStaffOut();

    Map<String, Object> response = new HashMap<>();
    response.put("currentDateTime", currentDateTime);
    response.put("totalStaffOut", totalStaffOut);

    return ResponseEntity.ok(response);
  }
  @GetMapping("/staffOutByYear/{year}")
  public long getStaffOutByYear(@PathVariable String year) {
    return staffsService.getTotalNumberOfStaffOutByYear(year);
  }
  @GetMapping("/staffOutByMonth/{month}")
  public long getStaffOutByMonth( @PathVariable String month) {
    return staffsService.getTotalNumberOfStaffOutByMonth( month);
  }
  @GetMapping("/staffOutByWeek/{week}")
  public long getStaffOutByWeek(@PathVariable String week) {
    return staffsService.getTotalNumberOfStaffOutByWeek(week);
  }
  @GetMapping("/totalAttendanceByDay/{day}")
  public long getTotalNumberOfAttendanceByDay(@PathVariable String day) {
    return staffsService.getTotalNumberOfAttendanceByDay(day);
  }
  @GetMapping("/totalAttendanceByHour/{hourName}")
  public long getTotalNumberOfAttendanceByHour(@PathVariable String hourName) {
    return staffsService.getTotalNumberOfAttendanceByHour(hourName);
  }
  @GetMapping("/totalAttendanceByWeek/{week}")
  public long getTotalNumberOfAttendanceByWeek(@PathVariable String week) {
    return staffsService.getTotalNumberOfAttendanceByWeek(week);
  }
  @GetMapping("/totalAttendanceByMonth/{month}")
  public long getTotalNumberOfAttendanceByMonth(@PathVariable String month) {
    return staffsService.getTotalNumberOfAttendanceByMonth(month);
  }
  @GetMapping("/totalAttendanceByYear/{year}")
  public long getTotalNumberOfAttendanceByYear(@PathVariable String year) {
    return staffsService.getTotalNumberOfAttendanceByYear(year);

  }
  @GetMapping("/totalAttendByWeek")
  public Map<String, Long> getTotalAttendByWeek() {
    return staffsService.getTotalAttendByWeek();
  }
  @GetMapping("/totalAttendanceByHourName/{hourName}")
  public long getTotalNumberOfAttendanceByHourName(@PathVariable String hourName) {
    return staffsService.getTotalNumberOfAttendanceByHourName(hourName);
  }
  @GetMapping("/totalAttendanceByMonthName/{monthName}")
  public long getTotalNumberOfAttendanceByMonthName(@PathVariable String monthName) {
    return staffsService.getTotalNumberOfAttendanceByMonthName(monthName);
  }
  @GetMapping("/totalAttendanceByFirstName/{firstName}")
  public long getTotalNumberOfAttendanceByFirstName(@PathVariable String firstName) {
    return staffsService.getTotalNumberOfAttendanceByFirstName(firstName);
  }
  @GetMapping("/totalVisitorCountByWeek/{week}")
  public long getTotalVisitorCountByWeek(@PathVariable String week) {
    return staffsService.getTotalNumberOfAttendanceByWeek(week);
  }
  @GetMapping("/totalNumberOfVByWeek/{week}")
  public long getTotalNumberOfVByWeek(@PathVariable String week) {
    return staffsService.getTotalNumberOfVByWeek(week);
  }
  @GetMapping("/totalNumberOfVByFirstName/{firstName}")
  public long getTotalNumberOfVByFirstName(@PathVariable String firstName) {
    return staffsService.getTotalNumberOfVByFirstName(firstName);
  }
  @GetMapping("/distinctFirstNames")
  public ResponseEntity<List<String>> getDistinctFirstNames() {
    List<String> distinctFirstNames = staffsService.getDistinctFirstNames();
    return ResponseEntity.ok(distinctFirstNames);
  }
  @GetMapping("/stayDurationByFirstName")
  public Map<String, Double> getStayDurationByFirstName() {
    return staffsService.getStayDurationByFirstName();
  }



  @PostMapping("/add")
  public ResponseEntity<Staffs> addStaff(@RequestBody Staffs staff) {
    Staffs addedStaff = staffsService.addStaff(staff);
    return new ResponseEntity<>(addedStaff, HttpStatus.CREATED);
  }
}







