package service;

import core.Campaign;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    /**
     * Deletes a specific campaign.
     * @param uuid id of the campaign to be deleted
     */
    void delete(UUID uuid);

    /**
     * Updates a campaign with its new attributes based on its ID
     * @param campaign that will be updated
     *                 in order for a campaign to be updated its ID must exist
     */
    void update(Campaign campaign);
}
