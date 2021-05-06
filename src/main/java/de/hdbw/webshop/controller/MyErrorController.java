package de.hdbw.webshop.controller;

import de.hdbw.webshop.exception.ErrorPayload;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@CommonsLog
public class MyErrorController implements ErrorController {


    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.valueOf(status.toString());

        ErrorPayload errorPayload = new ErrorPayload(
                (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE),
                statusCode,
                (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI));
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            log.info("Not Found : 404");
            return new ModelAndView("error/notFound_404", "error", errorPayload);
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            log.warn("Internal Server Error : 500 (" + errorPayload.getMessage() + ")");
            return new ModelAndView("error/internalServerError_500", "error", errorPayload);
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            log.info("Forbidden : 403");
            return new ModelAndView("error/Forbidden_403", "error", errorPayload);
        }
        log.warn("Unknown");
        return new ModelAndView("error/defaultError", "error", errorPayload);
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
