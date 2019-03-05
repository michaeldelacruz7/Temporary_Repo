package org.apache.fineract.infrastructure.creditscore.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.creditscore.domain.CreditScore;
import org.apache.fineract.infrastructure.creditscore.domain.RangeRuleType;
import org.apache.fineract.infrastructure.creditscore.domain.ChoiceRuleType;
import org.apache.fineract.infrastructure.creditscore.domain.CreditScoreRepository;
import org.apache.fineract.infrastructure.creditscore.domain.RangeRuleTypeRepository;
import org.apache.fineract.infrastructure.creditscore.domain.ChoiceRuleTypeRepository;
import org.apache.fineract.infrastructure.creditscore.serialization.CreditScoreCommandFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
public class CreditScoreWritePlatformServiceImpl implements CreditScoreWritePlatformService {
    
    private final PlatformSecurityContext context;
    private final CreditScoreRepository repository;
    private final RangeRuleTypeRepository rangeRepository;
    private final ChoiceRuleTypeRepository choiceRepository;
    private final CreditScoreCommandFromApiJsonDeserializer deserializer;

    @Autowired
    public CreditScoreWritePlatformServiceImpl(PlatformSecurityContext context, CreditScoreRepository repository,
            RangeRuleTypeRepository rangeRepository, ChoiceRuleTypeRepository choiceRepository, 
            CreditScoreCommandFromApiJsonDeserializer deserializer) {
        this.context = context;
        this.repository = repository;
        this.rangeRepository = rangeRepository;
        this.choiceRepository = choiceRepository;
        this.deserializer = deserializer;
    }

    @Transactional
    @Override
    public CommandProcessingResult addCreditScoreRule(JsonCommand command) {
        this.context.authenticatedUser();
        this.deserializer.validateForCreate(command.json());
        final CreditScore creditScore = CreditScore.fromJson(command);
        this.repository.save(creditScore);
        int id = creditScore.getId().intValue();
        if(null != creditScore.getRangeRuleTypeList() && !creditScore.getRangeRuleTypeList().isEmpty()){
            for(int i = 0; i < creditScore.getRangeRuleTypeList().size(); i++){
                creditScore.getRangeRuleTypeList().get(i).setScoringRuleId(id);
                this.rangeRepository.save(creditScore.getRangeRuleTypeList().get(i));
            }
        }else if(null != creditScore.getChoiceRuleTypeList() && !creditScore.getChoiceRuleTypeList().isEmpty()){
            for(int i = 0; i < creditScore.getChoiceRuleTypeList().size(); i++){
                creditScore.getChoiceRuleTypeList().get(i).setScoringRuleId(id);
                this.choiceRepository.save(creditScore.getChoiceRuleTypeList().get(i));
            }
        }
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(creditScore.getId()).build();
    }

    @Transactional
    @Override
    public CommandProcessingResult editCreditScoreRule(final int scoringRuleId, JsonCommand command) {
        this.context.authenticatedUser();
        this.deserializer.validateForUpdate(scoringRuleId, command.json());
        final CreditScore creditScoreToUpdate = this.repository.getOne(scoringRuleId);
        final CreditScore creditScore = CreditScore.fromJson(command);
        if(!creditScoreToUpdate.getRuleName().equals(creditScore.getRuleName())){
            creditScoreToUpdate.setRuleName(creditScore.getRuleName());
        }
        if(creditScoreToUpdate.getRuleType() != creditScore.getRuleType()){
            creditScoreToUpdate.setRuleType(creditScore.getRuleType());
        }
        if(creditScoreToUpdate.getWeightedValue() != creditScore.getWeightedValue()){
            creditScoreToUpdate.setWeightedValue(creditScore.getWeightedValue());
        }
        if(!creditScoreToUpdate.getStatus().equals(creditScore.getStatus())){
            creditScoreToUpdate.setStatus(creditScore.getStatus());
        }
        if(!creditScoreToUpdate.getTag().equals(creditScore.getTag())){
            creditScoreToUpdate.setTag(creditScore.getTag());
        }
        this.repository.saveAndFlush(creditScoreToUpdate);
        JsonArray ruleTypeDataList = command.arrayOfParameterNamed("ruleTypeDataList");
        if(1 == creditScoreToUpdate.getRuleType()){
            List<RangeRuleType> rangeRules = this.rangeRepository.findByScoringRuleId(scoringRuleId);
            List<RangeRuleType> rangeToDelete = new ArrayList<RangeRuleType>();
            for(int i = 0; i < rangeRules.size(); i++){
                int ctr = 0;
                for(int rule = 0; rule < ruleTypeDataList.size(); rule++){
                    final JsonObject jsonObject = ruleTypeDataList.get(rule).getAsJsonObject();
                    if(jsonObject.has("id")){
                        if(rangeRules.get(i).getId() == jsonObject.get("id").getAsLong()){
                            RangeRuleType rangeRule = rangeRules.get(i);
                            RangeRuleType object = creditScore.getRangeRuleTypeList().get(rule);
                            rangeRule.setMinValue(object.getMinValue());
                            rangeRule.setMaxValue(object.getMaxValue());
                            rangeRule.setRelativeValue(object.getRelativeValue());
                            this.rangeRepository.saveAndFlush(rangeRule);
                            break;
                        }else{
                            ctr++;
                        }
                    }else{
                        ctr++;
                    }
                }
                if(ctr == ruleTypeDataList.size()){
                    rangeToDelete.add(rangeRules.get(i));
                }
            }
            this.rangeRepository.deleteInBatch(rangeToDelete);
            for(int i = 0; i < ruleTypeDataList.size(); i++){
                final JsonObject jsonObject = ruleTypeDataList.get(i).getAsJsonObject();
                if(!jsonObject.has("id")){
                    RangeRuleType object = creditScore.getRangeRuleTypeList().get(i);
                    object.setScoringRuleId(scoringRuleId);
                    this.rangeRepository.save(object);
                }
            }
        }else{
            List<ChoiceRuleType> choiceRules = this.choiceRepository.findByScoringRuleId(scoringRuleId);
            List<ChoiceRuleType> choiceToDelete = new ArrayList<ChoiceRuleType>();
            for(int i = 0; i < choiceRules.size(); i++){
                int ctr = 0;
                for(int rule = 0; rule < ruleTypeDataList.size(); rule++){
                    final JsonObject jsonObject = ruleTypeDataList.get(rule).getAsJsonObject();
                    if(jsonObject.has("id")){
                        if(choiceRules.get(i).getId() == jsonObject.get("id").getAsLong()){
                            ChoiceRuleType choiceRule = choiceRules.get(i);
                            ChoiceRuleType object = creditScore.getChoiceRuleTypeList().get(rule);
                            choiceRule.setChoiceName(object.getChoiceName());
                            choiceRule.setRelativeValue(object.getRelativeValue());
                            this.choiceRepository.saveAndFlush(choiceRule);
                            break;
                        }else{
                            ctr++;
                        }
                    }else{
                        ctr++;
                    }
                }
                if(ctr == ruleTypeDataList.size()){
                    choiceToDelete.add(choiceRules.get(i));
                }
            }
            this.choiceRepository.deleteInBatch(choiceToDelete);
            for(int i = 0; i < ruleTypeDataList.size(); i++){
                final JsonObject jsonObject = ruleTypeDataList.get(i).getAsJsonObject();
                if(!jsonObject.has("id")){
                    ChoiceRuleType object = creditScore.getChoiceRuleTypeList().get(i);
                    object.setScoringRuleId(scoringRuleId);
                    this.choiceRepository.save(object);
                }
            }
        }
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(creditScoreToUpdate.getId()).build();
    }

    @Transactional
    @Override
    public CommandProcessingResult editCreditScoreRuleStatus(final int scoringRuleId, JsonCommand command) {
        this.context.authenticatedUser();
        this.deserializer.validateForUpdateStatus(scoringRuleId, command.json());
        final CreditScore creditScoreToUpdate = this.repository.getOne(scoringRuleId);
        final CreditScore creditScore = CreditScore.forUpdateStatus(command);
        if(!creditScoreToUpdate.getStatus().equals(creditScore.getStatus())){
            creditScoreToUpdate.setStatus(creditScore.getStatus());
        }
        this.repository.saveAndFlush(creditScoreToUpdate);
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(creditScoreToUpdate.getId()).build();
    }
}
