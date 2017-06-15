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
    private Map<String, Long> teamsMap;
    private long lastTeamId;

    private final CampaignService campaignService;

    @Autowired
    public FanServiceImpl(CampaignService campaignService) {
        this.campaignService = campaignService;
        fansMap = new HashMap<>();
        teamsMap = new HashMap<>();
        lastTeamId = 0;
    }

    @Override
    public Fan create(String name, String email, Date dateBirth, String team)
            throws FanAlreadyExistsException, FanAlreadyExistsAndAlreadyHasCampaignsException {
        logger.info("Create new fan: {}", name);

        if(fansMap.containsKey(email)) {
            logger.error("Fan with email {} already exists", email);

            Fan f = fansMap.get(email);
            if (f.hasCampaigns()) {
                logger.info("Fan {} already associated with one or more campaigns", email);
                throw new FanAlreadyExistsAndAlreadyHasCampaignsException();
            } else {
                logger.info("Fan {} not associated with campaigns yet", email);
                throw new FanAlreadyExistsException();
            }
        }

        if (!teamsMap.containsKey(team)) {
            teamsMap.put(team, ++lastTeamId);
        }

        Fan newFan = new Fan(name, email, team, dateBirth);
        fansMap.put(email, newFan);

        registerFanOnCampaigns(email, team);

        return newFan;
    }

    private void registerFanOnCampaigns(String email, String team) {
        if (!teamsMap.containsKey(team)) {
            logger.error("Team does not exist");
            return;
        }

        if (!fansMap.containsKey(email)) {
            logger.error("Fan does not exist");
            return;
        }

        Fan f = fansMap.get(email);
        long teamId = teamsMap.get(team);

        campaignService.findAllValidCampaigns().stream()
                .filter(c -> c.getTeamId() == teamId)
                .forEach(f::addCampaign);
    }

    @Override
    public void deleteAll() {
        fansMap.clear();
    }

}
