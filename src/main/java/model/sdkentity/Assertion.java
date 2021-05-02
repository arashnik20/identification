package model.sdkentity;

import java.util.ArrayList;

public class Assertion {

	private LevelOfAssurance loa;
	private ArrayList<ResponseInfo> responseInfos;

	public Assertion(LevelOfAssurance loa, ArrayList<ResponseInfo> responseInfos) {
		this.loa = loa;
		this.responseInfos = responseInfos;
	}

	public LevelOfAssurance getLOA() {
		return loa;
	}

	public void setLOA(LevelOfAssurance loa) {
		this.loa = loa;
	}

	public ArrayList<ResponseInfo> getResponseInfos() {
		return responseInfos;
	}

	public void setResponseInfos(ArrayList<ResponseInfo> responseInfos) {
		this.responseInfos = responseInfos;
	}
}
