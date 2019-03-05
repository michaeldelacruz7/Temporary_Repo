package org.apache.fineract.infrastructure.creditscore.data;


import java.math.BigDecimal;

public class RuleTypeData {
    
    private final int id;
    private final int scoringRuleId;
    private final int minValue;
    private final int maxValue;
    private final String choiceName;
    private final BigDecimal relativeValue;

    public RuleTypeData(int id, int scoringRuleId, int minValue, int maxValue, String choiceName, BigDecimal relativeValue) {
        super();
        this.id = id;
        this.scoringRuleId = scoringRuleId;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.choiceName = choiceName;
        this.relativeValue = relativeValue;
    }
    
    public static RuleTypeData instance(final int id, final int scoringRuleId, final int minValue, final int maxValue, final String choiceName,
            final BigDecimal relativeValue) {
        return new RuleTypeData(id, scoringRuleId, minValue, maxValue, choiceName, relativeValue);
    }

    public int getId() {
        return this.id;
    }

    public int getScoringRuleId() {
        return this.scoringRuleId;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public String getChoiceName() {
        return this.choiceName;
    }

    public BigDecimal getRelativeValue() {
        return this.relativeValue;
    }
    
}