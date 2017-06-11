import core.Application;
import core.Campaign;
import core.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.CampaignService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignServiceFindAllTest {

    private static final String TEST_CAMPAIGN_NAME = "testCampaign";

    @Autowired
    private CampaignService campaignService;

    @Before
    public void setUp() {
        campaignService.deleteAll();
        campaignService.create(TEST_CAMPAIGN_NAME + "1", 1, DateUtils.yesterday(), DateUtils.yesterday());
        campaignService.create(TEST_CAMPAIGN_NAME + "2", 1, DateUtils.yesterday(), DateUtils.today());
        campaignService.create(TEST_CAMPAIGN_NAME + "3", 1, DateUtils.today(), DateUtils.tomorrow());
        campaignService.create(TEST_CAMPAIGN_NAME + "4", 1, DateUtils.tomorrow(), DateUtils.tomorrow());
    }

    @Test
    public void findAllValidCampaigns() {
        List<Campaign> campaigns = campaignService.findAllValidCampaigns();

        assertNotNull(campaigns);
        assertEquals(3, campaigns.size()); // one campaign is old (effective date has already passed)

        Date today = DateUtils.today();
        for (Campaign c: campaigns) {
            Date dateEnd = c.getDateEnd();
            boolean campaignFinishesToday = DateUtils.isToday(dateEnd);
            boolean campaignFinishesAfterToday = dateEnd.after(today);
            assertTrue(campaignFinishesToday || campaignFinishesAfterToday);
        }
    }

}
