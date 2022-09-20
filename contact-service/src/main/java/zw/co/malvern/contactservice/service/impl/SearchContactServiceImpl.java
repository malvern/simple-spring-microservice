package zw.co.malvern.contactservice.service.impl;

import org.springframework.beans.BeanUtils;
import zw.co.malvern.contactservice.integration.IntegrationService;
import zw.co.malvern.contactservice.integration.SearchRules;
import zw.co.malvern.contactservice.service.SearchContactService;
import zw.co.malvern.contactservice.utils.dto.SearchRequest;
import zw.co.malvern.contactservice.utils.dto.UserDetails;
import zw.co.malvern.contactservice.utils.enums.SearchVerification;
import zw.co.malvern.contactservice.utils.exceptions.ResultsNotFoundException;
import zw.co.malvern.contactservice.utils.exceptions.SearchException;
import zw.co.malvern.spec.model.UserContactResponse;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static zw.co.malvern.contactservice.utils.constants.SearchUtils.ID_KEY;
import static zw.co.malvern.contactservice.utils.constants.SearchUtils.USERNAME_KEY;

public record SearchContactServiceImpl(
        IntegrationService integrationService) implements SearchContactService {

    @Override
    public List<UserContactResponse> searchUserContact(SearchRequest searchRequest) {
        final HashMap<String, String> searchParams = buildSearchQuery(searchRequest);
        validateSearchQuery(searchParams);
       var userDetails = integrationService.searchUserDetails(searchParams);
        if(userDetails.isEmpty())
            throw new ResultsNotFoundException("No contacts found");
       return userDetails.stream().map(this::convertUserDetailToContact).collect(Collectors.toList());
    }

    private HashMap<String, String> buildSearchQuery(SearchRequest searchRequest) {
        var checkId = SearchRules.isIdNull().apply(searchRequest);
        var checkUsername = SearchRules.isUsernameEmptyOrNull().apply(searchRequest);
        final HashMap<String, String> searchParams = new HashMap<>();
        if (checkId.name().equals(SearchVerification.SUCCESS.name()))
            searchParams.put(ID_KEY, searchRequest.getId().toString());
        if (checkUsername.name().equals(SearchVerification.SUCCESS.name()))
            searchParams.put(USERNAME_KEY, searchRequest.getUsername());
        return searchParams;
    }
    private UserContactResponse convertUserDetailToContact(UserDetails userDetail){
        final UserContactResponse userContactResponse = new UserContactResponse();
        BeanUtils.copyProperties(userDetail, userContactResponse);
        return userContactResponse;
    }
    private void validateSearchQuery(HashMap<String, String> searchParams) {
        if (searchParams.keySet().isEmpty())
            throw new SearchException("Id or Username cannot be empty");
        if (searchParams.keySet().size() > 1)
            throw new SearchException("Only Id or Username can be used for search not both");
    }

}
