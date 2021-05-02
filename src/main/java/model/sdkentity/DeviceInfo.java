package model.sdkentity;


public class DeviceInfo {
	private String cardReaderName;
	private String fingerScannerName;
	private String cameraName;

	public DeviceInfo(String cardReaderName, String fingerScannerName, String cameraName) {
		this.cardReaderName = cardReaderName;
		this.fingerScannerName = fingerScannerName;
		this.cameraName = cameraName;
	}

	public String getCardReaderName() {
		return cardReaderName;
	}

	public void setCardReaderName(String cardReaderName) {
		this.cardReaderName = cardReaderName;
	}

	public String getFingerScannerName() {
		return fingerScannerName;
	}

	public void setFingerScannerName(String fingerScannerName) {
		this.fingerScannerName = fingerScannerName;
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}
}
