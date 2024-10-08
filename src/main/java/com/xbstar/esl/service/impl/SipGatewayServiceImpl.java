package com.xbstar.esl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xbstar.esl.common.BaseMapper;
import com.xbstar.esl.common.BaseServiceImpl;
import com.xbstar.esl.dao.SipGatewayMapper;
import com.xbstar.esl.domain.SipGateway;
import com.xbstar.esl.service.SipGatewayService;

/**
 * @Description:
 * @Author:janus
 * @Date:2024年9月1日上午12:05:00
 * @Version:1.0.0
 */
@Service
public class SipGatewayServiceImpl extends BaseServiceImpl<SipGateway> implements SipGatewayService {
	
	@Resource
	private SipGatewayMapper sipGatewayMapper;
	

	@Override
	public SipGateway findByType(String type) {
		// TODO Auto-generated method stub
		return this.sipGatewayMapper.findByType(type);
	}

	@Override
	public BaseMapper<SipGateway> getMapper() {
		// TODO Auto-generated method stub
		return this.sipGatewayMapper;
	}

}
