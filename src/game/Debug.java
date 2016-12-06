/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author dorian
 */
public class Debug {

    public static boolean enabled;
    
    
    
    public static void info(String s) {
        if(enabled) {
            System.out.println("[+] " + s);
        }
    }
    
    public static void error(String s) {
        if(enabled) {
            System.out.println("[!] " + s);
        }
    }
    
    public static void show(String s) {
        if(enabled) {
            System.out.println("[*] " + s);
        }
    }
    
    public static void print(String s) {
        if(enabled) {
            System.out.println(s);
        }
    }
    
    public static void tcpRecv(String s) {
        if(enabled) {
            System.out.println("TCP >> " + s);
        }
    }
    
    public static void tcpSend(String s) {
        if(enabled) {
            System.out.println("TCP << " + s);
        }
    }
}
