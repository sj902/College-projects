import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Enumeration;

/**
 * The class extends the Thread class so we can receive and send messages at the same time
 */
public class TCPServer extends Thread {

    public static final int SERVERPORT = 5747;
    private boolean running = false;
    private PrintWriter mOut;
    private OnMessageReceived messageListener;

    public static void main(String[] args) throws UnknownHostException {


        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
        System.out.println("Host Name:- " + inetAddress.getHostName());

        //opens the window where the messages will be received and sent
        ServerBoard frame = new ServerBoard();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Constructor of the class
     * @param messageListener listens for the messages
     */
    public TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }

    /**
     * Method to send the messages from server to client
     * @param message the message sent by the server
     */
    public void sendMessage(String message){
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            mOut.flush();
        }
    }

    @Override
    public void run() {
        super.run();

        running = true;

        try {
            System.out.println("S: Connecting...");




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




            //create a server socket. A server socket waits for requests to come in over the network.
            ServerSocket serverSocket = new ServerSocket(SERVERPORT);

            System.out.println("serversocket"+serverSocket);

            //create client socket... the method accept() listens for a connection to be made to this socket and accepts it.
            Socket client = serverSocket.accept();

            System.out.println("client"+client);



            System.out.println("S: Receiving...");

            try {

                //sends the message to the client
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

                //read the message received from client
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                //in this while we wait to receive messages from client (it's an infinite loop)
                //this while it's like a listener for messages
                while (running) {
                    String message = in.readLine();

                    if (message != null && messageListener != null) {
                        //call the method messageReceived from ServerBoard class
                        messageListener.messageReceived(message);
                    }
                }

            } catch (Exception e) {
                System.out.println("S: Error");
                e.printStackTrace();
            } finally {
                client.close();
                System.out.println("S: Done.");
            }

        } catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the ServerBoard
    //class at on startServer button click
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

}