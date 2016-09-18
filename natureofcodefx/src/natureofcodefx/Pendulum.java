package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;

public class Pendulum
        extends AnimationCanvas {

    private Bob bob;

    public Pendulum() {
        setup = () -> {
            this.bob = new Bob();
        };
        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.save();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            g.translate(this.getWidth() / 2.0, 0.0);
            this.bob.update();
            this.bob.draw(g);
            g.restore();
        };
    }

    private class Bob {

        float armLength;
        float gravity;
        float angle;
        float aAcceleration;
        float aVelocity;
        float friction;
        PVector location;

        Bob() {
            this.location = new PVector();
            this.armLength = 200.0f;
            this.gravity = 0.8f;
            this.aVelocity = 0.0f;
            this.aAcceleration = 0.0f;
            this.friction = 0.97f;
        }

        void draw(GraphicsContext g) {
            g.setStroke((Paint) Color.BLACK);
            g.setFill((Paint) Color.GRAY);
            g.setLineWidth(2.0);
            g.strokeLine(0.0, 0.0, (double) this.location.x, (double) this.location.y);
            g.fillOval((double) (this.location.x - 40.0f), (double) (this.location.y - 40.0f), 80.0, 80.0);
            g.strokeOval((double) (this.location.x - 40.0f), (double) (this.location.y - 40.0f), 80.0, 80.0);
        }

        void update() {
            this.aAcceleration = (float) ((double) (-1.0f * this.gravity / this.armLength) * Math.sin(this.angle));
            this.aVelocity += this.aAcceleration;
            this.angle += this.aVelocity;
            this.aVelocity *= this.friction;
            this.location.x = (float) ((double) this.armLength * Math.sin(this.angle));
            this.location.y = (float) ((double) this.armLength * Math.cos(this.angle));
            if (Pendulum.this.mousePressed) {
                PVector mouse = new PVector((float) Pendulum.this.mouseX, (float) Pendulum.this.mouseY);
                PVector center = new PVector((float) (Pendulum.this.getWidth() / 2.0), 0.0f);
                PVector dir = PVector.sub((PVector) mouse, (PVector) center);
                dir.normalize();
                dir.mult(this.armLength);
                this.location.x = dir.x;
                this.location.y = dir.y;
                this.angle = (float) Math.atan2(this.location.x, this.location.y);
            }
        }
    }

}
