package utils.message;

/**
 * Represents a message sent between the client and server.
 */
public class Message {
    private MessageType type;
    private String content;

    /**
     * Initializes a new Message.
     * 
     * @param type The type of the message.
     * @param content The content of the message.
     */
    public Message(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }

    /**
     * Gets the type of the message.
     * 
     * @return The type of the message.
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Gets the content of the message.
     * 
     * @return The content of the message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the type of the message.
     * 
     * @param type The type of the message.
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * Sets the content of the message.
     * 
     * @param content The content of the message.
     */
    public void setContent(String content) {
        this.content = content;
    }
}