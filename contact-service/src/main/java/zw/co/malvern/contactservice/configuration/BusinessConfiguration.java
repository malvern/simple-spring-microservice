package zw.co.malvern.contactservice.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import zw.co.malvern.contactservice.integration.IntegrationService;
import zw.co.malvern.contactservice.integration.impl.IntegrationServiceImpl;
import zw.co.malvern.contactservice.service.SearchContactService;
import zw.co.malvern.contactservice.service.impl.SearchContactServiceImpl;

@Configuration
public class BusinessConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().build();
    }
    @Bean
    public IntegrationService integrationService(){
        return new IntegrationServiceImpl(restTemplate());
    }
    @Bean
    public SearchContactService searchContactService(){
        return new SearchContactServiceImpl(integrationService());
    }
}
