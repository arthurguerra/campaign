package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.CampaignService;

import java.util.List;

/**
 * Campaign controller.
 */
@RestController
@RequestMapping( value = "/campaign" )
class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Campaign> findAll() {
        return campaignService.findAll();
    }
}