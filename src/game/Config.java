/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;

/**
 *
 * @author dorian
 */
public class Config {
    
    public static int DEFAULT_CANVAS_WIDTH = 800;
    public static int DEFAULT_CANVAS_HEIGHT = 600;
    
    public static int MIN_CANVAS_WIDTH = 800;
    public static int MIN_CANVAS_HEIGHT = 600;
    
    public static int BULLET_RADIUS = 5;
    public static double PLAYER_VELOCITY = 3;
    
    public static int DEFAULT_WORLD_WIDTH = 3600;
    public static int DEFAULT_WORLD_HEIGHT = 3600;
    
    public static int GRID_WIDTH = 100;
    
    public static String TITLE = "Eraser";
    
    public static String START_TITLE = "WELCOME";
    public static String START_MESSAGE = "To connect to the server,\nplease input: \"NAME@IP:PORT\"";
    
    public static String END_TITLE = "GAME OVER";
    
    public static int GRAPHICS_UPS = 120;
    public static int CONTROL_UPS = 30;
    
    public static int PLAYER_DEFAULT_RADIUS = 10;
    public static Color PLAYER_INIT_COLOR = Color.GRAY;
    public static Color PLAYER_NORMAL_COLOR = Color.WHITE;
    public static Color PLAYER_DEAD_COLOR = Color.RED;
    public static Color PLAYER_NAME_COLOR = Color.ORANGE;
    
    public static Color BACKBROUND_COLOR = Color.BLACK;
    public static Color GRID_COLOR = Color.DARK_GRAY;
    public static Color RANK_COLOR = Color.LIGHT_GRAY;
    
}
