package app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.CampaignService;
import service.FanService;
import service.impl.CampaignServiceImpl;
import service.impl.FanServiceImpl;

/**
 * App configuration.
 */
@Configuration
public class AppConfig {

    private static final CampaignService campaignService = new CampaignServiceImpl();

    @Bean
    public static CampaignService campaignService() {
        return campaignService;
    }

    @Bean
    public static FanService fanService() {
        return new FanServiceImpl(campaignService);
    }

}
