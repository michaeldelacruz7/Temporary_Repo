package org.apache.fineract.infrastructure.creditscore.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.creditscore.data.CreditScoreFormulaData;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CreditScoreFormulaReadPlatformServiceImpl implements CreditScoreFormulaReadPlatformService {
    
    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    
    @Autowired
    public CreditScoreFormulaReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.context = context;
    }
    
    private static final class CreditScoreFormulaMapper implements RowMapper<CreditScoreFormulaData> {
        public String schema() {
            return " * FROM formula";
        }

        @Override
        public CreditScoreFormulaData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final int id = rs.getInt("id");
            final String formulaName = rs.getString("formula_name");
            final String formula = rs.getString("formula");
            final String status = rs.getString("status");
            final String createdBy = rs.getString("created_by");
            final String createdDate = rs.getString("created_date");
            final String updatedBy = rs.getString("updated_by");
            final String updatedDate = rs.getString("updated_date");
            return CreditScoreFormulaData.instance(id, formulaName, formula, status, createdBy, createdDate, updatedBy, updatedDate);
        }
    }

    @Override
    public Collection<CreditScoreFormulaData> getCreditScoreFormulaDataList() {
        this.context.authenticatedUser();
        final CreditScoreFormulaMapper mapper = new CreditScoreFormulaMapper();
        final String sql = "SELECT" + mapper.schema();
        Collection<CreditScoreFormulaData> dataList = this.jdbcTemplate.query(sql, mapper, new Object[] {});
        return dataList;
    }

    @Override
    public CreditScoreFormulaData getCreditScoreFormulaDataById(long id) {
        this.context.authenticatedUser();
        final CreditScoreFormulaMapper mapper = new CreditScoreFormulaMapper();
        final String sql = "SELECT" + mapper.schema() + " WHERE id = ?";
        CreditScoreFormulaData data = this.jdbcTemplate.queryForObject(sql, mapper, new Object[] {id});
        return data;
    }

}
