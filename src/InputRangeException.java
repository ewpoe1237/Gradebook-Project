public class InputRangeException extends Exception{ //additional custom exception class to handle out-of-range variables :)
    public InputRangeException() { }

    public InputRangeException(String message) {
        super(message);
    }
}
