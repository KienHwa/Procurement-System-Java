package oodjassignmentfinal;

import java.util.*;
import java.io.*;

public class User extends CRUD implements HashMapable{
    protected String username,email,password,role,name,otp;
    
    public User(){
        this.filePath = "users.txt";
    }

    public User(String id, String username, String email, String password, String role, String name, String otp) {
        this.filePath = "users.txt";
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.otp = otp;
    }
    
    @Override
    public String getFormat(String[] userInfo){
        String formatStr = "";
        for(String info : userInfo){
            formatStr += info;
            if(info.equals(userInfo[userInfo.length - 1])){
                formatStr += "\n";
            }else{
                formatStr += ",";
            }
        }
        return formatStr;
    }
    
    public String getFormat(String id, String username, String email, String password, String role, String name, String otp){
        String formatStr = id+","+username+","+email+","+password+","+role+","+name+","+otp+"\n";
        return formatStr;
    }
    
    public String getFormat(User user){
        String formatStr = user.id+","+user.username+","+user.email+","+user.password+","+user.role+","+user.name+","+user.otp+"\n";
        return formatStr;
    }
    
    @Override
    public void createFile(String[] userInfo){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(getFormat(userInfo));
        }catch(IOException e){}
    }
    
    @Override
    public ArrayList<String[]> readFile(){
        ArrayList<String[]> userList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] userInfo = line.split(",");
                userList.add(userInfo);
            }
        }catch(IOException e){}
        return userList;
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
    
    public ArrayList<User> readUser(){
        ArrayList<User> userList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] userInfo = line.split(",");
                userList.add(new User(userInfo[0],userInfo[1],userInfo[2],userInfo[3],userInfo[4],userInfo[5],userInfo[6]));
            }
        }catch(IOException e){}
        return userList;
    }
    
    public LinkedHashMap<String,User> readUserMap(){
        LinkedHashMap<String,User> userMap = new LinkedHashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] userInfo = line.split(",");
                userMap.put(userInfo[0], new User(userInfo[0],userInfo[1],userInfo[2],userInfo[3],userInfo[4],userInfo[5],userInfo[6]));
            }
        }catch(IOException e){}
        return userMap;
    }
    
    protected LinkedHashMap<String, String[]> readUserPR(){
        LinkedHashMap<String, String[]> userListMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null){
                String[] fields = line.split(",");
                String roleUser = fields[4];
                if (roleUser.equals("salesManager") || roleUser.equals("purchaseManager")){
                    String userID = fields[0];
                    userListMap.put(userID, fields);
                }
            }
        }catch(IOException e){e.printStackTrace();}
        
        return userListMap;
    }
    
    @Override
    public void updateFile(String[] updatedUserInfo, String id){
        ArrayList<String[]> userList = readFile();
        ArrayList<String[]> newUserList = new ArrayList<>();
        for(String[] userInfo : userList){
            if(userInfo[0].equals(id)){
                newUserList.add(updatedUserInfo);
            }
            else{
                newUserList.add(userInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(String[] userInfo : newUserList){
                formatStr += getFormat(userInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    public void updateFile(User updatedUserInfo){
        ArrayList<User> userList = readUser();
        ArrayList<User> newUserList = new ArrayList<>();
        for(User userInfo : userList){
            if(userInfo.getId().equals(id)){
                newUserList.add(updatedUserInfo);
            }
            else{
                newUserList.add(userInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            String formatStr = "";
            for(User userInfo : newUserList){
                formatStr += getFormat(userInfo);
            }
            bw.write(formatStr);
        }catch(IOException e){}
    }
    
    @Override
    public void deleteFile(String id){
        ArrayList<String[]> userList = readFile();
        String updatedData = "";
        for(String[] userInfo : userList){
            if(!userInfo[0].equals(id)){
                updatedData += getFormat(userInfo);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(updatedData);
        }catch(IOException e){}
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    
    public String generateOTP() {
        Random random = new Random();
        return String.format("%03d", random.nextInt(1000));
    }
    
    public void resetOTP(String[] userData){
        userData[6] = "null";
        User user = new User();
        user.updateFile(userData, userData[0]);
    }
}
