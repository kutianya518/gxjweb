package cn.unis.gmweb.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import cn.unis.gmweb.utils.ConfigTable;
import cn.unis.gmweb.utils.HbaseUtil;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.unis.gmweb.pojo.XjRtAlarm;
import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.websocket.WebSocket;
/**
 * 利用spring调度，定时写在配置文件中
 * @author lgf
 *
 */
@Component
public class AlarmTask {
	CopyOnWriteArraySet<WebSocket> webSocketSets = WebSocket.webSocketSet;
	@Autowired
	private HbaseService hbaseService;

	public void sendMassage() {
		
		if (webSocketSets.size() != 0) {
			List<Result> alarmResult = hbaseService.getAlarmRealTime(ConfigTable.alarmTable.toString());
			List<XjRtAlarm> alarmList = HbaseUtil.getRtAlarmList(alarmResult);
			// 测试数据
			//List<XjRtAlarm> alarmList=new ArrayList<XjRtAlarm>();
			XjRtAlarm arm = new XjRtAlarm();
			arm.setDescription("漏电流告警");//蓝黄橙红
			arm.setName("东B1配电室");
			arm.setSaveTime("2018-11-21");
			alarmList.add(arm);
			String jsonAlarm = JSONObject.toJSONString(alarmList);
			if (alarmList.size() != 0) {
				for (WebSocket ws : webSocketSets) {
					ws.sendAllMessage(jsonAlarm);
					System.err.println(jsonAlarm);
				}
			}

		}
	}
}
