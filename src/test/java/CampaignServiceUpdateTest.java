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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignServiceUpdateTest {

    private static final String TEST_CAMPAIGN_NAME = "testCampaign";

    @Autowired
    private CampaignService campaignService;

    private Campaign campaign;
    private long expectedTeamId;
    private Date expectedDate;

    @Before
    public void setUp() {
        campaignService.deleteAll();
        campaign = campaignService.create(TEST_CAMPAIGN_NAME, 1, DateUtils.today(), DateUtils.today());

        expectedTeamId = 20;
        expectedDate = DateUtils.tomorrow();

        campaign.setTeamId(expectedTeamId);
        campaign.setDateStart(expectedDate);
        campaign.setDateEnd(expectedDate);
    }

    @Test
    public void updateCampaign() {
        campaignService.update(campaign);

        Campaign c = campaignService.findAllValidCampaigns().get(0);
        assertFalse(c.getName().isEmpty());
        assertEquals(expectedTeamId, c.getTeamId());
        assertEquals(expectedDate, c.getDateStart());
        assertEquals(expectedDate, c.getDateEnd());
    }

}
