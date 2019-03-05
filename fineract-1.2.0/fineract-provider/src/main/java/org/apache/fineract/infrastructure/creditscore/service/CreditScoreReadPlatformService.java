package org.apache.fineract.infrastructure.creditscore.service;

import java.util.Collection;

import org.apache.fineract.infrastructure.creditscore.data.CreditScoreData;
import org.apache.fineract.infrastructure.creditscore.data.RuleTypeData;

public interface CreditScoreReadPlatformService {
    
    Collection<CreditScoreData> getCreditScoreList();
    
    CreditScoreData getCreditScoreById(int id);
    
    Collection<RuleTypeData> getRuleTypeDataList(int ruleType, int scoringRuleId);
    
    RuleTypeData getRuleTypeData(int ruleType, int id);

}
