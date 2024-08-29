package com.xbstar.esl.service;

import com.xbstar.esl.domain.SipGateway;

/**
 * Created by Simon on 2019/9/9 13:49
 */
public interface SipGatewayService {
   
    SipGateway findByType(String type);
}
