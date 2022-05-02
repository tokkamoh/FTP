import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
   static Scanner in;
  static boolean sent=false;
  static int i=4041;
    public static void main(String[] args) throws IOException {
        final String HOST = "127.0.0.1";
        final int PORT = 4040;
        String message;
        System.out.println("Client started.");

                Socket socket = new Socket(HOST, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                in = new Scanner(socket.getInputStream());
                Scanner s = new Scanner(System.in);
        {
            while (true) {



                message=in.nextLine();
                System.out.println("Echoed from server: " +message);
                if(message.equals("Login Successfully"))
                {

                    System.out.println("Echoed from server: " +in.nextLine());


                }

                System.out.print("Input: ");
                String input = s.nextLine();
                out.println(input);
                if(sent)
                {
                    System.out.println("Echoed from server: " +in.nextLine());
                    sent=false;
                }
                if (input.equalsIgnoreCase("QUIT")) {
                    out.print("QUIT");
                    break;
                };
                if(message.equals("Login Successfully"))
                {

                    System.out.println("Echoed from server: " +in.nextLine());


                }
                if(message.contains("available"))
                {
                    System.out.println("Echoed from server: " +in.nextLine());
                    System.out.println("here");

                }
                if(message.contains("desired directory"))
                {
                    String response=in.nextLine();
                    System.out.println("Echoed from server: " +response);
                }
                if(message.contains("Please choose the desired file to download"))
                {

                    String Filename=in.nextLine();
                    System.out.println(Filename);
                    byte[] contents = new byte[10000];
                    //Initialize the FileOutputStream to the output file's full path.
                    FileOutputStream fos = new FileOutputStream("C:\\Users\\moham\\Desktop\\FTP\\Download\\"+Filename);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    Socket Filesocket = new Socket(HOST, i);

                    InputStream is = Filesocket.getInputStream();
                    //No of bytes read in one read() call
                    int bytesRead = 0;
                    while((bytesRead=is.read(contents))!=-1)
                        bos.write(contents, 0, bytesRead);
                    bos.flush();
                    bos.close();
                    fos.close();
                    is.close();
                    System.out.println("File saved successfully!");
                    sent=true;
                    i++;
                }
            }
        }
    }

}
