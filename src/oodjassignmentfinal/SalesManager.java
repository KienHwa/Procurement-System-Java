package oodjassignmentfinal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SalesManager extends User{
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public SalesManager(){}
    
    public SalesManager(String id){
        this.id = id;
    }
    
    public void smMenu(){
        SMFilter filter = new SMFilter();
        SMDisplay display = new SMDisplay();
        
        while(true){
            String input = display.homePage();
            switch (input){
                case "1" ->{
                    display.displayItem();
                    System.out.println();

                    boolean backToMenu = false;
                    while(!backToMenu){
                        System.out.print("press Q to return: ");
                        String exitInput = scanner.nextLine().trim().toUpperCase();
                        if (exitInput.equals("Q")){
                            System.out.println("");
                            System.out.print("sending you back to menu...");
                            System.out.println("");
                            System.out.println("");
                            backToMenu = true;
                        }
                        else{
                            System.out.println("");
                            System.out.println("invalid input, try again");
                            System.out.println("");
                        }
                    }
                }

                case "2" ->{
                    display.displayItem();
                    System.out.println();

                    ArrayList<String[]> foundItems = filter.searchAndDisplayItems(new ArrayList<>());
                    checkoutProcess(foundItems);

                    boolean backToMenu = false;
                    while(!backToMenu){
                        System.out.print("press Q to return: ");
                        String exitInput = scanner.nextLine().trim().toUpperCase();

                        if (exitInput.equals("Q")){
                            System.out.println("");
                            System.out.print("sending you back to menu...");
                            System.out.println("");
                            System.out.println("");
                            backToMenu = true;
                        }
                        else{
                            System.out.println("");
                            System.out.println("invalid input, try again");
                            System.out.println("");
                        }
                    }
                }

                    case "3" ->{
                        display.displaySales();
                        Map<String, double[]> summary = calculateSummary();
                        display.displaySummary(summary);
        
                        boolean backToMenu = false;
                        while(!backToMenu){
                            System.out.print("\npress Q to return: ");
                            String exitInput = scanner.nextLine().trim().toUpperCase();
        
                            if (exitInput.equals("Q")){
                                System.out.println("");
                                System.out.print("sending you back to menu...");
                                System.out.println("");
                                System.out.println("");
                                backToMenu = true;
                            }
                            else{
                                System.out.println("");
                                System.out.println("invalid input, try again");
                            }
                        }
                    }
        
                    case "4" ->{
                        display.displayStock();
        
                        boolean backToMenu = false;
                        while(!backToMenu){
                            System.out.print("\npress Q to return: ");
                            String exitInput = scanner.nextLine().trim().toUpperCase();
        
                            if (exitInput.equals("Q")){
                                System.out.println("");
                                System.out.print("sending you back to menu...");
                                System.out.println("");
                                System.out.println("");
                                backToMenu = true;
                            }
                            else{
                                System.out.println("");
                                System.out.println("invalid input, try again");
                            }
                        }
                    }
        
                    case "5"->{
                        createViewPurchaseRequisition();
                    }
        
                case "Q" ->{
                    System.out.println("the program will be terminated");
                    return;
                }

                default ->{
                    System.out.println("");
                    System.out.println("!!!invalid option, pls try again!!!");
                    System.out.println("");
                }  
            }
        }
    }
    
    public void checkoutProcess(ArrayList<String[]> foundItems){
        SMDisplay display = new SMDisplay();
        SMFilter filter = new SMFilter();        
        while(true){
            System.out.print("do u wish to checkout (yes / no): ");
            String decision = scanner.nextLine().trim();
            System.out.println("");
            
            if(decision.equalsIgnoreCase("yes")){
                checkOut(foundItems);
                return;
            }else if(decision.equalsIgnoreCase("no")){
                while(true){
                    System.out.print("edit cart (yes / no): ");
                    String decisionEdit = scanner.nextLine().trim();

                    if(decisionEdit.equalsIgnoreCase("no")){
                        System.out.println("");
                        System.out.println("preparing to send you back...");

                        break;
                    }
                    else if(decisionEdit.equalsIgnoreCase("yes")){
                        System.out.println("");
                        System.out.print("add or delete?: ");
                        String decisionAorD = scanner.nextLine().trim();
                        while(true){
                            if(decisionAorD.equalsIgnoreCase("add")){
                                display.displayItem();
                                foundItems = filter.searchAndDisplayItems(foundItems);
                                break;
                            }else if(decisionAorD.equalsIgnoreCase("delete")){
                                foundItems = filter.deleteItems(foundItems);
                                break;
                            }else{
                                System.out.println("\nadd or delete only");
                            }
                        }
                        break;
                    }
                    else{
                        System.out.println("");
                        System.out.println("yes or no only :( ");
                        System.out.println("");
                    }
                }
            }else{
                System.out.println("yes or no only :( ");
                System.out.println("");
            }
        }
    }
    
    public void checkOut(ArrayList<String[]> foundItems){
        Balance balanceMethod = new Balance();
        Sales salesMethod = new Sales();
        ItemsSold itemsSoldMethod = new ItemsSold();
        
        double totalPrice = 0.0;
        for(String[] items : foundItems){
            double price = Double.parseDouble(items[2]);
            double quantity = Double.parseDouble(items[3]);
            totalPrice += price * quantity;
        }
        System.out.printf("your total is: %.2f\n", totalPrice);
        System.out.println();

        String saleID = salesMethod.generateNewID();
        salesMethod.createFile(new Sales(saleID, this.id));

        for(String[] items : foundItems){
            itemsSoldMethod.createFile(new String[]{saleID, items[0], items[3], items[2]});
        }
        
        double balance = balanceMethod.getBalance();
        balance += totalPrice;
        balanceMethod.updateBalance(String.format("%.1f", balance));

        System.out.println("checkout successful\n");
    }
    
    public static Map<String, double[]> calculateSummary(){
        ItemsSold itemsSoldMethod = new ItemsSold();
        
        Map<String, double[]> summary = new HashMap<>();
        ArrayList<String[]> itemsSoldList = itemsSoldMethod.readFile();
        for(String[] details : itemsSoldList){
            String itemID = details[1];
            int quantity = Integer.parseInt(details[2]);
            double unitPrice = Double.parseDouble(details[3]);
            double profit = quantity * unitPrice;

            if(!summary.containsKey(itemID)){
                summary.put(itemID, new double[] {quantity, profit});
            }
            else{
                summary.get(itemID)[0] += quantity;
                summary.get(itemID)[1] += profit;
            }
        }
        return summary;
    }
    
    public void createViewPurchaseRequisition(){
        SMFilter SMfilter = new SMFilter();
        SMDisplay SMdisplay = new SMDisplay();
        
        String filterReqID = "All", filterSubmitterID = "All", filterSubmitterUsername = "All", filterApproverID = "All", filterApproverName = "All", filterSupplierID = "All", 
               filterSupplierName = "All", filterItemID = "All", filterItemName = "All", filterQuantity = "All", filterStatus = "All", filterDateTime = "XX-XX-XXXX";
        boolean exitPRPage = false;
        
        while (!exitPRPage){
            SMdisplay.displayRequisition(false, false, filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterItemID, filterItemName, filterQuantity, filterStatus, filterDateTime);
            System.out.println("\n1.Edit filter\n2.Create New Requisition\n3.Delete Requisition (within 2hrs)\n4.Return to PM Page");
            boolean chooseOption = false;
            while(!chooseOption){
                System.out.print("Enter your option: ");
                String option = scanner.nextLine();
                switch (option){
                    case "1":{
                        ArrayList<String> filter = SMfilter.requisitionPageFilterEditSM("None", filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterItemID, filterItemName, filterQuantity, filterStatus, filterDateTime);
                        filterReqID = filter.get(0);
                        filterSubmitterID = filter.get(1);
                        filterSubmitterUsername = filter.get(2);
                        filterApproverID = filter.get(3);
                        filterApproverName = filter.get(4);
                        filterSupplierID = filter.get(5);
                        filterSupplierName = filter.get(6);
                        filterItemID = filter.get(7);
                        filterItemName = filter.get(8);
                        filterQuantity = filter.get(9);
                        filterStatus = filter.get(10);
                        filterDateTime = filter.get(11);
                        chooseOption = true;
                        break;
                    }
                    case "2":{
                        String filterItemIDCPR = "All", filterItemNameCPR = "All", filterItemSellingPriceCPR = "All", filterStockLevelCPR = "All";
                        boolean exitCreateReq = false;
                        while (!exitCreateReq){
                            SMdisplay.displayItem(false, filterItemIDCPR, filterItemNameCPR, filterItemSellingPriceCPR, filterStockLevelCPR);
                            System.out.println("\n1.Edit Item filter\n2.Choose Item to create Requisition\n3.Return to Purchase Requisition Page");
                            boolean chooseItem = false;
                            while (!chooseItem){
                                System.out.print("Enter your option: ");
                                String chooseItemOption = scanner.nextLine().trim();
                                switch (chooseItemOption){
                                    case "1"->{
                                        ArrayList<String> filter = SMfilter.itemPageFilterEdit(filterItemIDCPR, filterItemNameCPR, filterItemSellingPriceCPR, filterStockLevelCPR);
                                        filterItemIDCPR = filter.get(0);
                                        filterItemNameCPR = filter.get(1);
                                        filterItemSellingPriceCPR = filter.get(2);
                                        filterStockLevelCPR = filter.get(3);
                                        chooseItem = true;
                                        break;
                                    }
                                    case "2"->{
                                        System.out.println();
                                        boolean processCreatePR = false;
                                        boolean itemExists;
                                        while (!processCreatePR){
                                            System.out.print("Enter Item ID to create requisition (input Q to terminate create requisition): ");
                                            String inputItemID = scanner.nextLine().trim().toUpperCase();
                                            if (inputItemID.equalsIgnoreCase("q")){
                                                System.out.println("Terminated creating this requisition.");
                                                chooseItem = true;
                                                break;
                                            }
                                            else if (inputItemID.isEmpty()) {
                                                System.out.println("Please enter a valid Item ID.");
                                            }
                                            else if(!inputItemID.substring(0,1).equals("I") || inputItemID.length() > 5){
                                                System.out.println("Item ID format wrong, please re-enter correct format requisition ID.");
                                            }
                                            else {
                                                String itemSelected;
                                                itemExists = SMdisplay.displayItem(true, inputItemID, "All", "All", "All");
                                                if (!itemExists){
                                                    System.out.println("Item ID is not found or doesnt exists. Please re-input Item ID.");
                                                }
                                                else{
                                                    itemSelected = inputItemID;
                                                    String filterSupplierIDCPR = "All", filterSupplierNameCPR = "All", filterItemBuyingPriceCPR = "All";
                                                    boolean exitCreateReq2 = false;
                                                    while (!exitCreateReq2){
                                                        SMdisplay.displayItemSupplier(false, filterSupplierIDCPR, filterSupplierNameCPR, itemSelected, filterItemBuyingPriceCPR);
                                                        System.out.println("\n1.Edit Item Supplier filter\n2.Choose Supplier with Item to create Requisition\n3.Return to Purchase Requisition Page");
                                                        boolean chooseSupplier = false;
                                                        while (!chooseSupplier){
                                                            System.out.print("Enter your option: ");
                                                            String chooseSupplierOption = scanner.nextLine().trim();
                                                            switch (chooseSupplierOption){
                                                                case "1"->{
                                                                    ArrayList<String> filters = SMfilter.itemSupplierFilterEdit(filterSupplierIDCPR, filterSupplierNameCPR, filterItemBuyingPriceCPR);
                                                                    filterSupplierIDCPR = filters.get(0);
                                                                    filterSupplierNameCPR = filters.get(1);
                                                                    filterItemBuyingPriceCPR = filters.get(2);
                                                                    chooseSupplier = true;
                                                                    break;
                                                                }
                                                                case "2"->{
                                                                    System.out.println();
                                                                    
                                                                    PurchaseRequisition prMethod = new PurchaseRequisition();
                                                                    Item itemMethod = new Item();
                                                                    Supplier supplierMethod = new Supplier();
                                                                    
                                                                    boolean processCreatePR2 = false;
                                                                    boolean supplierExists;
                                                                    while (!processCreatePR2){
                                                                        System.out.print("Enter Supplier ID to create requisition (input Q to terminate create requisition): ");
                                                                        String inputSupplierID = scanner.nextLine().trim().toUpperCase();
                                                                        if (inputSupplierID.equalsIgnoreCase("q")){
                                                                            System.out.println("Terminated creating this requisition.");
                                                                            processCreatePR = true;
                                                                            chooseItem = true;
                                                                            exitCreateReq2 = true;
                                                                            chooseSupplier = true;
                                                                            break;
                                                                        }
                                                                        else if (inputSupplierID.isEmpty()) {
                                                                            System.out.println("Please enter a valid Supplier ID.");
                                                                        }
                                                                        else if(!inputSupplierID.substring(0,1).equals("S") || inputSupplierID.length() > 5){
                                                                            System.out.println("Supplier ID format wrong, please re-enter correct format Supplier ID.");
                                                                        }
                                                                        else {
                                                                            supplierExists = SMdisplay.displayItemSupplier(true, inputSupplierID, "All", itemSelected, "All");
                                                                            if (!supplierExists){
                                                                               System.out.println("Supplier ID is not found or doesnt exists. Please re-input Supplier ID.");
                                                                            }
                                                                            else if (supplierExists){
                                                                                LinkedHashMap<String, String[]> itemListMap = itemMethod.readHashMap();
                                                                                LinkedHashMap<String, String[]> supplierListMap = supplierMethod.readHashMap();
                                                                                boolean processEnterQuantity = false;
                                                                                while (!processEnterQuantity){
                                                                                    System.out.print("\nEnter quantity amount for item "+itemListMap.get(inputItemID)[1]+" (input Q to terminate create requisition): ");
                                                                                    String inputQuantity = scanner.nextLine();
                                                                                    if (inputQuantity.equalsIgnoreCase("q")){
                                                                                       System.out.println("Terminated creating this requisition."); //CONTINUE FROM HERE
                                                                                       processEnterQuantity = true;
                                                                                       processCreatePR2 = true;
                                                                                       processCreatePR = true;
                                                                                       chooseItem = true;
                                                                                       exitCreateReq2 = true;
                                                                                       chooseSupplier = true;
                                                                                       //break;
                                                                                    }
                                                                                    else if (inputQuantity.isEmpty()) {
                                                                                        System.out.println("Please re-enter only digits for quantity..");
                                                                                     }
                                                                                    else if (inputQuantity.contains(".")){
                                                                                        System.out.println("Only Integers are allow for quantity, please re-input quantity.");
                                                                                    }
                                                                                    else{
                                                                                        boolean checkDigits = SMfilter.onlyDigits(inputQuantity);
                                                                                        if (!checkDigits){
                                                                                            System.out.println("Only Integers are allow for quantity, please re-input quantity.");
                                                                                        }
                                                                                        else{
                                                                                            System.out.println("\nSummary for this new purchase requisition: ");
                                                                                            System.out.println("Supplier ID: "+inputSupplierID+"\nSupplier Name: "+supplierListMap.get(inputSupplierID)[1]+"\nItem ID: "+inputItemID+
                                                                                                               "\nItem Name: "+itemListMap.get(inputItemID)[1]+"\nQuantity: "+inputQuantity);
                                                                                            boolean comfirmReq = false;
                                                                                            while (!comfirmReq){
                                                                                                System.out.print("Are you sure you want to create this purchase requisition? (Y/N): ");
                                                                                                String enterComfirmReq = scanner.nextLine();
                                                                                                if (enterComfirmReq.equalsIgnoreCase("n")){
                                                                                                    System.out.println("Terminated creating this requisition.");
                                                                                                    break;
                                                                                                } 
                                                                                                else if (enterComfirmReq.equalsIgnoreCase("y")) {
                                                                                                    prMethod.createFile(new String[]{prMethod.provideID("PR"),this.id,"-",inputSupplierID,inputItemID,inputQuantity,"Pending",currentDateTime()});
                                                                                                    System.out.println("Successfully created this requisition.");
                                                                                                    break;
                                                                                                }
                                                                                                else{
                                                                                                    System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                                                                }
                                                                                            }
                                                                                            while (true){
                                                                                                System.out.print("Do you want to create another requisition? (Y/N): ");
                                                                                                String inputRepeatCreate = scanner.nextLine();
                                                                                                if (inputRepeatCreate.equalsIgnoreCase("n")){
                                                                                                    processEnterQuantity = true;
                                                                                                    processCreatePR2 = true;
                                                                                                    processCreatePR = true;
                                                                                                    chooseItem = true;
                                                                                                    comfirmReq = true;
                                                                                                    exitCreateReq2 = true;
                                                                                                    exitCreateReq = true;
                                                                                                    chooseSupplier = true;
                                                                                                    chooseOption = true;
                                                                                                    break;
                                                                                                }
                                                                                                else if (inputRepeatCreate.equalsIgnoreCase("y")){
                                                                                                    processEnterQuantity = true;
                                                                                                    processCreatePR2 = true;
                                                                                                    processCreatePR = true;
                                                                                                    chooseItem = true;
                                                                                                    comfirmReq = true;
                                                                                                    exitCreateReq2 = true;
                                                                                                    chooseSupplier = true;
                                                                                                    chooseOption = true;
                                                                                                    filterItemNameCPR = "All";
                                                                                                    filterItemSellingPriceCPR = "All";
                                                                                                    filterStockLevelCPR = "All";
                                                                                                    break;
                                                                                                }
                                                                                                else{
                                                                                                    System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                                                                }
                                                                                            }
                                                                                        }    
                                                                                    }
                                                                               }
                                                                            }
                                                                        }
                                                                    }
                                                                    break;
                                                                }
                                                                case "3"->{
                                                                    processCreatePR = true;
                                                                    chooseItem = true;
                                                                    exitCreateReq2 = true;
                                                                    chooseSupplier = true;
                                                                    exitCreateReq = true;
                                                                    chooseItem = true;
                                                                    chooseOption = true;
                                                                    break;
                                                                }
                                                                default->{
                                                                    System.out.println("Invalid input option, please re-input from 1 to 3.");
                                                                }
                                                            }
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case "3"->{
                                        exitCreateReq = true;
                                        chooseItem = true;
                                        chooseOption = true;
                                        break;
                                    }
                                    default->{
                                        System.out.println("Invalid input option, please re-input from 1 to 3.");
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case "3":{
                        String filterReqIDDPR = "All", filterSubmitterIDDPR = "All", filterSubmitterUsernameDPR = "All", filterApproverIDDPR = "All", filterApproverNameDPR = "All", filterSupplierIDDPR = "All", 
                        filterSupplierNameDPR = "All", filterItemIDDPR = "All", filterItemNameDPR = "All", filterQuantityDPR = "All", filterStatusDPR = "All", filterDateTimeDPR = "XX-XX-XXXX";
                        boolean processDltReq = false;
                        while (!processDltReq){
                            SMdisplay.displayRequisition(true, false, filterReqIDDPR, filterSubmitterIDDPR, filterSubmitterUsernameDPR, filterApproverIDDPR, filterApproverNameDPR, filterSupplierIDDPR, filterSupplierNameDPR, filterItemIDDPR, filterItemNameDPR, filterQuantityDPR, filterStatusDPR, filterDateTimeDPR);
                            System.out.println("\n1.Edit purchase requisition filter\n2.Choose purchase requisition to delete\n3.Return to Purchase Requisition Page");
                            boolean chooseDltReq = false;
                            while (!chooseDltReq){
                                System.out.print("Enter your option: ");
                                String dltReqOption = scanner.nextLine();
                                switch (dltReqOption){
                                    case "1"->{ // go to filter editer and change so that they cannot edit DateTime.
                                        ArrayList<String> filter = SMfilter.requisitionPageFilterEditSM("Deletion", filterReqIDDPR, filterSubmitterIDDPR, filterSubmitterUsernameDPR, filterApproverIDDPR, filterApproverNameDPR, filterSupplierIDDPR, filterSupplierNameDPR, filterItemIDDPR, filterItemNameDPR, filterQuantityDPR, filterStatusDPR, filterDateTimeDPR);
                                        filterReqIDDPR = filter.get(0);
                                        filterSubmitterIDDPR = filter.get(1);
                                        filterSubmitterUsernameDPR = filter.get(2);
                                        filterApproverIDDPR = filter.get(3);
                                        filterApproverNameDPR = filter.get(4);
                                        filterSupplierIDDPR = filter.get(5);
                                        filterSupplierNameDPR = filter.get(6);
                                        filterItemIDDPR = filter.get(7);
                                        filterItemNameDPR = filter.get(8);
                                        filterQuantityDPR = filter.get(9);
                                        filterStatusDPR = filter.get(10);
                                        filterDateTimeDPR = filter.get(11);
                                        chooseDltReq = true;
                                        break;
                                    }
                                    case "2"->{
                                        PurchaseRequisition prMethod = new PurchaseRequisition();
                                        
                                        System.out.println();
                                        boolean processDltPR = false;
                                        while (!processDltPR){
                                            System.out.print("Enter purchase requisition ID to delete requisition (input Q to terminate delete requisition): ");
                                            String inputDltReqID = scanner.nextLine().trim().toUpperCase();
                                            if (inputDltReqID.equalsIgnoreCase("q")){
                                                System.out.println("Terminated deleting this requisition.");
                                                chooseDltReq = true;
                                                processDltPR = true;
                                                break;
                                            }
                                            else if (inputDltReqID.isEmpty()) {
                                                System.out.println("Please enter a valid requisition ID.");
                                            }
                                            else if(inputDltReqID.length() < 2 || !inputDltReqID.substring(0,2).equals("PR") || inputDltReqID.length() > 6){
                                                System.out.println("Purchase Requistion ID format wrong, please re-enter correct format requisition ID.");
                                            }
                                            else {
                                                //String prSelected;
                                                boolean prExists;
                                                prExists = SMdisplay.displayRequisition(true, true, inputDltReqID, "All", "All", "All", "All", "All", "All", "All", "All", "All", "All", "XX-XX-XXXX");
                                                if (!prExists){
                                                    System.out.println("Purchase Requisition ID is not found or doesnt exists. Please re-input purchase requisition ID.");
                                                }
                                                else{
                                                    boolean comfirmDltReq = false;
                                                    while (!comfirmDltReq){
                                                        System.out.print("Are you sure you want to delete this purchase requisition? (Y/N): ");
                                                        String enterComfirmReq = scanner.nextLine();
                                                        if (enterComfirmReq.equalsIgnoreCase("n")){
                                                            System.out.println("Terminated deleting this requisition.");
                                                            break;
                                                        } 
                                                        else if (enterComfirmReq.equalsIgnoreCase("y")) {
                                                            prMethod.deleteFile(inputDltReqID);
                                                            System.out.println("Successfully deleted this requisition.");
                                                            break;
                                                        }
                                                        else{
                                                            System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                        }
                                                    }
                                                    while (true){
                                                        System.out.print("Do you want to delete another requisition? (Y/N): ");
                                                        String inputRepeatCreate = scanner.nextLine();
                                                        if (inputRepeatCreate.equalsIgnoreCase("n")){
                                                            chooseOption = true;
                                                            processDltReq = true;
                                                            chooseDltReq = true;
                                                            processDltPR = true;
                                                            comfirmDltReq = true;
                                                            break;
                                                        }
                                                        else if (inputRepeatCreate.equalsIgnoreCase("y")){
                                                            chooseDltReq = true;
                                                            processDltPR = true;
                                                            comfirmDltReq = true;
                                                            break;
                                                        }
                                                        else{
                                                            System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    case "3"->{
                                        chooseOption = true;
                                        processDltReq = true;
                                        chooseDltReq = true;
                                        break;
                                    }
                                    default->{
                                        System.out.println("Invalid input option, please re-input.");
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case "4":{
                        chooseOption = true;
                        exitPRPage = true;
                        break;
                    }
                    default:{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }
    }
    
    public String currentDateTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }
    
    public boolean withinTwoHours(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime inputDateTime = LocalDateTime.parse(dateTime, formatter);
        LocalDateTime currentDateTime = LocalDateTime.now();
        long hoursDifference = ChronoUnit.HOURS.between(currentDateTime, inputDateTime);
        return Math.abs(hoursDifference) <= 2;
    }
}
