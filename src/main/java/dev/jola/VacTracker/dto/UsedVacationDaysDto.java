package dev.jola.VacTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedVacationDaysDto {


    private String employeeEmail;
    private String startDate;
    private String endDate;



}
