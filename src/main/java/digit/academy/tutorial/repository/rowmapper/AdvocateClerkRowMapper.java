package digit.academy.tutorial.repository.rowmapper;

import digit.academy.tutorial.web.models.AdvocateClerk;
import org.apache.commons.lang3.ObjectUtils;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class AdvocateClerkRowMapper implements ResultSetExtractor<List<AdvocateClerk>> {

    /**
     * This method extracts data from the ResultSet and maps it into a list of AdvocateClerk objects.
     * It processes the ResultSet row by row, creating AdvocateClerk objects and adding documents
     * associated with each clerk if available.
     *
     * @param rs The ResultSet containing the database records.
     * @return A list of AdvocateClerk objects, each containing their respective documents.
     * @throws SQLException        If there is an issue while accessing the ResultSet.
     * @throws DataAccessException If there is a problem with data access.
     */
    @Override
    public List<AdvocateClerk> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, AdvocateClerk> advocateClerkRegnMap = new LinkedHashMap<>();

        while (rs.next()) {
            String applicationNumber = rs.getString("application_number");
            AdvocateClerk advocateClerk = advocateClerkRegnMap.get(applicationNumber);

            if (advocateClerk == null) {
                Long lastModifiedTime = rs.getLong("last_modified_time");
                if (rs.wasNull()) {
                    lastModifiedTime = null;
                }

                AuditDetails auditdetails = AuditDetails.builder().createdBy(rs.getString("created_by"))
                        .createdTime(rs.getLong("created_time")).lastModifiedBy(rs.getString("last_modified_by"))
                        .lastModifiedTime(lastModifiedTime).build();

                advocateClerk = AdvocateClerk.builder().applicationNumber(applicationNumber)
                        .tenantId(rs.getString("tenant_id")).id(rs.getString("adv_clerk_id"))
                        .stateRegnNumber(rs.getString("state_regn_number")).individualId(rs.getString("individual_id"))
                        .isActive(rs.getBoolean("is_active")).auditDetails(auditdetails)
                        .additionalDetails(rs.getObject("additional_details")).documents(new ArrayList<>()).build();

                advocateClerkRegnMap.put(applicationNumber, advocateClerk);
            }

            // Add Document
            String documentId = rs.getString("doc_id");
            if (!ObjectUtils.isEmpty(documentId)) {
                advocateClerk.addDocumentsItem(getDocument(rs));
            }
        }
        return new ArrayList<>(advocateClerkRegnMap.values());
    }

    /**
     * This method extracts document details from the ResultSet.
     *
     * @param rs The ResultSet containing document-related fields.
     * @return A Document object populated with values from the ResultSet.
     * @throws SQLException If there is an issue accessing the ResultSet.
     */
    private Document getDocument(ResultSet rs) throws SQLException {
        return Document.builder().id(rs.getString("doc_id")).documentType(rs.getString("document_type"))
                .fileStore(rs.getString("file_store")).documentUid(rs.getString("document_uid"))
                .additionalDetails(rs.getObject("additional_details")).build();
    }
}
