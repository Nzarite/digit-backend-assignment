package digit.academy.tutorial.repository.rowmapper;

import digit.academy.tutorial.web.models.Advocate;
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
public class AdvocateRowMapper implements ResultSetExtractor<List<Advocate>> {

    /**
     * This method extracts data from the ResultSet and maps it into a list of Advocate objects.
     * It processes the ResultSet row by row, creating Advocate objects and adding documents
     * associated with each advocate if available.
     *
     * @param rs The ResultSet containing the database records.
     * @return A list of Advocate objects, each containing their respective documents.
     * @throws SQLException        If there is an issue while accessing the ResultSet.
     * @throws DataAccessException If there is a problem with data access.
     */
    @Override
    public List<Advocate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Advocate> advocateRegistrationMap = new LinkedHashMap<>();

        while (rs.next()) {
            String applicationNumber = rs.getString("application_number");
            Advocate advocate = advocateRegistrationMap.get(applicationNumber);

            if (advocate == null) {
                Long lastModifiedTime = rs.getLong("last_modified_time");
                if (rs.wasNull()) {
                    lastModifiedTime = null;
                }

                AuditDetails auditdetails = AuditDetails.builder().createdBy(rs.getString("created_by"))
                        .createdTime(rs.getLong("created_time")).lastModifiedBy(rs.getString("last_modified_by"))
                        .lastModifiedTime(lastModifiedTime).build();

                advocate = Advocate.builder().applicationNumber(applicationNumber).tenantId(rs.getString("tenant_id"))
                        .id(rs.getString("adv_id")).barRegistrationNumber(rs.getString("bar_registration_number"))
                        .advocateType(rs.getString("advocate_type")).organisationID(rs.getString("organisation_id"))
                        .individualId(rs.getString("individual_id")).isActive(rs.getBoolean("is_active"))
                        .auditDetails(auditdetails).additionalDetails(rs.getObject("additional_details"))
                        .documents(new ArrayList<>()).build();

                advocateRegistrationMap.put(applicationNumber, advocate);
            }

            // Add Document
            String documentId = rs.getString("doc_id");
            if (!ObjectUtils.isEmpty(documentId)) {
                advocate.addDocumentsItem(getDocument(rs));
            }
        }
        return new ArrayList<>(advocateRegistrationMap.values());
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
