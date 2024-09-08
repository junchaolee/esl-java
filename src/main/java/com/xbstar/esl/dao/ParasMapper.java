/**
 * 
 */
package com.xbstar.esl.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xbstar.esl.common.BaseMapper;
import com.xbstar.esl.domain.ParasInfo;

/**
 *  @Description:
 *  @Date:2024年9月4日15:25:55
 *  @Author:tong
 *  @Version:v1.0.0
 */
@Mapper
public interface ParasMapper extends BaseMapper<ParasInfo> {

	/**
	 * @param name
	 * @return
	 */
	String queryValueByName(String name);
	
	String findByName(String name);

}
