package oodjassignmentfinal;

import java.util.*;

public class IMDisplay extends Display{
    
    private static final Scanner scanner = new Scanner(System.in);
    
    @Override
    public String homePage(){
        
        IMNotification notification = new IMNotification();
        String pendingUpdates = notification.getNotification();

        System.out.println("\n=== Inventory Manager Main Menu ===");
        if (!pendingUpdates.equals("0")) {
                System.out.println(">>> Notification: " + pendingUpdates+ " stock updates are pending. <<<");
            }
            
        System.out.println("1. Item Entry");
        System.out.println("2. Supplier Entry");
        System.out.println("3. View Stock Levels");
        System.out.println("4. Update Stock Levels");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }
    
    public void displayFilteredItems(Map<String, String> items, String priceFilter, String reorderFilter, String yearFilter, String monthFilter) {
        IMFilter filter = new IMFilter();
        System.out.println("\n========================================== Filter Settings ==========================================");
        System.out.printf("Selling Price: %-20s | Reorder Level: %-15s | Date: %s-%s\n",
                (priceFilter != null ? priceFilter : "All"),
                (reorderFilter != null ? reorderFilter : "All"),
                (yearFilter != null ? yearFilter : "XX"),
                (monthFilter != null ? monthFilter : "XX"));
        System.out.println("=====================================================================================================");

        System.out.println("\n-------------------------------------- List of Filtered Items ---------------------------------------");
        System.out.printf("| %-15s | %-20s | %-15s | %-15s | %-20s |\n",
                "Item ID", "Item Name", "Selling Price", "Reorder Level", "Updated Date");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        boolean hasResults = false;

        for (String item : items.values()) {
            String[] details = item.split(",");
            if (details.length < 6) continue;

            double sellingPrice = Double.parseDouble(details[2]);
            int reorderLevel = Integer.parseInt(details[4]);
            String updatedDate = details[5];

            boolean matchesPrice = (priceFilter == null || filter.matchesPriceFilter(sellingPrice, priceFilter));
            boolean matchesReorder = (reorderFilter == null || filter.matchesReorderFilter(reorderLevel, reorderFilter));
            boolean matchesDate = (yearFilter == null || monthFilter == null || filter.matchesDateFilter(updatedDate, yearFilter, monthFilter));

            if (matchesPrice && matchesReorder && matchesDate) {
                System.out.printf("| %-15s | %-20s | %-15.2f | %-15d | %-20s |\n",
                        details[0], details[1], sellingPrice, reorderLevel, updatedDate);
                hasResults = true;
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------------------");

        if (!hasResults) {
            System.out.println("No items found matching the selected filters.");
        }
    }
    
    public String itemEntryDisplayOption(){
        System.out.println("\n--- Item Entry Menu ---");
        System.out.println("1. Search for Item");
        System.out.println("2. Filter Items");
        System.out.println("3. Clear Filter");
        System.out.println("4. Add New Item");
        System.out.println("5. Edit Item");
        System.out.println("6. Delete Item");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }
    
    public boolean searchItemDisplayItem(boolean itemFound, Map<String, String> items, String searchQuery){
        for (String line : items.values()) {
            String[] itemDetails = line.split(",");

            if (itemDetails.length >= 6) {
                String itemID = itemDetails[0];
                String itemName = itemDetails[1];
                String sellingPrice = itemDetails[2];
                String reorderLevel = itemDetails[4];
                String updatedDate = itemDetails[5];

                if (itemID.equalsIgnoreCase(searchQuery) || itemName.equalsIgnoreCase(searchQuery)) {
                    System.out.println("\nItem Found:");
                    System.out.println("----------------------------");
                    System.out.printf("Item ID       : %s\n", itemID);
                    System.out.printf("Item Name     : %s\n", itemName);
                    System.out.printf("Selling Price : %s\n", sellingPrice);
                    System.out.printf("Reorder Level : %s\n", reorderLevel);
                    System.out.printf("Last Updated  : %s\n", updatedDate);
                    System.out.println("----------------------------");
                    itemFound = true;
                    break;
                }
            }
        }
        return itemFound;
    }
    
    public String itemFilterDisplayOption(){
        System.out.println("\n--- Select a Filter ---");
        System.out.println("1. Filter by Selling Price");
        System.out.println("2. Filter by Reorder Level");
        System.out.println("3. Filter by Last Updated Date");
        System.out.println("0. Apply Filters and Show Results");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }
    
    public String itemFilterPriceOption(){
        System.out.println("\nFilter by Selling Price:");
        System.out.println("1. Below 100");
        System.out.println("2. 100 - 1000");
        System.out.println("3. 1000 - 5000");
        System.out.println("4. 5000 - 10000");
        System.out.println("5. Above 10000");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }
    
    public String itemFilterReorderOption(){
        System.out.println("\nFilter by Reorder Level:");
        System.out.println("1. Below 100");
        System.out.println("2. 100 - 500");
        System.out.println("3. 500 - 1000");
        System.out.println("4. Above 1000");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }
    
    public String editItemDisplayOption(String itemIDToEdit, String itemName, double itemSellingPrice, int reorderLevel){
        System.out.println("\n------------------- Editing Item -------------------");
            System.out.printf("Item ID: %s\n", itemIDToEdit);
            System.out.printf("1. Item Name: %s\n", itemName);
            System.out.printf("2. Item Selling Price: %.2f\n", itemSellingPrice);
            System.out.printf("3. Reorder Level: %d\n", reorderLevel);

            System.out.print("Which detail would you like to edit (1 for Name, 2 for Selling Price, 3 for Reorder Level, '0' to quit): ");
            return scanner.nextLine();
    }
    
    public String editItemDisplayEditedItem(String itemIDToEdit, String itemName, double itemSellingPrice, int reorderLevel){
        System.out.println("\nEdit successful! Updated details:");
        System.out.printf("Item ID: %s, Name: %s\n", itemIDToEdit, itemName);
        System.out.printf("Item Selling Price: %.2f\n", itemSellingPrice);
        System.out.printf("Reorder Level: %d\n", reorderLevel);

        System.out.print("Do you want to make more changes? (yes/no): ");
        return scanner.nextLine();
    }
    
    public void displayFilteredSuppliers(Map<String, String> suppliers, Map<String, List<String>> supplierItems, Map<String, String> items,
                                          String nameFilter, String itemSuppliedFilter, Integer quantityFilter, String locationFilter) {
        System.out.println("\n========================================================== Filter Settings ==========================================================================");
        System.out.printf("     Item Supplied Filter: %-15s |             Quantity Filter: %-15s |             Location Filter: %-35s\n",
                (itemSuppliedFilter != null ? itemSuppliedFilter : "All"),
                (quantityFilter != null ? quantityFilter : "All"),
                (locationFilter != null ? locationFilter : "All"));
        System.out.println("=====================================================================================================================================================");

        System.out.println("\n----------------------------------------------------- List of Filtered Suppliers --------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-80s |\n", "Supplier ID", "Supplier Name", "Contact No", "Item Supplied");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");

        boolean hasResults = false;

        for (Map.Entry<String, String> entry : suppliers.entrySet()) {
            String supplierID = entry.getKey();
            String[] details = entry.getValue().split(",");
            String supplierName = details[1];
            String contactNo = details[2];
            String address = "Address: " + details[3];
            List<String> itemIDs = supplierItems.getOrDefault(supplierName, Collections.singletonList("None"));

            List<String> itemNames = new ArrayList<>();
            for (String itemID : itemIDs) {
                String itemName = items.getOrDefault(itemID, "No items supplied (previous items deleted)");
                itemNames.add(itemName);
            }

            boolean matchesName = (nameFilter == null || supplierName.contains(nameFilter));
            boolean matchesItem = (itemSuppliedFilter == null || itemNames.contains(itemSuppliedFilter));
            boolean matchesQuantity = (quantityFilter == null || itemNames.size() == quantityFilter);
            boolean matchesLocation = (locationFilter == null || details[3].endsWith(locationFilter));

            if (matchesName && matchesItem && matchesQuantity && matchesLocation) {
                System.out.printf("| %-11s | %-30s | %-15s | %-80s |\n", supplierID, supplierName, contactNo, String.join(", ", itemNames));
                List<String> wrappedAddressLines = wrapText(address, 58);
                for (String line : wrappedAddressLines) {
                    System.out.printf("| %-62s | %-80s |\n", line.trim(), "");
                }
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
                hasResults = true;
            }
        }

        if (!hasResults) {
            System.out.println("No suppliers found matching the selected filters.");
        }
    }
    
    private List<String> wrapText(String text, int lineLength) {
        List<String> wrappedLines = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + lineLength, text.length());
            wrappedLines.add(text.substring(start, end));
            start += lineLength;
        }
        return wrappedLines;
    }
    
    public String supplierEntryDisplayOption(){
        System.out.println("\n--- Supplier Entry Menu ---");
        System.out.println("1. Search for Supplier");
        System.out.println("2. Filter Suppliers");
        System.out.println("3. Clear Filter");
        System.out.println("4. Add New Supplier");
        System.out.println("5. Edit Supplier");
        System.out.println("6. Delete Supplier");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }
    
    public boolean searchSupplierDisplaySupplier(boolean supplierFound, Map<String, String> suppliers, String searchQuery){
        ItemsSupplier itemSupplierMethod = new ItemsSupplier();
        for (String line : suppliers.values()) {
            String[] supplierDetails = line.split(",");
            if (supplierDetails.length >= 4) {
                String supplierID = supplierDetails[0];
                String supplierName = supplierDetails[1];
                String contactNo = supplierDetails[2];
                String address = supplierDetails[3];

                if (supplierID.equalsIgnoreCase(searchQuery) || supplierName.equalsIgnoreCase(searchQuery)) {
                    System.out.println("\nSupplier Found:");
                    System.out.printf("Supplier ID    : %s\n", supplierID);
                    System.out.printf("Supplier Name  : %s\n", supplierName);
                    System.out.printf("Contact No     : %s\n", contactNo);
                    System.out.printf("Address        : %s\n", address);

                    List<String> suppliedItems = itemSupplierMethod.getItemsSuppliedBySupplier(supplierName);
                    System.out.println("Items Supplied : " + String.join(", ", suppliedItems));
                    supplierFound = true;
                    break;
                }
            }
        }
        return supplierFound;
    }
    
    public String supplierFilterDisplayOption(){
        System.out.println("\nFilter Options:");
        System.out.println("1. Item Supplied");
        System.out.println("2. Quantity of Items Supplied");
        System.out.println("3. Location");
        System.out.print("Select a filter option (1, 2, or 3): ");
        return scanner.nextLine();
    }
    
    public void editSupplierDisplaySupplier(String supplierID, String supplierNameToEdit, String contactNo, String address, List<String> suppliedItems){
        System.out.println("\nSupplier Details:");
        System.out.printf("Supplier ID    : %s\n", supplierID);
        System.out.printf("Supplier Name  : %s\n", supplierNameToEdit);
        System.out.printf("Contact No     : %s\n", contactNo);
        System.out.printf("Address        : %s\n", address);
        System.out.println("Items Supplied : " + (suppliedItems.isEmpty() ? "None" : String.join(", ", suppliedItems)));
    }
    
    public String editSupplierDisplayOption(){
        System.out.println("\nWhat would you like to edit?");
        System.out.println("1. Supplier Name");
        System.out.println("2. Contact No");
        System.out.println("3. Address");
        System.out.println("4. Items Supplied");
        System.out.println("0. No edits needed");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }
    
    public String editItemsSuppliedDisplayOption(){
        System.out.println("\nWould you like to:");
        System.out.println("1. Add items");
        System.out.println("2. Remove items");
        System.out.println("0. None");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }
    
    public List<String> displayAvailableItems(Map<String, String> items){
        System.out.println("\nAvailable Items:");
        List<String> itemList = new ArrayList<>(items.values());
        for (int i = 0; i < itemList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, itemList.get(i));
        }
        return itemList;
    }
    
    public void displayFilteredStockLevels(Map<String, String> items, String stockLevelFilter, String yearFilter, String monthFilter) {
        IMFilter filter = new IMFilter();
        
        System.out.println("\n========================================== Filter Settings ==========================================");
        System.out.printf("Stock Level: %-20s | Date: %s-%s\n",
                (stockLevelFilter != null ? stockLevelFilter : "All"),
                (yearFilter != null ? yearFilter : "XX"),
                (monthFilter != null ? monthFilter : "XX"));
        System.out.println("=====================================================================================================");

        System.out.println("\n-------------------------------------- List of Filtered Items ---------------------------------------");
        System.out.printf("| %-15s | %-20s | %-15s | %-20s |\n",
                "Item ID", "Item Name", "Stock Level", "Updated Date");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        boolean hasResults = false;

        for (String item : items.values()) {
            String[] details = item.split(",");
            if (details.length < 6) continue;

            int stockLevel = Integer.parseInt(details[3]);
            String updatedDate = details[5];

            boolean matchesStockLevel = (stockLevelFilter == null || filter.matchesStockLevelFilter(stockLevel, stockLevelFilter));
            boolean matchesDate = (yearFilter == null || monthFilter == null || filter.matchesDateFilter(updatedDate, yearFilter, monthFilter));

            if (matchesStockLevel && matchesDate) {
                System.out.printf("| %-15s | %-20s | %-15d | %-20s |\n",
                        details[0], details[1], stockLevel, updatedDate);
                hasResults = true;
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------------------");

        if (!hasResults) {
            System.out.println("No items found matching the selected filters.");
        }
    }
    
    public String viewStockLevelDisplayOption(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- View Stock Levels Menu ---");
        System.out.println("1. Search Item");
        System.out.println("2. Filter Items");
        System.out.println("3. Clear Filters");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        return choice;
    }
    
    public String stockLevelFilterDisplayOption(){
        System.out.println("\n--- Select a Filter ---");
        System.out.println("1. Filter by Stock Level");
        System.out.println("2. Filter by Last Updated Date");
        System.out.println("0. Apply Filters and Show Results");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        return choice;
    }
    
    public String stockLevelFilterStockLevelOption(){
        System.out.println("\nFilter by Stock Level:");
        System.out.println("1. Below 100");
        System.out.println("2. 100 - 500");
        System.out.println("3. 500 - 1000");
        System.out.println("4. Above 1000");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        return choice;
    }
    
    public int updateStockLevelDisplayStockLevel(List<String[]> pendingUpdates, Map<String, String> items){
        // Display Pending Stock Updates
        System.out.println("\n--- Update Stock Levels ---");
        System.out.println("Pending Stock Updates:");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-15s | %-20s | %-10s | %-10s |\n", "No.", "Item ID", "Item Name", "Quantity", "Order ID");
        System.out.println("-------------------------------------------------------------------------------");

        for (int i = 0; i < pendingUpdates.size(); i++) {
            String[] update = pendingUpdates.get(i);
            String itemID = update[1];
            String purchaseOrderID = update[0];
            String quantity = update[2];

            String itemName = "Unknown";
            if (items.containsKey(itemID)) {
                String[] itemDetails = items.get(itemID).split(",");
                if (itemDetails.length >= 2) {
                    itemName = itemDetails[1];
                }
            }

            System.out.printf("| %-5d | %-15s | %-20s | %-10s | %-10s |\n", i + 1, itemID, itemName, quantity, purchaseOrderID);
        }
        System.out.println("-------------------------------------------------------------------------------");

        System.out.print("Select a number to update stock (or '0' to go back): ");
        while(true){
            try{
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            }catch(NumberFormatException e){
                System.out.println("Invalid input format. Please try again.");
            }
        }
    }
    
    public String updateStockLevelDisplayItem(String purchaseOrderID, String itemID, String itemName, int currentStockLevel, int quantityToAdd){
        System.out.println("\n--- Selected Item ---");
        System.out.printf("Purchase Order ID: %s\n", purchaseOrderID);
        System.out.printf("Item ID          : %s\n", itemID);
        System.out.printf("Item Name        : %s\n", itemName);
        System.out.printf("Current Stock    : %d\n", currentStockLevel);
        System.out.printf("Stock Added      : %d\n", quantityToAdd);
        System.out.printf("New Stock        : %d\n", currentStockLevel + quantityToAdd);
        System.out.print("Confirm update? (yes/no): ");
        return scanner.nextLine();
    }
}
