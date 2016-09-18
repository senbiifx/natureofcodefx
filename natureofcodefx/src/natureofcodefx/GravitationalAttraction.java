package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import java.util.Random;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;

public class GravitationalAttraction extends AnimationCanvas {

    private Mover[] movers;
    private Attractor attractor;

    public GravitationalAttraction() {
        setup = () -> {
            this.setCursor(Cursor.NONE);
            this.attractor = new Attractor();
            this.movers = new Mover[10];
            for (int i = 0; i < this.movers.length; ++i) {
                this.movers[i] = new Mover();
            }
        };

        draw = () -> {
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            this.attractor.update();
            this.attractor.draw(g);
            for (Mover m : this.movers) {
                PVector attraction = this.attractor.attract(m);
                m.applyForce(attraction);
                m.update();
                m.draw(g);
            }
        };
    }


    private class Mover {

        PVector location;
        PVector velocity;
        PVector acceleration;
        float mass;
        float angle;
        float aVelocity;
        float aAcceleration;
        float rotationDirection;

        Mover() {
            this.angle = 0.0f;
            this.aVelocity = 0.0f;
            this.aAcceleration = 0.01f;
            Random random = new Random();
            this.location = new PVector((float) random.nextInt(640), (float) random.nextInt(320));
            this.velocity = new PVector(1.0f, 0.0f);
            this.acceleration = new PVector(0.0f, 0.0f);
            this.mass = (float) (random.nextDouble() + 0.01) * 8.0f;
            this.rotationDirection = random.nextInt(2) == 0 ? 1.0f : -1.0f;
        }

        void update() {
            this.velocity.add(this.acceleration);
            this.location.add(this.velocity);
            this.aAcceleration = this.acceleration.x * this.rotationDirection;
            this.aVelocity += this.aAcceleration;
            this.angle += this.aVelocity;
            this.acceleration.mult(0.0f);
        }

        void applyForce(PVector force) {
            PVector f = force.get();
            f.div(this.mass);
            this.acceleration.add(f);
        }

        void draw(GraphicsContext g) {
            g.setFill((Paint) Color.GRAY);
            g.setStroke((Paint) Color.BLACK);
            g.save();
            g.translate((double) this.location.x, (double) this.location.y);
            g.rotate((double) this.angle);
            double size = this.mass * 10.0f;
            double half = this.mass * 10.0f / 2.0f;
            g.strokeRect(-half, -half, size, size);
            g.restore();
        }
    }

    private class Attractor {

        float mass;
        PVector location;
        final float G;

        Attractor() {
            this.mass = 20.0f;
            this.G = 0.4f;
            this.location = new PVector();
        }

        void update() {
            this.location.x = (float) (GravitationalAttraction.this.mouseX - (double) (this.mass * 2.0f / 2.0f));
            this.location.y = (float) (GravitationalAttraction.this.mouseY - (double) (this.mass * 2.0f / 2.0f));
        }

        void draw(GraphicsContext g) {
            g.setFill((Paint) Color.GRAY);
            g.fillOval((double) this.location.x, (double) this.location.y, (double) (this.mass * 2.0f), (double) (this.mass * 2.0f));
        }

        PVector attract(Mover m) {
            PVector force = PVector.sub((PVector) this.location, (PVector) m.location);
            float d = force.mag();
            d = this.constrain(d, 5.0f, 25.0f);
            force.normalize();
            float strength = this.G * this.mass * m.mass / (d * d);
            force.mult(strength);
            return force;
        }

        float constrain(float amt, float low, float high) {
            return amt < low ? low : (amt > high ? high : amt);
        }
    }

}
