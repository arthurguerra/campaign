package service.impl;

import core.Campaign;
import org.springframework.stereotype.Service;
import service.CampaignService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Campaign create(int id, int teamId, Date dateStart, Date dateEnd) {
        Campaign nc = new Campaign(id, teamId, dateStart, dateEnd);
        campaigns.add(nc);
        return nc;
    }

    @Override
    public List<Campaign> findAll() {
        return campaigns;
    }

    @Override
    public void deleteAll() {
        campaigns.clear();
    }
}
