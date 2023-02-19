package dev.jola.VacTracker.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vacation_per_year")
public class VacationPerYear {

    @Id
    private ObjectId id;
    private String employeeEmail;
    private int year;
    private int totalVacationDays;

}