package core;

import java.util.Date;

/**
 * Campaing class representation.
 */
public class Campaign {
    private long id;
    private long teamId;
    private Date dateStart;
    private Date dateEnd;

    public Campaign(long id, long teamId, Date dateStart, Date dateEnd) {
        this.id = id;
        this.teamId = teamId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
