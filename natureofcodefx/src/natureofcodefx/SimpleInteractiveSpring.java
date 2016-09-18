package natureofcodefx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.AnimationCanvas;
import toxi.geom.ReadonlyVec2D;
import toxi.geom.Rect;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.VerletPhysics2D;
import toxi.physics2d.VerletSpring2D;
import toxi.physics2d.behaviors.GravityBehavior;
import toxi.physics2d.behaviors.ParticleBehavior2D;

public class SimpleInteractiveSpring
        extends AnimationCanvas {

    private VerletPhysics2D physics;
    private Particle p1;
    private Particle p2;

    public SimpleInteractiveSpring() {
        setup = () -> {
            double w = this.getWidth();
            double h = this.getHeight();
            this.physics = new VerletPhysics2D();
            this.physics.addBehavior((ParticleBehavior2D) new GravityBehavior(new Vec2D(0.0f, 1.0f)));
            this.physics.setWorldBounds(new Rect(0.0f, 0.0f, (float) w, (float) h));
            this.p1 = new Particle(new Vec2D((float) w / 2.0f, 20.0f));
            this.p2 = new Particle(new Vec2D((float) w / 2.0f + 160.0f, 180.0f));
            this.p1.lock();
            VerletSpring2D spring = new VerletSpring2D((VerletParticle2D) this.p1, (VerletParticle2D) this.p2, 150.0f, 0.01f);
            this.physics.addParticle((VerletParticle2D) this.p1);
            this.physics.addParticle((VerletParticle2D) this.p2);
            this.physics.addSpring(spring);
        };

        draw = () -> {
            this.physics.update();
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            g.setStroke((Paint) Color.GRAY);
            g.strokeLine((double) this.p1.x, (double) this.p1.y, (double) this.p2.x, (double) this.p2.y);
            this.p1.draw(g);
            this.p2.draw(g);
            if (this.mousePressed) {
                this.p2.lock();
                this.p2.x = (float) this.mouseX;
                this.p2.y = (float) this.mouseY;
                this.p2.unlock();
            }
        };
    }

    private class Particle
            extends VerletParticle2D {

        public Particle(Vec2D vec2D) {
            super((ReadonlyVec2D) vec2D);
        }

        void draw(GraphicsContext g) {
            g.setFill((Paint) Color.GRAY);
            g.setStroke((Paint) Color.BLACK);
            g.fillOval((double) (this.x - 20.0f), (double) (this.y - 20.0f), 40.0, 40.0);
            g.setLineWidth(2.0);
            g.strokeOval((double) (this.x - 20.0f), (double) (this.y - 20.0f), 40.0, 40.0);
        }
    }

}
