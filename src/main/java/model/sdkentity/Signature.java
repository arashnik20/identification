package model.sdkentity;

public class Signature {
	private String signatureAlgorithm;
	private String hashAlgorithm;
	private String algorithmVersion;
	private String domainParameters;
	private String CACertificate;
	private String endCertificate;
	private String signatureValue;

	public Signature(String signatureAlgorithm,
					 String hashAlgorithm,
					 String algorithmVersion,
					 String domainParameters,
					 String CACertificate,
					 String endCertificate,
					 String signatureValue) {
		this.signatureAlgorithm = signatureAlgorithm;
		this.hashAlgorithm = hashAlgorithm;
		this.algorithmVersion = algorithmVersion;
		this.domainParameters = domainParameters;
		this.CACertificate = CACertificate;
		this.endCertificate = endCertificate;
		this.signatureValue = signatureValue;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public String getAlgorithmVersion() {
		return algorithmVersion;
	}

	public void setAlgorithmVersion(String algorithmVersion) {
		this.algorithmVersion = algorithmVersion;
	}

	public String getDomainParameters() {
		return domainParameters;
	}

	public void setDomainParameters(String domainParameters) {
		this.domainParameters = domainParameters;
	}

	public String getSignatureValue() {
		return signatureValue;
	}

	public String getCACertificate() {
		return CACertificate;
	}

	public void setCACertificate(String CACertificate) {
		this.CACertificate = CACertificate;
	}

	public String getEndCertificate() {
		return endCertificate;
	}

	public void setEndCertificate(String endCertificate) {
		this.endCertificate = endCertificate;
	}

	public String getSignatureData() {
		return signatureValue;
	}

	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
}
