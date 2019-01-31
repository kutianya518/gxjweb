package cn.unis.gmweb.pojo;

/**
 * 此Bean为水泵详情，通过反射获得对应属性，限制hbase返回
 */
public class PumpDetails {
    private String MachineID;
    private String DataTime;
    private String DJ_shock;
    private String SB_shock;
    private String speed;
    private String temperature;
    private String ComState;
    private String HealthState;
    private String RunSate;
    private String In_flow;
    private String In_speed;
    private String In_sum_flow;
    private String level;
    private String out_pressure;
    private String out_flow;
    private String out_speed;
    private String out_sum_flow;
    private String ua;
    private String ub;
    private String uc;
    private String ia;
    private String ib;
    private String ic; 
    private String warnLevel="normal";
    private String warnArguments;

    public String getMachineID() {
        return MachineID;
    }

    public void setMachineID(String machineID) {
        MachineID = machineID;
    }

    public String getDataTime() {
        return DataTime;
    }

    public void setDataTime(String dataTime) {
        DataTime = dataTime;
    }

    public String getDJ_shock() {
        return DJ_shock;
    }

    public void setDJ_shock(String DJ_shock) {
        this.DJ_shock = DJ_shock;
    }

    public String getSB_shock() {
        return SB_shock;
    }

    public void setSB_shock(String SB_shock) {
        this.SB_shock = SB_shock;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getComState() {
        return ComState;
    }

    public void setComState(String comState) {
        ComState = comState;
    }

    public String getRunSate() {
        return RunSate;
    }

    public void setRunSate(String runSate) {
        RunSate = runSate;
    }

    public String getIn_flow() {
        return In_flow;
    }

    public void setIn_flow(String in_flow) {
        In_flow = in_flow;
    }

    public String getIn_speed() {
        return In_speed;
    }

    public void setIn_speed(String in_speed) {
        In_speed = in_speed;
    }

    public String getIn_sum_flow() {
        return In_sum_flow;
    }

    public void setIn_sum_flow(String in_sum_flow) {
        In_sum_flow = in_sum_flow;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOut_pressure() {
        return out_pressure;
    }

    public void setOut_pressure(String out_pressure) {
        this.out_pressure = out_pressure;
    }

    public String getOut_flow() {
        return out_flow;
    }

    public void setOut_flow(String out_flow) {
        this.out_flow = out_flow;
    }

    public String getOut_speed() {
        return out_speed;
    }

    public void setOut_speed(String out_speed) {
        this.out_speed = out_speed;
    }

    public String getOut_sum_flow() {
        return out_sum_flow;
    }

    public void setOut_sum_flow(String out_sum_flow) {
        this.out_sum_flow = out_sum_flow;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getUb() {
        return ub;
    }

    public void setUb(String ub) {
        this.ub = ub;
    }

    public String getUc() {
        return uc;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public String getIa() {
        return ia;
    }

    public void setIa(String ia) {
        this.ia = ia;
    }

    public String getIb() {
        return ib;
    }

    public void setIb(String ib) {
        this.ib = ib;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
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

    public String getHealthState() {
        return HealthState;
    }

    public void setHealthState(String healthState) {
        HealthState = healthState;
    }
}
