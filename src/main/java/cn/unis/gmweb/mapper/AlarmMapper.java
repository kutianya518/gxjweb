package cn.unis.gmweb.mapper;

import java.util.List;


import cn.unis.gmweb.pojo.XjAlarm;

public interface AlarmMapper {
	List<XjAlarm> findhisAlarm(String daystr);

}
