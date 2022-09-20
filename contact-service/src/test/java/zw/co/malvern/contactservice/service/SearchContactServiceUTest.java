package zw.co.malvern.contactservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import zw.co.malvern.contactservice.integration.IntegrationService;
import zw.co.malvern.contactservice.service.impl.SearchContactServiceImpl;
import zw.co.malvern.contactservice.utils.dto.*;
import zw.co.malvern.contactservice.utils.exceptions.ResultsNotFoundException;
import zw.co.malvern.contactservice.utils.exceptions.SearchException;


import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static zw.co.malvern.contactservice.TestData.sampleUserDetails;
import static zw.co.malvern.contactservice.TestData.userContactResponse;
import zw.co.malvern.spec.model.UserContactResponse;

public class SearchContactServiceUTest {
    @Mock
    private IntegrationService integrationService;
    private SearchContactService searchContactService;
    @SuppressWarnings("unchecked")
    private final ArgumentCaptor<HashMap<String,String>> searchParamsCaptor =
            ArgumentCaptor.forClass(HashMap.class);

    @BeforeEach
    void setUp() {
        integrationService =  mock(IntegrationService.class);
        searchContactService = new SearchContactServiceImpl(integrationService);
    }

    @Test
    @DisplayName("Success: User contact Details")
    void shouldReturnSearchContactResponse() {
        given(integrationService.searchUserDetails(anyMap()))
                .willReturn(Collections.singletonList(sampleUserDetails()));
        List<UserContactResponse> searchUserContact = searchContactService
                .searchUserContact(new SearchRequest("mal", null));
        verify(integrationService).searchUserDetails(searchParamsCaptor.capture());
        HashMap<String, String> capturedSearchValues = searchParamsCaptor.getValue();
        assertThat(capturedSearchValues).isNotNull();
        assertThat(capturedSearchValues.size()).isNotNull().isEqualTo(1L);
        assertThat(capturedSearchValues.get("username")).isNotNull().isEqualTo("mal");
        assertThat(searchUserContact).isNotNull();
        assertThat(searchUserContact.size()).isNotNull().isEqualTo(1L);
        assertThat(searchUserContact).asList().hasSize(1)
                .containsAnyElementsOf(Collections.singleton(userContactResponse()));
        verify(integrationService,times(1)).searchUserDetails(anyMap());
    }

    @Test
    @DisplayName("ResultsNotFoundException : When No result found")
    void givenEmptyList_shouldThrowNoResultsFoundError() {
        given(integrationService.searchUserDetails(anyMap()))
                .willReturn(Collections.emptyList());
        assertThatThrownBy(()-> searchContactService
                .searchUserContact(new SearchRequest("malv", null)))
                .hasMessage("No contacts found")
                .isInstanceOf(ResultsNotFoundException.class);
        verify(integrationService,times(1)).searchUserDetails(anyMap());
    }

    @Test
    @DisplayName("Search Exception: When search params are empty")
    void givenEmptyQueryParams_shouldThrowSearchException(){
        given(integrationService.searchUserDetails(anyMap()))
                .willReturn(Collections.emptyList());
        assertThatThrownBy(()-> searchContactService
                .searchUserContact(new SearchRequest(null, null)))
                .hasMessage("Id or Username cannot be empty")
                .isInstanceOf(SearchException.class);
        verify(integrationService,never()).searchUserDetails(anyMap());
    }
    @Test
    @DisplayName("SearchException: When all search params are provided")
    void givenAllQueryParamsAreProvided_shouldThrowSearchException(){
        given(integrationService.searchUserDetails(anyMap()))
                .willReturn(Collections.emptyList());
        assertThatThrownBy(()-> searchContactService
                .searchUserContact(new SearchRequest("mal", 10L)))
                .hasMessage("Only Id or Username can be used for search not both")
                .isInstanceOf(SearchException.class);
        verify(integrationService,never()).searchUserDetails(anyMap());
    }

}
