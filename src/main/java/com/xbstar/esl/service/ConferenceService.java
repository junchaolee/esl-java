package com.xbstar.esl.service;

import com.xbstar.esl.domain.Conference;

/**
 *  @Description:
 *  @Date:2024年9月2日15:35:29
 *  @Author:tong
 *  @Version:v1.0.0
 */
public interface ConferenceService {
	
	
	int insertConf(Conference conf);

	/**
	 * @param confName
	 * @param userId
	 * @return 
	 */
	String queryMemberId(String confName, String userId);

}
