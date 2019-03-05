package org.apache.fineract.infrastructure.creditscore.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;


public interface RangeRuleTypeRepository extends JpaRepository<RangeRuleType, Integer>, JpaSpecificationExecutor<RangeRuleType> {

	List<RangeRuleType> findByScoringRuleId(int scoringRuleId);
}
