/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eraser;

/**
 *
 * @author dorian
 */
public class Debug {
    
    public static void info(String s) {
        System.out.println("[+] " + s);
    }
    
    public static void error(String s) {
        System.out.println("[!] " + s);
    }
    
    public static void show(String s) {
        System.out.println("[*] " + s);
    }
    
    public static void print(String s) {
        System.out.println(s);
    }
    
    public static void tcpRecv(String s) {
        System.out.println("TCP >> " + s);
    }
    
    public static void tcpSend(String s) {
        System.out.println("TCP << " + s);
    }
}
