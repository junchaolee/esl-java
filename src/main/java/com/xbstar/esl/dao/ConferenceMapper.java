/**
 * 
 */
package com.xbstar.esl.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xbstar.esl.common.BaseMapper;
import com.xbstar.esl.domain.Conference;

/**
 *  @Description:
 *  @Date:2024年9月2日15:42:29
 *  @Author:tong
 *  @Version:v1.0.0
 */
@Mapper
public interface ConferenceMapper extends BaseMapper<Conference> {
	int insertConf(Conference conf);

}
