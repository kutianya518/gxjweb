package cn.unis.gmweb.service;

import java.util.List;


import cn.unis.gmweb.pojo.XjAlarm;

public interface AlarmService {

	List<XjAlarm> findhisAlarm(Integer days);

}
