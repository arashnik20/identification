package model.dto;

public class ResponseInfoDto extends ResponseDto {

    private String name ;
    private String surname;
    private String nid;
    private String fatherName ;
    private String gender ;
    private String dateOfBirth ;
    private String issuedLocation ;
    private String postalInfo;
    private String faceInfo;
    private String afisChecked;
    private String identityChanged ;
    private String replica ;
    private String cardIssuanceDate;
    private String cardExpirationDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIssuedLocation() {
        return issuedLocation;
    }

    public void setIssuedLocation(String issuedLocation) {
        this.issuedLocation = issuedLocation;
    }

    public String getPostalInfo() {
        return postalInfo;
    }

    public void setPostalInfo(String postalInfo) {
        this.postalInfo = postalInfo;
    }

    public String getFaceInfo() {
        return faceInfo;
    }

    public void setFaceInfo(String faceInfo) {
        this.faceInfo = faceInfo;
    }

    public String getAfisChecked() {
        return afisChecked;
    }

    public void setAfisChecked(String afisChecked) {
        this.afisChecked = afisChecked;
    }

    public String getIdentityChanged() {
        return identityChanged;
    }

    public void setIdentityChanged(String identityChanged) {
        this.identityChanged = identityChanged;
    }

    public String getReplica() {
        return replica;
    }

    public void setReplica(String replica) {
        this.replica = replica;
    }

    public String getCardIssuanceDate() {
        return cardIssuanceDate;
    }

    public void setCardIssuanceDate(String cardIssuanceDate) {
        this.cardIssuanceDate = cardIssuanceDate;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }
}
