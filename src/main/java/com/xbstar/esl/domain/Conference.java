package com.xbstar.esl.domain;

import javax.validation.Valid;

/**
 * @Description: 会议相关
 * @Author:janus
 * @Date:2024年9月1日下午11:03:05
 * @Version:1.0.0
 */
public class Conference {
	private String confName;//会议名
	@Valid
	private SipAccount account;//会议成员
	private String isVideo;//是否视频会议
	private String memberId;//会议中成员ID
	private String createTime;

	
	
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getConfName() {
		return confName;
	}
	public void setConfName(String confName) {
		this.confName = confName;
	}


	public SipAccount getAccount() {
		return account;
	}
	public void setAccount(SipAccount account) {
		this.account = account;
	}
	public String getIsVideo() {
		return isVideo;
	}
	public void setIsVideo(String isVideo) {
		this.isVideo = isVideo;
	}


}
