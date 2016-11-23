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
public class RankRow {
    public int rank, id, age;
    public String name;
    
    public RankRow(int id, String name, int age, int rank) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.rank = rank;
    }
}
