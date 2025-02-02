package oodjassignmentfinal;

import java.util.*;
import oodjassignmentfinal.User;

public class FinanceManager extends User{
    public FinanceManager(){}
    
    public FinanceManager(String id){
        this.id = id;
    }
    
    public void fmMenu(){
        while(true){
            FMDisplay display = new FMDisplay();
            String option = display.homePage();
            switch(option){
                case "1" -> {
                    FMNotification notification = new FMNotification();
                    notification.viewNotification();
                }
                case "2" -> {
                    managePurchaseOrder();
                }
                case "3" -> {
                    managePayment();
                }
                case "4" -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }
    
//Manage Purchase Order Part
    
    private void managePurchaseOrder(){
        Scanner sc = new Scanner(System.in);
        FMDisplay display = new FMDisplay();
        
        String filterPurchaseOrderID = "All";
        String filterSubmitterID = "All";
        String filterApproverID = "All";
        String filterSupplierID = "All";
        String filterStatus = "All";
        String filterPaymentStatus = "All";
        String filterDate = "XX-XX-XXXX";
        while(true){
            String option = display.managePODisplayPO(filterPurchaseOrderID,filterSubmitterID,filterApproverID,filterSupplierID,filterStatus,filterPaymentStatus,filterDate);
            switch(option){
                case "1" ->{
                    FMFilter filter = new FMFilter();
                    
                    ArrayList<String> filterEdit = filter.managePurchaseOrderFilter(filterPurchaseOrderID, filterSubmitterID, filterApproverID, filterSupplierID, filterStatus, filterPaymentStatus, filterDate);
                    filterPurchaseOrderID = filterEdit.get(0);
                    filterSubmitterID = filterEdit.get(1);
                    filterApproverID = filterEdit.get(2);
                    filterSupplierID = filterEdit.get(3);
                    filterStatus = filterEdit.get(4);
                    filterPaymentStatus = filterEdit.get(5);
                    filterDate = filterEdit.get(6);
                }
                case "2" ->{
                    PurchaseOrder poMethod = new PurchaseOrder();
                    
                    ArrayList<PurchaseOrder> poList = poMethod.readPO();
                    while(true){
                        System.out.print("\nEnter Purchase Order ID(Enter 'Return' to return): ");
                        String input = sc.nextLine();
                        if(input.equals("Return")){
                            break;
                        }
                        
                        boolean poFound = false;
                        for(PurchaseOrder po : poList){
                            if(po.getId().equals(input)){
                                display.managePODisplayPOI(po);
                                poFound = true;
                                System.out.print("\n(Enter to return)");
                                sc.nextLine();
                            }
                        }
                        
                        if(!poFound){
                            System.out.println("Invalid purchase order. Please try again.");
                        }
                    }
                }
                case "3" ->{
                    boolean escapeLoop = false;
                    while(!escapeLoop){
                        PurchaseOrder poMethod = new PurchaseOrder();
                        ArrayList<PurchaseOrder> poList = poMethod.readPO();
                        System.out.print("\nEnter Purchase Order ID(Enter 'Return' to return): ");
                        String input = sc.nextLine();
                        if(input.equals("Return")){
                            break;
                        }
                        
                        boolean poFound = false;
                        for(PurchaseOrder po : poList){
                            if(po.getId().equals(input) && po.getStatus().equals("Pending")){
                                escapeLoop = poValidate(po);
                                poFound = true;
                            }
                        }
                        
                        if(!poFound){
                            System.out.println("Invalid purchase order. Please try again.");
                        }
                    }
                }
                case "4" ->{
                    return;
                }
                default ->{
                    System.out.println("\nInvalid option. Please try again.\n");
                }
            }
        }
    }
    
    private boolean poValidate(PurchaseOrder po){
        FMDisplay display = new FMDisplay();
        PurchaseOrderItem poItemMethod = new PurchaseOrderItem();
        Scanner sc = new Scanner(System.in);
        
        while(true){
            display.managePODisplayPOI(po);
            ArrayList<PurchaseOrderItem> oldPoItemList = poItemMethod.readPOItem();
            ArrayList<PurchaseOrderItem> poItemList = new ArrayList<>();
            for(PurchaseOrderItem poItem : oldPoItemList){
                if(po.getId().equals(poItem.getId())){
                    poItemList.add(poItem);
                }
            }
            String option = display.validatePOOption();
            switch(option){
                case "1" ->{
                    while(true){
                        System.out.print("Insert the ItemID ('Return' to return):");
                        String checkItemID = sc.nextLine();
                        if(checkItemID.equals("Return")||checkItemID.equals("return")){
                            break;
                        }
                        else{
                            boolean existItemID = false;
                            Item itemMethod = new Item();
                            for(PurchaseOrderItem poItem : poItemList){
                                existItemID = poItem.getItemId().equals(checkItemID) ? true : existItemID;
                            }
                            if(existItemID){
                                String[] itemRankQuantity = getItemRankQuantity(checkItemID);
                                double profit = getItemProfit(checkItemID);
                                HashMap<String,Item> itemMap = itemMethod.readItemMap();
                                Item itemDetail = itemMap.get(checkItemID);
                                display.poValidateDisplayItemPotential(itemDetail, itemRankQuantity, profit);
                            }
                            else{
                                System.out.println("Incorrect itemID. Please try again.");
                            }
                        }
                    }
                }
                case "2" ->{
                    Balance balanceMethod = new Balance();
                    PurchaseOrder poMethod = new PurchaseOrder();
                    
                    double balance = balanceMethod.getBalance();
                    ArrayList<PurchaseOrder> poList = poMethod.readPO();
                    ArrayList<PurchaseOrderItem> poItemsList = poItemMethod.readPOItem();
                    double debt = 0;
                    for(PurchaseOrder purchaseOrder : poList){
                        if(purchaseOrder.getPaymentStatus().equals("Pending")){
                            for(PurchaseOrderItem purchaseOrderItem : poItemsList){
                                debt += purchaseOrderItem.getId().equals(purchaseOrder.getId()) ? Double.parseDouble(purchaseOrderItem.getQuantity())*Double.parseDouble(purchaseOrderItem.getUnitPrice()) : 0;
                            }
                        }
                    }
                    double balanceAfterDebtRepayment = balance - debt;
                    display.poValidateDisplayBalance(balance, debt, balanceAfterDebtRepayment);
                }
                case "3" ->{
                    validatePurchaseOrder(po.getId(), "Approved");
                    addItemOrdered(po.getId());
                    System.out.println("Purchase order has been approved.");
                    return false;
                }
                case "4" ->{
                    validatePurchaseOrder(po.getId(), "Rejected");
                    System.out.println("Purchase order has been rejected.");
                    return false;
                }
                case "5" ->{
                    return true;
                }
                default ->{
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }
    
    private String[] getItemRankQuantity(String itemID){
        int rank = 0;
        int lastQuantity = -1;
        String itemQuantity = "0";
        List<String[]> itemSalesRank = getItemSalesRankList();
        String itemRank = null;
        
        for(String[] itemSales : itemSalesRank){
            rank = (Integer.parseInt(itemSales[1])) == lastQuantity ? rank : rank+1;
            itemRank = itemSales[0].equals(itemID) ? Integer.toString(rank) : itemRank;
            itemQuantity = itemSales[0].equals(itemID) ? itemSales[1] : itemQuantity;
            lastQuantity = Integer.parseInt(itemSales[1]);
        }
        return new String[]{itemRank,itemQuantity};
    }
    
    protected List<String[]> getItemSalesRankList(){
        Item itemMethod = new Item();
        ItemsSold itemsSoldMethod = new ItemsSold();
        
        ArrayList<Item> itemList = itemMethod.readItem();
        ArrayList<ItemsSold> itemSoldList = itemsSoldMethod.readItemsSold();
        List<String[]> itemSalesRank = new ArrayList<>();
        for(Item item : itemList){
            int count = 0;
            for(ItemsSold itemSold : itemSoldList){
                count = itemSold.getItemID().equals(item.getId()) ? count+(Integer.parseInt(itemSold.getQuantity())) : count;
            }
            itemSalesRank.add(new String[]{item.getId(),Integer.toString(count)});
        }
        itemSalesRank = itemSalesRank.stream().sorted((item1, item2) -> Integer.compare(Integer.parseInt(item2[1]), Integer.parseInt(item1[1]))).toList();
        
        return itemSalesRank;
    }
    
    private double getItemProfit(String itemID){
        PurchaseOrderItem poItemsMethod = new PurchaseOrderItem();
        ItemsSold itemsSoldMethod = new ItemsSold();
        
        ArrayList<PurchaseOrderItem> poItemList = poItemsMethod.readPOItem();
        ArrayList<ItemsSold> itemsSoldList = itemsSoldMethod.readItemsSold();
        double costPrice = 0;
        double sellingPrice = 0;
        
        for(PurchaseOrderItem poItem : poItemList){
            costPrice = poItem.getItemId().equals(itemID) ? costPrice + (Double.parseDouble(poItem.getQuantity())*Double.parseDouble(poItem.getUnitPrice())) : costPrice;
        }
        for(ItemsSold itemsSold : itemsSoldList){
            sellingPrice = itemsSold.getItemID().equals(itemID) ? sellingPrice + (Double.parseDouble(itemsSold.getQuantity())*Double.parseDouble(itemsSold.getUnitPrice())) : sellingPrice;
        }
        return sellingPrice - costPrice;
    }
    
    private void validatePurchaseOrder(String currentPOID, String status){
        PurchaseOrder poMethod = new PurchaseOrder();
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        for(PurchaseOrder po : poList){
            if(currentPOID.equals(po.getId())){
                System.out.println("Updated");
                po.setApproverId(this.id);
                po.setStatus(status);
                po.setPaymentStatus(status.equals("Approved")?"Pending":po.getPaymentStatus());
                poMethod.updateFile(po);
                break;
            }
        }
    }
    
    private void addItemOrdered(String currentPOID){
        PurchaseOrderItem poItemsMethod = new PurchaseOrderItem();
        ItemsOrdered itemsOrderedMethod = new ItemsOrdered();
        
        ArrayList<PurchaseOrderItem> poItemsList = poItemsMethod.readPOItem();
        for(PurchaseOrderItem poItem : poItemsList){
            if(poItem.getId().equals(currentPOID)){
                String[] itemsOrderedInfo = new String[]{poItem.getId(),poItem.getItemId(),poItem.getQuantity()};
                itemsOrderedMethod.createFile(itemsOrderedInfo);
            }
        }
    }
    
//Manage Payment Part
    private void managePayment(){
        Scanner sc = new Scanner(System.in);
        FMDisplay display = new FMDisplay();
        FMFilter filter = new FMFilter();
        
        String filterPurchaseOrderID = "All";
        String filterApproverID = "All";
        String filterSupplierID = "All";
        String filterStatus = "All";
        String filterPaymentStatus = "All";
        String filterDate = "XX-XX-XXXX";
        String filterTotalAmount = "All";
        while(true){
            String option = display.managePaymentPODisplay(filterPurchaseOrderID, filterApproverID, filterSupplierID, filterStatus, filterPaymentStatus, filterDate, filterTotalAmount);
            switch(option){
                case "1" ->{
                    ArrayList<String> filterEdit = filter.managePaymentPaymentFilter(filterPurchaseOrderID, filterApproverID, filterSupplierID, filterStatus, filterPaymentStatus, filterDate, filterTotalAmount);
                    filterPurchaseOrderID = filterEdit.get(0);
                    filterApproverID = filterEdit.get(1);
                    filterSupplierID = filterEdit.get(2);
                    filterStatus = filterEdit.get(3);
                    filterPaymentStatus = filterEdit.get(4);
                    filterDate = filterEdit.get(5);
                    filterTotalAmount = filterEdit.get(6);
                }
                case "2" ->{
                    PurchaseOrder poMethod = new PurchaseOrder();
                    
                    ArrayList<PurchaseOrder> poList = poMethod.readPO();
                    while(true){
                        System.out.print("\nEnter Purchase Order ID(Enter 'Return' to return): ");
                        String input = sc.nextLine();
                        if(input.equals("Return")){
                            break;
                        }
                        
                        boolean poFound = false;
                        for(PurchaseOrder po : poList){
                            if(po.getId().equals(input)){
                                poFound = true;
                                if(po.getApproverId().equals("None")){
                                    System.out.println("This purchase order hasn't been approved yet.");
                                    break;
                                }
                                if(po.getPaymentStatus().equals("Paid")){
                                    System.out.println("This purchase order's payment has been made.");
                                    break;
                                }
                                makePayment(po);
                            }
                        }
                        
                        if(!poFound){
                            System.out.println("Invalid purchase order. Please try again.");
                        }
                    }
                }
                case "3" ->{
                    viewSupplierPaymentStatus();
                }
                case "4" ->{
                    return;
                }
                default ->{
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }
    
    private void makePayment(PurchaseOrder po){
        FMDisplay display = new FMDisplay();
        
        display.managePODisplayPOI(po);
        while(true){
            String option = display.managePaymentMakePaymentOption();
            switch(option){
                case "1" ->{
                    PurchaseOrderItem poItemMethod = new PurchaseOrderItem();
                    PurchaseOrder poMethod = new PurchaseOrder();
                    Balance balanceMethod = new Balance();
                    Payment paymentMethod = new Payment();
                    
                    ArrayList<PurchaseOrderItem> poItemList = poItemMethod.readPOItem();
                    double balance = balanceMethod.getBalance();
                    double totalAmount = 0;
                    for(PurchaseOrderItem poItem : poItemList){
                        totalAmount = poItem.getId().equals(po.getId()) ? totalAmount + (Double.valueOf(poItem.getQuantity()) * Double.valueOf(poItem.getUnitPrice())) : totalAmount;
                    }
                    String confirmation = display.managePaymentDisplayBalance(balance, totalAmount);
                    if(confirmation.equalsIgnoreCase("yes")){
                        po.setPaymentStatus("Paid");
                        poMethod.updateFile(po);
                        balanceMethod.updateBalance(String.valueOf(balance - totalAmount));
                        Payment payment = new Payment(po.getId());
                        paymentMethod.addPayment(payment);
                        System.out.println("Payment has been made successfully.");
                    }
                    else{
                        System.out.println("Payment has been cancelled.");
                    }
                    return;
                }
                case "2" ->{
                    return;
                }
                default ->{
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }
    
    private void viewSupplierPaymentStatus(){
        Scanner sc = new Scanner(System.in);
        FMDisplay display = new FMDisplay();
        Supplier supplierMethod = new Supplier();
        
        ArrayList<Supplier> supplierList = supplierMethod.readSupplier();
        
        display.managePaymentDisplaySupplierList();
        while(true){
            System.out.print("\nEnter the supplierID('Return' to return): ");
            String input = sc.nextLine();
            boolean supplierFound = false;
            if(input.equals("Return")){
                break;
            }
            for(Supplier supplier : supplierList){
               if(supplier.getId().equals(input)){
                   supplierFound = true;
                   display.managePaymentDisplaySupplierDetail(supplier);
                   display.managePaymentDisplaySupplierStatus(supplier.getId());
                   display.managePaymentDisplayPaymentHistory(supplier.getId());
                   System.out.print("\n(Enter to continue)");
                   sc.nextLine();
               }
            }
            if(!supplierFound){
                System.out.println("Supplier not found. Please try again.");
            }
        }
    }
}
