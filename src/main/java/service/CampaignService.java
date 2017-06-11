package service;

import core.Campaign;

import java.util.Date;
import java.util.List;

/**
 * Campaign service.
 */
public interface CampaignService {

    /**
     * Creates a new campaign.
     * @param teamId favorite team's id
     * @param dateStart date when the campaign starts
     * @param dateEnd date when the campaign ends
     * @return @{@link Campaign} object just created
     */
    Campaign create(long teamId, Date dateStart, Date dateEnd);

    /**
     * Creates a new campaign
     * @param newCampaign new campaign to be created
     *                    this new campaign must not have an ID
     *                    ID will be generated automatically
     * @return {@link Campaign} object with an unique ID
     */
    Campaign create(Campaign newCampaign);

    /**
     * Finds all existing campaigns that are still valid. Campaigns already expired
     * are not listed here.
     * @return List of valid campaigns
     */
    List<Campaign> findAll();

    /**
     * Deletes all campaigns.
     */
    void deleteAll();
}
