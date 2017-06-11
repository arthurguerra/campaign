import core.Application;
import core.Campaign;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.CampaignService;
import utils.DateUtils;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignServiceCreateTest {

    private static final String TEST_CAMPAIGN_NAME = "testCampaign";

    @Autowired
    private CampaignService campaignService;

    @Before
    public void setUp() {
        campaignService.deleteAll();
    }

    @Test
    public void createCampaign() {
        Campaign newCampaign = campaignService.create(TEST_CAMPAIGN_NAME, 1, new Date(), new Date());

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
        Campaign newCampaign = campaignService.create(new Campaign(TEST_CAMPAIGN_NAME, 1, date, date));

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
        Date dayAfterTomorrow = DateUtils.addOneDay(tomorrow);

        Campaign c1 = campaignService.create(TEST_CAMPAIGN_NAME + "1", 1, yesterday, today);
        Campaign c2 = campaignService.create(TEST_CAMPAIGN_NAME + "2", 2, yesterday, tomorrow);
        Campaign c3 = campaignService.create(TEST_CAMPAIGN_NAME + "3", 2, today, today);

        assertTrue(DateUtils.datesAreTheSame(c1.getDateEnd(), tomorrow)); // c1 end date was today
        assertTrue(DateUtils.datesAreTheSame(c2.getDateEnd(), dayAfterTomorrow)); // c2 end date was tomorrow
        assertTrue(DateUtils.datesAreTheSame(c3.getDateEnd(), today)); // c3 keeps its end date as today

    }
}
