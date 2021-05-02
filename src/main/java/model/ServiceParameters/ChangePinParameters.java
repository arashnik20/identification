package model.ServiceParameters;

import model.sdkentity.Credentials;
import model.sdkentity.PINType;
import model.sdkentity.SPSignature;

public class ChangePinParameters {
    private PINType pinType;
    private String oldPin;
    private String newPin;
    private SPSignature spSignature;

    public ChangePinParameters(PINType pinType, String oldPin, String newPin, SPSignature spSignature) {
        this.pinType = pinType;
        this.oldPin = oldPin;
        this.newPin = newPin;
        this.spSignature = spSignature;
    }

    public PINType getPinType() {
        return pinType;
    }

    public void setPinType(PINType pinType) {
        this.pinType = pinType;
    }

    public String getOldPin() {
        return oldPin;
    }

    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    public SPSignature getSpSignature() {
        return spSignature;
    }

    public void setSpSignature(SPSignature spSignature) {
        this.spSignature = spSignature;
    }
}
