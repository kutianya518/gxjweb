package cn.unis.gmweb.controller.jxcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.service.JxService;
import cn.unis.gmweb.utils.ConfigTable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("elevator")
@CrossOrigin(origins="*",maxAge=3600)
public class JxCrontroller {

	@Resource
	private HbaseService hbaseService;
	@Resource
	private JxService jxService;
	/**
	 * 电梯数据传输状态数据30分钟更新一次
	 * @param sbid 轿厢id
	 * @return
	 */
	@RequestMapping("state/{sbid}")
	@ResponseBody
	public HashMap<String,List<Object>> getEleDataState(@PathVariable String sbid) {
		String interface_Id = jxService.getInterfaceId(sbid, "eleDataState");
		final Integer minutes=30;
		HashMap<String, List<Object>> stateMap=hbaseService.getElevatorData(ConfigTable.jxinterfacedata.toString(),interface_Id, minutes);
		return stateMap;
	}
	/**
	 * 电梯健康评分数据1天更新一次
	 * @param sbid
	 * @return
	 */
	@RequestMapping("healthscore/{sbid}")
	@ResponseBody
	public HashMap<String, List<Object>> getHealthScore(@PathVariable String sbid) {
		String interface_Id = jxService.getInterfaceId(sbid, "healthScore");
		final Integer minutes=24*60;
		HashMap<String, List<Object>> stateMap=hbaseService.getElevatorData(ConfigTable.jxinterfacedata.toString(),interface_Id, minutes);
		return stateMap;
	}
	/**
	 * 电梯运行层数数据每5分钟更新一次  查6小时返回数组
	 * @param sbid
	 * @return
	 */
	@RequestMapping("load/{sbid}")
	@ResponseBody
	public HashMap<String, List<Object>> getload(@PathVariable String sbid) {
		String interface_Id = jxService.getInterfaceId(sbid, "load");
		final Integer minutes=60*6;
		HashMap<String, List<Object>> stateMap=hbaseService.getElevatorData(ConfigTable.jxinterfacedata.toString(),interface_Id, minutes);
		return stateMap;
	}
	/**
	 * 电梯运行启停次数数据每5分钟更新一次 查一天返回数组
	 * @param sbid
	 * @return
	 */
	@RequestMapping("runNumber/{sbid}")
	@ResponseBody
	public HashMap<String,List<Object>> getRunNumber(@PathVariable String sbid) {
		String interface_Id = jxService.getInterfaceId(sbid, "runNumber");
		final Integer minutes=60*24;
		HashMap<String, List<Object>> stateMap=hbaseService.getElevatorData(ConfigTable.jxinterfacedata.toString(),interface_Id, minutes);
		return stateMap;
	}
	/**
	 * 电梯加速度数据每30s更新一次 查10分钟的数据返回数组
	 * @param sbid
	 * @return
	 */
	@RequestMapping("acc/{sbid}")
	@ResponseBody
	public HashMap<String,List<Object>> getAcc(@PathVariable String sbid) {
		String interface_Id = jxService.getInterfaceId(sbid, "acc");
		final Integer minutes=10;
		HashMap<String, List<Object>> stateMap=hbaseService.getElevatorData(ConfigTable.jxinterfacedata.toString(),interface_Id, minutes);
		return stateMap;
	}
	/**
	 * 电梯运行速度数据每30s更新一次  查10分钟的数据返回数组
	 * @param sbid
	 * @return
	 */
	@RequestMapping("speed/{sbid}")
	@ResponseBody
	public HashMap<String,List<Object>> getSpeed(@PathVariable String sbid) {
		String interface_Id = jxService.getInterfaceId(sbid, "speed");
		final Integer minutes=10;
		HashMap<String, List<Object>> stateMap=hbaseService.getElevatorData(ConfigTable.jxinterfacedata.toString(),interface_Id, minutes);
		return stateMap;
	}
	/**
	 * 电梯运行角速度数据每30s更新一次
	 * @param sbid
	 * @return
	 */
	@RequestMapping("angular/{sbid}")
	@ResponseBody
	public HashMap<String,List<Object>> getAngular(@PathVariable String sbid) {
		String interface_Id = jxService.getInterfaceId(sbid, "angular");
		final Integer minutes=10;
		HashMap<String, List<Object>> stateMap=hbaseService.getElevatorData(ConfigTable.jxinterfacedata.toString(),interface_Id, minutes);
		return stateMap;
	}
	
	
	
}