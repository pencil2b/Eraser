package data;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

public class WorldTest{
    World world;
    ArrayList<Player> players;
    ArrayList<Bullet> bullets;

    @Before
    public void setUp(){
        world = new World();
        players = new ArrayList<>();
        bullets = new ArrayList<>();
        world.setObjects(players, bullets);
    }

    @Test
    public void testFindPlayerByIdNullIfNotExists(){
        Player result = world.findPlayerById(10);
        assertNull("FindPlayerById() finds player but no one even exists", result);
    }

    @Test
    public void testFindPlayerById(){
        players.add(new Player(44, "Name"));
        world.setObjects(players, bullets);
        Player result = world.findPlayerById(44);
        assertEquals("FindPlayerById() finds wrong player", result.id, 44);
    }
}