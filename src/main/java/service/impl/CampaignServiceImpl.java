package service.impl;

import core.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.CampaignService;
import utils.DateUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the Campaign Service.
 */
@Service
public class CampaignServiceImpl implements CampaignService {
    private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);

    private List<Campaign> campaigns;

    @PostConstruct
    public void postConstruct() {
        campaigns = new ArrayList<>();
    }

    @Override
    public Campaign create(String name, long teamId, Date dateStart, Date dateEnd) {
        logger.info("Creating {}  campaign", name);

        updateCampaignsEndDate(dateEnd);
        Campaign nc = new Campaign(name, teamId, dateStart, dateEnd);
        campaigns.add(nc);
        return nc;
    }

    /**
     * Updates all valid campaigns' end dates in order for none of them have the same end date as the given date.
     * @param date date that none of the valid campaigns can be equal to.
     */
    private void updateCampaignsEndDate(Date date) {
        logger.info("Updating campaigns: {}", date);

        Date conflictedDate = date;
        Campaign lastConflictedCampaign = null;

        while (true) {
            Campaign conflictedCampaign =
                    findOldestValidCampaignEndingOnTheSameDate(conflictedDate, lastConflictedCampaign);

            if (conflictedCampaign == null) {
                logger.info("No more campaigns to update date. Stop.");
                break;
            }

            Date newDateEnd = DateUtils.addOneDay(conflictedCampaign.getDateEnd());

            logger.info("Update end date of {} to {}", conflictedCampaign, newDateEnd);

            conflictedCampaign.setDateEnd(newDateEnd);

            conflictedDate = newDateEnd;
            lastConflictedCampaign = conflictedCampaign;
        }
    }

    /**
     * Find the oldest valid campaign whose end date is the same of the given date.
     * @param date date to be compared to
     * @return The campaign that has the end date the same as the given date, if any. Null, otherwise.
     */
    private Campaign findOldestValidCampaignEndingOnTheSameDate(Date date, Campaign campaignIgnore) {
        logger.info("Find oldest valid campaign with end date: {}", date);

        List<Campaign> campaignsSameDate = findAllValidCampaigns().stream()
                .filter(c -> c != campaignIgnore)
                .filter(c -> DateUtils.datesAreTheSame(c.getDateEnd(), date))
                .sorted(Comparator.comparing(Campaign::getDateCreated))
                .collect(Collectors.toList());

        Campaign conflictedCampaign = null;

        if (campaignsSameDate != null && !campaignsSameDate.isEmpty()) {
            conflictedCampaign = campaignsSameDate.get(0);
        }

        return conflictedCampaign;
    }

    @Override
    public Campaign create(Campaign nc) {
        return create(nc.getName(), nc.getTeamId(), nc.getDateStart(), nc.getDateEnd());
    }

    @Override
    public List<Campaign> findAllValidCampaigns() {
        Date today = DateUtils.today();

        return campaigns.stream()
                        .filter(c -> DateUtils.isToday(c.getDateEnd()) || c.getDateEnd().after(today))
                        .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        campaigns.clear();
    }

    @Override
    public void delete(UUID uuid) {
        Iterator<Campaign> it = campaigns.iterator();

        while(it.hasNext()) {
            Campaign c = it.next();
            if (c.getId().equals(uuid)) {
                it.remove();
                break;
            }
        }
    }

    @Override
    public void update(Campaign campaign) {
        Iterator<Campaign> it = campaigns.iterator();

        while(it.hasNext()) {
            Campaign currCampaign = it.next();
            if (currCampaign.getId().equals(campaign.getId())) {
                currCampaign.setTeamId(campaign.getTeamId());
                currCampaign.setName(campaign.getName());
                currCampaign.setDateStart(campaign.getDateStart());
                currCampaign.setDateEnd(campaign.getDateEnd());
                return;
            }
        }
    }
}
