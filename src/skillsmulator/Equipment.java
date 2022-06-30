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
import javafx.beans.property.SimpleDoubleProperty;
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
import static skillsmulator.Simulator.criticalDraw;
import static skillsmulator.Simulator.criticalEye;
import static skillsmulator.Simulator.maximumMight;
import static skillsmulator.Simulator.offensiveGuard;
import static skillsmulator.Simulator.peakPerformance;
import static skillsmulator.Simulator.weaknessExploit;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Equipment implements Comparable<Equipment>{
    private final Weapon weapon;
    private final Helm helm;
    private final Chest chest;
    private final Arm arm;
    private final Waist waist;
    private final Leg leg;
    private final Charm charm;
    private final List<Armor> armorList;
    private final List<Decoratable> decoratables;
    
    private int score;

    private final Map<Skill, Integer> skills;
    private Map<Skill, Integer> requiredSkills;
    
    private Map<Skill, Integer> decorations;
    private final Map<Skill, Integer> bestDecorations;
//    private double bestExpectation;
    private final SimpleDoubleProperty bestExpectation;
    boolean calculated;
    
    public Equipment(Weapon weapon, Helm helm, Chest chest, Arm arm, Waist waist, Leg leg, Charm charm){
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
        bestExpectation = new SimpleDoubleProperty(0);
        updateScore();
    }

    public String getHelm() {
        return helm.getName();
    }

    public void setHelm(Helm helm) {
//        this.helm = helm;
    }

    public String getChest() {
        return chest.getName();
    }

    public void setChest(Chest chest) {
//        this.chest = chest;
    }

    public String getArm() {
        return arm.getName();
    }

    public void setArm(Armor arm) {
//        this.arm = arm;
    }

    public String getWaist() {
        return waist.getName();
    }

    public void setWaist(Armor waist) {
//        this.waist = waist;
    }

    public String getLeg() {
        return leg.getName();
    }

    public void setLeg(Armor leg) {
//        this.leg = leg;
    }

    public String getCharm() {
        return charm.getName();
    }

    public void setCharm(Armor charm) {
//        this.charm = charm;
    }
   
    public SimpleDoubleProperty getBestExDoubleProperty()
    {
        return bestExpectation;
    }
    
    public void setBestExpectation(double value)
    {
        bestExpectation.set(value);
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
    
    public Map<Skill, Integer> getBestDecorationMap() {
        return Stream
                .of(bestDecorations, requiredSkills)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
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
        return Stream.of(decorationsArranged, skills, requiredSkills)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
//        return skillsCombined;
    }
    
    public void updateScore()
    {
        score = 0;
        score = armorList.stream().map(Armor::getScore).reduce(0, Integer::sum);
    }
    
    public boolean updateBestDecoration(Skill... activeSkills)
    {
        return updateBestDecoration(Arrays.asList(activeSkills));
    }
    public boolean updateBestDecoration()
    {
        return updateBestDecoration(Simulator.ALL_ATTACK_SKILLS);
    }
    
    public boolean updateBestDecoration(List<Skill> activeSkills)
    {
        calculated = false;
        bestExpectation.set(0);
        bestDecorations.clear();
        decorations.clear();
        
        if(Simulator.ALL_SERIESE_SKILLS
                .stream()
                .anyMatch(skill -> skill.getRequired() != 0 && skills.getOrDefault(skill, 0) < skill.getRequired()))
        {
//            System.out.println("Skipping");
            return false;
        }
        
        activeSkills = activeSkills
                .stream()
                .filter(AttackSkill.class::isInstance)
                .filter(Skill::isActive)
                .toList();
        
        int availableSlots3 = decoratables.stream().map(deco -> deco.getSlot3()).reduce(0, (a, b) -> a + b);
        int availableSlots2 = decoratables.stream().map(deco -> deco.getSlot2()).reduce(0, (a, b) -> a + b);
        int availableSlots1 = decoratables.stream().map(deco -> deco.getSlot1()).reduce(0, (a, b) -> a + b);

        
        requiredSkills = Simulator.ALL_SKILLS
                .stream()
                .filter(skill -> skill.getRequired() - skills.getOrDefault(skill, 0) > 0)
                .collect(Collectors.toMap(
                        skill->skill, 
                        skill->skill.getRequired() - skills.getOrDefault(skill, 0))
                );
        
        int requiredSlot3Count = requiredSkills
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getCost() == 3)
                .map(Entry::getValue)
                .filter(x -> x > 0)
                .reduce(0, Integer::sum);
        
        int requiredSlot2Count = requiredSkills
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getCost() == 2)
                .map(Entry::getValue)
                .filter(x -> x > 0)
                .reduce(0, Integer::sum);
        
        int requiredSlot1Count = requiredSkills
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getCost() == 1)
                .map(Entry::getValue)
                .filter(x -> x > 0)
                .reduce(0, Integer::sum);
        
        if(requiredSlot3Count + requiredSlot2Count + requiredSlot1Count <= availableSlots3 + availableSlots2 + availableSlots1)
        {
            if(requiredSlot3Count + requiredSlot2Count <= availableSlots3 + availableSlots2)
            {
                if(requiredSlot3Count <= availableSlots3)
                {
                    int x_1 = availableSlots3 - requiredSlot3Count;
                    int y_1 = Math.max(availableSlots2 - requiredSlot2Count, 0);
                    int x_2 = x_1 - (requiredSlot2Count - (availableSlots2 - y_1));
                    int z_1 = Math.max(availableSlots1 - requiredSlot1Count, 0);
                    int y_2 = Math.max(y_1 - (requiredSlot1Count - (availableSlots1 - z_1)), 0);
                    int x_3 = x_2 -((requiredSlot1Count - (availableSlots1 - z_1)) - (y_1 - y_2));
                    availableSlots3 =  x_3;
                    availableSlots2 = y_2;
                    availableSlots1 = z_1;
                }
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
        
        
        Map<AttackSkill, Integer> remainingSkills = skills.keySet()
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .collect(
                        Collectors.toMap(
                                key->key, 
                                key -> key.getMax() - skills.get(key)
                        )
                );
        activeSkills
                .stream()
                .filter(skill -> !remainingSkills.containsKey(skill))
                .map(AttackSkill.class::cast)
                .forEach(skill -> remainingSkills.put(skill, skill.getMax()));
        
        requiredSkills
                .keySet()
                .stream()
                .filter(skill -> remainingSkills.containsKey(skill))
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .forEach(skill -> remainingSkills.put(skill, remainingSkills.get(skill) - requiredSkills.get(skill)));
        
        remainingSkills.values().removeIf(v -> v == 0);
        
        List<Skill> slot3Skills = remainingSkills.keySet()
                .stream()
                .filter(skill -> skill.getCost() == 3)
                .collect(Collectors.toList());
        List<Skill> slot2Skills = remainingSkills.keySet()
                .stream()
                .filter(skill -> skill.getCost() == 2)
                .collect(Collectors.toList());
        
        int remainingSlot3SkillSum = remainingSkills
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getCost() == 3)
                .map(Entry::getValue)
                .reduce(0, Integer::sum);
//        if(slot3Skills.size() == 0 && remainingSlot3SkillSum != 0)
//            System.out.println("Heere");
        int remainingSlot2SkillSum = remainingSkills
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getCost() == 2)
                .map(Entry::getValue)
                .reduce(0, Integer::sum);
        
        
        
//        if(!slot2Skills.isEmpty())
//        {
            bestDecorations.clear();
            skillLooper(
                    remainingSkills, 
                    slot3Skills, 
                    slot2Skills, 
                    3, 
                    0, 
                    availableSlots3, 
                    availableSlots2, 
                    remainingSlot3SkillSum, 
                    remainingSlot2SkillSum);
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
//        }
        return true;
    }
    
    private void setBestDecoration(Map<Skill, Integer> decorations)
    {
        bestDecorations.clear();
        bestDecorations.putAll(decorations);
    }
    
     private void skillLooper(
            Map<AttackSkill, Integer> skillSizeMap, 
            List<Skill> slot3Skills, 
            List<Skill> slot2Skills, 
            int slotSearching,
            int skillIndex, 
            int remainingSlot3Count,
            int remainingSlot2Count,
            int remainingSlot3SkillSum,
            int remainingSlot2SkillSum)
    {
        List<Skill> skillKeys;
        int remainingSlotCount;
        
        switch (slotSearching) {
            case 2:
                skillKeys = slot2Skills;
                // if available skill is zero then finish
//                if(remainingSlot2SkillSum == 0)
//                    return;
                
                remainingSlotCount = remainingSlot3Count + remainingSlot2Count;
                break;
            case 3:
                skillKeys = slot3Skills;
                // if available skill is zero then go to search next slot
                if(remainingSlot3SkillSum == 0)
                {
                    skillLooper(skillSizeMap, slot3Skills, slot2Skills, 2, 0, remainingSlot3Count, remainingSlot2Count, remainingSlot3SkillSum, remainingSlot2SkillSum);
                    return;
                }
                remainingSlotCount = remainingSlot3Count;
                
                break;
            default:
                throw new RuntimeException("Error with choosing slot");
        }
        
        Skill keySkill = skillKeys.get(skillIndex);
        int skillSize = skillSizeMap.get(keySkill);
        
        
        if(remainingSlotCount < 0)
            throw new RuntimeException("Negative slot");
        
        // Reaching the end of skill list
        if(skillIndex == skillKeys.size() - 1)
        {
            // Assign remaining slots
            if(slotSearching == 2)
            {
                decorations.put(keySkill, Math.min(remainingSlotCount, skillSize));
                double currentExp = getExpectation();

                if(currentExp > bestExpectation.get())
                {
                    bestExpectation.set(currentExp);
                    setBestDecoration(decorations);
                }
                return;
            }
            
            
        }
        
        int skillLoopStart = 0;
        
        // All of available slots are pushed to the end so apply all of remaining slot to the rest of skills
        // Zero is not allowed.
        if(slotSearching == 3)
        {
            if(remainingSlotCount > remainingSlot3SkillSum - skillSize)
            {
                if(remainingSlotCount > remainingSlot2SkillSum)
                    throw new RuntimeException("Error detected");
                int difference = remainingSlotCount - (remainingSlot3SkillSum - skillSize);
                int acceptableOverflowInSlot2 = remainingSlot2SkillSum - remainingSlot2Count;
                if(acceptableOverflowInSlot2 <= 0 )
                    skillLoopStart = difference;
                else if(difference - acceptableOverflowInSlot2 > skillSize)
                    skillLoopStart = skillSize;
                else
                    if(difference - acceptableOverflowInSlot2 > 0)
                        skillLoopStart = difference - acceptableOverflowInSlot2;
            }
        }
        else if(slotSearching == 2)
        {
            if(remainingSlotCount > remainingSlot2SkillSum - skillSize)
            {
                // Max slot assignment
                if(remainingSlotCount > remainingSlot2SkillSum)
                    skillLoopStart = skillSize;
                else{
                    int difference = remainingSlotCount - (remainingSlot2SkillSum - skillSize);
                    skillLoopStart = difference;
                }
            }
        }
        else{
            throw new RuntimeException("Error");
        }

        
        for(int i = skillLoopStart; i <= Math.min(remainingSlotCount, skillSize); i++)
        {
            decorations.put(keySkill, i);
            if(slotSearching == 3)
            {
                // End of slot 3 skill list
                // Go to slot 2
                if(skillIndex == skillKeys.size() - 1)
                    skillLooper(skillSizeMap, slot3Skills, slot2Skills, 2, 0, remainingSlot3Count - i, remainingSlot2Count, remainingSlot3SkillSum - skillSize, remainingSlot2SkillSum);
                else
                    skillLooper(skillSizeMap, slot3Skills, slot2Skills, 3, skillIndex + 1, remainingSlot3Count - i, remainingSlot2Count, remainingSlot3SkillSum - skillSize, remainingSlot2SkillSum);
            }
            else if(slotSearching == 2)
                skillLooper(skillSizeMap, slot3Skills, slot2Skills, 2, skillIndex + 1, remainingSlot3Count, remainingSlot2Count - i, remainingSlot3SkillSum, remainingSlot2SkillSum - skillSize);
        }
    }
     
    
    
    public double getExpectation()
    {
        if(calculated)
            return bestExpectation.get();
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
    public int compareTo(Equipment o) {
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
        for(Armor a : armorList)
        {
            System.out.println(a);
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
        skills.add(criticalDraw);
        skills.add(offensiveGuard);
        
        
        Weapon weapon = new Weapon("Sord", 200, 0);
        Helm helm3 = new Helm("Helm3", 0, 2, 1);
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
        
        Equipment equipment = new Equipment(weapon, helm3, chest, arm, waist, leg, charm);
        equipment.updateBestDecoration(skills);
        double exp = equipment.getExpectation();
        System.out.println(exp);
        System.out.println(equipment);
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
