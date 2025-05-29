package digit.academy.tutorial.repository;

import digit.academy.tutorial.web.models.AdvocateClerk;
import digit.academy.tutorial.web.models.AdvocateClerkSearchCriteria;

import java.util.List;

public interface AdvocateClerkRepository {

    List<AdvocateClerk> getAdvocateClerkRegistrations(AdvocateClerkSearchCriteria searchCriteria);
}
