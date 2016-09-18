package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Spiral extends AnimationCanvas {

    private float startingTheta = 0.0f;

    public Spiral() {

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.save();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            g.setFill((Paint) Color.BLACK);
            g.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);
            float theta = this.startingTheta;
            float maxRadius = (float) (Math.max(this.getWidth(), this.getHeight()) / 2.0) + 50.0f;
            float r = 0.0f;
            while (r < maxRadius) {
                float y = (float) ((double) r * Math.sin(Math.toRadians(theta)));
                float x = (float) ((double) r * Math.cos(Math.toRadians(theta)));
                g.fillOval((double) x, (double) y, 15.0, 15.0);
                theta = (float) ((double) theta + 0.6);
                r = (float) ((double) r + 0.05);
            }
            g.restore();
            this.startingTheta = (float) ((double) this.startingTheta + 0.5);
            if (this.startingTheta >= 360.0f) {
                this.startingTheta = 0.0f;
            }
        };
    }

}
