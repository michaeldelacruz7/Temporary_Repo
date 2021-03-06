package org.apache.fineract.infrastructure.creditscore.handler;

import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.creditscore.service.CreditScoreWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommandType(entity = "CREDIT_SCORE", action = "CREATE")
public class CreateCreditScoreCommandHandler implements NewCommandSourceHandler {

    private final CreditScoreWritePlatformService writePlatformService;
    
    @Autowired
    public CreateCreditScoreCommandHandler(CreditScoreWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }

    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.writePlatformService.addCreditScoreRule(command);
    }

}
