/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author fes77
 */
public class Weapon implements Decoratable{
    private final String name;
   
//    private final int damage;
    private final IntegerProperty damage;
    private final IntegerProperty affinity;
    private ObjectProperty<Slot> slot;

    public Weapon(String name, int damage, int affinity, int slot3, int slot2, int slot1) {
        this.name = name;
        this.damage = new SimpleIntegerProperty(damage);
        this.affinity = new SimpleIntegerProperty(affinity);
        slot = new SimpleObjectProperty();
        slot.set(new Slot(slot3, slot2, slot1));
    }
    
    public Weapon(String name, int damage, int affinity, Slot slot) {
        this.name = name;
        this.damage = new SimpleIntegerProperty(damage);
        this.affinity = new SimpleIntegerProperty(affinity);
        this.slot = new SimpleObjectProperty();
        this.slot.set(slot);
    }
    
    public Weapon(String name, int damage, int affinity) {
        this.name = name;
        this.damage = new SimpleIntegerProperty(damage);
        this.affinity = new SimpleIntegerProperty(affinity);
        slot = new SimpleObjectProperty();
        this.slot.set(new Slot(0, 0, 0));
    }

    @Override
    public String toString() {
        return "Weapon{" + "name=" + name + ", damage=" + damage.get() + ", affinity=" + affinity.get() + ", slot=" + slot.get().toString() + '}';
    }

    

    public int getDamage() {
        return damage.get();
    }
    
    public int getAffinity() {
        return affinity.get();
    }

    public IntegerProperty getDamageProperty()
    {
        return damage;
    }
    
    public IntegerProperty getAffinityProperty()
    {
        return affinity;
    }
    
    public ObjectProperty<Slot> getSlotProperty()
    {
        return slot;
    }
    
    public int getSlot1() {
        return slot.get().getSlot1();
    }

    public int getSlot2() {
        return slot.get().getSlot2();
    }

    public int getSlot3() {
        return slot.get().getSlot3();
    }
    
    
}
