import javax.crypto.*;
import java.net.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    static String ID = "KDC";
    public static ConcurrentHashMap<String, SecretKey> ClientKeyPairs = new ConcurrentHashMap<String, SecretKey>();

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ServerSocket server;
        Socket connection;
        try {

            InetAddress addr = InetAddress.getByName("127.0.0.1");

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    addr = addresses.nextElement();
                    final  String ip = addr.getHostAddress();
                    System.out.println(""+ip);
                }
            }

            server = new ServerSocket( 6789, 10, addr);
            while (true) {
                //Start
                connection = server.accept();

                //Create Streams
                ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
                output.flush();

                ObjectInputStream input = new ObjectInputStream(connection.getInputStream());

                Thread t = new ClientHandler(connection, input, output);
                t.start();

            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    ObjectOutputStream output;
    ObjectInputStream input;
    Socket connection;

    public ClientHandler(Socket connection, ObjectInputStream input, ObjectOutputStream output) {
        this.connection = connection;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        super.run();
        try {
            String message = (String) input.readObject();
            if (message.startsWith("Initialize")) {
                initialise(message, connection, output);
            } else if (message.startsWith("Request")) {
                CreateKey(message, connection, output);
            }

            //Clean up
            output.close();
            input.close();
            connection.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initialise(String message, Socket connection, ObjectOutputStream output) {
        try {
            String[] messages = message.split("break");
            //messages[0] -- Initialize
            //messages[1] -- ID
            SecretKey key =
                    KeyGenerator.getInstance("DES").generateKey();
            output.writeObject(key);
            Main.ClientKeyPairs.put(messages[1], key);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void CreateKey(String message, Socket connection, ObjectOutputStream output) {
        SecretKey key;
        SecretKey otherKey;
        String[] messages = message.split("%%");
        //messages[0] -- Request
        //messages[1] -- IDA
        //messages[2] -- IDB
        //messages[3] -- nuance
        if (Main.ClientKeyPairs.containsKey(messages[1]))
            key = Main.ClientKeyPairs.get(messages[1]);
        else {
            System.out.println("Not Initialized");
            return;
        }
        if(Main.ClientKeyPairs.containsKey(messages[2]))
        {
            otherKey = Main.ClientKeyPairs.get(messages[2]);
        }
        else {
            System.out.println("Other Client Not Initialized");
            return;
        }
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, otherKey);
            SecretKey sessionKey =
                    KeyGenerator.getInstance("DES").generateKey();
            String dataForOtherClient = Base64.getEncoder().encodeToString(sessionKey.getEncoded())
                    + "%%" + messages[1];
            byte[] midBytes = cipher.doFinal(dataForOtherClient.getBytes());

            String EncodedMessage = Base64.getEncoder().encodeToString(sessionKey.getEncoded())
                    + "%%" + messages[1] + "%%" + messages[2] + "%%" + messages[3]+"%%"+java.util.Arrays.toString(midBytes);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptionBytes = cipher.doFinal(EncodedMessage.getBytes());

            String encryptedData = "KDC%%"+java.util.Arrays.toString(encryptionBytes);

            output.writeObject(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IOException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }
}