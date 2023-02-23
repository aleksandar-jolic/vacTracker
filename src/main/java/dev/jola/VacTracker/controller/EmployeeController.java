package dev.jola.VacTracker.controller;


import dev.jola.VacTracker.dto.EmployeeVacationDaysDto;
import dev.jola.VacTracker.dto.UsedVacationDaysDto;
import dev.jola.VacTracker.dto.VacationDaysPeriodDto;
import dev.jola.VacTracker.exception.EmployeeNotFoundException;
import dev.jola.VacTracker.service.UserService;
import dev.jola.VacTracker.service.UsedVacationDaysService;
import dev.jola.VacTracker.service.VacationPerYearService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class EmployeeController {

    private final UserService userService;
    private final UsedVacationDaysService usedVacationDaysService;

    public EmployeeController(UserService userService, UsedVacationDaysService usedVacationDaysService) {
        this.userService = userService;
        this.usedVacationDaysService = usedVacationDaysService;
    }

    @GetMapping("/vacation-days/{year}")
    public EmployeeVacationDaysDto   getEmployeeVacationDaysInfo(@PathVariable("year") String item)
                                                                 throws EmployeeNotFoundException {

         int year = Integer.parseInt(item);

        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        System.out.println(email);

        return  userService.getEmployeeVacationDaysInfo(email,year);



    }

    @GetMapping("/used-vacation-days-for-specified-period")
    public List<VacationDaysPeriodDto> getListOfUsedVacationDays(@RequestBody VacationDaysPeriodDto period)
                                                                 throws EmployeeNotFoundException {
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        period.setEmployeeEmail(email);

        return usedVacationDaysService.getListOfUsedVacationDays(email,period);


    }

    @PostMapping("/upload-new-record-of-used-vacation-days")
    public UsedVacationDaysDto uploadNewRecordOfUsedVacatioDays(@RequestBody UsedVacationDaysDto dto)
                                                                throws Exception{
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        return usedVacationDaysService.saveRecord(email,dto);

    }


}

