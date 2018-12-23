package cn.unis.gmweb.controller.bkccontroller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.hadoop.mapred.IFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.unis.gmweb.service.HbaseService;
import cn.unis.gmweb.utils.ConfigTable;

/**
 * 查询bkc 水泵 数据
 *
 * @author lgf
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/bkc")
public class BkcController {
    @Resource
    private HbaseService hbaseService;

    /**
     * 获取设备详情
     *
     * @param sbid 设备id
     * @return
     */
    @RequestMapping("/details/{sbid}")
    @ResponseBody
    public HashMap<String, String> GetDeviceDetails(@PathVariable String sbid) {
        HashMap<String, String> detailMap = hbaseService.getDeviceDetails(ConfigTable.bkcTable.toString(), sbid);
        return detailMap;
    }/**
     * 获取设备详情
     *
     * @param sbid 设备id
     * @return
     */
    @RequestMapping("/details2/{sbid}")
    @ResponseBody
    public HashMap<String, String> GetDeviceDetailsToBIM(@PathVariable String sbid) {
        HashMap<String, String> detailMap = hbaseService.getDeviceDetails(ConfigTable.bkcTable.toString(), sbid);
        String healthState =detailMap.get("HealthState");
        switch (healthState){
            case "A":
                detailMap.put("HealthState", "red");
                break;
            case "B":
                detailMap.put("HealthState", "orange");
                break;
            case "C":
                detailMap.put("HealthState", "yellow");
                break;
            case "D":
                detailMap.put("HealthState", "blue");
                break;
            default:
                detailMap.put("HealthState", "normal");
                break;
        }
        return detailMap;
    }

    /**
     * 获取电机端轴承、水泵端轴承震动、温度、转速曲线
     *
     * @param sbid    设备id
     * @param minutes 时间
     * @return
     */
    @RequestMapping("/trend/{sbid}/{minutes}")
    @ResponseBody
    public List<HashMap<String, String>> GetTrendVibration(@PathVariable String sbid, @PathVariable Integer minutes) {
        List<HashMap<String, String>> trendList = hbaseService.getTrendVibration(ConfigTable.bkcTable.toString(), sbid, minutes);
        return trendList;
    }

    /**
     * 获取电机端轴承震动曲线
     *
     * @param sbid    设备id
     * @param minutes 时间
     * @return
     */
    @RequestMapping("/motor/{sbid}/{minutes}")
    @ResponseBody
    public List<HashMap<String, String>> GetMotorVibration(@PathVariable String sbid, @PathVariable Integer minutes) {
        List<HashMap<String, String>> motorList = hbaseService.getMotorVibration("bkc_data_rt", sbid, minutes);
        return motorList;
    }

    /**
     * 获取水泵端轴承震动曲线
     *
     * @param sbid    设备id
     * @param minutes 时间
     * @return
     */
    @RequestMapping("/pump/{sbid}/{minutes}")
    @ResponseBody
    public List<HashMap<String, String>> GetPumpVibration(@PathVariable String sbid, @PathVariable Integer minutes) {
        List<HashMap<String, String>> pumpList = hbaseService.getPumpVibration("bkc_data_rt", sbid, minutes);
        return pumpList;
    }

    /**
     * 获取电机温度曲线
     *
     * @param sbid    设备id
     * @param minutes 时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/motortemp/{sbid}/{minutes}")
    public List<HashMap<String, String>> GetMotorTemperature(@PathVariable String sbid, @PathVariable Integer minutes) {
        List<HashMap<String, String>> tempList = hbaseService.getTempVibration("bkc_data_rt", sbid, minutes);
        return tempList;
    }

    /**
     * 获取转速曲线
     *
     * @param sbid    设备id
     * @param minutes 时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/motorspeed/{sbid}/{minutes}")
    public List<HashMap<String, String>> GetMotorSpeed(String sbid, @RequestParam(defaultValue = "20", required = false) Integer minutes) {
        List<HashMap<String, String>> speedList = hbaseService.getSpeedVibration("bkc_data_rt", sbid, minutes);
        return speedList;
    }

    /**
     * 诊断信息 前端hours小时调用一次
     *
     * @param sbid 设备id
     * @return
     */
    @ResponseBody
    @RequestMapping("/diagnosis/{sbid}")
    public List<HashMap<String, String>> GetDiagnosis(@PathVariable String sbid) {
        long hours = 24 * 100;//此处为n小时诊断一次
        List<HashMap<String, String>> diagnosisList = hbaseService.getDiagnosis(ConfigTable.diagnosisTable.toString(), sbid, hours);
        return diagnosisList;
    }

    /**
     * 诊断信息 前端hours小时调用一次
     *
     * @param sbid 设备id
     * @return
     */
    @ResponseBody
    @RequestMapping("/diagnosis2/{sbid}")
    public List<HashMap<String, String>> GetDiagnosisToBIM(@PathVariable String sbid) {
        long hours = 24 * 100;//此处为n小时诊断一次
        List<HashMap<String, String>> diagnosisList = hbaseService.getDiagnosis(ConfigTable.diagnosisTable.toString(), sbid, hours);
        for (HashMap<String, String> map : diagnosisList) {
            int serverity =Integer.valueOf(map.get("Severity"));
            String healthState ="normal";
            if(serverity>600){
                healthState="red";
            }else if (serverity>300 && serverity<=600){
                healthState="orage";
            }else if(serverity>100 && serverity<=300){
                healthState="yellow";
            }else if (serverity>0 && serverity<=100){
                healthState="blue";
            }else {
                //normal
            }
            map.put("Severity",healthState);
        }
        return diagnosisList;
    }


}
