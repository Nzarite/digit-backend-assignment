package digit.academy.tutorial.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * AdvocateResponse
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-11-20T15:58:36.708336466+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvocateResponse {

    @JsonProperty("responseInfo")
    @Valid
    private ResponseInfo responseInfo = null;

    @JsonProperty("advocates")
    @Valid
    private List<Advocate> advocates = null;

    @JsonProperty("pagination")
    @Valid
    private Pagination pagination = null;

    public AdvocateResponse addAdvocatesItem(Advocate advocatesItem) {
        if (this.advocates == null) {
            this.advocates = new ArrayList<>();
        }
        this.advocates.add(advocatesItem);
        return this;
    }

}
