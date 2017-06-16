package fans;

import app.Application;
import app.FanController;
import app.GlobalControllerExceptionHandler;
import core.Campaign;
import core.Fan;
import exceptions.FanAlreadyExistsAndAlreadyHasCampaignsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.FanService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test Fan controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class FanControllerTest {

    private static final String TEST_CAMPAIGN_NAME = "testCampaign";
    private static final String TEST_FAN_NAME = "John Smith";
    private static final String TEST_FAN_EMAIL = "john.smith@gmail.com";
    private static final String TEST_FAN_TEAM = "Real Madrid";
    private static final String TEST_FAN_DATE_BIRTH = "1980-06-06";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private MockMvc mockMvc;
    private String testDateStr;

    @Mock
    private FanService mockFanService;

    @Mock
    private GlobalControllerExceptionHandler mockGlobalControllerExceptionHandler;

    @InjectMocks
    private FanController fanController;

    private String fanJson;
    private List<Campaign> campaigns;

    @Before
    public void setUp() throws FanAlreadyExistsAndAlreadyHasCampaignsException, ParseException {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(fanController)
                .setControllerAdvice(mockGlobalControllerExceptionHandler).build();

        Date testDate = new Date();
        testDateStr = dateFormat.format(testDate);

        fanJson = getFanJson();

        Campaign testCampain = new Campaign(TEST_CAMPAIGN_NAME, 1, testDate, testDate);
        campaigns = new ArrayList<>();
        campaigns.add(testCampain);

        Fan f = new Fan(TEST_FAN_NAME, TEST_FAN_EMAIL, TEST_FAN_TEAM, dateFormat.parse(TEST_FAN_DATE_BIRTH));
        f.addCampaign(testCampain);

        when(mockGlobalControllerExceptionHandler.handleFanAlreadyExistsWithCampaigns()).thenCallRealMethod();
    }

    @Test
    public void createBrandNewFan() throws Exception {
        when(mockFanService.create(anyObject(), anyObject(), anyObject(), anyObject())).thenReturn(campaigns);

        mockMvc.perform(post("/fan")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(fanJson.getBytes())
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(TEST_CAMPAIGN_NAME)))
                .andExpect(jsonPath("$[0].teamId").isNotEmpty())
                .andExpect(jsonPath("$[0].dateStart", is(testDateStr)))
                .andExpect(jsonPath("$[0].dateEnd", is(testDateStr)))
                .andExpect(jsonPath("$[0].id").isNotEmpty());

        verify(mockFanService, times(1)).create(anyObject(), anyObject(), anyObject(), anyObject());
    }

    @Test
    public void existingFanWithCampaigns() throws Exception {
        when(mockFanService.create(anyObject(), anyObject(), anyObject(), anyObject()))
                .thenThrow(new FanAlreadyExistsAndAlreadyHasCampaignsException());

        mockMvc.perform(post("/fan")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(fanJson.getBytes())
        ).andExpect(status().isConflict())
            .andExpect(content().string("Fan already exists"));

        verify(mockFanService, times(1)).create(anyObject(), anyObject(), anyObject(), anyObject());
        verify(mockFanService, only()).create(anyObject(), anyObject(), anyObject(), anyObject());
    }

    private String getFanJson() {
        return fanJson = "{" +
                "\"name\":\"" + TEST_FAN_NAME + "\"," +
                "\"email\":\"" + TEST_FAN_EMAIL + "\", "+
                "\"dateBirth\":\"" + TEST_FAN_DATE_BIRTH + "\", " +
                "\"team\":\"" + TEST_FAN_TEAM + "\"" +
                "}";
    }
}
