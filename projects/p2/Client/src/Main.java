import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.net.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {


    public static String ID;
    static ConcurrentHashMap<String, SecretKey> ClientKeyPairs = new ConcurrentHashMap<String, SecretKey>();
    static ConcurrentHashMap<String, Integer> stateMap = new ConcurrentHashMap<String, Integer>();
    static ConcurrentHashMap<String, SecretKey> tempKey = new ConcurrentHashMap<String, SecretKey>();
    static ConcurrentHashMap<String, Integer> nuanceMap = new ConcurrentHashMap<String, Integer>();

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ObjectOutputStream output;
        ObjectInputStream input;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter KDC IP");
        String KDCIp = sc.next();
        Socket connection;
        ServerSocket server;
        //initialize
        try {
            server = new ServerSocket(0);

            String ip = "127.0.0.1";
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    //System.out.println(""+ip);
                }
            }

            ID = ip + "::" + server.getLocalPort();


            System.out.println("Started with ID:" + ID);
            connection = new Socket(InetAddress.getByName(KDCIp), 6789);//KDS port no
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            output.writeObject("Initializebreak" + ID);
            output.flush();
            SecretKey key = (SecretKey) input.readObject();
            ClientKeyPairs.put("KDC", key);
            output.close();
            input.close();
            connection.close();
            Thread t = new Communicate(server, ID);
            t.start();
        } catch (IOException | ClassNotFoundException io) {
            io.printStackTrace();
        }


        int choice;

        do {
            choice = 1;
            try {
                System.out.println("2. Connect to a machine.");
                System.out.println("3. Communicate with a machine.");
                System.out.println("4. Exit");
                Scanner scan = new Scanner(System.in);
                choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        Set<String> machines = ClientKeyPairs.keySet();
                        System.out.println("Connected machines:");
                        for (String s : machines) {
                            System.out.println("" + s + " : " + Base64.getEncoder().encodeToString(ClientKeyPairs.get(s).getEncoded()));
                        }
                        break;
                    case 2:
                        System.out.println("Enter IP Address:");
                        String ipadd;
                        ipadd = scan.next();
                        System.out.println("Enter port:");
                        int port;
                        port = scan.nextInt();
                        InetAddress addr = InetAddress.getByName(ipadd);
                        System.out.println("Enter ID:");
                        String id;
                        id = scan.next();
                        if (ClientKeyPairs.containsKey(id)) {
                            System.out.println("Already connected! Really wanna continue?(y/n)");
                            String re = scan.next();
                            if (re.equalsIgnoreCase("y")) {
                                ClientKeyPairs.remove(id);
                                Thread r = new Request(ipadd, port, ID, id, KDCIp);
                                r.start();
                            } else break;
                        } else {
                            Thread r = new Request(ipadd, port, ID, id, KDCIp);
                            r.start();
                        }
                        break;
                    case 3:
                        System.out.println("Enter IP Address:");
                        ipadd = scan.next();
                        System.out.println("Enter port:");
                        port = scan.nextInt();
                        addr = InetAddress.getByName(ipadd);
                        System.out.println("Enter your ID:");
                        String myID = scan.next();
                        System.out.println("Enter ID:");
                        id = scan.next();
                        System.out.println("Enter key:");
                        String inputKey = scan.next();
                        byte[] decodedKey = Base64.getDecoder().decode(inputKey);
                        //rebuild key using SecretKeySpec
                        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
                        System.out.println("Enter message:");
                        String msg = scan.next();
                        Cipher cipher = Cipher.getInstance("DES");
                        cipher.init(Cipher.ENCRYPT_MODE, key);
                        byte[] midBytes = cipher.doFinal(msg.getBytes());

                        String EncodedMessage = myID + "%%" + java.util.Arrays.toString(midBytes);

                        Socket conn = new Socket(InetAddress.getByName(ipadd), port);//KDS port no
                        ObjectOutputStream output2 = new ObjectOutputStream(conn.getOutputStream());
                        output2.flush();
                        output2.writeObject(EncodedMessage);
                        output2.close();
                        conn.close();
                        //} else System.out.println("Not connected");
                }

            } catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
                continue;
            }
        } while (choice != 4);

    }
}


class Communicate extends Thread {
    ServerSocket server;
    Socket connection;
    String ID;

    Communicate(ServerSocket server, String ID) {
        this.server = server;
        this.ID = ID;
    }

    public void run() {
        super.run();

        while (true) {
            try {
                //Start
                connection = server.accept();
                //Create Streams
                ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
                String incomingData = (String) input.readObject();


                String[] inarr = incomingData.split("%%", 2);

                String[] strings = inarr[1].replace("[", "").replace("]", "").split(", ");

                byte result[] = new byte[strings.length];
                for (int i = 0; i < result.length; i++) {
                    result[i] = Byte.parseByte(strings[i]);
                }

                //Fetch Key
                SecretKey key;


                if (inarr[0].equalsIgnoreCase("KDC")) {
                    key = Main.ClientKeyPairs.get("KDC");
                    //Decrypt -
                    Cipher cipher = Cipher.getInstance("DES");
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    byte[] recoveredBytes =
                            cipher.doFinal(result);
                    String[] recovered =
                            new String(recoveredBytes).split("%%");//Expected -- KS%%IDA
                    byte[] decodedKey = Base64.getDecoder().decode(recovered[0]);
                    // rebuild key using SecretKeySpec
                    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");

                    Random randomGenerator = new Random();
                    int nuance = randomGenerator.nextInt(100000);

                    cipher.init(Cipher.ENCRYPT_MODE, originalKey);
                    byte[] midBytes = cipher.doFinal(new String(nuance + "").getBytes());
                    String encryptedData = ID + "%%" + java.util.Arrays.toString(midBytes);

                    //send data
                    output.writeObject(encryptedData);

                    System.out.println("Nuance sent to :" + recovered[1]);

                    //update maps
                    Main.nuanceMap.put(recovered[1], nuance);
                    Main.tempKey.put(recovered[1], originalKey);
                    Main.stateMap.put(recovered[1], 4);

                    if (Main.ClientKeyPairs.containsKey(recovered[1]))
                        Main.ClientKeyPairs.remove(recovered[1]);

                    String finalStep = (String) input.readObject();
                    inarr = finalStep.split("%%", 2);

                    strings = inarr[1].replace("[", "").replace("]", "").split(", ");

                    result = new byte[strings.length];
                    for (int i = 0; i < result.length; i++) {
                        result[i] = Byte.parseByte(strings[i]);
                    }

                    key = Main.tempKey.get(recovered[1]);
                    //Decrypt -
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    recoveredBytes =
                            cipher.doFinal(result);
                    String re =
                            new String(recoveredBytes);//Expected -- nuance
                    if (nuance == Integer.parseInt(re)) {
                        System.out.println("Connected to" + recovered[1]);

                    }
                    Main.ClientKeyPairs.put(inarr[0], key);
                    Main.nuanceMap.remove(inarr[0]);
                    Main.tempKey.remove(inarr[0]);
                    Main.stateMap.remove(inarr[0]);


                } else if (Main.ClientKeyPairs.containsKey(inarr[0])) {
                    key = Main.ClientKeyPairs.get(inarr[0]);
                    //Decrypt -
                    Cipher cipher = Cipher.getInstance("DES");
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    byte[] recoveredBytes =
                            cipher.doFinal(result);
                    String recovered =
                            new String(recoveredBytes);
                    System.out.println("We got: " + recovered + "from:" + inarr[0]);
                } else {
                    System.out.println("Unidentified");
                }
            } catch (IOException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException | ClassNotFoundException io) {
                io.printStackTrace();
                continue;
            }

        }
    }
}


class Request extends Thread {
    String IPAddress;
    String IDA;
    String IDB;
    int port;
    String KDCIP;

    public Request(String ipadd, int port, String ida, String idb, String KDCIP) {
        IPAddress = ipadd;
        IDA = ida;
        IDB = idb;
        this.port = port;
        this.KDCIP = KDCIP;
    }

    public void run() {
        try {
            Socket connection = new Socket(InetAddress.getByName(KDCIP), 6789);//KDS port no
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            Random randomGenerator = new Random();
            int nuance = randomGenerator.nextInt(100000);
            output.writeObject("Request%%" + IDA + "%%" + IDB + "%%" + nuance);
            output.flush();

            Main.stateMap.put(IDB, 1);

            System.out.println("Request sent to KDC");

            //Fetch input 1 from KDC
            String incomingData = (String) input.readObject();

            System.out.println("Session Key recieved");

            String[] inarr = incomingData.split("%%", 2);

            String[] strings = inarr[1].replace("[", "").replace("]", "").split(", ");


            byte result[] = new byte[strings.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = Byte.parseByte(strings[i]);
            }

            //Fetch Key
            SecretKey key;
            if (Main.ClientKeyPairs.containsKey("KDC"))
                key = Main.ClientKeyPairs.get("KDC");
            else {
                System.out.println("Not Initialized");
                return;
            }


            //Decrypt -
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] recoveredBytes =
                    cipher.doFinal(result);
            String[] recovered =
                    new String(recoveredBytes).split("%%");

            //recovered[0] -- Key
            //recovered[1] -- IDA
            //recovered[2] -- IDB
            //recovered[3] -- nuance
            //recovered[4] -- Sent to B


            byte[] decodedKey = Base64.getDecoder().decode(recovered[0]);
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");


            if (Integer.parseInt(recovered[3]) == nuance) {
                Main.stateMap.put(IDB, 2);
                Main.tempKey.put(IDB, originalKey);
            }

            //Send append KDC%% and resultB to B
            Socket connection2 = new Socket(InetAddress.getByName(IPAddress), port);
            ObjectOutputStream output2 = new ObjectOutputStream(connection2.getOutputStream());
            output2.flush();
            output2.writeObject("KDC%%" + recovered[4]);


            Main.stateMap.put(IDB, 3);

            System.out.println("Session key sent");

            //recieve nuance from B
            ObjectInputStream input2 = new ObjectInputStream(connection2.getInputStream());

            String state4data = (String) input2.readObject();
            System.out.println("Nuance recieved");

            String[] inarr2 = state4data.split("%%", 2);

            String[] strings2 = inarr2[1].replace("[", "").replace("]", "").split(", ");

            byte result2[] = new byte[strings2.length];
            for (int i = 0; i < result2.length; i++) {
                result2[i] = Byte.parseByte(strings2[i]);
            }

            //Fetch Key
            SecretKey key2;

            key2 = Main.tempKey.get(IDB);

            //Decrypt -
            cipher.init(Cipher.DECRYPT_MODE, key2);
            byte[] recoveredBytes2 =
                    cipher.doFinal(result2);
            String recovered2 =
                    new String(recoveredBytes2);

            int nuanceB = Integer.parseInt(recovered2);
            Main.stateMap.put(IDB, 4);


            cipher.init(Cipher.ENCRYPT_MODE, key2);

            byte[] encryptionBytes = cipher.doFinal(recovered2.getBytes());
            String encryptedData = IDA + "%%" + java.util.Arrays.toString(encryptionBytes);
            output2.writeObject(encryptedData);

            System.out.println("Connected");

            Main.stateMap.remove(IDB);
            Main.ClientKeyPairs.put(IDB, Main.tempKey.get(IDB));
            Main.tempKey.remove(IDB);
            input2.close();
            output2.close();
            connection2.close();
            //CLean up
            output.close();
            input.close();
            connection.close();
        } catch (IOException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | ClassNotFoundException io) {
            io.printStackTrace();
        }
    }
}