package zw.co.malvern.contactservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import zw.co.malvern.contactservice.utils.exceptions.ResultsNotFoundException;
import zw.co.malvern.contactservice.utils.exceptions.SearchException;
import zw.co.malvern.spec.model.ResponseCode;

@Slf4j
@ControllerAdvice
public class SearchContactExceptionHandler {
    @ResponseBody
    @ExceptionHandler
    private ResponseEntity<ResponseCode> handleExceptions(Exception exception) {
        log.debug("Error occurred {}", exception.getLocalizedMessage());
        final ResponseCode responseCode = new ResponseCode();
        if (exception instanceof SearchException) {
            responseCode.setId(-2L);
            responseCode.setNarrative(exception.getLocalizedMessage());
            return ResponseEntity.badRequest().body(responseCode);
        }
        if (exception instanceof ResultsNotFoundException) {
            responseCode.setId(-1L);
            responseCode.setNarrative(exception.getLocalizedMessage());
            return ResponseEntity.ok().body(responseCode);
        }
        responseCode.setId(-3L);
        responseCode.setNarrative("Internal error occurred");
        return ResponseEntity.internalServerError().body(responseCode);
    }
}
