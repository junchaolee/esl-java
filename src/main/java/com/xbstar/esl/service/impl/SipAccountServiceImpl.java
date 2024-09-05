package com.xbstar.esl.service.impl;

import com.xbstar.esl.common.BaseMapper;
import com.xbstar.esl.common.BaseServiceImpl;
import com.xbstar.esl.dao.CallRecordMapper;
import com.xbstar.esl.dao.SipAccountMapper;
import com.xbstar.esl.domain.CallRecord;
import com.xbstar.esl.domain.SipAccount;
import com.xbstar.esl.service.CallRecordService;
import com.xbstar.esl.service.SipAccountService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author:janus
 * @Date:2024年9月1日上午12:04:49
 * @Version:1.0.0
 */
@Service
public class SipAccountServiceImpl extends BaseServiceImpl<SipAccount> implements SipAccountService {

	@Resource
	private SipAccountMapper sipAccountMapper;

	@Override
	public BaseMapper<SipAccount> getMapper() {
		return this.sipAccountMapper;
	}

	@Override
	public SipAccount findByUserId(String userid) {
		return sipAccountMapper.findByUserId(userid);
	}

	// 新增分机
	@Override
	public int insertAccount(SipAccount sip) {
		// 判断分机号是否已经存在，不存在才添加
		SipAccount user = findByUserId(sip.getUserId());
		if (user == null) {
			return sipAccountMapper.insertAccount(sip);

		} else {
			return 0;
		}

	}

	@Override
	public List<SipAccount> findAll() {
		// TODO Auto-generated method stub
		return sipAccountMapper.findAll();
	}

	/**
	 * 删除分机
	 */
	@Override
	public int delAccount(SipAccount sip) {
		// TODO Auto-generated method stub
		// 判断分机号是否已经存在，存在才删除
		SipAccount user = findByUserId(sip.getUserId());
		if (user == null) {

			return 0;
		} else {
			return sipAccountMapper.delAccount(sip);
		}
	}

}
