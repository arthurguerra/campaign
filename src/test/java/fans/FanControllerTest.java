package fans;

import app.Application;
import app.FanController;
import app.GlobalControllerExceptionHandler;
import core.Campaign;
import exceptions.FanAlreadyExistsException;
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
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private MockMvc mockMvc;
    private String testDateStr;

    @Mock
    private FanService mockFanService;

    @Mock
    private GlobalControllerExceptionHandler mockGlobalControllerExceptionHandler;

    @InjectMocks
    private FanController fanController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(fanController)
                .setControllerAdvice(mockGlobalControllerExceptionHandler).build();

        Date testDate = new Date();
        testDateStr = dateFormat.format(testDate);

        Campaign testCampain = new Campaign(TEST_CAMPAIGN_NAME, 1, testDate, testDate);
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(testCampain);

        when(mockGlobalControllerExceptionHandler.handleFanAlreadyExists()).thenReturn(campaigns);

    }

    @Test
    public void createBrandNewUser() throws Exception {
        String userJson = "{" +
                "\"name\":\"John Smith\"," +
                "\"email\":\"johnsmith@gmail.com\", "+
                "\"dateBirth\":\"1980-01-01\", " +
                "\"team\":\"Real Madrid\"" +
                "}";

        mockMvc.perform(post("/fan")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(userJson.getBytes())
        ).andExpect(status().isCreated());

        verify(mockFanService, times(1)).create(anyObject(), anyObject(), anyObject(), anyObject());
        verify(mockFanService, only()).create(anyObject(), anyObject(), anyObject(), anyObject());
    }

    @Test
    public void existingUser() throws Exception {
        when(mockFanService.create(anyObject(), anyObject(), anyObject(), anyObject()))
                .thenThrow(new FanAlreadyExistsException());

        String userJson = "{" +
                "\"name\":\"John Smith\"," +
                "\"email\":\"johnsmith@gmail.com\", "+
                "\"dateBirth\":\"1980-01-01\", " +
                "\"team\":\"Real Madrid\"" +
                "}";

        mockMvc.perform(post("/fan")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(userJson.getBytes())
        ).andExpect(status().isConflict())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").isNotEmpty())
            .andExpect(jsonPath("$[0].name", is(TEST_CAMPAIGN_NAME)))
            .andExpect(jsonPath("$[0].teamId", is(1)))
            .andExpect(jsonPath("$[0].dateStart", is(testDateStr)))
            .andExpect(jsonPath("$[0].dateEnd", is(testDateStr)));

        verify(mockFanService, times(1)).create(anyObject(), anyObject(), anyObject(), anyObject());
        verify(mockFanService, only()).create(anyObject(), anyObject(), anyObject(), anyObject());

    }
}
