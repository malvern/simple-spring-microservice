package zw.co.malvern.contactservice.configuration;

import org.springframework.web.client.RestTemplate;

public class TestConfiguration {
    public static RestTemplate testRestTemplate(){
        return new RestTemplate();
    }
}
