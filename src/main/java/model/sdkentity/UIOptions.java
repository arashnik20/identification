package model.sdkentity;


public class UIOptions {

	private boolean showPinInputUI;
	private boolean showFingerPrintUI;
	private boolean showFaceInputUI;
	private WindowSize windowSize;
	private WindowLocation windowLocation;
	private Color backgroundColor;
	private String headerImage;

	public UIOptions(boolean showPinInputUI, boolean showFingerPrintUI, boolean showFaceInputUI, WindowSize windowSize, WindowLocation windowLocation, Color backgroundColor, String headerImage) {
		this.showPinInputUI = showPinInputUI;
		this.showFingerPrintUI = showFingerPrintUI;
		this.showFaceInputUI = showFaceInputUI;
		this.windowSize = windowSize;
		this.windowLocation = windowLocation;
		this.backgroundColor = backgroundColor;
		this.headerImage = headerImage;
	}

	public boolean isShowPinInputUI() {
		return showPinInputUI;
	}

	public void setShowPinInputUI(boolean showPinInputUI) {
		this.showPinInputUI = showPinInputUI;
	}

	public boolean isShowFingerPrintUI() {
		return showFingerPrintUI;
	}

	public void setShowFingerPrintUI(boolean showFingerPrintUI) {
		this.showFingerPrintUI = showFingerPrintUI;
	}

	public boolean isShowFaceInputUI() {
		return showFaceInputUI;
	}

	public void setShowFaceInputUI(boolean showFaceInputUI) {
		this.showFaceInputUI = showFaceInputUI;
	}

	public WindowSize getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(WindowSize windowSize) {
		this.windowSize = windowSize;
	}

	public WindowLocation getWindowLocation() {
		return windowLocation;
	}

	public void setWindowLocation(WindowLocation windowLocation) {
		this.windowLocation = windowLocation;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getHeaderImage() {
		return headerImage;
	}

	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}
}

