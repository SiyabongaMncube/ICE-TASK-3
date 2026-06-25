

package com.mycompany.registerationandlogin;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class RegisterationAndLogin {

    public static void main(String[] args) {
       
Scanner sc = new Scanner(System.in);
        RegisterAndLoginClass user = new RegisterAndLoginClass();
        List <Message> messageList  = new ArrayList();

        

        //REGISTRATION 
        System.out.println("Registeration");

        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();

        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        System.out.print("Enter username (must contain '_', max 5 chars): ");
        String userName = sc.nextLine();
        System.out.println(user.getUserNameMessage(userName));

        System.out.print("Enter password (>=8 chars, 1 uppercase, 1 digit, 1 special): ");
        String password = sc.nextLine();
        System.out.println(user.getPasswordMessage(password));

        System.out.print("Enter South African cell number (e.g. +27838968976): ");
        String cellPhoneNumber = sc.nextLine();

        String registrationResult = user.registerUser(userName, password, cellPhoneNumber);
        System.out.println(registrationResult);

        if (!"User has been registered successfully.".equals(registrationResult)) {
            System.out.println("Registration unsuccessful. Please relaunch and try again.");
            sc.close();
            return;
        }

        System.out.println();

        //STEP 2: LOGIN 
        System.out.println("Login Details");

        System.out.print("Enter username: ");
        String enteredUserName = sc.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = sc.nextLine();

        boolean loginSuccessful = user.loginUser(enteredUserName, enteredPassword);
        System.out.println(user.returnLoginStatus(loginSuccessful));

        if (!loginSuccessful) {
            System.out.println("Login failed. Please relaunch and try again.");
            sc.close();
            return;
        }

        System.out.println();

        //STEP 3: HOW MANY MESSAGES 
        System.out.print("How many messages would you like to send this session? ");
        int totalMessages = sc.nextInt();
        sc.nextLine(); // consume newline

        //STEP 4: MESSAGING MENU
        boolean quit = false;

        while (!quit) {
            System.out.println("\nQuickchat Menu");
            System.out.println("1) Send messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter option: ");

            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {

                //Option 1: Compose & send messages 
                case 1:
                    int i = 0;
                    while (i < totalMessages) {
                        System.out.printf("Messaged of %d%n", i + 1, totalMessages);
                        System.out.println("-".repeat(30));

                        System.out.print("Enter recipient cell number (e.g. +27XXXXXXXXX): ");
                        String cell = sc.nextLine().trim();

                        System.out.print("Enter message text (max 250 chars): ");
                        String text = sc.nextLine();

                        Message msg = new Message(cell, text);

                        if (!msg.checkRecipientCell()) {
                            System.out.println("Invalid cell number. Must start with '+' "+ "and be 10-13 characters. Please try again.");
                        } else if (!msg.checkMessageLength()) {
                            System.out.println("Message exceeds 250 characters ("+ text.length() + " chars). Please shorten it.");
                        } else {
                            System.out.print("Action - send / store / delete: ");
                            String action = sc.nextLine().trim();
                            System.out.println(msg.sendMessage(action));
                            System.out.println();
                            System.out.println(msg.printMessage());
                            messageList.add(msg);
                            i++;
                        }
                    }
                    System.out.println("Total messages sent this session: "+ Message.returnTotalMessages());
                    break;

                //Option 2: Show sent messages
                case 2:
                    if (messageList.isEmpty()) {
                        System.out.println("No messages have been created yet.");
                    } else {
                        System.out.println("\nSent Message");
                        int sentCount = 0;
                        for (Message m : messageList) {
                            if (m.isSent()) {
                                sentCount++;
                                System.out.println(m.printMessage());
                                System.out.println("-".repeat(40));
                            }
                        }
                        if (sentCount == 0) {
                            System.out.println("No messages have been sent (stored or deleted only).");
                        }
                    }
                    break;

                //Option 3: Quit 
                case 3:
                    quit = true;
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }

       
        System.out.println("\nGoodbye, "  + "! Have a great day.");
        
        
    }

    // Stored Messages Sub-Menu
 
    /*
      Sub-menu for all stored-message operations (Part 3, requirement 2a–f).
      All operations use the parallel arrays declared in Message.java.
     
     sc shared Scanner
      senderName full name of the logged-in user (used as sender display)
     */
    private static void storedMessagesMenu(Scanner sc, String senderName) {
        boolean back = false;
 
        while (!back) {
            System.out.println("\nStored Messages Menu");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message by ID");
            System.out.println("d) Search messages by recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Display full stored messages report");
            System.out.println("x) Back to main menu");
            System.out.print("Enter option: ");
 
            String choice = sc.nextLine().trim().toLowerCase();
 
            switch (choice) {
 
                case "a":
                    // (a) Display senders and recipients – traverses parallel arrays
                    System.out.println(
                        Message.displayAllStoredSendersAndRecipients(senderName));
                    break;
 
                case "b":
                    // (b) Longest message – searches array by comparing lengths
                    System.out.println(Message.displayLongestStoredMessage());
                    break;
 
                case "c":
                    // (c) Search by message ID – array search (Chapter 8, slides 19–20)
                    System.out.print("Enter Message ID to search: ");
                    String searchID = sc.nextLine().trim();
                    System.out.println(Message.searchByMessageID(searchID));
                    break;
 
                case "d":
                    // (d) Search by recipient – parallel array search (Chapter 8, slides 21–23)
                    System.out.print("Enter recipient cell number to search: ");
                    String searchRecip = sc.nextLine().trim();
                    System.out.println(Message.searchByRecipient(searchRecip));
                    break;
 
                case "e":
                    // (e) Delete by hash – searches array, shifts elements left
                    System.out.print("Enter Message Hash to delete: ");
                    String hash = sc.nextLine().trim();
                    System.out.println(Message.deleteByMessageHash(hash));
                    break;
 
                case "f":
                    // (f) Full report – traverses all four parallel arrays
                    System.out.println(Message.displayStoredMessagesReport());
                    break;
 
                case "x":
                    back = true;
                    break;
 
                default:
                    System.out.println("Invalid option. Please enter a, b, c, d, e, f, or x.");
            }
        }
    }
}
   
    

       
        
    

