package service.api;

import model.dto.PersonalInformationDto;
import model.sdkentity.ChangePINResult;
import model.sdkentity.FingerIndexResult;
import model.sdkentity.SignatureResult;

public interface IdentificationService {

    PersonalInformationDto fingerPrintAuthenticationStk();

    FingerIndexResult getMocFingerIndex();

    PersonalInformationDto pinAuthenticationStk();

    ChangePINResult pinChangeOwn();

    SignatureResult signOwn();

    PersonalInformationDto fingerPrintAuthenticationOwn();

}
