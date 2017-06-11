import core.Application;
import core.Campaign;
import core.CampaignController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import service.CampaignService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test Campaign controller.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CampaignControllerTest {

    private MockMvc mockMvc;
    private Date testDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String testDateStr;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private CampaignService mockCampaignService;

    @InjectMocks
    private CampaignController campaignController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();
        //this.mockMvc = webAppContextSetup(webApplicationContext).build();

        testDate = new Date();
        testDateStr = dateFormat.format(testDate);

        Campaign testCampain = new Campaign(1, testDate, testDate);
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(testCampain);

        when(mockCampaignService.findAll()).thenReturn(campaigns);

        when(mockCampaignService.create(anyObject())).thenReturn(testCampain);
    }

    @Test
    public void findAllCampaigns() throws Exception {
        mockMvc.perform(get("/campaign/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].teamId", is(1)))
                .andExpect(jsonPath("$[0].dateStart", is(testDateStr)))
                .andExpect(jsonPath("$[0].dateEnd", is(testDateStr)));

        verify(mockCampaignService, times(1)).findAll();
        verify(mockCampaignService, only()).findAll(); // no other method was called
    }

    @Test
    public void createCampaign() throws Exception {
        String campaignJson = "{" +
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
                .andExpect(jsonPath("$.dateStart", is(testDateStr)))
                .andExpect(jsonPath("$.dateEnd", is(testDateStr)));

        verify(mockCampaignService, times(1)).create(anyObject());
        verify(mockCampaignService, only()).create(anyObject());
    }

}
