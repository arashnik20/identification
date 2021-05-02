package app;

import common.GlobalVariables;
import common.PKIUtil;
import common.RPS;
import common.Util;
import mdas.MdasService;
import model.sdkentity.*;
import model.ServiceParameters.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public void getPinAuthentication () throws Exception {
        MdasService mdasSDK = new MdasService();
        InitializeParameters initializeParameters = createInitializeParameters();
        mdasSDK.initializeSDK(initializeParameters);
        FingerIndexResult fingerIndexResult = mdasSDK.getMocFingerIndex();
        System.out.println(fingerIndexResult.getFinger1());
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        try {
            MdasService mdasSDK = new MdasService();

            Integer option1 = -1;
            // Repeats till you input 5
            while (option1 != 5) {
                showMenu1();
                String userInput = scanner.next();

                while ((option1 = Util.tryParse(userInput)) == null || option1 > 5 || option1 < 1) {
                    System.out.print("Please select an option and press enter : ");
                    userInput = scanner.next();
                }
                System.out.println();

                switch (option1) {
                    case 1:
                        Util.clearConsole();
                        InitializeParameters initializeParameters = createInitializeParameters();
                        mdasSDK.initializeSDK(initializeParameters);

                        Integer option2 = -1;
                        while (option2 != 5 && option2 != 4) {
                            showMenu12();
                            userInput = scanner.next();

                            while ((option2 = Util.tryParse(userInput)) == null || option2 > 5 || option1 < 1) {
                                System.out.print("Please select an option and press enter : ");
                                userInput = scanner.next();
                            }
                            System.out.println();

                            switch (option2) {
                                case 1:
                                    FingerIndexResult fingerIndexResult;
                                    try {
                                        fingerIndexResult = mdasSDK.getMocFingerIndex();
                                        System.out.println(fingerIndexResult.getFinger1() + " " + fingerIndexResult.getFinger2());
                                    } catch (Exception e) {
                                        Util.log(e.getMessage(), e, GlobalVariables.exceptionSourceMDAS);
                                        System.out.println("Press [ENTER] to continue...");
                                        Util.seekForEnter();
                                        break;
                                    }

                                    if (fingerIndexResult.getFinger1() == 0 && fingerIndexResult.getFinger2() == 0) {
                                        //This means there is no fingerprint in card so finger print authentication is impossible
                                        //In this case you can use PIN_PIN authentication in order to authenticate citizen with NMOC (Fingerprint pin)
                                        Util.log("This card has no fingerprint; Please use PIN_PIN authentication method",
                                                new Exception("This card has no fingerprint; Please use PIN_PIN authentication method"), GlobalVariables.exceptionSourceSample);
                                    }
                                    else {
                                        //todo use
                                        AuthenticationParameters authenticationParameters = createFingerAuthenticationParametersSP(fingerIndexResult);
                                        mdasSDK.authenticate(authenticationParameters);
                                    }
                                    System.out.println("Press [ENTER] to continue...");
                                    Util.seekForEnter();
                                    break;
                                case 2:
                                    SignParameters signParameters = createSignParameters();
                                    mdasSDK.Sign(signParameters);

                                    System.out.println("Press [ENTER] to continue...");
                                    Util.seekForEnter();
                                    break;
                                case 3:
                                    ChangePinParameters changePinParameters = createChangePinParameters();
                                    mdasSDK.changePin(changePinParameters);

                                    System.out.println("Press [ENTER] to continue...");
                                    Util.seekForEnter();
                                    break;
                                case 5:
                                    option1 = 5;
                                    break;
                            }
                            Util.clearConsole();
                        }
                        break;
                    case 2:
                        Util.clearConsole();

                        initializeParameters = createInitializeParameters();
                        initializeParameters.getUiOptions().setShowFingerPrintUI(true);
                        mdasSDK.initializeSDK(initializeParameters);

                        option2 = -1;
                        while (option2 != 4 && option2 != 5) {
                            showMenu11();
                            userInput = scanner.next();

                            while ((option2 = Util.tryParse(userInput)) == null || option2 > 5 || option1 < 1) {
                                System.out.println("Please select an option and press enter : ");
                                userInput = scanner.next();
                            }
                            System.out.println();

                            switch (option2) {
                                case 1:
                                    AuthenticationParameters authenticationParameters;

                                    // Here you need to check whether this citizen has Fingerprint or not
                                    FingerIndexResult fingerIndexResult;
                                    try {
                                        fingerIndexResult = mdasSDK.getMocFingerIndex();
                                    } catch (Exception e) {
                                        Util.log(e.getMessage(), e, GlobalVariables.exceptionSourceMDAS);

                                        System.out.println("Press [ENTER] to continue...");
                                        Util.seekForEnter();
                                        break;
                                    }

                                    if (fingerIndexResult.getFinger1() == 0 && fingerIndexResult.getFinger2() == 0) {
                                        //This means there is not fingerprint in card so finger print authen tication is impossible
                                        //In this case you can use PIN_PIN authentication in order to authenticate citizen with NMOC (Fingerprint pin)
                                        Util.log("This card has no fingerprint; Please use PIN_PIN authentication method",
                                                new Exception("This card has no fingerprint; Please use PIN_PIN authentication method"), GlobalVariables.exceptionSourceSample);
                                    } else {
                                        authenticationParameters = createFingerAuthenticationParametersSDK();
                                        mdasSDK.authenticate(authenticationParameters);
                                    }

                                    System.out.println("Press [ENTER] to continue...");
                                    Util.seekForEnter();
                                    break;
                                case 2:
                                    // In order to change PINType.NMOC_PIN you need to, first make sure there is no finger in card like we did in case 1
                                    authenticationParameters = createPinParameters();
                                    mdasSDK.authenticate(authenticationParameters);

                                    System.out.println("Press [ENTER] to continue...");
                                    Util.seekForEnter();
                                    break;
                                case 5:
                                    option1 = 5;
                                    break;
                            }
                            Util.clearConsole();
                        }
                        break;
                }
            }
            mdasSDK.finalizeSDK();
        } catch (Exception e) {
            Util.log(e.getMessage(), e, GlobalVariables.exceptionSourceMDAS);
            System.out.println("Press [ENTER] to continue...");
            Util.seekForEnter();
        }
    }

    //region [ Menus ]

    private static void showMenu1() {
        Util.clearConsole();
        System.out.println("\nPlease select input option");
        System.out.println("----------------------------");
        System.out.println("1- Capture fingerprint by your own method.");
        System.out.println("2- Capture fingerprint by SDK.");
        System.out.println("5- Exit");
        System.out.println();
        System.out.print("Please select an option and press enter : ");
    }

    private static void showMenu11() {
        System.out.println("\nSelect service");
        System.out.println("----------------------------");
        System.out.println("1- Fingerprint authentication");
        System.out.println("2- Pin authentication");
        System.out.println("4- Previous menu");
        System.out.println("5- Exit");
        System.out.println();
        System.out.print("Please select an option and press enter : ");
    }

    private static void showMenu12() {
        System.out.println("\nSelect service");
        System.out.println("----------------------------");
        System.out.println("1- Fingerprint authentication");
        System.out.println("2- Sign");
        System.out.println("3- PIN change");
        System.out.println("4- Previous menu");
        System.out.println("5- exit");
        System.out.println();
        System.out.print("Please select an option and press enter : ");
    }
    
    //endregion [ Menus ]

    //region [ Create Initialize Parameters ]

    //Creates an initialization parameters object
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

    //endregion [ Create Initialize Parameters ]

    //region [ Create Authentication Parameters ]

    //Creates an authentication parameters object for pin authentication
    private static AuthenticationParameters createPinParameters() throws Exception {
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

    //Creates an authentication parameters object for pin authentication
    private static AuthenticationParameters createFingerAuthenticationParametersSDK() throws Exception {
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

        Credentials credentials = new Credentials( "", "", new FingerPrint(), new FingerPrint());
        SPSignature spSignature = RPS.generateSPSignature(null, "");
        return new AuthenticationParameters(levelOfAssurance, credentials, scope, spSignature);
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

    //endregion [ Create Authentication Parameters]

    //region [ Create Sign Parameters ]

    //Creates a sign parameters object with fixed values
    private static SignParameters createSignParameters() throws Exception {
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

    //endregion [ Create Sign Parameters ]

    //region [ Create ChangePin Parameters ]

    //Creates a change parameters object for changin ID pin with fixed values
    private static ChangePinParameters createChangePinParameters() throws Exception {
        PINType pinType = PINType.ID_PIN;
        String oldPin = "";
        String newPin = "";
        SPSignature spSignature = RPS.generateSPSignature(null, "");

        return new ChangePinParameters(pinType, newPin, oldPin, spSignature);
    }

    //endregion [ Create ChangePin Parameters ]
}

