package exceptions;

import common.PropertiesHandler;
import model.dto.ExceptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private PropertiesHandler properties ;

    @ExceptionHandler(IdentificationException.class)
    public final ResponseEntity<Object> handleAllExceptions(IdentificationException ex) {
        ExceptionDto dto = ExceptionDto.builder().desc(properties.getProperty(ex.getCode())).exceptionMessage(ex.getMessage()).build();
        return new ResponseEntity(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GlobalException.class)
    public final ResponseEntity<Object> handleCatchExceptions(GlobalException ex) {
        ExceptionDto dto = ExceptionDto.builder().desc(properties.getProperty(ex.getMode())).exceptionMessage(ex.getMessage()).build();
        return new ResponseEntity(dto, HttpStatus.PRECONDITION_FAILED);
    }
}
