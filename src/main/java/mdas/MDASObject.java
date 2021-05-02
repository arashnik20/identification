package mdas;

import common.Util;
import model.sdkentity.*;
import org.springframework.stereotype.Component;

@Component
public class MDASObject {
    static {
        try {
            if(System.getProperty("sun.arch.data.model").contains("64"))
                System.loadLibrary("MDASJNIWrapper_x64");
            else
                System.loadLibrary("MDASJNIWrapper_x86");
        } catch (Exception e) {
            Util.log("Error in loading DLL");
        }
    }

    public native InitializeResult initializev1(DeviceInfo deviceInfo, String serverPath, UIOptions uiOptions);

    public native FingerIndexResult getMocFingerIndexv1();

    public native AuthenticationResult authenticatev1(LevelOfAssurance loa, Credentials credentials, Scope scope, SPSignature spsignature);

    public native SignatureResult signv1(String toBeSignedData, Credentials credentials, TypeOfSignature typeOfSignature, SPSignature spSignature);

    public native ChangePINResult changePinv1(PINType pinType, String oldPin, String newPin, SPSignature spSignature);

    public native void finalizev1();

}
