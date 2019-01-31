package cn.unis.gmweb.task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.unis.gmweb.pojo.XjRtAlarm;
import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.utils.ConfigTable;
import cn.unis.gmweb.utils.HbaseUtil;
import cn.unis.gmweb.websocket.WebSocket;

/**
 * 利用spring调度，定时写在方法中
 *
 * @author lgf
 */
@Component
public class AlarmScheduler {

    CopyOnWriteArraySet<WebSocket> webSocketSets = WebSocket.webSocketSet;
    @Autowired
    private HbaseService hbaseService;

    //@Scheduled(cron = "0 * * * * ?")//每分钟执行一次
    public void sendMassage() {
        List<Result> alarmResult = hbaseService.getAlarmRealTime(ConfigTable.alarmTable.toString());
        List<XjRtAlarm> alarmList = HbaseUtil.getRtAlarmList(alarmResult);
        //测试数据
        XjRtAlarm arm = new XjRtAlarm();
        arm.setDescription("漏电流告警");
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
