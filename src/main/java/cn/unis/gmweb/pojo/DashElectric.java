package cn.unis.gmweb.pojo;

public class DashElectric {
    private String lineName;//线路名称
    private int areaSize;//区域数量
    private int red;//通讯状态
    private int orange;
    private int yellow;
    private int blue;
    private int normal;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public int getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(int areaSize) {
        this.areaSize = areaSize;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getOrange() {
        return orange;
    }

    public void setOrange(int orange) {
        this.orange = orange;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }
}
