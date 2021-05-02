package model.ServiceParameters;

import model.sdkentity.Credentials;
import model.sdkentity.SPSignature;
import model.sdkentity.TypeOfSignature;

public class SignParameters {
    private TypeOfSignature typeOfSignature;
    private Credentials credentials;
    private String data;
    private SPSignature spSignature;

    public SignParameters(TypeOfSignature typeOfSignature, Credentials credentials, String data, SPSignature spSignature) {
        this.typeOfSignature = typeOfSignature;
        this.credentials = credentials;
        this.data = data;
        this.spSignature = spSignature;
    }

    public TypeOfSignature getTypeOfSignature() {
        return typeOfSignature;
    }

    public void setTypeOfSignature(TypeOfSignature typeOfSignature) {
        this.typeOfSignature = typeOfSignature;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public SPSignature getSpSignature() {
        return spSignature;
    }

    public void setSpSignature(SPSignature spSignature) {
        this.spSignature = spSignature;
    }
}
