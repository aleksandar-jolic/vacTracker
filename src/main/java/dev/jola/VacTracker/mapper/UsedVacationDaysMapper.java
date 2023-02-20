package dev.jola.VacTracker.mapper;

import dev.jola.VacTracker.dto.UsedVacationDaysDto;
import dev.jola.VacTracker.entity.UsedVacationDays;
import org.springframework.stereotype.Component;

@Component
public class UsedVacationDaysMapper {

    public UsedVacationDaysDto toDto(UsedVacationDays usedVacationDays){

        UsedVacationDaysDto item = new UsedVacationDaysDto();

        item.setEmployeeEmail(usedVacationDays.getEmployeeEmail());
        item.setStartDate(usedVacationDays.getStartDate());
        item.setEndDate(usedVacationDays.getEndDate());

        return item;
    }
    public UsedVacationDays toEntity(UsedVacationDaysDto vacationDaysDto){

        UsedVacationDays item = new UsedVacationDays();

        item.setEmployeeEmail(vacationDaysDto.getEmployeeEmail());
        item.setStartDate(vacationDaysDto.getStartDate());
        item.setEndDate(vacationDaysDto.getEndDate());

        return item;
    }

}
