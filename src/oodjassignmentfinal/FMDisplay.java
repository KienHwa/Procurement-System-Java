package oodjassignmentfinal;

import java.util.*;

public class FMDisplay extends Display{
    public String homePage(){
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                           
                           ----------------------------------------------------------------------
                                                   Finance Manager Page
                           ----------------------------------------------------------------------""");
        FMNotification notification = new FMNotification();
        System.out.println(notification.getNotification());
        System.out.print("1.Notification\n2.Manage Purchase Order\n3.Manage payment\n4.Logout\nEnter your option:");
        return sc.nextLine();
    }
    
    public String managePODisplayPO(String filterPurchaseOrderID, String filterSubmitterID, String filterApproverID,String filterSupplierID, String filterStatus, String filterPaymentStatus, String filterDate){
        Scanner sc = new Scanner(System.in);
        
        PurchaseOrder poMethod = new PurchaseOrder();
        User userMethod = new User();
        Supplier supplierMethod = new Supplier();
        FMFilter filter = new FMFilter();
        
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        LinkedHashMap<String,User> userMap = userMethod.readUserMap();
        LinkedHashMap<String,Supplier> supplierMap = supplierMethod.readSupplierMap();
        
        System.out.printf("\nPurchase Order ID: %s | SubmitterID: %s | ApproverID: %s | SupplierID: %s | Status: %s | PaymentStatus: : %s | Date: %s\n", 
                filterPurchaseOrderID, filterSubmitterID, filterApproverID, filterSupplierID, filterStatus, filterPaymentStatus, filterDate);
        System.out.println("----------------------------------------------------------------------- Purchase Orders -----------------------------------------------------------------------");
        System.out.println("Purchase Order ID | SubmitterID (Name)           | ApproverID (Name)            | SupplierID (Name)           | Status   | Payment Status | Date");
        for(PurchaseOrder po : poList){
            boolean poIDLogic = filter.dataCompare(filterPurchaseOrderID, po.getId());
            boolean submitterIDLogic = filter.dataCompare(filterSubmitterID, po.getCreatorId());
            boolean approverIDLogic = filter.dataCompare(filterApproverID, po.getApproverId());
            boolean supplierIDLogic = filter.dataCompare(filterSupplierID, po.getSupplierId());
            boolean statusLogic = filter.dataCompare(filterStatus, po.getStatus());
            boolean paymentStatusLogic = filter.dataCompare(filterPaymentStatus, po.getPaymentStatus());
            boolean dateLogic = filter.dateCompare(filterDate, po.getDate());
            if(poIDLogic && submitterIDLogic && approverIDLogic && supplierIDLogic && statusLogic && paymentStatusLogic && dateLogic){
                String submitterName = "("+userMap.get(po.getCreatorId()).getName()+")";
                String approverName = po.getApproverId().equals("-") ? "" : "("+userMap.get(po.getApproverId()).getName()+")";
                String supplierName = "("+supplierMap.get(po.getSupplierId()).getName()+")";
                System.out.printf("%-17s | %-5s %-22s | %-5s %-22s | %-4s %-22s | %-8s | %-14s | %s\n", po.getId(), po.getCreatorId(), submitterName, po.getApproverId(), approverName, po.getSupplierId(), supplierName, po.getStatus(), po.getPaymentStatus(), po.getDate());
            }
        }
        System.out.print("\n1.Edit filter\n2.View purchase order\n3.Validate purchase order\n4.Return\nEnter your option: ");
        return sc.nextLine().trim();
    }
    
    public void managePODisplayPOI(PurchaseOrder po){
        PurchaseOrderItem poItemMethod = new PurchaseOrderItem();
        Item itemMethod = new Item();
        User userMethod = new User();
        Supplier supplierMethod = new Supplier();
        
        ArrayList<PurchaseOrderItem> oldPoItemList = poItemMethod.readPOItem();
        ArrayList<PurchaseOrderItem> poItemList = new ArrayList<>();
        LinkedHashMap<String,Item> itemMap = itemMethod.readItemMap();
        LinkedHashMap<String,User> userMap = userMethod.readUserMap();
        LinkedHashMap<String,Supplier> supplierMap = supplierMethod.readSupplierMap();
        
        for(PurchaseOrderItem poItem : oldPoItemList){
            if(po.getId().equals(poItem.getId())){
                poItemList.add(poItem);
            }
        }
        System.out.printf("\n%-40s%-40s\n%-40s%-40s\n%s","Purchase order ID: "+po.getId(),"Date: "+po.getDate(), "Created by: "+po.getCreatorId()+" ("+userMap.get(po.getCreatorId()).getName()+")", "Approve by: "+po.getApproverId()+ (po.getApproverId().equals("-") ? "" : " ("+userMap.get(po.getApproverId()).getName()+")"), "Supplier: "+po.getSupplierId()+" ("+supplierMap.get(po.getSupplierId()).getName()+")");
        System.out.println("\n-------------------------------Purchase Item List-------------------------------");
        System.out.println("ItemID | Item Name            | Quantity | Unit Price | Total   ");
        String totalPrice = "0";
        for(PurchaseOrderItem poItem : poItemList){
            String itemID = poItem.getItemId();
            String itemName = itemMap.get(itemID).getName();
            String quantity = poItem.getQuantity();
            String unitPrice = String.format("%.2f", Double.valueOf(poItem.getUnitPrice()));
            String totalUnitPrice = String.format("%.2f", Double.valueOf(quantity) * Double.valueOf(unitPrice));
            totalPrice = String.format("%.2f", Double.valueOf(totalPrice) + Double.valueOf(totalUnitPrice));
            System.out.printf("%-6s | %-20s | %-8s | RM%-8s | RM%s\n",itemID,itemName,quantity,unitPrice,totalUnitPrice);
        }
        System.out.printf("%-53s | RM%s\n", "", totalPrice);
    }
    
    public String validatePOOption(){
        Scanner sc = new Scanner(System.in);
        System.out.print("\nValidate purchase order options:\n1.Check potential of item\n2.Check balance\n3.Approve\n4.Reject\n5.Return\nEnter your option:");
        return sc.nextLine().trim();
    }
    
    public void poValidateDisplayItemPotential(Item itemDetail, String[] itemRankQuantity, double profit){
        Scanner sc = new Scanner(System.in);
        System.out.printf("\n----------Item Detail----------\nItemID: %s\nItem Name: %s\nSales quantity: %s\nSales rank: %s\nProfit: %.2f\n(Enter to return)\n",
        itemDetail.getId(),itemDetail.getName(),itemRankQuantity[1],itemRankQuantity[0],profit);
        sc.nextLine();
    }
    
    public void poValidateDisplayBalance(double balance, double debt, double balanceAfterDebtRepayment){
        Scanner sc = new Scanner(System.in);
        System.out.printf("\n---------Balance Detail---------\nBalance: RM%.2f\nDebt RM%.2f\nBalance after debt repayment: RM%.2f\n(Enter to return)\n",balance,debt,balanceAfterDebtRepayment);
        sc.nextLine();
    }
    
    public String managePaymentPODisplay(
            String filterPurchaseOrderID, String filterApproverID, String filterSupplierID, String filterStatus, String filterPaymentStatus, String filterDate, String filterTotalAmount
    ){
        Scanner sc = new Scanner(System.in);
        
        FMFilter filter = new FMFilter();
        PurchaseOrderItem poItemMethod = new PurchaseOrderItem();
        PurchaseOrder poMethod = new PurchaseOrder();
        User userMethod = new User();
        Supplier supplierMethod = new Supplier();
        Item itemMethod = new Item();
        
        ArrayList<PurchaseOrderItem> poItemList = poItemMethod.readPOItem();
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        LinkedHashMap<String, User> userMap = userMethod.readUserMap();
        LinkedHashMap<String, Supplier> supplierMap = supplierMethod.readSupplierMap();
        LinkedHashMap<String, Item> itemMap = itemMethod.readItemMap();
        
        System.out.printf("\nPurchase Order ID: %s | ApproverID: %s | SupplierID: %s | Status: %s | Payment Status: %s | Date: %s | Total Amount: %s ", 
                filterPurchaseOrderID, filterApproverID, filterSupplierID, filterStatus, filterPaymentStatus, filterDate, filterTotalAmount);
        System.out.println("\n--------------------------------------------------------------------------------------------Purchase Order--------------------------------------------------------------------------------------------");
        System.out.println("Purchase Order ID | ApproverID (Name)            | SupplierID (Name)           | Status   | Payment Status | Date                | ItemID (Item Name )           | Quantity | Unit Price | Total Price");
        for(PurchaseOrder po : poList){
            boolean poIDLogic = filter.dataCompare(filterPurchaseOrderID, po.getId());
            boolean approverIDLogic = filter.dataCompare(filterApproverID, po.getApproverId());
            boolean supplierIDLogic = filter.dataCompare(filterSupplierID, po.getSupplierId());
            boolean statusLogic = filter.dataCompare(filterStatus, po.getStatus());
            boolean paymentStatusLogic = filter.dataCompare(filterPaymentStatus, po.getPaymentStatus());
            boolean dateLogic = filter.dateCompare(filterDate, po.getDate());
            float totalAmount = 0;
            for(PurchaseOrderItem poItem : poItemList){
                totalAmount = poItem.getId().equals(po.getId()) ? totalAmount + (Float.parseFloat(poItem.getQuantity()) * Float.parseFloat(poItem.getUnitPrice())) : totalAmount;
            }
            boolean totalAmountLogic = filter.rangeCompare(filterTotalAmount, totalAmount);
            boolean paymentNotNone = !po.getPaymentStatus().equals("None");
            String lastPOID = "";
            if(poIDLogic && approverIDLogic && supplierIDLogic && statusLogic && paymentStatusLogic && dateLogic && totalAmountLogic && paymentNotNone){
                String poTotal = "0";
                for(PurchaseOrderItem poItem : poItemList){
                    if(poItem.getId().equals(po.getId())){
                        boolean samePOID = false;
                        if(poItem.getId().equals(lastPOID)){
                            samePOID = true;
                        }
                        else{
                            lastPOID = poItem.getId();
                        }
                        System.out.printf("%-17s | %-5s %-22s | %-4s %-22s | %-8s | %-14s | %-19s | %-6s %-22s | %-8s | %-10s | %-11s\n",
                                (samePOID ? "" : po.getId()), 
                                (samePOID ? "" : po.getApproverId()), (samePOID ? "" : "("+(userMap.get(po.getApproverId()).getName())+")"), 
                                (samePOID ? "" : po.getSupplierId()), (samePOID ? "" : "("+(supplierMap.get(po.getSupplierId()).getName())+")"), 
                                (samePOID ? "" : po.getStatus()),
                                (samePOID ? "" : po.getPaymentStatus()),
                                (samePOID ? "" : po.getDate()),
                                poItem.getItemId(), itemMap.get(poItem.getItemId()).getName(),
                                poItem.getQuantity(), "RM" + String.format("%.2f", Double.valueOf(poItem.getUnitPrice())),
                                "RM" + String.format("%.2f", (Double.valueOf(poItem.getQuantity()) * Double.valueOf(poItem.getUnitPrice()))));
                        poTotal = String.format("%.2f", (Double.parseDouble(poTotal)+(Double.valueOf(poItem.getQuantity()) * Double.valueOf(poItem.getUnitPrice()))));
                    }
                }
                String empty = "";
                System.out.printf("%-17s | %-5s %-22s | %-4s %-22s | %-8s | %-14s | %-19s | %-6s %-22s | %-8s | %-10s | %-11s\n",
                        empty,empty,empty,empty,empty,empty,empty,empty,empty,empty,empty,empty,"RM"+poTotal);
            }
        }
        System.out.print("\n1.Edit filter\n2.Make payment\n3.View Supplier Payment Status\n4.Return\nEnter your option: ");
        return sc.nextLine().trim();
    }
    
    public String managePaymentMakePaymentOption(){
        Scanner sc = new Scanner(System.in);
        System.out.print("\nMake payment option:\n1.Make payment\n2.Return\nEnter your option:");
        return sc.nextLine().trim();
    }
    
    public String managePaymentDisplayBalance(double balance, double totalAmount){
        Scanner sc = new Scanner(System.in);
        System.out.printf("\nBalance:RM%.2f\nTotal Amount:RM%.2f\nBalance Left:RM%.2f\n\nAre you sure you want to proceed with the payment?(Yes/No):", balance, totalAmount, (balance - totalAmount));
        return sc.nextLine().trim();
    }
    
    public void managePaymentDisplaySupplierList(){
        Supplier supplierMethod = new Supplier();
        ArrayList<Supplier> supplierList = supplierMethod.readSupplier();
        System.out.println("\n------------Supplier List------------\nSupplier ID(Supplier Name)");
        for(Supplier supplier : supplierList){
            System.out.printf("%-11s(%s)\n", supplier.getId(), supplier.getName());
        }
    }
    
    public void managePaymentDisplaySupplierDetail(Supplier supplier){
        System.out.printf("\nSupplierID: %s\nSupplier Name: %s\nContact No: %s\nAddress: %s\n-------------------------------------\n",supplier.getId(),supplier.getName(),supplier.getContactNo(),supplier.getAddress());
    }
    
    public void managePaymentDisplaySupplierStatus(String supplierID){
        PurchaseOrder poMethod = new PurchaseOrder();
        PurchaseOrderItem poItemMethod = new PurchaseOrderItem();
        
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        ArrayList<PurchaseOrderItem> poItemList = poItemMethod.readPOItem();
        
        int poPaid = 0;
        int poDebt = 0;
        double totalPaid = 0;
        double totalDebt = 0;
        
        for(PurchaseOrder po : poList){
            if(po.getSupplierId().equals(supplierID)&&po.getPaymentStatus().equals("Paid")){
                poPaid++;
                for(PurchaseOrderItem poItem : poItemList){
                    if(poItem.getId().equals(po.getId())){
                        totalPaid += Double.parseDouble(poItem.getQuantity())*Double.parseDouble(poItem.getUnitPrice());
                    }
                }
            }
            else if(po.getSupplierId().equals(supplierID)&&po.getPaymentStatus().equals("Pending")){
                poDebt++;
                for(PurchaseOrderItem poItem : poItemList){
                    if(poItem.getId().equals(po.getId())){
                        totalDebt += Double.parseDouble(poItem.getQuantity())*Double.parseDouble(poItem.getUnitPrice());
                    }
                }
            }
        }
        
        System.out.printf("Total amount of purchase order: %d\nAmount of paid purchase order: %d\nTotal paid: RM%.2f\nAmount of pending purchase order: %d\nTotal debt: RM%.2f\n",poPaid+poDebt,poPaid,totalPaid,poDebt,totalDebt);
    }
    
    public void managePaymentDisplayPaymentHistory(String supplierId){
        Payment paymentMethod = new Payment();
        PurchaseOrderItem poItemMethod = new PurchaseOrderItem();
        PurchaseOrder poMethod = new PurchaseOrder();
        
        ArrayList<Payment> paymentList = paymentMethod.readPayment();
        ArrayList<PurchaseOrderItem> poItemList = poItemMethod.readPOItem();
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        ArrayList<String[]> paymentHistoryList = new ArrayList<>();
        
        for(Payment payment : paymentList){
            for(PurchaseOrder po : poList){
                if(po.getId().equals(payment.getId())&&po.getSupplierId().equals(supplierId)){
                    String poPaymentAmount = "0";
                    for(PurchaseOrderItem poItem : poItemList){
                        if(poItem.getId().equals(po.getId())){
                            poPaymentAmount = String.valueOf(Double.parseDouble(poPaymentAmount) + (Double.parseDouble(poItem.getQuantity()) * Double.parseDouble(poItem.getUnitPrice())));
                        }
                    }
                    paymentHistoryList.add(new String[]{po.getId(),payment.getDate(),poPaymentAmount});
                }
            }
        }
        System.out.println("\n-----------Payment History-----------");
        if(paymentHistoryList.size() <= 0){
            System.out.println(" <<There is no payment history>> ");
        }else{
            System.out.println("POID  | Date       | Amount      ");
            for(String[] paymentHistory : paymentHistoryList){
                System.out.printf("%-5s | %-10s | RM%.2f\n", paymentHistory[0], paymentHistory[1], Double.parseDouble(paymentHistory[2]));
            }
        }
    }
}
