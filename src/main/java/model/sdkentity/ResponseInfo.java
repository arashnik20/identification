package model.sdkentity;

public class ResponseInfo {

	public enum InfoType {
		NAME, SURNAME, NID, FATHER_NAME, GENDER, DATE_OF_BIRTH, ISSUED_LOCATION, POSTAL_INFO, FACE_INFO, AFIS_CHECKED, IDENTITY_CHANGED, REPLICA, CARD_ISSUANCE_DATE, CARD_EXPIRATION_DATE
	}

	public enum Source {
		NOCR(0),
		CARD(2);
		private final int value;

		Source(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private InfoType infoType;
	private String value;
	private Source source;

	public ResponseInfo() {
	}

	public ResponseInfo(InfoType infoType, String value, Source source) {
		this.infoType = infoType;
		this.source = source;
		this.value = value;
	}

	public InfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(InfoType infoType) {
		this.infoType = infoType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "ResponseInfo{" +
				"infoType=" + infoType +
				", value='" + value + '\'' +
				", source=" + source +
				'}';
	}
}

