package fans;

import app.Application;
import core.Campaign;
import core.Fan;
import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;
import exceptions.FanAlreadyExistsException;
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
import static org.junit.Assert.assertTrue;
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
    public void setUp() throws FanAlreadyExistsException {
        fanService = new FanServiceImpl(campaignService);

        fanService.deleteAll();

        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(new Campaign("testCampaign", 1, DateUtils.today(), DateUtils.tomorrow()));
        when(campaignService.findAllValidCampaigns()).thenReturn(campaigns);

    }

    @Test
    public void createFan() throws ParseException, FanAlreadyExistsException {
        Date dateBirth = dateFormat.parse("1980-01-01");
        Fan fan = fanService.create(TEST_FAN_NAME, TEST_FAN_EMAIL, dateBirth, TEST_FAN_TEAM);

        assertNotNull(fan);
        assertEquals(TEST_FAN_NAME, fan.getName());
        assertEquals(TEST_FAN_EMAIL, fan.getEmail());
        assertEquals(TEST_FAN_TEAM, fan.getTeam());
        assertTrue(DateUtils.datesAreTheSame(dateBirth, fan.getDateBirth()));
    }

    @Test(expected = FanAlreadyExistsException.class)
    public void fanAlreadyEnrolledWithNoCampaigns() throws ParseException, FanAlreadyExistsException {
        Date dateBirth = dateFormat.parse("1994-12-12");
        fanService.create(TEST_FAN_NAME, TEST_FAN_EMAIL, dateBirth, TEST_FAN_TEAM);
        fanService.create("duplicatedFan", TEST_FAN_EMAIL, dateBirth, "Barcelona");
    }

    @Test(expected = FanAlreadyExistsAndAlreadyHasCampaignsException.class)
    public void fanAlreadyEnrolledWithOneOrMoreCampaigns() throws ParseException, FanAlreadyExistsException {
        Date dateBirth = dateFormat.parse("1994-12-12");
        fanService.create(TEST_FAN_NAME, TEST_FAN_EMAIL, dateBirth, TEST_FAN_TEAM);
        fanService.create("duplicatedFan", TEST_FAN_EMAIL, dateBirth, "Barcelona");

    }
}
