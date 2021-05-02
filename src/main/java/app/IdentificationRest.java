package app;

import model.dto.PersonalInformationDto;
import model.sdkentity.ChangePINResult;
import model.sdkentity.SignatureResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.api.IdentificationService;

@RestController
@RequestMapping("/identification")
public class IdentificationRest {

    private static final Logger logger = LoggerFactory.getLogger(IdentificationRest.class);

    @Autowired
    IdentificationService identificationApiService;

    /**
     *  need to own initialize with start /ownmethod/...
     * */
    @PostMapping("/ownmethod/fingerprintauthentication")
    @ResponseBody
    public PersonalInformationDto fingerprintAuthenticationOwn(){
        return identificationApiService.fingerPrintAuthenticationOwn();
    }

    @PostMapping("/ownmethod/sign")
    @ResponseBody
    public SignatureResult signOwn () {
        return identificationApiService.signOwn();
    }

    @PostMapping("/ownmethod/pinchange")
    @ResponseBody
    public ChangePINResult pinChangeOwn() {
        return identificationApiService.pinChangeOwn();
    }

    /**
     * use stk
     * */
    @PostMapping("/stk/fingerprintauthentication")
    @ResponseBody
    public PersonalInformationDto FingerprintAuthenticationStk() {
        return identificationApiService.fingerPrintAuthenticationStk();
    }

    @PostMapping("/stk/pinauthentication") //pinChangeStk
    @ResponseBody
    public PersonalInformationDto pinAuthenticationStk() {
        return identificationApiService.pinAuthenticationStk();
    }
}
