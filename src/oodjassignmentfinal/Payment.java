package oodjassignmentfinal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.*;

public class Payment {
    private String id;
    private String date;
    private String filePath;
    
    public Payment(){
        filePath = "payment.txt";
    }
    
    public Payment(String id){
        filePath = "payment.txt";
        this.id = id;
        this.date = (LocalDate.now()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
    }
    
    public Payment (String id, String date){
        filePath = "payment.txt";
        this.id = id;
        this.date = date;
    }

    public void addPayment(Payment payment){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))){
            bw.write(payment.id+","+payment.date+"\n");
        }catch(IOException e){}
    }
    
    public ArrayList<Payment> readPayment(){
        ArrayList<Payment> paymentList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String[] paymentInfo = line.split(",");
                paymentList.add(new Payment(paymentInfo[0], paymentInfo[1]));
            }
        }catch(IOException e){}
        return paymentList;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
    
    
}
