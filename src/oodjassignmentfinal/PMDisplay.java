package oodjassignmentfinal;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PMDisplay extends Display{
    @Override
    public String homePage(){
        System.out.println("\n------------------------------\n     Purchase Manager Page\n------------------------------");
        System.out.println("1.List of Items (View)\n2.List of Suppliers (View)\n3.Create and View Requisition created by SM\n4.View List and Generate Purchase Order\n5.Log Out");
        return null;
    }
    
    public boolean displayItem(boolean checkItem, String filterItemID, String filterItemName, String filterItemSellingPrice, String filterstocklevel){
        Item itemMethod = new Item();
        ArrayList<Item> itemList = itemMethod.readItem();
        
        if (!checkItem){
            System.out.println("\nCurrent Filter: Item ID: "+filterItemID+" | Item Name: "+filterItemName+" | Item Selling Price: "+filterItemSellingPrice+" | Stock Level: "+filterstocklevel);
            System.out.println("----------------------------------------- List of Items --------------------------------------------");
            System.out.println("| Item ID    | Item Name          | Item Selling Price | Stock Level | Reorder Level | Updated Date |");
            System.out.println("----------------------------------------------------------------------------------------------------");
        }

        boolean recordFound = false;
        for (Item item : itemList){
            String itemID = item.getId();
            String itemName = item.getName();
            String itemSellingPrice = item.getSellingPrice();
            String stockLevel = item.getStockLevel();
            boolean matchesID = filterItemID.equals("All") || itemID.equalsIgnoreCase(filterItemID);            
            boolean matchesName = filterItemName.equals("All") || itemName.equalsIgnoreCase(filterItemName);
            boolean matchesSellingPrice = filterItemSellingPrice.equals("All") || itemSellingPrice.equalsIgnoreCase(filterItemSellingPrice);
            boolean matchesStockLevel = filterstocklevel.equals("All") || stockLevel.equalsIgnoreCase(filterstocklevel);
            if (!checkItem && matchesID && matchesName && matchesSellingPrice && matchesStockLevel){
                System.out.printf("| %-10s | %-18s | %-18s | %-11s | %-13s | %-12s |\n",itemID,itemName,itemSellingPrice,stockLevel,item.getReorderLevel(), item.getUpdateDate());    
                recordFound = true;
            }
            else if (checkItem && matchesID && matchesName && matchesSellingPrice && matchesStockLevel){
                System.out.println("Item Chosen:");   
                System.out.println("Item Code: "+itemID+"\nItem Name: "+itemName+"\nItem Selling Price: "+itemSellingPrice+"\nItem Current Stock Level: "+stockLevel+"\nItem Reorder Level: "+item.getReorderLevel()+"\nItem Updated Date: "+item.getUpdateDate()); 
                recordFound = true;
                return true;
            }
        }
        if (!recordFound){
            System.out.println("Item is not found.");
            return false;
        }
        return false;
    }
    
    public boolean displaySuppliers(String filterSupplierID, String filterSupplierName, String filterContactNumber, String filterAddress){
        Supplier supplierMethod = new Supplier();
        ArrayList<Supplier> supplierList = supplierMethod.readSupplier();
        
        System.out.println("\nCurrent Filter: Supplier ID: "+filterSupplierID+" | Supplier Name: "+filterSupplierName+" | Contact Number: "+filterContactNumber+" | Address: "+filterAddress);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------"); //65
        System.out.println("                                                                     List of Suppliers"); //17
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("| Supplier ID | Supplier Name        | Contact Number | Address ");

        boolean recordFound = false;
        for (Supplier supplier : supplierList){
            String supplierID = supplier.getId();
            String supplierName = supplier.getName();
            String contactNumber = supplier.getContactNo();
            String address = supplier.getAddress();
            boolean matchesID = filterSupplierID.equals("All") || supplierID.equalsIgnoreCase(filterSupplierID);            
            boolean matchesName = filterSupplierName.equals("All") || supplierName.equalsIgnoreCase(filterSupplierName);
            boolean matchesContactNumber = filterContactNumber.equals("All") || contactNumber.equalsIgnoreCase(filterContactNumber);
            boolean matchesAddress = filterAddress.equals("All") || address.equalsIgnoreCase(filterAddress);
            
            if (matchesID && matchesName && matchesContactNumber && matchesAddress){
                System.out.printf("| %-11s | %-20s | %-14s | %s \n",supplierID, supplierName, contactNumber, address);    
                recordFound = true;
            }
        }
        if (!recordFound){
            System.out.println("Supplier List is not found.");
            return false;
        }
        return false;
    }
    
    public boolean displayRequisition(boolean checkDate, boolean dltReq, String filterReqID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverName, String filterSupplierID, String filterSupplierName, String filterItemID, String filterItemName, String filterQuantity, String filterStatus, String filterDateTime){
        User userMethod = new User();
        PurchaseManager pm = new PurchaseManager();
        Item itemMethod = new Item();
        Supplier supplierMethod = new Supplier();
        PurchaseRequisition prMethod = new PurchaseRequisition();
        PMFilter f = new PMFilter();
        
        ArrayList<PurchaseRequisition> purchaseRequisitionList = prMethod.readPR();
        LinkedHashMap<String, Item> itemListMap = itemMethod.readItemMap();
        LinkedHashMap<String, Supplier> supplierListMap = supplierMethod.readSupplierMap();
        LinkedHashMap<String, String[]> userListMap = userMethod.readUserPR();
        //String filterReqID = "All", filterUsername = "All", filterApproverName = "All", filterSupplierName = "All", filterItemName = "All", filterQuantity = "All", filterStatus = "All", filterDateTime = "XX-XX-XXXX";
        
        if ((!checkDate && !dltReq) || (checkDate && !dltReq)){
            System.out.println("\nCurrent Filter: Requisition ID: "+filterReqID+" | Submitter ID: "+filterSubmitterID+" | Submitter Username: "+filterSubmitterUsername+" | Approval ID: "+filterApproverID+" | Approval Username: "+filterApproverName+" | Supplier ID: "+filterSupplierID+" | Supplier Name: "+filterSupplierName+" | Item ID: "+filterItemID+" | Item Name: "+filterItemName+" | Quantity: "+filterQuantity+" | Status: "+filterStatus+" | Date: "+filterDateTime);
            System.out.println("------------------------------------------------------------------------- Purchase Requisition List ---------------------------------------------------------------------------------------");
            System.out.println("| Requisition ID | Submitter ID (Username)      | Approver ID (Username)       | Supplier ID (Name)            | Item ID (Item Name)         | Quantity | Status    | Date and Time       |");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
         
        boolean recordFound = false;
        boolean matchesStatus = false;
        boolean matchesDateTime = false;
        for (PurchaseRequisition requisition : purchaseRequisitionList){
            String requisitionID = requisition.getId();  
            String submitterID = requisition.getSubmitterID();
            String approverID = requisition.getApproverID();
            String supplierID = requisition.getSupplierID();
            String itemID = requisition.getItemID();
            String quantity = requisition.getQuantity();
            String status = requisition.getStatus();
            String dateTime = requisition.getDate();
            
            String submitterName = userListMap.containsKey(submitterID) ? userListMap.get(submitterID)[5] : "Unknown";
            String approverName = userListMap.containsKey(approverID) ? userListMap.get(approverID)[5] : "Unknown";
            String supplierName = supplierListMap.containsKey(supplierID) ? supplierListMap.get(supplierID).getName() : "Unknown";
            String itemName = itemListMap.containsKey(itemID) ? itemListMap.get(itemID).getName() : "Unknown";
            
            boolean matchesReqID = f.dataCompare(filterReqID, requisitionID);
            boolean matchesSubmitterID = f.dataCompare(filterSubmitterID, submitterID);
            boolean matchesSubmitterUsername = f.dataCompare(filterSubmitterUsername, submitterName);
            boolean matchesApproverID = f.dataCompare(filterApproverID, approverID);
            boolean matchesApproverName = f.dataCompare(filterApproverName, approverName);
            boolean matchesSupplierID = f.dataCompare(filterSupplierID, supplierID);
            boolean matchesSupplierName = f.dataCompare(filterSupplierName, supplierName);
            boolean matchesItemID = f.dataCompare(filterItemID, itemID);
            boolean matchesItemName = f.dataCompare(filterItemName, itemName);
            boolean matchesQuantity = f.dataCompare(filterQuantity, quantity);
            
            if (!checkDate){    // normal checking
                matchesDateTime = f.dateCompare(filterDateTime, dateTime);
                matchesStatus = f.dataCompare(filterStatus, status);
            }
            else if (checkDate){    // when dlt req, check date if its within 2 hrs and status is not "Processed"
                matchesDateTime = pm.withinTwoHours(dateTime);
                if (status.equalsIgnoreCase("processed")){
                    matchesStatus = false;
                }
                else{
                    matchesStatus = f.dataCompare(filterStatus, status);
                }
            }
            
            if (!checkDate && !dltReq && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                System.out.printf("| %-14s | %-5s (%-20s) | %-5s (%-20s) | %-6s (%-20s) | %-4s (%-20s) | %-8s | %-9s | %-19s |\n", requisitionID, submitterID, submitterName, approverID, approverName, supplierID, supplierName, itemID, itemName, quantity, status, dateTime);
                recordFound = true;
            }
            else if (checkDate && !dltReq && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                System.out.printf("| %-14s | %-5s (%-20s) | %-5s (%-20s) | %-6s (%-20s) | %-4s (%-20s) | %-8s | %-9s | %-19s |\n", requisitionID, submitterID, submitterName, approverID, approverName, supplierID, supplierName, itemID, itemName, quantity, status, dateTime);
                recordFound = true;
            }
            else if (checkDate && dltReq && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                System.out.println("\nPurchase Requisition to delete chosen: ");
                System.out.println("Requisition ID: "+requisitionID+"\nSubmitter ID & Name: "+submitterID+" ("+submitterName+")\nApprover ID & Name: "+approverID+" ("+approverName+")\nSupplier ID & Name: "+supplierID+" ("+supplierName+")"
                                + "\nItem ID & Name: "+itemID+" ("+itemName+")\nQuantity: "+quantity+"\nStatus: "+status+"\nDate & Time: "+dateTime);
                recordFound = true;
                return true;
            }
        }
        if (!recordFound){
            System.out.println("Purchase Requisition is not found.");
            return false;
        }
        
        return true;
    }
    
    public boolean displayRequisition(boolean display, boolean checkReqExists, String requisitionMode, ArrayList<String> chosenReqID, String filterReqID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverName, String filterSupplierID, String filterSupplierName, String filterItemID, String filterItemName, String filterQuantity, String filterStatus, String filterDateTime){
        PurchaseManager pmMethod = new PurchaseManager();
        User userMethod = new User();
        PurchaseRequisition prMethod = new PurchaseRequisition();
        Item itemMethod = new Item();
        Supplier supplierMethod = new Supplier();
        PMFilter f = new PMFilter();
        ArrayList<PurchaseRequisition> purchaseRequisitionList = prMethod.readPR();
        LinkedHashMap<String, Item> itemListMap = itemMethod.readItemMap();
        LinkedHashMap<String, Supplier> supplierListMap = supplierMethod.readSupplierMap();
        LinkedHashMap<String, String[]> userListMap = userMethod.readUserPR();
        //String filterReqID = "All", filterUsername = "All", filterApproverName = "All", filterSupplierName = "All", filterItemName = "All", filterQuantity = "All", filterStatus = "All", filterDateTime = "XX-XX-XXXX";
        
        if (display){
            if (requisitionMode.equals("display")){
                System.out.println("Current Filter: Requisition ID: "+filterReqID+" | Submitter ID: "+filterSubmitterID+" | Submitter Username: "+filterSubmitterUsername+" | Approval ID: "+filterApproverID+" | Approval Username: "+filterApproverName+" | Supplier ID: "+filterSupplierID+" | Supplier Name: "+filterSupplierName+" | Item ID: "+filterItemID+" | Item Name: "+filterItemName+" | Quantity: "+filterQuantity+" | Status: "+filterStatus+" | Date: "+filterDateTime);            
            }
            System.out.println("------------------------------------------------------------------------- Purchase Requisition List ---------------------------------------------------------------------------------------");
            System.out.println("| Requisition ID | Submitter ID (Username)      | Approver ID (Username)       | Supplier ID (Name)            | Item ID (Item Name)         | Quantity | Status    | Date and Time       |");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
         
        boolean recordFound = false;
        for (PurchaseRequisition requisition : purchaseRequisitionList){
            String requisitionID = requisition.getId();  
            String submitterID = requisition.getSubmitterID();
            String approverID = requisition.getApproverID();
            String supplierID = requisition.getSupplierID();
            String itemID = requisition.getItemID();
            String quantity = requisition.getQuantity();
            String status = requisition.getStatus();
            String dateTime = requisition.getDate();
            
            String submitterName = userListMap.containsKey(submitterID) ? userListMap.get(submitterID)[5] : "Unknown";
            String approverName = userListMap.containsKey(approverID) ? userListMap.get(approverID)[5] : "Unknown";
            String supplierName = supplierListMap.containsKey(supplierID) ? supplierListMap.get(supplierID).getName() : "Unknown";
            String itemName = itemListMap.containsKey(itemID) ? itemListMap.get(itemID).getName() : "Unknown";
            
            boolean reqIDNotChosen = true;  
            for (String reqID : chosenReqID){
                if (reqID.equals(requisitionID)){
                    reqIDNotChosen = false;
                    break;
                }
            }
            boolean matchesReqID = f.dataCompare(filterReqID, requisitionID);
            boolean matchesSubmitterID = f.dataCompare(filterSubmitterID, submitterID);
            boolean matchesSubmitterUsername = f.dataCompare(filterSubmitterUsername, submitterName);
            boolean matchesApproverID = f.dataCompare(filterApproverID, approverID);
            boolean matchesApproverName = f.dataCompare(filterApproverName, approverName);
            boolean matchesSupplierID = f.dataCompare(filterSupplierID, supplierID);
            boolean matchesSupplierName = f.dataCompare(filterSupplierName, supplierName);
            boolean matchesItemID = f.dataCompare(filterItemID, itemID);
            boolean matchesItemName = f.dataCompare(filterItemName, itemName);
            boolean matchesQuantity = f.dataCompare(filterQuantity, quantity);
            boolean matchesStatus = status.equals("Approved"); 
            
            boolean matchesDateTime;    // check date only for creating po choosing after two hours req.
            if (display && !checkReqExists && reqIDNotChosen && requisitionMode.equals("display")){
                matchesDateTime = pmMethod.afterTwoHours(dateTime);
            }
            else{
                matchesDateTime = f.dateCompare(filterDateTime, dateTime);
            }
                     
            if (display && !checkReqExists && reqIDNotChosen && requisitionMode.equals("display") && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                System.out.printf("| %-14s | %-5s (%-20s) | %-5s (%-20s) | %-6s (%-20s) | %-4s (%-20s) | %-8s | %-9s | %-19s |\n", requisitionID, submitterID, submitterName, approverID, approverName, supplierID, supplierName, itemID, itemName, quantity, status, dateTime);
                recordFound = true;
            }
            else if (display && !checkReqExists && !reqIDNotChosen && requisitionMode.equals("displayChosenReqID") && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                System.out.printf("| %-14s | %-5s (%-20s) | %-5s (%-20s) | %-6s (%-20s) | %-4s (%-20s) | %-8s | %-9s | %-19s |\n", requisitionID, submitterID, submitterName, approverID, approverName, supplierID, supplierName, itemID, itemName, quantity, status, dateTime);
                recordFound = true;
            }
            else if (!display && checkReqExists && requisitionMode.equals("input") && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                if (reqIDNotChosen){    // if reqID not chosen before, then true so can choose 
                    System.out.println("\nPurchase Requisition chosen: ");
                    System.out.println("Requisition ID: "+requisitionID+"\nSubmitter ID & Name: "+submitterID+" ("+submitterName+")\nApprover ID & Name: "+approverID+" ("+approverName+")\nSupplier ID & Name: "+supplierID+" ("+supplierName+")"
                                    + "\nItem ID & Name: "+itemID+" ("+itemName+")\nQuantity: "+quantity+"\nStatus: "+status+"\nDate & Time: "+dateTime);
                    recordFound = true;
                    return true;
                }
                else if (!reqIDNotChosen){  // if reqID chosen before, then false because choose before de you stil wan overlap???
                    System.out.println("\nPurchase Requisition has been already chosen, please choose another requisition to input!");
                    return false;
                }
            }
            else if (!display && checkReqExists && requisitionMode.equals("delete") && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                if (!reqIDNotChosen){   // if reqID in arrayList, delete
                    System.out.println("\nPurchase Requisition chosen to delete: ");
                    System.out.println("Requisition ID: "+requisitionID+"\nSubmitter ID & Name: "+submitterID+" ("+submitterName+")\nApprover ID & Name: "+approverID+" ("+approverName+")\nSupplier ID & Name: "+supplierID+" ("+supplierName+")"
                                    + "\nItem ID & Name: "+itemID+" ("+itemName+")\nQuantity: "+quantity+"\nStatus: "+status+"\nDate & Time: "+dateTime);
                    recordFound = true;
                    return true;
                }
                else if (reqIDNotChosen){   // if reqID not in arrayList, how to delete you dumb
                    System.out.println("\nPurchase Requisition is not in the chosen requisition list, please choose another requisition to remove!");
                    return false;
                }
            }
        }
        if (!recordFound){
            if(requisitionMode.equals("display")){
                System.out.println("Available Purchase Requisition has been all selected or is not found.");
                return false;
            }
            else if (requisitionMode.equals("displayChosenReqID")){
                System.out.println("Selected Requisition is empty.");
                return false;
            }
            else if (requisitionMode.equals("input") || requisitionMode.equals("delete")){
                System.out.println("Purchase Requisition ID is unavailable for this supplier or doesnt exists. Please re-input requisition ID.");
                return false;
            }
        }
        return true;
    }
    
    public boolean displayItemSupplier(boolean checkItemSupplier, String filterSupplierID, String filterSupplierName, String filterItemID, String filterItemBuyingPrice){
        Item itemMethod = new Item();
        Supplier supplierMethod = new Supplier();
        ItemsSupplier isMethod = new ItemsSupplier();
        PMFilter f = new PMFilter();
        
        ArrayList<ItemsSupplier> itemSupplierList = isMethod.readIS();
        LinkedHashMap<String, Item> itemListMap = itemMethod.readItemMap();
        LinkedHashMap<String, Supplier> supplierListMap = supplierMethod.readSupplierCPR();
        
        if (!checkItemSupplier){
            System.out.println("\nCurrent Filter: Supplier ID: "+filterSupplierID+" | Supplier Name: "+filterSupplierName+" | Item ID: "+filterItemID+" |Item Buying Price: "+filterItemBuyingPrice);
            System.out.println("----------------------- Item Supplier List -------------------------------------------------");
            System.out.println("| Supplier ID | Supplier Name        | Item ID (Item Name)         | Item Buying Price |");
            System.out.println("--------------------------------------------------------------------------------------------");
        }

        boolean recordFound = false;
        for (ItemsSupplier itemSupplier : itemSupplierList){
            String iSItemID = itemSupplier.getId();
            String iSSupplierName = itemSupplier.getSupplierName();
            String iSItemBuyingPrice = itemSupplier.getItemBuyingPrice();
            
            String supplierID = supplierListMap.containsKey(iSSupplierName) ? supplierListMap.get(iSSupplierName).getId() : "Unknown";
            String itemName = itemListMap.containsKey(iSItemID) ? itemListMap.get(iSItemID).getName() : "Unknown";
            
            boolean matchesSupplierID = f.dataCompare(filterSupplierID, supplierID);
            boolean matchesSupplierName = f.dataCompare(filterSupplierName, iSSupplierName);
            boolean matchesItemID = f.dataCompare(filterItemID, iSItemID);
            boolean matchesItemBuyingPrice = f.dataCompare(filterItemBuyingPrice, iSItemBuyingPrice);
            
            if (!checkItemSupplier && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemBuyingPrice){
                System.out.printf("| %-11s | %-20s | %-4s (%-20s) | %-17s |\n", supplierID, iSSupplierName, iSItemID, itemName, iSItemBuyingPrice);
                recordFound = true;
            }
            else if (checkItemSupplier && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemBuyingPrice){
                System.out.println("\nItem with Supplier ID chosen: ");
                System.out.println("Supplier ID: "+supplierID+"\nSupplier Name: "+iSSupplierName+"\nItem ID: "+iSItemID+"\nItem Name: "+itemName+"\nItem Buying Price: "+iSItemBuyingPrice); 
                recordFound = true;
                return true;
            }
        }          
        if (!recordFound){
            System.out.println("Item with Supplier ID not found!");
            return false;
        }
        return true;
    }
    
    public boolean displayPurchaseOrder(boolean display, boolean checkDateTime, String filterPurchaseOrderID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverUsername, String filterSupplierID, String filterSupplierName, String filterStatus, String filterPaymentStatus, String filterDateTime){
        User userMethod = new User();
        PurchaseManager pmMethod = new PurchaseManager();
        PMFilter f = new PMFilter();
        PurchaseOrder poMethod = new PurchaseOrder();
        Supplier supplierMethod = new Supplier();
        
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        LinkedHashMap<String, User> userListMap = userMethod.readUserMap();
        LinkedHashMap<String, Supplier> supplierListMap = supplierMethod.readSupplierMap();
        
        if ((display && !checkDateTime) || (display && checkDateTime)){
            System.out.println("\nCurrent Filter: Purchase Order ID: "+filterPurchaseOrderID+" | Submitter ID: "+filterSubmitterID+" | Submitter Username: "+filterSubmitterUsername+" | Approver ID: "+filterApproverID+" | Approver Username: "+filterApproverUsername+" | Supplier ID: "+filterSupplierID+" | Supplier Name: "+filterSupplierName+" | Status: "+filterStatus+" |Payment Status: "+filterPaymentStatus+" | Date: "+filterDateTime);
            System.out.println("----------------------------------------------------------------------- Purchase Orders ----------------------------------------------------------------------------");
            System.out.println("| Purchase Order ID | Submitter ID (Username)      | Approver ID (Username)       | Supplier ID (Username)       | Status   | Payment Status | Date and Time       |");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }

        boolean recordFound = false;
        boolean matchesStatus = false;
        boolean matchesPaymentStatus = false;
        boolean matchesDateTime = false;
        
        for(PurchaseOrder requisition : poList){
            String orderID = requisition.getId();
            String submitterID = requisition.getCreatorId();
            String approverID = requisition.getApproverId();
            String supplierID = requisition.getSupplierId();
            String status = requisition.getStatus();
            String paymentStatus = requisition.getPaymentStatus();
            String dateTime = requisition.getDate();
            
            String submitterName = userListMap.containsKey(submitterID) ? userListMap.get(submitterID).getName() : "Unknown";
            String approverName = userListMap.containsKey(approverID) ? userListMap.get(approverID).getName() : "Unknown";
            String supplierName = supplierListMap.containsKey(supplierID) ? supplierListMap.get(supplierID).getName() : "Unknown";
            
            boolean matchesPOID = f.dataCompare(filterPurchaseOrderID, orderID);
            boolean matchesSubmitterID = f.dataCompare(filterSubmitterID, submitterID);
            boolean matchesSubmitterUsername = f.dataCompare(filterSubmitterUsername, submitterName);
            boolean matchesApproverID = f.dataCompare(filterApproverID, approverID);
            boolean matchesApproverName = f.dataCompare(filterApproverUsername, approverName);
            boolean matchesSupplierID = f.dataCompare(filterSupplierID, supplierID);
            boolean matchesSupplierName = f.dataCompare(filterSupplierName, supplierName);
            
            
            if (!checkDateTime){    // normal checking
                matchesStatus = f.dataCompare(filterStatus, status);
                matchesPaymentStatus = f.dataCompare(filterPaymentStatus, paymentStatus);
                matchesDateTime = f.dateCompare(filterDateTime, dateTime);
            }
            else if (checkDateTime){    // when dlt PO, check date if its within 2 hrs and status is not "Pending"
                matchesStatus = status.equalsIgnoreCase("Pending");
                matchesPaymentStatus = paymentStatus.equalsIgnoreCase("None");
                matchesDateTime = pmMethod.withinTwoHours(dateTime);
            }
            
            if (display && !checkDateTime && matchesPOID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesStatus && matchesPaymentStatus && matchesDateTime){
                System.out.printf("| %-17s | %-5s (%-20s) | %-5s (%-20s) | %-5s (%-20s) | %-8s | %-14s | %-19s |\n", orderID, submitterID, submitterName, approverID, approverName, supplierID, supplierName, status, paymentStatus, dateTime);
                recordFound = true;
            }
            else if (display && checkDateTime && matchesPOID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesStatus && matchesPaymentStatus && matchesDateTime){
                System.out.printf("| %-17s | %-5s (%-20s) | %-5s (%-20s) | %-5s (%-20s) | %-8s | %-14s | %-19s |\n", orderID, submitterID, submitterName, approverID, approverName, supplierID, supplierName, status, paymentStatus, dateTime);
                recordFound = true;
            }
            else if (!display && checkDateTime && matchesPOID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesStatus && matchesPaymentStatus && matchesDateTime){
                System.out.println("Purchase Order ID: "+orderID+"\nSubmitter ID & Name: "+submitterID+" ("+submitterName+")\nApprover ID & Name: "+approverID+" ("+approverName+")\nSupplier ID & Name: "+supplierID+" ("+supplierName+")"
                                    + "\nStatus: "+status+"\nPayment Status: "+paymentStatus+"\nDate & Time: "+dateTime);
                recordFound = true;
                return true;
            }
        }
        if (!recordFound){
            if (display && !checkDateTime){
                System.out.println("Purchase Order not found!");
            }
            else if (display && checkDateTime){
                System.out.println("No purchase order created within 2hrs or purchase order not found!");
            }
            return false;
        }
        return true;
    }
    
    public boolean displaySupplierWithReq(boolean checkSupplier, String filterSupplierID, String filterSupplierName, String filterReqID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverName, String filterItemID, String filterItemName, String filterQuantity, String filterStatus, String filterDateTime){
        PurchaseManager pmMethod = new PurchaseManager();
        User userMethod = new User();
        PurchaseRequisition prMethod = new PurchaseRequisition();
        Item itemMethod = new Item();
        Supplier supplierMethod = new Supplier();
        PMFilter f = new PMFilter();
        ArrayList<Supplier> supplierList = supplierMethod.readSupplier();
        ArrayList<PurchaseRequisition> purchaseRequisitionList = prMethod.readPR();
        LinkedHashMap<String, Item> itemListMap = itemMethod.readItemMap();
        LinkedHashMap<String, String[]> userListMap = userMethod.readUserPR();
        //String filterReqID = "All", filterUsername = "All", filterApproverName = "All", filterSupplierName = "All", filterItemName = "All", filterQuantity = "All", filterStatus = "All", filterDateTime = "XX-XX-XXXX";
        
        if (!checkSupplier){
            System.out.println("\nCurrent Filter: Supplier ID: "+filterSupplierID+" | Supplier Name: "+filterSupplierName+" | Requisition ID: "+filterReqID+" | Submitter ID: "+filterSubmitterID+" | Submitter Username: "+filterSubmitterUsername+" | Approval ID: "+filterApproverID+" | Approval Username: "+filterApproverName+" | Item ID: "+filterItemID+" | Item Name: "+filterItemName+" | Quantity: "+filterQuantity+" | Status: "+filterStatus+" | Date: "+filterDateTime);
            System.out.println("------------------------------------------------------------------------- Supplier in Requisition List -----------------------------------------------------------------------------------");
            System.out.println("| Supplier ID (Name)            | Requisition ID | Submitter ID (Username)      | Approver ID (Username)       | Item ID (Item Name)         | Quantity | Status   | Date and Time       |");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        boolean recordFound = false;
        String newSupplierID;
        String oldSupplierID = "";
        
        for (Supplier supplier : supplierList){
            String supplierID = supplier.getId();
            String supplierName = supplier.getName();
            newSupplierID = supplierID;
            for (PurchaseRequisition requisition : purchaseRequisitionList){
                if (requisition.getSupplierID().equals(supplierID)){
                    String requisitionID = requisition.getId();  
                    String submitterID = requisition.getSubmitterID();
                    String approverID = requisition.getApproverID();
                    String itemID = requisition.getItemID();
                    String quantity = requisition.getQuantity();
                    String status = requisition.getStatus();
                    String dateTime = requisition.getDate();
                    
                    String submitterName = userListMap.containsKey(submitterID) ? userListMap.get(submitterID)[5] : "Unknown";
                    String approverName = userListMap.containsKey(approverID) ? userListMap.get(approverID)[5] : "Unknown";
                    String itemName = itemListMap.containsKey(itemID) ? itemListMap.get(itemID).getName() : "Unknown";
                    
                    boolean matchesReqID = f.dataCompare(filterReqID, requisitionID);
                    boolean matchesSubmitterID = f.dataCompare(filterSubmitterID, submitterID);
                    boolean matchesSubmitterUsername = f.dataCompare(filterSubmitterUsername, submitterName);
                    boolean matchesApproverID = f.dataCompare(filterApproverID, approverID);
                    boolean matchesApproverName = f.dataCompare(filterApproverName, approverName);
                    boolean matchesSupplierID = f.dataCompare(filterSupplierID, supplierID);
                    boolean matchesSupplierName = f.dataCompare(filterSupplierName, supplierName);
                    boolean matchesItemID = f.dataCompare(filterItemID, itemID);
                    boolean matchesItemName = f.dataCompare(filterItemName, itemName);
                    boolean matchesQuantity = f.dataCompare(filterQuantity, quantity);
                    boolean matchesStatus =  status.equals("Approved"); //f.dataCompare(filterStatus, status);
                    boolean matchesDateTime = pmMethod.afterTwoHours(dateTime);
                    

                    if (!checkSupplier && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                        if (!newSupplierID.equals(oldSupplierID)){
                            System.out.printf("| %-6s (%-20s) | %-14s | %-5s (%-20s) | %-5s (%-20s) | %-4s (%-20s) | %-8s | %-8s | %-19s |\n", supplierID, supplierName, requisitionID, submitterID, submitterName, approverID, approverName, itemID, itemName, quantity, status, dateTime);
                            oldSupplierID = newSupplierID;
                            recordFound = true;
                        }
                        else{
                            System.out.printf("| %-6s %-22s | %-14s | %-5s (%-20s) | %-5s (%-20s) | %-4s (%-20s) | %-8s | %-8s | %-19s |\n", "", "", requisitionID, submitterID, submitterName, approverID, approverName, itemID, itemName, quantity, status, dateTime);
                            oldSupplierID = newSupplierID;
                            recordFound = true;
                        }
                    }
                    else if (checkSupplier && matchesReqID && matchesSubmitterID && matchesSubmitterUsername && matchesApproverID && matchesApproverName && matchesSupplierID && matchesSupplierName && matchesItemID && matchesItemName && matchesQuantity && matchesStatus && matchesDateTime){
                        System.out.println("\n Supplier to create purchase order chosen: "+supplierID+" ("+supplierName+")");
                        recordFound = true;
                        return true;
                    }
                }
            }
        }
        
        if (!recordFound){
            System.out.println("Purchase Requisition is not found.");
            return false;
        }
        
        return true;
    }
    
    public boolean displayPurchaseOrderItem(boolean checkPOID, String inputOrderID){
        User userMethod = new User();
        PurchaseOrder poMethod = new PurchaseOrder();
        PurchaseOrderItem poItemMethod = new PurchaseOrderItem();
        Item itemMethod = new Item();
        Supplier supplierMethod = new Supplier();
        ArrayList<PurchaseOrder> purchaseOrderList  = poMethod.readPO();
        ArrayList<PurchaseOrderItem> purchaseOrderItemList = poItemMethod.readPOItem();
        LinkedHashMap<String, Item> itemListMap = itemMethod.readItemMap();
        LinkedHashMap<String,User> userListMap = userMethod.readUserMap();
        LinkedHashMap<String, Supplier> supplierListMap = supplierMethod.readSupplierMap();
        
        double totalPrice = 0;
        boolean recordFound = false;
        for (PurchaseOrder order : purchaseOrderList){
            String orderID = order.getId();
            if (orderID.equals(inputOrderID)){
                if (checkPOID){
                    return true;
                }
                String submitterID = order.getCreatorId();
                String approverID = order.getApproverId();
                String supplierID = order.getSupplierId();
                String dateTime = order.getDate();
                
                String submitterName = userListMap.containsKey(submitterID) ? userListMap.get(submitterID).getName() : "Unknown";
                String approverName = userListMap.containsKey(approverID) ? userListMap.get(approverID).getName() : "Unknown";
                String supplierName = supplierListMap.containsKey(supplierID) ? supplierListMap.get(supplierID).getName() : "Unknown";   
                
                System.out.println("\nPurchase Order ID: "+orderID+" | Submitter ID & Name: "+submitterID+" ("+submitterName+") | Approver ID & Name: "+approverID+" ("+approverName+") | Supplier ID & Name: "+supplierID+" ("+supplierName+") | Date and Time: "+dateTime);
                if (!checkPOID){
                    System.out.println("-----------------Purchase Order Item List------------------------------");
                    System.out.println("| ItemID | Item Name            | Quantity | Unit Price  | Total      |");
                    System.out.println("-----------------------------------------------------------------------");
                }
                        
                for (PurchaseOrderItem orderItems : purchaseOrderItemList){
                    if (orderItems.getId().equals(orderID)){
                        String itemID = orderItems.getItemId();
                        String itemQuantity = orderItems.getQuantity();
                        String itemUnitPrice = orderItems.getUnitPrice();
                        
                        String itemName = itemListMap.containsKey(itemID) ? itemListMap.get(itemID).getName() : "Unknown";
                        String totalUnitPrice = String.format("%.2f",Double.parseDouble(itemQuantity)*Double.parseDouble(itemUnitPrice));
                        totalPrice = totalPrice + Double.parseDouble(totalUnitPrice);
                        
                        System.out.printf("| %-6s | %-20s | %-8s | RM%-9s | RM%-8s |\n", itemID, itemName, itemQuantity, itemUnitPrice, totalUnitPrice);
                        recordFound = true;
                    }
                }
                break;
            }
        }
        if (recordFound){
            System.out.printf("| %-40s | %-11s:  RM%-8s |\n\n", " ", "Total Price", totalPrice);
            return true;
        }
        else if(!recordFound){
            if (!checkPOID){
                System.out.println("Purchase Order Item not found!\n");
            }
            return false;
        }
        return true;
    }
}
