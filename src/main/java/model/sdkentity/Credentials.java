package model.sdkentity;


public class Credentials {

	private String pin;
	private String NMoCPin;
	private FingerPrint fingerPrint_1;
	private FingerPrint fingerPrint_2;
	private FaceData faceData;

	public Credentials(String pin, String NMoCPin, FingerPrint fingerPrint_1, FingerPrint fingerPrint_2) {
		this.pin = pin;
		this.NMoCPin = NMoCPin;
		this.fingerPrint_1 = fingerPrint_1;
		this.fingerPrint_2 = fingerPrint_2;
	}

	public Credentials(String pin, String NMoCPin, FingerPrint fingerPrint_1, FingerPrint fingerPrint_2, FaceData faceData) {
		this.pin = pin;
		this.NMoCPin = NMoCPin;
		this.fingerPrint_1 = fingerPrint_1;
		this.fingerPrint_2 = fingerPrint_2;
		this.faceData = faceData;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getNMoCPin() {
		return NMoCPin;
	}

	public void setNMoCPin(String NMoCPin) {
		this.NMoCPin = NMoCPin;
	}

	public FingerPrint getFingerPrint_1() {
		return fingerPrint_1;
	}

	public void setFingerPrint_1(FingerPrint fingerPrint_1) {
		this.fingerPrint_1 = fingerPrint_1;
	}

	public FingerPrint getFingerPrint_2() {
		return fingerPrint_2;
	}

	public void setFingerPrint_2(FingerPrint fingerPrint_2) {
		this.fingerPrint_2 = fingerPrint_2;
	}

	public FaceData getFaceData() {
		return faceData;
	}

	public void setFaceData(FaceData faceData) {
		this.faceData = faceData;
	}
}
