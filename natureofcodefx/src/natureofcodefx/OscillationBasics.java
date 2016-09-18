package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class OscillationBasics extends AnimationCanvas {

    public OscillationBasics() {
        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.save();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            float period = 120.0f;
            float amplitude = 200.0f;
            float x = amplitude * (float) Math.cos(6.283185307179586 * (double) this.frameCount / (double) period);
            g.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);
            g.setFill((Paint) Color.BLACK);
            g.fillOval((double) x, 0.0, 20.0, 20.0);
            g.restore();
        };
    }

  
}
