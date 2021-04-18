package lab;

public class ResultSet {
    private double left;
    private double right;
    private double mid;

    public ResultSet(double left, double right, double mid) {
        this.left = left;
        this.right = right;
        this.mid = mid;
    }

    public ResultSet() {

    }

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
}
