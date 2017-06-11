package service.impl;

import core.Campaign;
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
    private List<Campaign> campaigns;

    @PostConstruct
    public void postConstruct() {
        campaigns = new ArrayList<>();
    }

    @Override
    public Campaign create(String name, long teamId, Date dateStart, Date dateEnd) {
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
        Date conflictedDate = date;
        Campaign lastConflictedCampaign = null;

        while (true) {
            Campaign conflictedCampaign = findOldestValidCampaignEndingOnTheSameDate(conflictedDate);

            if (conflictedCampaign == null || conflictedCampaign == lastConflictedCampaign) {
                break;
            }

            Date newDateEnd = DateUtils.addOneDay(conflictedCampaign.getDateEnd());
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
    private Campaign findOldestValidCampaignEndingOnTheSameDate(Date date) {
        Campaign conflictedCampaign = null;

        for (Campaign c : findAllValidCampaigns()) {
            if (!DateUtils.datesAreTheSame(c.getDateEnd(), date)) continue;

            if (conflictedCampaign == null || conflictedCampaign.getDateCreated().before(c.getDateCreated())) {
                conflictedCampaign = c;
            }
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

    }
}
