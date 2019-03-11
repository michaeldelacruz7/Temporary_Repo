package org.apache.fineract.infrastructure.creditscore.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.creditscore.domain.CreditScoreFormula;
import org.apache.fineract.infrastructure.creditscore.domain.CreditScoreFormulaRepository;
import org.apache.fineract.infrastructure.creditscore.serialization.CreditScoreFormulaCommandFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditScoreFormulaWritePlatformServiceImpl implements CreditScoreFormulaWritePlatformService {
    
    private final PlatformSecurityContext context;
    private final CreditScoreFormulaRepository repository;
    private final CreditScoreFormulaCommandFromApiJsonDeserializer deserializer;
    
    @Autowired
    public CreditScoreFormulaWritePlatformServiceImpl(PlatformSecurityContext context, CreditScoreFormulaRepository repository,
            CreditScoreFormulaCommandFromApiJsonDeserializer deserializer) {
        this.context = context;
        this.repository = repository;
        this.deserializer = deserializer;
    }

    @Transactional
    @Override
    public CommandProcessingResult addCreditScoreFormula(JsonCommand command) {
        this.context.authenticatedUser();
        this.deserializer.validateForCreate(command.json());
        
        final CreditScoreFormula creditScoreFormula = CreditScoreFormula.fromJson(command);
        
        this.repository.save(creditScoreFormula);
        
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(creditScoreFormula.getId()).build();
    }

    @Transactional
    @Override
    public CommandProcessingResult editCreditScoreFormula(long id, JsonCommand command) {
        this.context.authenticatedUser();
        this.deserializer.validateForUpdate(id, command.json());
        
        final String formulaName = command.stringValueOfParameterNamed("formulaName");
        final String formula = command.stringValueOfParameterNamed("formula");
        final String status = command.stringValueOfParameterNamed("status");
        
        final CreditScoreFormula creditScoreFormula = this.repository.getOne(id);
        
        creditScoreFormula.setFormulaName(formulaName);
        creditScoreFormula.setFormula(formula);
        creditScoreFormula.setStatus(status);
        
        this.repository.saveAndFlush(creditScoreFormula);
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(creditScoreFormula.getId()).build();
    }

}
