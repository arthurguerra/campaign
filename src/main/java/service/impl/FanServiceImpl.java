package service.impl;

import core.Fan;
import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;
import exceptions.FanAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CampaignService;
import service.FanService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the Campaign Service.
 */
@Service
public class FanServiceImpl implements FanService {
    private static final Logger logger = LoggerFactory.getLogger(FanServiceImpl.class);

    private Map<String, Fan> fansMap;

    private final CampaignService campaignService;

    @Autowired
    public FanServiceImpl(CampaignService campaignService) {
        this.campaignService = campaignService;
        fansMap = new HashMap<>();
    }

    @Override
    public Fan create(String name, String email, Date dateBirth, String team)
            throws FanAlreadyExistsException, FanAlreadyExistsAndAlreadyHasCampaignsException {
        logger.info("Create new fan: {}", name);

        if(fansMap.containsKey(email)) {
            logger.error("Fan with email {} already exists", email);

            Fan f = fansMap.get(email);
            if (f.hasCampaigns()) {
                throw new FanAlreadyExistsAndAlreadyHasCampaignsException();
            } else {
                throw new FanAlreadyExistsException();
            }
        }

        Fan newFan = new Fan(name, email, team, dateBirth);
        fansMap.put(email, newFan);

        return newFan;
    }

    @Override
    public void deleteAll() {
        fansMap.clear();
    }
}
