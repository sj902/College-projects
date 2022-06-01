package HelperClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.qoppa.pdf.PDFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class Helper {

	public static String function(String enrolmentNumber)
	{
		String path = "/Users/shubhamjain/eclipse-workspace/NSS-2/Data/";
        String pinCode = "";
        Path pathToFile = Paths.get(path+"Database.csv");
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (attributes[0].equalsIgnoreCase(enrolmentNumber)) {
                    pinCode = attributes[1];
                    return pinCode;
                }
                line = br.readLine();
            }
        }
catch (IOException ioe) {
        }
        

		
		

        return "";
		
	}
	
}
