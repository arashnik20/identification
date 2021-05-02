package model.sdkentity;

public class SignedData {

	private String signatureAlgorithm;
	private String hashAlgorithm;
	private String citizenSignCertificate;
	private String citizenCACertificate;
	private String revocationData;
	private String signatureValue;
	private String algorithmVersion;
	private String domainParameters;

	public SignedData(String signatureAlgorithm, String hashAlgorithm, String citizenSignCertificate, String citizenCACertificate, String revocationData, String signatureValue, String algorithmVersion, String domainParameters) {
		this.signatureAlgorithm = signatureAlgorithm;
		this.hashAlgorithm = hashAlgorithm;
		this.citizenSignCertificate = citizenSignCertificate;
		this.citizenCACertificate = citizenCACertificate;
		this.revocationData = revocationData;
		this.signatureValue = signatureValue;
		this.algorithmVersion = algorithmVersion;
		this.domainParameters = domainParameters;
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

	public String getCitizenSignCertificate() {
		return citizenSignCertificate;
	}

	public void setCitizenSignCertificate(String citizenSignCertificate) {
		this.citizenSignCertificate = citizenSignCertificate;
	}

	public String getCitizenCACertificate() {
		return citizenCACertificate;
	}

	public void setCitizenCACertificate(String citizenCACertificate) {
		this.citizenCACertificate = citizenCACertificate;
	}

	public String getRevocationData() {
		return revocationData;
	}

	public void setRevocationData(String revocationData) {
		this.revocationData = revocationData;
	}

	public String getSignatureValue() {
		return signatureValue;
	}

	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
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
}
