package model.ServiceParameters;

import model.sdkentity.DeviceInfo;
import model.sdkentity.UIOptions;

public class InitializeParameters {
    private String serverAddress;
    private DeviceInfo deviceInfo;
    private UIOptions uiOptions;

    public InitializeParameters(String serverAddress, DeviceInfo deviceInfo, UIOptions uiOptions) {
        this.serverAddress = serverAddress;
        this.deviceInfo = deviceInfo;
        this.uiOptions = uiOptions;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public UIOptions getUiOptions() {
        return uiOptions;
    }

    public void setUiOptions(UIOptions uiOptions) {
        this.uiOptions = uiOptions;
    }
}
