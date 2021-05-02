package model.dto;

public class GlobalDto {
    private  String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public GlobalDto(String mode) {
        this.mode = mode;
    }
}
