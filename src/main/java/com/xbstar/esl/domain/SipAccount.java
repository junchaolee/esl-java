package com.xbstar.esl.domain;

public class SipAccount {
	
	private String userId;
	private String password;
	private String codec;
	private String createTime;
	
	
	public String getCodec() {
		return codec;
	}
	public void setCodec(String codec) {
		this.codec = codec;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
}
