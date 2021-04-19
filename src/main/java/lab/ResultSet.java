package lab;

public class ResultSet {
    private double left;
    private double right;
    private double mid;
    private double epsLeft;
    private double epsRight;
    private double epsMid;

    public ResultSet() {

    }

    /*
    Getter and Setter
     */
    public double getLeft() {
        return left;
    }
    public void setLeft(double left) {
        this.left = left;
    }
    public double getRight() {
        return right;
    }
    public void setRight(double right) {
        this.right = right;
    }
    public double getMid() {
        return mid;
    }
    public void setMid(double mid) {
        this.mid = mid;
    }
    public double getEpsLeft() { return epsLeft; }
    public void setEpsLeft(double epsLeft) { this.epsLeft = epsLeft; }
    public double getEpsRight() { return epsRight; }
    public void setEpsRight(double epsRight) { this.epsRight = epsRight; }
    public double getEpsMid() { return epsMid; }
    public void setEpsMid(double epsMid) { this.epsMid = epsMid; }
}
