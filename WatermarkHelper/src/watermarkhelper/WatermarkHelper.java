/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarkhelper;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author shubhamjain
 */
public class WatermarkHelper {

    /**
     * @param args the command line arguments
     */

   public static void main(String[] args) throws IOException, DocumentException {
       File file = new File(args[1]);
       //file.getParentFile().mkdirs();
       new WatermarkHelper().manipulatePdf(args[0], args[1], args[2]);
   }

   public void manipulatePdf(String src, String dest, String phrase) throws IOException, DocumentException {
       PdfReader reader = new PdfReader(src);
       PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
       PdfContentByte under = stamper.getUnderContent(1);
       Font f = new Font(FontFamily.HELVETICA, 15);
       Phrase p;
       //Phrase p = new Phrase("This watermark is added UNDER the existing content", f);
       //ColumnText.showTextAligned(under, Element.ALIGN_CENTER, p, 297, 550, 0);
       PdfContentByte over = stamper.getOverContent(1);
       //p = new Phrase("This watermark is added ON TOP OF the existing content", f);
       //ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 500, 0);
       p = new Phrase(phrase, f);
       over.saveState();
       PdfGState gs1 = new PdfGState();
       gs1.setFillOpacity(0.5f);
       over.setGState(gs1);
       ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 450, 0);
       over.restoreState();
       stamper.close();
       reader.close();
   }
    
}
