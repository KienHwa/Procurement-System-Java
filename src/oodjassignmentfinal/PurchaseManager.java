package oodjassignmentfinal;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PurchaseManager extends User{
    public PurchaseManager(){ 
    }
    
    public PurchaseManager(String id, String name){
        this.id = id;
        this.name = name;
    }
    
    public boolean withinTwoHours(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime inputDateTime = LocalDateTime.parse(dateTime, formatter);
        LocalDateTime currentDateTime = LocalDateTime.now();
        long hoursDifference = ChronoUnit.HOURS.between(currentDateTime, inputDateTime);
        return Math.abs(hoursDifference) <= 2;
    }
    
    public boolean afterTwoHours(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime inputDateTime = LocalDateTime.parse(dateTime, formatter);
        LocalDateTime currentDateTime = LocalDateTime.now();
        long hoursDifference = ChronoUnit.HOURS.between(currentDateTime, inputDateTime);
        return Math.abs(hoursDifference) >= 2;
    }
    
    public String currentDateTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }
    
    public void pmMenu(){
        PMNotification notification = new PMNotification();
        System.out.println(notification.getNotification());
        PMDisplay display = new PMDisplay();
        Scanner sc = new Scanner(System.in);
        boolean exitPMPage = false;
        while (!exitPMPage){
            display.homePage();
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch (option){
                    case "1" ->{
                        chooseOption = true;
                        ListOfItem();
                        break;
                    }
                    case "2" ->{
                        chooseOption = true;
                        listOfSuppliers();
                        break;
                    }
                    case "3" ->{
                        chooseOption = true;
                        createViewPurchaseRequisition();
                        break;
                    }
                    case "4" ->{
                        chooseOption = true;
                        createViewPurchaseOrder();
                        break;
                    }
                    case "5" ->{
                        chooseOption = true;
                        exitPMPage = true;
                        System.out.println(this.name + ", you have logged out successfully!");
                        break;
                    }
                    default ->{
                        System.out.println("Invalid input option, please re-input.");
                    }
               }
            }
        }
    }
    
    public void ListOfItem(){
        Scanner sc = new Scanner(System.in);
        PMFilter f = new PMFilter();
        boolean exitItemPage = false;
        String filterItemID = "All", filterItemName = "All", filterItemSellingPrice = "All", filterstocklevel = "All";
        
        while (!exitItemPage){
            PMDisplay display = new PMDisplay();
            display.displayItem(false, filterItemID, filterItemName, filterItemSellingPrice, filterstocklevel);
            System.out.println("\n1.Edit filter\n2.Return to PM Page");
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch (option){
                    case "1":{
                        ArrayList<String> filters = f.itemPageFilterEdit(filterItemID, filterItemName, filterItemSellingPrice, filterstocklevel);
                        filterItemID = filters.get(0);
                        filterItemName = filters.get(1);
                        filterItemSellingPrice = filters.get(2);
                        filterstocklevel = filters.get(3);
                        chooseOption = true;
                        break;
                    }
                    case "2":{
                        chooseOption = true;
                        exitItemPage = true;
                        break;
                    }
                    default :{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }   
    }
    
    public void listOfSuppliers(){
        Scanner sc = new Scanner(System.in);
        PMDisplay display = new PMDisplay();
        PMFilter f = new PMFilter();
        boolean exitItemPage = false;
        String filterSupplierID = "All", filterSupplierName = "All", filterContactNumber = "All", filterAddress = "All";
        
        while (!exitItemPage){
            display.displaySuppliers(filterSupplierID, filterSupplierName, filterContactNumber, filterAddress);
            System.out.println("\n1.Edit supplier filter\n2.Return to PM Page");
            boolean chooseOption = false;
            while (!chooseOption){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch (option){
                    case "1" ->{
                        ArrayList<String> filters = f.supplierPageFilterEdit(filterSupplierID, filterSupplierName, filterContactNumber, filterAddress);
                        filterSupplierID = filters.get(0);
                        filterSupplierName = filters.get(1);
                        filterContactNumber = filters.get(2);
                        filterAddress = filters.get(3);
                        chooseOption = true;
                        break;
                    }
                    case "2" ->{
                        chooseOption = true;
                        exitItemPage = true;
                        break;
                    }
                    default ->{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }   
    }
    
    public void createViewPurchaseRequisition(){
        Scanner sc = new Scanner(System.in);
        PMDisplay display = new PMDisplay();
        PurchaseRequisition prMethod = new PurchaseRequisition();
        PMFilter f = new PMFilter();
        String filterReqID = "All", filterSubmitterID = "All", filterSubmitterUsername = "All", filterApproverID = "All", filterApproverName = "All", filterSupplierID = "All", 
               filterSupplierName = "All", filterItemID = "All", filterItemName = "All", filterQuantity = "All", filterStatus = "All", filterDateTime = "XX-XX-XXXX";
        boolean exitPRPage = false;
        
        while (!exitPRPage){
            display.displayRequisition(false, false, filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterItemID, filterItemName, filterQuantity, filterStatus, filterDateTime);
            System.out.println("\n1.Edit filter\n2.Validate Requisition created by SM\n3.Create New Requisition\n4.Delete Requisition (within 2hrs)\n5.Return to PM Page");
            boolean chooseOption = false;
            while(!chooseOption){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch (option){
                    case "1":{
                        ArrayList<String> filter = f.requisitionPageFilterEdit("None", filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterItemID, filterItemName, filterQuantity, filterStatus, filterDateTime);
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
                        boolean exitValidateReq = false;
                        while (!exitValidateReq){
                            ArrayList<PurchaseRequisition> purchaseRequisitionList = prMethod.readPR();
                            display.displayRequisition(false, false, filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterItemID, filterItemName, filterQuantity, "Pending", filterDateTime);
                            System.out.println("\n1.Edit filter\n2.Choose requisition created by SM to validate\n3.Return to Purchase Requisition Page");
                            boolean validateReq = false;
                            while(!validateReq){
                                System.out.print("Enter your option: ");
                                String optionValidate = sc.nextLine().trim();
                                switch (optionValidate){
                                    case "1"->{
                                        ArrayList<String> filter = f.requisitionPageFilterEdit("Validation", filterReqID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterItemID, filterItemName, filterQuantity, "Pending", filterDateTime);
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
                                        validateReq = true;
                                        break;
                                    }
                                    case "2"->{
                                        System.out.println();
                                        boolean processValidate = false;
                                        while (!processValidate){
                                            System.out.print("Enter Requisition ID to validate (input Q to exit validation): ");
                                            String inputRequisitionID = sc.nextLine().trim();
                                            
                                            if(inputRequisitionID.equalsIgnoreCase("q")){
                                                System.out.println("Exit Validation successfully.");   
                                                processValidate = true;
                                                validateReq = true;
                                            }
                                            else if (inputRequisitionID.isEmpty()) {
                                                System.out.println("Please enter a valid Item ID.");
                                            }
                                            else if(inputRequisitionID.length() < 2 || !inputRequisitionID.substring(0,2).equals("PR") || inputRequisitionID.length() > 6){
                                                System.out.println("Requisition ID format wrong, please re-enter correct format requisition ID.");
                                            }
                                            else{
                                                boolean found = false;
                                                boolean validated = false;
                                                boolean editedPRList = false;
                                                String statusChange = "";
                                                for (PurchaseRequisition requisition : purchaseRequisitionList){
                                                    if(requisition.getId().equals(inputRequisitionID) && requisition.getSubmitterID().startsWith("SM") && requisition.getStatus().equals("Pending")){
                                                        found = true;
                                                        System.out.println("Requisition ID "+inputRequisitionID+" is selected for validation.");
                                                        System.out.println("1.Approved\n2.Reject\n3.Terminate validation");
                                                        while (true){
                                                            System.out.print("Choose your option to validate: ");
                                                            String chooseStatus = sc.nextLine().trim();
                                                            if (chooseStatus.equals("1")){
                                                                requisition.setStatus("Approved");
                                                                requisition.setApproverID(this.id);
                                                                statusChange = "Approved";
                                                                validated = true;
                                                                editedPRList = true;
                                                                break;
                                                            }
                                                            else if (chooseStatus.equals("2")){
                                                                requisition.setStatus("Rejected");
                                                                requisition.setApproverID(this.id);
                                                                statusChange = "Rejected";
                                                                validated = true;
                                                                editedPRList = true;
                                                                break;
                                                            }
                                                            else if (chooseStatus.equals("3")){
                                                                System.out.println("Terminated Validation successfully.");
                                                                validated = true;
                                                                break;
                                                            }
                                                            else{
                                                                System.out.println("Invalid input. Please choose between 1 to 3.");
                                                            }    
                                                        }
                                                        break;
                                                    }
                                                }
                                                if (!found){
                                                     System.out.println("Requisition ID is not found or not under 'Pending' status.");
                                                }
                                                else if (validated){
                                                    if (editedPRList){
                                                        // IF WANT TO ADD "ARE YOU SURE YOU WANT TO VALIDATE THIS REQUISITION", then add if else here, no need touch others.
                                                        System.out.println("Requisition " + inputRequisitionID + " has been "+statusChange+".");
                                                        prMethod.updateFile(purchaseRequisitionList);
                                                        System.out.println("Purchase Requisition file successfully updated."); 
                                                    }
                                                    while(true){
                                                        System.out.print("Do you want to validate another requisition? (Y/N): ");
                                                        String repeatValidate = sc.nextLine().trim();
                                                        if (repeatValidate.equalsIgnoreCase("y")){
                                                            processValidate = true;
                                                            validateReq = true;
                                                            break;
                                                        }
                                                        else if (repeatValidate.equalsIgnoreCase("n")){
                                                            filterReqID = "All";
                                                            filterSubmitterID = "All";
                                                            filterSubmitterUsername = "All";
                                                            filterApproverID = "All";
                                                            filterApproverName = "All";
                                                            filterSupplierID = "All";
                                                            filterSupplierName = "All";
                                                            filterItemID = "All";
                                                            filterItemName = "All";
                                                            filterQuantity = "All";
                                                            filterStatus = "All";
                                                            filterDateTime = "XX-XX-XXXX";
                                                            processValidate = true;
                                                            exitValidateReq = true;
                                                            validateReq = true;
                                                            chooseOption = true;
                                                            System.out.print("Returning to Purchase Requisition Page.");
                                                            break;
                                                        } 
                                                        else {
                                                            System.out.println("Invalid option, please re-enter only Y or N.");
                                                        }
                                                    }
                                                }
                                                //break; ??
                                            }
                                            
                                        }
                                        break;
                                        
                                    }
                                    case "3"->{
                                        filterReqID = "All";
                                        filterSubmitterID = "All";
                                        filterSubmitterUsername = "All";
                                        filterApproverID = "All";
                                        filterApproverName = "All";
                                        filterSupplierID = "All";
                                        filterSupplierName = "All";
                                        filterItemID = "All";
                                        filterItemName = "All";
                                        filterQuantity = "All";
                                        filterStatus = "All";
                                        filterDateTime = "XX-XX-XXXX";
                                        exitValidateReq = true;
                                        validateReq = true;
                                        chooseOption = true;
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
                    case "3":{
                        String filterItemIDCPR = "All", filterItemNameCPR = "All", filterItemSellingPriceCPR = "All", filterStockLevelCPR = "All";
                        boolean exitCreateReq = false;
                        while (!exitCreateReq){
                            display.displayItem(false, filterItemIDCPR, filterItemNameCPR, filterItemSellingPriceCPR, filterStockLevelCPR);
                            System.out.println("\n1.Edit Item filter\n2.Choose Item to create Requisition\n3.Return to Purchase Requisition Page");
                            boolean chooseItem = false;
                            while (!chooseItem){
                                System.out.print("Enter your option: ");
                                String chooseItemOption = sc.nextLine().trim();
                                switch (chooseItemOption){
                                    case "1"->{
                                        ArrayList<String> filters = f.itemPageFilterEdit(filterItemIDCPR, filterItemNameCPR, filterItemSellingPriceCPR, filterStockLevelCPR);
                                        filterItemIDCPR = filters.get(0);
                                        filterItemNameCPR = filters.get(1);
                                        filterItemSellingPriceCPR = filters.get(2);
                                        filterStockLevelCPR = filters.get(3);
                                        chooseItem = true;
                                        break;
                                    }
                                    case "2"->{
                                        System.out.println();
                                        boolean processCreatePR = false;
                                        boolean itemExists;
                                        while (!processCreatePR){
                                            System.out.print("Enter Item ID to create requisition (input Q to terminate create requisition): ");
                                            String inputItemID = sc.nextLine().trim().toUpperCase();
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
                                                itemExists = display.displayItem(true, inputItemID, "All", "All", "All");
                                                if (!itemExists){
                                                    System.out.println("Item ID is not found or doesnt exists. Please re-input Item ID.");
                                                }
                                                else{
                                                    itemSelected = inputItemID;
                                                    String filterSupplierIDCPR = "All", filterSupplierNameCPR = "All", filterItemBuyingPriceCPR = "All";
                                                    boolean exitCreateReq2 = false;
                                                    while (!exitCreateReq2){
                                                        display.displayItemSupplier(false, filterSupplierIDCPR, filterSupplierNameCPR, itemSelected, filterItemBuyingPriceCPR);
                                                        System.out.println("\n1.Edit Item Supplier filter\n2.Choose Supplier with Item to create Requisition\n3.Return to Purchase Requisition Page");
                                                        boolean chooseSupplier = false;
                                                        while (!chooseSupplier){
                                                            System.out.print("Enter your option: ");
                                                            String chooseSupplierOption = sc.nextLine().trim();
                                                            switch (chooseSupplierOption){
                                                                case "1"->{
                                                                    ArrayList<String> filters = f.itemSupplierFilterEdit(filterSupplierIDCPR, filterSupplierNameCPR, filterItemBuyingPriceCPR);
                                                                    filterSupplierIDCPR = filters.get(0);
                                                                    filterSupplierNameCPR = filters.get(1);
                                                                    filterItemBuyingPriceCPR = filters.get(2);
                                                                    chooseSupplier = true;
                                                                    break;
                                                                }
                                                                case "2"->{
                                                                    System.out.println();
                                                                    boolean processCreatePR2 = false;
                                                                    boolean supplierExists;
                                                                    while (!processCreatePR2){
                                                                        System.out.print("Enter Supplier ID to create requisition (input Q to terminate create requisition): ");
                                                                        String inputSupplierID = sc.nextLine().trim().toUpperCase();
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
                                                                            supplierExists = display.displayItemSupplier(true, inputSupplierID, "All", itemSelected, "All");
                                                                            if (!supplierExists){
                                                                               System.out.println("Supplier ID is not found or doesnt exists. Please re-input Supplier ID.");
                                                                            }
                                                                            else if (supplierExists){
                                                                                Item itemMethod = new Item();
                                                                                Supplier supplierMethod = new Supplier();
                                                                                LinkedHashMap<String, Item> itemListMap = itemMethod.readItemMap();
                                                                                LinkedHashMap<String, Supplier> supplierListMap = supplierMethod.readSupplierMap();
                                                                                boolean processEnterQuantity = false;
                                                                                while (!processEnterQuantity){
                                                                                    System.out.print("\nEnter quantity amount for item "+itemListMap.get(inputItemID).getName()+" (input Q to terminate create requisition): ");
                                                                                    String inputQuantity = sc.nextLine();
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
                                                                                        boolean checkDigits = f.onlyDigits(inputQuantity);
                                                                                        if (!checkDigits){
                                                                                            System.out.println("Only Integers are allow for quantity, please re-input quantity.");
                                                                                        }
                                                                                        else{
                                                                                            System.out.println("\nSummary for this new purchase requisition: ");
                                                                                            System.out.println("Supplier ID: "+inputSupplierID+"\nSupplier Name: "+supplierListMap.get(inputSupplierID).getName()+"\nItem ID: "+inputItemID+
                                                                                                               "\nItem Name: "+itemListMap.get(inputItemID).getName()+"\nQuantity: "+inputQuantity);
                                                                                            boolean comfirmReq = false;
                                                                                            while (!comfirmReq){
                                                                                                System.out.print("Are you sure you want to create this purchase requisition? (Y/N): ");
                                                                                                String enterComfirmReq = sc.nextLine();
                                                                                                if (enterComfirmReq.equalsIgnoreCase("n")){
                                                                                                    System.out.println("Terminated creating this requisition.");
                                                                                                    //processEnterQuantity = true;
                                                                                                    //processCreatePR2 = true;
                                                                                                    //processCreatePR = true;
                                                                                                    //chooseItem = true;
                                                                                                    //comfirmReq = true;
                                                                                                    //exitCreateReq2 = true;
                                                                                                    //chooseSupplier = true;
                                                                                                    break;
                                                                                                } 
                                                                                                else if (enterComfirmReq.equalsIgnoreCase("y")) {
                                                                                                    prMethod.createFile(prMethod.provideID("PR")+","+this.id+","+this.id+","+inputSupplierID+","+inputItemID+","+inputQuantity+",Approved,"+currentDateTime());
                                                                                                    System.out.println("Successfully created this requisition.");
                                                                                                    break;
                                                                                                }
                                                                                                else{
                                                                                                    System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                                                                }
                                                                                            }
                                                                                            while (true){
                                                                                                System.out.print("Do you want to create another requisition? (Y/N): ");
                                                                                                String inputRepeatCreate = sc.nextLine();
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
                    case "4":{
                        String filterReqIDDPR = "All", filterSubmitterIDDPR = "All", filterSubmitterUsernameDPR = "All", filterApproverIDDPR = "All", filterApproverNameDPR = "All", filterSupplierIDDPR = "All", 
                        filterSupplierNameDPR = "All", filterItemIDDPR = "All", filterItemNameDPR = "All", filterQuantityDPR = "All", filterStatusDPR = "All", filterDateTimeDPR = "XX-XX-XXXX";
                        boolean processDltReq = false;
                        while (!processDltReq){
                            display.displayRequisition(true, false, filterReqIDDPR, filterSubmitterIDDPR, filterSubmitterUsernameDPR, filterApproverIDDPR, filterApproverNameDPR, filterSupplierIDDPR, filterSupplierNameDPR, filterItemIDDPR, filterItemNameDPR, filterQuantityDPR, filterStatusDPR, filterDateTimeDPR);
                            System.out.println("\n1.Edit purchase requisition filter\n2.Choose purchase requisition to delete\n3.Return to Purchase Requisition Page");
                            boolean chooseDltReq = false;
                            while (!chooseDltReq){
                                System.out.print("Enter your option: ");
                                String dltReqOption = sc.nextLine();
                                switch (dltReqOption){
                                    case "1"->{ // go to filter editer and change so that they cannot edit DateTime.
                                        ArrayList<String> filter = f.requisitionPageFilterEdit("Deletion", filterReqIDDPR, filterSubmitterIDDPR, filterSubmitterUsernameDPR, filterApproverIDDPR, filterApproverNameDPR, filterSupplierIDDPR, filterSupplierNameDPR, filterItemIDDPR, filterItemNameDPR, filterQuantityDPR, filterStatusDPR, filterDateTimeDPR);
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
                                        System.out.println();
                                        boolean processDltPR = false;
                                        while (!processDltPR){
                                            System.out.print("Enter purchase requisition ID to delete requisition (input Q to terminate delete requisition): ");
                                            String inputDltReqID = sc.nextLine().trim().toUpperCase();
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
                                                prExists = display.displayRequisition(true, true, inputDltReqID, "All", "All", "All", "All", "All", "All", "All", "All", "All", "All", "XX-XX-XXXX");
                                                if (!prExists){
                                                    System.out.println("Purchase Requisition ID is not found or doesnt exists. Please re-input purchase requisition ID.");
                                                }
                                                else{
                                                    boolean comfirmDltReq = false;
                                                    while (!comfirmDltReq){
                                                        System.out.print("Are you sure you want to delete this purchase requisition? (Y/N): ");
                                                        String enterComfirmReq = sc.nextLine();
                                                        if (enterComfirmReq.equalsIgnoreCase("n")){
                                                            System.out.println("Terminated deleting this requisition.");
                                                            break;
                                                        } 
                                                        else if (enterComfirmReq.equalsIgnoreCase("y")) {
                                                            prMethod.deleteLineFile(inputDltReqID);
                                                            System.out.println("Successfully deleted this requisition.");
                                                            break;
                                                        }
                                                        else{
                                                            System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                        }
                                                    }
                                                    while (true){
                                                        System.out.print("Do you want to delete another requisition? (Y/N): ");
                                                        String inputRepeatCreate = sc.nextLine();
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
                    case "5":{
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
    
    public void createViewPurchaseOrder(){
        Scanner sc = new Scanner(System.in);
        PMDisplay display = new PMDisplay();
        PurchaseRequisition prMethod = new PurchaseRequisition();
        PurchaseOrder poMethod = new PurchaseOrder();
        PurchaseOrderItem poItemMethod = new PurchaseOrderItem();
        OrderRequisition orMethod = new OrderRequisition();
        Supplier supplierMethod = new Supplier();
        ItemsSupplier isMethod = new ItemsSupplier();
        PMFilter f = new PMFilter();
        String filterPurchaseOrderID = "All", filterSubmitterID = "All", filterSubmitterUsername = "All", filterApproverID = "All", filterApproverName = "All", 
               filterSupplierID = "All", filterSupplierName = "All", filterStatus = "All", filterPaymentStatus = "All", filterDateTime = "XX-XX-XXXX";
        boolean exitPOPage = false;
        
        while (!exitPOPage){
            display.displayPurchaseOrder(true, false, filterPurchaseOrderID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterStatus, filterPaymentStatus, filterDateTime);
            System.out.println("\n1.Edit filter\n2.View purchase order item list\n3.Create new purchase order\n4.Delete purchase order (within 2hrs)\n5.Return to PM Page");
            boolean chooseOption = false;
            while(!chooseOption){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch (option){
                    case "1"->{
                        ArrayList<String> filter = f.orderPageFilterEdit("None", filterPurchaseOrderID, filterSubmitterID, filterSubmitterUsername, filterApproverID, filterApproverName, filterSupplierID, filterSupplierName, filterStatus, filterPaymentStatus, filterDateTime);
                        filterPurchaseOrderID = filter.get(0);
                        filterSubmitterID = filter.get(1);
                        filterSubmitterUsername = filter.get(2);
                        filterApproverID = filter.get(3);
                        filterApproverName = filter.get(4);
                        filterSupplierID = filter.get(5);
                        filterSupplierName = filter.get(6);
                        filterStatus = filter.get(7);
                        filterPaymentStatus = filter.get(8);
                        filterDateTime = filter.get(9);
                        chooseOption = true;
                        break;
                    }
                    case "2"->{
                        System.out.println();
                        while (true){
                            System.out.print("Enter Purchase Order ID to view purchase order item list (input Q to return to purchase order page): ");
                            String inputViewOrder = sc.nextLine().trim().toUpperCase();
                            if (inputViewOrder.equalsIgnoreCase("q")){
                                System.out.println("Returning to Purchase Order Page...");
                                chooseOption = true;
                                break;
                            }
                            else if (inputViewOrder.isEmpty()){
                                System.out.println("Please enter a valid Supplier ID.");
                            }
                            else{
                                boolean orderItemExists = display.displayPurchaseOrderItem(true, inputViewOrder);
                                if (!orderItemExists){
                                    System.out.println("Invalid input Purchase Order ID, please re-input starting with PO.");
                                }
                                else{
                                    display.displayPurchaseOrderItem(false, inputViewOrder);
                                }
                            }
                        }
                    }
                    case "3"->{ //SCO = Supplier Create Order
                        String filterSupplierIDSCO = "All", filterSupplierNameSCO = "All", filterReqIDSCO = "All", filterSubmitterIDSCO = "All", filterSubmitterUsernameSCO = "All", filterApproverIDSCO = "All", filterApproverNameSCO = "All", filterItemIDSCO = "All", filterItemNameSCO = "All",filterQuantitySCO = "All", filterStatusSCO = "Approved", filterDateTimeSCO = "XX-XX-XXXX";
                        boolean exitCreateOrder = false;
                        while (!exitCreateOrder){
                            display.displaySupplierWithReq(false, filterSupplierIDSCO, filterSupplierNameSCO, filterReqIDSCO, filterSubmitterIDSCO, filterSubmitterUsernameSCO, filterApproverIDSCO, filterApproverNameSCO, filterItemIDSCO, filterItemNameSCO, filterQuantitySCO, "Approved", filterDateTimeSCO);
                            System.out.println("\n1.Edit purchase order filter\n2.Choose supplier to create purchase order\n3.Return to Purchase Order Page");
                            boolean chooseSupplier = false;
                            while (!chooseSupplier){
                                System.out.print("Enter your option: ");
                                String chooseSupplierOption = sc.nextLine().toUpperCase().trim();
                                switch (chooseSupplierOption){
                                    case "1"->{
                                        ArrayList<String> filters = f.orderSupplierFilterEdit(filterSupplierIDSCO, filterSupplierNameSCO, filterReqIDSCO, filterSubmitterIDSCO, filterSubmitterUsernameSCO, filterApproverIDSCO, filterApproverNameSCO, filterItemIDSCO, filterItemNameSCO, filterQuantitySCO, filterStatusSCO, filterDateTimeSCO);
                                        filterSupplierIDSCO = filters.get(0);
                                        filterSupplierNameSCO = filters.get(1);
                                        filterReqIDSCO = filters.get(2);
                                        filterSubmitterIDSCO = filters.get(3);
                                        filterSubmitterUsernameSCO = filters.get(4);
                                        filterApproverIDSCO = filters.get(5);
                                        filterApproverNameSCO = filters.get(6);
                                        filterItemIDSCO = filters.get(7);
                                        filterItemNameSCO = filters.get(8);
                                        filterQuantitySCO = filters.get(9);
                                        filterStatusSCO = filters.get(10);
                                        filterDateTimeSCO = filters.get(11);
                                        chooseSupplier = true;
                                        break;
                                    }
                                    case "2"->{
                                        System.out.println();
                                        boolean processCreatePO = false;
                                        boolean supplierExists;
                                        while (!processCreatePO){
                                            System.out.print("Enter Supplier ID to create purchase order (input Q to terminate create purchase order): ");
                                            String inputSupplierID = sc.nextLine().trim().toUpperCase();
                                            if (inputSupplierID.equalsIgnoreCase("q")){
                                                System.out.println("Terminated creating this purchase order.");
                                                chooseSupplier = true;
                                                break;
                                            }
                                            else if (inputSupplierID.isEmpty()) {
                                                System.out.println("Please enter a valid Supplier ID.");
                                            }
                                            else if(!inputSupplierID.substring(0,1).equals("S") || inputSupplierID.length() > 5){
                                                System.out.println("Supplier ID format wrong, please re-enter correct format requisition ID.");
                                            }
                                            else {
                                                String supplierSelected;
                                                supplierExists = display.displaySupplierWithReq(true, inputSupplierID, "All", "All", "All", "All", "All", "All", "All", "All", "All", "Approved", "XX-XX-XXXX");
                                                if (!supplierExists){
                                                    System.out.println("Supplier ID is not found or doesnt exists. Please re-input Supplier ID.");
                                                }
                                                else{
                                                    supplierSelected = inputSupplierID;
                                                    ArrayList<String> chosenReqID = new ArrayList<>();
                                                    boolean reqFound;
                                                    boolean reqChosenFound;
                                                    String filterReqIDCPO = "All", filterSubmitterIDCPO = "All", filterSubmitterUsernameCPO = "All", filterApproverIDCPO = "All", filterApproverNameCPO = "All", filterSupplierIDCPO = supplierSelected, 
                                                           filterSupplierNameCPO = "All", filterItemIDCPO = "All", filterItemNameCPO = "All", filterQuantityCPO = "All", filterStatusCPO = "Approved", filterDateTimeCPO = "XX-XX-XXXX";
                                                    boolean exitCreatePO2 = false;
                                                    while (!exitCreatePO2){
                                                        System.out.println("\nRequisitions under Supplier that can be chosen: ");
                                                        reqFound = display.displayRequisition(true, false, "display", chosenReqID, filterReqIDCPO, filterSubmitterIDCPO, filterSubmitterUsernameCPO, filterApproverIDCPO, filterApproverNameCPO, supplierSelected, filterSupplierNameCPO, filterItemIDCPO, filterItemNameCPO, filterQuantityCPO, "Approved", filterDateTimeCPO);
                                                        System.out.println("\nSelected Requisitions: ");
                                                        reqChosenFound = display.displayRequisition(true, false, "displayChosenReqID", chosenReqID, filterReqIDCPO, filterSubmitterIDCPO, filterSubmitterUsernameCPO, filterApproverIDCPO, filterApproverNameCPO, supplierSelected, filterSupplierNameCPO, filterItemIDCPO, filterItemNameCPO, filterQuantityCPO, "Approved", filterDateTimeCPO);
                                                        System.out.println("\n1.Edit purchase requisition filter\n2.Choose purchase requisition to create Requisition\n3.Remove purchase requisition from selected list\n4.Comfirm create purchase order\n5.Return to Purchase Order Page");
                                                        boolean chooseRequisition = false;
                                                        while(!chooseRequisition){
                                                            System.out.print("Enter your option: ");
                                                            String chooseRequisitionOption = sc.nextLine().trim();
                                                            switch (chooseRequisitionOption){
                                                                case "1"->{
                                                                    ArrayList<String> filter = f.requisitionPageFilterEdit("CPO", filterReqIDCPO, filterSubmitterIDCPO, filterSubmitterUsernameCPO, filterApproverIDCPO, filterApproverNameCPO, filterSupplierIDCPO, filterSupplierNameCPO, filterItemIDCPO, filterItemNameCPO, filterQuantityCPO, filterStatusCPO, filterDateTimeCPO);
                                                                    filterReqIDCPO = filter.get(0);
                                                                    filterSubmitterIDCPO = filter.get(1);
                                                                    filterSubmitterUsernameCPO = filter.get(2);
                                                                    filterApproverIDCPO = filter.get(3);
                                                                    filterApproverNameCPO = filter.get(4);
                                                                    filterSupplierIDCPO = filter.get(5);
                                                                    filterSupplierNameCPO = filter.get(6);
                                                                    filterItemIDCPO = filter.get(7);
                                                                    filterItemNameCPO = filter.get(8);
                                                                    filterQuantityCPO = filter.get(9);
                                                                    filterStatusCPO = filter.get(10);
                                                                    filterDateTimeCPO = filter.get(11);
                                                                    chooseRequisition = true;
                                                                    break;
                                                                }
                                                                case "2"->{
                                                                    System.out.println();
                                                                    if (!reqFound){
                                                                        System.out.println("No requisition can be selected, returning to selecting requisition to create order page.");
                                                                        chooseRequisition = true;
                                                                        break;
                                                                    }
                                                                    boolean processCreatePO2 = false;
                                                                    boolean reqNotChosen;    // if not chosen before then can add, if chosen before then cannot add.
                                                                    while (!processCreatePO2){
                                                                        System.out.print("Enter Requisition ID to create purchase order (input Q to return to selecting requisition to create order page): ");
                                                                        String inputReqID = sc.nextLine().trim().toUpperCase();
                                                                        if (inputReqID.equalsIgnoreCase("q")){
                                                                            System.out.println("Return to selecting requisition to create order page");
                                                                            chooseRequisition = true;
                                                                            processCreatePO2 = true;
                                                                            break;
                                                                        }
                                                                        else if (inputReqID.isEmpty()) {
                                                                            System.out.println("Please enter a valid Requisition ID.");
                                                                        }
                                                                        else if(inputReqID.length() < 2 || !inputReqID.substring(0, 2).equals("PR") || inputReqID.length() > 6){
                                                                            System.out.println("Requisition ID format wrong, please re-enter correct format requisition ID.");
                                                                        }
                                                                        else {
                                                                            reqNotChosen = display.displayRequisition(false, true, "input", chosenReqID, inputReqID, "All", "All", "All", "All", supplierSelected, "All", "All", "All", "All", "Approved", "XX-XX-XXXX");
                                                                            if (reqNotChosen){
                                                                                while (true){
                                                                                    System.out.print("Are you sure you want to select this requisition? (Y/N):");
                                                                                    String inputAddReq = sc.nextLine();
                                                                                    if (inputAddReq.equalsIgnoreCase("n")){
                                                                                        System.out.println("Abort successfully. Please re-enter your requisition ID to create purchase order.\n");
                                                                                        break;
                                                                                    }
                                                                                    else if (inputAddReq.equalsIgnoreCase("y")){
                                                                                        chosenReqID.add(inputReqID);
                                                                                        System.out.println("Successfully added requisition into selected requisitions.");
                                                                                        chooseRequisition = true;
                                                                                        processCreatePO2 = true;
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
                                                                    System.out.println();
                                                                    if (!reqChosenFound){
                                                                        System.out.println("No requisition can be selected, returning to selecting requisition to create order page.");
                                                                        chooseRequisition = true;
                                                                        break;
                                                                    }
                                                                    boolean processDeletePO2 = false;
                                                                    boolean reqExists;
                                                                    while (!processDeletePO2){
                                                                        System.out.print("Enter Requisition ID to remove a selected requisition (input Q to return to selecting requisition to create order page): ");
                                                                        String inputReqID = sc.nextLine().trim().toUpperCase();
                                                                        if (inputReqID.equalsIgnoreCase("q")){
                                                                            System.out.println("Return to selecting requisition to create order page.");
                                                                            chooseRequisition = true;
                                                                            processDeletePO2 = true;
                                                                            break;
                                                                        }
                                                                        else if (inputReqID.isEmpty()) {
                                                                            System.out.println("Please enter a valid Requisition ID.");
                                                                        }
                                                                        else if(inputReqID.length() < 2 || !inputReqID.substring(0, 2).equals("PR") || inputReqID.length() > 6){
                                                                            System.out.println("Requisition ID format wrong, please re-enter correct format requisition ID.");
                                                                        }
                                                                        else {
                                                                            reqExists = display.displayRequisition(false, true, "delete", chosenReqID, inputReqID, "All", "All", "All", "All", supplierSelected, "All", "All", "All", "All", "Approved", "XX-XX-XXXX");
                                                                            if (reqExists){
                                                                                while (true){
                                                                                    System.out.print("Are you sure you want to remove this requisition? (Y/N):");
                                                                                    String inputAddReq = sc.nextLine();
                                                                                    if (inputAddReq.equalsIgnoreCase("n")){
                                                                                        System.out.println("Abort successfully. Please re-enter your requisition ID to remove requisition from selected requisitions.\n");
                                                                                        break;
                                                                                    }
                                                                                    else if (inputAddReq.equalsIgnoreCase("y")){
                                                                                        chosenReqID.remove(inputReqID);
                                                                                        System.out.println("Successfully removed requisition from selected requisitions.");
                                                                                        chooseRequisition = true;
                                                                                        processDeletePO2 = true;
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
                                                                case "4"->{
                                                                    System.out.println();
                                                                    if (chosenReqID.isEmpty()){
                                                                        System.out.println("No requisition is selected, returning to selecting requisition to create order page.");
                                                                        chooseRequisition = true;
                                                                        break;
                                                                    }
                                                                    System.out.println("Selected Requisitions: ");
                                                                    display.displayRequisition(true, false, "displayChosenReqID", chosenReqID, filterReqIDCPO, filterSubmitterIDCPO, filterSubmitterUsernameCPO, filterApproverIDCPO, filterApproverNameCPO, supplierSelected, filterSupplierNameCPO, filterItemIDCPO, filterItemNameCPO, filterQuantityCPO, "Approved", filterDateTimeCPO);
                                                                    System.out.println();
                                                                    boolean comfirmCreatePO = false;
                                                                    while (!comfirmCreatePO){
                                                                        System.out.print("Are you sure you want to create this purchase order? (input Q to return to selecting requisition to create order page) (Y/N):");
                                                                        String inputAddReq = sc.nextLine();
                                                                        if (inputAddReq.equalsIgnoreCase("q")){
                                                                            System.out.println("Return to selecting requisition to create order page");
                                                                            chooseRequisition = true;
                                                                            comfirmCreatePO = true;
                                                                            break;
                                                                        }
                                                                        else if (inputAddReq.equalsIgnoreCase("n")){
                                                                            System.out.println("Abort successfully.");
                                                                            chooseRequisition = true;
                                                                            comfirmCreatePO = true;
                                                                            break;
                                                                        }
                                                                        else if (inputAddReq.equalsIgnoreCase("y")){
                                                                            ArrayList<PurchaseRequisition> purchaseRequisitionList = prMethod.readPR();
                                                                            ArrayList<ItemsSupplier> itemSupplierList = isMethod.readIS();
                                                                            LinkedHashMap<String, Supplier> supplierListMap = supplierMethod.readSupplierMap();
                                                                            ArrayList<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
                                                                            ArrayList<OrderRequisition> orderRequisitions = new ArrayList<>();
                                                                            String supplierID = supplierSelected;
                                                                            String orderID = poMethod.provideID("PO"); // Generate a new PO ID
                                                                            String currentDateTime = currentDateTime();

                                                                            for (String reqID : chosenReqID) {
                                                                                for (PurchaseRequisition requisition : purchaseRequisitionList) {
                                                                                    if (requisition.getId().equals(reqID)) {
                                                                                        requisition.setStatus("Processed");
                                                                                        String itemID = requisition.getItemID();
                                                                                        String quantity = requisition.getQuantity();
                                                                                        String supplierName = supplierListMap.containsKey(supplierID) ? supplierListMap.get(supplierID).getName() : "Unknown";
                                                                                        String unitPrice = "0";
                                                                                        for (ItemsSupplier itemSupplier : itemSupplierList) {
                                                                                            if (itemSupplier.getId().equals(itemID) && itemSupplier.getSupplierName().equals(supplierName)) {
                                                                                                unitPrice = itemSupplier.getItemBuyingPrice();
                                                                                                break;
                                                                                            }
                                                                                        }
                                                                                        
                                                                                        boolean itemExists = false;
                                                                                        for (PurchaseOrderItem orderItem : purchaseOrderItems) {
                                                                                            if (orderItem.getItemId().equals(itemID)) {
                                                                                                orderItem.setQuantity(Integer.toString(Integer.parseInt(orderItem.getQuantity()) + Integer.parseInt(quantity)));
                                                                                                itemExists = true;
                                                                                                break;
                                                                                            }
                                                                                        }
                                                                                        if (!itemExists) {
                                                                                            purchaseOrderItems.add(new PurchaseOrderItem(orderID, itemID, quantity, unitPrice));
                                                                                        }

                                                                                        orderRequisitions.add(new OrderRequisition(orderID, reqID));
                                                                                        break;
                                                                                    }
                                                                                }
                                                                            }

                                                                            prMethod.updateFile(purchaseRequisitionList);
                                                                            poMethod.createFile(orderID + "," + this.id + ",-," + supplierID + ",Pending,None," + currentDateTime);
                                                                            for (PurchaseOrderItem orderItem : purchaseOrderItems) {
                                                                                poItemMethod.createFile(orderItem.getId() + "," + orderItem.getItemId() + "," + orderItem.getQuantity() + "," + orderItem.getUnitPrice());
                                                                            }
                                                                            for (OrderRequisition order : orderRequisitions) {
                                                                                orMethod.createFile(order.getId() + "," + order.getPrId());
                                                                            }

                                                                            System.out.println("Successfully created this purchase order.");
                                                                            
                                                                            while (true){
                                                                                System.out.print("Do you want to create another purchase order? (Y/N): ");
                                                                                String inputRepeatCreate = sc.nextLine();
                                                                                if (inputRepeatCreate.equalsIgnoreCase("n")){
                                                                                    chooseOption = true;
                                                                                    exitCreateOrder = true;
                                                                                    chooseSupplier = true;
                                                                                    processCreatePO = true;
                                                                                    exitCreatePO2 = true;
                                                                                    chooseRequisition = true;
                                                                                    comfirmCreatePO = true;
                                                                                    break;
                                                                                }
                                                                                else if (inputRepeatCreate.equalsIgnoreCase("y")){
                                                                                    chooseSupplier = true;
                                                                                    processCreatePO = true;
                                                                                    exitCreatePO2 = true;
                                                                                    chooseRequisition = true;
                                                                                    comfirmCreatePO = true;
                                                                                    filterSupplierIDSCO = "All";
                                                                                    filterSupplierNameSCO = "All";
                                                                                    filterReqIDSCO = "All";
                                                                                    filterSubmitterIDSCO = "All";
                                                                                    filterSubmitterUsernameSCO = "All";
                                                                                    filterApproverIDSCO = "All";
                                                                                    filterApproverNameSCO = "All";
                                                                                    filterItemIDSCO = "All";
                                                                                    filterItemNameSCO = "All";
                                                                                    filterQuantitySCO = "All";
                                                                                    filterStatusSCO = "Approved";
                                                                                    filterDateTimeSCO = "XX-XX-XXXX";
                                                                                    break;
                                                                                }
                                                                                else{
                                                                                    System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                                                }
                                                                            }
                                                                            break;
                                                                        }
                                                                        else{
                                                                            System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                                        }
                                                                    }
                                                                    break;
                                                                }
                                                                case "5"->{
                                                                    chooseOption = true;
                                                                    exitCreateOrder = true;
                                                                    chooseSupplier = true;
                                                                    processCreatePO = true;
                                                                    exitCreatePO2 = true;
                                                                    chooseRequisition = true;
                                                                    break;
                                                                }
                                                                default->{
                                                                    System.out.println("Invalid input option, please re-input.");
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
                                        chooseOption = true;
                                        exitCreateOrder = true;
                                        chooseSupplier = true;
                                        break;
                                    }
                                }
                            }   
                        }
                    }
                    case "4"->{
                        String filterPurchaseOrderIDDPO = "All", filterSubmitterIDDPO = "All", filterSubmitterUsernameDPO = "All", filterApproverIDDPO = "All", filterApproverNameDPO = "All", 
                        filterSupplierIDDPO = "All", filterSupplierNameDPO = "All", filterStatusDPO = "Pending", filterPaymentStatusDPO = "None", filterDateTimeDPO = "XX-XX-XXXX";
                        boolean processDltOrder = false;
                        boolean poDltFound = false;
                        while (!processDltOrder){
                            poDltFound = display.displayPurchaseOrder(true, true, filterPurchaseOrderIDDPO, filterSubmitterIDDPO, filterSubmitterUsernameDPO, filterApproverIDDPO, filterApproverNameDPO, filterSupplierIDDPO, filterSupplierNameDPO, "Pending", "None", filterDateTimeDPO);                            
                            if (!poDltFound){
                                System.out.println("Returning to Purchase Order Page... ");
                                chooseOption = true;
                                break;
                            }
                            System.out.println("\n1.Edit purchase order filter\n2.Choose purchase order to delete\n3.Return to Purchase Order Page");
                            boolean chooseDltOrder = false;
                            while (!chooseDltOrder){
                                System.out.print("Enter your option: ");
                                String dltReqOption = sc.nextLine();
                                switch (dltReqOption){
                                    case "1"->{ // go to filter editer and change so that they cannot edit DateTime.
                                        ArrayList<String> filter = f.orderPageFilterEdit("Deletion", filterPurchaseOrderIDDPO, filterSubmitterIDDPO, filterSubmitterUsernameDPO, filterApproverIDDPO, filterApproverNameDPO, filterSupplierIDDPO, filterSupplierNameDPO, filterStatusDPO, filterPaymentStatusDPO, filterDateTimeDPO);
                                        filterPurchaseOrderIDDPO = filter.get(0);
                                        filterSubmitterIDDPO = filter.get(1);
                                        filterSubmitterUsernameDPO = filter.get(2);
                                        filterApproverIDDPO = filter.get(3);
                                        filterApproverNameDPO = filter.get(4);
                                        filterSupplierIDDPO = filter.get(5);
                                        filterSupplierNameDPO = filter.get(6);
                                        filterStatusDPO = filter.get(7);
                                        filterPaymentStatusDPO = filter.get(8);
                                        filterDateTimeDPO = filter.get(9);
                                        chooseDltOrder = true;
                                        break;
                                    }
                                    case "2"->{
                                        System.out.println();
                                        boolean processDltPO = false;
                                        while (!processDltPO){
                                            System.out.print("Enter purchase order ID to delete order (input Q to terminate delete purchase order): ");
                                            String inputDltOrderID = sc.nextLine().trim().toUpperCase();
                                            if (inputDltOrderID.equalsIgnoreCase("q")){
                                                System.out.println("Terminated deleting this purchase order.");
                                                chooseDltOrder = true;
                                                processDltPO = true;
                                                break;
                                            }
                                            else if (inputDltOrderID.isEmpty()) {
                                                System.out.println("Please enter a valid purchase order ID.");
                                            }
                                            else if(inputDltOrderID.length() < 2 || !inputDltOrderID.substring(0,2).equals("PO") || inputDltOrderID.length() > 6){
                                                System.out.println("Purchase Order ID format wrong, please re-enter correct format purchase order ID.");
                                            }
                                            else {
                                                //String poSelected;
                                                boolean poExists;
                                                poExists = display.displayPurchaseOrder(false, true, inputDltOrderID, "All", "All", "All", "All", "All", "All", "Pending", "None", "XX-XX-XXXX");
                                                if (!poExists){
                                                    System.out.println("Purchase Order ID is not found or doesnt exists. Please re-input purchase order ID.");
                                                }
                                                else{
                                                    boolean comfirmDltOrder = false;
                                                    while (!comfirmDltOrder){
                                                        System.out.print("Are you sure you want to delete this purchase order? (Y/N): ");
                                                        String enterComfirmReq = sc.nextLine();
                                                        if (enterComfirmReq.equalsIgnoreCase("n")){
                                                            System.out.println("Terminated deleting this purchase order.");
                                                            break;
                                                        } 
                                                        else if (enterComfirmReq.equalsIgnoreCase("y")) {
                                                            ArrayList<PurchaseRequisition> purchaseRequisitionList = prMethod.readPR();
                                                            ArrayList<OrderRequisition> orderRequisitions = orMethod.readOR();
                                                            for (OrderRequisition orderReq : orderRequisitions){
                                                                if (orderReq.getId().equals(inputDltOrderID)){
                                                                    String requisitionID = orderReq.getPrId();
                                                                    for (PurchaseRequisition requisition : purchaseRequisitionList){
                                                                        if (requisition.getId().equals(requisitionID)){
                                                                            requisition.setStatus("Approved");
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            prMethod.updateFile(purchaseRequisitionList);
                                                            poMethod.deleteLineFile(inputDltOrderID);
                                                            poItemMethod.deleteLineFile(inputDltOrderID);
                                                            orMethod.deleteLineFile(inputDltOrderID);
                                                            System.out.println("Successfully deleted this purchase order.");
                                                            break;
                                                        }
                                                        else{
                                                            System.out.println("Only Y or N are allow for answer, please re-input quantity.");
                                                        }
                                                    }
                                                    while (true){
                                                        System.out.print("Do you want to delete another purchase order? (Y/N): ");
                                                        String inputRepeatCreate = sc.nextLine();
                                                        if (inputRepeatCreate.equalsIgnoreCase("n")){
                                                            chooseOption = true;
                                                            processDltOrder = true;
                                                            chooseDltOrder = true;
                                                            processDltPO = true;
                                                            comfirmDltOrder = true;
                                                            break;
                                                        }
                                                        else if (inputRepeatCreate.equalsIgnoreCase("y")){
                                                            chooseDltOrder = true;
                                                            processDltPO = true;
                                                            comfirmDltOrder = true;
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
                                        processDltOrder = true;
                                        chooseDltOrder = true;
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
                    case "5"->{
                        chooseOption = true;
                        exitPOPage = true;
                        break;
                    }
                    default->{
                        System.out.println("Invalid input option, please re-input.");
                    }
                }
            }
        }
    }
}
