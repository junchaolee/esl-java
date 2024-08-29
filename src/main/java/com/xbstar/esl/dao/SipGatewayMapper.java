package com.xbstar.esl.dao;

import com.xbstar.esl.common.BaseMapper;
import com.xbstar.esl.domain.SipGateway;

public interface SipGatewayMapper extends BaseMapper<SipGateway> {
	
    SipGateway findByType(String type);


}
