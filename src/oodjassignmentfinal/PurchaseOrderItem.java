package oodjassignmentfinal;

import java.io.*;
import java.util.*;

public class PurchaseOrderItem extends CRUD{
    private String itemId;
    private String quantity;
    private String unitPrice;
    
    public PurchaseOrderItem(){
        this.filePath = "purchaseOrderItems.txt";
    }

    public PurchaseOrderItem(String id, String itemId, String quantity, String unitPrice) {
        this.filePath = "purchaseOrderItems.txt";
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @Override
    public String getFormat(String[] poItemInfo){
        String formatStr = "";
        for(String info : poItemInfo){
            formatStr += info;
            if(info.equals(poItemInfo[poItemInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    public String getFormat(String id, String itemId, String quantity, String unitPrice){
        String formatStr = id+","+itemId+","+quantity+","+unitPrice+"\n";
        return formatStr;
    }
    
    public String getFormat(PurchaseOrderItem poItem){
        String formatStr = poItem.id+","+poItem.itemId+","+poItem.quantity+","+poItem.unitPrice+"\n";
        return formatStr;
    }
    
    @Override
    public void createFile(String[] poItemInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(poItemInfo));
        }catch(IOException e){}
    }
    
    public void createFile(String lineToAppend){    //overload createFile
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(lineToAppend);
            bw.newLine();
        }
        catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    @Override
    public ArrayList<String[]> readFile(){
        ArrayList<String[]> poItemList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] poItemInfo = line.split(",");
                poItemList.add(poItemInfo);
            }
        }catch(IOException e){}
        return poItemList;
    }
    
    public ArrayList<PurchaseOrderItem> readPOItem(){
        ArrayList<PurchaseOrderItem> poItemList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] poItemInfo = line.split(",");
                poItemList.add(new PurchaseOrderItem(poItemInfo[0],poItemInfo[1],poItemInfo[2],poItemInfo[3]));
            }
        }catch(IOException e){}
        return poItemList;
    }
    
    @Override
    public void updateFile(String[] updatedPOItemInfo, String id){
        ArrayList<String[]> poItemList = readFile();
        ArrayList<String[]> newPOItemList = new ArrayList<>();
        for(String[] poItemInfo : poItemList){
            if(poItemInfo[0].equals(id)){
                newPOItemList.add(updatedPOItemInfo);
            }
            else{
                newPOItemList.add(poItemInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] poItemInfo : newPOItemList){
                formatStr += getFormat(poItemInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    public void updateFile(PurchaseOrderItem updatedPOItemInfo){
        ArrayList<PurchaseOrderItem> poItemList = readPOItem();
        ArrayList<PurchaseOrderItem> newPOItemList = new ArrayList<>();
        for(PurchaseOrderItem poItemInfo : poItemList){
            if(poItemInfo.getId().equals(id)){
                newPOItemList.add(updatedPOItemInfo);
            }
            else{
                newPOItemList.add(poItemInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(PurchaseOrderItem poItemInfo : newPOItemList){
                formatStr += getFormat(poItemInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    @Override
    public void deleteFile(String id){
        ArrayList<String[]> poItemList = readFile();
        String updatedData = "";
        for(String[] poItemInfo : poItemList){
            if(!poItemInfo[0].equals(id)){
                updatedData += getFormat(poItemInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }
    
    public void deleteLineFile(String lineToDelete){    //cannot use deleteFile because overload cannot be same parameter
        ArrayList<String[]> fileToDelete = readFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            for (String[] line : fileToDelete){
                if (!line[0].equals(lineToDelete)) {
                    bw.write(String.join(",", line));
                    bw.newLine();
                }
            }
        }
        catch (IOException e){
            System.out.println("Error deleting a line on file: " + e.getMessage());
        }
    }

    public String getItemId() {
        return itemId;
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
    
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}