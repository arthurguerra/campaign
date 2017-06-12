package service.impl;

import core.Fan;
import exceptions.FanAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CampaignService;
import service.FanService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the Campaign Service.
 */
@Service
public class FanServiceImpl implements FanService {
    private static final Logger logger = LoggerFactory.getLogger(FanServiceImpl.class);

    private Set<Fan> fans;

    private final CampaignService campaignService;

    @Autowired
    public FanServiceImpl(CampaignService campaignService) {
        this.campaignService = campaignService;
        fans = new HashSet<>();
    }

    @Override
    public Fan create(String name, String email, Date dateBirth, String team) throws FanAlreadyExistsException {
        logger.info("Create new fan: {}", name);

        Fan newFan = new Fan(name, email, team, dateBirth);

        if(fans.contains(newFan)) {
            logger.error("Fan with email {} already exists", email);
            throw new FanAlreadyExistsException();
        }

        fans.add(newFan);

        return newFan;
    }

    @Override
    public void deleteAll() {
        fans.clear();
    }
}