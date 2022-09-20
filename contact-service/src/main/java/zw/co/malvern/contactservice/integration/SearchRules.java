package zw.co.malvern.contactservice.integration;

import zw.co.malvern.contactservice.utils.dto.SearchRequest;
import zw.co.malvern.contactservice.utils.enums.SearchVerification;

import java.util.function.Function;
import java.util.function.Predicate;

public interface SearchRules extends Function<SearchRequest, SearchVerification> {
    static SearchRules isIdNull(){
        Predicate<SearchRequest> isIdNull = (SearchRequest searchRequest)->searchRequest.getId() ==null;
        return searchRequest -> isIdNull.test(searchRequest)?
                SearchVerification.EMPTY_SEARCH_INPUT : SearchVerification.SUCCESS;
    }
    static SearchRules isUsernameEmptyOrNull(){
        Predicate<SearchRequest> isUsernameNull = (SearchRequest searchRequest)->searchRequest.getUsername() ==null;
        Predicate<SearchRequest> isUsernameEmpty = (SearchRequest searchRequest)->searchRequest.getUsername().isEmpty();
        return searchRequest -> isUsernameNull.or(isUsernameEmpty).test(searchRequest)?
                SearchVerification.EMPTY_SEARCH_INPUT : SearchVerification.SUCCESS;
    }

}

