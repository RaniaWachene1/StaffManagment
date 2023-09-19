package com.example.demo.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.StaffsService;
import com.example.demo.entity.Staffs;

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
  @GetMapping("/totalvisitors")
  public long getTotalNumberOfVByWeek() {
    return staffsService.getTotalNumberOfVByWeek();
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
  @GetMapping("/totalNumberOfVisitorsByFirstName/{firstName}")
  public ResponseEntity<Long> getTotalVisitorsByFirstName(@PathVariable String firstName) {
    long totalVisitors = staffsService.getTotalNumberOfVisitorsByFirstName(firstName);
    return new ResponseEntity<>(totalVisitors, HttpStatus.OK);
  }
  @GetMapping("/stayDuration")
  public Map<String, Double> getStayDuration() {
    return staffsService.getStayDuration();
  }

  @PostMapping("/add")
  public ResponseEntity<Staffs> addStaff(@RequestBody Staffs staff) {
    Staffs addedStaff = staffsService.addStaff(staff);
    return new ResponseEntity<>(addedStaff, HttpStatus.CREATED);
  }
  @GetMapping("/distinctAreas")
  public ResponseEntity<List<String>> getDistinctAreas() {
    List<String> distinctAreas = staffsService.getDistinctAreas();
    return ResponseEntity.ok(distinctAreas);
  }
  @GetMapping("/distinctHour")
  public ResponseEntity<List<String>> getDistinctHour() {
    List<String> distinctHour = staffsService.getDistinctHour();
    return ResponseEntity.ok(distinctHour);
  }
  @GetMapping("/distinctHourT")
  public ResponseEntity<List<String>> getDistinctHourT() {
    List<String> distinctHourT = staffsService.getDistinctHourT();
    return ResponseEntity.ok(distinctHourT);
  }
  @GetMapping("/distinctMonth")
  public ResponseEntity<List<String>> getDistinctMonth() {
    List<String> distinctMonth = staffsService.getDistinctMonth();
    return ResponseEntity.ok(distinctMonth);
  }
  @GetMapping("/distinctWeek")
  public ResponseEntity<List<String>> getDistinctWeek() {
    List<String> distinctWeek = staffsService.getDistinctWeek();
    return ResponseEntity.ok(distinctWeek);
  }
  @GetMapping("/distinctMonthT")
  public ResponseEntity<List<String>> getDistinctMonthT() {
    List<String> distinctMonthT = staffsService.getDistinctMonthT();
    return ResponseEntity.ok(distinctMonthT);
  }
  @GetMapping("/distinctYear")
  public ResponseEntity<List<String>> getDistinctYear() {
    List<String> distinctYear = staffsService.getDistinctYear();
    return ResponseEntity.ok(distinctYear);
  }
  @GetMapping("/total-attendance/{area}")
  public Map<String, Long> getTotalAttendByWeekArea(@PathVariable String area) {
    return staffsService.getTotalAttendByWeekArea(area);
  }
  @GetMapping("/W/{week}")
  public Map<String, Long> getTotalAttendByWeekW(@PathVariable String week) {
    return staffsService.getTotalAttendByWeekW(week);
  }
  @GetMapping("/area/{area}")
  public Map<String, Double> getStayDurationByArea(@PathVariable String area) {
    return staffsService.getStayDurationByArea(area);
  }
  @GetMapping("/HN/{hourName}")
  public ResponseEntity<?> getTotalAttendByWeekHN(@PathVariable String hourName) {
    Map<String, Long> totalAttendanceByWeekHN = staffsService.getTotalAttendByWeekHN(hourName);
    return new ResponseEntity<>(totalAttendanceByWeekHN, HttpStatus.OK);
  }
  @GetMapping("/MN/{monthName}")
  public ResponseEntity<?> getTotalAttendByWeekMN(@PathVariable String monthName) {
    Map<String, Long> totalAttendanceByWeekMN = staffsService.getTotalAttendByWeekMN(monthName);
    return new ResponseEntity<>(totalAttendanceByWeekMN, HttpStatus.OK);
  }
  @GetMapping("/M/{month}")
  public ResponseEntity<?> getTotalAttendByWeekM(@PathVariable String month) {
    Map<String, Long> totalAttendanceByWeekM = staffsService.getTotalAttendByWeekM(month);
    return new ResponseEntity<>(totalAttendanceByWeekM, HttpStatus.OK);
  }
  @GetMapping("/totalStaffOut/{firstName}")
  public ResponseEntity<Long> getTotalNumberOfStaffOutByFirstName (@PathVariable String firstName) {
    long totalout = staffsService.getTotalNumberOfStaffOutByFirstName (firstName);
    return new ResponseEntity<>(totalout, HttpStatus.OK);
  }
  @GetMapping("/by-firstname")
  public Map<String, String> getEarliestArrivalTimeByFirstName() {
    return staffsService.getEarliestArrivalTimeByFirstName();
  }
  @GetMapping("/filter")
  public ResponseEntity<?> filterData(
          @RequestParam(name = "hour", required = false) String hour,
          @RequestParam(name = "week", required = false) String week,
          @RequestParam(name = "month", required = false) String month,
          @RequestParam(name = "year", required = false) String year,
          @RequestParam(name = "area", required = false) String area,
          @RequestParam(name = "hourName", required = false) String hourName,
          @RequestParam(name = "monthName", required = false) String monthName,
          @RequestParam(name = "firstName", required = false) String firstName) {

    StaffsService.FilterResult result = staffsService.filterData(hour, week, month, year, area, hourName, monthName, firstName);

    if (result.getMapResult() != null) {
      Map<String, Object> response = new HashMap<>();
      response.put("mapResult", result.getMapResult());
      response.put("saty", result.getStay());
      response.put("longResult", result.getLongResult());
      response.put("staffOut", result.getStaffOut());
      response.put("Enter", result.getEnter());
      response.put("Exit", result.getExit());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(result.getLongResult(), HttpStatus.OK);
    }
  }
  @GetMapping("/formatted")
  public ResponseEntity<List<String>> getAllFormattedTimestamps() {
    List<String> formattedTimestamps = staffsService.getAllFormattedTimestamps();
    return new ResponseEntity<>(formattedTimestamps, HttpStatus.OK);
  }

  @GetMapping("/formatted-by-firstname")
  public Map<String, List<String>> getAllFormattedTimestampsByFirstName() {
    // Call the service method to get the data
    return staffsService.getAllFormattedTimestampsByFirstName();
  }
  @GetMapping("/formatted-by-firstname-Out")
  public Map<String, List<String>> getAllFormattedTimestampsByFirstNameOut() {
    return staffsService.getAllFormattedTimestampsByFirstNameOut();
  }
  @GetMapping("/earliest-arrival-time")
  public String getEarliestArrivalTime() {
    return staffsService.getEarliestArrivalTime();
  }
  @GetMapping("/earliest-leaving-time")
  public String getLatestLeavingTimeOut() {
    return staffsService.getLatestLeavingTimeOut();
  }
}













