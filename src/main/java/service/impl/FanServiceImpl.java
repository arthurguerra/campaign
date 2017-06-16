package service.impl;

import core.Campaign;
import core.Fan;
import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CampaignService;
import service.FanService;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<Campaign> create(String name, String email, Date dateBirth, String team)
            throws FanAlreadyExistsAndAlreadyHasCampaignsException {
        logger.info("Create new fan: {}", name);

        if (!teamsMap.containsKey(team)) {
            teamsMap.put(team, ++lastTeamId);
        }

        if(!fansMap.containsKey(email)) {
            logger.error("Fan with email {} not exists. Creating fan.", email);
            fansMap.put(email, new Fan(name, email, team, dateBirth));
        }

        Fan f = fansMap.get(email);

        List<Campaign> campaigns = registerFanOnCampaigns(email, team);

        if (campaigns == null || campaigns.isEmpty()) {
            logger.info("Fan {} already associated with one or more campaigns", email);
            throw new FanAlreadyExistsAndAlreadyHasCampaignsException();
        }

        return campaigns;
    }

    private List<Campaign> registerFanOnCampaigns(String email, String team) {
        if (!teamsMap.containsKey(team)) {
            logger.error("Team does not exist");
            return new ArrayList<>();
        }

        if (!fansMap.containsKey(email)) {
            logger.error("Fan does not exist");
            return new ArrayList<>();
        }

        Fan f = fansMap.get(email);
        long teamId = teamsMap.get(team);

        List<Campaign> validCampaigns = campaignService.findAllValidCampaigns()
                .stream().filter(c -> c.getTeamId() == teamId)
                .collect(Collectors.toList());

        validCampaigns.removeAll(f.getCampaigns());

        validCampaigns.stream()
                .filter(c -> c.getTeamId() == teamId)
                .forEach(f::addCampaign);

        return validCampaigns;
    }

    @Override
    public void deleteAll() {
        fansMap.clear();
    }

    @Override
    public Fan find(String email) {
        return fansMap.get(email);
    }
}
