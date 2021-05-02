package model.dto;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
@Data
public class PersonalInformationDto implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PersonalInformationDto.class);

    private String name;
    private String surname;
    private String nid;
    private String fatherName;
    private String gender;
    private String dateOfBirth;
    private String issuedLocation;
    private String postalInfo;
    private String faceInfo;
    private String afisChecked;
    private String identityChanged;
    private String replica;
    private String cardIssuanceDate;
    private String cardExpirationDate;

    @Override
    public String toString() {
        return "PersonalInformationDto{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nid='" + nid + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", issuedLocation='" + issuedLocation + '\'' +
                ", postalInfo='" + postalInfo + '\'' +
                ", afisChecked='" + afisChecked + '\'' +
                ", identityChanged='" + identityChanged + '\'' +
                ", replica='" + replica + '\'' +
                ", cardIssuanceDate='" + cardIssuanceDate + '\'' +
                ", cardExpirationDate='" + cardExpirationDate + '\'' +
                '}';
    }
}
