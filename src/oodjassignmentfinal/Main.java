package oodjassignmentfinal;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        User userMethod = new User();
        
        while (true) {
            boolean otpExist = false;
            ArrayList<String[]> userList = userMethod.readFile();
            
            System.out.println("\n============ \nLogin Page:\n============");
            System.out.print("Enter your Username/Email (or type 'exit' to quit): ");
            String usernameEmail = scanner.nextLine().trim();
            if (usernameEmail.equalsIgnoreCase("exit")){
                break;
            }

            boolean userFound = false;
            String[] userData = null;
            
            for(String[] user : userList){
                if(user[2].equals(usernameEmail) || user[1].equals(usernameEmail)){
                    userFound = true;
                    userData = user;
                    if(userData[6] != null){
                        otpExist = false;
                    }
                    break;
                }
            }

            if (!userFound) {
                System.out.println("User email not found. Try again.");
                continue;
            }

            boolean loginSuccess = false;
            while(true) {
                System.out.print("Enter your Password: ");
                String password = scanner.nextLine().trim();
                if (password.equals(userData[3]) || (otpExist && password.equals(userData[6]))) {
                    
                    if(otpExist){
                        userMethod.resetOTP(userData);
                    }
                    
                    String role = userData[4];
                    System.out.println("Welcome " + userData[1] + ", Role: " + role);

                    switch (role) {
                        case "admin" -> {
                            Admin admin = new Admin();
                            admin.adminMenu();
                            loginSuccess = true;
                            break;
                        }
                        case "purchaseManager" -> {
                            PurchaseManager pm = new PurchaseManager(userData[0], userData[5]);
                            pm.pmMenu();
                            loginSuccess = true;
                            break;
                        }
                        case "salesManager" -> {
                            SalesManager sm = new SalesManager(userData[0]);
                            sm.smMenu();
                            loginSuccess = true;
                            break;
                        }
                        case "inventoryManager" -> {
                            InventoryManager im = new InventoryManager();
                            im.imMenu();
                            loginSuccess = true;
                            break;
                        }
                        case "financeManager" -> {
                            FinanceManager fm = new FinanceManager(userData[0]);
                            fm.fmMenu();
                            loginSuccess = true;
                            break;
                        }
                        default ->{
                            System.out.println("Role not recognized. Contact admin.");
                            loginSuccess = true;
                        }
                    }
                    if(loginSuccess){
                        break;
                    }
                } else {
                    System.out.print("Incorrect password. Have you forgotten your password? (y/n): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        otpExist = true;
                        String otp = userMethod.generateOTP();
                        userData[6] = otp; // Set OTP in the file data
                        
                        userMethod.updateFile(userData,userData[0]);

                        System.out.println("Three-digit OTP has been sent to your email.");
                    } else {
                        System.out.println("Try entering the password again.");
                    }
                }
            }
        }
    }
}
