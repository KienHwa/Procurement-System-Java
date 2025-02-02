package oodjassignmentfinal;

import java.util.*;

public class Admin extends User{
    public void adminMenu(){
        System.out.println();
        
        Scanner scanner = new Scanner(System.in);
        AdminDisplay display = new AdminDisplay();
        
        while (true) {
            String input = display.homePage();
            System.out.println();
            
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                continue;
            }

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> editProfile();
                case 3 -> {
                    display.displayItem();
                    
                    System.out.println("(Enter to Return)");
                    scanner.nextLine();
                }
                case 4 -> {
                    display.displaySupplier();
                    
                    System.out.println("(Enter to Return)");
                    scanner.nextLine();
                }
                case 5 -> {
                    display.displayRequisition();
                    
                    System.out.println("(Enter to Return)");
                    scanner.nextLine();
                }
                case 6 -> {
                    display.displayOrder();
                    
                    System.out.println("(Enter to Return)");
                    scanner.nextLine();
                }
                case 7 -> {
                    System.out.println("Exiting program...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void registerUser() {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        String role = selectRole();  
        String userID = generateNextIDForRole(role);

        System.out.println("Generated User ID: " + userID);

        System.out.print("Enter Username: ");
        String newUsername = scanner.nextLine().trim();

        System.out.print("Enter Email: ");
        String newEmail = scanner.nextLine().trim();    

        System.out.print("Enter Password: ");
        String newPassword = scanner.nextLine().trim();

        System.out.print("Enter Real Name: ");
        String realName = scanner.nextLine().trim();

        String[] userData = new String[]{userID, newUsername, newEmail, newPassword, role, realName, "null"};

        if (confirmRegistration(newUsername, newEmail, newPassword, role, realName)) {
            user.createFile(userData);
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration canceled.. Returning to main menu");
        }
    }
    
    private String selectRole() {
        Scanner scanner = new Scanner(System.in);
        AdminDisplay display = new AdminDisplay();
        String role = "";
        while (role.isEmpty()) {
            display.registerUserSelectRole();
            System.out.print("Enter role number: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> role = "salesManager";
                case "2" -> role = "purchaseManager";
                case "3" -> role = "inventoryManager";
                case "4" -> role = "financeManager";
                case "5" -> role = "admin";
                default -> System.out.println("Invalid choice. Please select a valid role.(1-5)");
            }
        }
        return role;
    }
    
    private String generateNextIDForRole(String role) {
        String prefix;
        switch (role) {
            case "salesManager" -> prefix = "SM";
            case "purchaseManager" -> prefix = "PM";
            case "inventoryManager" -> prefix = "IM";
            case "financeManager" -> prefix = "FM";
            case "admin" -> prefix = "AD";
            default -> throw new IllegalArgumentException("Invalid role");
        }
        return generateNextID(prefix);
    }
    
    private String generateNextID(String prefix) {
        User user = new User();
        ArrayList<String[]> userList = user.readFile();
        List<String> records = new ArrayList<>();
        for(String[] userInfo : userList){
            records.add(userInfo[0]);
        }
        int count = (int) records.stream().filter(record -> record.startsWith(prefix)).count() + 1;
        return prefix + String.format("%03d", count);
    }
    
    private boolean confirmRegistration(String username, String email, String password, String role, String realName) {
        Scanner scanner = new Scanner(System.in);
        AdminDisplay display = new AdminDisplay();
        while (true) {
            display.registerUserSelectionRole(username, email, password, role, realName);
            System.out.print("Select an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    return true;
                }
                case "2" -> {
                    return false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void editProfile() {
        Scanner sc = new Scanner(System.in);
        AdminDisplay display = new AdminDisplay();
        User user = new User();
        
        ArrayList<String[]> userList = user.readFile();
        System.out.print("Enter your Email: ");
        String email = sc.nextLine().trim();
        for(String[] userInfo : userList){
            if(userInfo[2].equals(email)){
                System.out.print("Enter your Password: ");
                String password = sc.nextLine().trim();
                if(!password.equals(userInfo[3])){
                    System.out.print("Incorrect password. Forgot Password? (y/n): ");
                    String forgot = sc.nextLine().trim();
                    if (forgot.equalsIgnoreCase("y")) {
                        // Generate OTP
                        String otp = generateOTP();
                        userInfo[6] = otp;  // Save OTP to the user's record
                        user.updateFile(userInfo, userInfo[0]);

                        System.out.println("Three-digit OTP has been sent to your email.");
                        System.out.print("Enter OTP (or type 'exit' to quit): ");
                        String enteredOtp = sc.nextLine().trim();

                        if (enteredOtp.equalsIgnoreCase("exit")) {
                            user.resetOTP(userInfo);
                            return;
                        } else if (enteredOtp.equals(otp)) {
                            System.out.println("OTP verified. You can now edit your profile.");
                            user.resetOTP(userInfo);
                        } else {
                            System.out.println("Incorrect OTP. Returning to menu.");
                            user.resetOTP(userInfo);
                            return;
                        }
                    } else {
                        return;
                    }
                }
                display.editProfileCurrentDetail(userInfo);
                
                System.out.print("Select the detail you want to edit: ");
                String choice = sc.nextLine().trim();

                if (choice.equals("4")) {
                    System.out.println("No changes made.");
                    return;
                }

                String[] newUserInfo = editField(choice, userInfo);

                user.updateFile(newUserInfo, newUserInfo[0]);
                System.out.println("Profile updated successfully.");
                return;
            }
        }
        System.out.println("Profile not found for the given email.");
    }
    
    private String[] editField(String choice, String[] userData) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter new Username: ");
                    userData[1] = scanner.nextLine().trim();
                    return userData;
                }
                case "2" -> {
                    System.out.print("Enter new Password: ");
                    userData[3] = scanner.nextLine().trim();
                    return userData;
                }
                case "3" -> {
                    System.out.print("Enter new Real Name: ");
                    userData[5] = scanner.nextLine().trim();
                    return userData;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}