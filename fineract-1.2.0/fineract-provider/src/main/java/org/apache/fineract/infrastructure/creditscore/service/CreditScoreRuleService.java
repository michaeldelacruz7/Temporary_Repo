package org.apache.fineract.infrastructure.creditscore.service;

import java.util.Collection;

import org.apache.fineract.infrastructure.creditscore.data.CreditScoreConfigurationData;
import org.apache.fineract.infrastructure.creditscore.data.RuleTypeData;

public interface CreditScoreRuleService {
    
    public Collection<CreditScoreConfigurationData> getScoringRuleList();
    
    public CreditScoreConfigurationData getScoringRule(int id);
    
    public int addScoringRule(CreditScoreConfigurationData scoringRuleData);
    
    public int editScoringRule(CreditScoreConfigurationData scoringRuleData);
    
    public int deleteScoringRule(int id);
    
    public Collection<RuleTypeData> getRuleTypeDataList(int id, int ruleType);
    
    public RuleTypeData getRuleTypeData(int id, int ruleType);

}
