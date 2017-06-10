package core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import service.CampaignService;
import service.impl.CampaignServiceImpl;

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

}
