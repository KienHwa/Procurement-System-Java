package oodjassignmentfinal;

import java.util.*;

public class Filter {
    public boolean dateCompare(String filter, String date){
        if(filter.equals("xx-xx-xxxx"))
            return true;
        
        String filterDay = filter.substring(0,2);
        String filterMonth = filter.substring(3,5);
        String filterYear = filter.substring(6,10);
        String day = date.substring(0,2);
        String month = date.substring(3,5);
        String year = date.substring(6,10);
        
        boolean dayLogic = true;
        boolean monthLogic = true;
        boolean yearLogic = true;
        
        if(!filterYear.equals("XXXX")){
            if(!filterYear.equals(year)){
                yearLogic = false;
            }
        }
        if(!filterMonth.equals("XX")){
            if(!filterMonth.equals(month)){
                monthLogic = false;
            }
        }
        if(!filterDay.equals("XX")){
            if(!filterDay.equals(day)){
                dayLogic = false;
            }
        }
        
        return yearLogic && monthLogic && dayLogic;
    }
    
    public boolean dataCompare(String filter, String data){
        return filter.equals(data) || filter.equals("All");
    }
    
    public boolean rangeCompare(String filterRange, float data){
        if(filterRange.equals("All")){
            return true;
        }
        String[] range = filterRange.split(" <= x <= ");
        float initialValue = Float.parseFloat(range[0]);
        float finalValue = Float.parseFloat(range[1]);
        return data >= initialValue && data <= finalValue;
    }
    
    private String formatString(String input){
        String[] words = input.split(" ");
        String output = "";
        for(String word : words){
            String firstLetter = word.substring(0,1).toUpperCase();
            String letters = word.substring(1).toLowerCase();
            output = output+" "+firstLetter+letters;
        }
        return output.trim();
    }
    
    private static boolean checkDate(String input){
        String[] dates = input.split("-");
        String dayString = dates[0];
        String monthString = dates[1];
        String yearString = dates[2];
        int month = 2;
        int year = 2000;

        if (dates.length != 3){
            return false;   // basically if no day month and year
        }

        if (!yearString.equalsIgnoreCase("XXXX")){
            try{
                year = Integer.parseInt(yearString);
                if (year < 2000 || year > 2024){
                    return false;
                } 
            }
            catch(NumberFormatException e){
                return false;
            }
        }

        if (!monthString.equalsIgnoreCase("XX")){
            try{
                month = Integer.parseInt(monthString);
                if (month < 1 || month > 12){
                    return false;
                } 
            }
            catch(NumberFormatException e){
                return false;
            }
        }

        if (!dayString.equalsIgnoreCase("XX")){
            try{
                int day = Integer.parseInt(dayString);
                int dayMonth = monthString.equalsIgnoreCase("XX") ? 2 : month;
                int dayYear = yearString.equalsIgnoreCase("XX") ? 2000 : year;

                if (day < 1 || day > getDaysWithMonthYear(dayMonth, dayYear)){
                    return false;
                }
            }
            catch(NumberFormatException e){
                return false;
            }
        }
        return true;
    }
    
    public static int getDaysWithMonthYear(int month, int year){
        switch(month){
            case 1, 3, 5, 7, 8, 10, 12 ->{
                return 31;
            }
            case 4, 6, 9, 11 ->{
                return 30;
            }
            case 2 ->{
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                    return 29;
                }
                else{
                    return 28;
                }
            }
            default ->{
                return 0;
            }
        }
    }
    
    public static boolean onlyStrings(String str)
    {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean onlyDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true; 
    }

    
    public String filterIDEditer(boolean multipleType, String filterCriteria, String firstID, String secondID) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        while (true) {
            System.out.print("Enter "+ filterCriteria +" ID (input 'All' to show all): ");
            String input = sc.nextLine().toUpperCase().trim();

            if (input.equalsIgnoreCase("all")) {
                System.out.println(filterCriteria+" ID filter successfully set as 'All'.");
                return "All";
            }

            if (input.length() >= 4 && input.length() <= 6) {
                if (input.startsWith(firstID) || (multipleType && input.startsWith(secondID))) {
                    System.out.println(filterCriteria+" ID filter successfully set as "+input+".");
                    return input;
                }
            }

            System.out.println("Invalid input option, please re-input starting with '"+firstID+ 
                               (multipleType ? ("' or '" +secondID) : "") + "', 'All', and ensure it is 5-6 characters.");
        }
    }
    
    public String filterDataEditer(String filterType, String filterCriteria){
        Scanner sc = new Scanner(System.in);
        switch (filterType){
            case "Username" -> {
                System.out.println();
                while (true){
                    System.out.print("Enter "+filterCriteria+" Username (input 'All' to show all): ");
                    String input = sc.nextLine().trim();
                    if (input.equalsIgnoreCase("all")){
                        System.out.println(filterCriteria+" Username filter successfully set as 'All'.");
                        return "All";
                    }
                    else{
                        if (!onlyStrings(input)){
                            System.out.println("Invalid input. Only alphabelts are allowed."); 
                        }
                        else if (onlyStrings(input)){
                            String newString = formatString(input);
                            System.out.println(filterCriteria+" Username filter successfully set as "+newString+".");
                            return newString;
                        }
                    }
                }
            }
            case "ItemOrSupplierName" ->{
                System.out.print("\nEnter "+filterCriteria+" Name (input 'All' to show all): ");
                String input = sc.nextLine().trim();
                if (input.equalsIgnoreCase("all")){
                    System.out.println(filterCriteria+" Name filter successfully set as 'All'.");
                    return "All";
                }
                else{
                    System.out.println(filterCriteria+" Name filter successfully set as "+input+".");
                    return input;
                }
            }
            case "ContactOrAddress" ->{
                System.out.print("\nEnter "+filterCriteria+" (input 'All' to show all): ");
                String input = sc.nextLine().trim();
                if (input.equalsIgnoreCase("all")){
                    System.out.println(""+filterCriteria+" filter successfully set as 'All'.");
                    return "All";
                }
                else{
                    System.out.println(""+filterCriteria+" filter successfully set as "+input+".");
                    return input;
                }
            }
            case "Numbers" ->{
                System.out.println();
                while (true){
                    System.out.print("Enter "+filterCriteria+" (input 'All' to show all): ");
                    String input = sc.nextLine().trim();
                    if (input.equalsIgnoreCase("all")){
                        System.out.println(filterCriteria+" filter successfully set as 'All'.");
                        return "All";
                    }
                    else{
                        try {
                            int newQuantity = Integer.parseInt(input);
                            System.out.println(filterCriteria+" filter successfully set as "+input+".");
                            return String.valueOf(newQuantity);
                        }catch(NumberFormatException e){System.out.println("Invalid input. Only whole numbers are allowed.");}   
                    }
                }
            }
            case "Double" ->{
                System.out.println();
                while(true){
                    System.out.print("Enter "+filterCriteria+" (input 'All' to show all): ");
                    String input = sc.nextLine().trim();
                    if (input.equalsIgnoreCase("all")){
                        System.out.println(filterCriteria+" filter successfully set as 'All'.");
                        return "All";
                    }
                    else{
                        try {
                            double newQuantity = Double.parseDouble(input);
                            System.out.println(filterCriteria+" filter successfully set as "+input+".");
                            return String.format("%.1f",newQuantity);
                        }catch(NumberFormatException e){System.out.println("Invalid input. Only whole numbers are allowed.");}   
                    }
                }
            }
            case "Status" ->{
                System.out.println("\nChoose Status Filter:\n1.All\n2.Pending\n3.Approved\n4.Rejected");
                while(true){
                    System.out.print("Enter your option: ");
                    String input = sc.nextLine();
                    switch(input){
                        case "1"->{
                            System.out.println("Status Filter successfully set as 'All'.");
                            return "All";
                        }
                        case "2"->{
                            System.out.println("Status Filter successfully set as 'Pending'.");
                            return "Pending";
                        }
                        case "3"->{
                            System.out.println("Status Filter successfully set as 'Approved'.");
                            return "Approved";
                        }
                        case "4"->{
                            System.out.println("Status Filter successfully set as 'Rejected'.");
                            return "Rejected";
                        }
                        default ->{
                            System.out.println("Invalid input option, please re-input numbers only 1 to 4.");
                        }
                    }
                }
            }
            case "poStatus" ->{
                System.out.println("\nChoose Status Filter:\n1.All\n2.Pending\n3.Approved\n4.Rejected\n5.Received");
                while(true){
                    System.out.print("Enter your option: ");
                    String input = sc.nextLine();
                    switch(input){
                        case "1"->{
                            System.out.println("Status Filter successfully set as 'All'.");
                            return "All";
                        }
                        case "2"->{
                            System.out.println("Status Filter successfully set as 'Pending'.");
                            return "Pending";
                        }
                        case "3"->{
                            System.out.println("Status Filter successfully set as 'Approved'.");
                            return "Approved";
                        }
                        case "4"->{
                            System.out.println("Status Filter successfully set as 'Rejected'.");
                            return "Rejected";
                        }
                        case "5"->{
                            System.out.println("Status Filter successfully set as 'Received'.");
                            return "Received";
                        }
                        default ->{
                            System.out.println("Invalid input option, please re-input numbers only 1 to 5.");
                        }
                    }
                }
            }
            case "poPaymentStatus"->{
                System.out.println("\nChoose Payment Status Filter:\n1.All\n2.None\n3.Pending\n4.Paid");
                while(true){
                    System.out.print("Enter your option: ");
                    String input = sc.nextLine();
                    switch(input){
                        case "1"->{
                            System.out.println("Status Filter successfully set as 'All'.");
                            return "All";
                        }
                        case "2"->{
                            System.out.println("Status Filter successfully set as 'None'.");
                            return "None";
                        }
                        case "3"->{
                            System.out.println("Status Filter successfully set as 'Pending'.");
                            return "Pending";
                        }
                        case "4"->{
                            System.out.println("Status Filter successfully set as 'Paid'.");
                            return "Paid";
                        }
                        default ->{
                            System.out.println("Invalid input option, please re-input numbers only 1 to 5.");
                        }
                    }
                }
            }
            case "Date"->{
                System.out.println();
                while (true){
                    System.out.print("Enter Date (input 'All' to show all, or only DD-MM-YYYY order (year between 2000 to 2024)): ");
                    String input = sc.nextLine();
                    if (!input.matches("((\\d{2}|XX)-(\\d{2}|XX)-(\\d{4}|XXXX))") && !input.equalsIgnoreCase("all")){
                        System.out.println("Invalid format. Please enter the date in DD-MM-YYYY format or 'All'.");
                    }
                    else if (input.equalsIgnoreCase("all")){
                        System.out.println("Date Filter successfully set as 'All' (XX-XX-XXXX).");
                        return "XX-XX-XXXX";
                    }
                    else{
                        boolean dateCheck;
                        if (input.length() == 10){
                            dateCheck = checkDate(input);
                            if(!dateCheck){
                               System.out.println("Date format entered worngly, pls re-enter based on DD-MM-YYYY order."); 
                            }
                            else if (dateCheck){
                               System.out.println("Date Filter successfully set as "+input+".");
                               return input;
                            }  
                        }
                        else{
                            System.out.println("Date length is wrong, must be DD-MM-YYYY order.");
                        }
                    }
                }
            }
            case "poTotalAmount" ->{
                System.out.println();
                String initialAmount = "";
                String finalAmount = "";
                
                while(true){
                    System.out.print("Enter the initial amount value: ");
                    initialAmount = sc.nextLine().trim();
                    if(onlyDigits(initialAmount)){
                        break;
                    }
                    else{
                        System.out.println("Invalid input. Only digits are allowed."); 
                    }
                }
                while(true){
                    System.out.print("Enter the final amount value: ");
                    finalAmount = sc.nextLine().trim();
                    if(onlyDigits(finalAmount)){
                        break;
                    }
                    else{
                        System.out.println("Invalid input. Only digits are allowed.");
                    }
                }
                System.out.println("Total Amount Filter successfully set as "+initialAmount+" <= x <= "+finalAmount+".");
                return initialAmount+" <= x <= "+finalAmount;
            }
        }
        return "Error";
    }
}
