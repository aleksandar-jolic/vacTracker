package dev.jola.VacTracker.helper;

import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    public static String TYPE= "text/csv";

    public static boolean isCSVFormat(MultipartFile file){

        if(!TYPE.equals(file.getContentType())){

            return false;
        }

        return true;
    }

}
