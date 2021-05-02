package model.sdkentity;

public class LevelOfAssurance {

	public enum AuthenticationMethod {PIN, PIN_PIN, PIN_FP, PIN_FP_FP}

	private AuthenticationMethod authenticationMethod;
	private boolean revocationCheck;
	private boolean authorizationCheck;

	public AuthenticationMethod getAuthenticationMethod() {
		return authenticationMethod;
	}

	public LevelOfAssurance(AuthenticationMethod authenticationMethod, boolean revocationCheck, boolean authorizationCheck) {
		this.authenticationMethod = authenticationMethod;
		this.revocationCheck = revocationCheck;
		this.authorizationCheck = authorizationCheck;
	}

	public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
		this.authenticationMethod = authenticationMethod;
	}

	public boolean getRevocationCheck() {
		return revocationCheck;
	}

	public void setRevocationCheck(boolean revocationCheck) {
		this.revocationCheck = revocationCheck;
	}

	public boolean getAuthorizationCheck() {
		return authorizationCheck;
	}

	public void setAuthorizationCheck(boolean authorizationCheck) {
		this.authorizationCheck = authorizationCheck;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LevelOfAssurance) {
			if (((LevelOfAssurance) obj).authorizationCheck == this.getAuthorizationCheck())
				if (((LevelOfAssurance) obj).revocationCheck == this.getRevocationCheck())
					if (((LevelOfAssurance) obj).authenticationMethod.equals(this.getAuthenticationMethod()))
						return true;
		}
		return false;
	}
}
