package org.apache.fineract.infrastructure.creditscore.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;


public interface ChoiceRuleTypeRepository extends JpaRepository<ChoiceRuleType, Integer>, JpaSpecificationExecutor<ChoiceRuleType> {

	List<ChoiceRuleType> findByScoringRuleId(int scoringRuleId);
}
