package cn.unis.gmweb.utils;

public enum ConfigTable {
	jxinterfacedata("jx_interface_data"),
	qdl_ycTable("xj_qdl_ycdata"),
	qdl_ymTable("xj_qdl_ymdata"),
	ht_ycTable("xj_ht_data"),
	alarmTable("xj_alarm_data"),
	bkcTable("bkc_data"),
	diagnosisTable("bkc_diagnosis_data");//声明枚举常量，并通过构造函数复制
	private String value;//属性及构造函数必须为private
	private ConfigTable(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {//重新toString方法
		return String.valueOf(this.value);
	}
}
