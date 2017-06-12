package controller;

import core.Fan;
import exceptions.FanAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.FanService;

/**
 * Campaign controller.
 */
@RestController
@RequestMapping( value = "/fan" )
public class FanController {

    @Autowired
    private FanService fanService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCampaign(@RequestBody Fan newFan) throws FanAlreadyExistsException {
        fanService.create(null, null, null, null);
    }

}