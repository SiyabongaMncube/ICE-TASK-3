

package com.mycompany.registerationandlogin;
import java.util.Scanner;

public class RegisterationAndLogin {

    public static void main(String[] args) {
       Scanner inp = new Scanner(System.in);
       RegisterAndLoginClass user = new RegisterAndLoginClass();
       
        System.out.print("Enter first name: ");
        String firstName = inp.nextLine();

        System.out.print("Enter last name: ");
        String lastName = inp.nextLine();

        System.out.print("Enter username: ");
        String userName = inp.nextLine();
        System.out.println(user.getUserNameMessage(userName));

        System.out.print("Enter password: ");
        String password = inp.nextLine();
        System.out.println(user.getPasswordMessage(password));

        System.out.print("Enter South African cell number (example: +27838968976): ");
        String cellPhoneNumber = inp.nextLine();
        
        String registrationMessage = user.registerUser(userName, password, cellPhoneNumber);
System.out.println(registrationMessage);
        if (!"User has been registered successfully.".equals(registrationMessage)) {
            
            return;
        }

        System.out.println();
        System.out.println("Login Details");//Login feature

        System.out.print("Enter username: ");
        String enteredUserName = inp.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = inp.nextLine();

        boolean loginSuccessful = user.loginUser(enteredUserName, enteredPassword);
        System.out.println(user.returnLoginStatus(loginSuccessful));

  

        
       }
        
    }

