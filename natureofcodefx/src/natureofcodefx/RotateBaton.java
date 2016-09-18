package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class RotateBaton
        extends AnimationCanvas {

    private double halfWidth;
    private double halfHeight;
    private float angle = 0.0f;
    private float velocity = 0.0f;
    private float acceleration = 1.0E-4f;

    public RotateBaton() {
        setup = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.setStroke((Paint) Color.BLACK);
            g.setLineWidth(2.0);
            this.halfWidth = this.getWidth() / 2.0;
            this.halfHeight = this.getHeight() / 2.0;
        };

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            g.translate(this.halfWidth, this.halfHeight);
            g.rotate((double) this.angle);
            g.strokeLine(-50.0, 0.0, 50.0, 0.0);
            g.setFill((Paint) Color.GRAY);
            g.fillOval(-80.0, -15.0, 30.0, 30.0);
            g.strokeOval(-80.0, -15.0, 30.0, 30.0);
            g.fillOval(50.0, -15.0, 30.0, 30.0);
            g.strokeOval(50.0, -15.0, 30.0, 30.0);
            g.translate(-this.halfWidth, -this.halfHeight);
            if (this.angle <= 70.0f) {
                this.velocity += this.acceleration;
                this.angle += this.velocity;
            }
        };
    }

}
