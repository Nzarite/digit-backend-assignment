package digit.academy.tutorial.repository;

import digit.academy.tutorial.web.models.Advocate;
import digit.academy.tutorial.web.models.AdvocateSearchCriteria;

import java.util.List;

public interface AdvocateRepository {

    List<Advocate> getAdvocates(AdvocateSearchCriteria searchCriteria);
}
