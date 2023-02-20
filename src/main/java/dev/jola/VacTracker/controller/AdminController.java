package dev.jola.VacTracker.controller;


import dev.jola.VacTracker.helper.FileValidator;
import dev.jola.VacTracker.service.EmployeeService;
import dev.jola.VacTracker.service.UsedVacationDaysService;
import dev.jola.VacTracker.service.VacationPerYearService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    private final EmployeeService employeeService;

    private final UsedVacationDaysService usedVacationDaysService;

    private final VacationPerYearService vacationPerYearService;
    private final FileValidator fileValidator;
    public AdminController(EmployeeService employeeService,
                           UsedVacationDaysService usedVacationDaysService,
                           VacationPerYearService vacationPerYearService,
                           FileValidator fileValidator) {
        this.employeeService = employeeService;
        this.usedVacationDaysService = usedVacationDaysService;
        this.vacationPerYearService = vacationPerYearService;
        this.fileValidator = fileValidator;
    }

    @PostMapping("/employees/upload")
    public ResponseEntity<String> importEmployeeProfiles(@RequestParam("file") MultipartFile file){

        String message ="";

        if(fileValidator.isCSVFormat(file)){

            try{

                employeeService.saveFromFile(file);

                message="Uploaded the file successfully: " +file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);


            }catch (Exception e){

                message="Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);


            }


        }
        message="Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);


    }




    @PostMapping("/usedVacationDays/upload")
    public ResponseEntity<String> importUsedVacationDays(@RequestParam("file") MultipartFile file){

        String message ="";

        if(fileValidator.isCSVFormat(file)){

            try{
                usedVacationDaysService.saveFromFile(file);

                message="Uploaded the file successfully: " +file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);


            }catch (Exception e){

                message="Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);


            }


        }

        message="Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);





    }
    @PostMapping("/vacationPerYear/upload")
    public ResponseEntity<String> importVacationPerYear(@RequestParam("file") MultipartFile file){

        String message ="";

        if(fileValidator.isCSVFormat(file)){

            try{
                vacationPerYearService.saveFromFile(file);

                message="Uploaded the file successfully: " +file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);


            }catch (Exception e){

                message="Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);


            }


        }

        message="Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

    }





















}
