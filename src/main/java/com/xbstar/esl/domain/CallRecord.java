package com.xbstar.esl.domain;

import javax.persistence.Id;

/**
 * @Description:
 * @Class:CallRecord.java
 * @Author:janus
 * @Date:2024年8月31日下午11:57:53
 * @Version:1.0.0
 */
public class CallRecord {
    @Id
    private Long id;
	private String callUuid;

    private String callerIdName;

    private String callerIdNumber;

    private String destinationNumber;

    private String startStamp;

    private String answerStamp;

    private String endStamp;

    private Long uduration;

    private Integer billsec;

    private String hangupCause;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCallUuid() {
		return callUuid;
	}

	public void setCallUuid(String callUuid) {
		this.callUuid = callUuid;
	}

	public String getCallerIdName() {
		return callerIdName;
	}

	public void setCallerIdName(String callerIdName) {
		this.callerIdName = callerIdName;
	}

	public String getCallerIdNumber() {
		return callerIdNumber;
	}

	public void setCallerIdNumber(String callerIdNumber) {
		this.callerIdNumber = callerIdNumber;
	}

	public String getDestinationNumber() {
		return destinationNumber;
	}

	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
	}

	public String getStartStamp() {
		return startStamp;
	}

	public void setStartStamp(String startTime) {
		this.startStamp = startTime;
	}

	public String getAnswerStamp() {
		return answerStamp;
	}

	public void setAnswerStamp(String answerTime) {
		this.answerStamp = answerTime;
	}

	public String getEndStamp() {
		return endStamp;
	}

	public void setEndStamp(String endTime) {
		this.endStamp = endTime;
	}

	public Long getUduration() {
		return uduration;
	}

	public void setUduration(Long uduration) {
		this.uduration = uduration;
	}

	public Integer getBillsec() {
		return billsec;
	}

	public void setBillsec(Integer billsec) {
		this.billsec = billsec;
	}

	public String getHangupCause() {
		return hangupCause;
	}

	public void setHangupCause(String hangupCause) {
		this.hangupCause = hangupCause;
	}



    
}