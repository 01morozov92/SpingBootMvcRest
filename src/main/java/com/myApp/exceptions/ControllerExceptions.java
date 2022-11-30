package com.myApp.exceptions;

import com.myApp.exceptions.user.UserNotFoundException;
import com.myApp.exceptions.user.UserNotFoundExceptionInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ControllerExceptions {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    UserNotFoundExceptionInfo handleBadRequest(HttpServletRequest req) {
        System.out.println();
        return new UserNotFoundExceptionInfo(req.getRequestURL(),
                new UserNotFoundException("Пользователь не найден"));
    }
}
