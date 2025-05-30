package digit.academy.tutorial.repository.querybuilder;

import digit.academy.tutorial.util.QueryBuilderUtil;
import digit.academy.tutorial.web.models.AdvocateSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AdvocateQueryBuilder {

    private static final String BASE_ADVOCATE_QUERY = " SELECT adv.id as adv_id, adv.tenant_id as tenant_id, adv.application_number as application_number, adv.bar_registration_number as bar_registration_number, adv.advocate_type as advocate_type, adv.organisation_id as organisation_id, adv.individual_id as individual_id, adv.is_active as is_active, adv.additional_details as additional_details, adv.created_by as created_by, adv.last_modified_by as last_modified_by, adv.created_time as created_time, adv.last_modified_time as last_modified_time, ";
    private static final String DOCUMENT_SELECT_QUERY = " doc.id as doc_id, doc.document_type as document_type, doc.file_store as file_store, doc.document_uid as document_uid, doc.advocate_id as advocate_id, doc.additional_details as additional_details ";
    private static final String FROM_TABLES = " FROM eg_advocate_registration adv LEFT JOIN eg_adv_document doc ON adv.id = doc.advocate_id ";
    private static final String ORDERBY_CREATEDTIME = " ORDER BY adv.created_time DESC ";

    /**
     * This method constructs the SQL query to search for advocate registrations
     * based on the provided search criteria and dynamically builds the WHERE clause
     * based on non-null search parameters.
     *
     * @param criteria         The search criteria containing various filters for the query.
     * @param preparedStmtList A list to hold the prepared statement values.
     * @return A string representing the constructed SQL query.
     */
    public String getAdvocateRegistrationSearchQuery(AdvocateSearchCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(BASE_ADVOCATE_QUERY);
        queryBuilder.append(DOCUMENT_SELECT_QUERY);
        queryBuilder.append(FROM_TABLES);

        Map<String, Object> conditionMap = Map.of("adv.id", criteria.getId(), "adv.bar_registration_number",
                criteria.getBarRegistrationNumber(), "adv.application_number", criteria.getApplicationNumber());

        QueryBuilderUtil.buildWhereClause(queryBuilder, preparedStmtList, conditionMap);
        queryBuilder.append(ORDERBY_CREATEDTIME);
        return queryBuilder.toString();
    }
}
