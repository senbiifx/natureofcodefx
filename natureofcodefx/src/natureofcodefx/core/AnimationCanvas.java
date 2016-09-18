
package natureofcodefx.core;

import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class AnimationCanvas extends Canvas {
    private AnimationTimer animationTimer;
    protected double mouseX;
    protected double mouseY;
    protected boolean mousePressed = false;
    protected long startFrameCount = System.currentTimeMillis();
    protected long frameCount = System.currentTimeMillis();
    public Runnable setup;
    public Runnable draw;
    
    AnimationCanvas(double w, double h) {
        super(w, h);
        this.init();
    }

    public AnimationCanvas() {
        this.init();
    }

    private void init() {
        this.addEventFilter(MouseEvent.ANY, e -> {
            if (e.getEventType() == MouseEvent.MOUSE_MOVED || e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                this.mouseX = e.getX();
                this.mouseY = e.getY();
            } else if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                this.mousePressed = true;
            } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
                this.mousePressed = false;
            }
        }
        );
        this.animationTimer = new AnimationTimer(){

            public void handle(long now) {
                ++AnimationCanvas.this.frameCount;
                Optional.ofNullable(draw).ifPresent((Runnable r) -> r.run() );
            }
        };
    }

    public final void start() {
        this.animationTimer.start();
    }

    public final void stop() {
        this.animationTimer.stop();
    }

}

