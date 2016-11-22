
package graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import world.*;

/**
 *
 * @author dorian
 */
public class GameCanvas extends Canvas {
    
    public GameCanvas(Dimension canvasSize) {
        setup(canvasSize);
    }
    
    private void setup(Dimension canvasSize) {
        this.setPreferredSize(canvasSize);
	this.setMinimumSize(canvasSize);
	this.setMaximumSize(canvasSize);
    }
    
    public void render(World world) {
        Graphics2D g = (Graphics2D) this.getBufferStrategy().getDrawGraphics();
        //
        // [!] maybe you should do transform here
        //
        
        Background background = new Background(getWidth(), getHeight(), getCenter());
        background.render(g);
        
        world.players.forEach((p) -> {
            PlayerRenderer playerRenderer = new PlayerRenderer(p);
            playerRenderer.render(g);
        });
        
        world.bullets.forEach((b) -> {
            BulletRenderer bulletRenderer = new BulletRenderer(b);
            bulletRenderer.render(g);
        });
        
        g.dispose();
        
        // Show graphics
	BufferStrategy strategy = this.getBufferStrategy();
	if (!strategy.contentsLost()) {
            strategy.show();
	}

	// Synchronizes graphics state
	Toolkit.getDefaultToolkit().sync();
    }
    
    public Point getCenter() {
        return new Point(this.getWidth() / 2, this.getHeight() / 2);
    }
}
