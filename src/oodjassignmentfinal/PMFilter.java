package oodjassignmentfinal;

import java.util.*;

public class PMFilter extends Filter{
    protected ArrayList<String> itemPageFilterEdit(String filterItemID, String filterItemName, String filterItemSellingPrice, String filterstocklevel){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> filterEdit = new ArrayList<String>();
        filterEdit.add(filterItemID);
        filterEdit.add(filterItemName);
        filterEdit.add(filterItemSellingPrice);
        filterEdit.add(filterstocklevel);
        
        while (true){
            System.out.println("\nCurrent Filter: Item ID: "+filterEdit.get(0)+" | Item Name: "+filterEdit.get(1)+" | Item Selling Price: "+filterEdit.get(2)+" | Stock Level: "+filterEdit.get(3));
            System.out.println("Filter Options:\n1.Item ID Filter\n2.Item Name Filter\n3.Item Selling Price Filter\n4.Stock Level Filter\n5.Reset All Filter\n6.Apply");
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch(option){
                    case "1":{
                        filterEdit.set(0, filterIDEditer(false, "Item", "I", ""));
                        chooseOption = true;
                        break;
                    }
                    case "2":{
                        filterEdit.set(1, filterDataEditer("ItemOrSupplierName", "Item"));
                        chooseOption = true;
                        break;
                    }
                    case "3":{
                        System.out.println();
                        filterEdit.set(2, filterDataEditer("Double", "Item Selling Price"));
                        chooseOption = true;
                        break;
                    }
                    case "4":{
                        System.out.println();
                        filterEdit.set(3, filterDataEditer("Numbers", "Stock Level"));
                        chooseOption = true;
                        break;
                    }
                    case "5":{
                        for (int i = 0; i < 4 ; i++){
                            filterEdit.set(i, "All");
                        }
                        System.out.println("All filter have been reset to 'All'.");
                        chooseOption = true;
                        break;
                    }
                    case "6":{
                        return filterEdit;
                    }
                    default:{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }
    }
    
    protected ArrayList<String> supplierPageFilterEdit(String filterSupplierID, String filterSupplierName, String filterContactNumber, String filterAddress){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> filterEdit = new ArrayList<String>();
        filterEdit.add(filterSupplierID);
        filterEdit.add(filterSupplierName);
        filterEdit.add(filterContactNumber);
        filterEdit.add(filterAddress);
        
        while (true){
            System.out.println("\nCurrent Filter: Supplier ID: "+filterEdit.get(0)+" | Supplier Name: "+filterEdit.get(1)+" | Contact Number: "+filterEdit.get(2)+" | Address: "+filterEdit.get(3));
            System.out.println("Filter Options:\n1.Supplier ID Filter\n2.Supplier Name Filter\n3.Contact Number\n4.Address\n5.Reset All Filter\n6.Apply");
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch(option){
                    case "1"->{
                        filterEdit.set(0, filterIDEditer(false, "Supplier", "S", ""));
                        chooseOption = true;
                        break;
                    }
                    case "2"->{
                        filterEdit.set(1, filterDataEditer("ItemOrSupplierName", "Item"));
                        chooseOption = true;
                        break;
                    }
                    case "3"->{
                        System.out.println();
                        filterEdit.set(2, filterDataEditer("ContactOrAddress", "Contact Number"));
                        chooseOption = true;
                        break;
                    }
                    case "4"->{
                        System.out.println();
                        filterEdit.set(3, filterDataEditer("ContactOrAddress", "Address"));
                        chooseOption = true;
                        break;
                    }
                    case "5"->{
                        for (int i = 0; i < 4 ; i++){
                            filterEdit.set(i, "All");
                        }
                        System.out.println("All filter have been reset to 'All'.");
                        chooseOption = true;
                        break;
                    }
                    case "6"->{
                        return filterEdit;
                    }
                    default->{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }
    }
    
    protected ArrayList<String> requisitionPageFilterEdit(String filterMode, String filterReqID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverName, String filterSupplierID, String filterSupplierName, String filterItemID, String filterItemName, String filterQuantity, String filterStatus, String filterDateTime){
        ArrayList<String> filterEdit = new ArrayList<>();
        filterEdit.addAll(Arrays.asList(filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterItemID, filterItemName, filterQuantity, filterStatus, filterDateTime));
        Scanner sc = new Scanner(System.in);
        
        while (true){
            System.out.println("\nCurrent Filter: Requisition ID: "+filterEdit.get(0)+" | Submitter ID: "+filterEdit.get(1)+" | Submitter Username: "+filterEdit.get(2)+" | Approval ID: "+filterEdit.get(3)+" | Approval Username: "+filterEdit.get(4)+
                               " | Supplier ID: "+filterEdit.get(5)+" | Supplier Name: "+filterEdit.get(6)+" | Item ID: "+filterEdit.get(7)+" | Item Name: "+filterEdit.get(8)+" | Quantity: "+filterEdit.get(9)+" | Status: "+filterEdit.get(10)+" | Date: "+filterEdit.get(11));
            System.out.println("Filter Options:\n1.Requisition ID\n2.Submitter ID\n3.Submitter Username\n4.Approver ID\n5.Approver Username\n6.Supplier ID\n7.Supplier Name\n8.Item ID\n9.Item Name\n10.Quantity\n11.Status\n12.Date\n13.Reset All Filters\n14.Apply");
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your filter option: ");
                String option = sc.nextLine();
                switch(option){
                    case "1":{
                        filterEdit.set(0, filterIDEditer(false, "Requisition", "PR", ""));
                        chooseOption = true;
                        break;
                    }
                    case "2":{
                        filterEdit.set(1, filterIDEditer(true, "Submitter", "SM", "PM"));
                        chooseOption = true;
                        break;
                    }
                    case "3":{
                        filterEdit.set(2, filterDataEditer("Username", "Submitter"));
                        chooseOption = true;
                        break;
                     }
                    case "4":{
                        filterEdit.set(3, filterIDEditer(false, "Approver", "PM", ""));
                        chooseOption = true;
                        break;
                    }
                    case "5":{
                        filterEdit.set(4, filterDataEditer("Username", "Approver"));
                        chooseOption = true;
                        break;
                    }
                    case "6":{
                        if (filterMode.equals("CPO")){
                            System.out.println("Changing supplier's ID is unavailable when creating purchase order.");
                            filterEdit.set(5, filterEdit.get(5));
                            chooseOption = true;
                            break;
                        }
                        else{
                            filterEdit.set(5, filterIDEditer(false, "Supplier", "S", ""));
                            chooseOption = true;
                            break;
                        }
                    }
                    case "7":{
                        if (filterMode.equals("CPO")){
                            System.out.println("Changing supplier name is unavailable when creating purchase order.");
                            filterEdit.set(6, filterEdit.get(6));
                            chooseOption = true;
                            break;
                        }
                        else{
                            filterEdit.set(6, filterDataEditer("ItemOrSupplierName", "Supplier"));
                            chooseOption = true;
                            break;
                        }
                    }
                    case "8":{
                        filterEdit.set(7, filterIDEditer(false, "Item", "I", ""));
                        chooseOption = true;
                        break;
                    }
                    case "9":{
                        filterEdit.set(8, filterDataEditer("ItemOrSupplierName", "Item"));
                        chooseOption = true;
                        break;
                    }
                    case "10":{
                        filterEdit.set(9, filterDataEditer("Numbers", "Quantity"));
                        chooseOption = true;
                        break;
                    }
                    case "11":{
                        if (filterMode.equals("Validation")){
                            System.out.println("Changing Status is unavailable during validation for SM's requisition.");
                            filterEdit.set(10, "Pending");
                            chooseOption = true;
                            break;
                        }
                        else if (filterMode.equals("CPO")){
                            System.out.println("Changing Status is unavailable when creating purchase order.");
                            filterEdit.set(10, "Approved");
                            chooseOption = true;
                            break;
                        }
                        else if (filterMode.equals("Deletion")){
                            while (true){
                                String infoStatus  = filterDataEditer("Status", "Status");
                                if (infoStatus.equals("Processed")){
                                    System.out.println("Status 'Processed' is unavailable during deletion for purchase requisition, please choose status again.");
                                }
                                else{
                                    System.out.println("Status Filter successfully set as "+infoStatus+".");
                                    filterEdit.set(10, infoStatus);
                                    chooseOption = true;
                                    break;
                                }
                            }
                            break;
                        }
                        else{
                            String chosenStatus  = filterDataEditer("Status", "Status");
                            System.out.println("Status Filter successfully set as "+chosenStatus+".");
                            filterEdit.set(10, chosenStatus);
                            chooseOption = true;
                            break;
                        }
                    }
                    case "12":{
                        if (filterMode.equals("Deletion")){
                            System.out.println("Changing Date is unavailable during deletion for purchase requisition.");
                            filterEdit.set(11, "XX-XX-XXXX");
                            chooseOption = true;
                            break;
                        }
                        else{
                            filterEdit.set(11, filterDataEditer("Date", "Date"));
                            chooseOption = true;
                            break;
                        }
                    }
                    case "13":{
                        for (int i = 0; i < filterEdit.size(); i++) {
                            if (filterMode.equals("CPO") && (i == 5 || i == 6)) {
                                continue;
                            }
                            else if (filterMode.equals("Validation") && i == 10) {
                                filterEdit.set(i, "Pending");
                            } 
                            else if (filterMode.equals("CPO") && i == 10) {
                                filterEdit.set(i, "Approved");
                            } 
                            else if (filterMode.equals("Deletion")) {
                                switch (i){
                                    case 10:
                                        continue;
                                    case 11:
                                        filterEdit.set(11, "XX-XX-XXXX");
                                    default:
                                        filterEdit.set(i, "All");
                                        break;
                                }
                            } 
                            else if (i == 11) {
                                filterEdit.set(11, "XX-XX-XXXX");
                            } 
                            else {
                                filterEdit.set(i, "All");
                            }
                        }
                        System.out.println("Filters have been reset. Restricted fields remain unchanged.");
                        chooseOption = true;
                        break;
                    }
                    case "14":{
                        return filterEdit;
                    }
                    default:{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }
    }
    
    protected ArrayList<String> itemSupplierFilterEdit(String filterSupplierIDCPR, String filterSupplierNameCPR, String filterItemBuyingPriceCPR){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> filterEdit = new ArrayList<String>();
        filterEdit.add(filterSupplierIDCPR);
        filterEdit.add(filterSupplierNameCPR);
        filterEdit.add(filterItemBuyingPriceCPR);
        
        while (true){
            System.out.println("\nCurrent Filter: Supplier ID: "+filterEdit.get(0)+" | Supplier Name: "+filterEdit.get(1)+" | Item Buying Price: "+filterEdit.get(2));
            System.out.println("Filter Options:\n1.Supplier ID Filter\n2.Supplier Name Filter\n3.Item Buying Price Filter\n4.Reset All Filter\n5.Apply");
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch(option){
                    case "1":{
                        System.out.println();
                        while (true){
                            System.out.print("Enter Supplier ID (input 'All' to show all): ");
                            String input = sc.nextLine().toUpperCase();
                            if (input.equalsIgnoreCase("all")){
                                filterEdit.set(0, "All");
                                System.out.println("Requisition ID filter successfully set as 'All'");
                                chooseOption = true;
                                break;
                            }
                            else if (input.substring(0,1).equals("S") && input.length() < 5){
                                filterEdit.set(0, input);
                                System.out.println("Requisition ID filter successfully set as "+input+".");
                                chooseOption = true;
                                break;
                            }
                            else{
                                System.out.println("Invalid input option, please re-input starting with 'S', 'All' or within 5 characters.");
                            }
                        }
                        break;
                    }
                    case "2":{
                        System.out.println();
                        filterEdit.set(1, filterDataEditer("ItemOrSupplierName", "Supplier"));
                        chooseOption = true;
                        break;
                    }
                    case "3":{
                        System.out.println();
                        filterEdit.set(2, filterDataEditer("Double", "Item Buying Price"));
                        chooseOption = true;
                        break;
                    }
                    case "4":{
                        for (int i = 0; i < 3 ; i++){
                            filterEdit.set(i, "All");
                        }
                        System.out.println("All filter have been reset to 'All'.");
                        chooseOption = true;
                        break;
                    }
                    case "5":{
                        return filterEdit;
                    }
                    default:{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }
    }
    
    protected ArrayList<String> orderPageFilterEdit(String filterMode, String filterPurchaseOrderID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverName, String filterSupplierID, String filterSupplierName, String filterStatus, String filterPaymentStatus, String filterDateTime){
        ArrayList<String> filterEdit = new ArrayList<>();
        filterEdit.addAll(Arrays.asList(filterPurchaseOrderID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterStatus, filterPaymentStatus, filterDateTime));
        Scanner sc = new Scanner(System.in);
        
        while (true){
            System.out.println("\nCurrent Filter: Purchase Order ID: "+filterEdit.get(0)+" | Submitter ID: "+filterEdit.get(1)+" | Submitter Username: "+filterEdit.get(2)+" | Approval ID: "+filterEdit.get(3)+" | Approval Username: "+filterEdit.get(4)+
                               " | Supplier ID: "+filterEdit.get(5)+" | Supplier Name: "+filterEdit.get(6)+" | Status: "+filterEdit.get(7)+" | Payment Status: "+filterEdit.get(8)+" | Date: "+filterEdit.get(9));
            System.out.println("Filter Options:\n1.Purchase Order ID\n2.Submitter ID\n3.Submitter Username\n4.Approver ID\n5.Approver Username\n6.Supplier ID\n7.Supplier Name\n8.Status\n9.Payment Status\n10.Date\n11.Reset All Filters\n12.Apply");
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your filter option: ");
                String option = sc.nextLine();
                switch(option){
                    case "1":{
                        filterEdit.set(0, filterIDEditer(false, "Purchase Order", "PO", ""));
                        chooseOption = true;
                        break;
                    }
                    case "2":{
                        filterEdit.set(1, filterIDEditer(false, "Submitter", "PM", ""));
                        chooseOption = true;
                        break;
                    }
                    case "3":{
                        filterEdit.set(2, filterDataEditer("Username", "Submitter"));
                        chooseOption = true;
                        break;
                     }
                    case "4":{
                        filterEdit.set(3, filterIDEditer(false, "Approver", "FM", ""));
                        chooseOption = true;
                        break;
                    }
                    case "5":{
                        filterEdit.set(4, filterDataEditer("Username", "Approver"));
                        chooseOption = true;
                        break;
                    }
                    case "6":{
                        filterEdit.set(5, filterIDEditer(false, "Supplier", "S", ""));
                        chooseOption = true;
                        break;
                    }
                    case "7":{
                        filterEdit.set(6, filterDataEditer("ItemOrSupplierName", "Supplier"));
                        chooseOption = true;
                        break;
                    }
                    case "8":{
                        if (filterMode.equals("Deletion")){
                            System.out.println("Changing Status is unavailable during deletion for Purchase Order.");
                            filterEdit.set(7, "Pending");
                            chooseOption = true;
                            break;
                        }
                        else{
                            filterEdit.set(7, filterDataEditer("poStatus", "Status"));
                            chooseOption = true;
                            break;
                        }
                    }
                    case "9":{
                        if (filterMode.equals("Deletion")){
                            System.out.println("Changing Status is unavailable during deletion for Purchase Order.");
                            filterEdit.set(8, "None");
                            chooseOption = true;
                            break;
                        }
                        else{
                            filterEdit.set(8, filterDataEditer("paymentStatus", "Payment Status"));
                            chooseOption = true;
                            break;
                        }
                    }
                    case "10":{
                        if (filterMode.equals("Deletion")){
                            System.out.println("Changing Date is unavailable during deletion for Purchase Order.");
                            filterEdit.set(9, "XX-XX-XXXX");
                            chooseOption = true;
                            break;
                        }
                        else{
                            filterEdit.set(9, filterDataEditer("Date", "Date"));
                            chooseOption = true;
                            break;
                        }
                    }
                    case "11": { 
                        for (int i = 0; i < filterEdit.size(); i++) {
                            if (filterMode.equals("Deletion") && (i == 7 || i == 8)) {
                                continue;
                            }
                            filterEdit.set(i, "All");
                        }
                        filterEdit.set(9, "XX-XX-XXXX");
                        if (filterMode.equals("Deletion")) {
                            filterEdit.set(7, "Pending");
                            filterEdit.set(8, "None");
                        }
                        System.out.println("Filters have been reset. Restricted fields remain unchanged.");
                        chooseOption = true;
                        break;
                    }
                    case "12":{
                        return filterEdit;
                    }
                    default:{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }
    }
    
    protected ArrayList<String> orderSupplierFilterEdit(String filterSupplierID, String filterSupplierName, String filterReqID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverName, String filterItemID, String filterItemName, String filterQuantity, String filterStatus, String filterDateTime){
        ArrayList<String> filterEdit = new ArrayList<>();
        filterEdit.addAll(Arrays.asList(filterSupplierID, filterSupplierName, filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterItemID, filterItemName, filterQuantity, filterStatus, filterDateTime));
        Scanner sc = new Scanner(System.in);
        
        while (true){
            System.out.println("\nCurrent Filter: Supplier ID: "+filterEdit.get(0)+" | Supplier Name: "+filterEdit.get(1)+" | Requisition ID: "+filterEdit.get(2)+" | Submitter ID: "+filterEdit.get(3)+" | Submitter Username: "+filterEdit.get(4)+" | Approval ID: "+filterEdit.get(5)+" | Approval Username: "+filterEdit.get(6)+" | Item ID: "+filterEdit.get(7)+" | Item Name: "+filterEdit.get(8)+" |  Quantity: "+filterEdit.get(9)+" | Status: "+filterEdit.get(10)+" | Date: "+filterEdit.get(11));
            System.out.println("Filter Options:\n1.Supplier ID\n2.Supplier Name\n3.Requisition ID\n4.Submitter ID\n5.Submitter Username\n6.Approver ID\n7.Approver Username\n8.Item ID\n9.Item Name\n10.Quantity\n11.Status\n12.Date\n13.Reset All Filters\n14.Apply");
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your filter option: ");
                String option = sc.nextLine();
                switch(option){
                    case "1":{
                        filterEdit.set(0, filterIDEditer(false, "Supplier", "S", ""));
                        chooseOption = true;
                        break;
                        
                    }
                    case "2":{
                        filterEdit.set(1, filterDataEditer("ItemOrSupplierName", "Supplier"));
                        chooseOption = true;
                        break;
                    }
                    case "3":{
                        filterEdit.set(2, filterIDEditer(false, "Requisition", "PR", ""));
                        chooseOption = true;
                        break;
                     }
                    case "4":{
                        filterEdit.set(3, filterIDEditer(false, "Submitter", "PM", ""));
                        chooseOption = true;
                        break;
                    }
                    case "5":{
                        filterEdit.set(4, filterDataEditer("Username", "Submitter"));
                        chooseOption = true;
                        break;
                    }
                    case "6":{
                        filterEdit.set(5, filterIDEditer(false, "Approver", "PM", ""));
                        chooseOption = true;
                        break;
                    }
                    case "7":{
                        filterEdit.set(6, filterDataEditer("Username", "Approver"));
                        chooseOption = true;
                        break;
                    }
                    case "8":{
                        filterEdit.set(7, filterIDEditer(false, "Item", "I", ""));
                        chooseOption = true;
                        break;
                    }
                    case "9":{
                        filterEdit.set(8, filterDataEditer("ItemOrSupplierName", "Item"));
                        chooseOption = true;
                        break;
                    }
                    case "10":{
                        filterEdit.set(9, filterDataEditer("Numbers", "Quantity"));
                        chooseOption = true;
                        break;
                    }
                    case "11":{
                        System.out.println("Changing Status is unavailable during validation for SM's requisition.");
                        filterEdit.set(10, "Approved");
                        chooseOption = true;
                        break;
                        }
                    case "12":{
                        filterEdit.set(11, filterDataEditer("Date", "Date"));
                        chooseOption = true;
                        break;
                    }
                    case "13":{
                        for (int i = 0; i < 12 ; i++){
                            filterEdit.set(i, "All");
                        }
                        filterEdit.set(11, "XX-XX-XXXX");
                        System.out.println("All filter have been reset to 'All'.");
                        chooseOption = true;
                        break;
                    }
                    case "14":{
                        return filterEdit;
                    }
                    default:{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }
    }
}
