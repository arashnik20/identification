package model.sdkentity;

public class ResultException {
	private String category;
	private String cause;
	private String field;
	private transient int retry;

	public ResultException(String category, String cause, String field, int retry) {
		this.category = category;
		this.cause = cause;
		this.field = field;
		this.retry = retry;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}
}

