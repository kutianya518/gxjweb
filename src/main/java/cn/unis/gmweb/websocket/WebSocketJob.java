package cn.unis.gmweb.websocket;
import java.util.concurrent.CopyOnWriteArraySet;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import cn.unis.gmweb.service.HbaseService;

public class WebSocketJob implements Job{

	@Autowired
	private HbaseService hbaseService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//主要用来对Spring Web Application Context之外的类提供@Autowired注入功能,
		//此方法可以自动注入spring容器中的对象，注入必须用Autowired，用其他注解如Resource不行
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		//List<Result> alarmResult = hbaseService.getAlarmRealTime("xj_alarm_data_rt");
		//List<XjAlarm> alarmList = HbaseUtil.getAlarmList(alarmResult);
		//String jsonAlarm = JSONObject.toJSONString(alarmList);
		CopyOnWriteArraySet<WebSocket> webSocketSet=WebSocket.webSocketSet;
		String msg=System.currentTimeMillis()+":1111";//jsonAlarm
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
