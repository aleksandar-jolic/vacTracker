package dev.jola.VacTracker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVacationDaysDto {


        private String employeeEmail;
        private int year;
        private int totalVacationDays;
        private int usedVacationDays;
        private int availableVacationDays;




}
