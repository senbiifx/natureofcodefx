package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;

public class VectorMotion extends AnimationCanvas {

    private Mover mover;

    public VectorMotion() {
        this.mover = new Mover();

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            this.mover.update();
            this.mover.checkEdges();
            this.mover.draw(g);
        };
    }


    private class Mover {

        PVector location;
        PVector velocity;
        PVector acceleration;

        Mover() {
            this.location = new PVector(0.0f, 0.0f);
            this.velocity = new PVector(0.0f, 0.0f);
            this.acceleration = new PVector(-0.001f, 0.01f);
            this.velocity.limit(10.0f);
        }

        void update() {
            this.velocity.add(this.acceleration);
            this.location.add(this.velocity);
        }

        void checkEdges() {
            if ((double) this.location.x > VectorMotion.this.getWidth()) {
                this.location.x = 0.0f;
            } else if (this.location.x < 0.0f) {
                this.location.x = (float) VectorMotion.this.getWidth();
            }
            if ((double) this.location.y > VectorMotion.this.getHeight()) {
                this.location.y = 0.0f;
            } else if (this.location.y < 0.0f) {
                this.location.x = (float) VectorMotion.this.getHeight();
            }
        }

        void draw(GraphicsContext g) {
            g.setFill((Paint) Color.GRAY);
            g.setStroke((Paint) Color.BLACK);
            g.fillOval((double) this.location.x, (double) this.location.y, 40.0, 40.0);
        }
    }

}
