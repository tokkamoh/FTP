import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    int i=4041;
    String username;
    boolean LoggedIn=false;
    String directory="";
    PrintWriter out;
    Scanner in;
    FileSystem fileHandler=new FileSystem();
    ServerSocket serverSocket=null;
    Socket fileSocket=null;
    ClientHandler(Socket client)
    {
        this.clientSocket=client;
    }
    @Override
    public void run() {

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
             in = new Scanner(clientSocket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
       {
            while (true) {
                String input="";

                if(!LoggedIn)
                {
                 out.println("Please choose ‘LOGIN’ or ‘QUIT’");
                 input = in.nextLine();
                }
                if(input.equalsIgnoreCase("LOGIN"))
                {
                    System.out.println(input);
                    out.println("Please enter your username");
                    System.out.println("Here");

                }
                if (input.equalsIgnoreCase("QUIT")) {
                    break;
                }
                while(!LoggedIn)
                {
                    input = in.nextLine();
                System.out.println("Received  from client: " + input);
                    HashMap<String, String> map = null;
                    try {
                        map = fileHandler.getCreditionals();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(map.containsKey(input))
                {
                    this.username=input;
                    out.println("Username OK, Please enter your password");
                    String Password=in.nextLine();
                    if(Password.equals(map.get(this.username)))
                    {
                        out.println("Login Successfully");

                        LoggedIn=true;
                    }
                    else{
                        out.println("Login failed,Please Enter The UserName Again ");
                        LoggedIn=false;

                    }
                }
                else
                {
                    out.println("Username invalid,Please Enter The UserName Again");
                    LoggedIn=false;

                }
                }
                out.println("Please choose: ‘SHOW DIRECTORIES’ or ‘SHOW DIRECTORY’ or ‘DOWNLOAD FILE’ or" +
                        "‘QUIT’");
                String operation=in.nextLine();
                if (operation.equalsIgnoreCase("QUIT")) {
                    break;
                }
                if(operation.equalsIgnoreCase("SHOW DIRECTORIES"))
                {
                    String listDirectories="";
                    HashMap<String, ArrayList<String>> map = null;
                    try {
                        map = fileHandler.getDirectories();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<String>Directories=map.get(this.username);
                    for(String directory:Directories)
                    {
                        System.out.println(directory);
                        listDirectories+=directory+", ";
                    }
                    out.println("The available directories are:"+listDirectories);
                }
                else if (operation.equalsIgnoreCase("SHOW DIRECTORY"))
                {
                    out.println("Please enter the desired directory");
                    String Directory=in.nextLine();
                    directory=Directory; //da me7tgeno fe el download
                    HashMap<String, ArrayList<String>> map = null;
                    try {
                        map = fileHandler.getDirectories();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<String>Directories=map.get(this.username);
                    if(Directories.contains(Directory))
                    {
                        System.out.println("Here");

                        String filePath = "C:\\Users\\moham\\Desktop\\FTP\\"+this.username+Directory;
                        System.out.println(filePath);
                        ArrayList<String> response= null;
                        try {
                            response = fileHandler.getContentOfDirectory(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String finalresponse=" ";
                        for(String responseString:response)
                        {
                            finalresponse+=responseString+" ";
                        }
                        out.println(finalresponse);
                    }
                    else {
                        out.println("You Are Not authorized to access this directory");
                    }

                }
                else if (operation.equalsIgnoreCase("DOWNLOAD FILE"))
                {
                    out.println("Please choose the desired file to download");
                    String FileToBeDownloaded=in.nextLine();
                    String filePath = "C:\\Users\\moham\\Desktop\\FTP\\"+this.username+directory+"\\"+FileToBeDownloaded;
                    out.println(FileToBeDownloaded);
                    File file = new File(filePath);

                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    assert fis != null;
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    //Get socket's output stream
                    OutputStream os = null;


                    try {
                         serverSocket = new ServerSocket(i);
                         fileSocket=serverSocket.accept();
                        os = fileSocket.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Read File Contents into contents array
                    byte[] contents;
                    long fileLength = file.length();
                    long current = 0;
                    long start = System.nanoTime();
                    while(current!=fileLength){
                        int size = 10000;
                        if(fileLength - current >= size)
                            current += size;
                        else{
                            size = (int)(fileLength - current);
                            current = fileLength;
                        }
                        contents = new byte[size];
                        try {
                            bis.read(contents, 0, size);
                            assert os != null;
                            os.write(contents);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Sending file ... "+(current*100)/fileLength+"% complete!");
                    }
                    try {
                        assert os != null;
                        os.flush();
                        bis.close();
                        fis.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        os.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //File transfer done. Close the socket connection!
                    System.out.println("File sent succesfully!");
                    i++;
                }

            }
        }

    }
}
