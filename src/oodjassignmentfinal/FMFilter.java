package oodjassignmentfinal;

import java.util.*;

public class FMFilter extends Filter{
    protected ArrayList<String> managePurchaseOrderFilter(
            String filterPurchaseOrderID, String filterSubmitterID, String filterApproverID, 
            String filterSupplierID, String filterStatus, String filterPaymentStatus, String filterDate
        ){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> filterEdit = new ArrayList<>();
        filterEdit.addAll(Arrays.asList(filterPurchaseOrderID,filterSubmitterID,filterApproverID,filterSupplierID,filterStatus,filterPaymentStatus,filterDate));
        while(true){
            boolean optionChoose = false;
            System.out.printf("Purchase Order ID: %s | SubmitterID: %s | ApproverID: %s | SupplierID: %s | Status: %s | Payment Status: %s | Date: %s\n", 
            filterEdit.get(0),filterEdit.get(1),filterEdit.get(2),filterEdit.get(3),filterEdit.get(4),filterEdit.get(5),filterEdit.get(6));
            System.out.println("\nFilter options:\n1.Purchase Order ID\n2.SubmitterID\n3.ApproverID\n4.SupplierID\n5.Status\n6.Payment Status\n7.Date\n8.Reset Filter\n9.Apply");
            while(!optionChoose){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch(option){
                    case "1" ->{
                        filterEdit.set(0, filterIDEditer(false,"Purchase Order ID","PO",null));
                        optionChoose = true;
                    }
                    case "2" ->{
                        filterEdit.set(1, filterIDEditer(false,"Submitter UserID","PM",null));
                        optionChoose = true;
                    }
                    case "3" ->{
                        filterEdit.set(2, filterIDEditer(false,"Approver UserID","FM",null));
                        optionChoose = true;
                    }
                    case "4" ->{
                        filterEdit.set(3, filterIDEditer(false,"SupplierID","S",null));
                        optionChoose = true;
                    }
                    case "5" ->{
                        filterEdit.set(4, filterDataEditer("poStatus", null));
                        optionChoose = true;
                    }
                    case "6" ->{
                        filterEdit.set(5, filterDataEditer("poPaymentStatus", null));
                        optionChoose = true;
                    }
                    case "7" ->{
                         filterEdit.set(6, filterDataEditer("Date", null));
                         optionChoose = true;
                    }
                    case "8" ->{
                        for(int i = 0; i < 6; i++){
                            filterEdit.set(i, "All");
                        }
                        filterEdit.set(6, "XX-XX-XXXX");
                        optionChoose = true;
                    }
                    case "9" ->{
                        return filterEdit;
                    }
                    default ->{
                        System.out.println("Invalid input. Please try again.");
                    }
                }
            }
        }
    }
    
    protected ArrayList<String> managePaymentPaymentFilter(
            String filterPurchaseOrderID, String filterApproverID, String filterSupplierID, String filterStatus, String filterPaymentStatus, String filterDate, String filterTotalAmount
    ){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> filterEdit = new ArrayList();
        filterEdit.addAll(Arrays.asList(filterPurchaseOrderID, filterApproverID, filterSupplierID, filterStatus, filterPaymentStatus, filterDate, filterTotalAmount));
        while(true){
            boolean optionChoose = false;
            System.out.printf("\nPurchase Order ID: %s | ApproverID: %s | SupplierID: %s | Status: %s | Payment Status: %s | Date: %s | Total Amount: %s", 
            filterEdit.get(0), filterEdit.get(1), filterEdit.get(2), filterEdit.get(3), filterEdit.get(4), filterEdit.get(5), filterEdit.get(6));
            System.out.println("\nFilter option:\n1.Purchase Order ID\n2.ApproverID\n3.SupplierID\n4.Status\n5.Payment Status\n6.Date\n7.Total Amount\n8.Reset Filter\n9.Apply");
            while(!optionChoose){
                System.out.print("Enter your option: ");
                String option = sc.nextLine();
                switch(option){
                    case "1" ->{
                        filterEdit.set(0, filterIDEditer(false,"Purchase Order ID","PO",null));
                        optionChoose = true;
                    }
                    case "2" ->{
                        filterEdit.set(1, filterIDEditer(false,"Approver UserID","FM",null));
                        optionChoose = true;
                    }
                    case "3" ->{
                        filterEdit.set(2, filterIDEditer(false,"SupplierID","S",null));
                        optionChoose = true;
                    }
                    case "4" ->{
                        filterEdit.set(3, filterDataEditer("poStatus", null));
                        optionChoose = true;
                    }
                    case "5" ->{
                        filterEdit.set(4, filterDataEditer("poPaymentStatus", null));
                        optionChoose = true;
                    }
                    case "6" ->{
                        filterEdit.set(5, filterDataEditer("Date", null));
                        optionChoose = true;
                    }
                    case "7" ->{
                        filterEdit.set(6, filterDataEditer("poTotalAmount", null));
                        optionChoose = true;
                    }
                    case "8" ->{
                        for(int i = 0; i < 5; i++){
                            filterEdit.set(i, "All");
                        }
                        filterEdit.set(5, "XX-XX-XXXX");
                        filterEdit.set(6, "All");
                        optionChoose = true;
                    }
                    case "9" ->{
                        return filterEdit;
                    }
                    default ->{
                        System.out.println("Invalid input. Please try again.");
                    }
                }
            }
        }
    }
}
