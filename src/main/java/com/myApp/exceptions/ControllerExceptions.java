package com.myApp.exceptions;

import com.myApp.exceptions.user.UserExceptionInfo;
import com.myApp.exceptions.user.UserNotCreatedException;
import com.myApp.exceptions.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptions {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    UserExceptionInfo handleNotFound(HttpServletRequest req) {
        return new UserExceptionInfo(req.getRequestURL(),
                new UserNotFoundException("Пользователь не найден"));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserNotCreatedException.class})
    UserExceptionInfo handleBadRequest(HttpServletRequest req, UserNotCreatedException e) {
        return new UserExceptionInfo(req.getRequestURL(),
                new UserNotCreatedException("Не удалось создать пользователя:\n" + e.getMessage()));
    }

    protected void createErrorMessageFromValidation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer errorMessage = new StringBuffer();
            bindingResult.getFieldErrors().forEach(error -> errorMessage.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";"));
            throw new UserNotCreatedException(errorMessage.toString());
        }
    }
}
