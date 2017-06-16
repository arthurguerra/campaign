package service;

import core.Campaign;
import core.Fan;
import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;

import java.util.Date;
import java.util.List;

/**
 * Fan Service interface.
 */
public interface FanService {

    /**
     * Creates a new fan.
     * @param name fan's full name
     * @param email fan's email
     * @param dateBirth fan's date of birth
     * @param team fan's favorite team
     * @return List of new campaigns associated to the fan
     * @throws FanAlreadyExistsAndAlreadyHasCampaignsException if the fan's email already exists and this fan
     *  is already associated with one more campaigns
     */
    List<Campaign> create(String name, String email, Date dateBirth, String team)
            throws FanAlreadyExistsAndAlreadyHasCampaignsException;

    /**
     * Deletes all fans.
     */
    void deleteAll();

    /**
     * Finds a fan by email
     * @param email fan's email
     * @return {@link Fan} instance for the corresponding the email
     */
    Fan find(String email);
}
