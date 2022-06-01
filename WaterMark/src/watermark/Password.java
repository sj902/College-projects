/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermark;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.qoppa.pdf.PDFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 *
 * @author dell
 */
public class Password {
    private static String USER_PASSWORD = "password";
private static String OWNER_PASSWORD = "naveen";
public static void main(String[] args) throws IOException, DocumentException, PDFException, GeneralSecurityException {

    /*Document document = new Document();
      try
      {

         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
         writer.setEncryption(USER_PASSWORD.getBytes(),OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING,PdfWriter.ENCRYPTION_AES_128);
         document.open();
         document.add(new Paragraph("This is Password Protected PDF document."));
         document.close();
         writer.close();
      } catch (DocumentException e)
      {
         e.printStackTrace();
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }*/
     PdfReader reader = new PdfReader("Test_WaterMark.pdf");
    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("Test_WaterMark_Password.pdf"));
    stamper.setEncryption(USER_PASSWORD.getBytes(),OWNER_PASSWORD.getBytes(),
        PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
    stamper.close();
    reader.close();
  

}
public void encryptPdf(String src, String dest) throws IOException, DocumentException {
    
}
}
