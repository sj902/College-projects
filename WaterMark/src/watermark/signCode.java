/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermark;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.SignatureAppearance;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfSecure.PDFSecure;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;

/**
 *
 * @author dell
 */
public class signCode {
    public static void main() throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, GeneralSecurityException, PDFException{
    // Load the document
PDFSecure pdfDoc=null;
        try {
            pdfDoc = new PDFSecure ("Test.pdf", null);
        } catch (PDFException ex) {
            Logger.getLogger(signCode.class.getName()).log(Level.SEVERE, null, ex);
        }
 System.out.println("yo");
// Load the keystore that contains the digital id to use in signing
// which can be loaded from a pfx file, a p12 file, etc... 
FileInputStream pkcs12Stream = new FileInputStream ("certificate.p12");
KeyStore store = KeyStore.getInstance("PKCS12");
store.load(pkcs12Stream, "pratham".toCharArray());
pkcs12Stream.close();
 
// Create signing information using the "Leila" alias
SigningInformation signInfo = new SigningInformation (store, "certificate", "pratham");
 
// Customize the signature appearance
SignatureAppearance signAppear = signInfo.getSignatureAppearance();
 
// Show an image instead of the signer's name on the left side of the signature field
signAppear.setVisibleName(false);
signAppear.setImagePosition(SwingConstants.LEFT);
signAppear.setImageFile("reported2.png");
 
// Only show the signer's name and date on the right side of the signature field
signAppear.setVisibleCommonName(false);
signAppear.setVisibleOrgUnit(false);
signAppear.setVisibleOrgName(false);
signAppear.setVisibleLocal(false);
signAppear.setVisibleState(false);
signAppear.setVisibleCountry(false);
signAppear.setVisibleEmail(false);
 
// Create signature field on the first page
Rectangle2D signBounds = new Rectangle2D.Double (36, 36, 144, 48);
SignatureField signField=null;
if(pdfDoc!=null){

    signField = pdfDoc.addSignatureField(0, "signature", signBounds);
}
// Apply digital signature
if(signField!=null){
pdfDoc.signDocument(signField, signInfo);
}
// Save the document
pdfDoc.saveDocument ("output.pdf");
    
    }
}
