package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;

public class SpringForces extends AnimationCanvas {

    private Spring spring;
    private Bob bob;
    private PVector anchorLocation;
    private PVector GRAVITY;

    public SpringForces() {

        setup = () -> {
            this.anchorLocation = new PVector((float) this.getWidth() / 2.0f, 0.0f);
            this.GRAVITY = new PVector(0.0f, 2.0f);
            this.spring = new Spring(0.0f, 0.0f, 100.0f);
            this.bob = new Bob();
        };

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.save();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            g.translate((double) this.anchorLocation.x, (double) this.anchorLocation.y);
            if (this.mousePressed) {
                this.bob.location.x = (float) (this.mouseX - (double) this.anchorLocation.x);
                this.bob.location.y = (float) (this.mouseY - (double) this.anchorLocation.y);
            } else {
                this.bob.applyForce(this.GRAVITY);
                this.spring.connect(this.bob);
                this.bob.update();
            }
            this.spring.draw(g, this.bob);
            this.bob.draw(g);
            g.restore();
        };
    }

    private class Spring {

        PVector anchor;
        float len;
        final float k = 0.1f;

        Spring(float x, float y, float l) {
            this.anchor = new PVector(x, y);
            this.len = l;
        }

        void connect(Bob bob) {
            PVector dir = PVector.sub((PVector) this.anchor, (PVector) bob.location);
            float currentLength = dir.mag();
            float delta = this.len - currentLength;
            dir.normalize();
            PVector force = PVector.mult((PVector) dir, (float) (-0.1f * delta));
            bob.applyForce(force);
        }

        void draw(GraphicsContext g, Bob bob) {
            g.setStroke((Paint) Color.BLACK);
            g.setLineWidth(2.0);
            g.strokeLine((double) this.anchor.x, (double) this.anchor.y, (double) bob.location.x, (double) bob.location.y);
        }
    }

    private class Bob {

        PVector location;
        PVector acceleration;
        PVector velocity;
        float mass;
        float friction;

        Bob() {
            this.mass = 24.0f;
            this.friction = 0.99f;
            this.location = new PVector(0.0f, 0.0f);
            this.acceleration = new PVector(0.0f, 0.0f);
            this.velocity = new PVector(0.0f, 0.0f);
        }

        void applyForce(PVector force) {
            PVector f = PVector.div((PVector) force, (float) this.mass);
            this.acceleration.add(f);
        }

        void update() {
            this.velocity.add(this.acceleration);
            this.velocity.mult(this.friction);
            this.location.add(this.velocity);
            this.acceleration.mult(0.0f);
        }

        void draw(GraphicsContext g) {
            g.setFill((Paint) Color.GRAY);
            g.setStroke((Paint) Color.BLACK);
            g.fillOval((double) (this.location.x - 40.0f), (double) (this.location.y - 40.0f), 80.0, 80.0);
            g.strokeOval((double) (this.location.x - 40.0f), (double) (this.location.y - 40.0f), 80.0, 80.0);
        }
    }

}
