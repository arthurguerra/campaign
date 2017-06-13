package app;

import core.Campaign;
import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;
import exceptions.FanAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import service.CampaignService;

import java.util.List;

/**
 * Controls all exceptions thrown by the system.
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @Autowired
    private CampaignService campaignService;

    @ExceptionHandler(value = FanAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public List<Campaign> handleFanAlreadyExistsWithoutCampaigns() {
        return campaignService.findAllValidCampaigns();
    }

    @ExceptionHandler(value = FanAlreadyExistsAndAlreadyHasCampaignsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleFanAlreadyExistsWithCampaigns() { }
}
