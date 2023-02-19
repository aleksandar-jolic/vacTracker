package dev.jola.VacTracker.service;

import dev.jola.VacTracker.entity.Employee;
import dev.jola.VacTracker.helper.EmployeeHelper;
import dev.jola.VacTracker.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void saveFromFile(MultipartFile file) {

        try{



            List<Employee> employees = EmployeeHelper.csvToEmployees(file.getInputStream());

            employeeRepository.saveAll(employees);

        }catch (Exception e){

            throw new RuntimeException("Fail to store csv data: " + e.getMessage());

        }


    }



}
