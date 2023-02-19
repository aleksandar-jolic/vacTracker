package dev.jola.VacTracker.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "used_vacation_days")
public class UsedVacationDays {

    @Id
    private ObjectId id;
    private String employeeEmail;
    private String startDate;
    private String endDate;



}
