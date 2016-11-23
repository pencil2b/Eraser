/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

/**
 *
 * @author dorian
 */
public class PlayerRow {
    public int rank, id;
    public String name;
    
    public PlayerRow(int id, String name, int rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
    }
}
