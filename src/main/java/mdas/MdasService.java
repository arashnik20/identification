package mdas;

import common.GlobalVariables;
import common.RPS;
import common.Util;
import model.ServiceParameters.AuthenticationParameters;
import model.ServiceParameters.ChangePinParameters;
import model.ServiceParameters.InitializeParameters;
import model.ServiceParameters.SignParameters;
import model.sdkentity.*;

import static model.sdkentity.Result.ResultType.SUCCESS;

public class MdasService {
    //region [ MDAS Object ]

    private MDASObject _MDAS;

    public MdasService() {
        this._MDAS = new MDASObject();
    }

    private MDASObject getMDAS() {
        if (_MDAS == null) {
            _MDAS = new MDASObject();
        }
        return _MDAS;
    }

    //endregion [ MDAS Object ]

    //region [ MDAS Main Functions ]

    /// <summary>
    /// Initializes sdk, Read complete process of initialization in provided documents
    /// </summary>
    /// <param name="initializeParameters">An object of InitializeParameters filled with static data</param>
    /// <returns>Throws exception if initialization failed...</returns>
    public void initializeSDK(InitializeParameters initializeParameters) throws Exception {
        Util.log("\n--[ Service is initializing ]-------------------------------------");
        try {
            InitializeResult initializeResult = getMDAS().initializev1( initializeParameters.getDeviceInfo(), initializeParameters.getServerAddress(), initializeParameters.getUiOptions());
            if (initializeResult.getResultType() == SUCCESS) {
                Util.log("InitializeSDK completed successfully! \u221A");
            }
            else {
                Util.log("Response status: Unsigned Exception X ");
                throw new Exception(getMDASExceptionMessage(initializeResult));
            }
        } catch (Exception e) {
            throw new Exception("InitializeSDK failed!", e);
        }
    }

    /// <summary>
    /// After you are done with server you need to inform it by calling finalize function
    /// </summary>
    public void finalizeSDK() throws Exception {
        Util.log("\n--[ Finalizing Service ]-------------------------------------");
        try {
            getMDAS().finalizev1();
            Util.log("Service finalized successfully! \u221A");
        } catch (Exception e) {
            Util.log("Exception info " + e.getMessage(), e, GlobalVariables.exceptionSourceSample);
        }
    }

    /// <summary>
    /// authenticate citizen, Read complete process of Authentication method in provided documents
    /// </summary>
    /// <param name="authenticationParameters">An object of AuthenticationParameters filled with static data</param>
    public AuthenticationResult authenticate(AuthenticationParameters authenticationParameters) {
        AuthenticationResult authenticationResult = null ;
        String response = "" ;
        try {
            authenticationResult = getMDAS().authenticatev1(
                    authenticationParameters.getLevelOfAssurance(),
                    authenticationParameters.getCredentials(),
                    authenticationParameters.getScope(),
                    authenticationParameters.getSpSignature()
            );

            Util.log("\n--[ Authenticate Response ]-------------------------------------");
            switch (authenticationResult.getResultType()) {
                case SUCCESS: {
                    Util.log("Response status: Success \u221A");

                    response = RPS.verifyAuthenticationResponse(authenticationParameters.getSpSignature(), authenticationParameters.getLevelOfAssurance(), authenticationResult);
                    // The received result is a valid mdas response
                    // You can continue you business here
                }
                break;
                case SIGNED_EXCEPTION: {
                    Util.log("Response status: Signed Exception X ");
//                    printMDASException(new Exception(getMDASExceptionMessage(authenticationResult)));
                     response = RPS.verifyAuthenticationResponse(authenticationParameters.getSpSignature(), authenticationParameters.getLevelOfAssurance(), authenticationResult);
//                    return getMDASExceptionMessage(authenticationResult);
                    // The received result is a valid mdas response
                    // Here some exception happened you need to handle it considering the singed exception
                }
                break;
                case UNSIGNED_EXCEPTION: {
                    Util.log("Response status: Unsigned Exception X ");
//                    printMDASException(new Exception(getMDASExceptionMessage(authenticationResult)));
//                    return getMDASExceptionMessage(authenticationResult);
                    response = "UNSIGNED_EXCEPTION" ;
                }
                break;
            }
        } catch (Exception e) {
            Util.log("Authenticate failed! " + e.getMessage(), e, GlobalVariables.exceptionSourceSample);
        }
//        return getMDASExceptionMessage(authenticationResult);
        return authenticationResult ;
    }

    /// <summary>
    /// Sign data , Read complete process of sign method in provided documents
    /// </summary>
    /// <param name="signParameters">An object of SignParameters filled with static data</param>
    public SignatureResult Sign(SignParameters signParameters) {
        SignatureResult signatureResult = null;
        try {
             signatureResult = getMDAS().signv1(signParameters.getData(), signParameters.getCredentials(),  signParameters.getTypeOfSignature(), signParameters.getSpSignature());

            Util.log("\n--[ Sign Response ]-------------------------------------");
            switch (signatureResult.getResultType()) {
                case SUCCESS: {
                    Util.log("Response status: Success \u221A");

                    RPS.verifySignResponse(signParameters.getSpSignature(), signParameters.getData(), signatureResult);
                    // The received result is a valid mdas response
                    // You can continue you business here
                }
                break;
                case SIGNED_EXCEPTION: {
                    Util.log("Response status: Signed Exception X ");
                    printMDASException(new Exception(getMDASExceptionMessage(signatureResult)));

                    RPS.verifySignResponse(signParameters.getSpSignature(), signParameters.getData(), signatureResult);
                    // The received result is a valid mdas response
                    // You can continue you business here
                }
                break;
                case UNSIGNED_EXCEPTION: {
                    Util.log("Response status: Unsigned Exception X ");
                    printMDASException(new Exception(getMDASExceptionMessage(signatureResult)));
                }
                break;
            }
        } catch (Exception e) {
            Util.log("Sign failed! " + e.getMessage(), e, GlobalVariables.exceptionSourceSample);
        }
        return signatureResult;
    }

    /// <summary>
    /// When you want to capture finger print by yourself (Set UiOption to false)
    /// you need to get finger indexes in the beginning then you can start capture finger prints
    /// </summary>
    /// <returns>Finger index result which contains finger index 1 and 2</returns>
    public FingerIndexResult getMocFingerIndex() throws Exception {
        FingerIndexResult fingerIndexResult = getMDAS().getMocFingerIndexv1();

        Util.log("\n--[ MocFingerIndex Response ]-------------------------------------");
        if (fingerIndexResult.getResultType() == SUCCESS) {
            Util.log("Response status: Success \u221A");

            return fingerIndexResult;
        } else {
            Util.log("Response status: Unsigned Exception X ");
            throw new Exception(getMDASExceptionMessage(fingerIndexResult));
        }
    }

    /// <summary>
    /// Change card's pins, Read complete process of changePin method in provided documents
    /// </summary>
    /// <param name="changePinParameters">An object of ChangePinParameters filled with static data</param>
    public void changePin(ChangePinParameters changePinParameters) throws Exception {
        try {
            ChangePINResult changePinResult = getMDAS().changePinv1(changePinParameters.getPinType(), changePinParameters.getOldPin(), changePinParameters.getNewPin(), changePinParameters.getSpSignature());

            Util.log("\n--[ ChangePin Response ]-------------------------------------");
            switch (changePinResult.getResultType()) {
                case SUCCESS: {
                    Util.log("Response status: Success \u221A");

                    RPS.verifyChangePinResponse(changePinParameters.getSpSignature(), changePinResult);
                    // The received result is a valid mdas response
                    // You can continue you business here
                }
                break;
                case SIGNED_EXCEPTION: {
                    Util.log("Response status: Signed Exception X ");
                    printMDASException(new Exception(getMDASExceptionMessage(changePinResult)));

                    RPS.verifyChangePinResponse(changePinParameters.getSpSignature(), changePinResult);
                    // The received result is a valid mdas response
                    // You can continue you business here
                }
                break;
                case UNSIGNED_EXCEPTION: {
                    Util.log("Response status: Unsigned Exception X ");
                    printMDASException(new Exception(getMDASExceptionMessage(changePinResult)));
                }
                break;
            }
        } catch (Exception e) {
            Util.log("ChangePin failed!" + e.getMessage(), e, GlobalVariables.exceptionSourceSample);
        }
    }

    //endregion [ MDAS Main Functions ]

    //region [ Private Functions ]

    /// <summary>
    /// Print exception values
    /// </summary>
    /// <param name="result">ResultException object</param>
    private void printMDASException(Exception exception) {
        Util.log(exception.getMessage(), exception, GlobalVariables.exceptionSourceMDAS);
    }

      private String getMDASExceptionMessage(Result result) {
            if(result == null)
                return "" ;
            String resultType = result.getResultType().toString();
            ResultException resultException = result.getResultException();

            String errorText = "{type :" + resultType +
                    ", source: " + (resultException != null ? resultException.getCategory() : "" )+
                    ", cause: " + (resultException != null ? resultException.getCause() : "") +
                    ", field: " + (resultException != null ? resultException.getField() : "" )+
                    (resultException != null && resultException.getRetry() >= 0 ?  ", retry: " + resultException.getRetry() : "" ) +
                    ", code: " + (resultException != null ? resultException.getCategory() + resultException.getCause() + resultException.getField() : "")  + " }";
            return errorText;
        }
//    private ResponseDto getMDASExceptionMessage(Result result) {
//        ResponseDto dto = new ResponseDto();
//        if (result == null) {
//            dto.setMode("no response from service");
//            return dto;
//        }
//        String resultType = result.getResultType().toString();
//        ResultException resultException = result.getResultException();
//        dto.setType(resultType);
//        if(resultException != null){
//            dto.setSource(resultException.getCategory());
//            dto.setCause(resultException.getCause());
//            dto.setField(resultException.getField());
//            if(resultException.getRetry() >= 0)
//                dto.setRetry(String.valueOf(resultException.getRetry()));
//            dto.setCode(resultException.getCategory() + resultException.getCause() + resultException.getField());
//        }
//        return dto;
//    }
    //endregion [ Private Functions ]
}

