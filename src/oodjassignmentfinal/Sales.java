package oodjassignmentfinal;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Sales{
    private String filePath;
    private String id;
    private String userId;
    private String date;
    
    public Sales(){
        this.filePath = "sales.txt";
    }
    
    public Sales(String id, String userId){
        this.filePath = "sales.txt";
        this.id = id;
        this.userId = userId;
        this.date = LocalDate.now().toString();
    }
    
    public String getFormat(Sales sales){
        String formatStr = sales.id+","+sales.userId+","+sales.date+"\n";
        return formatStr;
    }
    
    public void createFile(Sales sales){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(sales));
        }catch(IOException e){}
    }
    
    public ArrayList<Sales> readFile(){
        ArrayList<Sales> salesList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] salesInfo = line.split(",");
                salesList.add(new Sales(salesInfo[0],salesInfo[1]));
            }
        }catch(IOException e){}
        return salesList;
    }

    public String getId() {
        return id;
    }

    
    public String generateNewID() {
        int lastSalesID = 0;
        int newLastSalesID;
        ArrayList<Sales> salesList = readFile();

        if (!salesList.isEmpty()) {
            for(Sales sales : salesList){
                lastSalesID = (newLastSalesID = Integer.parseInt(sales.getId().substring(2))) > lastSalesID ? newLastSalesID : lastSalesID;
            }
        }

        int newID = lastSalesID + 1;
        return "Sa" + String.format("%03d", newID);
    }
}
