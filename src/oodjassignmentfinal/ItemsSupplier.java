package oodjassignmentfinal;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ItemsSupplier extends CRUD{
    private String supplierName;
    private String itemBuyingPrice;
    
    public ItemsSupplier(){
        this.filePath = "itemSupplier.txt";
    }

    public ItemsSupplier(String id, String supplierId, String itemBuyingPrice) {
        this.filePath = "itemSupplier.txt";
        this.id = id;
        this.supplierName = supplierId;
        this.itemBuyingPrice = itemBuyingPrice;
    }

    @Override
    public String getFormat(String[] itemsSupplierInfo){
        String formatStr = "";
        for(String info : itemsSupplierInfo){
            formatStr += info;
            if(info.equals(itemsSupplierInfo[itemsSupplierInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    public String getFormat(String id, String supplierId, String itemBuyingPrice){
        String formatStr = id+","+supplierId+","+itemBuyingPrice+"\n";
        return formatStr;
    }
    
    public String getFormat(ItemsSupplier itemsSupplier){
        String formatStr = itemsSupplier.id+","+itemsSupplier.supplierName+","+itemsSupplier.itemBuyingPrice+"\n";
        return formatStr;
    }
    
    @Override
    public void createFile(String[] itemsSupplierInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(itemsSupplierInfo));
        }catch(IOException e){}
    }
    
    @Override
    public ArrayList<String[]> readFile(){
        ArrayList<String[]> itemsSupplierList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] itemsSupplierInfo = line.split(",");
                itemsSupplierList.add(itemsSupplierInfo);
            }
        }catch(IOException e){}
        return itemsSupplierList;
    }
    
    public Map<String,List<String>> readItemsSupplierMap(){
        Map<String, List<String>> supplierItems = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("itemSupplier.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 2) {
                    String supplierName = details[1];
                    String itemID = details[0];

                    supplierItems.putIfAbsent(supplierName, new ArrayList<>());
                    supplierItems.get(supplierName).add(itemID);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading supplier items from itemSupplier.txt: " + e.getMessage());
        }
        return supplierItems;
    }
    
    public ArrayList<ItemsSupplier> readIS(){
        ArrayList<ItemsSupplier> isList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] isInfo = line.split(",");
                isList.add(new ItemsSupplier(isInfo[0],isInfo[1],isInfo[2]));
            }
        }catch(IOException e){}
        return isList;
    }
    
    @Override
    public void updateFile(String[] updatedItemsSupplierInfo, String id){
        ArrayList<String[]> itemsSupplierList = readFile();
        ArrayList<String[]> newItemsSupplierList = new ArrayList<>();
        for(String[] itemsSupplierInfo : itemsSupplierList){
            if(itemsSupplierInfo[0].equals(id) && itemsSupplierInfo[1].equals(updatedItemsSupplierInfo[1])){
                newItemsSupplierList.add(updatedItemsSupplierInfo);
            }
            else{
                newItemsSupplierList.add(itemsSupplierInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] itemsSupplierInfo : newItemsSupplierList){
                formatStr += getFormat(itemsSupplierInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    @Override
    public void deleteFile(String id){
        ArrayList<String[]> itemsSupplierList = readFile();
        String updatedData = "";
        for(String[] itemsSupplierInfo : itemsSupplierList){
            if(!itemsSupplierInfo[0].equals(id)){
                updatedData += getFormat(itemsSupplierInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }
    
    public List<String> getItemsSuppliedBySupplier(String supplierName) {
        List<String> items = new ArrayList<>();
        Item itemMethod = new Item();
        Map<String, String> itemMapping = itemMethod.readItemName();

        try (BufferedReader reader = new BufferedReader(new FileReader("itemSupplier.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 2 && details[1].equals(supplierName)) {
                    String itemID = details[0];
                    String itemName = itemMapping.getOrDefault(itemID, "No items supplied (previous items deleted)");
                    items.add(itemName);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading itemSupplier.txt: " + e.getMessage());
        }
        return items;
    }
    
    public void updateSupplierItemsInFile(String supplierNameToEdit, String supplierOldName, List<String> suppliedItems, Map<String, String> items) {
        InventoryManager im = new InventoryManager();
        try {
            List<String> lines = Files.readAllLines(Paths.get("itemSupplier.txt"));
            List<String> updatedLines = new ArrayList<>();
            Set<String> newItemsAdded = new HashSet<>(suppliedItems);

            for (String line : lines) {
                String[] details = line.split(",");
                if (details.length >= 3) {
                    String itemID = details[0];
                    String currentSupplierName = details[1];
                    String buyingPrice = details[2];

                    if (currentSupplierName.equals(supplierOldName.isEmpty() ? supplierNameToEdit : supplierOldName)) {
                        String itemName = items.get(itemID);
                        if (suppliedItems.contains(itemName)) {
                            updatedLines.add(itemID + "," + supplierNameToEdit + "," + buyingPrice);
                            newItemsAdded.remove(itemName);
                        }
                    } else {
                        updatedLines.add(line);
                    }
                }
            }

            for (String itemName : newItemsAdded) {
                String itemID = im.getKeyByValue(items, itemName);
                if (itemID != null) {
                    updatedLines.add(itemID + "," + supplierNameToEdit + ",0");
                }
            }

            Files.write(Paths.get("itemSupplier.txt"), updatedLines);
        } catch (IOException e) {
            System.out.println("Error updating itemSupplier.txt: " + e.getMessage());
        }
    }
    
    public String getSupplierName() {
        return supplierName;
    }

    public String getItemBuyingPrice() {
        return itemBuyingPrice;
    }

    public String getId() {
        return id;
    }
}
