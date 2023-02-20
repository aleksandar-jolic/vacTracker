package dev.jola.VacTracker.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

    public static String TYPE= "text/csv";

    public  boolean isCSVFormat(MultipartFile file){

        if(!TYPE.equals(file.getContentType())){

            return false;
        }

        return true;
    }

}
