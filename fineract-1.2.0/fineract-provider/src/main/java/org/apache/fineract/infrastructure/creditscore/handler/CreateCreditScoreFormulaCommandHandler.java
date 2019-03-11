package org.apache.fineract.infrastructure.creditscore.handler;

import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.creditscore.service.CreditScoreFormulaWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommandType(entity = "CREDIT_SCORE_FORMULA", action = "CREATE")
public class CreateCreditScoreFormulaCommandHandler implements NewCommandSourceHandler {
    
    private final CreditScoreFormulaWritePlatformService service;
    
    @Autowired
    public CreateCreditScoreFormulaCommandHandler(CreditScoreFormulaWritePlatformService service) {
        this.service = service;
    }

    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        
        return this.service.addCreditScoreFormula(command);
    }

}
