package org.apache.fineract.infrastructure.creditscore.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.math.BigDecimal;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.creditscore.data.CreditScoreConfigurationData;
import org.apache.fineract.infrastructure.creditscore.data.RuleTypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Service
public class CreditScoreRuleServiceImpl implements CreditScoreRuleService {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<CreditScoreConfigurationData> scoringRuleDataRowMapper;
    private final RowMapper<RuleTypeData> ruleTypeDataRowMapper;

    @Autowired
    public CreditScoreRuleServiceImpl(final RoutingDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.scoringRuleDataRowMapper = new ScoringRuleRowMapper();
        this.ruleTypeDataRowMapper = new RuleTypeRowMapper();
    }
    
    @Override
    public Collection<CreditScoreConfigurationData> getScoringRuleList() {
        String sql = "SELECT * FROM scoring_rule";
        final Collection<CreditScoreConfigurationData> scoringRuleDataTempList =  this.jdbcTemplate.query(sql, this.scoringRuleDataRowMapper, new Object[] {});
        final Collection<CreditScoreConfigurationData> scoringRuleDataList = new ArrayList<>();
        for(CreditScoreConfigurationData scoringRuleData : scoringRuleDataTempList) {
            scoringRuleDataList.add(new CreditScoreConfigurationData(scoringRuleData.getId(), scoringRuleData.getRuleName(), scoringRuleData.getWeightedValue(),
                    scoringRuleData.getRuleType(), this.getRuleTypeData(scoringRuleData.getId(), scoringRuleData.getRuleType()), scoringRuleData.getStatus()));
        }
        return scoringRuleDataList;
    }

    @Override
    public CreditScoreConfigurationData getScoringRule(int id) {
        String sql = "SELECT * FROM scoring_rule WHERE id = ?";
        final CreditScoreConfigurationData scoringRuleData =  this.jdbcTemplate.queryForObject(sql, this.scoringRuleDataRowMapper, new Object[] {id});
        return scoringRuleData;
    }

    @Override
    public int addScoringRule(CreditScoreConfigurationData scoringRuleData) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int editScoringRule(CreditScoreConfigurationData scoringRuleData) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int deleteScoringRule(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

  
    private static final class ScoringRuleRowMapper implements RowMapper<CreditScoreConfigurationData> {

        @Override
        public CreditScoreConfigurationData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
                throws SQLException {

            final int id = rs.getInt("id");
            final String ruleName = rs.getString("rule_name");
            final int weightedValue = rs.getInt("weighted_value");
            final int ruleType = rs.getInt("rule_type");
            final int status = rs.getInt("status");
            
            return new CreditScoreConfigurationData(id, ruleName, weightedValue, ruleType, null, status);
        }
    }


    @Override
    public Collection<RuleTypeData> getRuleTypeDataList(int id, int ruleType) {
        String sql = "";
        if(ruleType == 1) {
            sql = "SELECT * FROM range_rule_type WHERE scoring_rule_id = ?";
        } else if(ruleType == 2) {
            sql = "SELECT * FROM choice_rule_type WHERE scoring_rule_id = ?";
        }
        final Collection<RuleTypeData> ruleTypeDataList =  this.jdbcTemplate.query(sql, this.ruleTypeDataRowMapper, new Object[] {id});
        return ruleTypeDataList;
    }

    @Override
    public RuleTypeData getRuleTypeData(int id, int ruleType) {
        String sql = "";
        if(ruleType == 1) {
            sql = "SELECT * FROM range_rule_type WHERE id = ?";
        } else if(ruleType == 2) {
            sql = "SELECT * FROM choice_rule_type WHERE id = ?";
        }
        final RuleTypeData ruleTypeData =  this.jdbcTemplate.queryForObject(sql, this.ruleTypeDataRowMapper, new Object[] {id});
        return ruleTypeData;
    }
    
    private static final class RuleTypeRowMapper implements RowMapper<RuleTypeData> {

        @Override
        public RuleTypeData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            
            final int id = rs.getInt("id");
            final int scoringRuleId = rs.getInt("scoring_rule_id");
            final int minValue = rs.getInt("min_value");
            final int maxValue = rs.getInt("max_value");
            final String choiceName = rs.getString("choice_name");
            final BigDecimal relativeValue = rs.getBigDecimal("relative_value");
            
            return new RuleTypeData(id, scoringRuleId, minValue, maxValue, choiceName, relativeValue);
        }
        
    }

}
