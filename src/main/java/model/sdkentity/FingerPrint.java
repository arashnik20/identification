package model.sdkentity;


public class FingerPrint {

	public enum FingerPrintType {
		FingerImage,
		Feature_CC
	}

	private int fingerIndex;
	private FingerPrintType fingerDataType;
	private /*String*/byte[] fingerData;//change
	private int imageWidth;
	private int imageHeight;
	private int resolution;
	private int minuteaCount;

	public FingerPrint() {
		this.fingerIndex = 0;
		this.fingerData = /*""*/new byte[0];//change
		this.fingerDataType = FingerPrintType.FingerImage;
		this.imageWidth = 0;
		this.imageHeight = 0;
		this.resolution = 0;
		this.minuteaCount = 0;
	}

	public FingerPrint(int fingerIndex, FingerPrintType fingerDataType, /*String*/byte[] fingerData,//change
					   int imageWidth, int imageHeight, int resolution,
					   int minuteaCount) {
		this.fingerIndex = fingerIndex;
		this.fingerData = fingerData.clone();
		this.fingerDataType = fingerDataType;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.resolution = resolution;
		this.minuteaCount = minuteaCount;
	}

	public int getFingerIndex() {
		return fingerIndex;
	}

	public void setFingerIndex(int fingerIndex) {
		this.fingerIndex = fingerIndex;
	}

	public void setFingerDataType(FingerPrintType fingerDataType) {
		this.fingerDataType = fingerDataType;
	}

	public /*String*/byte[] getFingerData() {
		return fingerData;
	}//change

	public FingerPrintType getFingerDataType() {
		return fingerDataType;
	}

	public void setFingerData(byte[] fingerData) {
		this.fingerData = fingerData.clone();
	}//change

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public int getMinuteaCount() {
		return minuteaCount;
	}

	public void setMinuteaCount(int minuteaCount) {
		this.minuteaCount = minuteaCount;
	}
}
