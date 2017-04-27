import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Finding extends Thread {
    private String file_name = "library.txt";
    private String from_cur = "USD", to_cur = "RUB";
    private String json_from_file = null;
    private double answer = 0.0;

    public void setFileName(String file_name){
        this.file_name = file_name;
    }

    public void setFromCur(String from_cur){
        this.from_cur = from_cur;
    }

    public void setToCur(String to_cur){
        this.to_cur = to_cur;
    }

    public double getAnswer(){
        return answer;
    }

    @Override
    public void run() {
        json_from_file = FindInFile(file_name, from_cur, to_cur);
        if (json_from_file == null) {
            try {
            answer = GetFromWeb(file_name, from_cur, to_cur);
            }
            catch (Exception e) {
                System.out.println("Error in finding from web");
                e.printStackTrace();
            }
        }
        else {
            answer = AnsFromJson(json_from_file);
        }
    }

    public static double GetFromWeb (String file_name, String from_cur, String to_cur) throws Exception{
        double answer = 0.0;
        String def_cur = "http://api.fixer.io/latest?base=" + from_cur +
                "&symbols=" + to_cur;
        URL url = new URL(def_cur);
        try {
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            try {
                String line = null;
                BufferedReader in_buff_reader = new BufferedReader(new InputStreamReader(
                        connect.getInputStream()));
                line = in_buff_reader.readLine();
                SaveInFile(file_name, line);
                answer = AnsFromJson(line);
                in_buff_reader.close();
            }
            catch (Exception e) {
                System.out.println("Error in reading from server");
                e.printStackTrace();
            }
            finally {
                connect.disconnect();
            }
        }
        catch (Exception e) {
            System.out.println("No connection to the internet");
            e.printStackTrace();
        }
        return answer;
    }

    public static String GetDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return format.format(date);
    }

    public static String FindInFile (String file_name,String from_cur, String to_cur) {
        String line = null;
        String date = GetDate();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(file_name));
            while ((line = reader.readLine()) != null) {
                if ((line.contains("\"base\":\"" + from_cur) && line.contains(to_cur) && line.contains(date))) {
                    break;
                }
            }
            reader.close();
        }
        catch (NoSuchFileException e) {
            CreateFile(file_name);
        }
        catch (IOException e) {
            System.out.println("Error in reading from file");
            e.printStackTrace();
        }
        return line;
    }

    public static void CreateFile (String file_name){
        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(file_name));
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double AnsFromJson (String line) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RateObject.class, new RatesDeserializer())
                .create();
        ApiResponse responce = gson.fromJson(line, ApiResponse.class);
        return responce.getCur();
    }

    public static void SaveInFile (String file_name, String line){
        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(file_name), StandardOpenOption.APPEND);
            writer.write(line);
            writer.newLine();
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error in writing in file");
            e.printStackTrace();
        }
    }
}
