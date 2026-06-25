
package com.mycompany.registerationandlogin;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
  MessageTest - Unit tests for Part 3 (Chapter 8: Arrays).
 
  Test data matches the assignment specification exactly (Image 2).
  Each test corresponds to a row in the unit test rubric (Image 3).
 */
public class MessageTest {

    // ── Test Data from assignment spec

    // Message 1 - action: send
    private static final String RECIP_1 = "+27834557896";
    private static final String TEXT_1  = "Did you get the cake?";

    // Message 2 - action: store
    private static final String RECIP_2 = "+27838884567";
    private static final String TEXT_2  = "Where are you? You are late! I have asked you to be on time.";

    // Message 3 - action: disregard
    private static final String RECIP_3 = "+27834484567";
    private static final String TEXT_3  = "Yohoooo, I am at your gate.";

    // Message 4 - action: send
    private static final String RECIP_4 = "+27838884567";
    private static final String TEXT_4  = "It is dinner time!";

    // Message 5 - action: store
    private static final String RECIP_5 = "+27838884567";
    private static final String TEXT_5  = "Ok, I am leaving without you.";

    // ── Runs before EVERY test to reset arrays and create fresh test data ─────
    @BeforeEach
    public void setUp() throws Exception {
        resetStaticArrays();

        // Message 1 - Send
        Message m1 = new Message(RECIP_1, TEXT_1);
        m1.sendMessage("send");

        // Message 2 - Store
        Message m2 = new Message(RECIP_2, TEXT_2);
        m2.sendMessage("store");

        // Message 3 - Disregard
        Message m3 = new Message(RECIP_3, TEXT_3);
        m3.sendMessage("disregard");

        // Message 4 - Send
        Message m4 = new Message(RECIP_4, TEXT_4);
        m4.sendMessage("send");

        // Message 5 - Store
        Message m5 = new Message(RECIP_5, TEXT_5);
        m5.sendMessage("store");
    }

    // ── Resets all static fields so each test starts clean ────────────────────
    private void resetStaticArrays() throws Exception {
        setInt("totalMessagesSent", 0);
        setInt("sentCount",         0);
        setInt("disregardedCount",  0);
        setInt("storedCount",       0);
        setInt("hashCount",         0);
        setInt("idCount",           0);

        setArray("sentMessages",        new String[100]);
        setArray("disregardedMessages", new String[100]);
        setArray("storedMessages",      new String[100]);
        setArray("storedRecipients",    new String[100]);
        setArray("storedHashes",        new String[100]);
        setArray("storedIDs",           new String[100]);
        setArray("messageHashArray",    new String[100]);
        setArray("messageIDArray",      new String[100]);
    }

    // Helper: sets a private static int field by name using reflection
    private void setInt(String fieldName, int value) throws Exception {
        java.lang.reflect.Field f = Message.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(null, value);
    }

    // Helper: sets a private static String[] field by name using reflection
    private void setArray(String fieldName, String[] value) throws Exception {
        java.lang.reflect.Field f = Message.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(null, value);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 1: Sent Messages array correctly populated
    // Test data: messages 1 and 4 (both flagged "send")
    // Expected: sentMessages[0] = TEXT_1, sentMessages[1] = TEXT_4
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        assertEquals(2, Message.displayLongestStoredMessage(),
            "sentMessages array should have exactly 2 entries.");

        assertEquals(TEXT_1, Message.displayLongestStoredMessage(),
            "First sent message should be: " + TEXT_1);

        assertEquals(TEXT_4, Message.displayLongestStoredMessage(),
            "Second sent message should be: " + TEXT_4);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 2: Display the longest message
    // Test data: messages 1-4
    // Expected: TEXT_2 is the longest stored message
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testDisplayLongestStoredMessage() {
        String result = Message.displayLongestStoredMessage();

        assertTrue(result.contains(TEXT_2),
            "The longest stored message should be: " + TEXT_2);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 3: Search for a message by messageID
    // Test data: message 2 (first stored message, so storedIDs[0])
    // Expected: result contains TEXT_2 and RECIP_2
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testSearchByMessageID() {
        // Get the ID of the first stored message (Message 2)
        String id = Message.searchByMessageID(RECIP_1);
        String result = Message.searchByMessageID(id);

        assertTrue(result.contains(TEXT_2),
            "Search by ID should return message: " + TEXT_2);

        assertTrue(result.contains(RECIP_2),
            "Search by ID should return recipient: " + RECIP_2);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 4: Search all messages sent or stored for a particular recipient
    // Test data: +27838884567 (recipient of messages 2 and 5)
    // Expected: result contains TEXT_2 and TEXT_5
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testSearchByRecipient() {
        String result = Message.searchByRecipient("+27838884567");

        assertTrue(result.contains(TEXT_2),
            "Result should contain message 2: " + TEXT_2);

        assertTrue(result.contains(TEXT_5),
            "Result should contain message 5: " + TEXT_5);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 5: Delete a message using a message hash
    // Test data: Test Message 2 (first stored message, storedHashes[0])
    // Expected: "successfully deleted" in result, storedCount drops from 2 to 1
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testDeleteByMessageHash() {
        // Get the hash of Message 2 (first stored message)
        String hashToDelete = Message.deleteByMessageHash(TEXT_1);
        String result = Message.deleteByMessageHash(hashToDelete);

        assertTrue(result.contains("successfully deleted"),
            "Result should confirm deletion. Got: " + result);

        assertEquals(1, Message.deleteByMessageHash(result),
            "After deletion, storedCount should be 1.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 6: Display report shows all stored messages with full details
    // Expected: report contains TEXT_2, TEXT_5, RECIP_2
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testDisplayStoredMessagesReport() {
        String report = Message.displayStoredMessagesReport();

        assertTrue(report.contains(TEXT_2),
            "Report should contain: " + TEXT_2);

        assertTrue(report.contains(TEXT_5),
            "Report should contain: " + TEXT_5);

        assertTrue(report.contains(RECIP_2),
            "Report should contain recipient: " + RECIP_2);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 7: Disregarded Messages array correctly populated
    // Test data: Message 3 (flagged "disregard")
    // Expected: disregardedMessages[0] = TEXT_3
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testDisregardedMessagesArrayCorrectlyPopulated() {
        assertEquals(1, Message.displayStoredMessagesReport(),
            "disregardedMessages array should have exactly 1 entry.");

        assertEquals(TEXT_3, Message.displayStoredMessagesReport(),
            "Disregarded message should be: " + TEXT_3);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 8: returnTotalMessages() returns correct count
    // Messages 1 and 4 were sent, so total = 2
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testReturnTotalMessages() {
        assertEquals(2, Message.returnTotalMessages(),
            "Total sent messages should be 2.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 9: checkMessageID() returns true for a valid 10-digit ID
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testCheckMessageID() {
        Message msg = new Message(RECIP_1, TEXT_1);
        assertTrue(msg.checkMessageID(),
            "Message ID should be exactly 10 digits.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 10: checkRecipientCell() returns true for valid +27 number
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testCheckRecipientCellValid() {
        Message msg = new Message("+27838884567", TEXT_1);
        assertTrue(msg.checkRecipientCell(),
            "Cell number +27838884567 should be valid.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 11: checkRecipientCell() returns false for invalid number
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testCheckRecipientCellInvalid() {
        Message msg = new Message("0838884567", TEXT_1);
        assertFalse(msg.checkRecipientCell(),
            "Cell number without '+' should be invalid.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 12: checkMessageLength() returns false when text exceeds 250 chars
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testCheckMessageLengthTooLong() {
        String longText = "A".repeat(251);
        Message msg = new Message(RECIP_1, longText);
        assertFalse(msg.checkMessageLength(),
            "Message over 250 characters should fail length check.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 13: sendMessage() returns correct string for each action
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testSendMessageActions() {
        Message m1 = new Message(RECIP_1, TEXT_1);
        assertEquals("Message successfully sent.",   m1.sendMessage("send"));

        Message m2 = new Message(RECIP_2, TEXT_2);
        assertEquals("Message successfully stored.", m2.sendMessage("store"));

        Message m3 = new Message(RECIP_3, TEXT_3);
        assertEquals("Press 0 to delete message.",  m3.sendMessage("disregard"));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 14: Stored Messages array correctly populated
    // Messages 2 and 5 were stored
    // Expected: storedMessages[0] = TEXT_2, storedMessages[1] = TEXT_5
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    public void testStoredMessagesArrayCorrectlyPopulated() {
        assertEquals(2, Message.displayAllStoredSendersAndRecipients(RECIP_1),
            "storedMessages array should have exactly 2 entries.");

        assertEquals(TEXT_2, Message.displayStoredMessagesReport(),
            "First stored message should be: " + TEXT_2);

        assertEquals(TEXT_5, Message.displayStoredMessagesReport(),
            "Second stored message should be: " + TEXT_5);
    }
}
    

