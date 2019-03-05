package org.apache.fineract.infrastructure.creditscore.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;

public interface CreditScoreWritePlatformService {
    
    CommandProcessingResult addCreditScoreRule(JsonCommand command);
    
    CommandProcessingResult editCreditScoreRule(final int scoringRuleId, JsonCommand command);
    
    CommandProcessingResult editCreditScoreRuleStatus(final int scoringRuleId, JsonCommand command);

}
