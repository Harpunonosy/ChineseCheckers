package exceptions;

/**
 * Exception thrown when a move has an invalid format.
 */
public class InvalidMoveFormatException extends Exception {
    public InvalidMoveFormatException(String message) {
        super(message);
    }
}