package common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.sdkentity.*;
import org.joda.time.DateTime;

import java.util.Optional;

import static model.sdkentity.Result.ResultType.SUCCESS;

public class RPS {
    //region [ Generate SPSignature ]

    //The main purpose of this function is to create spSignature
    public static SPSignature generateSPSignature(Long nonce, String data)  {
        if (nonce == null) {
            nonce = PKIUtil.generateNonce();
        }

        SPSignature spSignature = new SPSignature(
                GlobalVariables.getSPID(),
                nonce,
                PKIUtil.generateTimestamp(),
                new Signature(
                        GlobalVariables.signatureAlgorithm,
                        GlobalVariables.hashAlgorithm,
                        GlobalVariables.algorithmVersion,
                        "", null, null, ""
                )
        );
        String signedData = spSignature.getSPID() + Long.toUnsignedString(spSignature.getNonce()) + spSignature.getTimestamp() + data;
        spSignature.getSignature().setSignatureValue(PKIUtil.signData(signedData, spSignature.getSignature().getSignatureAlgorithm(), GlobalVariables.getKeyStoreName(), GlobalVariables.getKeyStorePassword()));
        return spSignature;
    }

    //endregion [ Generate SPSignature ]

    //region [ RPS Services ]

    public static String verifyAuthenticationResponse(SPSignature spSignature, LevelOfAssurance levelOfAssurance, AuthenticationResult authenticationResult) throws Exception {
        Util.log("\n--[ Authentication Response Verification ]-------------------------------------");

        switch (authenticationResult.getResultType()) {
            case SUCCESS: {
                //region [ Response Validation ]
                try {
                    validateAssertion(levelOfAssurance, authenticationResult.getAssertion().getLOA());
                    validateResponse(spSignature.getNonce(), authenticationResult.getNonce(),
                            spSignature.getSPID(), authenticationResult.getSpId(),
                            spSignature.getTimestamp(), authenticationResult.getSpTimestamp(),
                            authenticationResult.getMdasTimestamp());
                    Util.log("\u221A Response validation done.");

                } catch (Exception e) {
                    throw new Exception("X Response validation failed!", e);
                }
                //endregion

                //region [ Signature Validation ]
                try {
                    PKIUtil.verifyCertificate(PKIUtil.stringToCertificate(GlobalVariables.mdasRootCertificate),
                            PKIUtil.base64StringToCertificate(authenticationResult.getSignature().getCACertificate()),
                            PKIUtil.base64StringToCertificate(authenticationResult.getSignature().getEndCertificate()),
                            GlobalVariables.mdasPolicyIdentifier,
                            GlobalVariables.mdasCACertCN);
                    String data = generatePlainData(authenticationResult);
                    PKIUtil.verifySignature(PKIUtil.base64StringToCertificate(authenticationResult.getSignature().getEndCertificate()),
                            data,
                            authenticationResult.getSignature().getSignatureValue());
//                    Util.log("\u221A Server signature verified successfully.");
                } catch (Exception e) {
                    throw new Exception("X Signature validation failed!", e);
                }
                //endregion
            }
            break;
            case SIGNED_EXCEPTION: {
                //region [ Response Validation ]
                try {
                    validateResponse(spSignature.getNonce(), authenticationResult.getNonce(),
                            spSignature.getSPID(), authenticationResult.getSpId(),
                            spSignature.getTimestamp(), authenticationResult.getSpTimestamp(),
                            authenticationResult.getMdasTimestamp());
                    Util.log("\u221A Response validation done.");
                } catch (Exception e) {
                    throw new Exception("X Response validation failed!", e);
                }
                //endregion

                //region [ Signature Validation ]
                try {
                    PKIUtil.verifyCertificate(PKIUtil.stringToCertificate(GlobalVariables.mdasRootCertificate),
                            PKIUtil.base64StringToCertificate(authenticationResult.getSignature().getCACertificate()),
                            PKIUtil.base64StringToCertificate(authenticationResult.getSignature().getEndCertificate()),
                            GlobalVariables.mdasPolicyIdentifier,
                            GlobalVariables.mdasCACertCN);
                    String data = generatePlainData(authenticationResult);
                    PKIUtil.verifySignature(PKIUtil.base64StringToCertificate(authenticationResult.getSignature().getEndCertificate()),
                            data,
                            authenticationResult.getSignature().getSignatureValue());
                    Util.log("\u221A Server signature verified successfully.");
                } catch (Exception e) {
                    throw new Exception("X Signature validation failed!", e);
                }
                //endregion
            }
            break;
        }
        return authenticationResult.getResultType().toString() ;
    }

    public static void verifySignResponse(SPSignature spSignature, String tobeSignedData, SignatureResult signatureResult) throws Exception {
        Util.log("\n--[ Sign Response Verification ]-------------------------------------");
        switch (signatureResult.getResultType()) {
            case SUCCESS: {
                //region [ Response Validation ]
                try {
                    validateResponse(spSignature.getNonce(), signatureResult.getNonce(),
                            spSignature.getSPID(), signatureResult.getSpId(),
                            spSignature.getTimestamp(), signatureResult.getSpTimestamp());
                    Util.log("\u221A Response validation done.");
                } catch (Exception e) {
                    throw new Exception("X Response validation failed!", e);
                }
                //endregion

                //region [ Signature Validation ]
                try {
                    PKIUtil.verifyCertificate(PKIUtil.stringToCertificate(GlobalVariables.cardRootCertificate),
                            PKIUtil.base64StringToCertificate(signatureResult.getSignedData().getCitizenCACertificate()),
                            PKIUtil.base64StringToCertificate(signatureResult.getSignedData().getCitizenSignCertificate()),
                            GlobalVariables.cardPolicyIdentifier,
                            GlobalVariables.cardCACertCN);

                    PKIUtil.verifyCardSignature(PKIUtil.base64StringToCertificate(signatureResult.getSignedData().getCitizenSignCertificate()),
                            tobeSignedData,
                            signatureResult.getSignedData().getSignatureValue());
                    Util.log("\u221A Card signature verified successfully.");
                } catch (Exception e) {
                    throw new Exception("X Signature validation failed!!", e);
                }
                //endregion

                //region [ OCSP Response Validation ]
                /*String ocspStr = signatureResult.getSignedData().getRevocationData();
                PKIUtil.getOcspStatus(,)*/
                //endregion
            }
            break;
            case SIGNED_EXCEPTION: {
                //region [ Response Validation ]
                try {
                    validateResponse(spSignature.getNonce(), signatureResult.getNonce(),
                            spSignature.getSPID(), signatureResult.getSpId(),
                            spSignature.getTimestamp(), signatureResult.getSpTimestamp(),
                            signatureResult.getMdasTimestamp());
                    Util.log("\u221A Response validation done.");
                } catch (Exception e) {
                    throw new Exception("X Response validation failed!", e);
                }
                //endregion

                //region [ Signature Validation ]
                try {
                    PKIUtil.verifyCertificate(PKIUtil.stringToCertificate(GlobalVariables.mdasRootCertificate),
                            PKIUtil.base64StringToCertificate(signatureResult.getSignature().getCACertificate()),
                            PKIUtil.base64StringToCertificate(signatureResult.getSignature().getEndCertificate()),
                            GlobalVariables.mdasPolicyIdentifier,
                            GlobalVariables.mdasCACertCN);
                    String data = generatePlainData(signatureResult);
                    PKIUtil.verifySignature(PKIUtil.base64StringToCertificate(signatureResult.getSignature().getEndCertificate()),
                            data,
                            signatureResult.getSignature().getSignatureValue());
                    Util.log("\u221A Server signature verified successfully.");
                } catch (Exception e) {
                    throw new Exception("X Signature validation failed!!", e);
                }
                //endregion
            }
            break;
        }
    }

    public static void verifyChangePinResponse(SPSignature spSignature, ChangePINResult changePinResult) throws Exception {
        Util.log("\n--[ ChangePin Response Verification ]-------------------------------------");
        switch (changePinResult.getResultType()) {
            case SUCCESS: {
                //region [ Response Validation ]
                try {
                    validateResponse(spSignature.getNonce(), changePinResult.getNonce(),
                            spSignature.getSPID(), changePinResult.getSpId(),
                            spSignature.getTimestamp(), changePinResult.getSpTimestamp());
                    Util.log("\u221A Response validation done.");
                } catch (Exception e) {
                    throw new Exception("X Response validation failed!", e);
                }
                //endregion
            }
            break;
            case SIGNED_EXCEPTION: {
                //region [ Response Validation ]
                try {
                    validateResponse(spSignature.getNonce(), changePinResult.getNonce(),
                            spSignature.getSPID(), changePinResult.getSpId(),
                            spSignature.getTimestamp(), changePinResult.getSpTimestamp(),
                            changePinResult.getMdasTimestamp());
                    Util.log("\u221A Response validation done.");
                } catch (Exception e) {
                    throw new Exception("X Response validation failed!", e);
                }
                //endregion

                //region [ Signature Validation ]
                try {
                    PKIUtil.verifyCertificate(PKIUtil.stringToCertificate(GlobalVariables.mdasRootCertificate),
                            PKIUtil.base64StringToCertificate(changePinResult.getSignature().getCACertificate()),
                            PKIUtil.base64StringToCertificate(changePinResult.getSignature().getEndCertificate()),
                            GlobalVariables.mdasPolicyIdentifier,
                            GlobalVariables.mdasCACertCN);
                    String data = generatePlainData(changePinResult);
                    PKIUtil.verifySignature(PKIUtil.base64StringToCertificate(changePinResult.getSignature().getEndCertificate()),
                            data,
                            changePinResult.getSignature().getSignatureValue());
                    Util.log("\u221A Server signature verified successfully.");
                } catch (Exception e) {
                    throw new Exception("X Signature validation failed!", e);
                }
                //endregion
            }
            break;
        }
    }

    //endregion

    //region [ Private Functions ]

    /// <summary>
    /// Check request LOA parameters with response LOA parameters. Throws exception in case of inconformity
    /// </summary>
    /// <param name="spLevelOfAssurance">an object of LevelOfAssusrance which sp sends in request</param>
    /// <param name="responseLevelOfAssurance">an object of LevelOfAssusrance which sp receives in response</param>
    private static void validateAssertion(LevelOfAssurance spLevelOfAssurance, LevelOfAssurance responseLevelOfAssurance) throws Exception {
        if (responseLevelOfAssurance.getAuthenticationMethod() == spLevelOfAssurance.getAuthenticationMethod() &&
                responseLevelOfAssurance.getAuthorizationCheck() == spLevelOfAssurance.getAuthorizationCheck() &&
                responseLevelOfAssurance.getRevocationCheck() == spLevelOfAssurance.getRevocationCheck())
            Util.log("\t \u221A Level of Assurance is verified.");
        else
            throw new Exception("\t X Level of Assurance is not verified!");
    }

    /// <summary>
    /// Validates response with provided values ( for successful authentication and signed exception of all services )
    /// </summary>
    /// <param name="requestNonce">Ulong: Request nonce which sp generates before sending request</param>
    /// <param name="resultNonce">Ulong: Nonce which sp receives in response</param>
    /// <param name="requestSpId">string: spId</param>
    /// <param name="resultSpId">string: spId in response</param>
    /// <param name="requestTimestamp">string: Timestamp generated before sending request</param>
    /// <param name="resultTimestamp">string: Timestamp sp receives in response</param>
    /// <param name="mdasTimestamp">MDAS server timestamp to check timeout</param>
    private static void validateResponse(Long requestNonce, Long resultNonce,
                                         String requestSpId, String resultSpId,
                                         String requestTimestamp, String resultTimestamp,
                                         String mdasTimestamp) throws Exception {
        if (requestNonce.equals(resultNonce))
            Util.log("\t \u221A Nonce is verified.");
        else
            throw new Exception("\t X Nonce is not verified!");

        if (requestSpId.equals(resultSpId))
            Util.log("\t \u221A ID is verified.");
        else
            throw new Exception("\t X ID is not verified!");

        if (requestTimestamp.equals(resultTimestamp))
            Util.log("\t \u221A timestamp is verified.");
        else
            throw new Exception("\t X timestamp is not verified!");

        if (!(mdasTimestamp == null || mdasTimestamp.isEmpty())) {
            long diff = Math.abs(DateTime.now().minus(DateTime.parse(mdasTimestamp).getMillis()).getMillis());
            if (diff <= GlobalVariables.validTimestampDifference) {
                Util.log("\t \u221A The difference between SP-Client and mdas-Server times is verified. : " + diff);
            } else {
                throw new Exception("\t X The difference between SP-Client and mdas-Server times is not verified! : " + diff);
            }
        } else
            throw new Exception("\t X mdas timestamp must not be null or empty!");
    }

    /// <summary>
    /// Validates response with provided values ( for successful sign and changepin )
    /// </summary>
    /// <param name="requestNonce">Ulong: Request nonce which sp generates before sending request</param>
    /// <param name="resultNonce">Ulong: Nonce which sp receives in response</param>
    /// <param name="requestSpId">string: spId</param>
    /// <param name="resultSpId">string: spId in response</param>
    /// <param name="requestTimestamp">string: Timestamp generated before sending request</param>
    /// <param name="resultTimestamp">string: Timestamp sp receives in response</param>
    private static void validateResponse(Long requestNonce, Long resultNonce,
                                         String requestSpId, String resultSpId,
                                         String requestTimestamp, String resultTimestamp) throws Exception {
        if (requestNonce.equals(resultNonce))
            Util.log("\t \u221A Nonce is verified.");
        else
            throw new Exception("\t X Nonce is not verified!");

        if (requestSpId.equals(resultSpId))
            Util.log("\t \u221A ID is verified.");
        else
            throw new Exception("\t X ID is not verified!");

        if (requestTimestamp.equals(resultTimestamp))
            Util.log("\t \u221A timestamp is verified.");
        else
            throw new Exception("\t X timestamp is not verified!");
    }

    /// <summary>
    /// Generates a data according to provided result object
    /// </summary>
    /// <param name="result">Here we create a plain data to validate server signature. so we create it according to type of result</param>
    /// <returns></returns>
    private static String generatePlainData(Object result) {
        Gson g = new GsonBuilder().disableHtmlEscaping().create();

        String data = null;

        if (result instanceof AuthenticationResult) {
            AuthenticationResult authenticationResult = (AuthenticationResult) result;
            data = authenticationResult.getId() +
                    authenticationResult.getSpId() +
                    Long.toUnsignedString(authenticationResult.getNonce()) +
                    authenticationResult.getMdasTimestamp() +
                    authenticationResult.getSpTimestamp() +
                    authenticationResult.getMdasId() +
                    authenticationResult.getResultType();
            if (authenticationResult.getResultType() == SUCCESS) {
                Optional<ResponseInfo> optional = authenticationResult.getAssertion().getResponseInfos().stream().filter((r) -> (r.getInfoType().equals(ResponseInfo.InfoType.FACE_INFO))).findFirst();
                ResponseInfo responseInfo = optional.isPresent() ? optional.get() : null;
                if (responseInfo != null) {
                    String faceData = responseInfo.getValue();
                    String faceHash = Util.byteArrayToBase64String(PKIUtil.hash256Generator(Util.base64StringToByteArray(faceData)));
                    responseInfo.setValue(faceHash);
                    String assertion = g.toJson(authenticationResult.getAssertion());
                    data += assertion;
                    responseInfo.setValue(faceData);
                }
                else {
                    data += g.toJson(authenticationResult.getAssertion());
                }
            } else {
                // You don't need retry value in json serialized object so you need to remove it from result exception
                String resultException = g.toJson(authenticationResult.getResultException());
                data += resultException;
            }
        } else if (result instanceof SignatureResult) {
            SignatureResult signatureResult = (SignatureResult) result;

            String resultException = g.toJson(signatureResult.getResultException());
            data = signatureResult.getId() +
                    signatureResult.getSpId() +
                    Long.toUnsignedString(signatureResult.getNonce()) +
                    signatureResult.getMdasTimestamp() +
                    signatureResult.getSpTimestamp() +
                    signatureResult.getMdasId() +
                    signatureResult.getResultType() +
                    resultException;
        } else if (result instanceof ChangePINResult) {
            ChangePINResult changePinResult = (ChangePINResult) result;

            String resultException = g.toJson(changePinResult.getResultException());
            data = changePinResult.getId() +
                    changePinResult.getSpId() +
                    Long.toUnsignedString(changePinResult.getNonce()) +
                    changePinResult.getMdasTimestamp() +
                    changePinResult.getSpTimestamp() +
                    changePinResult.getMdasId() +
                    changePinResult.getResultType() +
                    resultException;
        }
        return data;
    }

    //endregion
}
