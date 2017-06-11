import core.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import service.CampaignService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignControllerTest {

    private MockMvc mockMvc;
    private long startTimestamp, endTimestamp;
    private Date testDate;
    private SimpleDateFormat dateFormat;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CampaignService campaignService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        testDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        startTimestamp = calendar.getTimeInMillis();
        endTimestamp = calendar.getTimeInMillis();

        campaignService.create(1, 1, testDate, testDate);
        campaignService.create(2, 2, testDate, testDate);
    }

    @Test
    public void findAllCampaigns() throws Exception {
        mockMvc.perform(get("/campaign/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].teamId", is(1)))
                .andExpect(jsonPath("$[0].dateStart", is(startTimestamp)))
                .andExpect(jsonPath("$[0].dateEnd", is(endTimestamp)));
    }

    @Test
    public void createCampaign() throws Exception {
        String dateStr = dateFormat.format(testDate);

        String campaignJson = "{" +
                "\"teamId\":\"1\", "+
                "\"dateStart\":\"" + dateStr + "\", " +
                "\"dateEnd\":\"" + dateStr + "\"" +
                "}";

        mockMvc.perform(post("/campaign")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(campaignJson.getBytes())
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.teamId", is(1)))
                .andExpect(jsonPath("$.dateStart", is(dateStr)))
                .andExpect(jsonPath("$.dateEnd", is(dateStr)));
    }

}
