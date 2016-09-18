package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;

public class VectorNormalization extends AnimationCanvas {

    public VectorNormalization() {

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.save();
            g.setFill((Paint) Color.WHITESMOKE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            PVector mouse = new PVector((float) this.mouseX, (float) this.mouseY);
            PVector center = new PVector((float) this.getWidth() / 2.0f, (float) this.getHeight() / 2.0f);
            mouse.sub(center);
            mouse.normalize();
            mouse.mult(50.0f);
            g.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);
            g.setStroke((Paint) Color.BLACK);
            g.strokeLine(0.0, 0.0, (double) mouse.x, (double) mouse.y);
            g.stroke();
            g.restore();
        };
    }

}
