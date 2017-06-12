package campaign;

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

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignServiceUpdateTest {

    private static final String TEST_CAMPAIGN_NAME = "testCampaign";
    private static final String TEST_CAMPAIGN_NAME_UPDATED = "testCampaignUPDATED";

    @Autowired
    private CampaignService campaignService;

    private Campaign campaign, updateCampaign;
    private long expectedTeamId;
    private Date expectedDate;

    @Before
    public void setUp() {
        campaignService.deleteAll();
        campaign = campaignService.create(TEST_CAMPAIGN_NAME, 1, DateUtils.today(), DateUtils.today());

        expectedTeamId = 20;
        expectedDate = DateUtils.tomorrow();

        updateCampaign = new Campaign(TEST_CAMPAIGN_NAME_UPDATED, expectedTeamId, expectedDate, expectedDate);
        updateCampaign.setId(campaign.getId());
    }

    @Test
    public void updateCampaign() {
        campaignService.update(updateCampaign);

        Campaign c = campaignService.findAllValidCampaigns().get(0);
        assertEquals(TEST_CAMPAIGN_NAME_UPDATED, c.getName());
        assertEquals(expectedTeamId, c.getTeamId());
        assertEquals(expectedDate, c.getDateStart());
        assertEquals(expectedDate, c.getDateEnd());
    }

}
