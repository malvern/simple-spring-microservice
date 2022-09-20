package zw.co.malvern.contactservice;

import zw.co.malvern.contactservice.utils.dto.Address;
import zw.co.malvern.contactservice.utils.dto.Company;
import zw.co.malvern.contactservice.utils.dto.Geolocation;
import zw.co.malvern.contactservice.utils.dto.UserDetails;
import zw.co.malvern.spec.model.UserContactResponse;

public class TestData {
    public static UserDetails sampleUserDetails() {
        final UserDetails userDetails = new UserDetails();
        userDetails.setUsername("mal");
        userDetails.setName("malvern");
        userDetails.setId(1002L);
        userDetails.setEmail("malvern@test");
        userDetails.setPhone("+2637");
        userDetails.setWebsite("www.zero.com");
        final Company company = new Company();
        company.setName("solo");
        company.setBs("bs");
        company.setCatchPhrase("solo");
        userDetails.setCompany(company);
        final Address address = new Address();
        address.setCity("harare");
        address.setStreet("ghetto");
        address.setSuite("1234");
        address.setZipcode("+263");
        final Geolocation geolocation = new Geolocation();
        geolocation.setLatitude("000");
        geolocation.setLongitude("000");
        address.setGeolocation(geolocation);
        address.setGeolocation(geolocation);
        userDetails.setAddress(address);
        return userDetails;
    }

    public static UserContactResponse userContactResponse() {
        final UserContactResponse userContactResponse = new UserContactResponse();
        userContactResponse.setEmail("malvern@test");
        userContactResponse.setPhone("+2637");
        userContactResponse.setId(1002L);
        return userContactResponse;
    }

}
