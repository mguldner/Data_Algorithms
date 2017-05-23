package machinelearning.general.exception;

public class UnallowedValueException extends Exception{

    /**
     * Exception to report when the value is not allowed.
     */
    private static final long serialVersionUID = 1L;
    
    public UnallowedValueException(String message){
        super(message);
    }

}
