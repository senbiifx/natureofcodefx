package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;

public class VectorAcceleration extends AnimationCanvas {

    private Mover[] movers = new Mover[15];

    public VectorAcceleration() {

        setup = () -> {
            for (int i = 0; i < this.movers.length; ++i) {
                this.movers[i] = new Mover();
            }
            this.mouseX = this.getWidth() / 2.0;
            this.mouseY = this.getHeight() / 2.0;
        };

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            for (Mover mover : this.movers) {
                mover.update();
                mover.draw(g);
            }
        };
    }

    private class Mover {

        PVector location;
        PVector velocity;
        PVector acceleration;
        float topSpeed;
        double size;
        float aScale;

        Mover() {
            Random random = new Random();
            this.location = new PVector(0.0f, 0.0f);
            this.velocity = new PVector(0.0f, 0.0f);
            this.size = random.nextInt(20) + 20;
            this.topSpeed = random.nextInt(10) + 5;
            this.aScale = (float) Math.random();
        }

        void update() {
            PVector mouse = new PVector((float) VectorAcceleration.this.mouseX, (float) VectorAcceleration.this.mouseY);
            PVector direction = PVector.sub((PVector) mouse, (PVector) this.location);
            direction.normalize();
            direction.mult(this.aScale);
            this.acceleration = direction;
            this.velocity.add(this.acceleration);
            this.velocity.limit(this.topSpeed);
            this.location.add(this.velocity);
        }

        void draw(GraphicsContext g) {
            g.setFill((Paint) Color.WHITE);
            g.setStroke((Paint) Color.BLACK);
            g.strokeOval((double) this.location.x, (double) this.location.y, this.size, this.size);
        }
    }

}
