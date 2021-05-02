package model.sdkentity;

public class SPSignature {
	private Signature signature;
	private String SPID;
	private Long nonce;
	private String timestamp;

	public SPSignature(String SPID, Long nonce, String timestamp, Signature signature) {
		this.SPID = SPID;
		this.nonce = nonce;
		this.timestamp = timestamp;
		this.signature = signature;
	}

	public String getSPID() {
		return this.SPID;
	}

	public void setSPID(String SPID) {
		this.SPID = SPID;
	}

	public Long getNonce() {
		return this.nonce;
	}

	public void setNonce(Long nonce) {
		this.nonce = nonce;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Signature getSignature() {
		return this.signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}
}
