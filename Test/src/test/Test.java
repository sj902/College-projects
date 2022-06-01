package test;

import javax.crypto.*;

import java.net.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Test {


    public static String ID;
    static ConcurrentHashMap<String, SecretKey> ClientKeyPairs = new ConcurrentHashMap<String, SecretKey>();

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ObjectOutputStream output;
        ObjectInputStream input;
        String KDCIp = "127.0.0.1"; //args[0]
        Socket connection;
        ServerSocket server;
        //initialize
        try {
            server = new ServerSocket(54121);
            ID =  server.getInetAddress()+"::"+server.getLocalPort();
            System.out.println("ID"+ID);
            connection = new Socket(InetAddress.getByName(KDCIp), 6789);//KDS port no
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            output.writeObject("Initializebreak"+ID);
            output.flush();
            SecretKey key = (SecretKey) input.readObject();
            ClientKeyPairs.put("KDC", key);
            output.close();
            input.close();
            connection.close();
        } catch (IOException io) {
            io.printStackTrace();
        }catch (ClassNotFoundException cfe){
            cfe.printStackTrace();
        }




        try {
            connection = new Socket(InetAddress.getByName(KDCIp), 6789);//KDS port no
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            Random randomGenerator = new Random();
            int nuance = randomGenerator.nextInt(100000);
            output.writeObject("Request%%"+ID+"%%"+"0.0.0.0/0.0.0.0::52862"+"%%"+nuance);
            output.flush();
            String encryptionBytes = (String)input.readObject();
            
            System.out.println("input::::"+encryptionBytes); 
            
            SecretKey key;
            if(ClientKeyPairs.containsKey("KDC"))
                key = ClientKeyPairs.get("KDC");
            else {
                System.out.println("Not Initialized");
                return;
            }
            
            String[] results = encryptionBytes.split("%%",2);
            
            String[] strings = results[1].replace("[", "").replace("]", "").split(", ");
            byte result[] = new byte[strings.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = Byte.parseByte(strings[i]);
            }
            
            for(byte b:result)
            {
                System.out.print(" "+b); 
            }
            
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] recoveredBytes =
                    cipher.doFinal(result);
            String recovered =
                    new String(recoveredBytes);
            System.out.println("recovered"+recovered);

            //String[] results  = recovered.split("%%%%");
            strings = results[1].replace("[", "").replace("]", "").split(", ");
            result = new byte[strings.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = Byte.parseByte(strings[i]);
            }

            byte[] recoveredBytes2 =
                    cipher.doFinal(result);
            String recovered2 =
                    new String(recoveredBytes2);


            System.out.println(recovered2);
            output.close();
            input.close();
            connection.close();
        } catch (IOException io) {
            io.printStackTrace();
        }catch (ClassNotFoundException cfe){
            cfe.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
