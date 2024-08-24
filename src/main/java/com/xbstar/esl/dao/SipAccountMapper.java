package com.xbstar.esl.dao;

import com.xbstar.esl.domain.SipAccount;
import com.xbstar.esl.common.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SipAccountMapper extends BaseMapper<SipAccount> {

    SipAccount findByUserId(String userid);
}