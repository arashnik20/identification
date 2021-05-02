package model.sdkentity;

public class Result {
	public enum ResultType {
		SUCCESS(0),
		UNSIGNED_EXCEPTION(1),
		SIGNED_EXCEPTION(2);

		private final int value;

		ResultType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private ResultType resultType;
	private ResultException resultException;

	public Result(ResultType resultType, ResultException resultException) {
		this.resultType = resultType;
		this.resultException = resultException;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public ResultException getResultException() {
		return resultException;
	}

	public void setResultException(ResultException resultException) {
		this.resultException = resultException;
	}
}
