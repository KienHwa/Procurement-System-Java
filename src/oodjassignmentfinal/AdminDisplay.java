package oodjassignmentfinal;

import java.util.*;
import oodjassignmentfinal.Display;

public class AdminDisplay extends Display{
    public String homePage(){
        Scanner sc = new Scanner(System.in);
        System.out.println("============ \nAdmin Menu:\n============ ");
        System.out.println("1. Register User");
        System.out.println("2. Edit Profile");
        System.out.println("3. View Item");
        System.out.println("4. View Supplier");
        System.out.println("5. View Purchase Requisition");
        System.out.println("6. View Purchase Order");
        System.out.println("7. Exit");
        System.out.print("Select an option: ");
        return sc.nextLine().trim();
    }
    
    public void registerUserSelectRole(){
        System.out.println("============\nSelect a role\n============:");
        System.out.println("1. Sales Manager\n2. Purchase Manager\n3. Inventory Manager\n4. Finance Manager\n5. Admin");
    }
    
    public void registerUserSelectionRole(String username, String email, String password, String role, String realName){
        System.out.println("============\nRegistration Summary\n===========");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Role: " + role);
        System.out.println("Real Name: " + realName);
        System.out.println("1. Confirm\n2. Cancel");
    }
    
    public void editProfileCurrentDetail(String[] userData){
        System.out.println("Profile found. Current details:");
        System.out.println("1. Username: " + userData[1]);
        System.out.println("2. Password: " + userData[3]);
        System.out.println("3. Real Name: " + userData[5]);
        System.out.println("4. Exit without saving");
    }
    
    public void displayItem(){
        Item itemMethod = new Item();
        Map<String, String> items = itemMethod.readMap();
        
        System.out.println("\n-------------------------------------- List of Filtered Items ---------------------------------------");
        System.out.printf("| %-15s | %-20s | %-15s | %-15s | %-20s |\n",
                "Item ID", "Item Name", "Selling Price", "Reorder Level", "Updated Date");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        for (String item : items.values()) {
            String[] details = item.split(",");
            if (details.length < 6) continue;

            double sellingPrice = Double.parseDouble(details[2]);
            int reorderLevel = Integer.parseInt(details[4]);
            String updatedDate = details[5];

            System.out.printf("| %-15s | %-20s | %-15.2f | %-15d | %-20s |\n", details[0], details[1], sellingPrice, reorderLevel, updatedDate);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }
    
    public void displaySupplier(){
        Supplier supplierMethod = new Supplier();
        ItemsSupplier itemsSupplierMethod = new ItemsSupplier();
        Item itemMethod = new Item();
        Map<String, String> suppliers = supplierMethod.readMap();
        Map<String, List<String>> supplierItems = itemsSupplierMethod.readItemsSupplierMap();
        Map<String, String> items = itemMethod.readItemName();
        
        System.out.println("\n----------------------------------------------------- List of Filtered Suppliers --------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-80s |\n", "Supplier ID", "Supplier Name", "Contact No", "Item Supplied");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");

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

            System.out.printf("| %-11s | %-30s | %-15s | %-80s |\n", supplierID, supplierName, contactNo, String.join(", ", itemNames));
            List<String> wrappedAddressLines = wrapText(address, 58);
            for (String line : wrappedAddressLines) {
                System.out.printf("| %-62s | %-80s |\n", line.trim(), "");
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
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
    
    public void displayRequisition(){
        User userMethod = new User();
        Item itemMethod = new Item();
        Supplier supplierMethod = new Supplier();
        PurchaseRequisition prMethod = new PurchaseRequisition();
        
        ArrayList<PurchaseRequisition> purchaseRequisitionList = prMethod.readPR();
        LinkedHashMap<String, Item> itemListMap = itemMethod.readItemMap();
        LinkedHashMap<String, Supplier> supplierListMap = supplierMethod.readSupplierMap();
        LinkedHashMap<String, String[]> userListMap = userMethod.readUserPR();
        
        System.out.println("------------------------------------------------------------------------- Purchase Requisition List ---------------------------------------------------------------------------------------");
        System.out.println("| Requisition ID | Submitter ID (Username)      | Approver ID (Username)       | Supplier ID (Name)            | Item ID (Item Name)         | Quantity | Status    | Date and Time       |");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        
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

            System.out.printf("| %-14s | %-5s (%-20s) | %-5s (%-20s) | %-6s (%-20s) | %-4s (%-20s) | %-8s | %-9s | %-19s |\n", requisitionID, submitterID, submitterName, approverID, approverName, supplierID, supplierName, itemID, itemName, quantity, status, dateTime);
        }
    }
    
    public void displayOrder(){
        PurchaseOrder poMethod = new PurchaseOrder();
        User userMethod = new User();
        Supplier supplierMethod = new Supplier();
        
        ArrayList<PurchaseOrder> poList = poMethod.readPO();
        LinkedHashMap<String,User> userMap = userMethod.readUserMap();
        LinkedHashMap<String,Supplier> supplierMap = supplierMethod.readSupplierMap();
        
        System.out.println("----------------------------------------------------------------------- Purchase Orders -----------------------------------------------------------------------");
        System.out.println("Purchase Order ID | SubmitterID (Name)           | ApproverID (Name)            | SupplierID (Name)           | Status   | Payment Status | Date");

        for(PurchaseOrder po : poList){
            String submitterName = userMap.get(po.getCreatorId()).getName()+")";
            String approverName = po.getApproverId().equals("-") ? "" : "("+userMap.get(po.getApproverId()).getName()+")";
            String supplierName = "("+supplierMap.get(po.getSupplierId()).getName()+")";
            System.out.printf("%-17s | %-5s %-22s | %-5s %-22s | %-4s %-22s | %-8s | %-14s | %s\n", po.getId(), po.getCreatorId(), submitterName, po.getApproverId(), approverName, po.getSupplierId(), supplierName, po.getStatus(), po.getPaymentStatus(), po.getDate());
        }
    }
}
