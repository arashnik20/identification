package model.sdkentity;

public class ChangePINResult extends SignedResult {

	public ChangePINResult(ResultType resultType, ResultException resultException, String id, String mdasId,
						   Long nonce, String spId, String mdasTimestamp, String spTimestamp, Signature signature) {
		super(resultType, resultException, id, mdasId, nonce, spId, mdasTimestamp, spTimestamp, signature);
	}
}
