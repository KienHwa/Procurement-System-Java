package oodjassignmentfinal;

import java.io.*;

public class Balance {
    private double balance = 0;
    private String filePath = "balance.txt";
    
    public double getBalance(){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            balance = Double.parseDouble(br.readLine());
        }catch(IOException e){}
        
        return balance;
    }
    
    public void updateBalance(String newBalance){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(newBalance);
        }catch(IOException e){}
    }
}
