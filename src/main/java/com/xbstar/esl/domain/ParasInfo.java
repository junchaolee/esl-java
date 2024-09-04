package com.xbstar.esl.domain;

/**
 *  @Description:
 *  @Date:2024年9月4日14:58:34
 *  @Author:tong
 *  @Version:v1.0.0
 */
public class ParasInfo {
	private String parameterName;
	private String parameterValue;
	private String createTime;
	
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Parameters [parameterName=" + parameterName + ", parameterValue=" + parameterValue + ", createTime="
				+ createTime + "]";
	}

	
	

}
