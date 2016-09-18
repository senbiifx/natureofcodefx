package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;

public class DirectionOfMovement extends AnimationCanvas {

    private Mover mover;

    public DirectionOfMovement() {
        setup = () -> {
            this.mover = new Mover((float) this.getWidth(), (float) this.getHeight());
        };

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.setStroke((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            this.mover.update(new PVector((float) this.mouseX, (float) this.mouseY));
            this.mover.draw(g);
        };
    }

    class Mover {

        PVector location;
        PVector velocity;
        PVector acceleration;
        float topSpeed;
        double angle;

        Mover(float w, float h) {
            this.topSpeed = 10.0f;
            this.angle = 0.0;
            this.velocity = new PVector(0.0f, 0.0f);
            this.acceleration = new PVector(0.0f, 0.0f);
            this.location = new PVector(w, h);
        }

        void update(PVector mouse) {
            PVector acceleration = PVector.sub((PVector) mouse, (PVector) this.location);
            acceleration.normalize();
            this.velocity.add(acceleration);
            this.velocity.limit(this.topSpeed);
            this.location.add(this.velocity);
            this.angle = Math.atan2(this.velocity.y, this.velocity.x);
        }

        void draw(GraphicsContext g) {
            g.save();
            g.setFill((Paint) Color.GRAY);
            g.setStroke((Paint) Color.BLACK);
            g.translate((double) this.location.x, (double) this.location.y);
            g.rotate(Math.toDegrees(this.angle));
            g.fillRect(-35.0, -15.0, 70.0, 30.0);
            g.strokeRect(-35.0, -15.0, 70.0, 30.0);
            g.restore();
        }
    }

}
