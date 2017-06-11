import core.Application;
import core.Campaign;
import core.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.CampaignService;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignServiceCreateTest {

    @Autowired
    private CampaignService campaignService;

    @Test
    public void createCampaign() {
        Campaign newCampaign = campaignService.create(1, new Date(), new Date());

        Date dateStart = newCampaign.getDateStart();
        Date dateEnd = newCampaign.getDateEnd();

        assertNotNull(newCampaign);
        assertNotNull(newCampaign.getId());
        assertNotNull(newCampaign.getDateStart());
        assertNotNull(newCampaign.getDateEnd());
        assertTrue(dateEnd.equals(dateStart) || dateEnd.after(dateStart));
    }

    @Test
    public void createCampaignPassingObject() {
        Date date = DateUtils.today();
        Campaign newCampaign = campaignService.create(new Campaign(1, date, date));

        Date dateStart = newCampaign.getDateStart();
        Date dateEnd = newCampaign.getDateEnd();

        assertNotNull(newCampaign);
        assertNotNull(newCampaign.getId());
        assertEquals(newCampaign.getTeamId(), 1);
        assertEquals(newCampaign.getDateStart(), date);
        assertEquals(newCampaign.getDateEnd(), date);
        assertTrue(dateEnd.equals(dateStart) || dateEnd.after(dateStart));
    }

    /**
     * If a campaign is being create and its end date is on the same date as one or more existing
     * campaigns, the system has to add one day to the end date of each of those campaigns, so that
     * none of the campaigns has the name end date.
     */
    @Test
    public void createCampaignsEndingOnSameDay() {
        Date yesterday = DateUtils.yesterday();
        Date today = DateUtils.today();
        Date tomorrow = DateUtils.tomorrow();

        Campaign newCampaign1 = campaignService.create(new Campaign(1, yesterday, tomorrow));
        Campaign newCampaign2 = campaignService.create(new Campaign(2, today, tomorrow));

        // campaign 2 will end tomorrow, but campaign 1 already ends that day, so campaign 1 has to change its
        // end date to the day after tomorrow

        assertEquals(newCampaign2.getDateEnd(), tomorrow);
        assertNotEquals(newCampaign1.getDateEnd(), tomorrow);
        assertTrue(newCampaign1.getDateEnd().after(tomorrow));
    }
}
