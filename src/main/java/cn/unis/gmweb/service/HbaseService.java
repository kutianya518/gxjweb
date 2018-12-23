package cn.unis.gmweb.service;

import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.hbase.client.Result;


public interface HbaseService {

	 List<Result> getHtHistory(String tableName, final String qid, final Integer hours);

	 List<Result> getQdlHistory(String tableName, final String qid, final Integer hours);

	 Result getHtRealTime(String tableName, final String qyid);

	 Result getQdlRealTime(String tableName, final String qyid);

	 List<Result> getQdlymHistory(String tableName, final String qid, final Integer days);
	
	 List<Result> getAlarmHistory(String tableName, final Integer days);
	
	 List<Result> getAlarmRealTime(String tableName);

	 HashMap<String, String> getDeviceDetails(String tableName, final String sbid);
	
	 List<HashMap<String, String>> getDiagnosis(String diagnosisTable, final String sbid, final long hours);

	 List<HashMap<String, String>> getTrendVibration(String string, final String sbid, final Integer minutes);

	 List<HashMap<String, String>> getMotorVibration(String tableName, final String sbid, final Integer minutes);

	 List<HashMap<String, String>> getPumpVibration(String tableName, final String sbid, final Integer minutes);

	 List<HashMap<String, String>> getTempVibration(String tableName, final String sbid, final Integer minutes);

	 List<HashMap<String, String>> getSpeedVibration(String tablename, final String sbid, final Integer minutes);

	 HashMap<String, List<Object>> getElevatorData(String tableName, final String interface_Id, final Integer minutes);



}
