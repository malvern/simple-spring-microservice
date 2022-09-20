package zw.co.malvern.contactservice.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import zw.co.malvern.contactservice.integration.impl.IntegrationServiceImpl;
import zw.co.malvern.contactservice.utils.dto.UserDetails;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zw.co.malvern.contactservice.configuration.TestConfiguration.testRestTemplate;

@SpringBootTest
@ActiveProfiles("dev")
public class IntegrationServiceITest {
    private IntegrationService integrationService;

    @BeforeEach
    void setUp() {
        integrationService = new IntegrationServiceImpl(testRestTemplate());
        ReflectionTestUtils.setField(integrationService, "searchUrl", "https://jsonplaceholder.typicode.com/users");
    }
    
    @Test
    @DisplayName("search user by username")
    void shouldReturnUserDetails() {
        final var searchParams = new HashMap<String,String>();
        searchParams.put("username","Bret");
        var userDetails = integrationService.searchUserDetails(searchParams);
        assertThat(userDetails).as("user details").isNotNull();
        assertAll("user details", ()->assertThat(userDetails.size()).isEqualTo(1L));
    }

    @Test
    @DisplayName("search user by id")
    void whenSearchingUsingId_shouldReturnUserDetails() {
        final var searchParams = new HashMap<String,String>();
        searchParams.put("id","1");
        List<UserDetails> userDetails = integrationService.searchUserDetails(searchParams);
        assertThat(userDetails).as("user details").isNotNull();
        assertAll("user details", ()->assertThat(userDetails.size()).isEqualTo(1L));
    }

    @Test
    @DisplayName("search non existing user by name")
    void shouldReturnEmptyListForNonExistingUser() {
        final var searchParams = new HashMap<String,String>();
        searchParams.put("username","malvern");
        List<UserDetails> userDetails = integrationService.searchUserDetails(searchParams);
        assertThat(userDetails).as("user details").isNotNull();
        assertAll("user details", ()->assertThat(userDetails.size()).isEqualTo(0));
    }

    @Test
    @DisplayName("search non existing user by id")
    void whenSearchById_shouldReturnEmptyListForNonExistingUser() {
        final var searchParams = new HashMap<String,String>();
        searchParams.put("id","1002");
        List<UserDetails> userDetails = integrationService.searchUserDetails(searchParams);
        assertThat(userDetails).as("user details").isNotNull();
        assertAll("user details", ()->assertThat(userDetails.size()).isEqualTo(0));
    }

}
