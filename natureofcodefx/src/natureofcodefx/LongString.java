package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import toxi.geom.Rect;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.VerletPhysics2D;
import toxi.physics2d.VerletSpring2D;
import toxi.physics2d.behaviors.GravityBehavior;
import toxi.physics2d.behaviors.ParticleBehavior2D;

public class LongString
        extends AnimationCanvas {

    private VerletPhysics2D physics;
    private Particle p1;
    private Particle p2;
    private List<Particle> particles;
    private float len = 1.0f;
    private float str = 0.04f;
    private Particle tail;
    private Particle head;

    public LongString() {
        setup = () -> {
            this.physics = new VerletPhysics2D();
            this.physics.addBehavior((ParticleBehavior2D) new GravityBehavior(new Vec2D(0.0f, 0.1f)));
            this.physics.setWorldBounds(new Rect(0.0f, 0.0f, (float) this.getWidth(), (float) this.getHeight()));
            this.particles = new ArrayList<Particle>();
            this.head = new Particle((float) this.getWidth() / 2.0f, 0.0f);
            this.particles.add(this.head);
            for (int i = 0; i < 25; ++i) {
                Particle particle = new Particle((float) i * this.len + this.head.x, 10.0f);
                this.particles.add(particle);
                this.physics.addParticle((VerletParticle2D) particle);
            }
            Particle prev = this.particles.get(0);
            prev.lock();
            for (int i2 = 1; i2 < this.particles.size(); ++i2) {
                VerletSpring2D spring = new VerletSpring2D((VerletParticle2D) prev, (VerletParticle2D) this.particles.get(i2), this.len, this.str);
                this.physics.addSpring(spring);
                prev = this.particles.get(i2);
            }
            this.tail = this.particles.get(this.particles.size() - 1);
            this.tail.setWeight(2.0f);
        };
        
        draw = () -> {
            this.physics.update();
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            g.setStroke((Paint) Color.GRAY);
            g.beginPath();
            g.moveTo((double) this.head.x, (double) this.head.y);
            this.particles.stream().forEach(particle -> {
                g.lineTo((double) particle.x, (double) particle.y);
            }
            );
            g.stroke();
            this.tail.draw(g);
            if (this.mousePressed) {
                this.tail.lock();
                this.tail.x = (float) this.mouseX;
                this.tail.y = (float) this.mouseY;
                this.tail.unlock();
            }
        };
    }


    private class Particle
            extends VerletParticle2D {

        public Particle(float f, float f1) {
            super(f, f1);
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
