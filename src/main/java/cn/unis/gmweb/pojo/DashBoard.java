package cn.unis.gmweb.pojo;

import java.util.HashMap;
import java.util.List;

/**
 * 仪表盘总览页
 */
public class DashBoard {

    private List<DashPump> pump;
    private List<DashElectric> electric;

    public List<DashPump> getPump() {
        return pump;
    }

    public void setPump(List<DashPump> pump) {
        this.pump = pump;
    }

    public List<DashElectric> getElectric() {
        return electric;
    }

    public void setElectric(List<DashElectric> electric) {
        this.electric = electric;
    }
}
