package cn.unis.gmweb.pojo;


public class HtTrend {

	private String Ivalue;
	private String Tvalue;
	private String date;
	public String getIvalue() {
		return Ivalue;
	}
	public void setIvalue(String ivalue) {
		Ivalue = ivalue;
	}
	public String getTvalue() {
		return Tvalue;
	}
	public void setTvalue(String tvalue) {
		Tvalue = tvalue;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "HtTrend [Ivalue=" + Ivalue + ", Tvalue=" + Tvalue + ", date=" + date + "]";
	}
	
	
	
}
