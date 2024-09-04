/**
 * 
 */
package com.xbstar.esl.service;

import com.xbstar.esl.domain.ParasInfo;

/**
 *  @Description:
 *  @Date:2024年9月4日15:13:07
 *  @Author:tong
 *  @Version:v1.0.0
 */
public interface ParasService {
	
	ParasInfo findByName(String name);

}
