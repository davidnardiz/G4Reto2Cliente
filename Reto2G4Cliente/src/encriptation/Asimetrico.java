/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package encriptation;

/**
 *
 * @author bego
 */
import java.io.*;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class Asimetrico {

    public static String encriptar(String pass) {
        String p = null;
        try {
            // Load Private Key
            FileInputStream fis = new FileInputStream(System.getProperty("user.home") + File.separator + "Documents\\MarketMaker\\publicKey.der");
            byte[] publicKeyBytes = new byte[fis.available()];
            fis.read(publicKeyBytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // Encrypt and send data
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(pass.getBytes());
            System.out.println(encryptedData.toString());
            p = DatatypeConverter.printHexBinary(encryptedData);
            System.out.println(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }

}
