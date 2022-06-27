/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Armor;

import java.util.Map;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class UnknownArmor extends Armor{

    public UnknownArmor(String title, Map<Skill, Integer> skills, int slot3, int slot2, int slot1) {
        super(title, skills, slot3, slot2, slot1);
    }

    public UnknownArmor(String title, int slot3, int slot2, int slot1) {
        super(title, slot3, slot2, slot1);
    }
    
    public Helm convHelm()
    {
        return new Helm(getName(), getSkills(), getSlot3(), getSlot2(), getSlot1());
    }
    
    public Chest convChest()
    {
        return new Chest(getName(), getSkills(), getSlot3(), getSlot2(), getSlot3());
    }
    
    public Arm convArm()
    {
        return new Arm(getName(), getSkills(), getSlot3(), getSlot2(), getSlot3());
    }
    
    public Waist convWaist()
    {
        return new Waist(getName(), getSkills(), getSlot3(), getSlot2(), getSlot3());
    }
    
    public Leg convLeg()
    {
        return new Leg(getName(), getSkills(), getSlot3(), getSlot2(), getSlot3());
    }
    public Charm convCharm()
    {
        return new Charm(getName(), getSkills(), getSlot3(), getSlot2(), getSlot3());
    }
}
