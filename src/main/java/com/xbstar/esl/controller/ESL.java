package com.xbstar.esl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xbstar.esl.domain.CallRecord;
import com.xbstar.esl.domain.CallSound;
import com.xbstar.esl.service.impl.CallRecordServiceImpl;
import com.xbstar.esl.service.impl.CallSoundServiceImpl;
import com.xbstar.esl.util.EventConstant;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

import java.sql.Date;
import java.util.Map;

/**
 * Java esl调用FreeSWITCH发起呼叫等
 *
 * @author SimonHua
 */
@Controller
public class ESL {
	@Autowired
	CallRecordServiceImpl callRecordService;
	@Autowired
	CallSoundServiceImpl callSoundService;

	private static final Logger log = LoggerFactory.getLogger(ESL.class);
	/**
	 * autoload_configs目录event_socket.conf.xml文件中
	 */
	@Value("${host}")
	private String host;
	// 连接101叶总的被acl限制了，只能通过nginx代理18000访问18021
	@Value("${port}")
	private int port;
	@Value("${password}")
	private String password;

	final Client client = new Client();

	@PostConstruct
	public Client inBand() {

		try {
			client.connect(host, port, password, 20);
		} catch (InboundConnectionFailure e) {
			log.error("Connect failed", e);
			return null;
		}
		

		// 注册事件处理程序
		client.addEventListener(new IEslEventListener() {
			public void eventReceived(EslEvent event) {
				String type = event.getEventName();
				Map<String, String> map = event.getEventHeaders();
				String call_uuid = map.get("Unique-ID");
				String caller_id_name = map.get("Caller-Caller-ID-Number");
				String caller_id_number = map.get("Caller-Caller-ID-Name");
				String destination_number = map.get("Caller-Destination-Number");
				String channelID = map.get("Unique-ID");

				JSONObject json = JSONObject.parseObject(JSON.toJSONString(map));
				switch (type) {
				case EventConstant.RECORD_START:
					String filepath = map.get("Record-File-Path");
					CallSound callSound = new CallSound();
					callSound.setCallUuid(call_uuid);
					callSound.setFilePath(filepath);
					callSoundService.insert(callSound);
					break;
				case EventConstant.CHANNEL_CREATE:
					System.out.println("【事件】：CHANNEL_CREATE");
					System.out.println(json);
					//没有给180响应，直接给了200 OK
					client.sendSyncApiCommand("uuid_answer",channelID);
					break;
				case EventConstant.CHANNEL_ANSWER:
					System.out.println("【事件】：CHANNEL_ANSWER");
					client.canSend();
					break;

					
				case EventConstant.RECORD_STOP:
					break;
//                    case EventConstant.CHANNEL_ANSWER:
//                        break;
				case EventConstant.CHANNEL_BRIDGE:
//                        long startTime = Long.parseLong(map.get("Caller-Channel-Created-Time"));
//                        long answerTime = Long.parseLong(map.get("Caller-Channel-Answered-Time"));
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
					System.out.println("【事件】：CHANNEL_BRIDGE");

					break;
//                    case EventConstant.CHANNEL_DESTROY:
//                        break;
				case EventConstant.CHANNEL_HANGUP_COMPLETE:
					String hangupCause = map.get("variable_hangup_cause");
//                        long endTime = Long.parseLong(map.get("Caller-Channel-Hangup-Time"));
					String endTime = map.get("Caller-Channel-Hangup-Time");
					CallRecord callRecord = callRecordService.findByuuid(call_uuid);
					if (callRecord == null)
						break;
					callRecord.setEndStamp(endTime);
//                        callRecord.setUduration(endTime-callRecord.getStartStamp());
//                        int billsec = Integer.parseInt((endTime-callRecord.getAnswerStamp())/(1000*1000)+"");
					int billsec = 23;
					callRecord.setBillsec(billsec);
					callRecord.setHangupCause(hangupCause);
					callRecordService.update(callRecord);
					System.out.println("【事件】：CHANNEL_HANGUP_COMPLETE");
					break;

				//case EventConstant.HEARTBEAT:
					//System.out.println("事件:HEAERBEAT");
					//break;

				default:
					break;
				}
			}



				


			public void backgroundJobResultReceived(EslEvent event) {
				// String uuid = event.getEventHeaders().get("Job-UUID");
//				log.info("Background job result received+:" + event.getEventName() + "/" + event.getEventHeaders());// +"/"+JoinString(event.getEventHeaders())+"/"+JoinString(event.getEventBodyLines()));
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
		return client;
	}

	@RequestMapping("/invoke")
	@ResponseBody
	public String invoke() {
		String callResult = client.sendAsyncApiCommand("originate", "user/1002 &playback(/tmp/demo.wav)");
		return callResult.toString();
	}

	@RequestMapping("/call")
	@ResponseBody
	/*
	 * originate <call_url> <exten>|&<application_name>(<app_args>) [<dialplan>]
	 * [<context>] [<cid_name>] [<cid_num>] [<timeout_sec>]
	 * 	命令说明
	 * 它的第一个参数是呼叫字符串， 第二个参数可以是 & 加上一个 APP，APP的参数要放到 ( ) 里
	 * 	eg：
	 *  originate user/1000 &echo
	 *	originate user/1000 &playback(/tmp/sound.wav)
     *  originate user/1000 &record(/tmp/recording.wav)
	 * 
	 * 
	 * 
	 */
	public String call(@RequestParam(name="called") String called) {
		System.out.println("被叫号码:"+called);
		String callResult = client.sendAsyncApiCommand("originate", "user/"+called+" &playback(/tmp/demo.wav)");
		return callResult.toString();
	}
	
	
	/*
	 * @GetMapping("/calls")
	 * 
	 * @ResponseBody 命令：originate public String call2(@RequestParam(name="called")
	 * String called,@RequestParam(name="caller") String caller) {
	 * System.out.println("被叫号码:"+called); String callResult =
	 * client.sendAsyncApiCommand("originate",
	 * "user/"+called+" &playback(/tmp/demo.wav)"); return callResult.toString(); }
	 */

}