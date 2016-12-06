
package graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import world.*;

/**
 *
 * @author dorian
 */
public class Scene extends Canvas {
    
    AffineTransform transformReverse;
    
    public Scene(Dimension canvasSize) {
        setup(canvasSize);
    }
    
    private void setup(Dimension canvasSize) {
        this.setPreferredSize(canvasSize);
	this.setMinimumSize(canvasSize);
	this.setMaximumSize(canvasSize);
    }
    
    public void render(World world, int playerId, ArrayList<Player> rankList) {
        
        Graphics2D g = (Graphics2D) this.getBufferStrategy().getDrawGraphics();
        
        BackgroundRenderer.renderBackgroundColor(g, this.getSize());
        
        transformForPlayer(g, world.findPlayer(playerId));
        
        BackgroundRenderer.renderWorldGrid(g, new Dimension(world.width, world.height));
        
        
        world.players.forEach((p) -> {
            PlayerRenderer.render(g, p);
        });
        
        world.bullets.forEach((b) -> {
            BulletRenderer.render(g, b);
        });
        
        restoreTransform(g);
        
        PanelRenderer.renderPanel(g, rankList);
        
        g.dispose();
        
        // Show graphics
	BufferStrategy strategy = this.getBufferStrategy();
	if (!strategy.contentsLost()) {
            strategy.show();
	}

	// Synchronizes graphics state
	Toolkit.getDefaultToolkit().sync();
    }
    
    private void transformForPlayer(Graphics2D g, Player player) {
        Point center = getCenter(), position;
        if(player != null) {
            position = player.position();
        } else {
            position = getCenter();
        }
        transformReverse = AffineTransform.getTranslateInstance(position.x - center.x, position.y - center.y);
        g.transform(AffineTransform.getTranslateInstance(center.x - position.x, center.y - position.y));
    }
    
    private void restoreTransform(Graphics2D g) {
        g.transform(transformReverse);
    }
    
    public Point getCenter() {
        return new Point(this.getWidth() / 2, this.getHeight() / 2);
    }

    public void render(World world, int id, HashMap<Integer, String> nameList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
