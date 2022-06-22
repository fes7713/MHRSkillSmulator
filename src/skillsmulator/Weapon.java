/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

/**
 *
 * @author fes77
 */
public class Weapon implements Decoratable{
    private final String name;
   
    private final int damage;
    private final int affinity;
    private int slot1;
    private int slot2;
    private int slot3;

    public Weapon(String name, int damage, int affinity, int slot3, int slot2, int slot1) {
        this.name = name;
        this.damage = damage;
        this.affinity = affinity;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
    }
    
    public Weapon(String name, int damage, int affinity) {
        this.name = name;
        this.damage = damage;
        this.affinity = affinity;
        slot1 = 0;
        slot2 = 0;
        slot3 = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getDamage() {
        return damage;
    }
    
    public int getAffinity() {
        return affinity;
    }

    public int getSlot1() {
        return slot1;
    }

    public int getSlot2() {
        return slot2;
    }

    public int getSlot3() {
        return slot3;
    }
    
    
}
