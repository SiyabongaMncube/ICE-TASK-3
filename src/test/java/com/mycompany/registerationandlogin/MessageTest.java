
package com.mycompany.registerationandlogin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class MessageTest {
    
   

    private Message message;

    @BeforeEach
    void setUp() {
        // Fresh message before every test
        message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }

     // checkMessageID()
    @Test
    @DisplayName("Generated message ID should be exactly 10 characters")
    void testCheckMessageID_valid() {
        assertTrue(message.checkMessageID(),
                "Expected auto-generated message ID to be 10 digits");
    }

    @Test
    @DisplayName("Message ID should contain only digits")
    void testMessageID_onlyDigits() {
        String id = message.getMessageID();
        assertTrue(id.matches("\\d{10}"),
                "Expected message ID to contain only digits and be 10 chars long");
    }

   
    // checkRecipientCell()
   @Test
    @DisplayName("Valid recipient cell number starting with + should pass")
    void testCheckRecipientCell_valid() {
        assertTrue(message.checkRecipientCell(),
                "Expected +27718693002 to pass recipient validation");
    }

    @Test
    @DisplayName("Recipient without + prefix should fail")
    void testCheckRecipientCell_noPlus() {
        Message msg = new Message("27718693002", "Hello");
        assertFalse(msg.checkRecipientCell(),
                "Recipient without '+' should return false");
    }

    @Test
    @DisplayName("Recipient shorter than 10 characters should fail")
    void testCheckRecipientCell_tooShort() {
        Message msg = new Message("+27123", "Hello");
        assertFalse(msg.checkRecipientCell(),
                "Recipient shorter than 10 chars should return false");
    }

    @Test
    @DisplayName("Recipient longer than 13 characters should fail")
    void testCheckRecipientCell_tooLong() {
        Message msg = new Message("+271234567890123", "Hello");
        assertFalse(msg.checkRecipientCell(),
                "Recipient longer than 13 chars should return false");
    }

    @Test
    @DisplayName("Recipient of exactly 10 characters with + should pass")
    void testCheckRecipientCell_minLength() {
        Message msg = new Message("+271234567", "Hello");
        assertTrue(msg.checkRecipientCell(),
                "Recipient of exactly 10 chars starting with '+' should pass");
    }

    @Test
    @DisplayName("Recipient of exactly 13 characters with + should pass")
    void testCheckRecipientCell_maxLength() {
        Message msg = new Message("+271234567890", "Hello");
        assertTrue(msg.checkRecipientCell(),
                "Recipient of exactly 13 chars starting with '+' should pass");
    }

    
    // checkMessageLength()
     @Test
    @DisplayName("Message within 250 characters should pass")
    void testCheckMessageLength_valid() {
        assertTrue(message.checkMessageLength(),
                "Expected short message to pass length check");
    }

    @Test
    @DisplayName("Message of exactly 250 characters should pass")
    void testCheckMessageLength_exactly250() {
        String text = "A".repeat(250);
        Message msg = new Message("+27718693002", text);
        assertTrue(msg.checkMessageLength(),
                "Message of exactly 250 chars should return true");
    }

    @Test
    @DisplayName("Message of 251 characters should fail")
    void testCheckMessageLength_tooLong() {
        String text = "A".repeat(251);
        Message msg = new Message("+27718693002", text);
        assertFalse(msg.checkMessageLength(),
                "Message of 251 chars should return false");
    }

// createMessageHash()
      @Test
    @DisplayName("Hash should be in uppercase")
    void testCreateMessageHash_isUpperCase() {
        String hash = message.getMessageHash();
        assertEquals(hash.toUpperCase(), hash,
                "Expected message hash to be fully uppercase");
    }

    @Test
    @DisplayName("Hash should contain the first word of the message")
    void testCreateMessageHash_containsFirstWord() {
        // message text: "Hi Mike, can you join us for dinner tonight?"
        // first word: "HI"
        assertTrue(message.getMessageHash().contains("HI"),
                "Expected hash to contain first word 'HI'");
    }

    @Test
    @DisplayName("Hash should contain the last word of the message")
    void testCreateMessageHash_containsLastWord() {
        // last word: "TONIGHT?"
        assertTrue(message.getMessageHash().contains("TONIGHT?"),
                "Expected hash to contain last word 'TONIGHT?'");
    }

    @Test
    @DisplayName("Hash for single-word message should repeat the word")
    void testCreateMessageHash_singleWord() {
        Message msg = new Message("+27718693002", "Hello");
        String hash = msg.getMessageHash();
        // single word → firstWord + lastWord = "HELLOHELLO"
        assertTrue(hash.contains("HELLOHELLO"),
                "Expected single-word hash to repeat the word twice");
    }

    
    // sendMessage()
     @Test
    @DisplayName("sendMessage('send') should return success message")
    void testSendMessage_send() {
        assertEquals("Message successfully sent.",
                message.sendMessage("send"),
                "Expected 'Message successfully sent.' when action is send");
    }

    @Test
    @DisplayName("sendMessage('send') should mark message as sent")
    void testSendMessage_send_marksAsSent() {
        message.sendMessage("send");
        assertTrue(message.isSent(),
                "Expected isSent() to be true after send action");
    }

    @Test
    @DisplayName("sendMessage('store') should return stored message")
    void testSendMessage_store() {
        assertEquals("Message successfully stored.",
                message.sendMessage("store"),
                "Expected 'Message successfully stored.' when action is store");
    }

    @Test
    @DisplayName("sendMessage('store') should not mark message as sent")
    void testSendMessage_store_notSent() {
        message.sendMessage("store");
        assertFalse(message.isSent(),
                "Expected isSent() to be false after store action");
    }

    @Test
    @DisplayName("sendMessage('delete') should return delete prompt")
    void testSendMessage_delete() {
        assertEquals("Press 0 to delete message.",
                message.sendMessage("delete"),
                "Expected delete prompt when action is delete");
    }

    @Test
    @DisplayName("sendMessage with invalid input should return error message")
    void testSendMessage_invalidChoice() {
        assertEquals("Invalid choice. Please enter send, store, or delete.",
                message.sendMessage("xyz"),
                "Expected error message for unrecognised action");
    }

    @Test
    @DisplayName("sendMessage should be case-insensitive")
    void testSendMessage_caseInsensitive() {
        assertEquals("Message successfully sent.",
                message.sendMessage("SEND"),
                "Expected send action to work regardless of case");
    }

    
    // returnTotalMessages()
    @Test
    @DisplayName("Total messages sent increases after each send action")
    void testReturnTotalMessages_incrementsOnSend() {
        int before = Message.returnTotalMessages();
        message.sendMessage("send");
        int after = Message.returnTotalMessages();
        assertEquals(before + 1, after,
                "Expected total messages sent to increase by 1 after a send");
    }

    @Test
    @DisplayName("Total messages sent does not increase after store action")
    void testReturnTotalMessages_noIncrementOnStore() {
        int before = Message.returnTotalMessages();
        message.sendMessage("store");
        int after = Message.returnTotalMessages();
        assertEquals(before, after,
                "Expected total messages sent to stay the same after store");
    }

    @Test
    @DisplayName("Total messages sent does not increase after delete action")
    void testReturnTotalMessages_noIncrementOnDelete() {
        int before = Message.returnTotalMessages();
        message.sendMessage("delete");
        int after = Message.returnTotalMessages();
        assertEquals(before, after,
                "Expected total messages sent to stay the same after delete");
    }

    
    // printMessage()
    @Test
    @DisplayName("printMessage should contain the message ID")
    void testPrintMessage_containsID() {
        assertTrue(message.printMessage().contains(message.getMessageID()),
                "Expected printMessage output to contain the message ID");
    }

    @Test
    @DisplayName("printMessage should contain the recipient")
    void testPrintMessage_containsRecipient() {
        assertTrue(message.printMessage().contains("+27718693002"),
                "Expected printMessage output to contain the recipient");
    }

    @Test
    @DisplayName("printMessage should contain the message text")
    void testPrintMessage_containsText() {
        assertTrue(message.printMessage().contains("Hi Mike"),
                "Expected printMessage output to contain part of the message text");
    }

    @Test
    @DisplayName("printMessage shows 'Not sent' before any action")
    void testPrintMessage_notSentByDefault() {
        assertTrue(message.printMessage().contains("Not sent"),
                "Expected status to be 'Not sent' before any action");
    }

    @Test
    @DisplayName("printMessage shows 'Sent' after send action")
    void testPrintMessage_sentAfterSend() {
        message.sendMessage("send");
        assertTrue(message.printMessage().contains("Sent"),
                "Expected status to be 'Sent' after send action");
    }
}
    

