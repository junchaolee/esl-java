package com.xbstar.esl.domain;

import javax.persistence.Id;

/* +"  		    <gateway name=\"gwfxo8\">\n"
+"     				<param name=\"proxy\" value=\"192.168.0.240:5077\" />\n"
+"     				<param name=\"realm\" value=\"192.168.0.240:5077\" />\n"
+"    				<param name=\"register\" value=\"false\" />\n"
+"     				<param name=\"rtp-autofix-timing\" value=\"false\" />\n"
+"     				<param name=\"caller-id-in-from\" value=\"true\" />\n"
+"     				<param name=\"register-transport\" value=\"udp\" />\n"
+"  			</gateway>\n"


					+"      <extension name=\"2FXO\">\n"
					+"        <condition field=\"destination_number\" expression=\"^([0-9]{11,12})$\">\n"
					+"          <action application=\"export\" data=\"dialed_extension=$1\"/>\n"
					+"          <action application=\"bridge\""+" data=\"sofia/gateway/gwfxo8/$1\"/>\n"
					+"        </condition>\n"
网关类型gwtype
ext	统一通信网关
trunk	SIP中继网关
synway	三汇SIP网关
xunjie	迅捷SIP网关
other	其他网关
vhf	无线电台网关

*/



public class SipGateway {
	@Id
	private Long id;
	private String gwName;
	private String gwIP;
	private String gwType;
	
	
	public String getGwName() {
		return gwName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setGwName(String gwName) {
		this.gwName = gwName;
	}
	public String getGwIP() {
		return gwIP;
	}
	public void setGwIP(String gwIP) {
		this.gwIP = gwIP;
	}
	public String getGwType() {
		return gwType;
	}
	public void setGwType(String gwType) {
		this.gwType = gwType;
	}
	


	

}
