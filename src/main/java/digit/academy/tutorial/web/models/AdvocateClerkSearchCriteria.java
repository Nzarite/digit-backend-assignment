package digit.academy.tutorial.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * AdvocateClerkSearchCriteria
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-11-20T15:58:36.708336466+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvocateClerkSearchCriteria {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("applicationNumber")
    private String applicationNumber = null;

    @JsonProperty("stateRegnNumber")
    private String stateRegnNumber = null;
}
