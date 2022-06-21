/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import skillsmulator.Skill.AffinitySkill;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.DamageUpMultiplePreSkill;
import skillsmulator.Skill.DamageUpSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Armor implements Comparable<Armor>{
    private final String title;
    private final Map<Skill, Integer> skills;
    private int slot1;
    private int slot2;
    private int slot3;

    private int score;
    
    private List<Armor> similar;
    
    public Armor(String title, Map<Skill, Integer> skills, int slot3, int slot2, int slot1) {
        this.title = title;
        this.skills = skills;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        updateScore();
        
    }
    
    public Armor(String title, int slot3, int slot2, int slot1) {
        this.title = title;
        this.skills = new HashMap();
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        
    }

    @Override
    public String toString() {
        return title;
    }
    
    public void updateScore()
    {
        score = slot1 * 1 + slot2 * 2 + slot3 * 3;
        score += skills
                .keySet()
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .map(skill -> skill.getCost() * skills.get(skill))
                .reduce(0, Integer::sum);
    }
    
    public void addSkill(Skill skill, int level)
    {
        if(level < 0)
            throw new IllegalArgumentException("Level should be mor than 0 in code");
        
        if(level > skill.getMax())
            level = skill.getMax() - 1;
        skills.put(skill, level);
        updateScore();
    }
    
    public int getScore()
    {
        return score;
    }
    
    public Map<Skill, Integer> getSkills() {
        return skills;
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
    
    public void addSimilar(Armor armor)
    {
        similar.add(armor);
    }
    
    public void clearSimilar(Armor armor)
    {
        similar.clear();
    }
    
    @Override
    public int compareTo(Armor o) {
//        if(equals(o))
//            return 0;
        
        // Chekc if two has the same skills
        // It does not check skill level here.
        
        boolean allMatch1 = skills
                    .keySet()
                    .stream()
                    .filter(skill -> skill.isActive())
                    .allMatch(skill -> o.getSkills().containsKey(skill));
        boolean allMatch2 = o.getSkills()
                    .keySet()
                    .stream()
                    .filter(skill -> skill.isActive())
                    .allMatch(skill -> skills.containsKey(skill));
        if(!allMatch1 || !allMatch2)
            return 0;
        
        // Same slot
        if(slot1 == o.getSlot1() && slot2 == o.getSlot2() && slot3 == o.getSlot3())
        {
            boolean allEqual = skills
                        .keySet()
                        .stream()
                        .filter(skill -> skill.isActive())
                        .allMatch(skill -> skills.get(skill) == o.getSkills().get(skill));
            if(allEqual)
                return 0;
            
            
            boolean compare1to2 = skills
                        .keySet()
                        .stream()
                        .filter(skill -> skill.isActive())
                        .allMatch(skill -> skills.get(skill) >= o.getSkills().get(skill));
            
            boolean compare2to1 = skills
                        .keySet()
                        .stream()
                        .filter(skill -> skill.isActive())
                        .allMatch(skill -> skills.get(skill) <= o.getSkills().get(skill));
            
            if(compare1to2)
                return 1;
            if(compare2to1)
                return -1;
            
            return 0;
        }
        else{
            boolean compare = skills
                        .keySet()
                        .stream()
                        .filter(skill -> skill.isActive())
                        .allMatch(skill -> skills.get(skill) == o.getSkills().get(skill));
            
            if(compare)
            {
                int slot3Battle = slot3 - o.getSlot3();
                int slot2Battle = slot3 + slot2 - (o.getSlot3() + o.getSlot2());
                int slot1Battle = slot3 + slot2 + slot1 - (o.getSlot3() + o.getSlot2() + o.getSlot1());
                
                if(slot3Battle >= 0 && slot2Battle >= 0 && slot1Battle >= 0)
                    return 1;
                if(slot3Battle <= 0 && slot2Battle <= 0 && slot1Battle <= 0)
                    return -1;
                return 0;
            }
            else{
                return 0;
            }
        }
    }

//    @Override
//    public boolean equals(Object obj) {
//        if(obj instanceof Armor)
//        {
//            Armor o = (Armor)obj;
//            if(slot1 == o.getSlot1() && slot2 == o.getSlot2() && slot3 == o.getSlot3())
//            {
//                // Bidirection check
//                for(Skill skill : skills.keySet())
//                {
//                    if(!skill.isActive())
//                        continue;
//                    if(o.getSkills().containsKey(skill))
//                    {
//                        if(skills.get(skill).compareTo(o.getSkills().get(skill)) != 0)
//                            return false;
//                    }
//                    else{
//                        return false;
//                    }
//                }
//                
//                for(Skill skill : o.getSkills().keySet())
//                {
//                    if(!skill.isActive())
//                        continue;
//                    if(skills.containsKey(skill))
//                    {
//                        if(skills.get(skill).compareTo(o.getSkills().get(skill)) != 0)
//                            return false;
//                    }
//                    else{
//                        return false;
//                    }
//                }
//                return true;
//            }
//        }
//        return false;
//    }
    
    
    
    public static void main(String[] args)
    {
        Skill attackBoost = new DamageUpMultiplePreSkill("AttackBoost", 2, new int[]{3, 5, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
        Skill peakPerformance = new DamageUpSkill("PeakPerformance", 2, new int[]{5, 10, 20});
        Skill criticalEye = new AffinitySkill("CriticalEye", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
        
        
        Armor armor = new Armor("Helm", 0, 2, 1);
        armor.addSkill(attackBoost, 4);
        armor.addSkill(peakPerformance, 2);
        armor.addSkill(criticalEye, 4);
        
        Armor armor1 = new Armor("Helm1", 1, 1, 1);
        armor1.addSkill(attackBoost, 4);
        armor1.addSkill(peakPerformance, 2);
//        armor1.addSkill(criticalEye, 4);
//        criticalEye.setActive(false);
        
        System.out.println(armor.compareTo(armor1));
        System.out.println(armor.getScore());
    }

    
}
