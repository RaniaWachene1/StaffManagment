package com.example.myteam.service;
import com.example.myteam.entity.Staffs;
import com.example.myteam.repository.StaffsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class StaffsService {

    private StaffsRepository staffsRepository;

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

    public long getTotalNumberOfStaffOutByWeek(String targetWeek) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalStaffOut = staffList.stream()
                .filter(staff -> "1".equals(staff.getOut()) && targetWeek.equals(staff.getWeek()))
                .count();

        return totalStaffOut;
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
    public long getTotalNumberOfVByWeek(String targetWeek) {
        List<Staffs> staffList = staffsRepository.findAll();

        long totalVisitorByWeek = staffList.stream()
                .filter(staff -> targetWeek.equals(staff.getWeek()) && "1".equals(staff.getVisitor()))
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

















}

