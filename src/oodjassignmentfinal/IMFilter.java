package oodjassignmentfinal;

import java.util.*;

public class IMFilter extends Filter{
    private static final Scanner scanner = new Scanner(System.in);
    
    public boolean matchesPriceFilter(double sellingPrice, String priceFilter) {
        switch (priceFilter) {
            case "Below 100": return sellingPrice < 100;
            case "100 - 1000": return sellingPrice >= 100 && sellingPrice <= 1000;
            case "1000 - 5000": return sellingPrice >= 1000 && sellingPrice <= 5000;
            case "5000 - 10000": return sellingPrice >= 5000 && sellingPrice <= 10000;
            case "Above 10000": return sellingPrice > 10000;
            default: return true;
        }
    }

    public boolean matchesReorderFilter(int reorderLevel, String reorderFilter) {
        switch (reorderFilter) {
            case "Below 100": return reorderLevel < 100;
            case "100 - 500": return reorderLevel >= 100 && reorderLevel <= 500;
            case "500 - 1000": return reorderLevel >= 500 && reorderLevel <= 1000;
            case "Above 1000": return reorderLevel > 1000;
            default: return true;
        }
    }

    public boolean matchesDateFilter(String updatedDate, String yearFilter, String monthFilter) {
        String[] dateParts = updatedDate.split("/");
        return dateParts[2].equals(yearFilter) && dateParts[1].equals(monthFilter);
    }
    
    public boolean matchesStockLevelFilter(int stockLevel, String stockLevelFilter) {
        switch (stockLevelFilter) {
            case "Below 100": return stockLevel < 100;
            case "100 - 500": return stockLevel >= 100 && stockLevel < 500;
            case "500 - 1000": return stockLevel >= 500 && stockLevel < 1000;
            case "Above 1000": return stockLevel >= 1000;
            default: return true;
        }
    }
    
    public void searchItemFilter(String searchQuery) {
        boolean itemFound = false;
        Item itemMethod = new Item();
        Map<String, String> items = itemMethod.readMap();
        IMDisplay display = new IMDisplay();
        
        itemFound = display.searchItemDisplayItem(itemFound, items, searchQuery);

        if (!itemFound) {
            System.out.println("No item found matching the given ID or Name.");
        }
    }
    
    public void filterItemsFilter(String priceFilter, String reorderFilter, String dateFilterYear, String dateFilterMonth) {
        IMDisplay display = new IMDisplay();
        Item itemMethod = new Item();
        Map<String, String> items = itemMethod.readMap();
        Scanner scanner = new Scanner(System.in);

        boolean moreFilters = true;

        while (moreFilters) {
            String choice = display.itemFilterDisplayOption();

            switch (choice) {
                case "1" ->{
                    String priceChoice = display.itemFilterPriceOption();
                    priceFilter = getPriceFilter(priceChoice);
                    break;
                }
                case "2" ->{
                    String reorderChoice = display.itemFilterReorderOption();
                    reorderFilter = getReorderFilter(reorderChoice);
                    break;
                }
                case "3" ->{
                    List<String> years = itemMethod.getAvailableYears(items);
                    List<String> months = itemMethod.getAvailableMonths(items);

                    System.out.println("\nFilter by Last Updated Date:");
                    System.out.println("Available Years: " + years);
                    System.out.print("Enter the year: ");
                    dateFilterYear = scanner.nextLine();

                    System.out.println("Available Months: " + months);
                    System.out.print("Enter the month (e.g., 01 for January): ");
                    dateFilterMonth = scanner.nextLine();
                    break;
                }
                case "0" ->{
                    moreFilters = false;
                    break;
                }
                default ->{
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            }

            if (moreFilters) {
                System.out.print("\nDo you want to apply more filters? (yes/no): ");
                String moreFilterChoice = scanner.nextLine();
                moreFilters = moreFilterChoice.equalsIgnoreCase("yes");
            }
        }

        display.displayFilteredItems(items, priceFilter, reorderFilter, dateFilterYear, dateFilterMonth);
    }
    
    private String getPriceFilter(String choice) {
        switch (choice) {
            case "1": return "Below 100";
            case "2": return "100 - 1000";
            case "3": return "1000 - 5000";
            case "4": return "5000 - 10000";
            case "5": return "Above 10000";
            default: return null;
        }
    }

    private String getReorderFilter(String choice) {
        switch (choice) {
            case "1": return "Below 100";
            case "2": return "100 - 500";
            case "3": return "500 - 1000";
            case "4": return "Above 1000";
            default: return null;
        }
    }
    
    public void searchSupplierFilter(String searchQuery) {
        Supplier supplierMethod = new Supplier();
        Map<String, String> suppliers = supplierMethod.readMap();
        IMDisplay display = new IMDisplay();

        boolean supplierFound = false;

        supplierFound = display.searchSupplierDisplaySupplier(supplierFound, suppliers, searchQuery);

        if (!supplierFound) {
            System.out.println("No supplier found with the given ID or Name.");
        }
    }
    
    public void filterSuppliersFilter(String[] nameFilter, String[] itemSuppliedFilter, Integer[] quantityFilter, String[] locationFilter) {
        Supplier supplierMethod = new Supplier();
        ItemsSupplier itemSupplierMethod = new ItemsSupplier();
        Item itemMethod = new Item();
        
        Map<String, String> suppliers = supplierMethod.readMap();
        Map<String, List<String>> supplierItems = itemSupplierMethod.readItemsSupplierMap();
        Map<String, String> items = itemMethod.readItemName();
        
        Scanner scanner = new Scanner(System.in);
        IMDisplay display = new IMDisplay();

        boolean applyMoreFilters = true;
        while (applyMoreFilters) {
            String filterChoice = display.supplierFilterDisplayOption();

            switch (filterChoice) {
                case "1" ->{
                    Set<String> itemNameOptions = new HashSet<>(items.values());
                    List<String> itemOptionsList = new ArrayList<>(itemNameOptions);
                    System.out.println("\nExisting Items:");
                    for (int i = 0; i < itemOptionsList.size(); i++) {
                        System.out.printf("%d. %s\n", i + 1, itemOptionsList.get(i));
                    }
                    System.out.print("Select an item to filter by: ");
                    int itemChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (itemChoice > 0 && itemChoice <= itemOptionsList.size()) {
                        itemSuppliedFilter[0] = itemOptionsList.get(itemChoice - 1);
                    }
                    break;
                }
                case "2" ->{
                    Map<String, Integer> supplierItemCounts = new TreeMap<>();
                    for (Map.Entry<String, List<String>> entry : supplierItems.entrySet()) {
                        supplierItemCounts.put(entry.getKey(), entry.getValue().size());
                    }

                    Set<Integer> uniqueQuantities = new HashSet<>(supplierItemCounts.values());
                    List<Integer> quantityOptionsList = new ArrayList<>(uniqueQuantities);
                    Collections.sort(quantityOptionsList);
                    for (int i = 0; i < quantityOptionsList.size(); i++) {
                        System.out.printf("%d. %d items\n", i + 1, quantityOptionsList.get(i));
                    }
                    System.out.print("Select a quantity to filter by: ");
                    int quantityChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (quantityChoice > 0 && quantityChoice <= quantityOptionsList.size()) {
                        quantityFilter[0] = quantityOptionsList.get(quantityChoice - 1);
                    }
                    break;
                }
                case "3" ->{
                    Set<String> locationOptions = new HashSet<>();
                    for (String line : suppliers.values()) {
                        String[] details = line.split(",");
                        if (details.length >= 4) {
                            String[] addressParts = details[3].split(";");
                            if (addressParts.length >= 2) {
                                String cityCountry = addressParts[addressParts.length - 2].trim() + "; " + addressParts[addressParts.length - 1].trim();
                                locationOptions.add(cityCountry);
                            }
                        }
                    }

                    List<String> locationOptionsList = new ArrayList<>(locationOptions);
                    for (int i = 0; i < locationOptionsList.size(); i++) {
                        System.out.printf("%d. %s\n", i + 1, locationOptionsList.get(i));
                    }
                    System.out.print("Select a location to filter by: ");
                    int locationChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (locationChoice > 0 && locationChoice <= locationOptionsList.size()) {
                        locationFilter[0] = locationOptionsList.get(locationChoice - 1);
                    }
                    break;
                }
                default ->{
                    System.out.println("Invalid choice. Returning to menu.");
                    return;
                }
            }

            System.out.print("Do you want to apply more filters? (yes/no): ");
            String moreFilters = scanner.nextLine();
            applyMoreFilters = moreFilters.equalsIgnoreCase("yes");
        }

        display.displayFilteredSuppliers(suppliers, supplierItems, items, nameFilter[0], itemSuppliedFilter[0], quantityFilter[0], locationFilter[0]);
    }
    
    public void searchStockLevelFilter(Map<String, String> items, String searchQuery){
        boolean itemFound = false;

        System.out.println("\n-------------------------------------- Search Results --------------------------------------");
        System.out.printf("| %-15s | %-20s | %-15s | %-20s |\n", "Item ID", "Item Name", "Stock Level", "Updated Date");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (String line : items.values()) {
            String[] itemDetails = line.split(",");

            if (itemDetails.length >= 6) {
                String itemID = itemDetails[0];
                String itemName = itemDetails[1];
                String stockLevel = itemDetails[3];
                String updatedDate = itemDetails[5];

                if (itemID.equalsIgnoreCase(searchQuery) || itemName.equalsIgnoreCase(searchQuery)) {
                    System.out.printf("| %-15s | %-20s | %-15s | %-20s |\n", itemID, itemName, stockLevel, updatedDate);
                    itemFound = true;
                }
            }
        }

        if (!itemFound) {
            System.out.println("No item found matching the given ID or Name.");
        }

        System.out.println("--------------------------------------------------------------------------------------------");
    }
    
    public String getStockLevelFilter(String choice) {
        switch (choice) {
            case "1": return "Below 100";
            case "2": return "100 - 500";
            case "3": return "500 - 1000";
            case "4": return "Above 1000";
            default: return null;
        }
    }
    
    public void filterStockLevelFilter(Map<String, String> items, String stockLevelFilter,String yearFilter, String monthFilter){
        Item itemMethod = new Item();
        IMDisplay display = new IMDisplay();
        
        boolean applyMoreFilters = true;
        
        while (applyMoreFilters) {
            String filterChoice = display.stockLevelFilterDisplayOption();

            switch (filterChoice) {
                case "1" ->{
                    String stockChoice = display.stockLevelFilterStockLevelOption();
                    stockLevelFilter = getStockLevelFilter(stockChoice);
                    break;
                }
                case "2" ->{
                    List<String> years = itemMethod.getAvailableYears(items);
                    List<String> months = itemMethod.getAvailableMonths(items);

                    System.out.println("\nFilter by Last Updated Date:");
                    System.out.println("Available Years: " + years);
                    System.out.print("Enter the year: ");
                    yearFilter = scanner.nextLine();

                    System.out.println("Available Months: " + months);
                    System.out.print("Enter the month (e.g., 01 for January): ");
                    monthFilter = scanner.nextLine();
                    break;
                }
                case "0" ->{
                    applyMoreFilters = false;
                    break;
                }
                default ->{
                    System.out.println("Invalid choice. Please try again.");
                    break;
                }
            }

            if (applyMoreFilters) {
                System.out.print("\nDo you want to apply more filters? (yes/no): ");
                String moreFilterChoice = scanner.nextLine();
                applyMoreFilters = moreFilterChoice.equalsIgnoreCase("yes");
            }
        }

        // Display filtered items
        display.displayFilteredStockLevels(items, stockLevelFilter, yearFilter, monthFilter);
    }
}
