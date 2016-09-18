package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;

public class ForceAccumulation extends AnimationCanvas {

    private final Mover mover;

    public ForceAccumulation() {
        this.mover = new Mover();

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            PVector wind = new PVector(0.01f, 0.0f);
            PVector gravity = new PVector(0.0f, 0.1f);
            this.mover.applyForce(this.getFriction(this.mover.velocity));
            this.mover.applyForce(wind);
            this.mover.applyForce(gravity);
            this.mover.update();
            this.mover.checkEdges();
            this.mover.draw(g);
        };
    }

    private PVector getFriction(PVector velocity) {
        float c = 0.01f;
        float n = 1.0f;
        PVector friction = velocity.get();
        friction.mult(-1.0f);
        friction.normalize();
        friction.mult(c);
        friction.mult(n);
        return friction;
    }

    private class Mover {

        PVector location;
        PVector velocity;
        PVector acceleration;
        float topSpeed;
        double size;
        float mass;

        Mover() {
            Random random = new Random();
            this.location = new PVector(30.0f, 30.0f);
            this.velocity = new PVector(0.0f, 0.0f);
            this.acceleration = new PVector(0.0f, 0.0f);
            this.size = 16.0;
            this.topSpeed = random.nextInt(5) + 5;
            this.mass = 1.0f;
        }

        void update() {
            this.velocity.add(this.acceleration);
            this.location.add(this.velocity);
            this.acceleration.mult(0.0f);
        }

        void draw(GraphicsContext g) {
            g.setFill((Paint) Color.GRAY);
            g.setStroke((Paint) Color.BLACK);
            g.fillOval((double) this.location.x, (double) this.location.y, (double) this.mass * this.size, (double) this.mass * this.size);
        }

        void applyForce(PVector force) {
            PVector f = force.get();
            f.div(this.mass);
            this.acceleration.add(f);
        }

        void checkEdges() {
            if ((double) this.location.x > ForceAccumulation.this.getWidth()) {
                this.location.x = (float) ForceAccumulation.this.getWidth();
                this.velocity.x *= -1.0f;
            } else if (this.location.x < 0.0f) {
                this.location.x = 0.0f;
                this.velocity.x *= -1.0f;
            }
            if ((double) this.location.y > ForceAccumulation.this.getHeight()) {
                this.location.y = (float) ForceAccumulation.this.getHeight();
                this.velocity.y *= -1.0f;
            }
        }
    }

}
