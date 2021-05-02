package model.sdkentity;

public class RequestedCitizenInfo {
	public enum InfoType {NAME, SURNAME, NID, FATHER_NAME, GENDER, DATE_OF_BIRTH, ISSUED_LOCATION, POSTAL_INFO, FACE_INFO}

	private InfoType infoType;
	private boolean isMandatory;

	public RequestedCitizenInfo(InfoType infoType, boolean isMandatory) {
		this.infoType = infoType;
		this.isMandatory = isMandatory;
	}

	public InfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(InfoType infoType) {
		this.infoType = infoType;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean mandatory) {
		isMandatory = mandatory;
	}
}
