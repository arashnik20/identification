package model.sdkentity;

public class SignedResult extends Result {

	private Signature signature;
	private String id;
	private String mdasId;
	private String spId;
	private Long nonce;
	private String mdasTimestamp;
	private String spTimestamp;

	public SignedResult(ResultType resultType, ResultException resultException, String id, String mdasId,
						Long nonce, String spId, String mdasTimestamp, String spTimestamp, Signature signature) {
		super(resultType, resultException);
		this.id = id;
		this.mdasId = mdasId;
		this.nonce = nonce;
		this.spId = spId;
		this.mdasTimestamp = mdasTimestamp;
		this.spTimestamp = spTimestamp;
		this.signature = signature;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMdasId() {
		return mdasId;
	}

	public void setMdasId(String mdasId) {
		this.mdasId = mdasId;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public Long getNonce() {
		return nonce;
	}

	public void setNonce(Long nonce) {
		this.nonce = nonce;
	}

	public String getMdasTimestamp() {
		return mdasTimestamp;
	}

	public void setMdasTimestamp(String mdasTimestamp) {
		this.mdasTimestamp = mdasTimestamp;
	}

	public String getSpTimestamp() {
		return spTimestamp;
	}

	public void setSpTimestamp(String spTimestamp) {
		this.spTimestamp = spTimestamp;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}
}
