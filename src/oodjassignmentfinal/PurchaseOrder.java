package oodjassignmentfinal;

import java.io.*;
import java.util.*;

public class PurchaseOrder extends CRUD implements ProvideID{
    private String creatorId;
    private String approverId;
    private String supplierId;
    private String status;
    private String paymentStatus;
    private String date;
    
    public PurchaseOrder(){
        this.filePath = "purchaseOrders.txt";
    }

    public PurchaseOrder(String id, String creatorId, String approverId, String supplierId, String status, String paymentStatus, String date) {
        this.filePath = "purchaseOrders.txt";
        this.id = id;
        this.creatorId = creatorId;
        this.approverId = approverId;
        this.supplierId = supplierId;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.date = date;
    }
    
    @Override
    public String getFormat(String[] poInfo){
        String formatStr = "";
        for(String info : poInfo){
            formatStr += info;
            if(info.equals(poInfo[poInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    public String getFormat(String id, String creatorId, String approverId, String supplierId, String status, String paymentStatus, String date){
        String formatStr = id+","+creatorId+","+approverId+","+supplierId+","+status+","+paymentStatus+","+date+"\n";
        return formatStr;
    }
    
    public String getFormat(PurchaseOrder po){
        String formatStr = po.id+","+po.creatorId+","+po.approverId+","+po.supplierId+","+po.status+","+po.paymentStatus+","+po.date+"\n";
        return formatStr;
    }
    
    @Override
    public void createFile(String[] poInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(poInfo));
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
        ArrayList<String[]> poList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] poInfo = line.split(",");
                poList.add(poInfo);
            }
        }catch(IOException e){}
        return poList;
    }
    
    public ArrayList<PurchaseOrder> readPO(){
        ArrayList<PurchaseOrder> poList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] poInfo = line.split(",");
                poList.add(new PurchaseOrder(poInfo[0],poInfo[1],poInfo[2],poInfo[3],poInfo[4],poInfo[5],poInfo[6]));
            }
        }catch(IOException e){}
        return poList;
    }
    
    @Override
    public void updateFile(String[] updatedPOInfo, String id){
        ArrayList<String[]> poList = readFile();
        ArrayList<String[]> newPOList = new ArrayList<>();
        for(String[] poInfo : poList){
            if(poInfo[0].equals(id)){
                newPOList.add(updatedPOInfo);
            }
            else{
                newPOList.add(poInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] poInfo : newPOList){
                formatStr += getFormat(poInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    public void updateFile(PurchaseOrder updatedPOInfo){
        ArrayList<PurchaseOrder> poList = readPO();
        ArrayList<PurchaseOrder> newPOList = new ArrayList<>();
        for(PurchaseOrder poInfo : poList){
            if(poInfo.getId().equals(updatedPOInfo.getId())){
                newPOList.add(updatedPOInfo);
            }
            else{
                newPOList.add(poInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(PurchaseOrder poInfo : newPOList){
                formatStr += getFormat(poInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    @Override
    public void deleteFile(String id){
        ArrayList<String[]> poList = readFile();
        String updatedData = "";
        for(String[] poInfo : poList){
            if(!poInfo[0].equals(id)){
                updatedData += getFormat(poInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }
    
    protected void deleteLineFile(String lineToDelete){     //cannot use deleteFile because overload cannot be same parameter
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

    public String getCreatorId() {
        return creatorId;
    }

    public String getApproverId() {
        return approverId;
    }
    
    public void setApproverId(String approverID) {
        this.approverId = approverID;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
    
    @Override
    public String provideID(String frontChar){
        int maxID = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null){
                String[] fields = line.split(",");
                if (fields.length > 0 && fields[0].startsWith(frontChar)){
                    String numberID = fields[0].substring(frontChar.length());
                    try{
                        int numInFile = Integer.parseInt(numberID);
                        if (numInFile > maxID){
                            maxID = numInFile;
                        }
                    }catch(NumberFormatException e){System.out.println("Invalid ID format: " + fields[0]);}
                }
            }
        }catch(IOException e){System.out.println("Error reading file: " + e.getMessage());}
        
        int nextNum = maxID + 1;
        String formattedID;
        
        if (nextNum < 10){
            formattedID = frontChar+"00"+nextNum;
        }
        else if (nextNum < 100){
            formattedID = frontChar+"0"+nextNum;
        }
        else{
            formattedID = frontChar+nextNum;
        }
        
        return formattedID;
    }
}
