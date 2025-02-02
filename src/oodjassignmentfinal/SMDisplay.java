package oodjassignmentfinal;

import java.util.*;

public class SMDisplay extends Display{
    private static final Scanner scanner = new Scanner(System.in);
    
    @Override
    public String homePage(){
        SMNotification notification = new SMNotification();
        notification.getNotification();
        
        System.out.println("\nwelcome to sales manager portal");
        System.out.println("1. view list of items");
        System.out.println("2. daily item wise sales entry");
        System.out.println("3. sales report");
        System.out.println("4. stock level");
        System.out.println("5. view and create requisition");
        System.out.println("Q to exit");
        System.out.println("");
        System.out.print("what do u feel like doing? (insert number): ");

        return scanner.nextLine().trim().toUpperCase();
    }
    
    public void displayItem(){
        Item itemMethod = new Item();
        
        String title  = "<------- List of Items ------->";
        int consoleWidth = 25;
        int padding = consoleWidth - title.length() / 2;
        
        System.out.println("\n" + " ".repeat(padding) + title);
        System.out.println("");
        
        System.out.printf("| %-8s | %-17s | %-18s |\n", "itemID", "itemName", "itemSellingPrice");
        System.out.println("-".repeat(53));

        ArrayList<String[]> itemList = itemMethod.readFile();
        for (String[] details : itemList){
            System.out.printf("| %-8s | %-17s | %-18s |\n", details[0], details[1],details[2]);
        }
    }
    
    public void displayCartItems(ArrayList<String[]> foundItems){
        System.out.printf("| %-10s | %-20s | %-17s | %-10s |\n", 
                    "itemID", "itemName", "itemSellingPrice", "quantity");
        for (String[] items : foundItems){
            System.out.printf("| %-10s | %-20s | %-17s | %-10s |\n",
                    items[0], items[1], items[2], items[3]);
        }
        System.out.println("");
    }
    
    public void displaySales(){
        ItemsSold itemsSoldMethod = new ItemsSold();
        
        String title  = "<------- List of Sales ------->";
        int consoleWidth = 17;
        int padding = consoleWidth - title.length() / 2;

        System.out.println("\n" + " ".repeat(padding) + title);
        System.out.println("");

        System.out.printf("| %-6s | %-9s | %-10s |\n",
                            "saleID", "itemID", "quantity");
        System.out.println("-".repeat(35));

        ArrayList<String[]> itemList = itemsSoldMethod.readFile();
        for (String[] details : itemList){
            System.out.printf("| %-6s | %-9s | %-10s | \n", details[0], details[1],details[2]);
        }
    }
    
    public void displaySummary(Map<String, double[] > summary){
        System.out.println("\n<------- Summary of Items Sold ------->");
        System.out.printf("| %-9s | %-10s | %-18s |\n", 
                "itemID", "quantity", "totalProfit");
        System.out.println("-".repeat(47));
        
        double totalProfit = 0;
        
        for(Map.Entry<String, double[] > entry : summary.entrySet()){
            String itemID = entry.getKey();
            double[] values = entry.getValue();
            int totalQuantity = (int)values[0];
            double totalItemProfit = values[1];
            
            totalProfit += totalItemProfit;
            
            System.out.printf("| %-9s | %-10d | %-18.2f |\n",
                    itemID, totalQuantity, totalItemProfit);
        }
        System.out.println("-".repeat(47));
        System.out.printf("Total Profit: %.2f\n", totalProfit);
    }
    
    public void displayStock(){
        Item itemMethod = new Item();
        
        String title  = "<------- List of Stock ------->";
        int consoleWidth = 25;
        int padding = consoleWidth - title.length() / 2;
        
        System.out.println("\n" + " ".repeat(padding) + title);
        System.out.println("");
        
        System.out.printf("| %-8s | %-17s | %-12s |\n",
                            "itemID", "itemName", "itemStock");
        System.out.println("-".repeat(53));
        
        ArrayList<String[]> itemList = itemMethod.readFile();
        for (String[] details : itemList){
            System.out.printf("| %-8s | %-17s | %-12s |\n", details[0], details[1],details[3]);
        }
    }
    
    public boolean displayRequisition(boolean checkDate, boolean dltReq, String filterReqID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverName, String filterSupplierID, String filterSupplierName, String filterItemID, String filterItemName, String filterQuantity, String filterStatus, String filterDateTime){
        SalesManager smMethod = new SalesManager();
        
        PurchaseRequisition prMethod = new PurchaseRequisition();
        Item itemMethod = new Item();
        Supplier supplierMethod = new Supplier();
        User userMethod = new User();
        
        Filter f = new Filter();
        ArrayList<String[]> purchaseRequisitionList = prMethod.readFile();
        LinkedHashMap<String, String[]> itemListMap = itemMethod.readHashMap();
        LinkedHashMap<String, String[]> supplierListMap = supplierMethod.readHashMap();
        LinkedHashMap<String, String[]> userListMap = userMethod.readHashMap();
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
        for (String[] requisition : purchaseRequisitionList){
            String requisitionID = requisition[0];  
            String submitterID = requisition[1];
            String approverID = requisition[2];
            String supplierID = requisition[3];
            String itemID = requisition[4];
            String quantity = requisition[5];
            String status = requisition[6];
            String dateTime = requisition[7];
            
            String submitterName = userListMap.containsKey(submitterID) ? userListMap.get(submitterID)[5] : "Unknown";
            String approverName = userListMap.containsKey(approverID) ? userListMap.get(approverID)[5] : "Unknown";
            String supplierName = supplierListMap.containsKey(supplierID) ? supplierListMap.get(supplierID)[1] : "Unknown";
            String itemName = itemListMap.containsKey(itemID) ? itemListMap.get(itemID)[1] : "Unknown";
            
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
                matchesDateTime = smMethod.withinTwoHours(dateTime);
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
                return true;
            }
        }
        if (!recordFound){
            System.out.println("Purchase Requisition is not found.");
            return false;
        }
        
        return true;
    }
    
    public void requisitionPageFilterDisplayOption(ArrayList<String> filterEdit){
            System.out.println("\nCurrent Filter: Requisition ID: "+filterEdit.get(0)+" | Submitter ID: "+filterEdit.get(1)+" | Submitter Username: "+filterEdit.get(2)+" | Approval ID: "+filterEdit.get(3)+" | Approval Username: "+filterEdit.get(4)+
                " | Supplier ID: "+filterEdit.get(5)+" | Supplier Name: "+filterEdit.get(6)+" | Item ID: "+filterEdit.get(7)+" | Item Name: "+filterEdit.get(8)+" | Quantity: "+filterEdit.get(9)+" | Status: "+filterEdit.get(10)+" | Date: "+filterEdit.get(11));
            System.out.println("Filter Options:\n1.Requisition ID\n2.Submitter ID\n3.Submitter Username\n4.Approver ID\n5.Approver Username\n6.Supplier ID\n7.Supplier Name\n8.Item ID\n9.Item Name\n10.Quantity\n11.Status\n12.Date\n13.Reset All Filters\n14.Apply");

    }
    
    public boolean displayItem(boolean checkItem, String filterItemID, String filterItemName, String filterItemSellingPrice, String filterstocklevel){
        Item itemMethod = new Item();
        
        ArrayList<String[]> itemList = itemMethod.readFile();
        
        if (!checkItem){
            System.out.println("\nCurrent Filter: Item ID: "+filterItemID+" | Item Name: "+filterItemName+" | Item Selling Price: "+filterItemSellingPrice+" | Stock Level: "+filterstocklevel);
            System.out.println("----------------------------------------- List of Items --------------------------------------------");
            System.out.println("| Item ID    | Item Name          | Item Selling Price | Stock Level | Reorder Level | Updated Date |");
            System.out.println("----------------------------------------------------------------------------------------------------");
        }

        boolean recordFound = false;
        for (String[] item : itemList){
            String itemID = item[0];
            String itemName = item[1];
            String itemSellingPrice = item[2];
            String stockLevel = item[3];
            boolean matchesID = filterItemID.equals("All") || itemID.equalsIgnoreCase(filterItemID);            
            boolean matchesName = filterItemName.equals("All") || itemName.equalsIgnoreCase(filterItemName);
            boolean matchesSellingPrice = filterItemSellingPrice.equals("All") || itemSellingPrice.equalsIgnoreCase(filterItemSellingPrice);
            boolean matchesStockLevel = filterstocklevel.equals("All") || stockLevel.equalsIgnoreCase(filterstocklevel);
            if (!checkItem && matchesID && matchesName && matchesSellingPrice && matchesStockLevel){
                System.out.printf("| %-10s | %-18s | %-18s | %-11s | %-13s | %-12s |\n",itemID,itemName,itemSellingPrice,stockLevel,item[4],item[5]);    
                recordFound = true;
            }
            else if (checkItem && matchesID && matchesName && matchesSellingPrice && matchesStockLevel){
                System.out.println("Item Chosen:");   
                System.out.println("Item Code: "+item[0]+"\nItem Name: "+item[1]+"\nItem Selling Price: "+item[2]+"\nItem Current Stock Level: "+item[3]+"\nItem Reorder Level: "+item[4]+"\nItem Updated Date: "+item[5]); 
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
    
    public void itemPageFilterDisplayOption(ArrayList<String> filterEdit){
            System.out.println("\nCurrent Filter: Item ID: "+filterEdit.get(0)+" | Item Name: "+filterEdit.get(1)+" | Item Selling Price: "+filterEdit.get(2)+" | Stock Level: "+filterEdit.get(3));
            System.out.println("Filter Options:\n1.Item ID Filter\n2.Item Name Filter\n3.Item Selling Price Filter\n4.Stock Level Filter\n5.Reset All Filter\n6.Apply");

    }
    
    public boolean displayItemSupplier(boolean checkItemSupplier, String filterSupplierID, String filterSupplierName, String filterItemID, String filterItemBuyingPrice){
        SMFilter filter = new SMFilter();
        
        ItemsSupplier itemSupplierMethod = new ItemsSupplier();
        Item itemMethod = new Item();
        Supplier supplierMethod = new Supplier();
        
        ArrayList<String[]> itemSupplierList = itemSupplierMethod.readFile();
        LinkedHashMap<String, String[]> itemListMap = itemMethod.readHashMap();
        LinkedHashMap<String, String[]> supplierListMap = supplierMethod.readHashMapName();
        
        if (!checkItemSupplier){
            System.out.println("\nCurrent Filter: Supplier ID: "+filterSupplierID+" | Supplier Name: "+filterSupplierName+" | Item ID: "+filterItemID+" |Item Buying Price: "+filterItemBuyingPrice);
            System.out.println("----------------------- Item Supplier List -------------------------------------------------");
            System.out.println("| Supplier ID | Supplier Name        | Item ID (Item Name)         | Item Buying Price |");
            System.out.println("--------------------------------------------------------------------------------------------");
        }

        boolean recordFound = false;
        for (String[] itemSupplier : itemSupplierList){
            String iSItemID = itemSupplier[0];
            String iSSupplierName = itemSupplier[1];
            String iSItemBuyingPrice = itemSupplier[2];
            
            String supplierID = supplierListMap.containsKey(iSSupplierName) ? supplierListMap.get(iSSupplierName)[0] : "Unknown";
            String itemName = itemListMap.containsKey(iSItemID) ? itemListMap.get(iSItemID)[1] : "Unknown";
            
            boolean matchesSupplierID = filter.dataCompare(filterSupplierID, supplierID);
            boolean matchesSupplierName = filter.dataCompare(filterSupplierName, iSSupplierName);
            boolean matchesItemID = filter.dataCompare(filterItemID, iSItemID);
            boolean matchesItemBuyingPrice = filter.dataCompare(filterItemBuyingPrice, iSItemBuyingPrice);
            
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
    
    public void itemSupplierFilterDisplayOption(ArrayList<String> filterEdit){
            System.out.println("\nCurrent Filter: Supplier ID: "+filterEdit.get(0)+" | Supplier Name: "+filterEdit.get(1)+" | Item Buying Price: "+filterEdit.get(2));
            System.out.println("Filter Options:\n1.Supplier ID Filter\n2.Supplier Name Filter\n3.Item Buying Price Filter\n4.Reset All Filter\n5.Apply");

    }
}
