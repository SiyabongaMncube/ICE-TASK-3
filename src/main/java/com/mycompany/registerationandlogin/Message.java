
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
    
    
    //  Parallel Arrays 
    // Maximum capacity for each array
    private static final int MAX_MESSAGES = 100;
 
    // Array 1: Sent messages (message text only)
    private static String[] sentMessages     = new String[MAX_MESSAGES];
    private static int      sentCount        = 0;
 
    // Array 2: Disregarded messages (message text only)
    private static String[] disregardedMessages = new String[MAX_MESSAGES];
    private static int      disregardedCount    = 0;
 
    // Array 3: Stored messages (loaded from JSON)
    private static String[] storedMessages   = new String[MAX_MESSAGES];
    private static String[] storedRecipients = new String[MAX_MESSAGES];
    private static String[] storedHashes     = new String[MAX_MESSAGES];
    private static String[] storedIDs        = new String[MAX_MESSAGES];
    private static int      storedCount      = 0;
 
    // Array 4: All message hashes (from every message created this session)
    private static String[] messageHashArray = new String[MAX_MESSAGES];
    private static int      hashCount        = 0;
 
    // Array 5: All message IDs (from every message created this session)
    private static String[] messageIDArray   = new String[MAX_MESSAGES];
    private static int      idCount          = 0;
 
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
                // Populate all four parallel stored arrays at the same index
                // Parallel arrays: Chapter 8, slides 21–23
                storedMessages[storedCount]   = messageText;
                storedRecipients[storedCount] = recipient;
                storedHashes[storedCount]     = messageHash;
                storedIDs[storedCount]        = messageID;
                storedCount++;
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
     // JSON Loading
    // Reads message.json line by line using Scanner.
    // 'throws Exception' is used so no try/catch block is needed here.
    // The exception is passed up to the caller (main method) to handle.
    // Code attribution - Scanner reading from File:
    // Oracle Java SE Docs:
    // https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
    
  public static String loadStoredMessagesFromJSON(String filePath) throws Exception {
        java.util.Scanner fileScanner = new java.util.Scanner(new java.io.File(filePath));
 
        String currentID    = null;
        String currentHash  = null;
        String currentRecip = null;
        String currentText  = null;
 
        // Read the file line by line (Chapter 8  working with arrays of data)
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
 
            if (line.contains("\"messageID\"")) {
                currentID = extractValue(line);
            } else if (line.contains("\"messageHash\"")) {
                currentHash = extractValue(line);
            } else if (line.contains("\"recipient\"")) {
                currentRecip = extractValue(line);
            } else if (line.contains("\"messageText\"")) {
                currentText = extractValue(line);
            }
 
            // Once all four fields are found, store in parallel arrays
            if (currentID != null && currentHash != null
                    && currentRecip != null && currentText != null
                    && storedCount < MAX_MESSAGES) {
 
                storedMessages[storedCount]   = currentText;
                storedRecipients[storedCount] = currentRecip;
                storedHashes[storedCount]     = currentHash;
                storedIDs[storedCount]        = currentID;
                storedCount++;
 
                // Reset for next message object in the file
                currentID = null;
                currentHash = null;
                currentRecip = null;
                currentText = null;
            }
        }
        
        return "Stored messages loaded from JSON. (" + storedCount + " messages loaded)";
    }
 
    // Helper: extracts the string value from a JSON line like: "key" : "value",
    private static String extractValue(String line) {
        int colonIndex = line.indexOf(":");
        int firstQuote = line.indexOf("\"", colonIndex);
        int secondQuote = line.indexOf("\"", firstQuote + 1);
        if (firstQuote == -1 || secondQuote == -1) {
            return null;
        }
        return line.substring(firstQuote + 1, secondQuote);
    }
    //Static accessor 
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
 
     
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
    //  Stored Messages Menu Methods (Part 3 requirements a-f) 
    // ALL methods below are OUTSIDE isSent() and INSIDE the class only
 
    // (a) Displays sender and recipient of every stored message.
    // Uses a for loop to traverse the parallel arrays (Chapter 8, slide 10).
    public static String displayAllStoredSendersAndRecipients(String senderName) {
        if (storedCount == 0) {
            return "No stored messages found.";
        }
        String result = "Stored Messages - Sender & Recipient\n";
        for (int i = 0; i < storedCount; i++) {
            result = result + (i + 1) + ". Sender: " + senderName
                   + "  |  Recipient: " + storedRecipients[i] + "\n";
        }
        return result;
    }
 
    // (b) Finds and displays the longest stored message.
    // Searches the array by comparing element lengths (Chapter 8, slides 19-20).
    public static String displayLongestStoredMessage() {
        if (storedCount == 0) {
            return "No stored messages found.";
        }
        String longest = storedMessages[0];
        for (int i = 1; i < storedCount; i++) {
            if (storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
        return "Longest stored message:\n\"" + longest + "\"";
    }
 
    // (c) Searches the storedIDs parallel array for a given ID.
    // Returns the matching recipient and message text.
    // Array search pattern: Chapter 8, slides 19-23.
    public static String searchByMessageID(String searchID) {
        for (int i = 0; i < storedCount; i++) {
            if (storedIDs[i] != null && storedIDs[i].equalsIgnoreCase(searchID)) {
                return "Found:\nRecipient: " + storedRecipients[i]
                     + "\nMessage:   " + storedMessages[i];
            }
        }
        return "No stored message found with ID: " + searchID;
    }
 
    // (d) Searches storedRecipients parallel array for a given cell number.
    // Returns all messages stored for that recipient.
    // Parallel array search: Chapter 8, slides 21-23.
    public static String searchByRecipient(String searchRecipient) {
        String result = "";
        int found = 0;
        for (int i = 0; i < storedCount; i++) {
            if (storedRecipients[i] != null
                    && storedRecipients[i].equalsIgnoreCase(searchRecipient)) {
                found++;
                result = result + found + ". \"" + storedMessages[i] + "\"\n";
            }
        }
        if (found == 0) {
            return "No messages found for recipient: " + searchRecipient;
        }
        return "Messages for " + searchRecipient + ":\n" + result;
    }
 
    // (e) Deletes a stored message by searching the storedHashes array.
    // Shifts remaining elements left after deletion.
    // Array manipulation with index: Chapter 8, slide 10-11.
    public static String deleteByMessageHash(String hash) {
        for (int i = 0; i < storedCount; i++) {
            if (storedHashes[i] != null
                    && storedHashes[i].equalsIgnoreCase(hash)) {
 
                String deletedText = storedMessages[i];
 
                // Shift all elements left to close the gap
                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j]   = storedMessages[j + 1];
                    storedRecipients[j] = storedRecipients[j + 1];
                    storedHashes[j]     = storedHashes[j + 1];
                    storedIDs[j]        = storedIDs[j + 1];
                }
                // Null out the last slot and decrement the counter
                storedMessages[storedCount - 1]   = null;
                storedRecipients[storedCount - 1] = null;
                storedHashes[storedCount - 1]     = null;
                storedIDs[storedCount - 1]        = null;
                storedCount--;
 
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "No stored message found with hash: " + hash;
    }
 
    // (f) Displays a full report of all stored messages.
    // Uses a for loop to traverse all four parallel arrays (Chapter 8, slides 21-23).
    public static String displayStoredMessagesReport() {
        if (storedCount == 0) {
            return "No stored messages to report.";
        }
        String result = "Stored Messages Report\n";
        for (int i = 0; i < storedCount; i++) {
            result = result + "Message " + (i + 1) + ":\n"+ "  Hash:      " + storedHashes[i]     + "\n" + "  Recipient: " + storedRecipients[i] + "\n" + "  Message:   " + storedMessages[i]   + "\n" + "  ID:        " + storedIDs[i]        + "\n" + "\n";
        }
        return result;
    }
    
    }



   
    



