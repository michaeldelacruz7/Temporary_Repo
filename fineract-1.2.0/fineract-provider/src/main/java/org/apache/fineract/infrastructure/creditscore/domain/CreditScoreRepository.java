package org.apache.fineract.infrastructure.creditscore.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CreditScoreRepository extends JpaRepository<CreditScore, Integer>, JpaSpecificationExecutor<CreditScore> {

}
