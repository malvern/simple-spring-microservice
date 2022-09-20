package zw.co.malvern.contactservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import zw.co.malvern.contactservice.service.SearchContactService;
import zw.co.malvern.contactservice.utils.dto.SearchRequest;
import zw.co.malvern.contactservice.utils.exceptions.ResultsNotFoundException;
import zw.co.malvern.contactservice.utils.exceptions.SearchException;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zw.co.malvern.contactservice.TestData.userContactResponse;

@WebMvcTest(controllers = {SearchContactController.class})
public class SearchContactControllerUTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchContactService searchContactService;

    @Test
    @DisplayName("Success : return contact info")
    void shouldReturnUsersContactInformation() throws Exception {
        final MultiValueMap<String,String> searchParams = new LinkedMultiValueMap<>();
        searchParams.add("username","Leanne Graham");
        given(searchContactService.searchUserContact(any(SearchRequest.class)))
                .willReturn(Collections.singletonList(userContactResponse()));
        mockMvc.perform(get("/getusercontacts").params(searchParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].email").isString())
                .andExpect(jsonPath("$[0].phone").isString())
                .andExpect(jsonPath("$[0].id").value(1002))
                .andExpect(jsonPath("$[0].email").value("malvern@test"))
                .andExpect(jsonPath("$[0].phone").value("+2637"));
        verify(searchContactService,times(1)).searchUserContact(any(SearchRequest.class));
    }

    @Test
    @DisplayName("Search Exception : When All Search params are provided")
    void shouldHandleSearchException() throws Exception {
        final MultiValueMap<String,String> searchParams = new LinkedMultiValueMap<>();
        searchParams.add("username","Leanne Graham");
        searchParams.add("id","1002");
        given(searchContactService.searchUserContact(any(SearchRequest.class)))
                .willThrow(new SearchException("Only Id or Username can be used for search not both"));
        mockMvc.perform(get("/getusercontacts").params(searchParams))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("id").value(-2))
                .andExpect(jsonPath("narrative").isString())
                .andExpect(jsonPath("narrative")
                        .value("Only Id or Username can be used for search not both"));
        verify(searchContactService,times(1)).searchUserContact(any(SearchRequest.class));

    }
    @Test
    @DisplayName("Search Exception: When Params are null")
    void shouldNoResultExceptionSearchException() throws Exception {
        final MultiValueMap<String,String> searchParams = new LinkedMultiValueMap<>();
        given(searchContactService.searchUserContact(any(SearchRequest.class)))
                .willThrow(new SearchException("Only Id or Username can be used for search not both"));
        mockMvc.perform(get("/getusercontacts").params(searchParams))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("id").value(-2))
                .andExpect(jsonPath("narrative").isString())
                .andExpect(jsonPath("narrative")
                        .value("Only Id or Username can be used for search not both"));
        verify(searchContactService,times(1)).searchUserContact(any(SearchRequest.class));
    }
    @Test
    @DisplayName("No Result Exception: Empty contact result")
    void shouldHandleNoResultException() throws Exception {
        final MultiValueMap<String,String> searchParams = new LinkedMultiValueMap<>();
        given(searchContactService.searchUserContact(any(SearchRequest.class)))
                .willThrow(new ResultsNotFoundException("No contacts found"));
        mockMvc.perform(get("/getusercontacts").params(searchParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("narrative").isString())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("id").value(-1))
                .andExpect(jsonPath("narrative")
                        .value("No contacts found"));
        verify(searchContactService,times(1)).searchUserContact(any(SearchRequest.class));
    }
    @Test
    @DisplayName("Exception: Internal Server exceptions")
    void shouldHandleException() throws Exception {
        final MultiValueMap<String,String> searchParams = new LinkedMultiValueMap<>();
        given(searchContactService.searchUserContact(any(SearchRequest.class)))
                .willThrow(new RuntimeException("Ooops some error occurred"));
        mockMvc.perform(get("/getusercontacts").params(searchParams))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("narrative").isString())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("id").value(-3))
                .andExpect(jsonPath("narrative")
                        .value("Internal error occurred"));
        verify(searchContactService,times(1)).searchUserContact(any(SearchRequest.class));
    }
}
