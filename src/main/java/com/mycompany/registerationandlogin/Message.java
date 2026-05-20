
package com.mycompany.registerationandlogin;

import java.util.Random;
public class Message {
    //Fields 
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private boolean sent;
 
    private static int totalMessagesSent = 0;
 
    // Constructor
    public Message(String recipient, String messageText) {
        this.recipient   = recipient;
        this.messageText = messageText;
        this.messageID   = generateMessageID();
        this.messageHash = createMessageHash();
        this.sent        = false;
    }
 
    // Private helpers 
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
 
    // Validation 
    /* Message ID must be exactly 10 digits. 
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }
 
    /*
      Recipient must start with '+', be at least 10 chars (e.g. +27XXXXXXXX)
      and no more than 13 chars.
     */
    public boolean checkRecipientCell() {
        return recipient != null && recipient.startsWith("+") && recipient.length() >= 10 && recipient.length() <= 13;
    }
 
    // Message body must not exceed 250 characters. 
    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }
 
    //Hash generation 
    /*
 Example: 50:0:HIMIKE
     */
    public String createMessageHash() {
    String trimmed = messageText.trim();//I used the trimmed method  meaning removing extra spaces and tags.
    // Get first word: everything before the first space
    String firstWord;
    String lastWord;

    int firstSpace = trimmed.indexOf(" ");

    if (firstSpace == -1) {
        // Only one word in the message
        firstWord = trimmed;
        lastWord  = trimmed;
    } else {
        firstWord = trimmed.substring(0, firstSpace);

        // Get last word: everything after the last space
        int lastSpace = trimmed.lastIndexOf(" ");
        lastWord = trimmed.substring(lastSpace + 1);
    }

    String hash = messageID.substring(0, 2)+ ":"+ totalMessagesSent + ":" + firstWord + lastWord;

    return hash.toUpperCase();
}
 
    //Actions 
   //Processes the user's chosen action for the message.
    public String sendMessage(String userChoice) {
        switch (userChoice.trim().toLowerCase()) {
            case "send":
                sent = true;
                totalMessagesSent++;
                // Regenerate hash now that totalMessagesSent has incremented
                messageHash = createMessageHash();
                return "Message successfully sent.";
 
            case "store":
                sent = false;
                return "Message successfully stored.";
 
            case "delete":
                sent = false;
                return "Press 0 to delete message.";   // matches spec wording
 
            default:
                return "Invalid choice. Please enter send, store, or delete.";
        }
    }
 
    //the Display 
    public String printMessage() {
        return String.format(
            "Message ID:   "+ "Message Hash: " + "Recipient:    "+ "Message:    "+ "Status:    ", messageID, messageHash,recipient,messageText, sent ? "Sent" : "Not sent");
        //Code Attribute
        //Author:VHTC
        //The question mark (?) typically represents the conditional operator in languages like ++. It is used to evaluate a condition and return a value based on that condition. in a sensereturn b; otherwise, return c. This operator is syntactic sugar, allowing for concise code that can simplify complex logical expressions. 
//Website:https://www.vhtc.org/2025/04/top-50-coding-symbols-explained.html
    }
 
    //Static accessor 
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
 
     //
    public String getMessageID() { 
        
        return messageID;   
    
    }
    public String getRecipient()  { 
        return recipient;   
    
    }
    public String getMessageText() { 
        
        return messageText; 
    
    }
    public String getMessageHash() { return messageHash; 
    
    }
    public boolean isSent()  {
        
        return sent;       
    
    }



   
    }



