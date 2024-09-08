package com.xbstar.esl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xbstar.esl.domain.CallRecord;
import com.xbstar.esl.domain.CallSound;
import com.xbstar.esl.domain.Conference;
import com.xbstar.esl.domain.SipAccount;
import com.xbstar.esl.service.ConferenceService;
import com.xbstar.esl.service.impl.CallRecordServiceImpl;
import com.xbstar.esl.service.impl.CallSoundServiceImpl;
import com.xbstar.esl.util.AlvesJSONResult;
import com.xbstar.esl.util.DateUtil;
import com.xbstar.esl.util.EventConstant;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description: 连接fs，事件处理
 * @Author:janus
 * @Date:2024年8月31日下午11:54:06
 * @Version:1.0.0
 */
@RestController
public final class ESL {
	@Autowired
	ConferenceService confService;
	@Autowired
	CallRecordServiceImpl callRecordService;
	@Autowired
	CallSoundServiceImpl callSoundService;
	private static final Logger log = LoggerFactory.getLogger(ESL.class);
	@Value("${host}")
	private String host;
	@Value("${port}")
	private int port;
	@Value("${password}")
	private String password;
	
	public static final Client client = new Client();

	// 创建map存数据
	HashMap<String, String> legMap = new HashMap<String, String>();

	@PostConstruct
	public Client inBand() {

		try {
			client.connect(host, port, password, 20);
		} catch (InboundConnectionFailure e) {
			log.error("连接FS失败");
			return null;
		}

		// 注册事件处理程序
		client.addEventListener(new IEslEventListener() {
			public void eventReceived(EslEvent event) {
				String event_name = event.getEventName();
				Map<String, String> map = event.getEventHeaders();
				String call_uuid = map.get("Unique-ID");
				String caller_id_name = map.get("Caller-Caller-ID-Number");
				String caller_id_number = map.get("Caller-Caller-ID-Name");
				String destination_number = map.get("Caller-Destination-Number");
				String channelID = map.get("Unique-ID");
				String direction = map.get("Call-Direction");
				JSONObject paramsJson = JSONObject.parseObject(JSON.toJSONString(map));


				switch (event_name) {
				case EventConstant.RECORD_START:
					String filepath = map.get("Record-File-Path");
					log.info("【录制文件路径】："+filepath);
					CallSound callSound = new CallSound();
					callSound.setCallUuid(call_uuid);
					callSound.setFilePath(filepath);
					callSoundService.insert(callSound);
					break;

				case EventConstant.CHANNEL_CREATE:
					log.info("【事件CHANNEL_CREATE】" + " | " + "通道ID：" + channelID + "|" + "【呼叫方向】：" + direction);
					//1、程序控制sip交互响应直接给了200 OK,可以在dialplan的park之前加入early-ring给出180响应
					//client.sendSyncApiCommand("uuid_answer",channelID);
					//2、设置通道变量
					//client.sendAsyncApiCommand("uuid_setvar",channelID+" Ext_type 2");
					String cmd1=String.format("uuid_setvar %s %s %s", channelID, "Ext_type","2");
					client.sendAsyncApiCommand("cmd1", "");
					break;

				case EventConstant.CHANNEL_ANSWER:
					/*
					 * == 程序控制 B-leg ==
					 * 1、收到应答ack，开始查找路由桥接被叫 originate会执行拨号计划查找，inline可以不需
					 * 应该判断来电方向，如果不判断，总是有新的通道产生触发originate命令执行，将陷入死循环
					 */
					//log.info("【事件CHANNEL_ANSWER】" + "| " + "【通道ID】：" + channelID + "|" + "【呼叫方向】：" + direction);
					//String cmdstr = "{absolute_codec_string=^^:PCMA:PCMU,origination_caller_id_number="
					//		+ caller_id_number + "}user/" + destination_number + " inline";

					//if ("inbound".equals(direction)) {
					//	String result = client.sendAsyncApiCommand("originate", cmdstr);
					//	System.out.println("【originate命令执行结果】：" + result.toString());
					//}

					/* 2、开始执行uuid-bridge，将aleg、bleg连起来 */
					//if ("inbound".equals(direction)) {
					//	legMap.put("a-leg-channelID", channelID);
					//} else if ("outbound".equals(direction)) {
					//	legMap.put("b-leg-channelID", channelID);
					//}
					//String alegID = legMap.get("a-leg-channelID");
					//String blegID = legMap.get("b-leg-channelID");
					//log.info("【A-legID】：" + alegID + "| " + "【B-legID】：" + blegID);
					//String cmdstr2 = alegID + " " + blegID;
					//if (StringUtils.isNotEmpty(alegID) && StringUtils.isNoneEmpty(blegID)) {
					//	client.sendAsyncApiCommand("uuid_bridge", cmdstr2);
					//	System.out.println("【uuid_bridge执行的参数】：" + cmdstr2);
					//}
					break;

				case EventConstant.RECORD_STOP:
					break;

				case EventConstant.CHANNEL_BRIDGE:
					// long startTime = Long.parseLong(map.get("Caller-Channel-Created-Time"));
					// long answerTime = Long.parseLong(map.get("Caller-Channel-Answered-Time"));
					String startTime = map.get("Caller-Channel-Created-Time");
					String answerTime = map.get("Caller-Channel-Answered-Time");
					CallRecord record = new CallRecord();
					record.setCallUuid(call_uuid);
					record.setCallerIdName(caller_id_name);
					record.setCallerIdNumber(caller_id_number);
					record.setDestinationNumber(destination_number);
					record.setStartStamp(startTime);
					record.setAnswerStamp(answerTime);
					callRecordService.insert(record);
					log.info("【事件：CHANNEL_BRIDGE】");
					break;
				case EventConstant.CHANNEL_HANGUP_COMPLETE:
					String hangupCause = map.get("variable_hangup_cause");
					// long endTime = Long.parseLong(map.get("Caller-Channel-Hangup-Time"));
					String endTime = map.get("Caller-Channel-Hangup-Time");
					CallRecord callRecord = callRecordService.findByuuid(call_uuid);
					if (callRecord == null)
						break;
					callRecord.setEndStamp(endTime);
					// callRecord.setUduration(endTime-callRecord.getStartStamp());
					// int billsec =
					// Integer.parseInt((endTime-callRecord.getAnswerStamp())/(1000*1000)+"");
					int billsec = 23;
					callRecord.setBillsec(billsec);
					callRecord.setHangupCause(hangupCause);
					callRecordService.update(callRecord);
					legMap.remove("a-leg-channelID");
					legMap.remove("b-leg-channelID");
					System.out.println("【事件：CHANNEL_HANGUP_COMPLETE】");
					break;
				case EventConstant.CUSTOM:
					/* 1、处理会议相关 */
					if("conference-create".equals(map.get("Action"))) {
						log.info("【会议"+map.get("Conference-Name")+"创建】,"+"会议profile："+map.get("Conference-Profile-Name"));
					}else if("del-member".equals(map.get("Action"))) {
						log.info("【成员"+map.get("Caller-Destination-Number")+"离开会议!!!】：");

					}else if("add-member".equals(map.get("Action"))) {
						log.info("【成员"+map.get("Caller-Destination-Number")+"进入会议,信息入库!!!】");
						//添加成员的时候，存储成员数据到数据库
						Conference conf = new Conference();
						conf.setUserId(map.get("Caller-Caller-ID-Number"));
						conf.setConfName(map.get("Conference-Name"));
						conf.setMemberId(map.get("Member-ID"));
						conf.setIsVideo(map.get("Video"));
						conf.setCreateTime(DateUtil.getNowStr());
						int save = confService.insertConf(conf);
						if (save != 0) {
							log.info("MemberID写入数据库成功");
						} else {
							log.info("MemberID写入数据库失败");
						}

					}else if("conference-destroy".equals(map.get("Action"))) {
						log.info("【会议"+map.get("Conference-Name")+"销毁】："+paramsJson);
					}
					//log.info("【Custom事件】"+map.get("Event-Subclass"));
					
					break;
					
				case EventConstant.CALL_DETAIL:
						log.info("【CALL_DETAIL事件：】"+paramsJson);
					break;
				case EventConstant.CHANNEL_STATE:
					log.info("【CHANNEL_STATE事件：】"+paramsJson);
				break;

				default:
					break;
				}
			}

			public void backgroundJobResultReceived(EslEvent event) {
				String uuid = event.getEventHeaders().get("Job-UUID");
				log.info("Background job result received+:" + event.getEventName() + "/" + event.getEventHeaders());// +"/"+JoinString(event.getEventHeaders())+"/"+JoinString(event.getEventBodyLines()));
			}

		});

		client.setEventSubscriptions("plain", "all");

		/*
		 * if (client.canSend()) { System.out.println("连接成功，发起呼叫..."); //
		 * （异步）向1000用户发起呼叫，用户接通后，播放音乐/tmp/demo1.wav String callResult =
		 * client.sendAsyncApiCommand("originate",
		 * "user/1002 &playback(/tmp/demo.wav)"); System.out.println("api uuid:" +
		 * callResult);
		 * 
		 * }
		 */
//		if(client.canSend()) {
//			System.out.println("连接FS SOCKET成功!!!");
//		}

		return client;
	}

	@RequestMapping("/invoke")
	public String invoke() {
		String callResult = client.sendAsyncApiCommand("originate", "user/1002 &playback(/home/audio/demo.wav)");
		return callResult.toString();
	}

	@RequestMapping("/call")
	/*
	 * originate <call_url> <exten>|&<application_name>(<app_args>) [<dialplan>]
	 * [<context>] [<cid_name>] [<cid_num>] [<timeout_sec>] 命令说明 它的第一个参数是呼叫字符串，
	 * 第二个参数可以是 & 加上一个 APP，APP的参数要放到 ( ) 里 eg： originate user/1000 &echo originate
	 * user/1000 &playback(/tmp/sound.wav) originate user/1000
	 * &record(/tmp/recording.wav)
	 * 
	 * 
	 * 
	 */
	public String call(@RequestParam(name = "called") String called) {
		System.out.println("被叫号码:" + called);
		String callResult = client.sendAsyncApiCommand("originate",
				"user/" + called + " &playback(/home/audio/demo.wav)");
		return callResult.toString();
	}

}