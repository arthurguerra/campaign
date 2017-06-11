import core.Application;
import core.Campaign;
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
        Date date = new Date();
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

}
