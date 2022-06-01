/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwrodhelper;

import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class PasswrodHelper {

    private static PrivateKey privateKey;

    private static Certificate[] certificateChain;

    public static void main(String args[]) throws FileNotFoundException, IOException, Exception {

        String inputPath = args[0];
        String outputPath = args[1];
        String pinCode = args[2];
        String pkcs12Password = args[3];
        String pkcs12FileName = args[4];

        String pdfInputFileName = inputPath;
        String pdfOutputFileName = outputPath;

        KeyStore ks = null;
        
        ks = KeyStore.getInstance("pkcs12");
        ks.load(new FileInputStream(pkcs12FileName), pkcs12Password
                .toCharArray());

        String alias = "";
        alias = (String) ks.aliases().nextElement();
        privateKey = (PrivateKey) ks.getKey(alias, pkcs12Password
                .toCharArray());

        certificateChain = ks.getCertificateChain(alias);

        PdfReader reader = null;
        reader = new PdfReader(pdfInputFileName);
        FileOutputStream fout = null;
        fout = new FileOutputStream(pdfOutputFileName);

        PdfStamper stp = null;
        File f = new File("temp");
        stp = PdfStamper.createSignature(reader, fout, '\0', f, false);
        PdfSignatureAppearance sap = stp.getSignatureAppearance();
        sap.setCrypto(privateKey, certificateChain, null,
                PdfSignatureAppearance.WINCER_SIGNED);

        stp.setEncryption(true, pinCode, pinCode,
                PdfWriter.AllowModifyAnnotations);
        stp.close();
    }
}
