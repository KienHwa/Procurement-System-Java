package oodjassignmentfinal;

import java.io.*;
import java.util.*;

public class OrderRequisition extends CRUD{
    private String prId;
    
    public OrderRequisition(){
        this.filePath = "orderRequisitions.txt";
    }
    
    public OrderRequisition(String id, String prId){
        this.filePath = "orderRequisitions.txt";
        this.id = id;
        this.prId = prId;
    }
    
    @Override
    public String getFormat(String[] orInfo){
        String formatStr = "";
        for(String info : orInfo){
            formatStr += info;
            if(info.equals(orInfo[orInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    @Override
    public void createFile(String[] orInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(orInfo));
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
        ArrayList<String[]> orList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] orInfo = line.split(",");
                orList.add(orInfo);
            }
        }catch(IOException e){}
        return orList;
    }
    
    public ArrayList<OrderRequisition> readOR(){
        ArrayList<OrderRequisition> orList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] orInfo = line.split(",");
                orList.add(new OrderRequisition(orInfo[0],orInfo[1]));
            }
        }catch(IOException e){}
        return orList;
    }
    
    @Override
    public void updateFile(String[] updatedORInfo, String id){
        ArrayList<String[]> orList = readFile();
        ArrayList<String[]> newORList = new ArrayList<>();
        for(String[] orInfo : orList){
            if(orInfo[0].equals(id)){
                newORList.add(updatedORInfo);
            }
            else{
                newORList.add(orInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] orInfo : newORList){
                formatStr += getFormat(orInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    @Override
    public void deleteFile(String id){
        ArrayList<String[]> orList = readFile();
        String updatedData = "";
        for(String[] orInfo : orList){
            if(!orInfo[0].equals(id)){
                updatedData += getFormat(orInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }
    
    public void deleteLineFile(String lineToDelete){
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

    public String getId() {
        return id;
    }

    public String getPrId() {
        return prId;
    }
}
