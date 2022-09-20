package zw.co.malvern.contactservice.utils.dto;

import lombok.Data;

@Data
public class UserDetails {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private Address address;
    private Company company;

}
