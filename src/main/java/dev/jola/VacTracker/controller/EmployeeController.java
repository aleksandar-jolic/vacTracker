package dev.jola.VacTracker.controller;


import dev.jola.VacTracker.dto.EmployeeVacationDaysDto;
import dev.jola.VacTracker.exception.EmployeeNotFoundException;
import dev.jola.VacTracker.service.EmployeeService;
import dev.jola.VacTracker.service.UsedVacationDaysService;
import dev.jola.VacTracker.service.VacationPerYearService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private UsedVacationDaysService usedVacationDaysService;
    private VacationPerYearService vacationPerYearService;

    public EmployeeController(EmployeeService employeeService, UsedVacationDaysService usedVacationDaysService, VacationPerYearService vacationPerYearService) {
        this.employeeService = employeeService;
        this.usedVacationDaysService = usedVacationDaysService;
        this.vacationPerYearService = vacationPerYearService;
    }

    @GetMapping("/{email}/{year}/vacation-days")
    public EmployeeVacationDaysDto   getEmployeeVacationDaysInfo(@PathVariable("email") String email, @PathVariable("year")int year)throws EmployeeNotFoundException {

        return  employeeService.getEmployeeVacationDaysInfo(email,year);


    }




}
