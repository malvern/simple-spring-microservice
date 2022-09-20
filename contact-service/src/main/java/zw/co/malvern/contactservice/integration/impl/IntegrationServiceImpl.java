package zw.co.malvern.contactservice.integration.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import zw.co.malvern.contactservice.integration.IntegrationService;
import zw.co.malvern.contactservice.utils.dto.UserDetails;

import java.util.List;
import java.util.Map;

@Slf4j
public class IntegrationServiceImpl implements IntegrationService{
    @Value("${api.search.url}")
    private String searchUrl;
    private final RestTemplate restTemplate;

    public IntegrationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<UserDetails> searchUserDetails(Map<String,String> searchParams) {
        var searchParameters = new LinkedMultiValueMap<String,String>();
         searchParameters.setAll(searchParams);
        final var url = UriComponentsBuilder.fromHttpUrl(searchUrl)
                .queryParams(searchParameters)
                .build().toUriString();
        var  response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<UserDetails>>() {});
        log.info("search results {}",response);
        return response.getBody();
    }
}
