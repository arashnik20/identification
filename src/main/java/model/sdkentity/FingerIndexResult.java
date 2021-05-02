package model.sdkentity;

public class FingerIndexResult extends Result {

	public enum FingerStatus {
		NA,			// no finger status
		Blocked,	// no more tries left
		Critical,	// 1 - 5 tries left
		Normal		// 6 - 15 tries left
	}

	private int finger_1;
	private FingerStatus finger_1_status;
	private int finger_2;
	private FingerStatus finger_2_status;

	public FingerIndexResult(ResultType resultType, ResultException resultException, int finger_1, FingerStatus finger_1_status, int finger_2, FingerStatus finger_2_status) {
		super(resultType, resultException);
		this.finger_1 = finger_1;
		this.finger_1_status = finger_1_status;
		this.finger_2 = finger_2;
		this.finger_2_status = finger_2_status;
	}

	public int getFinger1() {
		return finger_1;
	}

	public void setFinger1(int finger_1) {
		this.finger_1 = finger_1;
	}

	public FingerStatus getFinger1Status() {
		return finger_1_status;
	}

	public void setFinger1Status(FingerStatus finger_1_status) {
		this.finger_1_status = finger_1_status;
	}

	public int getFinger2() {
		return finger_2;
	}

	public void setFinger2(int finger_2) {
		this.finger_2 = finger_2;
	}

	public FingerStatus getFinger2Status() {
		return finger_2_status;
	}

	public void setFinger2Status(FingerStatus finger_2_status) {
		this.finger_2_status = finger_2_status;
	}
}
