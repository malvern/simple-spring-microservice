package zw.co.malvern.contactservice.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequest {
    private String username;
    private Long id;
}
