package org.apache.fineract.infrastructure.creditscore.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;

public interface CreditScoreFormulaWritePlatformService {
    
    CommandProcessingResult addCreditScoreFormula(JsonCommand command);
    
    CommandProcessingResult editCreditScoreFormula(final long id, JsonCommand command);

}