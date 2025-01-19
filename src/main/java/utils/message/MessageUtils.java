package utils.message;

import com.google.gson.Gson;

/**
 * Utility class for serializing and deserializing messages.
 */
public class MessageUtils {
    private static final Gson gson = new Gson();

    /**
     * Serializes a message to a JSON string.
     * 
     * @param message The message to serialize.
     * @return The serialized message as a JSON string.
     */
    public static String serializeMessage(Message message) {
        return gson.toJson(message);
    }

    /**
     * Deserializes a message from a JSON string.
     * 
     * @param json The JSON string representing the message.
     * @return The deserialized message.
     */
    public static Message deserializeMessage(String json) {
        return gson.fromJson(json, Message.class);
    }
}