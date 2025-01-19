package utils.message;

/**
 * Enum representing the types of messages that can be sent between the client and server.
 */
public enum MessageType {
    BOARD_STATE,
    MOVE,
    INFO,
    GAME_STARTED,
    YOUR_ID,
    YOUR_TURN,
    GAME_OVER
}