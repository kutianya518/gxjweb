package cn.unis.gmweb.pojo;


public class HtRealTimeTree extends BaseTree{

	private String I;//漏电流
	private String T;//温度
	private String warnLevel="normal";//预警级别
	private String warnArguments;


	public String getI() {
		return I;
	}

	public void setI(String i) {
		I = i;
	}

	public String getT() {
		return T;
	}

	public void setT(String t) {
		T = t;
	}

	public String getWarnLevel() {
		return warnLevel;
	}

	public void setWarnLevel(String warnLevel) {
		this.warnLevel = warnLevel;
	}

	public String getWarnArguments() {
		return warnArguments;
	}

	public void setWarnArguments(String warnArguments) {
		this.warnArguments = warnArguments;
	}
}
