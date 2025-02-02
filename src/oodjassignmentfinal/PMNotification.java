package oodjassignmentfinal;

import java.util.ArrayList;

public class PMNotification extends Notification {
    
    @Override
    public String getNotification() {
        Item itemMethod = new Item();
        ArrayList<Item> itemList = itemMethod.readItem();

        boolean recordFound = false;
        int stockLevel;
        int reorderLevel;

        for (Item item : itemList) {
            String itemID = item.getId();
            String itemName = item.getName();
            try {
                stockLevel = Integer.parseInt(item.getStockLevel());
                reorderLevel = Integer.parseInt(item.getReorderLevel());
            } catch (NumberFormatException e) {
                continue;
            }

            if (stockLevel <= reorderLevel) {
                if (!recordFound) {
                    System.out.println("\n------------ List of Items at Reorder Level-------------------");
                    System.out.println("| Item ID    | Item Name          | Stock Level | Reorder Level |");
                    System.out.println("----------------------------------------------------------------");
                }
                System.out.printf("| %-10s | %-18s | %-11d | %-13d |\n", itemID, itemName, stockLevel, reorderLevel);
                recordFound = true;
            }
        }

        if (!recordFound) {
            return "\nNo items below reorder level.\n";
        } 
        else {
            return "\nPlease restock items as soon as possible.\n";
        }
    }

}
