/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static skillsmulator.Simulator.attackBoost;
import static skillsmulator.Simulator.criticalBoost;
import static skillsmulator.Simulator.criticalEye;
import static skillsmulator.Simulator.peakPerformance;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Equipment implements Comparable<Equipment>{
    private Weapon weapon;
    private Armor helm;
    private Armor chest;
    private int score;

    private Map<Skill, Integer> skills;
    
    private Map<Skill, Integer> decorations;
    private Map<Skill, Integer> bestDecorations;
    private double bestExpectation;
    boolean calculated;
    public Equipment(Weapon weapon, Armor helm, Armor chest){
        this.weapon = weapon;
        this.helm = helm;
        this.chest = chest;
        calculated = false;
        skills = findSkillMap();
        decorations = new HashMap();
        bestDecorations = new HashMap<>();
        updateScore();
    }
   
    private Map<Skill, Integer> findSkillMap()
    {
        Map<Skill, Integer> skillMap = new HashMap();
        skillMap.putAll(helm.getSkills());
        
        Map<Skill, Integer> chestSkills = chest.getSkills();
        for(Skill skill : chestSkills.keySet())
        {
            if(skillMap.containsKey(skill))
            {
                int sum = skillMap.get(skill) + chestSkills.get(skill);
                if(skill.getMax() > sum)
                {
                    skillMap.put(skill, sum);
                }
                else{
                    skillMap.put(skill, skill.getMax() - 1);
                }
            }
            else{
                skillMap.put(skill, chestSkills.get(skill));
            }
        }

        return skillMap;
    }
    
    public void updateScore()
    {
        score = 0;
        score += helm.getScore();
        score += chest.getScore();
    }
    
    public void updateBestDecoration(Skill... activeSkills)
    {
        updateBestDecoration(Arrays.asList(activeSkills));
    }
    
    public void updateBestDecoration(List<Skill> activeSkills)
    {
        activeSkills = activeSkills.stream().filter(Skill::isActive).toList();
        
        double bestExpectation = 0;
        
        int availableSlots3 = Stream.of(weapon, helm, chest).map(deco -> deco.getSlot3()).reduce(0, (a, b) -> a + b);
        int availableSlots2 = Stream.of(weapon, helm, chest).map(deco -> deco.getSlot2()).reduce(0, (a, b) -> a + b);
        int availableSlots1 = Stream.of(weapon, helm, chest).map(deco -> deco.getSlot1()).reduce(0, (a, b) -> a + b);
        
//        System.out.println("avbailableSlots3 : " + availableSlots3);
//        System.out.println("avbailableSlots2 : " + availableSlots2);
//        System.out.println("avbailableSlots1 : " + availableSlots1);
        
        
        List<Skill> slot3Skills = activeSkills.stream().filter(skill -> skill.getCost() == 3).toList();
        List<Skill> slot2Skills = activeSkills.stream().filter(skill -> skill.getCost() == 2).toList();
        
//        System.out.println("slot3Skills" + slot3Skills);
//        System.out.println("slot2Skills" + slot2Skills);
        
        Map<Skill, Integer> skillIncrease = skills.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Entry::getKey, 
                                entry -> entry.getKey().getMax() - entry.getValue() - 1
                        )
                );
        activeSkills
                .stream()
                .filter(skill -> !skillIncrease.containsKey(skill))
                .forEach(skill -> skillIncrease.put(skill, skill.getMax()));
//                        Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
        
//        List<Skill> skillKeys = skillIncrease.keySet().stream().toList();
        int remainingSkillSum = skillIncrease.entrySet().stream().map(Entry::getValue).reduce(0, Integer::sum);
        bestExpectation = 0;
        
        if(!slot2Skills.isEmpty())
        {
            bestExpectation = 0;
            skillLooper(skillIncrease, slot2Skills, 0, availableSlots2 + availableSlots3, remainingSkillSum);
            decorations = bestDecorations
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != 0)
                .collect(
                        Collectors.toMap(
                                Entry::getKey, 
                                Entry::getValue
                        )
                );
        }
    }
    
    private void skillLooper(
            Map<Skill, Integer> skillSizeMap, 
            List<Skill> skillKeys, 
            int skillIndex, 
            int remainingSlotCount,
            int remainingSkillSum)
    {
        Skill keySkill = skillKeys.get(skillIndex);
        int skillSize = skillSizeMap.get(keySkill);
        
        if(remainingSlotCount < 0)
            throw new RuntimeException("Negative slot");
//        if(remainingSlotCount == 0)
//        {
//            decorations.put(keySkill, 0);
//            skillLooper(skillSizeMap, skillKeys, skillIndex + 1, remainingSlotCount - i, remainingSkillSum - skillSize);
//            return;
//        }
        
        if(remainingSlotCount <= skillSize)
        {
            decorations.put(keySkill, remainingSlotCount);
        }
        
        if(skillIndex == skillKeys.size() - 1)
        {
//            System.out.println("Reached End");
//            System.out.println(decorations);
            double currentExp = getExpectation();
            if(currentExp > bestExpectation)
            {
                bestExpectation = currentExp;
                bestDecorations.clear();
                bestDecorations.putAll(decorations);
            }
            return;
        }
        
        int skillLoopStart = 0;
        
        if(remainingSlotCount > remainingSkillSum - skillSize)
        {
            //works
            if(remainingSlotCount > remainingSkillSum)
                throw new RuntimeException("Error detected");
            int difference = remainingSlotCount - (remainingSkillSum - skillSize);
            skillLoopStart = difference;
        }
        
        for(int i = skillLoopStart; i <= skillSize && i <= remainingSlotCount; i++)
        {
//            if(i != 0)
            decorations.put(keySkill, i);
            skillLooper(skillSizeMap, skillKeys, skillIndex + 1, remainingSlotCount - i, remainingSkillSum - skillSize);
        }
    }
     
    public Map<Skill, Integer> getSkillMap()
    {
        Map<Skill, Integer>decorationsArranged = decorations
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != 0)
                .collect(
                        Collectors.toMap(
                                Entry::getKey, 
                                Entry::getValue
                        )
                );
        
        return Stream.of(decorationsArranged, skills)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
    }
    
    
    
    
    public double getExpectation()
    {
        Expectation exp = new Expectation(weapon);
        
        Map<Skill, Integer> newSkills = getSkillMap();

        
        newSkills.keySet()
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .forEach(skill -> skill.evalExpectation(exp, newSkills.get(skill)));
        
        return exp.getExpectation();
    }
    
    @Override
    public int compareTo(Equipment o) {
        return score - o.getScore();
    }

    public int getScore() {
        return score;
    }
    
            
    public static void main(String[] args)
    {

        Armor helm = new Armor("Helm", 1, 2, 1);
        helm.addSkill(attackBoost, 2);
        helm.addSkill(peakPerformance, 1);
        Armor helm1 = new Armor("Helm", 0, 0, 0);
        helm1.addSkill(criticalEye, 3);
        helm1.addSkill(criticalBoost, 1);
        Armor helm2 = new Armor("Helm", 0, 0, 0);
        helm2.addSkill(peakPerformance, 1);
        helm2.addSkill(criticalBoost, 1);
        helm2.addSkill(attackBoost, 1);
        
        List<Armor> helms = new LinkedList();
        helms.add(helm);
        helms.add(helm1);
        helms.add(helm2);
        
        Armor chest = new Armor("Chest", 0, 2, 2);
        chest.addSkill(criticalEye, 1);
        chest.addSkill(criticalBoost, 1);
        Armor chest1 = new Armor("Chest", 0, 0, 0);
        chest1.addSkill(criticalEye, 2);
        Armor chest2 = new Armor("Chest", 0, 0, 0);
        chest2.addSkill(attackBoost, 1);
        chest2.addSkill(criticalEye, 1);
        
        List<Armor> chests = new LinkedList();
        chests.add(chest);
        chests.add(chest1);
        chests.add(chest2);
        
        Weapon weapon = new Weapon("Sord", 200, 0);
        peakPerformance.setActive(false);
        Equipment equipment = new Equipment(weapon, helm, chest);
        System.out.println("Skill Map" + equipment.getSkillMap());
        System.out.println(equipment.getExpectation());
        equipment.updateBestDecoration(attackBoost, peakPerformance, criticalBoost, criticalEye);
        System.out.println(equipment.getExpectation());
        System.out.println("Skill Map" + equipment.getSkillMap());
    }

    
}
