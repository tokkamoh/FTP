import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileSystem {
    File myObj = new File("C:\\Users\\moham\\Desktop\\FTP");
    void createFile(String fileName) throws IOException {
        String fileNameAndPath=myObj.getName()+fileName;
        myObj=new File(fileName);
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
        } else {
            System.out.println("File already exists.");
        }
    }
    public ArrayList<String> getContentOfDirectory(String path) throws IOException {
        ArrayList<String>response=new ArrayList<>();
        String line;
        BufferedReader reader=new BufferedReader(new FileReader(path+"\\AvaliableFiles.txt"));
        while((line = reader.readLine()) != null)
        {
            String [] parts=line.split(":");
            for(String part:parts)
            {
                response.add(part);
            }
        }
        return response;
    }
    HashMap<String,String> getCreditionals() throws IOException {
        String filePath = "C:\\Users\\moham\\Desktop\\FTP\\Credentials.txt";
        HashMap<String, String> map = new HashMap<String, String>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(":", 2);
            if (parts.length >= 2)
            {
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            } else {
                System.out.println("ignoring line: " + line);
            }
        }
        for (String key : map.keySet())
        {
            System.out.println(key + ":" + map.get(key));
        }
        reader.close();
        return map;
    }
    HashMap<String, ArrayList<String>> getDirectories() throws IOException {
        String filePath = "C:\\Users\\moham\\Desktop\\FTP\\Directories.txt";
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(":", 4);
            if (parts.length >= 4)
            {
                String key = parts[0];
                String File1 = parts[1];
                String File2 = parts[2];
                String File3 = parts[3];
                ArrayList<String> Files=new ArrayList<>();
                Files.add(File1);
                Files.add(File2);
                Files.add(File3);
                map.put(key, Files);
            } else {
                System.out.println("ignoring line: " + line);
            }
        }
        for (String key : map.keySet())
        {
            System.out.println(key + ":" + map.get(key));
        }
        reader.close();
        return map;
    }

}
