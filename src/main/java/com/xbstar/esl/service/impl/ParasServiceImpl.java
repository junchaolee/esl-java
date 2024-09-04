/**
 * 
 */
package com.xbstar.esl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xbstar.esl.common.BaseMapper;
import com.xbstar.esl.common.BaseServiceImpl;
import com.xbstar.esl.dao.ParasMapper;
import com.xbstar.esl.domain.ParasInfo;
import com.xbstar.esl.service.ParasService;

/**
 *  @Description:
 *  @Date:2024年9月4日15:18:46
 *  @Author:tong
 *  @Version:v1.0.0
 */

@Service
public class ParasServiceImpl extends BaseServiceImpl<ParasInfo> implements ParasService {
	@Resource
	private ParasMapper parasMapper;
	

	@Override
	public ParasInfo findByName(String name) {
		// TODO Auto-generated method stub
		return parasMapper.findByName(name);
	}

	@Override
	public BaseMapper<ParasInfo> getMapper() {
		// TODO Auto-generated method stub
		return this.parasMapper;
	}

}
