package exception;

/**
 * @author Jorge Báez
 */
public class EmptyCommandException extends RuntimeException{
    public EmptyCommandException(String message) {
        super(message);
    }
}
