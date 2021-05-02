package component;

import common.GlobalVariables;
import common.PKIUtil;
import common.RPS;
import common.Util;
import exceptions.GlobalException;
import exceptions.IdentificationException;
import mdas.MDASObject;
import model.ServiceParameters.AuthenticationParameters;
import model.ServiceParameters.ChangePinParameters;
import model.ServiceParameters.InitializeParameters;
import model.ServiceParameters.SignParameters;
import model.sdkentity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

import static model.sdkentity.Result.ResultType.*;

@Component
public class Identification {

    private static final Logger logger = LoggerFactory.getLogger(Identification.class);

    @Autowired
    MDASObject MDIS;

    public InitializeResult initializeSDK(String type)  {
        InitializeParameters initializeParameters = createInitializeParameters();
        if(type.equals("stk"))
            initializeParameters.getUiOptions().setShowFingerPrintUI(true);
        InitializeResult initializeResult ;
        logger.info("--[ Service is initializing ]-------------------------------------");
        try {
            initializeResult = MDIS.initializev1( initializeParameters.getDeviceInfo(), initializeParameters.getServerAddress(), initializeParameters.getUiOptions());
            if (initializeResult.getResultType() == SUCCESS) {
                logger.info("InitializeSDK completed successfully! \u221A");
            }
            else {
                logger.info("Response status: Unsigned Exception X ");
                ResultException resultException = initializeResult.getResultException();
                throw new IdentificationException(initializeResult.getResultType().toString(),resultException.getCategory(),
                        resultException.getCause(),resultException.getField(), String.valueOf(resultException.getRetry()),
                        resultException.getCategory() + resultException.getCause() + resultException.getField(),"Response status: Unsigned Exception X");
            }
        } catch (Exception e) {
            throw new GlobalException("InitializeSDK");
        }
        return initializeResult;
    }

    public ArrayList<ResponseInfo> authenticate(InitializeResult initializeResult){

        return null;
    }

    public FingerIndexResult getMocFingerIndex()  {
        FingerIndexResult fingerIndexResult = MDIS.getMocFingerIndexv1();
        logger.info("--[ MocFingerIndex Response ]-------------------------------------");
        if (fingerIndexResult.getResultType() == SUCCESS)
            logger.info("Response status: Success \u221A");
            return fingerIndexResult;
    }

    private static InitializeParameters createInitializeParameters() {
        return new InitializeParameters(
                GlobalVariables.getServerAddress(),
                new DeviceInfo("", "", ""),
                new UIOptions(
                        true,
                        false,
                        false,
                        new WindowSize(0, 0),
                        new WindowLocation(0, 0),
                        new Color(0, 0, 0),
                        ""));
    }

    public ArrayList<ResponseInfo> fingerprintauthentication(LevelOfAssurance levelOfAssurance, InitializeResult initializeResult)  {
        AuthenticationParameters parameters = createFingerAuthenticationParametersSDK();
        AuthenticationResult authenticationResult = MDIS.authenticatev1(levelOfAssurance, parameters.getCredentials(), parameters.getScope(), parameters.getSpSignature());
        if (authenticationResult.getResultType().equals(AuthenticationResult.ResultType.SUCCESS)) {
            logger.info("authenticate result is SUCCESS.");
            // Get assertion.
            Assertion assertion = authenticationResult.getAssertion();
            ArrayList<ResponseInfo> responseInfos = assertion.getResponseInfos();
            logger.info("Authentication successful");
            return responseInfos;
        } else {
            logger.info("authenticate result is UNSIGNED_EXCEPTION.");
            ResultException resultException = authenticationResult.getResultException();
            throw new IdentificationException(initializeResult.getResultType().toString(),resultException.getCategory(),
                    resultException.getCause(),resultException.getField(), String.valueOf(resultException.getRetry()),
                    resultException.getCategory() + resultException.getCause() + resultException.getField(),"authenticate result is UNSIGNED_EXCEPTION");
        }
    }

    private static AuthenticationParameters createFingerAuthenticationParametersSDK()  {
        LevelOfAssurance levelOfAssurance = new LevelOfAssurance(
                LevelOfAssurance.AuthenticationMethod.PIN_FP,
                false,
                false
        );

        Scope scope = new Scope(
                Scope.Source.CARD,
                new ArrayList<RequestedCitizenInfo>() {{
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.NAME, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.SURNAME, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.NID, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.FATHER_NAME, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.GENDER, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.DATE_OF_BIRTH, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.ISSUED_LOCATION, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.POSTAL_INFO, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.FACE_INFO, true));
                }},
                new ArrayList<Scope.SupplementInfo>() {{
                    add(Scope.SupplementInfo.AFIS_CHECKED);
                    add(Scope.SupplementInfo.IDENTITY_CHANGED);
                    add(Scope.SupplementInfo.REPLICA);
                    add(Scope.SupplementInfo.CARD_ISSUANCE_DATE);
                    add(Scope.SupplementInfo.CARD_EXPIRATION_DATE);
                }}
        );

        Credentials credentials = new Credentials( "", "", new FingerPrint(), new FingerPrint());
        SPSignature spSignature = RPS.generateSPSignature(null, "");
        return new AuthenticationParameters(levelOfAssurance, credentials, scope, spSignature);
    }

    public ArrayList<ResponseInfo> pinAuthenticationStk(InitializeResult initializeResult) {
        AuthenticationParameters  parameters = createPinParameters() ;
        AuthenticationResult authenticationResult = MDIS.authenticatev1(parameters.getLevelOfAssurance(), parameters.getCredentials(), parameters.getScope(), parameters.getSpSignature());
        if (authenticationResult.getResultType().equals(AuthenticationResult.ResultType.SUCCESS)) {
            logger.info("authenticate result is SUCCESS.");
            // Get assertion.
            Assertion assertion = authenticationResult.getAssertion();
            ArrayList<ResponseInfo> responseInfos = assertion.getResponseInfos();
            logger.info("Authentication successful");
            return responseInfos;
        } else {
            logger.info("authenticate result is UNSIGNED_EXCEPTION.");
            ResultException resultException = authenticationResult.getResultException();
            throw new IdentificationException(initializeResult.getResultType().toString(),resultException.getCategory(),
                    resultException.getCause(),resultException.getField(), String.valueOf(resultException.getRetry()),
                    resultException.getCategory() + resultException.getCause() + resultException.getField(),"authenticate result is UNSIGNED_EXCEPTION");
        }
    }
    private static AuthenticationParameters createPinParameters()  {
        LevelOfAssurance levelOfAssurance = new LevelOfAssurance(
                LevelOfAssurance.AuthenticationMethod.PIN,
                false,
                false
        );

        Scope scope = new Scope(Scope.Source.CARD,
                new ArrayList<RequestedCitizenInfo>() {{
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.NAME, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.SURNAME, true));
                }},
                new ArrayList<Scope.SupplementInfo>() {{
                    add(Scope.SupplementInfo.REPLICA);
                }}
        );

        Credentials credentials = new Credentials("", "", new FingerPrint(), new FingerPrint());

        SPSignature spSignature = RPS.generateSPSignature(null, "");
        return new AuthenticationParameters(levelOfAssurance, credentials, scope, spSignature);
    }

    public ChangePINResult pinChangeOwn(InitializeResult initializeResult) throws Exception {
        ChangePinParameters changePinParameters = createChangePinParameters();
        ChangePINResult changePinResult = MDIS.changePinv1(changePinParameters.getPinType(), changePinParameters.getOldPin(), changePinParameters.getNewPin(), changePinParameters.getSpSignature());
        logger.info("--[ ChangePin Response ]-------------------------------------");
        if(changePinResult.getResultType().equals(SUCCESS)) {
            logger.info("Response status: Success \u221A");
            RPS.verifyChangePinResponse(changePinParameters.getSpSignature(), changePinResult);
        }  else if(changePinResult.getResultType().equals(SIGNED_EXCEPTION)) {
            logger.info("Response status: Signed Exception X ");
            ResultException resultException = changePinResult.getResultException();
            RPS.verifyChangePinResponse(changePinParameters.getSpSignature(), changePinResult);
            throw new IdentificationException(initializeResult.getResultType().toString(),resultException.getCategory(),
                    resultException.getCause(),resultException.getField(), String.valueOf(resultException.getRetry()),
                    resultException.getCategory() + resultException.getCause() + resultException.getField(),"Response status: Signed Exception");
        } else if(changePinResult.getResultType().equals(UNSIGNED_EXCEPTION)) {
            logger.info("Response status: Unsigned Exception X ");
            ResultException resultException = changePinResult.getResultException();
            throw new IdentificationException(initializeResult.getResultType().toString(),resultException.getCategory(),
                    resultException.getCause(),resultException.getField(), String.valueOf(resultException.getRetry()),
                    resultException.getCategory() + resultException.getCause() + resultException.getField(),"Response status: Unsigned Exception");
        }
        return changePinResult;
    }

    //Creates a change parameters object for changin ID pin with fixed values
    private static ChangePinParameters createChangePinParameters() {
        PINType pinType = PINType.ID_PIN;
        String oldPin = "";
        String newPin = "";
        SPSignature spSignature = RPS.generateSPSignature(null, "");

        return new ChangePinParameters(pinType, newPin, oldPin, spSignature);
    }

    public void finalizeSdk() {
        MDIS.finalizev1();
        logger.info("finalizeSdk OK.");

    }

    public SignatureResult signOwn(InitializeResult initializeResult) throws Exception{
        SignParameters signParameters = createSignParameters();
        SignatureResult signatureResult = MDIS.signv1(signParameters.getData(), signParameters.getCredentials(),  signParameters.getTypeOfSignature(), signParameters.getSpSignature());
        logger.info("------------[ Sign Response ]-------------------------------------");
        if(signatureResult.getResultType().equals(SUCCESS)) {
            logger.info("Response status: Success \u221A");
            RPS.verifySignResponse(signParameters.getSpSignature(), signParameters.getData(), signatureResult);
        }else if(signatureResult.getResultType().equals(SIGNED_EXCEPTION)) {
            logger.info("Response status: Signed Exception X ");
            RPS.verifySignResponse(signParameters.getSpSignature(), signParameters.getData(), signatureResult);
            ResultException resultException = signatureResult.getResultException();
            throw new IdentificationException(initializeResult.getResultType().toString(),resultException.getCategory(),
                    resultException.getCause(),resultException.getField(), String.valueOf(resultException.getRetry()),
                    resultException.getCategory() + resultException.getCause() + resultException.getField(),"Response status: Signed Exception");

        }else if(signatureResult.getResultType().equals(UNSIGNED_EXCEPTION)) {
            logger.info("Response status: Unsigned Exception X ");
            ResultException resultException = signatureResult.getResultException();
            throw new IdentificationException(initializeResult.getResultType().toString(),resultException.getCategory(),
                    resultException.getCause(),resultException.getField(), String.valueOf(resultException.getRetry()),
                    resultException.getCategory() + resultException.getCause() + resultException.getField(),"Response status: Signed Exception");
        }
        return signatureResult;
    }

    private static SignParameters createSignParameters() {
        TypeOfSignature typeOfSignature = new TypeOfSignature (
                false,
                false
        );
        Credentials credentials = new Credentials(
                "",
                "",
                new FingerPrint(),
                new FingerPrint()
        );

        String data = Util.byteArrayToBase64String/*byteArrayToHexString*/(PKIUtil.hash256Generator("The String which is needed to be signed by citizen certificate.".getBytes()));//change
        SPSignature spSignature = RPS.generateSPSignature(null, data);

        return new SignParameters(typeOfSignature, credentials, data, spSignature);
    }

    public ArrayList<ResponseInfo> fingerprintauthenticationOwn(FingerIndexResult fingerIndexResult, InitializeResult initializeResult) throws Exception {
        AuthenticationParameters parameters = createFingerAuthenticationParametersSP(fingerIndexResult);
        AuthenticationResult authenticationResult = MDIS.authenticatev1(parameters.getLevelOfAssurance(), parameters.getCredentials(), parameters.getScope(), parameters.getSpSignature());
        if (authenticationResult.getResultType().equals(AuthenticationResult.ResultType.SUCCESS)) {
            logger.info("authenticate fingerprintauthenticationOwn result is SUCCESS.");
            // Get assertion.
            Assertion assertion = authenticationResult.getAssertion();
            ArrayList<ResponseInfo> responseInfos = assertion.getResponseInfos();
            logger.info("Authentication fingerprintauthenticationOwn successful");
            return responseInfos;
        } else {
            logger.info("authenticate result fingerprintauthenticationOwn is UNSIGNED_EXCEPTION.");
            ResultException resultException = authenticationResult.getResultException();
            throw new IdentificationException(initializeResult.getResultType().toString(),resultException.getCategory(),
                    resultException.getCause(),resultException.getField(), String.valueOf(resultException.getRetry()),
                    resultException.getCategory() + resultException.getCause() + resultException.getField(),"authenticate fingerprintauthenticationOwn result is UNSIGNED_EXCEPTION");
        }
    }
    //Creates an authentication parameters object for pin and fingerprint authentication
    private static AuthenticationParameters createFingerAuthenticationParametersSP(FingerIndexResult fingerIndexresult) throws Exception {
        LevelOfAssurance loa = new LevelOfAssurance(
                LevelOfAssurance.AuthenticationMethod.PIN_FP,
                false,
                false
        );

        Scope scope = new Scope(
                Scope.Source.CARD,
                new ArrayList<RequestedCitizenInfo>() {{
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.NAME, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.SURNAME, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.NID, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.FATHER_NAME, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.DATE_OF_BIRTH, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.GENDER, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.POSTAL_INFO, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.ISSUED_LOCATION, true));
                    add(new RequestedCitizenInfo(RequestedCitizenInfo.InfoType.FACE_INFO, true));
                }},
                new ArrayList<Scope.SupplementInfo>() {{
                    add(Scope.SupplementInfo.REPLICA);
                }}
        );

        FaceData faceData = new FaceData(10, 5, 3, new byte[] { 23, 13, 12, 11, 20 , 23, 13, 12, 11, 20 });

        Credentials credentials = new Credentials(
                "",
                "",
                new FingerPrint(
                        fingerIndexresult.getFinger1(),
                        FingerPrint.FingerPrintType.Feature_CC,
                        Util.loadSampleFingerPrint("SampleFinger_Feature_1.minutea"),
                        0,
                        0,
                        0,
                        64
                ),
                new FingerPrint(),
                faceData
        );

        SPSignature spSignature = RPS.generateSPSignature(null, "");

        return new AuthenticationParameters(loa, credentials, scope, spSignature);
    }

    public static void main(String[] args) {
        try {
            Util.loadSampleFingerPrint("SampleFinger_Feature_1.minutea");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
