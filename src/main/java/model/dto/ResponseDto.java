package model.dto;

public class ResponseDto {

    private  String type;
    private  String source;
    private  String cause;
    private  String field;
    private  String retry;
    private  String code;
    private  String mode;

    public ResponseDto() {
    }

    public ResponseDto(String type, String source, String cause, String field, String retry, String code, String mode) {
        this.type = type;
        this.source = source;
        this.cause = cause;
        this.field = field;
        this.retry = retry;
        this.code = code;
        this.mode = mode;
    }

    public ResponseDto(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRetry() {
        return retry;
    }

    public void setRetry(String retry) {
        this.retry = retry;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
