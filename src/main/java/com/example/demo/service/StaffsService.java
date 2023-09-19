package com.example.demo.service;
import com.example.demo.entity.Staffs;
import com.example.demo.repository.StaffsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class StaffsService {

    private StaffsRepository staffsRepository;
    private final MongoTemplate mongoTemplate;

    @GetMapping("/all")
    public List<Staffs> getStaffs() {
        return staffsRepository.findAll();
    }

    public Staffs getStaffsById(String id) {
        Optional<Staffs> staffsOptional = staffsRepository.findById(id);

        Staffs staffs = staffsOptional.orElse(null); // You can replace null with a default value if needed

        return staffs;
    }

    public List<Staffs> getStaffsByFirstName(String firstName) {
        return staffsRepository.findByFirstName(firstName);
    }

    public List<Staffs> getStaffsByLastName(String lastName) {
        return staffsRepository.findByLastName(lastName);
    }

    public List<Staffs> getStaffsByArea(String area) {
        return staffsRepository.findByArea(area);
    }

    public long getTotalNumberOfAttendance() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendance = staffList.stream()
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalAttendance;
    }


    public long getTotalNumberOfAttendanceByDay(String targetDay) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendanceByDay = staffList.stream()
                .filter(staff -> targetDay.equals(staff.getDay()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalAttendanceByDay;
    }

    public long getTotalNumberOfAttendanceByHour(String targetHourName) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendanceByHour = staffList.stream()
                .filter(staff -> targetHourName.equals(staff.getHourName()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalAttendanceByHour;
    }

    public long getTotalNumberOfAttendanceByWeek(String targetWeek) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendanceByWeek = staffList.stream()
                .filter(staff -> targetWeek.equals(staff.getWeek()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalAttendanceByWeek;
    }

    public long getTotalNumberOfAttendanceByMonth(String targetMonth) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendanceByMonth = staffList.stream()
                .filter(staff -> targetMonth.equals(staff.getMonth()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalAttendanceByMonth;
    }

    public long getTotalNumberOfAttendanceByYear(String targetYear) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendanceByYear = staffList.stream()
                .filter(staff -> targetYear.equals(staff.getYear()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalAttendanceByYear;
    }

    public long getTotalNumberOfAttendanceByHourName(String targetHourName) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendanceByHourName = staffList.stream()
                .filter(staff -> targetHourName.equals(staff.getHourName()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalAttendanceByHourName;
    }

    public long getTotalNumberOfAttendanceByMonthName(String targetMonthName) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendanceByMonthName = staffList.stream()
                .filter(staff -> targetMonthName.equals(staff.getMonthName()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalAttendanceByMonthName;
    }

    public long getTotalNumberOfAttendanceByFirstName(String targetFirstName) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalAttendanceByFirstName = staffList.stream()
                .filter(staff -> targetFirstName.equals(staff.getFirstName()))
                .count();

        return totalAttendanceByFirstName;
    }


    public long getTotalNumberOfEmployees() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        Set<String> uniqueFirstNames = new HashSet<>();

        for (Staffs staff : staffList) {
            String firstName = staff.getFirstName();
            uniqueFirstNames.add(firstName);
        }

        long totalEmployees = uniqueFirstNames.size();

        return totalEmployees;
    }

    public Map<String, Long> getTotalNumberOfEmployeesByHourName() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        Map<String, Set<String>> hourNameToFirstNamesMap = new HashMap<>();

        for (Staffs staff : staffList) {
            String hourName = staff.getHourName();
            String firstName = staff.getFirstName();
            String out = staff.getOut();

            if ("1".equals(out)) { // Only consider staff that have left
                hourNameToFirstNamesMap.computeIfAbsent(hourName, k -> new HashSet<>()).add(firstName);
            }
        }

        Map<String, Long> employeesByHourName = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : hourNameToFirstNamesMap.entrySet()) {
            employeesByHourName.put(entry.getKey(), (long) entry.getValue().size());
        }

        return employeesByHourName;
    }

    public long getTotalNumberOfEmployeesInStoreByMonthName(String targetMonthName) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalEmployeesInStore = staffList.stream()
                .filter(staff -> targetMonthName.equals(staff.getMonthName()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalEmployeesInStore;
    }

    public long getTotalNumberOfEmployeesInStoreByDayName(String targetDayName) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalEmployeesInStore = staffList.stream()
                .filter(staff -> targetDayName.equals(staff.getDayName()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalEmployeesInStore;
    }

    public long getTotalNumberOfEmployeesInStoreByDay(String targetDay) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalEmployeesInStore = staffList.stream()
                .filter(staff -> targetDay.equals(staff.getDay()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalEmployeesInStore;
    }

    public long getTotalNumberOfEmployeesInStoreByHourName(String targetHourName) {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        long totalEmployeesInStore = staffList.stream()
                .filter(staff -> targetHourName.equals(staff.getHourName()))
                .map(Staffs::getFirstName)
                .distinct()
                .count();

        return totalEmployeesInStore;
    }


    public Map<String, Long> getTotalNumberOfEmployeesByArea() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        Map<String, Set<String>> areaToNamesMap = new HashMap<>();

        for (Staffs staff : staffList) {
            String area = staff.getArea();
            String firstName = staff.getFirstName();

            if (area != null) {
                areaToNamesMap.computeIfAbsent(area, k -> new HashSet<>()).add(firstName);
            }
        }

        Map<String, Long> employeesByArea = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : areaToNamesMap.entrySet()) {
            employeesByArea.put(entry.getKey(), (long) entry.getValue().size());
        }

        return employeesByArea;
    }

    public long getTotalNumberOfStaffOut() {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()))
                .count();

        return totalStaffOut;
    }


    public List<String> getAllFormattedTimestamps() {
        // Fetch all records with timestamps
        List<Staffs> staffsList = staffsRepository.findAll();

        // Extract and format timestamps
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        List<String> formattedTimestamps = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter).format(outputFormatter))
                .collect(Collectors.toList());

        return formattedTimestamps;
    }

    private boolean isValidTimestamp(String timestamp, DateTimeFormatter formatter) {
        if (timestamp == null || timestamp.isEmpty()) {
            return false; // Handle null or empty timestamps
        }
        try {
            LocalDateTime.parse(timestamp, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public Map<String, List<String>> getAllFormattedTimestampsByFirstName() {
        // Fetch all records with timestamps
        List<Staffs> staffsList = staffsRepository.findAll();

        // Extract and format timestamps
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(" HH:mm:ss");

        Map<String, List<String>> formattedTimestampsByFirstName = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getVisitor())) // Filter by visitor "1"
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName, // Group by first name
                        Collectors.mapping(
                                staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter).format(outputFormatter),
                                Collectors.toList()
                        )
                ));

        return formattedTimestampsByFirstName;
    }
    public Map<String, List<String>> getAllFormattedTimestampsByFirstNameOut() {
        // Fetch all records with timestamps
        List<Staffs> staffsList = staffsRepository.findAll();

        // Extract and format timestamps
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("  HH:mm:ss");

        Map<String, List<String>> formattedTimestampsByFirstNameOut = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getOut())) // Filter by visitor "1"
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName, // Group by first name
                        Collectors.mapping(
                                staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter).format(outputFormatter),
                                Collectors.toList()
                        )
                ));

        return formattedTimestampsByFirstNameOut;
    }
    public Map<String, String> getEarliestArrivalTimeByFirstName() {
        // Fetch all records with timestamps
        List<Staffs> staffsList = staffsRepository.findAll();

        // Extract and format timestamps
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Map<String, String> earliestArrivalTimeByFirstName = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getVisitor())) // Filter by visitor "1"
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName, // Group by first name
                        Collectors.collectingAndThen(
                                Collectors.minBy(Comparator.comparing(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter))),
                                earliestArrival -> earliestArrival.map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter).format(outputFormatter)).orElse(null)
                        )
                ));

        return earliestArrivalTimeByFirstName;
    }
    public Map<String, String> getLatestLeavingTimeByFirstNameOut() {
        List<Staffs> staffsList = staffsRepository.findAll();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("  HH:mm:ss");

        Map<String, String> latestLeavingTimeByFirstNameOut = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getOut()))
                .collect(Collectors.toMap(
                        Staffs::getFirstName, // Key: First name
                        staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter).format(outputFormatter), // Value: Formatted timestamp
                        (existing, replacement) -> {
                            LocalDateTime existingTime = LocalDateTime.parse(existing, outputFormatter);
                            LocalDateTime replacementTime = LocalDateTime.parse(replacement, outputFormatter);
                            return existingTime.isAfter(replacementTime) ? existing : replacement;
                        }
                ));

        return latestLeavingTimeByFirstNameOut;
    }

    public String getEarliestArrivalTime() {
        // Fetch all records with timestamps
        List<Staffs> staffsList = staffsRepository.findAll();

        // Extract and format timestamps
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Optional<String> earliestArrivalTime = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getVisitor())) // Filter by visitor "1"
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter))
                .min(LocalDateTime::compareTo)
                .map(dateTime -> dateTime.format(outputFormatter));

        return earliestArrivalTime.orElse(null);
    }
    public String getLatestLeavingTimeOut() {
        List<Staffs> staffsList = staffsRepository.findAll();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Optional<String> latestLeavingTime = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getOut()))
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter).format(outputFormatter))
                .max(String::compareTo);

        return latestLeavingTime.orElse(null);
    }
    public String getLatestLeavingTimeByFirstName(String firstName) {
        // Fetch records with timestamps for the specified firstName
        List<Staffs> staffsList = staffsRepository.findByFirstName(firstName);

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Optional<String> latestLeavingTime = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getOut()))
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter).format(outputFormatter))
                .max(String::compareTo);

        return latestLeavingTime.orElse(null);
    }

    public String getEarliestArrivalTimeByFirstName(String firstName) {
        List<Staffs> staffsList = staffsRepository.findByFirstName(firstName); // Assuming you have a method like this to fetch by first name
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Optional<String> earliestArrivalTime = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getVisitor())) // Filter by visitor "1"
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter))
                .min(LocalDateTime::compareTo)
                .map(dateTime -> dateTime.format(outputFormatter));

        return earliestArrivalTime.orElse(null);
    }

    public String getLatestLeavingTimeOutByFirstName(String firstName) {
        List<Staffs> staffsList = staffsRepository.findByFirstName(firstName); // Assuming you have a method like this to fetch by first name
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Optional<String> latestLeavingTime = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getOut()))
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter).format(outputFormatter))
                .max(String::compareTo);

        return latestLeavingTime.orElse(null);
    }





    public String getCurrentDateTimeFormatted() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return currentDateTime.format(formatter);
    }


    public Staffs addStaff(Staffs staff) {
        return staffsRepository.save(staff);
    }



    public Map<String, Long> getTotalAttendByWeek() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        Map<String, Long> totalAttendanceByWeek = staffList.stream()
                .filter(staff -> staff.getWeek() != null) // Filter out entries with null week
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeek;
    }
    public Map<String, Long> getTotalAttendByWeekFN(String firstName) {
        List<Staffs> staffList = staffsRepository.findByFirstName(firstName); // Fetch staff members by FirstName

        Map<String, Long> totalAttendanceByWeekFN = staffList.stream()
                .filter(staff -> staff.getWeek() != null) // Filter out entries with null week
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeekFN;
    }

    public long getTotalNumberOfVByWeek() {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByWeek = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()))
                .count();

        return totalVisitorByWeek;
    }


    public long getTotalNumberOfVByFirstName(String firstName) {
        List<Staffs> staffList = staffsRepository.findByFirstName(firstName);

        long totalVisitorByFirstName = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()))
                .count();

        return totalVisitorByFirstName;
    }
    public List<String> getDistinctFirstNames() {
        List<String> distinctFirstNames = staffsRepository.findDistinctFirstNamess();
        return distinctFirstNames.stream()
                .filter(name -> name != null && !name.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
    public Map<String, Double> getStayDurationByFirstName() {
        List<Staffs> staffList = staffsRepository.findAll();

        Map<String, Double> stayDurationByFirstName = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));

        return stayDurationByFirstName;
    }
    public List<String> getDistinctAreas() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        List<String> distinctAreas = staffList.stream()
                .map(Staffs::getArea)
                .filter(area -> area != null && !area.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        return distinctAreas;
    }
    public List<String> getDistinctHour() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        List<String> distinctHour = staffList.stream()
                .map(Staffs::getHourName)
                .filter(hourName -> hourName != null && !hourName.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        return distinctHour;
    }
    public List<String> getDistinctHourT() {
        List<Staffs> staffList = staffsRepository.findAll();

        List<String> distinctHourT = staffList.stream()
                .map(Staffs::getHour)
                .filter(hour -> hour != null && !hour.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        return distinctHourT;
    }
    public List<String> getDistinctMonth() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        List<String> distinctMonth = staffList.stream()
                .map(Staffs::getMonthName)
                .filter(monthName -> monthName != null && !monthName.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        return distinctMonth;
    }
    public List<String> getDistinctYear() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        List<String> distinctYear = staffList.stream()
                .map(Staffs::getYear)
                .filter(year -> year != null && !year.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        return distinctYear;
    }
    public List<String> getDistinctMonthT() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        List<String> distinctMonthT = staffList.stream()
                .map(Staffs::getMonth)
                .filter(month -> month != null && !month.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        return distinctMonthT;
    }
    public List<String> getDistinctWeek() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        List<String> distinctWeek = staffList.stream()
                .map(Staffs::getWeek)
                .filter(week -> week != null && !week.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        return distinctWeek;
    }
    public List<String> getDistinctDayName() {
        List<Staffs> staffList = staffsRepository.findAll(); // Fetch all staff members

        List<String> distinctDayName = staffList.stream()
                .map(Staffs::getDayName)
                .filter(dayName -> dayName != null && !dayName.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        return distinctDayName;
    }


    public Map<String, Long> getTotalAttendByWeekArea(String area) {
        List<Staffs> staffList = staffsRepository.findByArea(area);

        Map<String, Long> totalAttendanceByWeekArea = staffList.stream()
                .filter(staff -> staff.getWeek() != null)
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeekArea;
    }
    public Map<String, Long> getTotalAttendByWeekHN(String hourName) {
        List<Staffs> staffList = staffsRepository.findByHourName(hourName);

        Map<String, Long> totalAttendanceByWeekHN = staffList.stream()
                .filter(staff -> staff.getHourName() != null)
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeekHN;
    }
    public Map<String, Long> getTotalAttendByWeekH(String hour) {
        List<Staffs> staffList = staffsRepository.findByHour(hour);

        Map<String, Long> totalAttendanceByWeekH = staffList.stream()
                .filter(staff -> staff.getHour() != null)
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeekH;
    }
    public Map<String, Long> getTotalAttendByWeekMN(String monthName) {
        List<Staffs> staffList = staffsRepository.findByMonthName(monthName);

        Map<String, Long> totalAttendanceByWeekMN = staffList.stream()
                .filter(staff -> staff.getMonthName() != null)
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeekMN;
    }
    public Map<String, Long> getTotalAttendByWeekM(String month) {
        List<Staffs> staffList = staffsRepository.findByMonth(month);

        Map<String, Long> totalAttendanceByWeekM = staffList.stream()
                .filter(staff -> staff.getMonth() != null)
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeekM;
    }
    public Map<String, Long> getTotalAttendByWeekY(String year) {
        List<Staffs> staffList = staffsRepository.findByYear(year);

        Map<String, Long> totalAttendanceByWeekY = staffList.stream()
                .filter(staff -> staff.getYear() != null)
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeekY;
    }
    public Map<String, Long> getTotalAttendByWeekW(String week) {
        List<Staffs> staffList = staffsRepository.findByWeek(week);

        Map<String, Long> totalAttendanceByWeekW = staffList.stream()
                .filter(staff -> staff.getWeek() != null)
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getFirstName, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeekW;
    }

    public Map<String, Long> getTotalAttendByArea(String area) {
        List<Staffs> staffList = staffsRepository.findByArea(area);

        Map<String, Long> totalAttendanceByWeek = staffList.stream()
                .filter(staff -> staff.getWeek() != null)
                .collect(Collectors.groupingBy(
                        Staffs::getWeek,
                        Collectors.mapping(Staffs::getArea, Collectors.collectingAndThen(Collectors.toSet(), set -> (long) set.size()))
                ));

        return totalAttendanceByWeek;
    }

    public class FilterResult {
        private Map<String, Long> mapResult;
        private Map<String, Double> stay;
        private long longResult;
        private long staffOut;
        private String Enter;
        private String Exit;
        public FilterResult(Map<String, Long> mapResult, Map<String, Double> stay, long longResult, long staffOut,String Enter,String Exit) {
            this.mapResult = mapResult;
            this.stay = stay;
            this.longResult = longResult;
            this.staffOut = staffOut;
            this.Enter= Enter;
            this.Exit= Exit;
        }

        public Map<String, Long> getMapResult() {
            return mapResult;
        }

        public Map<String, Double> getStay() {
            return stay;
        }

        public long getLongResult() {
            return longResult;
        }

        public long getStaffOut() {
            return staffOut;
        }
        public String getEnter() {
            return Enter;
        }
        public String getExit() {
            return Exit;
        }

    }

    public FilterResult filterData(String week, String hour, String month, String year, String area, String hourName, String monthName, String firstName) {
        Map<String, Long> mapResult = null;
        Map<String, Double> stay = null;
        long longResult = 0;
        long staffOut = 0;
        String Enter="";
        String Exit="";
        if (firstName != null) {
            mapResult = getTotalAttendByWeekFN(firstName);
            longResult = getTotalNumberOfVisitorsByFirstName(firstName);
            staffOut=getTotalNumberOfStaffOutByFirstName(firstName);
            stay=getStayDurationByFirstName(firstName);
            Enter=getEarliestArrivalTimeByFN(firstName);
            Exit=getLatestLeavingTimeByFirstName(firstName);

        } else if (area != null) {
            mapResult = getTotalAttendByArea(area);
            longResult = getTotalNumberOfVisitorsByArea(area);
            staffOut=getTotalNumberOfStaffOutByArea(area);
            stay=getStayDurationByArea(area);

        } else if (monthName != null) {
            mapResult = getTotalAttendByWeekMN(monthName);
            staffOut=getTotalNumberOfStaffOutByMonthName(monthName);
            stay=getStayDurationByMonthName(monthName);
        } else if (hourName != null) {
            mapResult = getTotalAttendByWeekHN(hourName);
            longResult = getTotalNumberOfVisitorsByHourName(hourName);
            staffOut=getTotalNumberOfStaffOutByHourName(hourName);
            stay=getStayDurationByHourName(hourName);


        } else if (month != null) {
            mapResult = getTotalAttendByWeekM(month);
            longResult = getTotalNumberOfVisitorsByMonth(month);
            staffOut=getTotalNumberOfStaffOutByMonth(month);
            stay=getStayDurationByMonth(month);

        } else if (year != null) {
            mapResult = getTotalAttendByWeekY(year);
            longResult = getTotalNumberOfVisitorsByYear(year);
            staffOut=getTotalNumberOfStaffOutByYear(year);
            stay=getStayDurationByYear(year);

        } else if (week != null) {
            mapResult = getTotalAttendByWeekW(week);
            longResult = getTotalNumberOfVisitorsByWeek(week);
            staffOut=getTotalNumberOfStaffOutByWeek(week);
            stay=getStayDurationByWeek(week);


        } else if (hour != null) {
            mapResult = getTotalAttendByWeekH(hour);
            longResult = getTotalNumberOfVisitorsByHour(hour);
            staffOut=getTotalNumberOfStaffOutByHour(hour);
            stay=getStayDurationByHour(hour);


        } else {
            Criteria filterCriteria = new Criteria();

            if (week != null) {
                filterCriteria.and("week").is(week);
            }
            if (month != null) {
                filterCriteria.and("month").is(month);
            }
            if (year != null) {
                filterCriteria.and("year").is(year);
            }
            if (hour != null) {
                filterCriteria.and("hour").is(hour);
            }
            Query dynamicQuery = new Query(filterCriteria);

            System.out.println("Generated Query: " + dynamicQuery.toString());

            List<Staffs> filteredData = mongoTemplate.find(dynamicQuery, Staffs.class);

            System.out.println("Filtered Data: " + filteredData);

            longResult = filteredData.size();
             staffOut= filteredData.size();
        }

        return new FilterResult(mapResult, stay,longResult,staffOut,Enter,Exit);
    }



    public long getTotalNumberOfVisitorsByYear(String targetYear) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByYear = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()) && targetYear.equals(staff.getYear()))
                .count();

        return totalVisitorByYear;
    }
    public long getTotalNumberOfVisitorsByMonth(String targetMonth) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByMonth = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()) && targetMonth.equals(staff.getMonth()))
                .count();

        return totalVisitorByMonth;
    }
    public long getTotalNumberOfVisitorsByWeek(String targetWeek) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByWeek = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()) && targetWeek.equals(staff.getWeek()))
                .count();

        return totalVisitorByWeek;
    }
    public long getTotalNumberOfVisitorsByHour(String targetHour) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByHour = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()) && targetHour.equals(staff.getHour()))
                .count();

        return totalVisitorByHour;
    }
    public long getTotalNumberOfVisitorsByFirstName(String targetFirstName) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByFirstName = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()) && targetFirstName.equals(staff.getFirstName()))
                .count();

        return totalVisitorByFirstName;
    }
    public long getTotalNumberOfVisitorsByArea(String targetArea) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByArea = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()) && targetArea.equals(staff.getArea()))
                .count();

        return totalVisitorByArea;
    }
    public long getTotalNumberOfVisitorsByHourName(String targetHourName) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByHourName = staffList.stream()
                .filter(staff -> "1".equals(staff.getVisitor()) && targetHourName.equals(staff.getHourName()))
                .count();

        return totalVisitorByHourName;
    }
    public long getTotalNumberOfStaffOutByYear(String year) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && year.equals(staff.getYear()))
                .count();

        return totalStaffOut;
    }

    public long getTotalNumberOfStaffOutByMonth(String month) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && month.equals(staff.getMonth()))
                .count();

        return totalStaffOut;
    }

    public long getTotalNumberOfStaffOutByWeek(String week) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && week.equals(staff.getWeek()))
                .count();

        return totalStaffOut;
    }

    public long getTotalNumberOfStaffOutByFirstName(String firstName) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && firstName.equals(staff.getFirstName()))
                .count();

        return totalStaffOut;
    }

    public long getTotalNumberOfStaffOutByHour(String hour) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && hour.equals(staff.getHour()))
                .count();

        return totalStaffOut;
    }

    public long getTotalNumberOfStaffOutByArea(String area) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && area.equals(staff.getArea()))
                .count();

        return totalStaffOut;
    }

    public long getTotalNumberOfStaffOutByHourName(String hourName) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && hourName.equals(staff.getHourName()))
                .count();

        return totalStaffOut;
    }

    public long getTotalNumberOfStaffOutByMonthName(String monthName) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && monthName.equals(staff.getMonthName()))
                .count();

        return totalStaffOut;
    }

    public Map<String, Double> getStayDurationByFirstName(String firstName) {
        List<Staffs> staffList = staffsRepository.findByFirstName(firstName);

        Map<String, Double> stayDurationByFirstName = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));

        return stayDurationByFirstName;
    }
    public Map<String, Double> getStayDurationByWeek(String week) {
        List<Staffs> staffList = staffsRepository.findByWeek(week);

        Map<String, Double> stayDurationByWeek = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));
        return stayDurationByWeek;
    }
    public Map<String, Double> getStayDurationByMonth(String month) {
        List<Staffs> staffList = staffsRepository.findByMonth(month);
        Map<String, Double> stayDurationByMonth = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));

        return stayDurationByMonth;
    }
    public Map<String, Double> getStayDurationByYear(String year) {
        List<Staffs> staffList = staffsRepository.findByYear(year);
        Map<String, Double> stayDurationByYear = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));

        return stayDurationByYear;
    }

    public Map<String, Double> getStayDurationByHour(String hour) {
        List<Staffs> staffList = staffsRepository.findByHour(hour);
        Map<String, Double> stayDurationByHour = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));

        return stayDurationByHour;
    }
    public Map<String, Double> getStayDurationByArea(String area) {
        List<Staffs> staffList = staffsRepository.findByArea(area);

        Map<String, Double> stayDurationByArea = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));

        return stayDurationByArea;
    }

    public Map<String, Double> getStayDurationByHourName(String hourName) {
        List<Staffs> staffList = staffsRepository.findByHourName(hourName);

        Map<String, Double> stayDurationByHourName = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));

        return stayDurationByHourName;
    }
    public Map<String, Double> getStayDurationByMonthName(String monthName) {
        List<Staffs> staffList = staffsRepository.findByHourName(monthName);

        Map<String, Double> stayDurationByMonthName = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty()) // Filter out null and empty FirstName values
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        })
                ));

        return stayDurationByMonthName;
    }
    public Map<String, Double> getStayDuration() {
        List<Staffs> staffList = staffsRepository.findAll();

        Map<String, Double> stayDuration = staffList.stream()
                .filter(staff -> staff.getFirstName() != null && !staff.getFirstName().isEmpty())
                .collect(Collectors.groupingBy(
                        Staffs::getFirstName,
                        Collectors.summingDouble(staff -> {
                            try {
                                return Double.parseDouble(staff.getStayDuration());
                            } catch (NumberFormatException e) {
                                return 0.0; // Handle invalid or empty stay duration as 0.0
                            }
                        })
                ));

        return stayDuration;
    }

    public String getEarliestArrivalTimeByFN(String firstName) {
        // Fetch records with timestamps for the specified firstName
        List<Staffs> staffsList = staffsRepository.findByFirstName(firstName);

        // Extract and format timestamps
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Optional<String> earliestArrivalTime = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getVisitor())) // Filter by visitor "1"
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter))
                .min(LocalDateTime::compareTo)
                .map(dateTime -> dateTime.format(outputFormatter));

        return earliestArrivalTime.orElse(null);
    }
    public String getEarliestArrivalTimeByWeek(String week) {
        List<Staffs> staffsList = staffsRepository.findAll();

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Optional<String> earliestArrivalTime = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getVisitor())) // Filter by visitor "1"
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter))
                .min(LocalDateTime::compareTo)
                .map(dateTime -> dateTime.format(outputFormatter));

        return earliestArrivalTime.orElse(null);}
    public String getEarliestArrivalTimeByMonth(String month) {
        // Fetch all records with timestamps
        List<Staffs> staffsList = staffsRepository.findAll();

        // Extract and format timestamps
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Optional<String> earliestArrivalTime = staffsList.stream()
                .filter(staff -> isValidTimestamp(staff.getTimestamp(), inputFormatter))
                .filter(staff -> "1".equals(staff.getVisitor())) // Filter by visitor "1"
                .map(staff -> LocalDateTime.parse(staff.getTimestamp(), inputFormatter))
                .min(LocalDateTime::compareTo)
                .map(dateTime -> dateTime.format(outputFormatter));

        return earliestArrivalTime.orElse(null);
    }



}

