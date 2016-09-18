package natureofcodefx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.AnimationCanvas;
import natureofcodefx.core.PVector;

public class VectorDivision extends AnimationCanvas {

    public VectorDivision() {

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.save();
            g.setFill((Paint) Color.WHITESMOKE);
            g.setStroke((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            PVector mouse = new PVector((float) this.mouseX, (float) this.mouseY);
            PVector center = new PVector((float) this.getWidth() / 2.0f, (float) this.getHeight() / 2.0f);
            mouse.sub(center);
            mouse.div(3.0f);
            g.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);
            g.setFill((Paint) Color.BLACK);
            g.setStroke((Paint) Color.BLACK);
            g.strokeLine(0.0, 0.0, (double) mouse.x, (double) mouse.y);
            g.stroke();
            g.restore();
        };
    }

}
