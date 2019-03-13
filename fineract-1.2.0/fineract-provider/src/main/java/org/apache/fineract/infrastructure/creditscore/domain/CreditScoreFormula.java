package org.apache.fineract.infrastructure.creditscore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "formula")
public class CreditScoreFormula extends AbstractPersistableCustom<Integer> {
    
    @Column(name = "formula_name")
    private String formulaName;

    @Column(name = "formula")
    private String formula;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private String updatedDate;
    
    public CreditScoreFormula() {
        
    }
    
    public CreditScoreFormula(String status) {
        this.status = status;
    }
    
    public CreditScoreFormula(String formulaName, String formula, String status, String createdBy, String createdDate, String updatedBy, String updatedDate) {
        this.formulaName = formulaName;
        this.formula = formula;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }
    
    public static CreditScoreFormula fromJson(final JsonCommand command) {
        String formulaName = command.stringValueOfParameterNamed("formulaName");
        String formula = command.stringValueOfParameterNamed("formula");
        String status = command.stringValueOfParameterNamed("status");
        return new CreditScoreFormula(formulaName, formula, status, null, null, null, null);
    }

    public String getFormulaName() {
        return this.formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}