package app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import service.CampaignService;
import service.FanService;
import service.impl.CampaignServiceImpl;
import service.impl.FanServiceImpl;

/**
 * App configuration.
 */
@Configuration
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public static CampaignService campaignService() {
        return new CampaignServiceImpl();
    }

    @Bean
    public static FanService fanService() {
        return new FanServiceImpl();
    }

}
