package game.board.StandardBoard;

import org.junit.jupiter.api.Test;

import utils.message.Message;
import utils.message.MessageType;
import utils.message.MessageUtils;

import static org.junit.jupiter.api.Assertions.*;

public class MessageUtilsTest {

    @Test
    public void testSerializeMessage() {
        Message message = new Message(MessageType.INFO, "Test message");
        String json = MessageUtils.serializeMessage(message);
        assertNotNull(json);
        assertTrue(json.contains("\"type\":\"INFO\""));
        assertTrue(json.contains("\"content\":\"Test message\""));
    }

    @Test
    public void testDeserializeMessage() {
        String json = "{\"type\":\"INFO\",\"content\":\"Test message\"}";
        Message message = MessageUtils.deserializeMessage(json);
        assertNotNull(message);
        assertEquals(MessageType.INFO, message.getType());
        assertEquals("Test message", message.getContent());
    }

}