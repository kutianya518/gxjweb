package cn.unis.gmweb.websocket;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Result;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.unis.gmweb.pojo.XjAlarm;
import cn.unis.gmweb.service.AlarmService;
import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.utils.HbaseUtil;

public class WebSocketJob2 implements Job{

	@Autowired
	private HbaseService hbaseService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//主要用来对Spring Web Application Context之外的类提供@Autowired注入功能,
		//此方法可以自动注入spring容器中的对象，注入必须用Autowired，用其他注解如Resource不行
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		//List<Result> alarmResult = hbaseService.getAlarmRealTime("xj_alarm_data");
		//List<XjAlarm> alarmList = HbaseUtil.getAlarmList(alarmResult);
		//String jsonAlarm = JSONObject.toJSONString(alarmList);
		CopyOnWriteArraySet<WebSocket> webSocketSet=WebSocket.webSocketSet;
		String msg=System.currentTimeMillis()+":2222";//jsonAlarm
		/*if(alarmList.size()!=0) {
			执行下面的ws
		}*/
		for (WebSocket ws : webSocketSet) {
			ws.sendAllMessage(msg);
		}
		//websocketjs包下的实现
		/*ArrayList<WebSocketSession> users=ChatMessageHandler.users;
		for (WebSocketSession ws : users) {
			TextMessage tm= new TextMessage(System.currentTimeMillis()+"sockjs");
			try {
				ws.sendMessage(tm);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
}
