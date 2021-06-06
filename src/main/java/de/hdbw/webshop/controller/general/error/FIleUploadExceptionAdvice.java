package de.hdbw.webshop.controller.general.error;

import de.hdbw.webshop.exception.ErrorPayload;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@CommonsLog
public class FIleUploadExceptionAdvice {

    private final MessageSource messageSource;

    public FIleUploadExceptionAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException ex,
                                               HttpServletRequest request,
                                               Authentication authentication) {
        if (authentication!=null) {
            log.info(authentication.getName() + ": " + ex.getCause().getMessage());
        } else {
            log.info("Unknown auth: " + ex.getCause().getMessage());
        }
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = Integer.parseInt(status.toString());
        ErrorPayload errorPayload = new ErrorPayload(
                messageSource.getMessage("error.max_upload_size", null, LocaleContextHolder.getLocale()),
                statusCode,
                (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI));
        return new ModelAndView("error/400erErrors", "error", errorPayload);
    }
}
