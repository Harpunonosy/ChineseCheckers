package utils.message;

import com.google.gson.Gson;

public class MessageUtils {
    private static final Gson gson = new Gson();

    public static String serializeMessage(Message message) {
        return gson.toJson(message);
    }

    public static Message deserializeMessage(String json) {
        return gson.fromJson(json, Message.class);
    }
}