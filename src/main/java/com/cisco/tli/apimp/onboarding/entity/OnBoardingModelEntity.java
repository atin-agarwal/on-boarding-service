/**
 * 
 */
package com.cisco.tli.apimp.onboarding.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author atiagarw
 *
 */
@Document(value = "model_inventory")
@Setter
@Getter
@ToString
public class OnBoardingModelEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5205712261104043682L;
	
	@Id
	@Field("_id")
	private String id;

	private String name;
	
	private String modelFileName;
	
	private String modelFileExtension;
	
	private Long modelFileSize;
	
	private String modelFileLocation;
	
	private String inputFileName;
	
	private String inputFileExtension;
	
	private Long inputFileSize;
	
	private String inputFileLocation;
	
	private String sampleOutput;
	
	private Date createdDate;
	
	private Date modifiedDate;
	

}
