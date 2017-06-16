package app;

import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import service.CampaignService;

/**
 * Controls all exceptions thrown by the system.
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @Autowired
    private CampaignService campaignService;

    @ExceptionHandler(value = FanAlreadyExistsAndAlreadyHasCampaignsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleFanAlreadyExistsWithCampaigns() {
        return "Fan already exists";
    }
}
