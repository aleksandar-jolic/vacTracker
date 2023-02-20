package dev.jola.VacTracker.service;

import dev.jola.VacTracker.dto.EmployeeVacationDaysDto;
import dev.jola.VacTracker.entity.Employee;
import dev.jola.VacTracker.entity.UsedVacationDays;
import dev.jola.VacTracker.exception.EmployeeNotFoundException;
import dev.jola.VacTracker.helper.EmployeeHelper;
import dev.jola.VacTracker.repository.EmployeeRepository;
import dev.jola.VacTracker.repository.UsedVacationDaysRepository;
import dev.jola.VacTracker.repository.VacationPerYearRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final UsedVacationDaysRepository usedVacationDaysRepository;
    private final VacationPerYearRepository vacationPerYearRepository;
    private  final EmployeeHelper employeeHelper;

    public EmployeeService(EmployeeRepository employeeRepository,
                           UsedVacationDaysRepository usedVacationDaysRepository,
                           VacationPerYearRepository vacationPerYearRepository,
                           EmployeeHelper employeeHelper) {
        this.employeeRepository = employeeRepository;
        this.usedVacationDaysRepository = usedVacationDaysRepository;
        this.vacationPerYearRepository = vacationPerYearRepository;
        this.employeeHelper = employeeHelper;
    }

    public void saveFromFile(MultipartFile file) {

        try {


            List<Employee> employees = employeeHelper.csvToEmployees(file.getInputStream());

            employeeRepository.saveAll(employees);

        } catch (Exception e) {

            throw new RuntimeException("Fail to store csv data: " + e.getMessage());

        }


    }


    public EmployeeVacationDaysDto getEmployeeVacationDaysInfo(String email, int year) throws EmployeeNotFoundException{


            if(!employeeRepository.existsByEmail(email)){

                    throw new EmployeeNotFoundException("Employee record does not exist.");

            }

        EmployeeVacationDaysDto item = new EmployeeVacationDaysDto();

        List<UsedVacationDays> listOfUsedVacationDays = usedVacationDaysRepository.findUsedVacationDaysByEmployeeEmail(email);

        int totalVacationDays = vacationPerYearRepository.findVacationPerYearByEmployeeEmailAndYear(email, year).getTotalVacationDays();

        int usedDays = 0;
        String startDate = "";
        String endDate = "";

        DateFormat formatter = new SimpleDateFormat("EEE, MMMM dd, yyyy");
        Date start = null;
        Date end = null;

        for (UsedVacationDays record : listOfUsedVacationDays) {

            startDate = record.getStartDate();
            endDate = record.getEndDate();

            try {

                start = formatter.parse(startDate);
                end = formatter.parse(endDate);


                while (start.getYear() + 1900 == year && !start.after(end)) {

                    usedDays++;

                    System.out.println(usedDays);

                    Calendar c = Calendar.getInstance();
                    c.setTime(start);
                    c.add(Calendar.DATE, 1);
                    start = c.getTime();

                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        item.setEmployeeEmail(email);
        item.setTotalVacationDays(totalVacationDays);
        item.setYear(year);
        item.setUsedVacationDays(usedDays);
        item.setAvailableVacationDays(totalVacationDays - usedDays);

        return item;


    }
}
