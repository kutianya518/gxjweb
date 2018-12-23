package cn.unis.gmweb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.unis.gmweb.mapper.AlarmMapper;
import cn.unis.gmweb.pojo.XjAlarm;
import cn.unis.gmweb.utils.DateUtil;

@Service
public class AlarmServiceImpl implements AlarmService{
	@Resource
	private AlarmMapper alarmMapper;
	@Override
	public List<XjAlarm> findhisAlarm(Integer days) {
		String daystr = DateUtil.getlastdayDate(days);
		return alarmMapper.findhisAlarm(daystr);
	}


}
