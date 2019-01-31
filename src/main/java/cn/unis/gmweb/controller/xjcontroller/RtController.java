package cn.unis.gmweb.controller.xjcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.unis.gmweb.pojo.*;
import cn.unis.gmweb.task.DashBoardTask;
import cn.unis.gmweb.utils.TreeToolUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.web.bind.annotation.*;
import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.service.TreeService;
import cn.unis.gmweb.utils.ConfigTable;
import cn.unis.gmweb.utils.HbaseUtil;

/**
 * 获取实时数据
 *
 * @author lgf
 */
@RestController
@RequestMapping("/xj/realtime")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RtController {

    @Resource
    private HbaseService hbaseService;
    @Resource
    private TreeService treeService;

    /**
     * 获取火探实时数据
     *
     * @param lineName
     * @return
     */
    @RequestMapping("/ht/{lineName}")
    public List<HtRealTime> getHtRealTime(@PathVariable String lineName) {
        // 1、查出火探所有的区域id 遍历查询每个qyid的实时数据
        List<Tree> qyidList = treeService.findHtTree(lineName);
        List<HtRealTime> htRealTimeList = new ArrayList<HtRealTime>();
        for (Tree tree : qyidList) {
            HtRealTime ht = new HtRealTime();
            ht.setQyName(tree.getQname());
            Result htRsult = hbaseService.getHtRealTime(ConfigTable.ht_ycTable.toString(), tree.getQid());
            if (htRsult != null) {
                HbaseUtil.setHtReal(htRsult, ht);
                //二期增加预警级别
                ht.setWarnLevel("normal");
            }
            htRealTimeList.add(ht);
        }
        return htRealTimeList;
    }

    /**
     * 获取火探实时tree数据
     *
     * @param lineName 线路名称
     * @return
     */
    @RequestMapping("/httree/{lineName}")
    public List<HtRealTimeTree> getHtRealTimeTree(@PathVariable String lineName) {

        if (DashBoardTask.htMemoryCache.size()==0){
            new DashBoardTask().InitDashBoard();
        }
        List<HtRealTimeTree> htrtList = DashBoardTask.htMemoryCache.get(lineName);
        if (htrtList==null) return null;
        List<HtRealTimeTree> rootList = new ArrayList<>();
        List<HtRealTimeTree> bodyList = new ArrayList<>();

        for (HtRealTimeTree ht : htrtList) {
            if (ht.getParentId().equals("0")) {
                rootList.add(ht);
            } else {
                bodyList.add(ht);
            }
        }
        TreeToolUtils<HtRealTimeTree> treeUtils = new TreeToolUtils<>(rootList, bodyList);
        return treeUtils.getTree();
    }


    /**
     * 获取全电量实时数据
     *
     * @param lineName
     * @return
     */
    @RequestMapping("/qdl/{lineName}")
    public List<QdlRealTime> getQdlRealTime(@PathVariable String lineName) {
        List<Tree> qyidList = treeService.findqdlTree(lineName);
        List<QdlRealTime> qdlRealTimeList = new ArrayList<QdlRealTime>();
        for (Tree tree : qyidList) {
            QdlRealTime qdl = new QdlRealTime();
            qdl.setQyName(tree.getQname());
            Result qdlRsult = hbaseService.getQdlRealTime(ConfigTable.qdl_ycTable.toString(), tree.getQid());
            if (qdlRsult != null) {
                HbaseUtil.setQdlReal(qdl, qdlRsult);
            }
            qdlRealTimeList.add(qdl);
        }
        return qdlRealTimeList;
    }

    /**
     * 获取全电量实时数据tree
     *
     * @param lineName 线路名称
     * @return
     */
    @RequestMapping("/qdltree/{lineName}")
    public List<QdlRealTimeTree> getQdlRealTimeTree(@PathVariable String lineName) {
        if (DashBoardTask.qdlMemoryCache.size()==0){
            new DashBoardTask().InitQdlRealTimeTree();
        }
        List<QdlRealTimeTree> qdlrtList = DashBoardTask.qdlMemoryCache.get(lineName);
        if (qdlrtList==null) return null;
        List<QdlRealTimeTree> rootList = new ArrayList<>();
        List<QdlRealTimeTree> bodyList = new ArrayList<>();
        for (QdlRealTimeTree qdl : qdlrtList) {
            if (qdl.getParentId().equals("0")) {
                rootList.add(qdl);
            } else {
                bodyList.add(qdl);
            }
        }
        TreeToolUtils<QdlRealTimeTree> treeUtils = new TreeToolUtils<>(rootList, bodyList);
        return treeUtils.getTree();
    }
}
