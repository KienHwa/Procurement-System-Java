package oodjassignmentfinal;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InventoryManager extends User{
    private static final Scanner scanner = new Scanner(System.in);
    
    public void imMenu(){
        String choice = "";
        do {
            IMDisplay display = new IMDisplay();
            choice = display.homePage();
            if (choice.equals("1")) {
                itemEntryMenu();
            } else if (choice.equals("2")) {
                Supplier supplierMethod = new Supplier();
                ItemsSupplier itemsSupplierMethod = new ItemsSupplier();
                Item itemMethod = new Item();
                Map<String, String> suppliers = supplierMethod.readMap();
                Map<String, List<String>> supplierItems = itemsSupplierMethod.readItemsSupplierMap();
                Map<String, String> items = itemMethod.readItemName();

                // Default filter values
                String nameFilter = null;
                String itemSuppliedFilter = null;
                Integer quantityFilter = null;
                String locationFilter = null;

                display.displayFilteredSuppliers(suppliers, supplierItems, items, nameFilter, itemSuppliedFilter, quantityFilter, locationFilter);
                supplierEntryMenu();
            } else if (choice.equals("3")) {
                viewStockLevels();
            } else if (choice.equals("4")) {
                updateStockLevels();
            } else if (choice.equals("0")) {
                System.out.println("Exiting Inventory Manager. Goodbye!");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("0"));
    }
    
// Item Entry Part
    
    public void itemEntryMenu() {
        Item itemMethod = new Item();
        IMDisplay display = new IMDisplay();
        
        Map<String, String> items = itemMethod.readMap();
        String priceFilter = null;
        String reorderFilter = null;
        String dateFilterYear = null;
        String dateFilterMonth = null;
        display.displayFilteredItems(items, priceFilter, reorderFilter, dateFilterYear, dateFilterMonth);

        String choice = display.itemEntryDisplayOption();

        if (choice.equals("1")) {
            searchItem();
        } else if (choice.equals("2")) {
            filterItems();
        } else if (choice.equals("3")) {
            itemEntryMenu();
        } else if (choice.equals("4")) {
            addItem();
        } else if (choice.equals("5")) {
            editItem();
        } else if (choice.equals("6")) {
            deleteItem();
        } else if (choice.equals("0")) {
            System.out.println("Returning to Main Menu...");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
    
    public void searchItem(){
        IMFilter filter = new IMFilter();
        System.out.print("Enter the Item ID or Name of the item you wish to view details for (or '0' to quit): ");
        String searchQuery = scanner.nextLine();

        if (searchQuery.equals("0")) {
            System.out.println("Exiting search function...");
            itemEntryMenu();
            return;
        }
        filter.searchItemFilter(searchQuery);
        itemEntryMenu();
    }
    
    public void filterItems(){
        IMFilter filter = new IMFilter();
        IMDisplay display = new IMDisplay();
        filter.filterItemsFilter(null, null, null, null);
        String choice = display.itemEntryDisplayOption();

        if (choice.equals("1")) {
            searchItem();
        } else if (choice.equals("2")) {
            filterItems();
        } else if (choice.equals("3")) {
            itemEntryMenu();
        } else if (choice.equals("4")) {
            addItem();
        } else if (choice.equals("5")) {
            editItem();
        } else if (choice.equals("6")) {
            deleteItem();
        } else if (choice.equals("0")) {
            System.out.println("Returning to Main Menu...");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
    
    public void addItem() {
        Item itemMethod = new Item();
        Map<String, String> items = itemMethod.readMap();
        String newItemID = itemMethod.generateNewID(items);

        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();

        boolean duplicateFound = items.values().stream()
                .anyMatch(line -> line.split(",")[1].equalsIgnoreCase(itemName));
        if (duplicateFound) {
            System.out.println("An item with this name already exists. Please try again.");
            itemEntryMenu();
            return;
        }

        System.out.print("Enter Selling Price: ");
        double sellingPrice = 0;
        while(true){
            try{
                sellingPrice = scanner.nextDouble();
                break;
            }catch(NumberFormatException e){
                System.out.println("Invalid input format. Please try again.");
            }
        }

        System.out.print("Enter Reorder Level: ");
        int reorderLevel = 0;
        while(true){
            try{
                reorderLevel = scanner.nextInt();
                scanner.nextLine();
                break;
            }catch(NumberFormatException e){
                System.out.println("Invalid input format. Please try again.");
            }
        }

        int stockLevel = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String updatedDate = LocalDateTime.now().format(formatter);

        String[] itemInfo = new String[] {newItemID,itemName,String.valueOf(sellingPrice),String.valueOf(stockLevel),String.valueOf(reorderLevel),updatedDate};
        itemMethod.createFile(itemInfo);

        itemEntryMenu();
    }
    
    public void editItem() {
        Item itemMethod = new Item();
        
        Map<String, String> items = itemMethod.readMap();
        IMDisplay display = new IMDisplay();
        String itemIDToEdit;

        while (true) {
            System.out.print("\nEnter the Item ID of the item you want to edit (or '0' to quit): ");
            itemIDToEdit = scanner.nextLine();

            if (itemIDToEdit.equals("0")) {
                System.out.println("Exiting edit function...");
                itemEntryMenu();
                return;
            }

            if (items.containsKey(itemIDToEdit)) {
                break;
            } else {
                System.out.println("Item with ID " + itemIDToEdit + " not found. Please try again.");
            }
        }

        String[] itemDetails = items.get(itemIDToEdit).split(",");
        String itemName = itemDetails[1];
        double itemSellingPrice = Double.parseDouble(itemDetails[2]);
        int reorderLevel = Integer.parseInt(itemDetails[4]);
        String updatedDate = itemDetails[5]; // Keep the current Updated Date

        boolean editMore = true;
        while (editMore) {
            String choice = display.editItemDisplayOption(itemIDToEdit, itemName, itemSellingPrice, reorderLevel);

            if (choice.equals("0")) {
                System.out.println("Exiting edit function...");
                itemEntryMenu();
                return;
            }

            switch (choice) {
                case "1" ->{
                    System.out.print("Enter new Item Name: ");
                    itemName = scanner.nextLine();
                    break;
                }
                case "2" ->{
                    System.out.print("Enter new Selling Price: ");
                    while(true){
                        try{
                        itemSellingPrice = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                        }catch(NumberFormatException e){
                            System.out.println("Invalid input format. Please try again.");
                        }
                    }
                }
                case "3" ->{
                    System.out.print("Enter new Reorder Level: ");
                    while(true){
                        try{
                        reorderLevel = scanner.nextInt();
                        scanner.nextLine();
                        break;
                        }catch(NumberFormatException e){
                            System.out.println("Invalid input format. Please try again.");
                        }
                    }
                }
                default ->{
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 0 to quit.");
                    continue;
                }
            }

            String moreChanges = display.editItemDisplayEditedItem(itemIDToEdit, itemName, itemSellingPrice, reorderLevel);
            editMore = moreChanges.equalsIgnoreCase("yes");
        }

        String[] updatedItem = new String[]{itemIDToEdit, itemName, String.valueOf(itemSellingPrice), itemDetails[3], String.valueOf(reorderLevel), updatedDate};
        itemMethod.updateFile(updatedItem, updatedItem[0]);
        
        System.out.println("Item with ID " + itemIDToEdit + " updated successfully.");
        itemEntryMenu();
    }
    
    public void deleteItem() {
        Item itemMethod = new Item();
        Map<String, String> items = itemMethod.readMap();

        while (true) {
            System.out.print("\nEnter the Item ID of the item you want to delete (or '0' to quit): ");
            String itemIDToDelete = scanner.nextLine();

            if (itemIDToDelete.equals("0")) {
                System.out.println("Exiting delete function...");
                itemEntryMenu();
                return;
            }

            if (items.containsKey(itemIDToDelete)) {
                itemMethod.deleteFile(itemIDToDelete);

                // Remove associated data from itemSupplier.txt
                itemMethod.removeItemFromFile("itemSupplier.txt", itemIDToDelete);

                // Remove associated data from itemsOrdered.txt
                itemMethod.removeItemFromFile("itemOrdered.txt", itemIDToDelete);
                               
                System.out.println("Item with ID " + itemIDToDelete + " deleted successfully.");
                itemEntryMenu();
                return;
            } else {
                System.out.println("Item with ID " + itemIDToDelete + " not found. Please try again.");
            }
        }
    }
    
// Supplier Entry Part
    
    public void supplierEntryMenu() {
        String nameFilter = null;
        String itemSuppliedFilter = null;
        Integer quantityFilter = null;
        String locationFilter = null;
        
        IMDisplay display = new IMDisplay();
        Supplier supplierMethod = new Supplier();
        ItemsSupplier itemSupplierMethod = new ItemsSupplier();
        Item itemMethod = new Item();

        String choice = display.supplierEntryDisplayOption();

        Map<String, String> suppliers = supplierMethod.readMap();
        Map<String, List<String>> supplierItems = itemSupplierMethod.readItemsSupplierMap();
        Map<String, String> items = itemMethod.readItemName();

        switch (choice) {
            case "1" ->{
                searchSupplier();
                display.displayFilteredSuppliers(suppliers, supplierItems, items, nameFilter, itemSuppliedFilter, quantityFilter, locationFilter);
                supplierEntryMenu();
                break;
            }
            case "2" ->{
                filterSuppliers(new String[]{nameFilter}, new String[]{itemSuppliedFilter}, new Integer[]{quantityFilter}, new String[]{locationFilter});
                break;
            }
            case "3" ->{
                display.displayFilteredSuppliers(suppliers, supplierItems, items, nameFilter, itemSuppliedFilter, quantityFilter, locationFilter);
                supplierEntryMenu();
                break;
            }
            case "4" ->{
                addSupplier();
                suppliers = supplierMethod.readMap();
                supplierItems = itemSupplierMethod.readItemsSupplierMap();
                display.displayFilteredSuppliers(suppliers, supplierItems, items, nameFilter, itemSuppliedFilter, quantityFilter, locationFilter);
                supplierEntryMenu();
                break;
            }
            case "5" ->{
                editSupplier();
                suppliers = supplierMethod.readMap();
                supplierItems = itemSupplierMethod.readItemsSupplierMap();
                display.displayFilteredSuppliers(suppliers, supplierItems, items, nameFilter, itemSuppliedFilter, quantityFilter, locationFilter);
                supplierEntryMenu();
                break;
            }
            case "6" ->{
                deleteSupplier();
                suppliers = supplierMethod.readMap();
                supplierItems = itemSupplierMethod.readItemsSupplierMap();
                display.displayFilteredSuppliers(suppliers, supplierItems, items, nameFilter, itemSuppliedFilter, quantityFilter, locationFilter);
                supplierEntryMenu();
                break;
            }
            case "0" ->{
                System.out.println("Returning to Main Menu...");
                return;
            }
            default ->{
                System.out.println("Invalid choice. Please try again.");
                break;
            }
        }
    }
    
    public void searchSupplier(){
        IMFilter filter = new IMFilter();
        System.out.print("Enter the Supplier ID or Name to view details (or '0' to quit): ");
        String searchQuery = scanner.nextLine();

        if (searchQuery.equals("0")) {
            System.out.println("Exiting search function...");
            supplierEntryMenu();
            return;
        }
        filter.searchSupplierFilter(searchQuery);
    }
    
    public void filterSuppliers(String[] nameFilter, String[] itemSuppliedFilter, Integer[] quantityFilter, String[] locationFilter){
        IMFilter filter = new IMFilter();
        filter.filterSuppliersFilter(nameFilter, itemSuppliedFilter, quantityFilter, locationFilter);
        supplierEntryMenu();
    }
    
    public void addSupplier() {
        IMDisplay display = new IMDisplay();
        Supplier supplierMethod = new Supplier();
        Item itemMethod = new Item();
        ItemsSupplier itemSupplierMethod = new ItemsSupplier();
        
        Map<String, String> suppliers = supplierMethod.readMap();
        Map<String, String> items = itemMethod.readItemName();

        String newSupplierID = supplierMethod.generateNewID(suppliers);
        System.out.print("Enter Supplier Name: ");
        String supplierName = scanner.nextLine();

        System.out.print("Enter Contact No: ");
        String contactNo = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        String[] supplierInfo = new String[]{newSupplierID,supplierName,contactNo,address};
        supplierMethod.createFile(supplierInfo);

        System.out.println("\n--- Add Items Supplied by This Supplier ---");
        boolean addMoreItems = true;

        while (addMoreItems) {
            List<String> itemList = display.displayAvailableItems(items);
            
            System.out.print("Select an item number to add: ");
            
            int itemChoice = 0;
            while(true){
                try{
                    itemChoice = scanner.nextInt();
                    break;
                }catch(NumberFormatException e){
                    System.out.println("Invalid input format. Please try again.");
                }
            }

            if (itemChoice > 0 && itemChoice <= itemList.size()) {
                String selectedItemName = itemList.get(itemChoice - 1);
                String selectedItemID = getKeyByValue(items, selectedItemName);

                System.out.print("Enter buying price for " + selectedItemName + ": ");
                double buyingPrice = scanner.nextDouble();
                scanner.nextLine();

                String[] itemsSupplierInfo = new String[]{selectedItemID,supplierName,String.valueOf(buyingPrice)};
                itemSupplierMethod.createFile(itemsSupplierInfo);

                System.out.print("Do you want to add another item supplied by this supplier? (yes/no): ");
                String response = scanner.nextLine();
                addMoreItems = response.equalsIgnoreCase("yes");
            } else {
                System.out.println("Invalid item selection. Please try again.");
            }
        }
        System.out.println("Recorded successfully.");
    }
    
    public String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public void editSupplier() {
        Supplier supplierMethod = new Supplier();
        Item itemMethod = new Item();
        ItemsSupplier itemSupplierMethod = new ItemsSupplier();
        IMDisplay display = new IMDisplay();
        
        Map<String, String> suppliers = supplierMethod.readMap();
        Map<String, String> items = itemMethod.readItemName();
        String supplierIDToEdit = null;
        String supplierNameToEdit = null;
        String supplierOldName = "";
        String searchQuery;

        System.out.print("\nEnter the Supplier ID or Name of the supplier you want to edit (or '0' to quit): ");
        searchQuery = scanner.nextLine();

        if (searchQuery.equals("0")) {
            System.out.println("Exiting edit function...");
            supplierEntryMenu();
            return;
        }

        boolean supplierFound = false;
        for (Map.Entry<String, String> entry : suppliers.entrySet()) {
            String[] supplierDetails = entry.getValue().split(",");
            if (entry.getKey().equalsIgnoreCase(searchQuery) || supplierDetails[1].equalsIgnoreCase(searchQuery)) {
                supplierIDToEdit = entry.getKey();
                supplierNameToEdit = supplierDetails[1];
                supplierFound = true;
                break;
            }
        }

        if (!supplierFound) {
            System.out.println("Supplier with the given ID or Name not found. Please try again.");
            return;
        }

        String[] supplierDetails = suppliers.get(supplierIDToEdit).split(",");
        String contactNo = supplierDetails[2];
        String address = supplierDetails[3];
        List<String> suppliedItems = itemSupplierMethod.getItemsSuppliedBySupplier(supplierNameToEdit);

        display.editSupplierDisplaySupplier(supplierIDToEdit, supplierNameToEdit, contactNo, address, suppliedItems);

        boolean editMore = true;
        while (editMore) {
            String choice = display.editSupplierDisplayOption();

            switch (choice) {
                case "1" ->{
                    System.out.print("Enter new Supplier Name: ");
                    String newName = scanner.nextLine();
                    supplierOldName = newName.isEmpty() ? supplierOldName : supplierNameToEdit;
                    supplierNameToEdit = newName.isEmpty() ? supplierNameToEdit : newName;
                    break;
                }
                case "2" ->{
                    System.out.print("Enter new Contact No: ");
                    String newContactNo = scanner.nextLine();
                    contactNo = newContactNo.isEmpty() ? contactNo : newContactNo;
                    break;
                }
                case "3" ->{
                    System.out.print("Enter new Address: ");
                    String newAddress = scanner.nextLine();
                    address = newAddress.isEmpty() ? address : newAddress;
                    break;
                }
                case "4" ->{
                    System.out.println("\nCurrent Items Supplied: " + (suppliedItems.isEmpty() ? "None" : String.join(", ", suppliedItems)));

                    boolean modifyItems = true;
                    while (modifyItems) {
                        String itemActionChoice = display.editItemsSuppliedDisplayOption();

                        if (itemActionChoice.equals("1")) {
                            List<String> itemList = display.displayAvailableItems(items);
                            
                            System.out.print("Select an item number to add: ");
                            int itemChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (itemChoice > 0 && itemChoice <= itemList.size()) {
                                String selectedItemName = itemList.get(itemChoice - 1);
                                String selectedItemID = getKeyByValue(items, selectedItemName);
                                if (!suppliedItems.contains(selectedItemName)) {
                                    System.out.print("Enter the buying price for " + selectedItemName + ": ");
                                    double buyingPrice = scanner.nextDouble();
                                    scanner.nextLine();

                                    suppliedItems.add(selectedItemName);
                                    System.out.println(selectedItemName + " added.");
                                    itemSupplierMethod.createFile(new String[]{selectedItemID, supplierNameToEdit, String.valueOf(buyingPrice)});
                                } else {
                                    System.out.println("Item already supplied.");
                                }
                            } else {
                                System.out.println("Invalid item selection. Please try again.");
                            }
                        } else if (itemActionChoice.equals("2")) {
                            System.out.println("\nCurrent Items Supplied:");
                            for (int i = 0; i < suppliedItems.size(); i++) {
                                System.out.printf("%d. %s\n", i + 1, suppliedItems.get(i));
                            }
                            System.out.print("Enter the number of the item to remove: ");
                            int removeChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (removeChoice > 0 && removeChoice <= suppliedItems.size()) {
                                String itemToRemove = suppliedItems.get(removeChoice - 1);
                                suppliedItems.remove(itemToRemove);
                                System.out.println(itemToRemove + " removed.");
                                itemSupplierMethod.updateSupplierItemsInFile(supplierNameToEdit, supplierOldName, suppliedItems, items);
                            } else {
                                System.out.println("Invalid selection. Please try again.");
                            }
                        } else if (itemActionChoice.equals("0")) {
                            modifyItems = false;
                        } else {
                            System.out.println("Invalid action. Please enter '1', '2', or '0'.");
                        }
                    }
                    break;
                }
                case "0" ->{
                    System.out.println("No edits needed.");
                    editMore = false;
                    continue;
                }
                default ->{
                    System.out.println("Invalid choice. Please select 1, 2, 3, 4, or 0 to quit.");
                    continue;
                }
            }

            supplierMethod.updateSupplierInFile(supplierIDToEdit, supplierNameToEdit, contactNo, address);
            itemSupplierMethod.updateSupplierItemsInFile(supplierNameToEdit, supplierOldName, suppliedItems, items);
            supplierOldName = "";
            
            System.out.println("Supplier updated successfully.");
            
            System.out.print("Anymore details to edit? (yes/no): ");
            String moreChanges = scanner.nextLine();
            editMore = moreChanges.equalsIgnoreCase("yes");
        }
    }
    
    public void deleteSupplier() {
        Supplier supplierMethod = new Supplier();
        
        Map<String, String> suppliers = supplierMethod.readMap();

        System.out.print("Enter the Supplier ID or Name of the supplier you want to delete (or '0' to quit): ");
        String searchQuery = scanner.nextLine();

        if (searchQuery.equals("0")) {
            System.out.println("Exiting delete function...");
            supplierEntryMenu();
            return;
        }

        String supplierIDToDelete = null;
        String supplierNameToDelete = null;
        for (Map.Entry<String, String> entry : suppliers.entrySet()) {
            String[] supplierDetails = entry.getValue().split(",");
            if (entry.getKey().equalsIgnoreCase(searchQuery) || supplierDetails[1].equalsIgnoreCase(searchQuery)) {
                supplierIDToDelete = entry.getKey();
                supplierNameToDelete = supplierDetails[1];
                break;
            }
        }

        if (supplierIDToDelete != null) {
            suppliers.remove(supplierIDToDelete);
            supplierMethod.createFile(suppliers);

            try {
                List<String> lines = Files.readAllLines(Paths.get("itemSupplier.txt"));
                List<String> updatedLines = new ArrayList<>();

                for (String line : lines) {
                  String[] details = line.split(",");
                    if (details.length >= 2 && !details[1].equals(supplierNameToDelete)) {
                        updatedLines.add(line);
                    }
                }

                Files.write(Paths.get("itemSupplier.txt"), updatedLines);

            } catch (IOException e) {
                System.out.println("Error updating itemSupplier.txt: " + e.getMessage());
            }

            System.out.println("Supplier with ID " + supplierIDToDelete + " deleted successfully.");
        } else {
            System.out.println("Supplier with the given ID or Name not found.");
        }
    }
    
// View Stock Levels Part
    
    public void viewStockLevels() {
        Item itemMethod = new Item();
        Map<String, String> items = itemMethod.readMap();
        String stockLevelFilter = null;
        String yearFilter = null;
        String monthFilter = null;
        
        IMDisplay display = new IMDisplay();

        // Display the original list of items without any filters
        display.displayFilteredStockLevels(items, stockLevelFilter, yearFilter, monthFilter);

        boolean continueViewing = true;

        while (continueViewing) {
            String choice = display.viewStockLevelDisplayOption();

            switch (choice) {
                case "1" ->{
                    searchStockLevel(items, stockLevelFilter, yearFilter, monthFilter);
                    break;
                }
                case "2" ->{
                    applyFilters(items);
                    break;
                }
                case "3" ->{
                    // Clear filters and show the original list
                    stockLevelFilter = null;
                    yearFilter = null;
                    monthFilter = null;
                    display.displayFilteredStockLevels(items, stockLevelFilter, yearFilter, monthFilter);
                    break;
                }
                case "0" ->{
                    continueViewing = false;
                    break;
                }
                default ->{
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 0.");
                    break;
                }
            }
        }
    }
    
    public void searchStockLevel(Map<String, String> items, String stockLevelFilter, String yearFilter, String monthFilter) {
        IMDisplay display = new IMDisplay();
        IMFilter filter = new IMFilter();
        
        System.out.print("\nEnter the Item ID or Name of the item to search (or '0' to quit): ");
        String searchQuery = scanner.nextLine();

        if (searchQuery.equals("0")) {
            System.out.println("Exiting search function...");
            return;
        }

        filter.searchStockLevelFilter(items, searchQuery);

        // Show the original list after search results
        display.displayFilteredStockLevels(items, stockLevelFilter, yearFilter, monthFilter);
    }
    
    public void applyFilters(Map<String, String> items) {
        String stockLevelFilter = null;
        String yearFilter = null;
        String monthFilter = null;
        
        IMFilter filter = new IMFilter();
        filter.filterStockLevelFilter(items, stockLevelFilter, yearFilter, monthFilter);
    }
    
    public void updateStockLevels() {
        ItemsOrdered itemOrderMethod = new ItemsOrdered();
        Item itemMethod = new Item();
        IMDisplay display = new IMDisplay();

        boolean continueUpdating = true;
        while (continueUpdating) {
            List<String[]> pendingUpdates = itemOrderMethod.readFile(); // Load pending updates from itemsOrdered.txt
            Map<String, String> items = itemMethod.readMap(); // Load items from item.txt
            
            if (pendingUpdates.isEmpty()) {
                System.out.println("No pending stock updates.");
                return;
            }

            // Display Pending Stock Updates
            int choice = display.updateStockLevelDisplayStockLevel(pendingUpdates, items);

            if (choice == 0) {
                return; // Exit to the main menu
            }

            if (choice < 1 || choice > pendingUpdates.size()) {
                System.out.println("Invalid selection. Please try again.");
                continue;
            }

            // Process the selected update
            String[] selectedUpdate = pendingUpdates.get(choice - 1);
            String currentEditingPOID = selectedUpdate[0];
            String itemID = selectedUpdate[1];
            int quantityToAdd = Integer.parseInt(selectedUpdate[2]);
            String purchaseOrderID = selectedUpdate[0];

            String itemDetails = items.get(itemID);

            if (itemDetails == null) {
                System.out.println("Item not found in inventory. Please check the data.");
                continue;
            }

            String[] details = itemDetails.split(",");
            String itemName = details[1];
            int currentStockLevel = Integer.parseInt(details[3]);

            String confirmation = display.updateStockLevelDisplayItem(purchaseOrderID, itemID, itemName, currentStockLevel, quantityToAdd);

            if (confirmation.equalsIgnoreCase("yes")) {
                // Update stock levels in item.txt
                currentStockLevel += quantityToAdd;
                details[3] = String.valueOf(currentStockLevel);

                // Update the updated date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                details[5] = LocalDateTime.now().format(formatter);

                itemMethod.updateFile(new String[]{details[0],details[1],details[2],details[3],details[4],details[5]}, itemID);

                // Remove the processed update from the pending list
                String[] itemOrderInfo = pendingUpdates.get(choice - 1);
                System.out.println(itemOrderInfo[0]);
                System.out.println(itemOrderInfo[1]);
                itemOrderMethod.deleteFile(itemOrderInfo[0], itemOrderInfo[1]);
                
                boolean itemOrderedLeft = checkAnyItemOrderedLeft(currentEditingPOID);
                if(!itemOrderedLeft){
                    purchaseOrderReceived(currentEditingPOID);
                }

                System.out.println("Stock levels updated successfully.");
            } else {
                System.out.println("Update cancelled. No changes made.");
            }
        }
    }
    
    public boolean checkAnyItemOrderedLeft(String poId){
        ItemsOrdered itemsOrderedMethod = new ItemsOrdered();
        
        ArrayList<String[]> itemsOrderedList = itemsOrderedMethod.readFile();
        for(String[] itemOrdered : itemsOrderedList){
            if(itemOrdered[0].equals(poId)){
                return true;
            }
        }
        return false;
    }
    
    public void purchaseOrderReceived(String poId){
        PurchaseOrder poMethod = new PurchaseOrder();
        ArrayList<String[]> poList = poMethod.readFile();
        for(String[] po : poList){
            if(po[0].equals(poId)){
                po[4] = "Received";
                poMethod.updateFile(po, poId);
                return;
            }
        }
    }
}
