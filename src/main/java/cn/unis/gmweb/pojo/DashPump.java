package cn.unis.gmweb.pojo;

public class DashPump {
    private String MachineID;
    private String RunSate="1";
    private String ComState;
    private String warnLevel;

    public String getMachineID() {
        return MachineID;
    }

    public void setMachineID(String machineID) {
        MachineID = machineID;
    }

    public String getRunSate() {
        return RunSate;
    }

    public void setRunSate(String runSate) {
        RunSate = runSate;
    }

    public String getComState() {
        return ComState;
    }

    public void setComState(String comState) {
        ComState = comState;
    }

    public String getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel;
    }
}
