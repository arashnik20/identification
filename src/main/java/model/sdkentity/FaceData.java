package model.sdkentity;

public class FaceData {
    private int nProbeImgHeight;
    private int nProbeImgStride;
    private int nProbeImgWidth;
    private byte[] pProbeImgData;

    public FaceData(int probeImgHeight, int probeImgStride, int probeImgWidth, byte[] probeImgData) {
        this.nProbeImgHeight = probeImgHeight;
        this.nProbeImgStride = probeImgStride;
        this.nProbeImgWidth = probeImgWidth;
        this.pProbeImgData = probeImgData.clone();
    }

    public int getnProbeImgHeight() {
        return nProbeImgHeight;
    }

    public void setnProbeImgHeight(int nProbeImgHeight) {
        this.nProbeImgHeight = nProbeImgHeight;
    }

    public int getnProbeImgStride() {
        return nProbeImgStride;
    }

    public void setnProbeImgStride(int nProbeImgStride) {
        this.nProbeImgStride = nProbeImgStride;
    }

    public int getnProbeImgWidth() {
        return nProbeImgWidth;
    }

    public void setnProbeImgWidth(int nProbeImgWidth) {
        this.nProbeImgWidth = nProbeImgWidth;
    }

    public byte[] getpProbeImgData() {
        return pProbeImgData;
    }

    public void setpProbeImgData(byte[] pProbeImgData) {
        this.pProbeImgData = pProbeImgData;
    }
}
