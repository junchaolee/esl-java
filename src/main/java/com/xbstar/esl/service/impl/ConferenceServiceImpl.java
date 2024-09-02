package com.xbstar.esl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xbstar.esl.common.BaseMapper;
import com.xbstar.esl.common.BaseServiceImpl;
import com.xbstar.esl.dao.ConferenceMapper;
import com.xbstar.esl.domain.Conference;
import com.xbstar.esl.service.ConferenceService;

/**
 *  @Description:
 *  @Date:2024年9月2日15:36:50
 *  @Author:tong
 *  @Version:v1.0.0
 */
@Service
public class ConferenceServiceImpl  extends BaseServiceImpl<Conference> implements ConferenceService {
	
	@Resource
	private ConferenceMapper confMapper;

	@Override
	public int insertConf(Conference conf) {
		// TODO Auto-generated method stub
		return confMapper.insert(conf);
	}

	@Override
	public BaseMapper<Conference> getMapper() {
		// TODO Auto-generated method stub
		return this.confMapper;
	}

	/**
	 * @param confName
	 * @param userId
	 * @return 
	 */
	public String queryMemberId(String confName, String userId) {
		// TODO Auto-generated method stub
		return confMapper.queryMemberId(confName,userId);
		
	}
	
	

}
