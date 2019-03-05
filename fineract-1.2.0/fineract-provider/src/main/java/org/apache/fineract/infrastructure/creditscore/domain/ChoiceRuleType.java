package org.apache.fineract.infrastructure.creditscore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "choice_rule_type")
public class ChoiceRuleType extends AbstractPersistableCustom<Integer> {

    // @Column(name="id")
    // private int id;

    @Column(name="scoring_rule_id")
    private long scoringRuleId;

    @Column(name="choice_name")
    private String choiceName;

    @Column(name="relative_value")
    private BigDecimal relativeValue;
    
    public ChoiceRuleType(int scoringRuleId, String choiceName, BigDecimal relativeValue) {
        this.scoringRuleId = scoringRuleId;
        this.choiceName = choiceName;
        this.relativeValue = relativeValue;
    }
    
    public ChoiceRuleType(String choiceName, BigDecimal relativeValue) {
        this.choiceName = choiceName;
        this.relativeValue = relativeValue;
    }
    
    public ChoiceRuleType() {
        
    }
    
    public static ChoiceRuleType fromJson(JsonCommand command) {
        
        final int scoringRuleId = command.integerValueOfParameterNamed("scoringRuleId");
        final String choiceName = command.stringValueOfParameterNamed("choiceName");
        final BigDecimal relativeValue = command.bigDecimalValueOfParameterNamed("relativeValue");
        
        return new ChoiceRuleType(scoringRuleId, choiceName, relativeValue);
    }

    // public int getChoiceRuleId() {
    //     return this.id;
    // }

    // public void setId(int id) {
    //     this.id = id;
    // }

    public long getScoringRuleId() {
        return this.scoringRuleId;
    }

    public void setScoringRuleId(long scoringRuleId) {
        this.scoringRuleId = scoringRuleId;
    }

    public String getChoiceName() {
        return this.choiceName;
    }

    public void setChoiceName(String choiceName) {
        this.choiceName = choiceName;
    }

    public BigDecimal getRelativeValue() {
        return this.relativeValue;
    }

    public void setRelativeValue(BigDecimal relativeValue) {
        this.relativeValue = relativeValue;
    }
}