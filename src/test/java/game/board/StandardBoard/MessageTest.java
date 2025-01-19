package game.board.StandardBoard;

import org.junit.jupiter.api.Test;

import utils.message.Message;
import utils.message.MessageType;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageCreation() {
        Message message = new Message(MessageType.INFO, "Test message");
        assertEquals(MessageType.INFO, message.getType());
        assertEquals("Test message", message.getContent());
    }

    @Test
    public void testSetType() {
        Message message = new Message(MessageType.INFO, "Test message");
        message.setType(MessageType.MOVE);
        assertEquals(MessageType.MOVE, message.getType());
    }

    @Test
    public void testSetContent() {
        Message message = new Message(MessageType.INFO, "Test message");
        message.setContent("New content");
        assertEquals("New content", message.getContent());
    }
}