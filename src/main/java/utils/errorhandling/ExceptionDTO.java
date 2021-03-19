package utils.errorhandling;

public class ExceptionDTO {
    private final int code;
    private final String message;

    public ExceptionDTO(int code, String description){
        this.code = code;
        this.message = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}