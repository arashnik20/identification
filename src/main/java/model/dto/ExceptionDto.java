package model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDto {
    private String desc;
    private String exceptionMessage;
}
