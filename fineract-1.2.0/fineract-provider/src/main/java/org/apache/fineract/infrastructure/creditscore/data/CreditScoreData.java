package org.apache.fineract.infrastructure.creditscore.data;

import java.util.ArrayList;
import java.util.Collection;

public class CreditScoreData {

    private final int id;
    private final String ruleName;
    private final int weightedValue;
    private final int ruleType;
    private Collection<RuleTypeData> ruleTypeDataList = new ArrayList<>();
    private final String status;
    private final String tag;
    private final String createdBy;
    private final String createdDate;
    private final String updatedBy;
    private final String updatedDate;
    
    public CreditScoreData(int id, String ruleName, int weightedValue, int ruleType, Collection<RuleTypeData> ruleTypeDataList, String status, String tag, 
            String createdBy, String createdDate, String updatedBy, String updatedDate) {
        this.id = id;
        this.ruleName = ruleName;
        this.weightedValue = weightedValue;
        this.ruleType = ruleType;
        this.ruleTypeDataList = ruleTypeDataList;
        this.status = status;
        this.tag = tag;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }
    
    public CreditScoreData(CreditScoreData creditScoreData) {
        this.id = creditScoreData.getId();
        this.ruleName = creditScoreData.getRuleName();
        this.weightedValue = creditScoreData.getWeightedValue();
        this.ruleType = creditScoreData.getRuleType();
        this.status = creditScoreData.getStatus();
        this.tag = creditScoreData.getTag();
        this.createdBy = creditScoreData.getCreatedBy();
        this.createdDate = creditScoreData.getCreatedDate();
        this.updatedBy = creditScoreData.getUpdatedBy();
        this.updatedDate = creditScoreData.getUpdatedDate();
    }
    
    public static CreditScoreData instance(final int id, final String ruleName, final int weightedValue, final int ruleType, 
            final Collection<RuleTypeData> ruleTypeDataList, final String status, final String tag, final String createdBy, final String createdDate,
            final String updatedBy, final String updatedDate) {
        return new CreditScoreData(id, ruleName, weightedValue, ruleType, ruleTypeDataList, status, tag, createdBy, createdDate, updatedBy, updatedDate);
    }

    public int getId() {
        return this.id;
    }
    
    public String getRuleName() {
        return this.ruleName;
    }
    
    public int getWeightedValue() {
        return this.weightedValue;
    }
    
    public int getRuleType() {
        return this.ruleType;
    }
    
    public Collection<RuleTypeData> getRuleTypeDataList() {
        return this.ruleTypeDataList;
    }
    
    public void setRuleTypeDataList(Collection<RuleTypeData> ruleTypeDataList) {
        this.ruleTypeDataList = ruleTypeDataList;
    }

    public String getStatus() {
        return this.status;
    }
        
    public String getTag() {
        return this.tag;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }
    
    public String getUpdatedBy() {
        return this.updatedBy;
    }
    
    public String getUpdatedDate() {
        return this.updatedDate;
    }
    
}
