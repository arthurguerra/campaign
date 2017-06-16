package app;

import core.Campaign;
import core.Fan;
import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.FanService;

import java.util.List;

/**
 * Fan controller.
 */
@RestController
@RequestMapping( value = "/fan" )
public class FanController {

    @Autowired
    private FanService fanService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Campaign> createFan(@RequestBody Fan newFan) throws FanAlreadyExistsAndAlreadyHasCampaignsException {
        return fanService.create(newFan.getName(), newFan.getEmail(), newFan.getDateBirth(), newFan.getTeam());
    }

}