package service.impl;

import core.Campaign;
import org.springframework.stereotype.Service;
import service.CampaignService;

import javax.annotation.PostConstruct;
import java.util.*;

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
    public Campaign create(long teamId, Date dateStart, Date dateEnd) {
        Campaign nc = new Campaign(teamId, dateStart, dateEnd);
        campaigns.add(nc);
        return nc;
    }

    @Override
    public Campaign create(Campaign newCampaign) {
        return create(newCampaign.getTeamId(), newCampaign.getDateStart(), newCampaign.getDateEnd());
    }

    @Override
    public List<Campaign> findAll() {
        return campaigns;
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
