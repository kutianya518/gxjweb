package cn.unis.gmweb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Service;

import cn.unis.gmweb.utils.DateUtil;
import cn.unis.gmweb.utils.HbaseUtil;


@SuppressWarnings("deprecation")
@Service
public class HbaseServiceImpl implements HbaseService {

    @Resource
    private HbaseTemplate hbaseTemplate;
    @Resource
    private BkcService bkcService;


    /**
     * 获取火探近24小时历史数据
     */
    @Override
    public List<Result> getHtHistory(String tableName, final String qid, final Integer hours) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<Result>>() {
            List<Result> rsList = new ArrayList<>();

            @Override
            public List<Result> doInTable(HTableInterface table) throws Throwable {
                List<Get> getList = new ArrayList<Get>();
                // 获取最近24小时ht数据
                for (int i = 0; i < hours; i++) {
                    String lasttime = DateUtil.getlasthourDate(i);
                    String tmp = qid + DateUtil.getHourStamp(lasttime);
                    Get get = new Get(tmp.getBytes());
                    getList.add(get);
                }
                Result[] result = table.get(getList);
                for (Result rs : result) {
                    rsList.add(rs);
                }
                return rsList;
            }
        });
    }

    /**
     * 获取全电量近24小时历史数据
     */
    @Override
    public List<Result> getQdlHistory(String tableName, final String qid, final Integer hours) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<Result>>() {
            List<Result> rsList = new ArrayList<>();

            @Override
            public List<Result> doInTable(HTableInterface table) throws Throwable {
                List<Get> getList = new ArrayList<Get>();
                // 获取最近24小时ht数据
                for (int i = 0; i < hours; i++) {
                    String lasttime = DateUtil.getlasthourDate(i);
                    String tmp = qid + DateUtil.getHourStamp(lasttime);
                    Get get = new Get(tmp.getBytes());
                    getList.add(get);
                }
                Result[] result = table.get(getList);
                for (Result rs : result) {
                    rsList.add(rs);
                    // rs.isEmpty()判断该结果对应的rowkey是否有值返回,rs.setExists(true)可设置存不存在
                    //exists是Result的一个布尔属性，
                    //System.err.println(rs.isEmpty() + "全电量是否存在" + rs.getExists());
                }
                return rsList;
            }
        });
    }

    /**
     * 获取火探实时数据
     */
    @Override
    public Result getHtRealTime(String tableName, final String qyid) {
        return hbaseTemplate.execute(tableName, new TableCallback<Result>() {
            @Override
            public Result doInTable(HTableInterface table) throws Throwable {
                String minuteStr = DateUtil.getMinuteDate(System.currentTimeMillis());
                // 系统当前分钟时间戳
                long nowminute = DateUtil.getMinuteStamp(minuteStr);
                //当查询时是0分---1分，此时数据正在入库，入库时间4s，故整分的0-4s内查不到数据
                long lasttime = nowminute - 60000L;
                String endrow = qyid + lasttime;
                String startrow = qyid + nowminute;
                Scan sc = new Scan();
                sc.setStartRow(Bytes.toBytes(startrow));
                sc.setStopRow(Bytes.toBytes(endrow));
                sc.setReversed(true);
                Filter filterNum = new PageFilter(1);
                sc.setFilter(filterNum);
                ResultScanner scanner = table.getScanner(sc);
                Result rs = scanner.next();
                return rs;
            }
        });
    }

    /**
     * 获取全电量实时数据
     */
    @Override
    public Result getQdlRealTime(String tableName, final String qyid) {
        return hbaseTemplate.execute(tableName, new TableCallback<Result>() {
            @Override
            public Result doInTable(HTableInterface table) throws Throwable {
                String minuteStr = DateUtil.getMinuteDate(System.currentTimeMillis());
                // 系统当前分钟时间戳
                long nowminute = DateUtil.getMinuteStamp(minuteStr);
                long lasttime = nowminute - 60000L;
                String endrow = qyid + lasttime;
                String startrow = qyid + nowminute;
                Scan sc = new Scan();
                sc.setStartRow(Bytes.toBytes(startrow));
                sc.setStopRow(Bytes.toBytes(endrow));
                sc.setReversed(true);
                Filter filterNum = new PageFilter(1);
                sc.setFilter(filterNum);
                ResultScanner scanner = table.getScanner(sc);
                Result rs = scanner.next();
                return rs;
            }
        });
    }

    /**
     * 获取全电量遥脉近7天有功电度
     */
    @Override
    public List<Result> getQdlymHistory(String tableName, final String qid, final Integer days) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<Result>>() {
            List<Result> qdlymResult = new ArrayList<Result>();

            @Override
            public List<Result> doInTable(HTableInterface table) throws Throwable {
                List<Get> getList = new ArrayList<Get>();
                // 获取当日零点时间戳
                for (int day = 0; day <= days; day++) {
                    String dayDate = DateUtil.getlastdayDate(day);
                    String rowkey = qid + DateUtil.getDayStamp(dayDate);
                    Get get = new Get(Bytes.toBytes(rowkey));
                    getList.add(get);
                }
                // 如果获取不到result就是空得实例，后期要修改一下
                Result[] results = table.get(getList);
                for (Result rs : results) {
                    qdlymResult.add(rs);
                }
                return qdlymResult;
            }
        });
    }

    /**
     * 获取历史告警
     */
    @Override
    public List<Result> getAlarmHistory(String tableName, final Integer days) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<Result>>() {
            List<Result> alarmResult = new ArrayList<Result>();

            @Override
            public List<Result> doInTable(HTableInterface table) throws Throwable {
                String minuteStr = DateUtil.getMinuteDate(System.currentTimeMillis());
                // 系统当前分钟时间戳
                long nowminute = DateUtil.getMinuteStamp(minuteStr);
                // 七天前时间戳
                long lastminute = nowminute - 60000 * 60 * 24 * days;
                // 同一时间戳设备1001在设备2030之前
                String endrow = lastminute + "1001";
                String startrow = nowminute + "2030";
                /*
                 * FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL);
                 * RegexStringComparator rg_endrow=new RegexStringComparator(endrow); RowFilter
                 * rf = new RowFilter(CompareOp.GREATER_OR_EQUAL, rg_endrow);
                 * RegexStringComparator rg_startrow=new RegexStringComparator(startrow);
                 * RowFilter rf2 = new RowFilter(CompareOp.LESS_OR_EQUAL, rg_startrow);
                 * fl.addFilter(rf); fl.addFilter(rf2); sc.setFilter(fl);
                 */
                Scan sc = new Scan();
                sc.setStartRow(Bytes.toBytes(startrow));
                sc.setStopRow(Bytes.toBytes(endrow));
                sc.setReversed(true);
                ResultScanner scanner = table.getScanner(sc);
                for (Result rs : scanner) {
                    alarmResult.add(rs);
                }
                return alarmResult;
            }
        });
    }

    /**
     * 获取实时告警
     */
    @Override
    public List<Result> getAlarmRealTime(String tableName) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<Result>>() {
            List<Result> alarmResult = new ArrayList<>();

            @Override
            public List<Result> doInTable(HTableInterface table) throws Throwable {
                String minuteStr = DateUtil.getMinuteDate(System.currentTimeMillis());
                // 系统当前分钟时间戳
                long nowminute = DateUtil.getMinuteStamp(minuteStr);
                // 上一分钟时间戳
                long lastminute = nowminute - 60006L;
                // 同一时间戳设备2001在设备2030之前
                String endrow = lastminute + "2001";
                String startrow = nowminute + "2030";
                /*
                 * FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL);
                 * RegexStringComparator rg_endrow=new RegexStringComparator(endrow); RowFilter
                 * rf = new RowFilter(CompareOp.GREATER_OR_EQUAL, rg_endrow);
                 * RegexStringComparator rg_startrow=new RegexStringComparator(startrow);
                 * RowFilter rf2 = new RowFilter(CompareOp.LESS_OR_EQUAL, rg_startrow);
                 * fl.addFilter(rf); fl.addFilter(rf2); sc.setFilter(fl);
                 */
                Scan sc = new Scan();
                sc.setStartRow(Bytes.toBytes(startrow));
                sc.setStopRow(Bytes.toBytes(endrow));
                sc.setReversed(true);
                ResultScanner scanner = table.getScanner(sc);
                for (Result rs : scanner) {
                    alarmResult.add(rs);
                }
                return alarmResult;
            }
        });
    }

    /**
     * 查询设备详情
     */
    @Override
    public HashMap<String, String> getDeviceDetails(String tableName, final String sbid) {
        return hbaseTemplate.execute(tableName, new TableCallback<HashMap<String, String>>() {
            HashMap<String, String> map = new HashMap<>();

            @Override
            public HashMap<String, String> doInTable(HTableInterface table) throws Throwable {
                Scan scan = new Scan();
                long tm = System.currentTimeMillis();
                long nowTime = DateUtil.getMinuteStamp(DateUtil.getMinuteDate(tm));
                long oneminute = nowTime - 60 * 1000L;
                scan.setStartRow(Bytes.toBytes(sbid + nowTime));
                scan.setStopRow(Bytes.toBytes(sbid + oneminute));
                scan.setReversed(true);
                ResultScanner scanner = table.getScanner(scan);
                Result nextRs = scanner.next();
                if (nextRs != null) HbaseUtil.setBkcDeviceDetailsHashMap(nextRs, map);
                return map;
            }
        });
    }

    /**
     * 振动、温度、转速曲线
     */
    @Override
    public List<HashMap<String, String>> getTrendVibration(String tableName, final String sbid, final Integer minutes) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<HashMap<String, String>>>() {
            List<HashMap<String, String>> list = new ArrayList<>();

            @Override
            public List<HashMap<String, String>> doInTable(HTableInterface table) throws Throwable {
                Scan scan = new Scan();
                long nowTime = System.currentTimeMillis();
                long twenty = nowTime - minutes * 60 * 1000;
                scan.setStartRow(Bytes.toBytes(sbid + twenty));
                scan.setStopRow(Bytes.toBytes(sbid + nowTime));
                ResultScanner scanner = table.getScanner(scan);
                for (Result rs : scanner) {
                    HashMap<String, String> map = new HashMap<>();
                    HbaseUtil.setBkcTrendVibration(rs, map);
                    list.add(map);
                }
                return list;
            }
        });
    }

    /**
     * 电机端轴承近20分钟震动曲线
     */
    @Override
    public List<HashMap<String, String>> getMotorVibration(String tableName, final String sbid, final Integer minutes) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<HashMap<String, String>>>() {
            List<HashMap<String, String>> list = new ArrayList<>();

            @Override
            public List<HashMap<String, String>> doInTable(HTableInterface table) throws Throwable {
                Scan scan = new Scan();
                long nowTime = System.currentTimeMillis();
                long twenty = nowTime - minutes * 60 * 1000;
                scan.setStartRow(Bytes.toBytes(sbid + twenty));
                scan.setStopRow(Bytes.toBytes(sbid + nowTime));
                ResultScanner scanner = table.getScanner(scan);
                for (Result rs : scanner) {
                    HashMap<String, String> map = new HashMap<>();
                    HbaseUtil.setBkcMotorVibration(rs, map);
                    list.add(map);
                }
                return list;
            }
        });
    }

    /**
     * 水泵端轴承近20分钟震动曲线
     */
    @Override
    public List<HashMap<String, String>> getPumpVibration(String tableName, final String sbid, final Integer minutes) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<HashMap<String, String>>>() {
            List<HashMap<String, String>> list = new ArrayList<>();

            @Override
            public List<HashMap<String, String>> doInTable(HTableInterface table) throws Throwable {
                Scan scan = new Scan();
                long nowTime = System.currentTimeMillis();
                long twenty = nowTime - minutes * 60 * 1000;
                scan.setStartRow(Bytes.toBytes(sbid + twenty));
                scan.setStopRow(Bytes.toBytes(sbid + nowTime));
                ResultScanner scanner = table.getScanner(scan);
                for (Result rs : scanner) {
                    HashMap<String, String> map = new HashMap<>();
                    HbaseUtil.setBkcPumpVibration(rs, map);
                    list.add(map);
                }
                return list;
            }
        });
    }

    /**
     * 电机温度曲线
     */
    @Override
    public List<HashMap<String, String>> getTempVibration(String tableName, final String sbid, final Integer minutes) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<HashMap<String, String>>>() {
            List<HashMap<String, String>> list = new ArrayList<>();

            @Override
            public List<HashMap<String, String>> doInTable(HTableInterface table) throws Throwable {
                Scan scan = new Scan();
                long nowTime = System.currentTimeMillis();
                long twenty = nowTime - minutes * 60 * 1000;
                scan.setStartRow(Bytes.toBytes(sbid + twenty));
                scan.setStopRow(Bytes.toBytes(sbid + nowTime));
                ResultScanner scanner = table.getScanner(scan);
                for (Result rs : scanner) {
                    HashMap<String, String> map = new HashMap<>();
                    HbaseUtil.setBkcTempVibration(rs, map);
                    list.add(map);
                }
                return list;
            }
        });
    }

    /**
     * 水泵转速曲线
     */
    @Override
    public List<HashMap<String, String>> getSpeedVibration(String tableName, final String sbid, final Integer minutes) {
        return hbaseTemplate.execute(tableName, new TableCallback<List<HashMap<String, String>>>() {
            List<HashMap<String, String>> list = new ArrayList<>();

            @Override
            public List<HashMap<String, String>> doInTable(HTableInterface table) throws Throwable {
                Scan scan = new Scan();
                long nowTime = System.currentTimeMillis();
                long twenty = nowTime - minutes * 60 * 1000;
                scan.setStartRow(Bytes.toBytes(sbid + twenty));
                scan.setStopRow(Bytes.toBytes(sbid + nowTime));
                ResultScanner scanner = table.getScanner(scan);
                for (Result rs : scanner) {
                    HashMap<String, String> map = new HashMap<>();
                    HbaseUtil.setBkcSpeedVibration(rs, map);
                    list.add(map);
                }
                return list;
            }
        });
    }

    @Override
    public List<HashMap<String, String>> getDiagnosis(String diagnosisTable, final String sbid, final long hours) {

        return hbaseTemplate.execute(diagnosisTable, new TableCallback<List<HashMap<String, String>>>() {
            List<HashMap<String, String>> list = new ArrayList<>();

            @Override
            public List<HashMap<String, String>> doInTable(HTableInterface table) throws Throwable {
                List<String> cidList = bkcService.findCidList(sbid);
                Scan scan = new Scan();
                long nowTime = System.currentTimeMillis();
                // hours小时诊断一次
                long lastHour = nowTime - hours * 60 * 60 * 1000L;
                String starrow = lastHour + cidList.get(0);
                String stoprow = nowTime + cidList.get(1);
                scan.setStartRow(Bytes.toBytes(starrow));
                scan.setStopRow(Bytes.toBytes(stoprow));
                //scan.setReversed(true);
                ResultScanner scanner = table.getScanner(scan);
                for (Result rs : scanner) {
                    String cid = new String(rs.getValue(Bytes.toBytes("Diagnosis"), Bytes.toBytes("C_id")));
                    if (cidList.get(0).equals(cid) || cidList.get(1).equals(cid)) {
                        HashMap<String, String> map = new HashMap<>();
                        HbaseUtil.setBkcDiagnosis(rs, map);
                        list.add(map);
                    }

                }
                return list;
            }
        });
    }

    @Override
    public HashMap<String, List<Object>> getElevatorData(String tableName, final String interface_Id, final Integer minutes) {
        HashMap<String, List<Object>> map = new HashMap<>();
        return hbaseTemplate.execute(tableName, new TableCallback<HashMap<String, List<Object>>>() {
            @Override
            public HashMap<String, List<Object>> doInTable(HTableInterface table) throws Throwable {
                Scan scan = new Scan();
                long nowTime = System.currentTimeMillis();
                long lastminute = nowTime - minutes * 60 * 1000L;
                scan.setStartRow(Bytes.toBytes(interface_Id + lastminute));
                scan.setStopRow(Bytes.toBytes(interface_Id + nowTime));
                scan.addColumn(Bytes.toBytes("interface"), Bytes.toBytes("records"));
                ResultScanner scanner = table.getScanner(scan);
                List<Object> list= new ArrayList<>();
                for (Result rs : scanner) {
                    if (rs != null) HbaseUtil.setJxInterfaceData(list, rs);
                }
                map.put("records",list);
                return map;
            }
        });
    }

}
