package oodjassignmentfinal;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Item extends CRUD implements Mapable, HashMapable{
    private String name;
    private String sellingPrice;
    private String stockLevel;
    private String reorderLevel;
    private String updateDate;
    
    public Item(){
        this.filePath = "item.txt";
    }

    public Item(String id, String name, String sellingPrice, String stockLevel, String reorderLevel, String updateDate) {
        this.filePath = "item.txt";
        this.id = id;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.stockLevel = stockLevel;
        this.reorderLevel = reorderLevel;
        this.updateDate = updateDate;
    }
    
    @Override
    public String getFormat(String[] itemInfo){
        String formatStr = "";
        for(String info : itemInfo){
            formatStr += info;
            if(info.equals(itemInfo[itemInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    public String getFormat(String id, String name, String sellingPrice, String stockLevel, String reorderLevel, String updateDate){
        String formatStr = id+","+name+","+sellingPrice+","+stockLevel+","+reorderLevel+","+updateDate+"\n";
        return formatStr;
    }
    
    public String getFormat(Item item){
        String formatStr = item.id+","+item.name+","+item.sellingPrice+","+item.stockLevel+","+item.reorderLevel+","+item.updateDate+"\n";
        return formatStr;
    }
    
    @Override
    public void createFile(String[] itemInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(itemInfo));
            System.out.println("Item added successfully with Item ID: " + itemInfo[0]);
        }catch(IOException e){
            System.out.println("Error writing to item.txt: " + e.getMessage());
        }
    }
    
    @Override
    public ArrayList<String[]> readFile(){
        ArrayList<String[]> itemList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] itemInfo = line.split(",");
                itemList.add(itemInfo);
            }
        }catch(IOException e){}
        return itemList;
    }
    
    @Override
    public Map<String,String> readMap(){
        Map<String, String> items = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("item.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemDetails = line.split(",");
                if (itemDetails.length >= 5) {
                    items.put(itemDetails[0], line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading items from item.txt: " + e.getMessage());
        }
        return items;
    }
    
    public ArrayList<Item> readItem(){
        ArrayList<Item> itemList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] itemInfo = line.split(",");
                itemList.add(new Item(itemInfo[0],itemInfo[1],itemInfo[2],itemInfo[3],itemInfo[4],itemInfo[5]));
            }
        }catch(IOException e){}
        return itemList;
    }
    
    public LinkedHashMap<String,Item> readItemMap(){
        LinkedHashMap<String,Item> itemMap = new LinkedHashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] itemInfo = line.split(",");
                itemMap.put(itemInfo[0], new Item(itemInfo[0],itemInfo[1],itemInfo[2],itemInfo[3],itemInfo[4],itemInfo[5]));
            }
        }catch(IOException e){}
        return itemMap;
    }
    
    public Map<String, String> readItemName() {
        Map<String, String> items = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("item.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemDetails = line.split(",");
                if (itemDetails.length >= 2) {
                    String itemID = itemDetails[0];
                    String itemName = itemDetails[1];
                    items.put(itemID, itemName);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading items from item.txt: " + e.getMessage());
        }
        return items;
    }
    
    @Override
    public LinkedHashMap<String, String[]> readHashMap(){
        LinkedHashMap<String, String[]> itemListMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null){
                String[] data = line.split(",");
                itemListMap.put(data[0], data);
            }
        }catch(IOException e){System.out.println("Error reading file: " + e.getMessage());}
        
        return itemListMap;
    }
    
    @Override
    public void updateFile(String[] updatedItemInfo, String id){
        ArrayList<String[]> itemList = readFile();
        ArrayList<String[]> newItemList = new ArrayList<>();
        for(String[] itemInfo : itemList){
            if(itemInfo[0].equals(id)){
                newItemList.add(updatedItemInfo);
            }
            else{
                newItemList.add(itemInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] itemInfo : newItemList){
                formatStr += getFormat(itemInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    public void updateFile(Item updatedItemInfo){
        ArrayList<Item> itemList = readItem();
        ArrayList<Item> newItemList = new ArrayList<>();
        for(Item itemInfo : itemList){
            if(itemInfo.getId().equals(id)){
                newItemList.add(updatedItemInfo);
            }
            else{
                newItemList.add(itemInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(Item itemInfo : newItemList){
                formatStr += getFormat(itemInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    @Override
    public void deleteFile(String id){
        ArrayList<String[]> itemList = readFile();
        String updatedData = "";
        for(String[] itemInfo : itemList){
            if(!itemInfo[0].equals(id)){
                updatedData += getFormat(itemInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }

    public String getName() {
        return name;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public String getStockLevel() {
        return stockLevel;
    }

    public String getReorderLevel() {
        return reorderLevel;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getId() {
        return id;
    }
    
    public List<String> getAvailableYears(Map<String, String> items) {
        Set<String> years = new HashSet<>();
        for (String item : items.values()) {
            String[] details = item.split(",");
            if (details.length >= 6) {
                String year = details[5].split("/")[2];
                years.add(year);
            }
        }
        List<String> yearList = new ArrayList<>(years);
        Collections.sort(yearList);
        return yearList;
    }

    public List<String> getAvailableMonths(Map<String, String> items) {
        Set<String> months = new HashSet<>();
        for (String item : items.values()) {
            String[] details = item.split(",");
            if (details.length >= 6) {
                String month = details[5].split("/")[1];
                months.add(month);
            }
        }
        List<String> monthList = new ArrayList<>(months);
        Collections.sort(monthList);
        return monthList;
    }
    
    public String generateNewID(Map<String, String> items) {
        String lastItemID = "I000";

        if (!items.isEmpty()) {
            lastItemID = items.keySet().stream()
                    .max(String::compareTo)
                    .orElse("I000");
        }

        int numericPart = Integer.parseInt(lastItemID.substring(1)) + 1;
        return "I" + String.format("%03d", numericPart);
    }
    
    public void removeItemFromFile(String filePath, String itemID) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (!line.contains(itemID)) {
                    updatedLines.add(line);
                }
            }

            Files.write(Paths.get(filePath), updatedLines);
        } catch (IOException e) {
            System.out.println("Error updating file: " + filePath + ". " + e.getMessage());
        }
    }
}
