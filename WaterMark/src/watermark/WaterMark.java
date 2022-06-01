/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermark;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.IOException;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 *
 * @author dell
 */
public class WaterMark {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, DocumentException {
      /*  OutputStream file = new FileOutputStream(new File("C:\\Users\\dell\\Documents\\NetBeansProjects\\WaterMark\\Test.pdf"));

			Document document = new Document();
                        document.open();
			document.add(new Paragraph("Hello World, iText"));
			document.add(new Paragraph(new Date().toString()));

			document.close();
			file.close();*/
      /*  PdfReader reader = new PdfReader("Test.pdf");
        int n=reader.getNumberOfPages();
        reader.close();
PdfStamper pdfStamper = new PdfStamper(reader,
    new FileOutputStream("Test.pdf"));
Image image = Image.getInstance("reported2.png");

for(int i=1; i<= n; i++){
    PdfContentByte content = pdfStamper.getUnderContent(i);
    image.setAbsolutePosition(150f, 750f);
    content.addImage(image);
}

pdfStamper.close();*/
      Process p = Runtime.getRuntime().exec("java -jar jPdfSign.jar certificate.p12 Test.pdf fromJava.pdf");

         // get the output stream
         OutputStream out = p.getOutputStream();
         out.write("pratham".getBytes());
         // close the output stream
         System.out.println("Closing the output stream...");
         out.close();

    }
    
}
