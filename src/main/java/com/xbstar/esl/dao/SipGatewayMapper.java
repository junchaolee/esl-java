package com.xbstar.esl.dao;

import com.xbstar.esl.common.BaseMapper;
import com.xbstar.esl.domain.SipGateway;

/**
 * @Description:
 * @Author:janus
 * @Date:2024年8月31日下午11:57:14
 * @Version:1.0.0
 */
public interface SipGatewayMapper extends BaseMapper<SipGateway> {
	
    SipGateway findByType(String type);


}
