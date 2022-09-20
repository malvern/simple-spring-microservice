package zw.co.malvern.contactservice.service;


import zw.co.malvern.contactservice.utils.dto.SearchRequest;
import zw.co.malvern.spec.model.UserContactResponse;

import java.util.List;

@FunctionalInterface
public interface SearchContactService {
    List<UserContactResponse> searchUserContact(SearchRequest searchRequest);
}
