package core;

import java.util.Date;

/**
 * Fan's class representation.
 */
public class Fan {
    private String name, email, team;
    private Date dateBirth;

    public Fan(String name, String email, String team, Date dateBirth) {
        this.name = name;
        this.email = email;
        this.team = team;
        this.dateBirth = dateBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Fan)) {
            return false;
        }

        Fan f = (Fan) o;

        return this.email.equals(f.getEmail());
    }

    public boolean hasCampaigns() {
        return true;
    }
}
