package oodjassignmentfinal;

import java.io.*;
import java.util.*;

public class ItemsSold{
    private String filePath;
    private String id;
    private String itemID;
    private String quantity;
    private String unitPrice;
    
    public ItemsSold(){
        this.filePath = "itemsSold.txt";
    }

    public ItemsSold(String id, String itemID, String quantity, String unitPrice) {
        this.filePath = "itemsSold.txt";
        this.id = id;
        this.itemID = itemID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    public String getFormat(String[] itemsSoldInfo){
        String formatStr = "";
        for(String info : itemsSoldInfo){
            formatStr += info;
            if(info.equals(itemsSoldInfo[itemsSoldInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    public void createFile(String[] itemsSoldInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(itemsSoldInfo));
        }catch(IOException e){}
    }
    
    public ArrayList<String[]> readFile(){
        ArrayList<String[]> itemsSoldList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] itemsSoldInfo = line.split(",");
                itemsSoldList.add(itemsSoldInfo);
            }
        }catch(IOException e){}
        return itemsSoldList;
    }
    
    public ArrayList<ItemsSold> readItemsSold(){
        ArrayList<ItemsSold> itemsSoldList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] itemsSoldInfo = line.split(",");
                itemsSoldList.add(new ItemsSold(itemsSoldInfo[0],itemsSoldInfo[1],itemsSoldInfo[2],itemsSoldInfo[3]));
            }
        }catch(IOException e){}
        return itemsSoldList;
    }

    public String getItemID() {
        return itemID;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getId() {
        return id;
    }
}
