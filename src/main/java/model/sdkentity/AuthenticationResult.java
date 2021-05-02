package model.sdkentity;

public class AuthenticationResult extends SignedResult {

	private Assertion assertion;

	public AuthenticationResult(ResultType resultType, ResultException resultException, String id, String mdasId,
								Long nonce, String spId, String mdasTimestamp, String spTimestamp, Signature signature, Assertion assertion) {
		super(resultType, resultException, id, mdasId, nonce, spId, mdasTimestamp, spTimestamp, signature);
		this.assertion = assertion;
	}

	public Assertion getAssertion() {
		return assertion;
	}

	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}
}
