package com.xbstar.esl.domain;

import javax.persistence.Id;

/**
 * @Description:
 * @Class:CallSound.java
 * @Author:janus
 * @Date:2024年8月31日下午11:57:59
 * @Version:1.0.0
 */
public class CallSound {

    @Id
    private Long id;

    private String callUuid;

    private String filePath;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
