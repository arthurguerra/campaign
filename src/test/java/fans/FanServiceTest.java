package fans;

import app.Application;
import core.Campaign;
import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.CampaignService;
import service.FanService;
import service.impl.CampaignServiceImpl;
import service.impl.FanServiceImpl;
import utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class FanServiceTest {

    private static final String TEST_FAN_NAME = "John Smith";
    private static final String TEST_FAN_TEAM = "Real Madrid";
    private static final String TEST_FAN_EMAIL = "johnsmith@gmail.com";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Mock
    private CampaignService campaignService = new CampaignServiceImpl();

    private FanService fanService;

    @Before
    public void setUp() {
        fanService = new FanServiceImpl(campaignService);

        fanService.deleteAll();

        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(new Campaign("testCampaign", 1, DateUtils.today(), DateUtils.tomorrow()));
        when(campaignService.findAllValidCampaigns()).thenReturn(campaigns);

    }

    @Test
    public void createFan() throws ParseException,
            FanAlreadyExistsAndAlreadyHasCampaignsException {
        Date dateBirth = dateFormat.parse("1980-01-01");
        List<Campaign> campaigns = fanService.create(TEST_FAN_NAME, TEST_FAN_EMAIL, dateBirth, TEST_FAN_TEAM);

        assertNotNull(campaigns);
        assertEquals("testCampaign", campaigns.get(0).getName());
        assertEquals(1, campaigns.get(0).getTeamId());
    }

    @Test(expected = FanAlreadyExistsAndAlreadyHasCampaignsException.class)
    public void fanAlreadyEnrolledWithOneOrMoreCampaigns() throws ParseException,
            FanAlreadyExistsAndAlreadyHasCampaignsException {
        Date dateBirth = dateFormat.parse("1994-12-12");
        fanService.create(TEST_FAN_NAME, TEST_FAN_EMAIL, dateBirth, TEST_FAN_TEAM);
        fanService.create(TEST_FAN_NAME, TEST_FAN_EMAIL, dateBirth, "Barcelona");

    }
}
