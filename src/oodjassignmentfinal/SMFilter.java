package oodjassignmentfinal;

import java.util.*;

public class SMFilter extends Filter{
    private static final Scanner scanner = new Scanner(System.in);
    
    public ArrayList<String[]> searchAndDisplayItems(ArrayList<String[]> foundItems){
        Item itemMethod = new Item();
        SMDisplay display = new SMDisplay();
        
        System.out.print("insert item id to add (type done when done)");
        System.out.println("");
        System.out.println("");
        while(true){
            System.out.print("enter item id: ");
            String itemId = scanner.nextLine().trim();
            
            if (itemId.equalsIgnoreCase("done")){
                System.out.println("");
                System.out.println("finalizing...");
                System.out.println("");
                break;
            }
            
            ArrayList<String[]> itemList = itemMethod.readFile();
            for(String[] item : itemList){
                if(item[0].equalsIgnoreCase(itemId)){
                    int availableQuantity = Integer.parseInt(item[3]);
                    String quantity;
                    
                    while(true){
                        System.out.print("Enter quantity for " + itemId + " (available quantity: " + availableQuantity +"): ");
                        quantity = scanner.nextLine().trim();

                        try{
                            int parsedQuantity = Integer.parseInt(quantity);
                            if(parsedQuantity > 0 && parsedQuantity <= availableQuantity){
                                availableQuantity -= parsedQuantity;
                                item[3] = String.valueOf(availableQuantity);
                                
                                itemMethod.updateFile(item, itemId);
                                break;
                            }
                            else{
                                System.out.println("\ninvalid input, cannot be negative, 0, or more than the available quantity\n");
                            }
                        }
                        catch(NumberFormatException e){
                            System.out.println("\ninvalid input\n");
                        }
                    }
                    
                    boolean existingItem = false;
                    for(String[] foundItem : foundItems){
                        if(foundItem[0].equalsIgnoreCase(itemId)){
                            int currentQuantity = Integer.parseInt(foundItem[3]);
                            int userQuantity = Integer.parseInt(quantity);
                            foundItem[3] = String.valueOf(currentQuantity + userQuantity);
                            existingItem = true;
                            break;
                        }
                    }
                    if(!existingItem){
                        foundItems.add(new String[]{item[0], item[1], item[2], quantity});
                    }
                    System.out.println("\nitem successfully added\n");
                    break;
                }
            }
        }
        display.displayCartItems(foundItems);
        return foundItems;
    }
    
    
    public ArrayList<String[]> deleteItems(ArrayList<String[]> foundItems){ 
        Item itemMethod = new Item();
        SMDisplay display = new SMDisplay();
        
        if(!foundItems.isEmpty()){
            display.displayCartItems(foundItems);
        }
        else{
            System.out.println("\n your cart is empty");
            return foundItems;
        }

        System.out.print("\ninsert item ID to delete amount (type done when done)\n");
        System.out.println("");
        System.out.println("");
        while(true){
            System.out.print("enter item ID: ");
            String itemId = scanner.nextLine().trim();

            if(itemId.equalsIgnoreCase("done")){
                System.out.println("");
                System.out.println("finalizing...");
                System.out.println("");
                break;
            }
            ArrayList<String[]> newFoundItems = new ArrayList<>();
            ArrayList<String[]> itemList = itemMethod.readFile();
            
            ArrayList<String> foundItemsID = new ArrayList<>();
            
            for(String[] foundItem : foundItems){
                foundItemsID.add(foundItem[0]);
            }
            
            boolean itemInCart = false;
            
            for(String[] item : itemList){
                if(item[0].equalsIgnoreCase(itemId) && foundItemsID.contains(itemId)){
                    itemInCart = true;
                    
                    int itemStockQuantity = Integer.parseInt(item[3]);
                    int foundItemQuantity = 0;
                    String quantity;
                    for(String[] foundItem : foundItems){
                        if(foundItem[0].equalsIgnoreCase(itemId)){
                            foundItemQuantity = Integer.parseInt(foundItem[3]);
                            break;
                        }
                    }
                    
                    while(true){
                        System.out.print("Enter quantity for " + itemId + " to delete (item quantity: " + foundItemQuantity +"): ");
                        quantity = scanner.nextLine().trim();
                        
                        try{
                            int parseQuantity = Integer.parseInt(quantity);
                            if(parseQuantity > 0 && parseQuantity <= foundItemQuantity){
                                foundItemQuantity -= parseQuantity;
                                itemStockQuantity += parseQuantity;
                                item[3] = String.valueOf(itemStockQuantity);
                                
                                itemMethod.updateFile(item, itemId);
                                break;
                            }
                            else{
                                System.out.println("\ninvalid input, cannot be negative, 0, or more than the item quantity\n");
                            }
                        }
                        catch(NumberFormatException e){
                            System.out.println("\ninvalid input\n");
                        }
                    }
                    for(String[] foundItem : foundItems){
                        if(foundItem[0].equalsIgnoreCase(itemId)){
                            if(foundItemQuantity != 0){
                                foundItem[3] = String.valueOf(foundItemQuantity);
                                newFoundItems.add(foundItem);
                            }
                        }
                        else{
                            newFoundItems.add(foundItem);
                        }
                    }
                    foundItems = newFoundItems;
                    System.out.println("\nitem successfully deleted\n");
                    break;
                }
            }
            if(!itemInCart){
                System.out.println("Item not in cart. Please try again.\n");
            }
        }
        display.displayCartItems(foundItems);
        return foundItems;
    }
    
    public ArrayList<String> requisitionPageFilterEditSM(String filterMode, String filterReqID, String filterSubmitterID, String filterSubmitterUsername, String filterApproverID, String filterApproverName, String filterSupplierID, String filterSupplierName, String filterItemID, String filterItemName, String filterQuantity, String filterStatus, String filterDateTime){
        SMDisplay display = new SMDisplay();
        
        ArrayList<String> filterEdit = new ArrayList<>();
        filterEdit.addAll(Arrays.asList(filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterItemID, filterItemName, filterQuantity, filterStatus, filterDateTime));
        
        while (true){
            display.requisitionPageFilterDisplayOption(filterEdit);
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your filter option: ");
                String option = scanner.nextLine();
                switch(option){
                    case "1":{
                        filterEdit.set(0, filterIDEditer(false, "Requisition", "PR", ""));
                        chooseOption = true;
                        break;
                    }
                    case "2":{
                        if (filterMode.equals("Deletion")){
                            System.out.println("Changing Submitter ID is unavailable during deletion for purchase requisition.");   // removed filterEdit.set(), if wrong, put back
                            chooseOption = true;
                            break;
                        }
                        else{
                            filterEdit.set(1, filterIDEditer(true, "Submitter", "SM", ""));     // not sure if correct if removed "PR"
                            chooseOption = true;
                            break;
                        }
                    }
                    case "3":{
                        if (filterMode.equals("Deletion")){
                            System.out.println("Changing Submitter Name is unavailable during deletion for purchase requisition.");   // removed filterEdit.set(), if wrong, put back
                            chooseOption = true;
                            break;
                        }
                        else{
                            filterEdit.set(2, filterDataEditer("Username", "Submitter"));
                            chooseOption = true;
                            break;
                        }
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
                        if (filterMode.equals("Deletion")){
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
                            if (filterMode.equals("Deletion")) {
                                switch (i){
                                    case 10:
                                        continue;       //need to understand again why continue, if not delet this case 10
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
    
    protected ArrayList<String> itemPageFilterEdit(String filterItemID, String filterItemName, String filterItemSellingPrice, String filterstocklevel){
        SMDisplay display = new SMDisplay();
        
        ArrayList<String> filterEdit = new ArrayList<String>();
        filterEdit.add(filterItemID);
        filterEdit.add(filterItemName);
        filterEdit.add(filterItemSellingPrice);
        filterEdit.add(filterstocklevel);
        
        while (true){
            display.itemPageFilterDisplayOption(filterEdit);
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your option: ");
                String option = scanner.nextLine();
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
    
    protected ArrayList<String> itemSupplierFilterEdit(String filterSupplierIDCPR, String filterSupplierNameCPR, String filterItemBuyingPriceCPR){
        SMDisplay smDisplay = new SMDisplay();
        
        ArrayList<String> filterEdit = new ArrayList<String>();
        filterEdit.add(filterSupplierIDCPR);
        filterEdit.add(filterSupplierNameCPR);
        filterEdit.add(filterItemBuyingPriceCPR);
        
        smDisplay.itemSupplierFilterDisplayOption(filterEdit);
        while (true){
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your option: ");
                String option = scanner.nextLine();
                switch(option){
                    case "1":{
                        System.out.println();
                        while (true){
                            System.out.print("Enter Supplier ID (input 'All' to show all): ");
                            String input = scanner.nextLine().toUpperCase();
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
}
