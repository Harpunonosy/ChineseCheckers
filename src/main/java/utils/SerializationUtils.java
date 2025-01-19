package utils;

import game.board.BoardState;
import game.board.StandardBoard.StandardBoard;

import java.io.*;
import java.util.Base64;

/**
 * Utility class for serialization and deserialization of board states.
 */
public class SerializationUtils {

    /**
     * Serializes a board state to a Base64 encoded string.
     * 
     * @param boardState The board state to serialize.
     * @return The serialized board state as a Base64 encoded string.
     */
    public static String serializeBoardState(BoardState boardState) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(boardState);
            objectOutputStream.close();
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deserializes a board state from a Base64 encoded string.
     * 
     * @param serializedBoardState The serialized board state as a Base64 encoded string.
     * @return The deserialized board state.
     */
    public static BoardState deserializeBoardState(String serializedBoardState) {
        try {
            byte[] data = Base64.getDecoder().decode(serializedBoardState);
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return (BoardState) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Serializes a board to a Base64 encoded string.
     * 
     * @param board The board to serialize.
     * @return The serialized board as a Base64 encoded string.
     */
    public static String serializeBoard(StandardBoard board) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(board);
            objectOutputStream.close();
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deserializes a board from a Base64 encoded string.
     * 
     * @param serializedBoard The serialized board as a Base64 encoded string.
     * @return The deserialized board.
     */
    public static StandardBoard deserializeBoard(String serializedBoard) {
        try {
            byte[] data = Base64.getDecoder().decode(serializedBoard);
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return (StandardBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}