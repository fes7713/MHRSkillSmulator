/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import skillsmulator.Armor.Arm;
import skillsmulator.Armor.Armor;
import skillsmulator.Armor.Charm;
import skillsmulator.Armor.Chest;
import skillsmulator.Armor.Helm;
import skillsmulator.Armor.Leg;
import skillsmulator.Armor.Waist;
import skillsmulator.Skill.AffinityMultiplierSkill;
import skillsmulator.Skill.AffinitySkill;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.DamageAffinityUpSkill;
import skillsmulator.Skill.DamageMultiplierSkill;
import skillsmulator.Skill.DamageUpMultiplePreSkill;
import skillsmulator.Skill.DamageUpSkill;
import skillsmulator.Skill.Skill;
import skillsmulator.Skill.UnknownSkill;

/**
 *
 * @author fes77
 */
public class Simulator {
    boolean active;
    List<Helm> helms;
    List<Chest> chests;
    List<Arm> arms;
    List<Waist> waists;
    List<Leg> legs;
    List<Charm> charms;
    
    List<Helm> selectedHelms;
    List<Chest> selectedChests;
    List<Arm> selectedArms;
    List<Waist> selectedWaists;
    List<Leg> selectedLegs;
    List<Charm> selectedCharms;
    
    List<Equipment> equipments;
    DoubleProperty progress;
    DoubleProperty skipRate;
    DoubleProperty skipValue;
    
    double totalExpectation;
    double totalScore;
    int chartCounter;
    
    ObservableList<Data<Integer, Double>> bestExpectationData;
    ObservableList<Data<Integer, Double>> worstExpectationData;
    ObservableList<Data<Integer, Double>> averageExpectationData;
    
    ObservableList<Data<Integer, Double>> bestScoreData;
    ObservableList<Data<Integer, Double>> worstScoreData;
    ObservableList<Data<Integer, Double>> averageScoreData;
    
    
    public static final Skill emptySkill
            = new UnknownSkill("", 10);
    public static final Skill attackBoost
            = new DamageUpMultiplePreSkill("攻撃", "攻撃珠", 2, new int[]{3, 6, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
    public static final Skill peakPerformance
            = new DamageUpSkill("フルチャージ", "無傷珠", 2, new int[]{5, 10, 20});
    public static final Skill criticalEye
            = new AffinitySkill("見切り", "達人珠", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
    public static final Skill criticalBoost
            = new AffinityMultiplierSkill("超会心", "超心珠", 2, new double[]{1.3, 1.35, 1.4});
    public static final Skill weaknessExploit
            = new AffinitySkill("弱点特効", "痛撃珠", 2, new int[]{15, 30, 50});
    public static final Skill criticalDraw
            = new AffinitySkill("抜刀術【技】", "抜刀珠", 3, new int[]{10, 20, 40});
    public static final Skill maximumMight
            = new AffinitySkill("渾身", "渾身珠", 2, new int[]{10, 20, 30});
    public static final Skill agitator
            = new DamageAffinityUpSkill("挑戦者", "挑戦珠", 2, new int[]{4, 8, 12, 16, 20}, new int[]{3, 5, 7, 10, 15});
    public static final Skill counterstrike
            = new DamageUpSkill("逆襲", "逆襲珠", 2, new int[]{10, 15, 25});
    public static final Skill punishingDraw
            = new DamageUpSkill("抜刀術【力】", "抜打珠", 2, new int[]{3, 5, 7});
    public static final Skill offensiveGuard
            = new DamageMultiplierSkill("攻めの守勢", "守勢珠", 3, new double[]{1.05, 1.1, 1.15});
    public static final Skill heroics
            = new DamageMultiplierSkill("火事場力", "底力珠", 2, new double[]{1, 1.05, 1.05, 1.1, 1.3});
    public static final Skill latentPower
            = new AffinitySkill("力の解放", "全開珠", 2, new int[]{10, 20, 30, 40, 50});
    public static final Skill resentment
            = new DamageUpSkill("逆恨み", "守勢珠", 2, new int[]{5, 10, 15, 20, 25});
    public static final Skill resuscitate
            = new DamageUpSkill("死中に活", "窮地珠", 2, new int[]{5, 10, 20});
    
    public static final Skill masterTouch
            = new Skill("達人芸", "達芸珠", 2, 3);
    
    
    public static final Skill handicraft
            = new Skill("匠", "匠珠", 3, 5);
    public static final Skill razorSharp
            = new Skill("業物", "斬鉄珠", 2, 3);
    public static final Skill speedSharping
            = new Skill("砥石使用高速化", "研磨珠", 1, 3);
    public static final Skill protectivePolish
            = new Skill("剛刃研磨", "剛刃珠", 2, 3);
    public static final Skill pieceUp
            = new Skill("貫通弾・貫通矢強化", "貫通珠", 3, 3);
    public static final Skill rapidFireUp
            = new Skill("速射強化", "速射珠", 3, 3);
    public static final Skill recoilDown
            = new Skill("反動軽減", "抑反珠", 1, 3);
    public static final Skill reloadSpeed
            = new Skill("装填速度", "早填珠", 1, 3);
    public static final Skill spareShot
            = new Skill("弾丸節約", "節弾珠", 2, 3);
    public static final Skill quickSheath
            = new Skill("納刀術", "納刀珠", 2, 3);
    public static final Skill flinchFree
            = new Skill("ひるみ軽減", "耐衝珠", 1, 3);
    public static final Skill stunResistance
            = new Skill("気絶耐性", "耐絶珠", 1, 3);
    public static final Skill freeMeal
            = new Skill("満足感", "節食珠", 1, 3);
    public static final Skill mushroomancer
            = new Skill("キノコ大好き", "茸好珠", 3, 3);
    public static final Skill wideRange
            = new Skill("広域化", "友愛珠", 2, 5);
    public static final Skill ammoUp
            = new Skill("装填拡張", "装填珠", 3, 3);
    public static final Skill slugger
            = new Skill("ＫＯ術", "ＫＯ珠", 2, 3);
    public static final Skill defenceBoost
            = new Skill("防御", "防御珠", 1, 5);
    public static final Skill criticalElement
            = new Skill("会心撃【属性】", "属会珠", 1, 5);
    public static final Skill fireAttack
            = new Skill("火属性攻撃強化", "火炎珠", 1, 5);
    public static final Skill thunderAttack
            = new Skill("雷属性攻撃強化", "雷光珠", 1, 5);
    public static final Skill waterAttack
            = new Skill("水属性攻撃強化", "流水珠", 1, 5);
    public static final Skill iceAttack
            = new Skill("氷属性攻撃強化", "氷結珠", 1, 5);
    public static final Skill dragonAttack
            = new Skill("龍属性攻撃強化", "破龍珠", 1, 5);
    public static final Skill blastAttack
            = new Skill("爆破属性強化", "爆破珠", 1, 5);
    public static final Skill paralysisAttack
            = new Skill("麻痺属性強化", "麻痺珠", 1, 5);
    
    
    
    public static final List<Skill> ALL_SKILLS = getAllSkills();
    public static final List<Skill> ALL_ATTACK_SKILLS = getAllAttackSkills();
    
    public Simulator() {
        this(new ArrayList<>());
    }
    
    public Simulator(List<Equipment> equipments) {
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
        
        this.equipments = equipments;
        progress = new SimpleDoubleProperty(0);
        skipRate = new SimpleDoubleProperty(0);
        skipValue = new SimpleDoubleProperty(0);
        
        bestExpectationData = FXCollections.observableArrayList();
        averageExpectationData = FXCollections.observableArrayList();
        worstExpectationData = FXCollections.observableArrayList();
        bestScoreData = FXCollections.observableArrayList();
        averageScoreData = FXCollections.observableArrayList();
        worstScoreData = FXCollections.observableArrayList();
        
        
//        DEFAULT_ACTIVE_SKILLS();
        setActive(false);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void activateSkill(Skill skill) {
//        activeSkills.add(skill);
        skill.setActive(true);
    }
    
    public void diactivateSkill(Skill skill) {
//        activeSkills.remove(skill);
        skill.setActive(false);
    }
    
    private void DEFAULT_ACTIVE_SKILLS() {
        diactivateSkill(punishingDraw);
        diactivateSkill(counterstrike);
        diactivateSkill(criticalDraw);
    }
    
    public void addHelm(Helm helm) {
        helms.add(helm);
    }
    
    public void addChest(Chest chest) {
        chests.add(chest);
    }
    
    public void addArm(Arm arm) {
        arms.add(arm);
    }
    
    public void addWaist(Waist waist) {
        waists.add(waist);
    }
    
    public void addLeg(Leg leg) {
        legs.add(leg);
    }
    
    public void addCharm(Charm charm) {
        charms.add(charm);
    }
    
    public void clearCharms() {
        charms.clear();
    }
    
    public List<Charm> getCharms()
    {
        return charms;
    }
    
    public DoubleProperty getProgressProperty()
    {
        return progress;
    }
    
    public DoubleProperty getSkipRateProperty()
    {
        return skipRate;
    }
    
    public DoubleProperty getSkipValueProperty()
    {
        return skipValue;
    }

    public ObservableList<Data<Integer, Double>> getBestExpectationData() {
        return bestExpectationData;
    }

    public ObservableList<Data<Integer, Double>> getWorstExpectationData() {
        return worstExpectationData;
    }

    public ObservableList<Data<Integer, Double>> getAverageExpectationData() {
        return averageExpectationData;
    }

    public ObservableList<Data<Integer, Double>> getAverageScoreData() {
        return averageScoreData;
    }
    
    
    
    private <T extends Armor> void select(List<T> armors, List<T> selected, int limit) {
        selected.clear();
        
        boolean addFlag;
        for (T armor : armors) {
            addFlag = true;
            if(armor.getScore() < limit)
            {
                addFlag = false;
                continue;
            }
            
            for (int i = 0; i < selected.size();) {
                
                int compare = armor.isStrongerThan(selected.get(i));
                if (compare > 0) {
                    Armor selectedArmor = selected.remove(i);
                    System.out.println("Removing " + selectedArmor);
                } else if (compare < 0) {
                    System.out.println("Not adding " + armor);
                    addFlag = false;
                    break;
                } else {
                    i++;
                }
            }
            if (addFlag) {
                System.out.println("Adding " + armor);
                selected.add(armor);
            }
        }
        
        if(selected.isEmpty() && !armors.isEmpty())
        {
            selected.add(armors.get(0));
        }
        
        
    }
    
    public void run(Weapon weapon, int componentScoreLimit, int combinationScoreLimit, int listSize) {
        setActive(true);
        equipments.clear();
        progress.set(0);
        skipRate.set(0);
//        skipValue.set(combinationScoreLimit);

        select(helms, selectedHelms, componentScoreLimit);
        select(chests, selectedChests, componentScoreLimit);
        select(arms, selectedArms, componentScoreLimit);
        select(waists, selectedWaists, componentScoreLimit);
        select(legs, selectedLegs, componentScoreLimit);
        select(charms, selectedCharms, componentScoreLimit);
        
        Collections.sort(selectedHelms, Comparator.reverseOrder());
        Collections.sort(selectedChests, Comparator.reverseOrder());
        Collections.sort(selectedArms, Comparator.reverseOrder());
        Collections.sort(selectedWaists, Comparator.reverseOrder());
        Collections.sort(selectedLegs, Comparator.reverseOrder());
        Collections.sort(selectedCharms, Comparator.reverseOrder());
        
        List<Skill> activeSkills = getActiveSkills();
        
        int combinations = 
                selectedHelms.size() *
                selectedChests.size() * 
                selectedArms.size() *
                selectedWaists.size() *
                selectedLegs.size() *
                selectedCharms.size();
        
        System.out.println(combinations + " Patterns");
        
        int progressCounter = 0;
        int skipCounter = 0;
        for (int i = 0; i < selectedHelms.size(); i++) {
            for (int j = 0; j < selectedChests.size(); j++) {
                for (int k = 0; k < selectedArms.size(); k++) {
                    for (int x = 0; x < selectedWaists.size(); x++) {
                        for (int y = 0; y < selectedLegs.size(); y++) {
                            for (int w = 0; w < selectedCharms.size(); w++) {
                                if(!isActive())
                                {
                                    System.out.println("Exiting");
                                    return;
                                }
                                progress.set(progressCounter++/(double)combinations);
                                Equipment e = new Equipment(
                                        weapon,
                                        selectedHelms.get(i),
                                        selectedChests.get(j),
                                        selectedArms.get(k),
                                        selectedWaists.get(x),
                                        selectedLegs.get(y),
                                        selectedCharms.get(w));

                                if(e.getScore() < skipValue.get())
                                {
                                    skipRate.set(skipCounter ++/(double)progressCounter);
                                    continue;
                                }
                                
                                if(e.updateBestDecoration()){
                                    if(equipments.size() < listSize)
                                    {
                                        pushExpectation(e);
                                        if(e.getExpectation() == 0)
                                        {
                                            System.out.println("Zero");
                                            e.updateBestDecoration();
                                        }
                                    }
                                    else{
                                        if(equipments.get(equipments.size() - 1).getExpectation() < e.getExpectation())
                                        {
                                            Equipment removed = equipments.remove(equipments.size() - 1);
//                                            totalExpectation -= removed.getExpectation();
//                                            totalScore -= removed.getScore();
                                            pushExpectation(e);
                                        }
                                    }
                                    
                                }
                            }
                        }
                    }
                }
                
            }
        }
        Collections.sort(equipments, Comparator.reverseOrder());
//        equipments.stream().forEach(e -> System.out.println(e.getExpectation()));
        for(Equipment e : equipments)
        {
            System.out.println(e.getSkillMap());
            System.out.println(e.getExpectation());
        }
        System.out.println(equipments.get(0));
        System.out.println(equipments.get(0).getExpectation());
        setActive(false);
        
    }
    
    private void pushExpectation(Equipment e)
    {
//        chartCounter++;
//        totalExpectation += e.getExpectation();
//        totalScore += e.getScore();
        equipments.add(e);
        Collections.sort(equipments, Comparator.reverseOrder());
        
//        Platform.runLater(new Runnable(){
//            @Override
//            public void run() {
//                bestExpectationData.add(
//                        new XYChart.Data<>(chartCounter, equipments.get(0).getExpectation())
//                );
//                worstExpectationData.add(
//                        new XYChart.Data<>(chartCounter, equipments.get(equipments.size() - 1).getExpectation())
//                );
//
//                averageExpectationData.add(
//                        new XYChart.Data<>(chartCounter, totalExpectation / (double)equipments.size())
//                );
//                averageScoreData.add(
//                        new XYChart.Data<>(chartCounter, totalScore / (double)equipments.size())
//                );
//            }
//        });
    }
    
    private List<Skill> getActiveSkills() {
        return ALL_SKILLS.stream().filter(Skill::isActive).toList();
    }
    
    private static List<Skill> getAllSkills() {
        return List.of(emptySkill,
                attackBoost, 
                peakPerformance, 
                criticalEye, 
                criticalBoost, 
                weaknessExploit, 
                criticalDraw, 
                maximumMight, 
                agitator, 
                counterstrike, 
                punishingDraw, 
                offensiveGuard, 
                resuscitate, 
                resentment, 
                latentPower, 
                heroics, 
                masterTouch,
                handicraft,
                razorSharp,
                speedSharping,
                protectivePolish,
                pieceUp,
                rapidFireUp,
                recoilDown,
                reloadSpeed,
                spareShot,
                quickSheath,
                flinchFree,
                stunResistance,
                freeMeal,
                mushroomancer,
                wideRange,
                ammoUp,
                slugger,
                criticalElement,
                defenceBoost,
                fireAttack,
                thunderAttack,
                waterAttack,
                iceAttack,
                dragonAttack,
                blastAttack,
                paralysisAttack);
    }
    
    public static List<Skill> getAllAttackSkills()
    {
        return getAllSkills().stream().filter(AttackSkill.class::isInstance).toList();
    }
    
    public static void main(String[] args) {
        Simulator simu = new Simulator();
        masterTouch.setRequired(0);
        
        Helm helm = new Helm("Helm", 0, 1, 0);
        helm.addSkill(attackBoost, 2);
        Helm helm1 = new Helm("Helm1", 0, 2, 1);
        helm1.addSkill(criticalEye, 1);
        Helm helm2 = new Helm("Helm2", 0, 0, 2);
        helm2.addSkill(peakPerformance, 1);
        helm2.addSkill(criticalBoost, 1);
        Helm helm3 = new Helm("Helm3", 0, 2, 1);
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
        Chest chest6 = new Chest("Chest6", 0, 1, 2);
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
        
        simu.run(new Weapon("Name", 200, 0), 3, 10, 10);
        
        System.out.println("Simu finished");
    }
}
