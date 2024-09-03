package com.xbstar.esl.util;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException; 

public class ReadXml{


    public static String readXMLFile(String filePath) {
        StringBuilder xmlContent = new StringBuilder(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {  
            String line;
            while ((line = br.readLine()) != null) {     
                xmlContent.append(line);               
                xmlContent.append("\n");                 
            }
        } catch (IOException e) {     
            e.printStackTrace();      
        }
        return xmlContent.toString(); 
    }

}

