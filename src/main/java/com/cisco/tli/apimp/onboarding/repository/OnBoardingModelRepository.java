/**
 * 
 */
package com.cisco.tli.apimp.onboarding.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cisco.tli.apimp.onboarding.entity.OnBoardingModelEntity;

/**
 * @author atiagarw
 *
 */
public interface OnBoardingModelRepository extends MongoRepository<OnBoardingModelEntity, String> {

}
