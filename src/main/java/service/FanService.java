package service;

import core.Fan;
import exceptions.FanAlreadyExistsException;

import java.util.Date;

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
     * @return Fan object just created
     * @throws FanAlreadyExistsException if the fan's email already exists
     */
    Fan create(String name, String email, Date dateBirth, String team) throws FanAlreadyExistsException;

    /**
     * Deletes all fans.
     */
    void deleteAll();

}
