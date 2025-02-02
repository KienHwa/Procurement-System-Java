package oodjassignmentfinal;

import java.text.*;
import java.util.*;
import java.util.concurrent.*;

public class FMNotification extends Notification implements NotificationViewable{
    private String notificationStr;
    
    @Override
    public String getNotification(){
        ArrayList<String[]> outdatedPOList = checkOutdatedPO();
        ArrayList<String[]> unpayReceivedPOList = checkUnpayReceivePO();
        int notificationAmount = outdatedPOList.size()+unpayReceivedPOList.size();
        notificationStr = "<<You have "+notificationAmount+" unsettled notification. Please view your notification.>>";
        return notificationStr;
    }
    
    @Override
    public void viewNotification(){
        Scanner sc = new Scanner(System.in);
        ArrayList<String[]> outdatedPOList = checkOutdatedPO();
        ArrayList<String[]> unpayReceivedPOList = checkUnpayReceivePO();
        int index = 1;
        System.out.println("\n------------------------------Notification------------------------------\n<<Outdated Purchase Orders>>");
        for(String[] outdatedPO : outdatedPOList){
            System.out.println(index+". The purchase order, "+outdatedPO[0]+" that created on "+outdatedPO[1]+" from the supplier, "+outdatedPO[2]+" has passed "+outdatedPO[3]+" days. Please approve it as soon as posible.");
            index++;
        }
        index = 1;
        System.out.println("<<Unpay Received Purchase Orders>>");
        for(String[] unpayReceivedPO : unpayReceivedPOList){
            System.out.println(index+". The items of the purchase order, "+unpayReceivedPO[0]+" has received. Please make payment to the supplier, "+unpayReceivedPO[1]+" as soon as posible.");
            index++;
        }
        System.out.print("(Enter to return)");
        sc.nextLine();
    }
    
    private ArrayList<String[]> checkUnpayReceivePO(){
        PurchaseOrder poMethod = new PurchaseOrder();
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        ArrayList<String[]> unpayReceivePOList = new ArrayList<>();
        for(PurchaseOrder po : poList){
            if(po.getStatus().equals("Received")&&po.getPaymentStatus().equals("Pending")){
                unpayReceivePOList.add(new String[]{po.getId(),po.getSupplierId()});
            }
        }
        return unpayReceivePOList;
    }
    
    private ArrayList<String[]> checkOutdatedPO(){
        PurchaseOrder poMethod = new PurchaseOrder();
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        ArrayList<String[]> outdatedPOList = new ArrayList<>();
        for(PurchaseOrder po : poList){
            String dateString = po.getDate().substring(0, 10);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try{
                Date poDate = dateFormat.parse(dateString);
                Date nowDate = new Date();
                long daysDifference = TimeUnit.MILLISECONDS.toDays(Math.abs(nowDate.getTime() - poDate.getTime()));
                
                if(daysDifference >= 7&&po.getStatus().equals("Pending")){
                    outdatedPOList.add(new String[]{po.getId(),dateString,po.getSupplierId(),String.valueOf(daysDifference)});
                }
            }catch(ParseException e){}
        }
        return outdatedPOList;
    }
}
