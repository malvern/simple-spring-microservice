package zw.co.malvern.contactservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import zw.co.malvern.spec.SearchContactApi;
import zw.co.malvern.contactservice.service.SearchContactService;
import zw.co.malvern.contactservice.utils.dto.SearchRequest;
import zw.co.malvern.spec.model.UserContactResponse;

import java.util.List;

@Slf4j
@RestController
public class SearchContactController implements SearchContactApi {
    private final SearchContactService searchContactService;

    public SearchContactController(SearchContactService searchContactService) {
        this.searchContactService = searchContactService;
    }

    @Override
    public ResponseEntity<List<UserContactResponse>> getUserContacts(String username, Long id) {
        return ResponseEntity.ok(searchContactService.searchUserContact(new SearchRequest(username, id)));
    }


}
