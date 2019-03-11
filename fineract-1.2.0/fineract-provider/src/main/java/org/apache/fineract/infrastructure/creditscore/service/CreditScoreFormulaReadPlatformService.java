package org.apache.fineract.infrastructure.creditscore.service;

import java.util.Collection;

import org.apache.fineract.infrastructure.creditscore.data.CreditScoreFormulaData;

public interface CreditScoreFormulaReadPlatformService {
    
    Collection<CreditScoreFormulaData> getCreditScoreFormulaDataList();
    
    CreditScoreFormulaData getCreditScoreFormulaDataById(long id);

}