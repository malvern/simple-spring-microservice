package zw.co.malvern.contactservice.integration;

import zw.co.malvern.contactservice.utils.dto.UserDetails;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface IntegrationService {
    List<UserDetails> searchUserDetails(Map<String,String> searchParams);
}
