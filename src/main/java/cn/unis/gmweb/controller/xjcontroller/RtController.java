package cn.unis.gmweb.controller.xjcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.unis.gmweb.pojo.HtRealTime;
import cn.unis.gmweb.pojo.QdlRealTime;
import cn.unis.gmweb.pojo.Tree;
import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.service.TreeService;
import cn.unis.gmweb.utils.ConfigTable;
import cn.unis.gmweb.utils.HbaseUtil;

/**
 * 获取实时数据
 * 
 * @author lgf
 *
 */
@Controller
@RequestMapping("/xj/realtime")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RtController {

	@Resource
	private HbaseService hbaseService;
	@Resource
	private TreeService treeService;

	/**
	 * 获取火探实时数据
	 * @param lineName
	 * @return
	 */
	@RequestMapping("/ht/{lineName}")
	@ResponseBody
	public List<HtRealTime> getHtRealTime(@PathVariable String lineName) {
		// 1、查出火探所有的区域id 遍历查询每个qyid的实时数据
		List<Tree> qyidList = treeService.findHtTree(lineName);
		List<HtRealTime> htRealTimeList = new ArrayList<HtRealTime>();
		for (Tree tree : qyidList) {
			HtRealTime ht = new HtRealTime();
			ht.setQyName(tree.getQname());
			Result htRsult = hbaseService.getHtRealTime(ConfigTable.ht_ycTable.toString(), tree.getQid());
			if(htRsult!=null) HbaseUtil.setHtReal(htRsult, ht);
			htRealTimeList.add(ht);
		}
		return htRealTimeList;
	}
	/**
	 * 获取全电量实时数据
	 * @param lineName
	 * @return
	 */
	@RequestMapping("/qdl/{lineName}")
	@ResponseBody
	public List<QdlRealTime> getQdlRealTime(@PathVariable String lineName) {
		// 1、查出火探所有的区域id 遍历查询每个qyid的实时数据
		List<Tree> qyidList = treeService.findqdlTree(lineName);
		List<QdlRealTime> qdlRealTimeList = new ArrayList<QdlRealTime>();
		for (Tree tree : qyidList) {
			QdlRealTime qdl = new QdlRealTime();
			qdl.setQyName(tree.getQname());
			Result qdlRsult = hbaseService.getQdlRealTime(ConfigTable.qdl_ycTable.toString(), tree.getQid());
			if(qdlRsult!=null) HbaseUtil.setQdlReal(qdl, qdlRsult);
			qdlRealTimeList.add(qdl);
		}
		return qdlRealTimeList;
	}
	
	
}
