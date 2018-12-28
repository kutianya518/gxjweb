package cn.unis.gmweb.pojo;

public class XjRtAlarm {// 蓝色预警、38C/1000MA、东B1配电箱、2018-12-28
    private String saveTime;//告警时间
    private String description;//告警内容
    private String name;//告警设备所处位置
    private int tag = 0;//0未读，1已读
    //private String warnLevel;
    //private String warnValue;

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }



}
