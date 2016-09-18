package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import natureofcodefx.core.PVector;
import toxi.geom.Rect;
import toxi.geom.Vec2D;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.VerletPhysics2D;
import toxi.physics2d.VerletSpring2D;
import toxi.physics2d.behaviors.GravityBehavior;
import toxi.physics2d.behaviors.ParticleBehavior2D;

public class HangingCloth extends AnimationCanvas {

    private VerletPhysics2D physics;
    private Particle p1;
    private Particle p2;
    private float len = 5.0f;
    private float str = 1.0f;
    private Particle pin1;
    private Particle pin2;
    private Particle heldParticle;
    private boolean locked;
    private Particle[][] p;
    private int dimension = 40;
    private float startX;
    private float startY;

    public HangingCloth() {
        setup = () -> {
            int y;
            int x;
            this.physics = new VerletPhysics2D();
            this.physics.addBehavior((ParticleBehavior2D) new GravityBehavior(new Vec2D(0.0f, 0.8f)));
            this.physics.setWorldBounds(new Rect(0.0f, 0.0f, (float) this.getWidth(), (float) this.getHeight()));
            this.p = new Particle[this.dimension][this.dimension];
            float start = (float) (this.getWidth() / 2.0 - (double) ((float) this.dimension * this.len / 2.0f));
            for (y = 0; y < this.dimension; ++y) {
                for (x = 0; x < this.dimension; ++x) {
                    this.p[x][y] = new Particle(start + this.len * (float) x, (float) y * this.len);
                    this.physics.addParticle((VerletParticle2D) this.p[x][y]);
                }
            }
            for (y = 0; y < this.dimension; ++y) {
                for (x = 0; x < this.dimension; ++x) {
                    VerletSpring2D vsd;
                    Particle current = this.p[x][y];
                    if (x - 1 >= 0) {
                        vsd = new VerletSpring2D((VerletParticle2D) this.p[x - 1][y], (VerletParticle2D) current, this.len, this.str);
                        this.physics.addSpring(vsd);
                    }
                    if (y - 1 >= 0) {
                        vsd = new VerletSpring2D((VerletParticle2D) this.p[x][y - 1], (VerletParticle2D) current, this.len, this.str);
                        this.physics.addSpring(vsd);
                    }
                    if (x + 1 < this.dimension) {
                        vsd = new VerletSpring2D((VerletParticle2D) this.p[x + 1][y], (VerletParticle2D) current, this.len, this.str);
                        this.physics.addSpring(vsd);
                    }
                    if (y + 1 >= this.dimension) {
                        continue;
                    }
                    vsd = new VerletSpring2D((VerletParticle2D) this.p[x][y + 1], (VerletParticle2D) current, this.len, this.str);
                    this.physics.addSpring(vsd);
                }
            }
            this.pin1 = this.p[0][0];
            this.pin2 = this.p[this.dimension - 1][0];
            this.pin1.lock();
            this.pin2.lock();
            this.locked = false;
        };

        draw = () -> {
            int x;
            int y;
            if (this.mousePressed) {
                PVector mouse = new PVector((float) this.mouseX, (float) this.mouseY);
                if (!this.locked) {
                    block0:
                    for (x = 0; x < this.dimension; ++x) {
                        for (y = 1; y < this.dimension; ++y) {
                            if (!this.isWithinRadius(mouse, new PVector(this.p[x][y].x, this.p[x][y].y))) {
                                continue;
                            }
                            this.heldParticle = this.p[x][y];
                            this.locked = true;
                            break block0;
                        }
                    }
                }
                if (this.heldParticle != null) {
                    PVector force = PVector.sub((PVector) mouse, (PVector) new PVector(this.heldParticle.x, this.heldParticle.y));
                    force.normalize();
                    force.mult(200.0f);
                    this.heldParticle.x = mouse.x;
                    this.heldParticle.y = mouse.y;
                }
            } else {
                this.locked = false;
                this.heldParticle = null;
            }
            this.physics.update();
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill((Paint) Color.WHITE);
            g.fillRect(0.0, 0.0, this.getWidth(), this.getHeight());
            g.setStroke((Paint) Color.GRAY);
            g.beginPath();
            for (int y2 = 0; y2 < this.dimension; ++y2) {
                g.moveTo((double) this.p[0][y2].x, (double) this.p[0][y2].y);
                for (int x2 = 1; x2 < this.dimension; ++x2) {
                    g.lineTo((double) this.p[x2][y2].x, (double) this.p[x2][y2].y);
                }
            }
            for (x = 0; x < this.dimension; ++x) {
                g.moveTo((double) this.p[x][0].x, (double) this.p[x][0].y);
                for (y = 1; y < this.dimension; ++y) {
                    g.lineTo((double) this.p[x][y].x, (double) this.p[x][y].y);
                }
            }
            g.stroke();
        };
    }

    private boolean isWithinRadius(PVector mouse, PVector particle) {
        return PVector.sub((PVector) mouse, (PVector) particle).mag() < 10.0f;
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
