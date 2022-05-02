import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        HashMap<String,String>accounts=new HashMap<>();
        accounts.put("Mohamed","Password");
        accounts.put("Ahmed","Password123");
        accounts.put("Ali","Password123");
        accounts.put("Mahmoud","Password123");

        final int PORT = 4040;
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Server started...");
        System.out.println("Wating for clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler handler=new ClientHandler(clientSocket);
            new Thread(handler).start();

        }
    }

}
