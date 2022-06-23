/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import skillsmulator.Armor.Leg;
import skillsmulator.Armor.Armor;
import skillsmulator.Armor.Charm;
import skillsmulator.Armor.Helm;
import skillsmulator.Armor.Waist;
import skillsmulator.Armor.Arm;
import skillsmulator.Armor.Chest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import skillsmulator.Skill.AffinityMultiplierSkill;
import skillsmulator.Skill.AffinitySkill;
import skillsmulator.Skill.DamageAffinityUpSkill;
import skillsmulator.Skill.DamageUpMultiplePreSkill;
import skillsmulator.Skill.DamageUpSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Simulator {
    
    List<Helm>helms;
    List<Chest>chests;
    List<Arm>arms;
    List<Waist>waists;
    List<Leg>legs;
    List<Charm>charms;
    
    List<Helm>selectedHelms;
    List<Chest>selectedChests;
    List<Arm>selectedArms;
    List<Waist>selectedWaists;
    List<Leg>selectedLegs;
    List<Charm>selectedCharms;
    
    Set<Skill>skills;
    Set<Skill>activeSkills;
    
    List<Equipment> equipments;
    
    public static final Skill attackBoost = 
            new DamageUpMultiplePreSkill("AttackBoost", 2, new int[]{3, 6, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
    public static final Skill peakPerformance = 
            new DamageUpSkill("PeakPerformance", 2, new int[]{5, 10, 20});
    public static final Skill criticalEye = 
            new AffinitySkill("CriticalEye", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
    public static final Skill criticalBoost = 
            new AffinityMultiplierSkill("CriticalBoost", 2, new double[]{1.3, 1.35, 1.4});
    public static final Skill weaknessExploit = 
            new AffinitySkill("WeaknessExploit", 2, new int[]{15, 30, 50});
    public static final Skill criticalDraw = 
            new AffinitySkill("CriticalDraw", 3, new int[]{10, 20, 40});
    public static final Skill maximumMight = 
            new AffinitySkill("MaximumMight", 2, new int[]{10, 20, 30});
    public static final Skill agitator = 
            new DamageAffinityUpSkill("Agitator", 2, new int[]{4, 8, 12, 16, 20}, new int[]{3, 5, 7, 10, 15});
    public static final Skill counterstrike = 
            new DamageUpSkill("Counterstrike", 2, new int[]{10, 15, 25});
    public static final Skill punishingDraw = 
            new DamageUpSkill("Counterstrike", 2, new int[]{3, 5, 7});
        
    public Simulator()
    {
        helms = new ArrayList<>();
        chests = new ArrayList<>();
        arms = new ArrayList<>();
        waists = new ArrayList<>();
        legs = new ArrayList<>();
        charms = new ArrayList<>();

        selectedHelms = new ArrayList<>();
        selectedChests = new ArrayList<>();
        selectedArms = new ArrayList<>();
        selectedWaists = new ArrayList<>();
        selectedLegs = new ArrayList<>();
        selectedCharms = new ArrayList<>();

        skills = new HashSet();
        activeSkills = new HashSet();
        
        equipments = new ArrayList<>();
                
        skills.add(attackBoost);
        skills.add(peakPerformance);
        skills.add(criticalEye);
        skills.add(criticalBoost);
        skills.add(weaknessExploit);
        skills.add(criticalDraw);
        skills.add(maximumMight);
        skills.add(agitator);
        skills.add(counterstrike);
        skills.add(punishingDraw);
        
        for(Skill skill: skills)
        {
            activeSkills.add(skill);
            skill.setActive(true);
        }
        DEFAULT_ACTIVE_SKILLS();
    }
    
    public void activateSkill(Skill skill)
    {
        activeSkills.add(skill);
        skill.setActive(true);
    }
    
    public void diactivateSkill(Skill skill)
    {
        activeSkills.remove(skill);
        skill.setActive(false);
    }
    
    private void DEFAULT_ACTIVE_SKILLS()
    {
        diactivateSkill(punishingDraw);
        diactivateSkill(counterstrike);
        diactivateSkill(criticalDraw);
    }
    
    public void addHelm(Helm helm)
    {
        helms.add(helm);
    }
    
    public void addChest(Chest chest)
    {
        chests.add(chest);
    }
    
    public void addArm(Arm arm)
    {
        arms.add(arm);
    }
    
    public void addWaist(Waist waist)
    {
        waists.add(waist);
    }
    
    public void addLeg(Leg leg)
    {
        legs.add(leg);
    }
    
    public void addCharm(Charm charm)
    {
        charms.add(charm);
    }
    
    private <T extends Armor> void  select(List<T>armors, List<T>selected)
    {
        selected.clear();
        
        boolean addFlag;
        for(T armor: armors)
        {
            addFlag = true;
            for(int i = 0; i < selected.size(); )
            {
                
                int compare = armor.isStrongerThan(selected.get(i));
                if(compare > 0)
                {
                    Armor selectedArmor = selected.remove(i);
                    System.out.println("Removing " + selectedArmor);
                }
                else if(compare < 0)
                {
                    System.out.println("Not adding " + armor);
                    addFlag = false;
                    break;
                }
                else
                    i++;
            }
            if(addFlag)
            {
                System.out.println("Adding " + armor);
                selected.add(armor);
            }
                
        }
    }
    
    private void prepare()
    {
        Weapon weapon = new Weapon("Sord", 200, 0);
        select(helms, selectedHelms);
        select(chests, selectedChests);
        select(arms, selectedArms);
        select(waists, selectedWaists);
        select(legs, selectedLegs);
        select(charms, selectedCharms);
        
        
        Collections.sort(selectedHelms, Comparator.reverseOrder());
        Collections.sort(selectedChests, Comparator.reverseOrder());
        Collections.sort(selectedArms, Comparator.reverseOrder());
        Collections.sort(selectedWaists, Comparator.reverseOrder());
        Collections.sort(selectedLegs, Comparator.reverseOrder());
        Collections.sort(selectedCharms, Comparator.reverseOrder());
        
        List<Skill> activeSkillList = activeSkills.stream().toList();
        for(int i = 0; i < selectedHelms.size(); i++)
        {
            for(int j = 0; j < selectedChests.size(); j++)
            {
                for(int k = 0; k < selectedArms.size(); k++)
                {
                    for(int x = 0; x < selectedWaists.size(); x++)
                    {
                        for(int y = 0; y < selectedLegs.size(); y++)
                        {
                            for(int w = 0; w < selectedCharms.size(); w++)
                            {
                                Equipment e = new Equipment(
                                        weapon, 
                                        selectedHelms.get(i), 
                                        selectedChests.get(j),
                                        selectedArms.get(k),
                                        selectedWaists.get(x),
                                        selectedLegs.get(y),
                                        selectedCharms.get(w));
                                
//                                System.out.println(selectedHelms.get(i));
//                                System.out.println(selectedChests.get(j));
                                e.updateBestDecoration(activeSkillList);
                                equipments.add(e);
                            }
                        }
                    }
                }

                
            }
        }
        Collections.sort(equipments, Comparator.reverseOrder());
        equipments.stream().forEach(e -> System.out.println(e.getExpectation()));
        System.out.println(equipments.get(0));
        System.out.println(equipments.get(0).getExpectation());
        
        
    }
    
//    private void sortSeletced(List<Armor> selected)
//    {
//        selected.stream().sorted((o1, o2) -> {
//            return o1.getScore() - o2.getScore();
//        });
//    }
    
    public void run()
    {
        prepare();
    }

    public Set<Skill> getActiveSkills() {
        return activeSkills;
    }
    
    
    public static void main(String[] args)
    {
        Simulator simu = new Simulator();
        
        Helm helm = new Helm("Helm", 0, 1, 0);
        helm.addSkill(attackBoost, 2);
        Helm helm1 = new Helm("Helm1", 0, 2, 1);
        helm1.addSkill(criticalEye, 1);
        Helm helm2 = new Helm("Helm2", 0, 0, 2);
        helm2.addSkill(peakPerformance, 1);
        helm2.addSkill(criticalBoost, 1);
        Helm helm3 = new Helm("Helm3", 0, 5, 1);
        helm3.addSkill(maximumMight, 1);
        Helm helm4 = new Helm("Helm4", 0, 0, 3);
        helm4.addSkill(weaknessExploit, 2);
        Helm helm5 = new Helm("Helm5", 0, 1, 0);
        helm5.addSkill(agitator, 2);
        helm5.addSkill(counterstrike, 1);
        Helm helm6 = new Helm("Helm6", 0, 1, 1);
        helm6.addSkill(criticalEye, 1);
        Helm helm7 = new Helm("Helm7", 0, 0, 2);
        helm7.addSkill(peakPerformance, 2);
        helm7.addSkill(criticalBoost, 1);
        
        simu.addHelm(helm);
        simu.addHelm(helm1);
        simu.addHelm(helm2);
        simu.addHelm(helm3);
        simu.addHelm(helm4);
        simu.addHelm(helm5);
        simu.addHelm(helm6);
        simu.addHelm(helm7);
        
        Chest chest = new Chest("Chest", 0, 1, 0);
        chest.addSkill(criticalEye, 1);
        chest.addSkill(criticalBoost, 1);
        Chest chest1 = new Chest("Chest1", 0, 1, 0);
        chest1.addSkill(criticalEye, 2);
        Chest chest2 = new Chest("Chest2", 0, 0, 3);
        chest2.addSkill(attackBoost, 1);
        chest2.addSkill(criticalEye, 1);
        Chest chest3 = new Chest("Chest3", 0, 1, 0);
        chest3.addSkill(weaknessExploit, 1);
        Chest chest4 = new Chest("Chest4", 0, 0, 0);
        chest4.addSkill(attackBoost, 2);
        Chest chest5 = new Chest("Chest5", 0, 0, 0);
        chest5.addSkill(attackBoost, 1);
        Chest chest6 = new Chest("Chest5", 1, 0, 0);
        chest6.addSkill(criticalBoost, 1);
        chest6.addSkill(criticalDraw, 1);
        
        simu.addChest(chest);
        simu.addChest(chest1);
        simu.addChest(chest2);
        simu.addChest(chest3);
        simu.addChest(chest4);
        simu.addChest(chest5);
        simu.addChest(chest6);
        
        Arm arm = new Arm("arm", 0, 0, 0);
        arm.addSkill(attackBoost, 2);
        Waist waist = new Waist("waist", 0, 2, 0);
        waist.addSkill(criticalEye, 1);
        Leg leg = new Leg("leg", 0, 0, 1);
        leg.addSkill(peakPerformance, 1);
        Charm charm = new Charm("charm", 0, 1, 2);
        charm.addSkill(criticalEye, 1);
        
        simu.addArm(arm);
        simu.addWaist(waist);
        simu.addLeg(leg);
        simu.addCharm(charm);
        
        
        simu.run();
        
        System.out.println("Simu finished");
    }
}
