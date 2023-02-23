package dev.jola.VacTracker.service;

import dev.jola.VacTracker.dto.EmployeeVacationDaysDto;
import dev.jola.VacTracker.entity.UsedVacationDays;
import dev.jola.VacTracker.entity.User;
import dev.jola.VacTracker.entity.VacationPerYear;
import dev.jola.VacTracker.exception.EmployeeNotFoundException;
import dev.jola.VacTracker.helper.UserHelper;
import dev.jola.VacTracker.repository.UserRepository;
import dev.jola.VacTracker.repository.UsedVacationDaysRepository;
import dev.jola.VacTracker.repository.VacationPerYearRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {


    private final UserRepository userRepository;
    private final UsedVacationDaysRepository usedVacationDaysRepository;
    private final VacationPerYearRepository vacationPerYearRepository;
    private  final UserHelper userHelper;

    public UserService(UserRepository userRepository,
                       UsedVacationDaysRepository usedVacationDaysRepository,
                       VacationPerYearRepository vacationPerYearRepository,
                       UserHelper userHelper) {
        this.userRepository = userRepository;
        this.usedVacationDaysRepository = usedVacationDaysRepository;
        this.vacationPerYearRepository = vacationPerYearRepository;
        this.userHelper = userHelper;
    }

    public void saveFromFile(MultipartFile file) {

        try {


            List<User> employees = userHelper.csvToEmployees(file.getInputStream());

            userRepository.saveAll(employees);

        } catch (Exception e) {

            throw new RuntimeException("Fail to store csv data: " + e.getMessage());

        }


    }


    public EmployeeVacationDaysDto getEmployeeVacationDaysInfo(String email, int year) throws EmployeeNotFoundException{



            if(!userRepository.existsByEmail(email)){

                    throw new EmployeeNotFoundException("Employee record does not exist.");

            }

        EmployeeVacationDaysDto item = new EmployeeVacationDaysDto();

        List<UsedVacationDays> listOfUsedVacationDays = usedVacationDaysRepository.findUsedVacationDaysByEmployeeEmail(email);

        Optional<VacationPerYear> vacationPerYear = vacationPerYearRepository.findVacationPerYearByEmployeeEmailAndYear(email, year);

        int totalVacationDays = 0;



       if(vacationPerYear.isPresent()){

           totalVacationDays =  vacationPerYear.get().getTotalVacationDays();
       }


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
