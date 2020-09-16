
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ChatSystemClient {
    public static void main(String[] args) throws IOException {

        InetAddress addressOfTheServer = InetAddress.getByName("127.0.0.1");

        // Connecting to the server
        Socket client = new Socket(addressOfTheServer, 3000);


        // Getting the server input/output streams
        BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter outputStream = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

        // Getting the keyboard input stream
        BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));

        Thread messagePrinter = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        String messageFromServer = inputFromServer.readLine();
                        if(messageFromServer != null) {
                            System.out.println("Message from the server: " + messageFromServer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        // Start the message printer Thread
        messagePrinter.start();

        while(true) {

            // Reading message from the keyboard
            String line = keyboardInput.readLine();

            // Sending the message to the server
            outputStream.println(line);
            outputStream.flush();

        }

    }
}
