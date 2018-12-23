package cn.unis.gmweb.pojo;

public class QdlTrend {
	private String Ia;
	private String Ib;
	private String Ic;
	private String date;
	public String getIa() {
		return Ia;
	}
	public void setIa(String ia) {
		Ia = ia;
	}
	public String getIb() {
		return Ib;
	}
	public void setIb(String ib) {
		Ib = ib;
	}
	public String getIc() {
		return Ic;
	}
	public void setIc(String ic) {
		Ic = ic;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "QdlTrend [Ia=" + Ia + ", Ib=" + Ib + ", Ic=" + Ic + ", date=" + date + "]";
	}
	
}
