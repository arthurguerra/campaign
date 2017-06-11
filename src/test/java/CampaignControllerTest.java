import controller.CampaignController;
import core.Application;
import core.Campaign;
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
import service.CampaignService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignControllerTest {

    private static final String TEST_CAMPAIGN_NAME = "testCampaign";
    private MockMvc mockMvc;
    private Date testDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String testDateStr;

    @Mock
    private CampaignService mockCampaignService;

    @InjectMocks
    private CampaignController campaignController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();

        testDate = new Date();
        testDateStr = dateFormat.format(testDate);

        Campaign testCampain = new Campaign(TEST_CAMPAIGN_NAME, 1, testDate, testDate);
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(testCampain);

        when(mockCampaignService.findAllValidCampaigns()).thenReturn(campaigns);
        when(mockCampaignService.create(anyObject())).thenReturn(testCampain);
        doNothing().when(mockCampaignService).delete(anyObject());
        doNothing().when(mockCampaignService).update(anyObject());
    }

    @Test
    public void findAllCampaigns() throws Exception {
        mockMvc.perform(get("/campaign/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name", is(TEST_CAMPAIGN_NAME)))
                .andExpect(jsonPath("$[0].teamId", is(1)))
                .andExpect(jsonPath("$[0].dateStart", is(testDateStr)))
                .andExpect(jsonPath("$[0].dateEnd", is(testDateStr)));

        verify(mockCampaignService, times(1)).findAllValidCampaigns();
        verify(mockCampaignService, only()).findAllValidCampaigns(); // no other method was called
    }

    @Test
    public void createCampaign() throws Exception {
        String campaignJson = "{" +
                "\"name\":\"" + TEST_CAMPAIGN_NAME + "\", " +
                "\"teamId\":\"1\", "+
                "\"dateStart\":\"" + testDateStr + "\", " +
                "\"dateEnd\":\"" + testDateStr + "\"" +
                "}";

        mockMvc.perform(post("/campaign")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(campaignJson.getBytes())
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.teamId", is(1)))
                .andExpect(jsonPath("$.dateCreated").isNotEmpty())
                .andExpect(jsonPath("$.dateStart", is(testDateStr)))
                .andExpect(jsonPath("$.dateEnd", is(testDateStr)));

        verify(mockCampaignService, times(1)).create(anyObject());
        verify(mockCampaignService, only()).create(anyObject());
    }

    @Test
    public void deleteCampaign() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/campaign")
                .param("id", id.toString())
        ).andExpect(status().isNoContent());

        verify(mockCampaignService, times(1)).delete(notNull(UUID.class));
        verify(mockCampaignService, only()).delete(anyObject());
    }

    @Test
    public void updateCampaign() throws Exception {
        String campaignJson = "{" +
                "\"id\":\"" + UUID.randomUUID() + "\"," +
                "\"name\":\"" + TEST_CAMPAIGN_NAME + "\", " +
                "\"teamId\":\"1\", "+
                "\"dateStart\":\"" + testDateStr + "\", " +
                "\"dateEnd\":\"" + testDateStr + "\"" +
                "}";

        mockMvc.perform(put("/campaign")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(campaignJson.getBytes())
        ).andExpect(status().isNoContent());

        verify(mockCampaignService, times(1)).update(notNull(Campaign.class));
        verify(mockCampaignService, only()).update(notNull(Campaign.class));
    }

}
