package cn.unis.gmweb.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import cn.unis.gmweb.pojo.HtRealTime;
import cn.unis.gmweb.pojo.HtTrend;
import cn.unis.gmweb.pojo.QdlRealTime;
import cn.unis.gmweb.pojo.QdlTrend;
import cn.unis.gmweb.pojo.QdlymTrend;
import cn.unis.gmweb.pojo.XjAlarm;
import cn.unis.gmweb.pojo.XjRtAlarm;

import static org.apache.commons.lang.StringEscapeUtils.unescapeJavaScript;

@SuppressWarnings("deprecation")
public class HbaseUtil {

    public static List<QdlTrend> getQdlList(List<Result> result) {
        List<QdlTrend> qdlList = new ArrayList<QdlTrend>();
        for (Result rs : result) {// 对返回的结果集进行操作
            QdlTrend qdl = new QdlTrend();
            for (KeyValue kv : rs.raw()) {
                if (Bytes.toString(kv.getQualifier()).equals("Ia")) {
                    qdl.setIa(Bytes.toString(kv.getValue()));
                } else if (Bytes.toString(kv.getQualifier()).equals("Ib")) {
                    qdl.setIb(Bytes.toString(kv.getValue()));
                } else if (Bytes.toString(kv.getQualifier()).equals("Ic")) {
                    qdl.setIc(Bytes.toString(kv.getValue()));
                } else if (Bytes.toString(kv.getQualifier()).equals("SaveTime")) {
                    qdl.setDate(Bytes.toString(kv.getValue()));
                }
            }
            qdlList.add(qdl);
        }
        return qdlList;
    }

    public static List<QdlymTrend> getQdlymList(List<Result> qdlymRsult) {
        List<QdlymTrend> qdlymList = new ArrayList<QdlymTrend>();
        //注意：全电量如若查不到，则Result的keyvalue=NONE，那么rs.rawCells的size为0，qdlym对象未赋值
        for (Result rs : qdlymRsult) {
            QdlymTrend qdlym = new QdlymTrend();
            for (Cell cell : rs.rawCells()) {
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                switch (qualifier) {
                    case "WP":
                        qdlym.setWP(value);
                        break;
                    case "SaveTime":
                        qdlym.setDate(value);
                        break;
                    default:
                        break;
                }
            }
            if (qdlym.getWP() != null) qdlymList.add(qdlym);
        }
        // 计算每天的能耗电度wp,qdlymList为最近7天的凌晨能耗，共8个数据
        for (int i = 0; i < qdlymList.size() - 1; i++) {
            QdlymTrend ym1 = qdlymList.get(i);
            QdlymTrend ym2 = qdlymList.get(i + 1);
            Double plus = Double.valueOf(ym1.getWP()) - Double.valueOf(ym2.getWP());
            qdlymList.get(i).setWP(plus.toString());
        }
        // 移除第八个，得到计算后的七个
        qdlymList.remove(qdlymList.size() - 1);
        return qdlymList;
    }

    public static List<HtTrend> getHtList(List<Result> result) {
        List<HtTrend> hTList = new ArrayList<HtTrend>();
        for (Result rs : result) {// 对返回的结果集进行操作
            HtTrend ht = new HtTrend();
            for (KeyValue kv : rs.raw()) {
                if (Bytes.toString(kv.getQualifier()).equals("I")) {
                    ht.setIvalue(Bytes.toString(kv.getValue()));
                } else if (Bytes.toString(kv.getQualifier()).equals("T")) {
                    ht.setTvalue(Bytes.toString(kv.getValue()));
                } else if (Bytes.toString(kv.getQualifier()).equals("SaveTime")) {
                    ht.setDate(Bytes.toString(kv.getValue()));
                }
            }
            hTList.add(ht);
        }
        return hTList;
    }

    public static void setHtReal(Result htRsult, HtRealTime ht) {
        // 对返回的结果集进行操作
        for (KeyValue kv : htRsult.raw()) {
            if (Bytes.toString(kv.getQualifier()).equals("I")) {
                ht.setIvalue(Bytes.toString(kv.getValue()));
            } else if (Bytes.toString(kv.getQualifier()).equals("T")) {
                ht.setTvalue(Bytes.toString(kv.getValue()));
            } else if (Bytes.toString(kv.getQualifier()).equals("SaveTime")) {
                ht.setDate(Bytes.toString(kv.getValue()));
            }
        }
    }

    public static void setQdlReal(QdlRealTime qdl, Result qdlResult) {

        for (Cell cell : qdlResult.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (qualifier) {
                case "Ia":
                    qdl.setIa(value);
                    break;
                case "Ib":
                    qdl.setIb(value);
                    break;
                case "Ic":
                    qdl.setIc(value);
                    break;
                case "Ua":
                    qdl.setUa(value);
                    break;
                case "Ub":
                    qdl.setUb(value);
                    break;
                case "Uc":
                    qdl.setUc(value);
                    break;
                case "P":
                    qdl.setP(value);
                    break;
                case "Q":
                    qdl.setQ(value);
                    break;
                case "SaveTime":
                    qdl.setDate(value);
                    break;
                default:
                    break;
            }
        }
    }

    public static List<XjAlarm> getAlarmList(List<Result> alarmResult) {
        List<XjAlarm> alarmList = new ArrayList<XjAlarm>();
        for (Result rs : alarmResult) {
            XjAlarm alarm = new XjAlarm();
            for (Cell cell : rs.rawCells()) {
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                switch (qualifier) {
                    case "Descriptions":
                        alarm.setDescription(value);
                        break;
                    case "Positions":
                        alarm.setName(value);
                        break;
                    case "SaveTime":
                        alarm.setSaveTime(value);
                        break;
                    default:
                        break;
                }
            }
            alarmList.add(alarm);
        }
        return alarmList;
    }

    public static List<XjRtAlarm> getRtAlarmList(List<Result> alarmResult) {
        List<XjRtAlarm> alarmList = new ArrayList<XjRtAlarm>();
        for (Result rs : alarmResult) {
            XjRtAlarm alarm = new XjRtAlarm();
            for (Cell cell : rs.rawCells()) {
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                switch (qualifier) {
                    case "Descriptions":
                        alarm.setDescription(value);
                        break;
                    case "Positions":
                        alarm.setName(value);
                        break;
                    case "SaveTime":
                        alarm.setSaveTime(value);
                        break;
                    default:
                        break;
                }
            }
            alarmList.add(alarm);
        }
        return alarmList;
    }

    /**
     * 把设备详情实例映射为hashmap
     *
     * @param nextRs
     * @param map
     */
    public static void setBkcDeviceDetailsHashMap(Result nextRs, HashMap<String, String> map) {
        for (Cell cell : nextRs.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            if (!"In_sum_flow".equals(qualifier) || !"out_sum_flow".equals(qualifier)) {
                map.put(qualifier, value);
            }
        }
    }

    /**
     * 把实例映射为hashmap
     *
     * @param rs
     * @param map
     */
    public static void setBkcTrendVibration(Result rs, HashMap<String, String> map) {
        for (Cell cell : rs.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (qualifier) {
                case "DataTime":
                    map.put(qualifier, value);
                    break;
                case "DJ_shock":
                    map.put(qualifier, value);
                    break;
                case "SB_shock":
                    map.put(qualifier, value);
                    break;
                case "speed":
                    map.put(qualifier, value);
                    break;
                case "temperature":
                    map.put(qualifier, value);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 电机震动实例映射为hashmap
     *
     * @param rs
     * @param map
     */
    public static void setBkcMotorVibration(Result rs, HashMap<String, String> map) {
        for (Cell cell : rs.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (qualifier) {
                case "DataTime":
                    map.put(qualifier, value);
                    break;
                case "DJ_shock":
                    map.put(qualifier, value);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 水泵震动实例映射为hashmap
     *
     * @param rs
     * @param map
     */
    public static void setBkcPumpVibration(Result rs, HashMap<String, String> map) {
        for (Cell cell : rs.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (qualifier) {
                case "DataTime":
                    map.put(qualifier, value);
                    break;
                case "Sb_shock":
                    map.put(qualifier, value);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 电机温度实例映射为hashmap
     *
     * @param rs
     * @param map
     */
    public static void setBkcTempVibration(Result rs, HashMap<String, String> map) {
        for (Cell cell : rs.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (qualifier) {
                case "DataTime":
                    map.put(qualifier, value);
                    break;
                case "temperature":
                    map.put(qualifier, value);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 温度实例映射为hashmap
     *
     * @param rs
     * @param map
     */
    public static void setBkcSpeedVibration(Result rs, HashMap<String, String> map) {
        for (Cell cell : rs.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (qualifier) {
                case "DataTime":
                    map.put(qualifier, value);
                    break;
                case "speed":
                    map.put(qualifier, value);
                    break;
                default:
                    break;
            }
        }
    }

    public static void setBkcDiagnosis(Result rs, HashMap<String, String> map) {
        for (Cell cell : rs.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (qualifier) {
                case "DataTime":
                    map.put(qualifier, value);
                    break;
                case "C_id":
                    map.put(qualifier, value);
                    break;
                case "Severity":
                    map.put(qualifier, value);
                    break;
                case "Diagnosis":
                    map.put(qualifier, value);
                    break;
                case "Desc":
                    map.put(qualifier, value);
                    break;
                case "Recommend":
                    map.put(qualifier, value);
                    break;
                default:
                    break;
            }
        }

    }

    public static void setJxInterfaceData(List<Object> list, Result rs) {
        for (Cell cell : rs.rawCells()) {
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            //去除json转义斜杠
            String value = StringEscapeUtils.unescapeJava(Bytes.toString(CellUtil.cloneValue(cell)));
            JSONObject jsonObject = JSONObject.parseObject(value);
            switch (qualifier) {
				/*case "Interface_Id":
					map.put(qualifier, value);
					break;*/
                case "records":
                    list.add(jsonObject);
                    break;
				/*case "SaveTime":
					map.put(qualifier, value);
					break;*/
                default:
                    break;
            }
        }
    }
}
