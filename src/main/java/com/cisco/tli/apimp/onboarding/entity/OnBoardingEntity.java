/**
 * 
 */
package com.cisco.tli.apimp.onboarding.entity;

import java.io.Serializable;
import java.util.List;

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
@Document(value = "api_inventory")
@Setter
@Getter
@ToString
public class OnBoardingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1717400180040895071L;

	@Id
	@Field("_id")
	private String id;

	private String name;

	private String description;

	private String category;

	private String baseURL;

	private String proxyHeaderValue;

	private boolean authHeaderReq;

	private String authHeaderName;

	private List<EndPoint> endPoints;

}

@Setter
@Getter
@ToString
class EndPoint {

	private String name;

	private String description;

	private String path;

	private String method;

	// Need to review - blob
	private String sampleResponse;

	private List<Header> headers;

	private List<QueryParam> queryParams;

	private List<BodyParam> bodyParams;

}

@Setter
@Getter
@ToString
class Header {

	private String name;

	private String description;

	private boolean optional;

	private String type;

	private String defaultValue;

}

@Setter
@Getter
@ToString
class QueryParam {
	
	private String name;

	private String description;

	private boolean optional;

	private String type;

	private String defaultValue;
}

@Setter
@Getter
@ToString
class BodyParam {
	
	private String name;

	private String description;

	private boolean optional;

	private String type;

	private String defaultValue;
}
