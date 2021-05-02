package model.ServiceParameters;

import model.dto.ResponseDto;
import model.sdkentity.Credentials;
import model.sdkentity.LevelOfAssurance;
import model.sdkentity.SPSignature;
import model.sdkentity.Scope;

public class AuthenticationParameters  extends ResponseDto {
    private LevelOfAssurance levelOfAssurance;
    private Credentials credentials;
    private Scope scope;
    private SPSignature spSignature;

    public AuthenticationParameters(LevelOfAssurance loa, Credentials credentials, Scope scope, SPSignature spSignature) {
        this.levelOfAssurance = loa;
        this.credentials = credentials;
        this.scope = scope;
        this.spSignature = spSignature;
    }

    public LevelOfAssurance getLevelOfAssurance() {
        return levelOfAssurance;
    }

    public void setLevelOfAssurance(LevelOfAssurance levelOfAssurance) {
        this.levelOfAssurance = levelOfAssurance;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public SPSignature getSpSignature() {
        return spSignature;
    }

    public void setSpSignature(SPSignature spSignature) {
        this.spSignature = spSignature;
    }
}
