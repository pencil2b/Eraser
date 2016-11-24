
package graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
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
    
    public void render(World world, int playerId) {
        Graphics2D g = (Graphics2D) this.getBufferStrategy().getDrawGraphics();
        
        BackgroundRenderer background = new BackgroundRenderer(this, world);
        background.renderBackgroundColor(g);
        
        translateForPlayer(g, world.findPlayer(playerId));
        
        background.renderWorldGrid(g);
        
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
    
    private void translateForPlayer(Graphics2D g, Player player) {
        Point center = getCenter(), position;
        if(player != null) {
            position = player.position();
        } else {
            position = getCenter();
        }
        g.transform(AffineTransform.getTranslateInstance(center.x - position.x, center.y - position.y));
    }
    
    public Point getCenter() {
        return new Point(this.getWidth() / 2, this.getHeight() / 2);
    }
}
