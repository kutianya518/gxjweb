package cn.unis.gmweb.task;

import cn.unis.gmweb.pojo.*;
import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.service.TreeService;
import cn.unis.gmweb.utils.ConfigTable;
import cn.unis.gmweb.utils.HbaseUtil;
import cn.unis.gmweb.utils.TreeToolUtils;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Component
public class DashBoardTask {
    //仪表盘
    public static DashBoard dashBoardMemoryCache;
    public static HashMap<String,HashMap<String, String>> pumpMemoryCache=new HashMap<>();
    public static HashMap<String,List<HtRealTimeTree>> htMemoryCache=new HashMap<>();
    public static HashMap<String,List<QdlRealTimeTree>> qdlMemoryCache=new HashMap<>();
    @Autowired
    private HbaseService hbaseService;
    @Autowired
    private TreeService treeService;

    @Async
    public  DashBoard InitDashBoard(){
        //主要用来对Spring Web Application Context之外的类提供@Autowired注入功能,
        //此方法可以自动注入spring容器中的对象，注入必须用Autowired，用其他注解如Resource不行

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        DashBoard dashBoard = new DashBoard();
        //1、封装pump
        //1.1查询所有的水泵
        List<String> pumpIdList = treeService.findAllPump();
        //1.2封装list
        List<DashPump> dashPumpList = new ArrayList<>();
        for (String pumpId : pumpIdList) {
            HashMap<String, String> detailMap = hbaseService.getDeviceDetails(ConfigTable.bkcTable.toString(), pumpId);
            pumpMemoryCache.put(pumpId,detailMap);
            //封装实时数据
            DashPump dashPump = new DashPump();
            dashPump.setMachineID(pumpId);
            if (detailMap.size()!=0){
                dashPump.setRunSate(detailMap.get("RunSate"));
                dashPump.setComState(detailMap.get("ComState"));
                dashPump.setWarnLevel(detailMap.get("warnLevel"));
            }
            dashPumpList.add(dashPump);
        }
        dashBoard.setPump(dashPumpList);
        //2、封装强电
        //2.1查出所有线路名
        List<String> lineNameList = treeService.findAllLine();
        //2.2封装线路
        List<DashElectric> dashElectricList = new ArrayList<>();
        for (String lineName : lineNameList) {
            DashElectric dashElectric = new DashElectric();
            dashElectric.setLineName(lineName);
            List<HtRealTimeTree> htrtList = treeService.findHtRealTimeTree(lineName);
            for (HtRealTimeTree ht : htrtList) {
                Result htRsult = hbaseService.getHtRealTime(ConfigTable.ht_ycTable.toString(), ht.getQyid());
                if (htRsult != null) {
                    HbaseUtil.setHtRealTree(htRsult, ht);
                }
                try {
                    Field field =dashElectric.getClass().getDeclaredField(ht.getWarnLevel());
                    field.setAccessible(true);
                    field.set(dashElectric,field.getInt(dashElectric)+1);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            htMemoryCache.put(lineName,htrtList);
            dashElectric.setAreaSize(htrtList.size());
            dashElectricList.add(dashElectric);
        }
        dashBoard.setElectric(dashElectricList);
        dashBoardMemoryCache=dashBoard;
        return dashBoard;
    }

    @Async
    public void InitQdlRealTimeTree(){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        List<String> qdlLine=treeService.findAllQdlLine();
        for (String lineName:qdlLine){
            List<QdlRealTimeTree> qdlrtList = treeService.findQdlRealTimeTree(lineName);
            for (QdlRealTimeTree qdl : qdlrtList) {
                Result qdlRsult = hbaseService.getQdlRealTime(ConfigTable.qdl_ycTable.toString(), qdl.getQyid());
                if (qdlRsult != null) {
                    HbaseUtil.setQdlRealTree(qdl,qdlRsult);
                }
            }
            qdlMemoryCache.put(lineName,qdlrtList);
        }
    }
}
