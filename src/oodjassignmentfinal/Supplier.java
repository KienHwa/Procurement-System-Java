package oodjassignmentfinal;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Supplier extends CRUD implements Mapable, HashMapable{
    private String name;
    private String contactNo;
    private String address;
    
    public Supplier(){
        this.filePath = "supplier.txt";
    }

    public Supplier(String id, String name, String contactNo, String address) {
        this.filePath = "supplier.txt";
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.address = address;
    }

    @Override
    public String getFormat(String[] supplierInfo){
        String formatStr = "";
        for(String info : supplierInfo){
            formatStr += info;
            if(info.equals(supplierInfo[supplierInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    public String getFormat(String id, String name, String contactNo, String address){
        String formatStr = id+","+name+","+contactNo+","+address+"\n";
        return formatStr;
    }
    
    public String getFormat(Supplier supplier){
        String formatStr = supplier.id+","+supplier.name+","+supplier.contactNo+","+supplier.address+"\n";
        return formatStr;
    }
    
    @Override
    public void createFile(String[] supplierInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(supplierInfo));
        }catch(IOException e){}
    }
    
    public void createFile(Map<String, String> suppliers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("supplier.txt"))) {
            for (String supplier : suppliers.values()) {
                writer.write(supplier);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving suppliers to supplier.txt: " + e.getMessage());
        }
    }
    
    @Override
    public ArrayList<String[]> readFile(){
        ArrayList<String[]> supplierList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] supplierInfo = line.split(",");
                supplierList.add(supplierInfo);
            }
        }catch(IOException e){}
        return supplierList;
    }
    
    @Override
    public Map<String, String> readMap(){
        Map<String, String> suppliers = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("supplier.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] supplierDetails = line.split(",");
                if (supplierDetails.length >= 4) {
                    suppliers.put(supplierDetails[0], line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading suppliers from supplier.txt: " + e.getMessage());
        }
        return suppliers;
    }
    
    public ArrayList<Supplier> readSupplier(){
        ArrayList<Supplier> supplierList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] supplierInfo = line.split(",");
                supplierList.add(new Supplier(supplierInfo[0],supplierInfo[1],supplierInfo[2],supplierInfo[3]));
            }
        }catch(IOException e){}
        return supplierList;
    }
    
    public LinkedHashMap<String,Supplier> readSupplierMap(){
        LinkedHashMap<String,Supplier> supplierMap = new LinkedHashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] supplierInfo = line.split(",");
                supplierMap.put(supplierInfo[0], new Supplier(supplierInfo[0],supplierInfo[1],supplierInfo[2],supplierInfo[3]));
            }
        }catch(IOException e){}
        return supplierMap;
    }
    
    @Override
    public LinkedHashMap<String, String[]> readHashMap(){
        LinkedHashMap<String, String[]> itemListMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("supplier.txt"))){
            String line;
            while ((line = br.readLine()) != null){
                String[] data = line.split(",");
                itemListMap.put(data[0], data);
            }
        }catch(IOException e){e.printStackTrace();}
        
        return itemListMap;
    }
    
    public LinkedHashMap<String, String[]> readHashMapName(){
        LinkedHashMap<String, String[]> itemListMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("supplier.txt"))){
            String line;
            while ((line = br.readLine()) != null){
                String[] data = line.split(",");
                itemListMap.put(data[1], data);
            }
        }catch(IOException e){e.printStackTrace();}
        
        return itemListMap;
    }
    
    public LinkedHashMap<String, Supplier> readSupplierCPR(){
        LinkedHashMap<String, Supplier> itemListMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null){
                String[] data = line.split(",");
                itemListMap.put(data[1], new Supplier(data[0], data[1], data[2], data[3]));
            }
        }catch(IOException e){}
        return itemListMap;
    }
    
    @Override
    public void updateFile(String[] updatedSupplierInfo, String id){
        ArrayList<String[]> supplierList = readFile();
        ArrayList<String[]> newSupplierList = new ArrayList<>();
        for(String[] supplierInfo : supplierList){
            if(supplierInfo[0].equals(id)){
                newSupplierList.add(updatedSupplierInfo);
            }
            else{
                newSupplierList.add(supplierInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] supplierInfo : newSupplierList){
                formatStr += getFormat(supplierInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    public void updateFile(Supplier updatedSupplierInfo){
        ArrayList<Supplier> supplierList = readSupplier();
        ArrayList<Supplier> newSupplierList = new ArrayList<>();
        for(Supplier supplierInfo : supplierList){
            if(supplierInfo.getId().equals(id)){
                newSupplierList.add(updatedSupplierInfo);
            }
            else{
                newSupplierList.add(supplierInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(Supplier supplierInfo : newSupplierList){
                formatStr += getFormat(supplierInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    @Override
    public void deleteFile(String id){
        ArrayList<String[]> supplierList = readFile();
        String updatedData = "";
        for(String[] supplierInfo : supplierList){
            if(!supplierInfo[0].equals(id)){
                updatedData += getFormat(supplierInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }

    public String getName() {
        return name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }
    
    public String generateNewID(Map<String, String> suppliers) {
        String lastID = suppliers.keySet().stream().max(String::compareTo).orElse("S000");
        int numericPart = Integer.parseInt(lastID.substring(1)) + 1;
        return "S" + String.format("%03d", numericPart);
    }
    
    public void updateSupplierInFile(String supplierID, String supplierName, String contactNo, String address) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("supplier.txt"));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(supplierID + ",")) {
                    lines.set(i, supplierID + "," + supplierName + "," + contactNo + "," + address);
                    break;
                }
            }
            Files.write(Paths.get("supplier.txt"), lines);
        } catch (IOException e) {
            System.out.println("Error updating supplier.txt: " + e.getMessage());
        }
    }
}
