/**
 * 
 */
package com.cisco.tli.apimp.onboarding.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cisco.tli.apimp.onboarding.entity.OnBoardingEntity;

/**
 * @author atiagarw
 *
 */
@Repository
public interface OnBoardingRepository extends MongoRepository<OnBoardingEntity, String> {

}
