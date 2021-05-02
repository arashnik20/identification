package exceptions;

public class GlobalException extends RuntimeException {

    private String mode;

    public GlobalException(String mode) {
        super(mode);
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
