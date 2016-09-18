package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Waves extends AnimationCanvas {

    private List<Mover> movers;

    public Waves() {
        setup = () -> {
            this.movers = new ArrayList<Mover>();
            float angle = 0.0f;
            int count = (int) (this.getWidth() + 20.0);
            for (float x = -20.0f; x < (float) count; x += 1.0f) {
                Mover mover = new Mover();
                mover.angle = angle;
                mover.x = x;
                angle = (float) ((double) angle + 0.01);
                this.movers.add(mover);
            }
        };

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.save();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            g.translate(0.0, this.getHeight() / 2.0);
            this.movers.forEach(m -> {
                m.update();
                m.draw(g);
            }
            );
            g.restore();
        };
    }

    private class Mover {

        float velocity;
        float angle;
        float x;
        float y;
        float amplitude;

        Mover() {
            this.velocity = 0.05f;
            this.angle = 0.0f;
            this.amplitude = 180.0f;
        }

        void draw(GraphicsContext g) {
            g.save();
            g.setFill((Paint) Color.BLACK);
            g.fillOval((double) this.x, (double) this.y, 10.0, 10.0);
            g.restore();
        }

        void update() {
            this.angle += this.velocity;
            this.y = (float) ((double) this.amplitude * Math.sin(this.angle));
        }
    }

}
