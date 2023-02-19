package dev.jola.VacTracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employee")
public class Employee {



        @Id
        private ObjectId id;
        private String email;
        private String password;
        @DocumentReference
        private List<VacationPerYear> vacationPerYearIds;
        @DocumentReference
        private List<UsedVacationDays> usedVacationDaysIds;



    }
