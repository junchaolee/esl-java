package com.xbstar.esl.service;

import com.xbstar.esl.domain.SipAccount;

/**
 * Created by Simon on 2019/9/9 13:49
 */
public interface SipAccountService {
   
    SipAccount findByUserId(String userid);
}
