package model.sdkentity;

import java.util.ArrayList;


public class Scope {

	public enum SupplementInfo {AFIS_CHECKED, IDENTITY_CHANGED, REPLICA, CARD_ISSUANCE_DATE, CARD_EXPIRATION_DATE}

	public enum Source {NOCR, NOCR_PREFERRED, CARD}

	private Source source;
	private ArrayList<RequestedCitizenInfo> requiredCitizenInfo;
	private ArrayList<SupplementInfo> requiredSupplementaryInfo;

	public Scope(Source source, ArrayList<RequestedCitizenInfo> requiredCitizenInfo, ArrayList<SupplementInfo> requiredSupplementaryInfo) {
		this.source = source;
		this.requiredCitizenInfo = requiredCitizenInfo;
		this.requiredSupplementaryInfo = requiredSupplementaryInfo;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public ArrayList<RequestedCitizenInfo> getRequiredCitizenInfo() {
		return requiredCitizenInfo;
	}

	public void setRequiredCitizenInfo(ArrayList<RequestedCitizenInfo> requiredCitizenInfo) {
		this.requiredCitizenInfo = requiredCitizenInfo;
	}

	public ArrayList<SupplementInfo> getRequiredSupplementaryInfo() {
		return requiredSupplementaryInfo;
	}

	public void setRequiredSupplementaryInfo(ArrayList<SupplementInfo> requiredSupplementaryInfo) {
		this.requiredSupplementaryInfo = requiredSupplementaryInfo;
	}
}
