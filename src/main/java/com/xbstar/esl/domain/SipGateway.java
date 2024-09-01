package com.xbstar.esl.domain;

import javax.persistence.Id;

/**
 * @Description:
 * @Class:SipGateway.java
 * @Author:janus
 * @Date:2024年8月31日下午11:58:22
 * @Version:1.0.0
 */
public class SipGateway {
	@Id
	private Long id;
	private String gwName;
	private String gwIP;
	private String gwType;
	
	
	public String getGwName() {
		return gwName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setGwName(String gwName) {
		this.gwName = gwName;
	}
	public String getGwIP() {
		return gwIP;
	}
	public void setGwIP(String gwIP) {
		this.gwIP = gwIP;
	}
	public String getGwType() {
		return gwType;
	}
	public void setGwType(String gwType) {
		this.gwType = gwType;
	}
	


	

}
