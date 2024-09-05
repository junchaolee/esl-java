package com.xbstar.esl.dao;

import com.xbstar.esl.domain.SipAccount;
import com.xbstar.esl.common.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author:janus
 * @Date:2024年8月31日下午11:57:03
 * @Version:1.0.0
 */
@Mapper
public interface SipAccountMapper extends BaseMapper<SipAccount> {

    SipAccount findByUserId(String userid);
    int insertAccount(SipAccount sip);
	List<SipAccount> findAll();
	/**
	 * @param sip
	 * @return
	 */
	int delAccount(SipAccount sip);
}