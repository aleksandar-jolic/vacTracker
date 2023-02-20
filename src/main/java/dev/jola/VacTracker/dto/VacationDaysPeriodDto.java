package dev.jola.VacTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationDaysPeriodDto {

    String employeeEmail;
    String startDate;
    String endDate;


}
