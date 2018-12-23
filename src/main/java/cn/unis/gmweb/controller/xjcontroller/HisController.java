package cn.unis.gmweb.controller.xjcontroller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.unis.gmweb.pojo.HtTrend;
import cn.unis.gmweb.pojo.QdlTrend;
import cn.unis.gmweb.pojo.QdlymTrend;
import cn.unis.gmweb.pojo.XjAlarm;
import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.utils.ConfigTable;
import cn.unis.gmweb.utils.HbaseUtil;

/**
 * 获取历史趋势数据
 * 
 * @author lgf
 *
 */

@Controller
@RequestMapping("/xj/his")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HisController {
	@Resource
	private HbaseService hbaseService;

	/**
	 * 获取火探近24小时电流、温度值
	 * @param qid
	 * @return
	 */
	@RequestMapping("ht/{qid}/{hours}")
	@ResponseBody
	public List<HtTrend> getHtHistory(@PathVariable String qid,@PathVariable Integer hours) {
		List<Result> iRsult = hbaseService.getHtHistory(ConfigTable.ht_ycTable.toString(), qid,hours);
		List<HtTrend> IHtTrend = HbaseUtil.getHtList(iRsult);
		return IHtTrend;
	}

	/**
	 * 获取全电量近24小时三项电流值
	 * 
	 * @param qid
	 * @return
	 */
	@RequestMapping("qdl/{qid}/{hours}")
	@ResponseBody
	public List<QdlTrend> getQdlHistory(@PathVariable String qid,@PathVariable Integer hours) {
		List<Result> qdlRsult = hbaseService.getQdlHistory(ConfigTable.qdl_ycTable.toString(), qid,hours);
		List<QdlTrend> qdlTrend = HbaseUtil.getQdlList(qdlRsult);
		return qdlTrend;
	}
	/**
	 * 最近7天的能耗数据
	 * @param qid
	 * @return
	 */
	@RequestMapping("qdlym/{qid}/{days}")
	@ResponseBody
	public List<QdlymTrend> getQdlymHistory(@PathVariable String qid,@PathVariable Integer days) {
		List<Result> qdlymResult = hbaseService.getQdlymHistory(ConfigTable.qdl_ymTable.toString(), qid,days);
		List<QdlymTrend> qdlymTrend = HbaseUtil.getQdlymList(qdlymResult);
		return qdlymTrend;
	}
	/**
	 * 获取近7天的告警信息
	 * @param days
	 * @return
	 */
	@RequestMapping("alarm/{days}")
	@ResponseBody
	public List<XjAlarm> gethisAlarm(@PathVariable Integer days) {
		List<Result> alarmResult = hbaseService.getAlarmHistory(ConfigTable.alarmTable.toString(), days);
		List<XjAlarm> alarmList =HbaseUtil.getAlarmList(alarmResult);
		return alarmList;
	}
}
