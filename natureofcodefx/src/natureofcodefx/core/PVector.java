package natureofcodefx.core;

public class PVector {

    public float x;
    public float y;

    public PVector() {

    }

    public PVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void add(PVector v) {
        x += v.x;
        y += v.y;
    }

    public void sub(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void sub(PVector v) {
        x -= v.x;
        y -= v.y;
    }

    public void mult(float n) {
        x *= n;
        y *= n;
    }

    public void mult(PVector v) {
        x *= v.x;
        y *= v.y;
    }

    public void div(float n) {
        x /= n;
        y /= n;
    }

    public void div(PVector v) {
        x /= v.x;
        y /= v.y;
    }

    public float dist(PVector v) {
        float dx = x - v.x;
        float dy = y - v.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public void normalize() {
        float m = mag();
        if (m != 0 && m != 1) {
            div(m);
        }
    }

    public void limit(float max) {
        if (mag() > max) {
            normalize();
            mult(max);
        }
    }

    public float mag() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public PVector get() {
        return new PVector(x, y);
    }

    public static PVector add(PVector v1, PVector v2) {
        return new PVector(v1.x + v2.x,v1.y + v2.y);
    }
    
    

    public static PVector sub(PVector v1, PVector v2) {
        return new PVector(v1.x - v2.x, v1.y - v2.y);
    }

    public static PVector mult(PVector v, float n) {
        return new PVector(v.x*n, v.y*n);
    }

    static public PVector div(PVector v, float n) {
        return new PVector(v.x/n, v.y/n);
    }

}
