package de.hdbw.webshop.controller.general.error;

import de.hdbw.webshop.exception.ErrorPayload;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.web.servlet.error.ErrorController;
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
        int statusCode = Integer.parseInt(status.toString());

        ErrorPayload errorPayload = new ErrorPayload(
                (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE),
                statusCode,
                (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI));
        if (500 > statusCode && statusCode >= 400) {
            log.info(errorPayload.getStatusCode() + " : " + errorPayload.getMessage());
            return new ModelAndView("error/400erErrors", "error", errorPayload);
        } else if (statusCode >= 500) {
            log.warn(errorPayload.getStatusCode() + " : " + errorPayload.getMessage());
            return new ModelAndView("error/500erErrors", "error", errorPayload);
        }
        log.warn("Unknown");
        return new ModelAndView("error/defaultError", "error", errorPayload);
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
