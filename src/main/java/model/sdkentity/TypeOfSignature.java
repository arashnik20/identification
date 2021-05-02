package model.sdkentity;

public class TypeOfSignature {
	private boolean revocationCheck;
	private boolean authorizationCheck;

	public TypeOfSignature(boolean revocationCheck, boolean authorizationCheck) {
		this.revocationCheck = revocationCheck;
		this.authorizationCheck = authorizationCheck;
	}

	public boolean isRevocationCheck() {
		return revocationCheck;
	}

	public void setRevocationCheck(boolean revocationCheck) {
		this.revocationCheck = revocationCheck;
	}

	public boolean isAuthorizationCheck() {
		return authorizationCheck;
	}

	public void setAuthorizationCheck(boolean authorizationCheck) {
		this.authorizationCheck = authorizationCheck;
	}
}
