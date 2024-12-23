package itk.academy.orekhov.ordermanagementsystem.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}