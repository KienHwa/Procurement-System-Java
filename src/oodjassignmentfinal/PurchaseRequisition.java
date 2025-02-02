package oodjassignmentfinal;

import java.io.*;
import java.util.*;

public class PurchaseRequisition extends CRUD implements ProvideID{
    private String submitterID;
    private String approverID;
    private String supplierID;
    private String itemID;
    private String quantity;
    private String status;
    private String date;
    
    public PurchaseRequisition(){
        this.filePath = "purchaseRequisitions.txt";
    }

    public PurchaseRequisition(String id, String submitterID, String approverID, String supplierID, String itemID, String quantity, String status, String date) {
        this.filePath = "purchaseRequisitions.txt";
        this.id = id;
        this.submitterID = submitterID;
        this.approverID = approverID;
        this.supplierID = supplierID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.status = status;
        this.date = date;
    }
    
    @Override
    public String getFormat(String[] prInfo){
        String formatStr = "";
        for(String info : prInfo){
            formatStr += info;
            if(info.equals(prInfo[prInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    public String getFormat(String id, String submitterID, String approverID, String supplierID, String itemID, String quantity, String status, String date){
        String formatStr = id+","+submitterID+","+approverID+","+supplierID+","+itemID+","+quantity+","+status+","+date+"\n";
        return formatStr;
    }
    
    public String getFormat(PurchaseRequisition pr){
        String formatStr = pr.id+","+pr.submitterID+","+pr.approverID+","+pr.supplierID+","+pr.itemID+","+pr.quantity+","+pr.status+","+pr.date+"\n";
        return formatStr;
    }
    
    @Override
    public void createFile(String[] prInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(prInfo));
        }catch(IOException e){}
    }
    
    @Override
    public ArrayList<String[]> readFile(){
        ArrayList<String[]> prList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] prInfo = line.split(",");
                prList.add(prInfo);
            }
        }catch(IOException e){}
        return prList;
    }
    
    public ArrayList<PurchaseRequisition> readPR(){     //Use THIS
        ArrayList<PurchaseRequisition> prList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] prInfo = line.split(",");
                prList.add(new PurchaseRequisition(prInfo[0],prInfo[1],prInfo[2],prInfo[3],prInfo[4],prInfo[5],prInfo[6],prInfo[7]));
            }
        }catch(IOException e){}
        return prList;
    }
    
    @Override
    public void updateFile(String[] updatedPRInfo, String id){
        ArrayList<String[]> prList = readFile();
        ArrayList<String[]> newPRList = new ArrayList<>();
        for(String[] prInfo : prList){
            if(prInfo[0].equals(id)){
                newPRList.add(updatedPRInfo);
            }
            else{
                newPRList.add(prInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] prInfo : newPRList){
                formatStr += getFormat(prInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    @Override
    public void deleteFile(String id){
        ArrayList<String[]> prList = readFile();
        String updatedData = "";
        for(String[] prInfo : prList){
            if(!prInfo[0].equals(id)){
                updatedData += getFormat(prInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }

    public String getSubmitterID() {
        return submitterID;
    }

    public String getApproverID() {
        return approverID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setApproverID(String approverID) {
        this.approverID = approverID;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    //TsengHei stuff
    
    public String getFormatPM(PurchaseRequisition pr){      //因为你original后面有一个\n, 所以不行
        String formatStr = pr.id+","+pr.submitterID+","+pr.approverID+","+pr.supplierID+","+pr.itemID+","+pr.quantity+","+pr.status+","+pr.date;
        return formatStr;
    }
    
    public void updateFile(ArrayList<PurchaseRequisition> updateFileInfo){     //USE THIS
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            for (PurchaseRequisition pr : updateFileInfo){
                String line = getFormatPM(pr);
                bw.write(line);
                bw.newLine();
            }
        }catch(IOException e){System.out.println("Error writing to file: " + e.getMessage());}
    }
    
    public void createFile(String lineToAppend){    //parameter not the same
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(lineToAppend);
            bw.newLine();
        }
        catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
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