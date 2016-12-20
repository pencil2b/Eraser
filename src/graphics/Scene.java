package graphics;

import data.Player;
import data.World;
import control.MouseControl;
import game.Events;
import game.Game;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

/**
 * Game canvas
 *
 * @author dorian
 */
class Scene extends Canvas {

    private AffineTransform transformReverse;

    Scene(Dimension canvasSize) {
        setup(canvasSize);
    }

    private void setup(Dimension canvasSize) {
        this.setPreferredSize(canvasSize);
        this.setMinimumSize(canvasSize);
        this.setMaximumSize(canvasSize);
    }

    void render() {

        Graphics2D g = (Graphics2D) this.getBufferStrategy().getDrawGraphics();

        Renderer.renderBackground(g, this.getSize());

        transformForPlayer(g, World.findPlayer(Game.id));

        Renderer.renderGrid(g, new Dimension(World.getWidth(), World.getHeight()));

        World.getPlayers().forEach((p) -> {
            Renderer.renderPlayer(g, p);
        });

        World.getBullets().forEach((b) -> {
            Renderer.renderBullet(g, b);
        });

        restoreTransform(g);

        Renderer.renderPanel(g, Events.getRankList());

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
        if (player != null) {
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

    Point getCenter() {
        return new Point(this.getWidth() / 2, this.getHeight() / 2);
    }

    void addListeners(MouseControl mouseControl) {
        addMouseListener(mouseControl);
        addMouseMotionListener(mouseControl);
    }
}
