package service.impl;

import component.Identification;
import exceptions.GlobalException;
import model.dto.PersonalInformationDto;
import model.sdkentity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.api.IdentificationService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

@Component
public class IdentificationImpl implements IdentificationService {

    private static final Logger logger = LoggerFactory.getLogger(Identification.class);

    @Autowired
    private Identification identification;

    @Override
    public PersonalInformationDto fingerPrintAuthenticationStk() {
        LevelOfAssurance levelOfAssurance = new LevelOfAssurance(
                LevelOfAssurance.AuthenticationMethod.PIN_FP,
                false,
                false
        );
        InitializeResult initializeResult = identification.initializeSDK("stk");
        FingerIndexResult fingerIndexResult = identification.getMocFingerIndex();
        if (!fingerIndexResult.getResultType().equals(Result.ResultType.SUCCESS) && fingerIndexResult.getFinger1() == 0 && fingerIndexResult.getFinger2() == 0){
            logger.info("This card has no fingerprint; Please use PIN_PIN authentication method");
            levelOfAssurance = new LevelOfAssurance(
                    LevelOfAssurance.AuthenticationMethod.PIN_PIN,
                    false,
                    false
            );
        }
        ArrayList<ResponseInfo> responseInfoList = identification.fingerprintauthentication(levelOfAssurance,initializeResult);
        identification.finalizeSdk();
        return generatePersonalInformation(levelOfAssurance, responseInfoList);
    }

    @Override
    public FingerIndexResult getMocFingerIndex() {
        return identification.getMocFingerIndex();
    }

    private PersonalInformationDto generatePersonalInformation(LevelOfAssurance levelOfAssurance, ArrayList<ResponseInfo> responseInfoList) {
        PersonalInformationDto personalInformation =  getPersonalInformationDto(responseInfoList, levelOfAssurance.getAuthenticationMethod());
        return personalInformation;
    }

    @Override
    public PersonalInformationDto pinAuthenticationStk() {
        LevelOfAssurance levelOfAssurance = new LevelOfAssurance(
                LevelOfAssurance.AuthenticationMethod.PIN,
                false,
                false
        );
        InitializeResult initializeResult = identification.initializeSDK("stk");
        ArrayList<ResponseInfo> responseInfoList = identification.pinAuthenticationStk(initializeResult);
        identification.finalizeSdk();
        return generatePersonalInformation(levelOfAssurance,responseInfoList);
    }

    @Override
    public ChangePINResult pinChangeOwn() {
        InitializeResult initializeResult = identification.initializeSDK("own");
        ChangePINResult changePINResult = null;
        try {
            changePINResult = identification.pinChangeOwn(initializeResult);
        } catch (Exception e) {
            throw new GlobalException("ChangePin_failed");
        }
        identification.finalizeSdk();
        return changePINResult;
    }

    @Override
    public SignatureResult signOwn() {
        InitializeResult initializeResult = identification.initializeSDK("own");
        SignatureResult signatureResult = null;
        try {
            signatureResult = identification.signOwn(initializeResult);
        } catch (Exception e) {
            throw new GlobalException("verifySignResponse");
        }
        identification.finalizeSdk();
        return signatureResult;
    }

    @Override
    public PersonalInformationDto fingerPrintAuthenticationOwn() {
        InitializeResult initializeResult = identification.initializeSDK("own");
        //پین شناسایی شهروند + تصویر/اثر یک انگشت (برای شهروندان دارای اثر انگشت) pin_fp
        LevelOfAssurance loa = new LevelOfAssurance(
                LevelOfAssurance.AuthenticationMethod.PIN_FP,
                false,
                false
        );
        FingerIndexResult fingerIndexResult = identification.getMocFingerIndex();
        if (!fingerIndexResult.getResultType().equals(Result.ResultType.SUCCESS) && fingerIndexResult.getFinger1() == 0 && fingerIndexResult.getFinger2() == 0)
            throw new GlobalException("PIN_PIN_authentication");
        ArrayList<ResponseInfo> responseInfoList = null;
        try {
            responseInfoList = identification.fingerprintauthenticationOwn(fingerIndexResult,initializeResult);
        } catch (Exception e) {
            throw new GlobalException("fingerPrintAuthenticationOwn");
        }
        return generatePersonalInformation(loa ,responseInfoList);
    }

    public PersonalInformationDto getPersonalInformationDto (ArrayList<ResponseInfo> responseInfoList, LevelOfAssurance.AuthenticationMethod authenticationMethod)  {
        PersonalInformationDto dto = new PersonalInformationDto();
        if (responseInfoList != null && responseInfoList.size() > 0) {
            logger.info(" tedad :  ====> " + String.valueOf(responseInfoList.size()));
            try {
                dto.setName(new String(Base64.getDecoder().decode(responseInfoList.get(0).getValue()),"utf-8"));
                dto.setSurname(new String(Base64.getDecoder().decode(responseInfoList.get(1).getValue()),"utf-8"));

                if (authenticationMethod.equals(LevelOfAssurance.AuthenticationMethod.PIN_FP) || authenticationMethod.equals(LevelOfAssurance.AuthenticationMethod.PIN_FP_FP)) {
                    dto.setNid(new String(Base64.getDecoder().decode(responseInfoList.get(2).getValue()),"utf-8"));
                    dto.setFatherName(new String(Base64.getDecoder().decode(responseInfoList.get(3).getValue()),"utf-8"));
                    dto.setGender(new String(Base64.getDecoder().decode(responseInfoList.get(4).getValue()),"utf-8"));
                    dto.setDateOfBirth(new String(Base64.getDecoder().decode(responseInfoList.get(5).getValue()),"utf-8"));
                    dto.setIssuedLocation(new String(Base64.getDecoder().decode(responseInfoList.get(6).getValue()),"utf-8"));
                    dto.setPostalInfo(new String(Base64.getDecoder().decode(responseInfoList.get(7).getValue()),"utf-8"));
                    dto.setFaceInfo(registrationBase64ToBrowserBase64(responseInfoList.get(8).getValue()));
                    dto.setAfisChecked(new String(Base64.getDecoder().decode(responseInfoList.get(9).getValue()),"utf-8"));
                    dto.setIdentityChanged(new String(Base64.getDecoder().decode(responseInfoList.get(10).getValue()),"utf-8"));
                    dto.setReplica(new String(Base64.getDecoder().decode(responseInfoList.get(11).getValue()),"utf-8"));
                    dto.setCardIssuanceDate(new String(Base64.getDecoder().decode(responseInfoList.get(12).getValue()),"utf-8"));
                    dto.setCardExpirationDate( new String(Base64.getDecoder().decode(responseInfoList.get(13).getValue()),"utf-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (ArrayIndexOutOfBoundsException ex){
                System.out.println(dto);
                ex.printStackTrace();
            }
        }
        return dto;
    }
    public static String registrationBase64ToBrowserBase64(String registrationBase64) {
        byte[] img = Base64.getDecoder().decode(registrationBase64);
        byte[] subImg = Arrays.copyOfRange(img, 50, img.length);
        return new String(Base64.getEncoder().encode(subImg));
    }
}
