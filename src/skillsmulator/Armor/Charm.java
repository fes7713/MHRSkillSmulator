/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import skillsmulator.Skill.Skill;
import skillsmulator.Skill.UnknownSkill;
import skillsmulator.Slot;

/**
 *
 * @author fes77
 */
public class Charm extends Armor{
    List<Entry<Skill, Integer>> entries;
    public Charm(String title, Map<Skill, Integer> skills, int slot3, int slot2, int slot1) {
        super(title, skills, slot3, slot2, slot1);
        entries = skills.entrySet().stream().toList();
    }

    public Charm(String title, int slot3, int slot2, int slot1) {
        super(title, slot3, slot2, slot1);
        entries = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Charm{"  + getName() +   '}';
    }
    
    public Skill getSkill1(){
        if(!skills.isEmpty())
            return entries.get(0).getKey();
        return new UnknownSkill("", 10);
    }
    
    public Integer getSkill1Level(){
        if(!skills.isEmpty())
            return entries.get(0).getValue();
        return 0;
    }
    
    public Skill getSkill2(){
        if(skills.size() >= 2)
            return entries.get(1).getKey();
        return new UnknownSkill("", 10);
    }
    
    public Integer getSkill2Level(){
        if(skills.size() >= 2)
            return entries.get(1).getValue();
        return 0;
    }
    
    public Slot getSlot()
    {
        return new Slot(getSlot3(), getSlot2(), getSlot1());
    }
}
