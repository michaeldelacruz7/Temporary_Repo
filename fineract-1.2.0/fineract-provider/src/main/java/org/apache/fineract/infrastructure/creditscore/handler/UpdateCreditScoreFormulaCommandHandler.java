package org.apache.fineract.infrastructure.creditscore.handler;

import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.creditscore.service.CreditScoreFormulaWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommandType(entity = "CREDIT_SCORE_FORMULA", action = "UPDATE")
public class UpdateCreditScoreFormulaCommandHandler implements NewCommandSourceHandler {
    
    private final CreditScoreFormulaWritePlatformService service;
    
    @Autowired
    public UpdateCreditScoreFormulaCommandHandler(CreditScoreFormulaWritePlatformService service) {
        this.service = service;
    }

    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        
        return this.service.editCreditScoreFormula(command.getFormulaId(), command);
    }

}
