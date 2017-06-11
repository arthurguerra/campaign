import app.Application;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignServiceDeleteTest {

    private static final String TEST_CAMPAIGN_NAME = "testCampaign";

    @Autowired
    private CampaignService campaignService;

    private Campaign campaign1, campaign2;

    @Before
    public void setUp() {
        campaign1 = campaignService.create(TEST_CAMPAIGN_NAME, 1, DateUtils.today(), DateUtils.tomorrow());
        campaign2 = campaignService.create(TEST_CAMPAIGN_NAME, 2, DateUtils.today(), DateUtils.today());
    }

    @Test
    public void deleteAllCampaigns() {
        campaignService.deleteAll();
        assertTrue(campaignService.findAllValidCampaigns().isEmpty());
    }

    @Test
    public void deleteCampaign() {
        campaignService.delete(campaign1.getId());
        assertEquals(1, campaignService.findAllValidCampaigns().size());
        assertEquals(campaign2.getId(), campaignService.findAllValidCampaigns().get(0).getId());
    }

}
