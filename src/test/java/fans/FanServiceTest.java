package fans;

import app.Application;
import core.Fan;
import exceptions.FanAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.FanService;
import utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Autowired
    private FanService fanService;

    @Before
    public void setUp() {
        fanService.deleteAll();
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
    public void duplicatedFan() throws ParseException, FanAlreadyExistsException {
        Date dateBirth = dateFormat.parse("1994-12-12");
        fanService.create(TEST_FAN_NAME, TEST_FAN_EMAIL, dateBirth, TEST_FAN_TEAM);
        fanService.create("duplicatedFan", TEST_FAN_EMAIL, dateBirth, "Barcelona");
    }
}
