package oodjassignmentfinal;

import java.io.*;
import java.util.*;

public class ItemsOrdered extends CRUD{
    public ItemsOrdered(){
        this.filePath = "itemOrdered.txt";
    }

    @Override
    public String getFormat(String[] itemsOrderedInfo){
        String formatStr = "";
        for(String info : itemsOrderedInfo){
            formatStr += info;
            if(info.equals(itemsOrderedInfo[itemsOrderedInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    @Override
    public void createFile(String[] itemsOrderedInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(itemsOrderedInfo));
        }catch(IOException e){}
    }
    
    @Override
    public ArrayList<String[]> readFile(){
        ArrayList<String[]> itemsOrderedList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] itemsOrderedInfo = line.split(",");
                itemsOrderedList.add(itemsOrderedInfo);
            }
        }catch(IOException e){}
        return itemsOrderedList;
    }
    
    @Override
    public void updateFile(String[] updatedItemsOrderedInfo, String id){
        ArrayList<String[]> itemsOrderedList = readFile();
        ArrayList<String[]> newItemsOrderedList = new ArrayList<>();
        for(String[] itemsOrderedInfo : itemsOrderedList){
            if(itemsOrderedInfo[0].equals(id)){
                newItemsOrderedList.add(updatedItemsOrderedInfo);
            }
            else{
                newItemsOrderedList.add(itemsOrderedInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] itemsOrderedInfo : newItemsOrderedList){
                formatStr += getFormat(itemsOrderedInfo);
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
    
    public void deleteFile(String id, String itemId){
        ArrayList<String[]> poItemList = readFile();
        String updatedData = "";
        for(String[] poItemInfo : poItemList){
            if(!(poItemInfo[0].equals(id)&&poItemInfo[1].equals(itemId))){
                updatedData += getFormat(poItemInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }
}
