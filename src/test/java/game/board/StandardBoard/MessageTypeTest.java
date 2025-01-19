package game.board.StandardBoard;

import org.junit.jupiter.api.Test;

import utils.message.MessageType;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTypeTest {

    @Test
    public void testMessageTypeValues() {
        MessageType[] types = MessageType.values();
        assertEquals(7, types.length);
    }

    @Test
    public void testMessageTypeValueOf() {
        MessageType type = MessageType.valueOf("INFO");
        assertEquals(MessageType.INFO, type);
    }

    @Test
    public void testMessageTypeToString() {
        assertEquals("INFO", MessageType.INFO.toString());
    }

    @Test
    public void testMessageTypeOrdinal() {
        assertEquals(2, MessageType.INFO.ordinal());
    }
}