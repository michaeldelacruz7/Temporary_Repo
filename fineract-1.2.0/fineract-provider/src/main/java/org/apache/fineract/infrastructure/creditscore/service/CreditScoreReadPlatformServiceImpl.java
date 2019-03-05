package org.apache.fineract.infrastructure.creditscore.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.math.BigDecimal;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.creditscore.data.CreditScoreData;
import org.apache.fineract.infrastructure.creditscore.data.RuleTypeData;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreReadPlatformServiceImpl implements CreditScoreReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;

    @Autowired
    public CreditScoreReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource) {
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.context = context;
    }
    
    private static final class CreditScoreMapper implements RowMapper<CreditScoreData> {
        public String schema() {
            return " * FROM scoring_rule";
        }

        @Override
        public CreditScoreData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final int id = rs.getInt("id");
            final String ruleName = rs.getString("rule_name");
            final int weightedValue = rs.getInt("weighted_value");
            final int ruleType = rs.getInt("rule_type");
            final String status = rs.getString("status");
            final String tag = rs.getString("tag");
            final String createdBy = rs.getString("created_by");
            final String createdDate = rs.getString("created_date");
            final String updatedBy = rs.getString("updated_by");
            final String updatedDate = rs.getString("updated_date");
            return CreditScoreData.instance(id, ruleName, weightedValue, ruleType, null, status, tag, createdBy, createdDate, updatedBy, updatedDate);
        }
    }
    
    private static final class RangeRuleTypeMapper implements RowMapper<RuleTypeData> {
        @Override
        public RuleTypeData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final int id = rs.getInt("id");
            final int scoringRuleId = rs.getInt("scoring_rule_id");
            final int minValue = rs.getInt("min_value");
            final int maxValue = rs.getInt("max_value");
            final BigDecimal relativeValue = rs.getBigDecimal("relative_value");
            return RuleTypeData.instance(id, scoringRuleId, minValue, maxValue, null, relativeValue);
        }
        
    }
    
    private static final class ChoiceRuleTypeMapper implements RowMapper<RuleTypeData> {
        @Override
        public RuleTypeData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final int id = rs.getInt("id");
            final int scoringRuleId = rs.getInt("scoring_rule_id");
            final String choiceName = rs.getString("choice_name");
            final BigDecimal relativeValue = rs.getBigDecimal("relative_value");
            return RuleTypeData.instance(id, scoringRuleId, 0, 0, choiceName, relativeValue);
        }
        
    }

    @Override
    public Collection<CreditScoreData> getCreditScoreList() {
        this.context.authenticatedUser();
        final CreditScoreMapper creditScoreMapper = new CreditScoreMapper();
        final String sql = "SELECT" + creditScoreMapper.schema();
        Collection<CreditScoreData> tempCreditScoreDataList = this.jdbcTemplate.query(sql, creditScoreMapper, new Object[] {});
        Collection<CreditScoreData> creditScoreDataList = new ArrayList<>();
        for(CreditScoreData creditScoreData : tempCreditScoreDataList) {
            CreditScoreData tempCreditScoreData = new CreditScoreData(creditScoreData);
            tempCreditScoreData.setRuleTypeDataList(this.getRuleTypeDataList(tempCreditScoreData.getRuleType(), tempCreditScoreData.getId()));
            creditScoreDataList.add(tempCreditScoreData);
        }
        return creditScoreDataList;
    }

    @Override
    public CreditScoreData getCreditScoreById(int id) {
        this.context.authenticatedUser();
        final CreditScoreMapper creditScoreMapper = new CreditScoreMapper();
        final String sql = "SELECT" + creditScoreMapper.schema() + " WHERE id = ?";
        CreditScoreData tempCreditScoreData = this.jdbcTemplate.queryForObject(sql, creditScoreMapper, new Object[] {id});
        CreditScoreData creditScoreData = new CreditScoreData(tempCreditScoreData);
        creditScoreData.setRuleTypeDataList(this.getRuleTypeDataList(tempCreditScoreData.getRuleType(), tempCreditScoreData.getId()));
        return creditScoreData;
    }

    @Override
    public Collection<RuleTypeData> getRuleTypeDataList(int ruleType, int scoringRuleId) {
        this.context.authenticatedUser();
        Collection<RuleTypeData> ruleTypeDataList = null;
        String sql = "";
        if(ruleType == 1) {
            final RangeRuleTypeMapper rangeRuleTypeMapper = new RangeRuleTypeMapper();
            sql = "SELECT * FROM range_rule_type WHERE scoring_rule_id = ?";
            ruleTypeDataList =  this.jdbcTemplate.query(sql, rangeRuleTypeMapper, new Object[] {scoringRuleId});
        } else {
            final ChoiceRuleTypeMapper choiceRuleTypeMapper = new ChoiceRuleTypeMapper();
            sql = "SELECT * FROM choice_rule_type WHERE scoring_rule_id = ?";
            ruleTypeDataList =  this.jdbcTemplate.query(sql, choiceRuleTypeMapper, new Object[] {scoringRuleId});
        }
        return ruleTypeDataList;
    }

    @Override
    public RuleTypeData getRuleTypeData(int ruleType, int id) {
        this.context.authenticatedUser();
        RuleTypeData ruleTypeData = null;
        String sql = "";
        if(ruleType == 1) {
            final RangeRuleTypeMapper rangeRuleTypeMapper = new RangeRuleTypeMapper();
            sql = "SELECT * FROM range_rule_type WHERE id = ?";
            ruleTypeData = this.jdbcTemplate.queryForObject(sql, rangeRuleTypeMapper, new Object[] {id});
        } else {
            final ChoiceRuleTypeMapper choiceRuleTypeMapper = new ChoiceRuleTypeMapper();
            sql = "SELECT * FROM choice_rule_type WHERE id = ?";
            ruleTypeData = this.jdbcTemplate.queryForObject(sql, choiceRuleTypeMapper, new Object[] {id});
        }
        return ruleTypeData;
    }

}
