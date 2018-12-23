package cn.unis.gmweb.pojo;

public class Tree {

	private String qid;//区域id（电流/温度）
	private String qname;//区域名称（电流/温度所属区域）
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getQname() {
		return qname;
	}
	public void setQname(String qname) {
		this.qname = qname;
	}
	@Override
	public String toString() {
		return "Tree [qid=" + qid + ", qname=" + qname + "]";
	}
	
	
	
}
