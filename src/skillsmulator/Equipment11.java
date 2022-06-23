/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import skillsmulator.Armor.Arm;
import skillsmulator.Armor.Armor;
import skillsmulator.Armor.Charm;
import skillsmulator.Armor.Chest;
import skillsmulator.Armor.Helm;
import skillsmulator.Armor.Leg;
import skillsmulator.Armor.Waist;
import static skillsmulator.Simulator.agitator;
import static skillsmulator.Simulator.attackBoost;
import static skillsmulator.Simulator.criticalBoost;
import static skillsmulator.Simulator.criticalEye;
import static skillsmulator.Simulator.maximumMight;
import static skillsmulator.Simulator.peakPerformance;
import static skillsmulator.Simulator.weaknessExploit;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Equipment11 implements Comparable<Equipment11>{
    private Weapon weapon;
    private Armor helm;
    private Armor chest;
    private Armor arm;
    private Armor waist;
    private Armor leg;
    private Armor charm;
    private List<Armor> armorList;
    private List<Decoratable> decoratables;
    
    private int score;

    private Map<Skill, Integer> skills;
    private Map<Skill, Integer> specifiedSkills;
    
    private Map<Skill, Integer> decorations;
    private Map<Skill, Integer> bestDecorations;
    private double bestExpectation;
    boolean calculated;
    
    public Equipment11(Weapon weapon, Helm helm, Chest chest, Arm arm, Waist waist, Leg leg, Charm charm){
        this.weapon = weapon;
        this.helm = helm;
        this.chest = chest;
        this.arm = arm;
        this.waist = waist;
        this.leg = leg;
        this.charm = charm;
         
        armorList = Stream.of(helm, chest, arm, waist, leg, charm).toList();
        decoratables = new ArrayList(armorList);
        decoratables.add(weapon);
        
        calculated = false;
        skills = findSkillMap();
        decorations = new HashMap();
        bestDecorations = new HashMap<>();
        updateScore();
    }
   
    private Map<Skill, Integer> findSkillMap()
    {
//        Map<Skill, Integer> skillMap = new HashMap();
//        skillMap.putAll(helm.getSkills());
//        
//        Map<Skill, Integer> chestSkills = chest.getSkills();
//        for(Skill skill : chestSkills.keySet())
//        {
//            if(skillMap.containsKey(skill))
//            {
//                int sum = skillMap.get(skill) + chestSkills.get(skill);
//                if(skill.getMax() > sum)
//                {
//                    skillMap.put(skill, sum);
//                }
//                else{
//                    skillMap.put(skill, skill.getMax() - 1);
//                }
//            }
//            else{
//                skillMap.put(skill, chestSkills.get(skill));
//            }
//        }
         Map<Skill, Integer> skillMap = Stream.of(helm.getSkills(), chest.getSkills(), arm.getSkills(), waist.getSkills(), leg.getSkills(), charm.getSkills())
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
        
         skillMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getMax() < entry.getValue())
                .forEach(entry-> entry.setValue(entry.getKey().getMax()));
         
        return skillMap;
    }
    
    public Map<Skill, Integer> getSkillMap()
    {
//        if(calculated)
//            return skillsCombined;
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
//        return skillsCombined;
    }
    
    public void updateScore()
    {
        score = 0;
        score = armorList.stream().map(Armor::getScore).reduce(0, Integer::sum);
    }
    
    public void updateBestDecoration(Skill... activeSkills)
    {
        updateBestDecoration(Arrays.asList(activeSkills));
    }
    
    public void updateBestDecoration(List<Skill> activeSkills)
    {
        activeSkills = activeSkills.stream().filter(Skill::isActive).toList();
        
        int availableSlots3 = decoratables.stream().map(deco -> deco.getSlot3()).reduce(0, (a, b) -> a + b);
        int availableSlots2 = decoratables.stream().map(deco -> deco.getSlot2()).reduce(0, (a, b) -> a + b);
        int availableSlots1 = decoratables.stream().map(deco -> deco.getSlot1()).reduce(0, (a, b) -> a + b);
        
//        System.out.println("avbailableSlots3 : " + availableSlots3);
//        System.out.println("avbailableSlots2 : " + availableSlots2);
//        System.out.println("avbailableSlots1 : " + availableSlots1);
        
        
        List<Skill> slot3Skills = activeSkills.stream().filter(skill -> skill.getCost() == 3).toList();
        List<Skill> slot2Skills = activeSkills.stream().filter(skill -> skill.getCost() == 2).toList();
        
//        System.out.println("slot3Skills" + slot3Skills);
//        System.out.println("slot2Skills" + slot2Skills);
        
        Map<Skill, Integer> remainingSkills = skills.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Entry::getKey, 
                                entry -> entry.getKey().getMax() - entry.getValue()
                        )
                );
        activeSkills
                .stream()
                .filter(skill -> !remainingSkills.containsKey(skill))
                .forEach(skill -> remainingSkills.put(skill, skill.getMax()));
        
        int remainingSkillSum = remainingSkills.entrySet().stream().map(Entry::getValue).reduce(0, Integer::sum);
        
        if(!slot2Skills.isEmpty())
        {
            bestExpectation = 0;
            skillLooper(remainingSkills, slot2Skills, 0, availableSlots2 + availableSlots3, remainingSkillSum);
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
            calculated = true;
        }
    }
    
    private void setBestDecoration(Map<Skill, Integer> decorations)
    {
        bestDecorations.clear();
        bestDecorations.putAll(decorations);
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
        
        boolean flag = false;
        
        
        // Reaching the end of skill list
        if(skillIndex == skillKeys.size() - 1)
        {
            // Assign remaining slots
            decorations.put(keySkill, Math.min(remainingSlotCount, skillSize));
            
            double currentExp = getExpectation();

            if(currentExp > bestExpectation)
            {
                bestExpectation = currentExp;
                setBestDecoration(decorations);
            }
            return;
        }
        
        int skillLoopStart = 0;
        
        // All of available slots are pushed to the end so apply all of remaining slot to the rest of skills
        // Zero is not allowed.
        if(remainingSlotCount > remainingSkillSum - skillSize)
        {
            //works
            if(remainingSlotCount > remainingSkillSum)
                throw new RuntimeException("Error detected");
            int difference = remainingSlotCount - (remainingSkillSum - skillSize);
            skillLoopStart = difference;
            flag = false;
        }
        if(flag)
            System.out.println("This is it");
        

        for(int i = skillLoopStart; i <= skillSize && i <= remainingSlotCount; i++)
        {
//            if(i != 0)
            if(flag)
                System.out.println("Entered");
            decorations.put(keySkill, i);
            skillLooper(skillSizeMap, skillKeys, skillIndex + 1, remainingSlotCount - i, remainingSkillSum - skillSize);
        }
    }
     
    
    
    public double getExpectation()
    {
//        if(calculated)
//            return bestExpectation;
        Expectation exp = new Expectation(weapon);
        
        Map<Skill, Integer> newSkills = getSkillMap();

        int availableSlots3 = decoratables.stream().map(deco -> deco.getSlot3()).reduce(0, (a, b) -> a + b);
        int availableSlots2 = decoratables.stream().map(deco -> deco.getSlot2()).reduce(0, (a, b) -> a + b);
        
        newSkills.keySet()
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .forEach(skill -> skill.evalExpectation(exp, newSkills.get(skill)));
        
        return exp.getExpectation();
    }
    
    @Override
    public int compareTo(Equipment11 o) {
        if(calculated)
            return (int)((getExpectation() - o.getExpectation()) * 100);
        return score - o.getScore();
    }

    public int getScore() {
        return score;
    }
    
            
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Armor]");
        for(Armor armor: armorList)
        {
            sb.append(armor.getName()).append("\t");
        }
        
        sb.append("\n[Skill]");
        Map<Skill, Integer> skillMap = getSkillMap();
        for(Skill skill: getSkillMap().keySet())
        {
            sb.append("\n\t")
                    .append(skill)
                    .append("->")
                    .append(skillMap.get(skill));
        }
                
        return sb.toString();
    }
    
    public static void main(String[] args)
    {
        List<Skill>skills = new ArrayList();

        skills.add(attackBoost);
        skills.add(peakPerformance);
        skills.add(criticalEye);
        skills.add(criticalBoost);
        skills.add(weaknessExploit);
        skills.add(maximumMight);
        skills.add(agitator);
        
        
        Weapon weapon = new Weapon("Sord", 200, 0);
        Helm helm3 = new Helm("Helm3", 0, 5, 1);
        helm3.addSkill(maximumMight, 1);

        Chest chest = new Chest("Chest", 0, 1, 0);
        chest.addSkill(criticalEye, 1);
        chest.addSkill(criticalBoost, 1);
        Arm arm = new Arm("arm", 0, 0, 0);
        arm.addSkill(attackBoost, 2);
        Waist waist = new Waist("waist", 0, 2, 0);
        waist.addSkill(criticalEye, 1);
        Leg leg = new Leg("leg", 0, 0, 1);
        leg.addSkill(peakPerformance, 1);
        Charm charm = new Charm("charm", 0, 1, 2);
        charm.addSkill(criticalEye, 1);
        
        Equipment11 equipment = new Equipment11(weapon, helm3, chest, arm, waist, leg, charm);
        equipment.updateBestDecoration(skills);
        double exp = equipment.getExpectation();
        System.out.println(exp);
    }
//    public static void main(String[] args)
//    {
//
//        Helm helm = new Helm("Helm", 1, 0, 1);
//        helm.addSkill(attackBoost, 2);
//        helm.addSkill(peakPerformance, 1);
//        Arm arm = new Arm("arm", 0, 0, 0);
//        arm.addSkill(criticalEye, 3);
//        arm.addSkill(criticalBoost, 1);
//        Waist waist = new Waist("waist", 0, 0, 0);
//        waist.addSkill(peakPerformance, 1);
//        waist.addSkill(criticalBoost, 1);
//        waist.addSkill(attackBoost, 1);
//        
//        
//        Chest chest = new Chest("Chest", 0, 2, 2);
//        chest.addSkill(criticalEye, 1);
//        chest.addSkill(criticalBoost, 1);
//        Leg leg = new Leg("leg", 0, 0, 0);
//        leg.addSkill(criticalEye, 2);
//        Charm charm = new Charm("charm", 0, 0, 0);
//        charm.addSkill(attackBoost, 1);
//        charm.addSkill(criticalEye, 1);
//        
//        Weapon weapon = new Weapon("Sord", 200, 0);
////        peakPerformance.setActive(false);
//        Equipment equipment = new Equipment(weapon, helm, chest, arm, waist, leg, charm);
//        
//        System.out.println("Skill Map" + equipment.getSkillMap());
//        System.out.println(equipment.getExpectation());
//        equipment.updateBestDecoration(attackBoost, peakPerformance, criticalBoost, criticalEye);
//        System.out.println(equipment.getExpectation());
//        System.out.println("Skill Map" + equipment.getSkillMap());
//        System.out.println(equipment);
//        System.out.println(equipment.getExpectation());
//    }

    
}
