/**
 * 
 */
package com.xbstar.esl.controller;

import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xbstar.esl.domain.Conference;
import com.xbstar.esl.service.ConferenceService;
import com.xbstar.esl.service.impl.SipAccountServiceImpl;
import com.xbstar.esl.service.impl.SipGatewayServiceImpl;
import com.xbstar.esl.util.AlvesJSONResult;

import link.thingscloud.freeswitch.esl.InboundClient;

/**
 *  @Description:
 *  @Date:2024年9月4日14:17:39
 *  @Author:tong
 *  @Version:v1.0.0
 */
@RestController
@RequestMapping("/api")
public class API {
	private static final Logger log = LoggerFactory.getLogger(API.class);
	@Autowired
	SipAccountServiceImpl sipAccountService;
	
	@Autowired
	SipGatewayServiceImpl sipGatewayService;
	
	@Resource
	ConferenceService confService;
	
//	@Autowired
//	private InboundClient client;
	
	
	/**
	 * 创建会议
	 * 创建一个会议对象，对象里包含有会议名称、成员、并将会议信息存储到表结构
	 * 先期只做音频，后期加上视频做判断
	 */
	@RequestMapping("/conference/create")
	public AlvesJSONResult confCreate(@Validated @RequestBody Conference conf) {
		//生成会议名(ID)
		String confName = RandomStringUtils.randomNumeric(8);
		//主持人
		String userId = conf.getUserId();
		String cmdstr = confName+"@video-mcu-stereo bgdial {absolute_codec_string=^^:pcma:pcmu}user/"+userId+" "+confName+" conference";
//		client.sendAsyncApiCommand("192.168.0.130:18021","conference", cmdstr);
//		String cmdStr2="6577786 bgdial {absolute_codec_string=^^:pcma:pcmu}user/321321";
//		ESL.client.sendAsyncApiCommand("conference", cmdStr2);
		
		
		return AlvesJSONResult.ok("会议【"+confName+"】创建成功,主持人："+userId);

	}
	
	
	
	/**
	 * @param:会议名+成员分机
	 * 添加会议成员+
	 * 如果添加N个成员，是执行N次接口调用
	 * 还是一次调用接口，获取所有成员信息
	 * 循环执行？
	 * 
	 * 添加会议成员时，根据事件存储结果存储信息到数据库
	 * @return
	 */
	@RequestMapping("/conference/addMember")
	public AlvesJSONResult confAdd(@Validated @RequestBody Conference con) {
		String userId = con.getUserId();
		
		String confName = con.getConfName();
//		log.info("成员分机："+userId+"|会议名："+confName);
		String cmdStr=confName+" bgdial {absolute_codec_string=^^:pcma:pcmu}user/"+userId;
//		client.sendAsyncApiCommand("","conference", cmdStr);
		return AlvesJSONResult.ok("会议成员【"+userId+"】添加成功");
	}
	
	
	/**
	 * @param:会议名+成员分机
	 * 踢出会议成员
	 * 通过成员获取所在会议的memberID
	 * @return
	 */
	@RequestMapping("/conference/delMember")
	public AlvesJSONResult confDel(@Validated @RequestBody Conference con) {
		String confName = con.getConfName();
		String userId = con.getUserId();
		String mId=confService.queryMemberId(confName,userId);
//		client.sendAsyncApiCommand("","conference", confName+" kick "+mId);
		return AlvesJSONResult.ok("会议成员【"+userId+"】已踢出");
	}
	
	/**
	 * 销毁会议
	 * conference 6577786 hup all
	 */
	@RequestMapping("/conference/destroy")
	public AlvesJSONResult confDestroy(@Validated @RequestBody Conference con){
		//获取会议名,直接传参
		//通过会议成员获取
		String confName = con.getConfName();
		
		
		String res ="" ;//client.sendAsyncApiCommand("","conference", confName+" hup all");
		if(StringUtils.isNoneEmpty(res)) {
			
			return AlvesJSONResult.ok("会议【"+confName+"】销毁成功");

		}else {
			return AlvesJSONResult.ok("会议【"+confName+"】销毁失败");

		}
		
		
	}

}
