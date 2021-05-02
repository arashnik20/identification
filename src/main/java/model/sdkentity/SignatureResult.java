package model.sdkentity;

public class SignatureResult extends SignedResult {

	private SignedData signedData;

	public SignatureResult(ResultType resultType, ResultException resultException, String id, String mdasId,
						   Long nonce, String spId, String mdasTimestamp, String spTimestamp, Signature signature, SignedData signedData) {
		super(resultType, resultException, id, mdasId, nonce, spId, mdasTimestamp, spTimestamp, signature);
		this.signedData = signedData;
	}

	public SignedData getSignedData() {
		return signedData;
	}

	public void setSignedData(SignedData signedData) {
		this.signedData = signedData;
	}
}
