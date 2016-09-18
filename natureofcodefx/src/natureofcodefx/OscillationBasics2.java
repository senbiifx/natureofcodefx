package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class OscillationBasics2 extends AnimationCanvas {

    private float velocity = 0.05f;
    private float angle = 0.0f;

    public OscillationBasics2() {
        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.save();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            float amplitude = 200.0f;
            this.angle += this.velocity;
            float x = amplitude * (float) Math.cos(this.angle);
            g.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);
            g.setFill((Paint) Color.BLACK);
            g.fillOval((double) x, 0.0, 20.0, 20.0);
            g.restore();
        };
    }

    
}
