package exceptions;

import lombok.Data;

@Data
public class IdentificationException  extends  RuntimeException{
    private  String type;
    private  String source;
    private  String causes;
    private  String field;
    private  String retry;
    private  String code;
    private  String mode;

    public IdentificationException(String type, String source, String cause, String field, String retry, String code, String mode) {
        super(mode);
        this.type = type;
        this.source = source;
        this.causes = cause;
        this.field = field;
        this.retry = retry;
        this.code = code;
        this.mode = mode;
    }
}
