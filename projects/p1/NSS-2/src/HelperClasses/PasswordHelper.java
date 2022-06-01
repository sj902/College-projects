package HelperClasses;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.qoppa.pdf.PDFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class PasswordHelper {
	
public static void createPDF (String inputPath, String outputPath, String pinCode) 
{
	
	try {
PdfReader
	reader = new PdfReader(inputPath);

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputPath));
        String USER_PASSWORD = pinCode;
        String OWNER_PASSWORD = pinCode;
        stamper.setEncryption(USER_PASSWORD.getBytes(),OWNER_PASSWORD.getBytes(),
            PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
        stamper.close();
        reader.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
	
} 
}
