/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package skillsmulator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import skillsmulator.Armor.Arm;
import skillsmulator.Armor.Charm;
import skillsmulator.Armor.Chest;
import skillsmulator.Armor.Helm;
import skillsmulator.Armor.Leg;
import skillsmulator.Armor.Waist;
import static skillsmulator.Simulator.agitator;
import static skillsmulator.Simulator.attackBoost;
import static skillsmulator.Simulator.counterstrike;
import static skillsmulator.Simulator.criticalBoost;
import static skillsmulator.Simulator.criticalDraw;
import static skillsmulator.Simulator.criticalEye;
import static skillsmulator.Simulator.heroics;
import static skillsmulator.Simulator.latentPower;
import static skillsmulator.Simulator.maximumMight;
import static skillsmulator.Simulator.offensiveGuard;
import static skillsmulator.Simulator.peakPerformance;
import static skillsmulator.Simulator.punishingDraw;
import static skillsmulator.Simulator.resentment;
import static skillsmulator.Simulator.resuscitate;
import static skillsmulator.Simulator.weaknessExploit;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class UImainController implements Initializable {
    List<Skill> skillList;
    
    @FXML
    private Label label;
    
    @FXML
    private ComboBox<Integer> combobox;
    private ObjectProperty<Integer> selectedData;

    @FXML
    private LineChart chart;
    
    
    
    @FXML 
    private Button button;
    
    @FXML
    private CheckBox AttackBoostBox;
    @FXML
    private CheckBox PeakPerformanceBox;
    //死に活
    @FXML
    private CheckBox ResuscitateBox;
    @FXML
    private CheckBox CounterstrikeBox;
    @FXML
    private CheckBox ResentmentBox;
    @FXML
    private CheckBox OffensiveGuardBox;
    @FXML
    private CheckBox HeroicsBox;
    @FXML
    private CheckBox AgitatorBox;
    @FXML
    private CheckBox PunishingDrawBox;
    @FXML
    private CheckBox CriticalDrawBox;
    @FXML
    private CheckBox CriticalBoostBox;
    @FXML
    private CheckBox CriticalEyeBox;
    @FXML
    private CheckBox WeaknessExploitBox;
    @FXML
    private CheckBox MaximumMightBox;
    @FXML
    private CheckBox LatentPowerBox;

    
    
    private ObservableList<Equipment> equipmentData;
    @FXML 
    private TableView equipmentTable;
    @FXML 
    private TableColumn<Equipment, Double> attackCol;
    @FXML 
    private TableColumn helmCol;
    @FXML 
    private TableColumn chestCol;
    @FXML 
    private TableColumn armCol;
    @FXML 
    private TableColumn waistCol;
    @FXML 
    private TableColumn legCol;
    @FXML 
    private TableColumn charmCol;
    
    
    private ObservableList<SkillItem> skillData;
    @FXML 
    private TableView skillTable;
    @FXML 
    private TableColumn skillNameCol;
    @FXML 
    private TableColumn skillLevelCol;
    
    
    private ObservableList<SkillItem> decorationData;
    @FXML 
    private TableView decorationlTable;
    @FXML 
    private TableColumn decorationNameCol;
    @FXML 
    private TableColumn decorationLevelCol;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println(attackBoost.getRequired());
        updateBestDecorations();
        System.out.println("Updated");
    }
    
    @FXML
    private void updateBestDecorations() {
        List<Skill> activeSkills = skillList
                    .stream()
                    .filter(AttackSkill.class::isInstance)
                    .filter(Skill::isActive)
                    .toList();
        for(Equipment e: equipmentData)
        {
            e.updateBestDecoration(activeSkills);
        }
   }
    
    @FXML
    private void updateSkillList(Equipment e) {
        Map<Skill, Integer> skills = e.getSkillMap();
        Map<Skill, Integer> decorations = e.getBestDecorationMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != 0)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        
        skillData.clear();
        decorationData.clear();
        
        System.out.println("Entred");
        for(Entry<Skill, Integer> entry : skills.entrySet())
        {
            skillData.add(new SkillItem(entry.getKey(), entry.getValue()));
            
        }
        
        for(Entry<Skill, Integer> entry : decorations.entrySet())
        {
            decorationData.add(new SkillItem(entry.getKey(), entry.getValue()));
        }
         
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        button.setOnAction(this::onClick);
        skillList = Simulator.getAllSkills();
        equipmentData = FXCollections.observableArrayList();

        equipmentTable.itemsProperty().setValue(equipmentData);
        equipmentTable.setItems(equipmentData);
        equipmentTable.setRowFactory(tv -> {
            TableRow<Equipment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()) {
                    Equipment rowData = row.getItem();
                    updateSkillList(rowData);
                }
            });
            return row ;
        });
    
        attackCol.setCellValueFactory(e -> e.getValue().getBestExDoubleProperty().asObject());
        helmCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("helm"));
        chestCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("chest"));
        armCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("arm"));
        waistCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("waist"));
        legCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("leg"));
        charmCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("charm"));
        
        // Skill table
        skillData = FXCollections.observableArrayList();
        skillTable.itemsProperty().setValue(skillData);
        skillTable.setItems(skillData);
        skillNameCol.setCellValueFactory(new PropertyValueFactory<SkillItem, String>("name"));
        skillLevelCol.setCellValueFactory(new PropertyValueFactory<SkillItem, Integer>("level"));
        
        // Decoration Data
        decorationData = FXCollections.observableArrayList();
        decorationlTable.itemsProperty().setValue(decorationData);
        decorationlTable.setItems(decorationData);
        decorationNameCol.setCellValueFactory(new PropertyValueFactory<SkillItem, String>("decoration"));
        decorationLevelCol.setCellValueFactory(new PropertyValueFactory<SkillItem, Integer>("level"));
        
        combobox.getItems().add(0);
        combobox.getItems().add(1);
        combobox.getItems().add(2);
        combobox.getItems().add(3);
        combobox.setValue(0);
        
//        combobox.setConverter();

        selectedData = new SimpleObjectProperty<>();
        Simulator.masterTouch.requiredProperty().bind(combobox.getSelectionModel().selectedItemProperty());
//        lblCmbBoxSelected.textProperty().bind(selectedData.asString());

        combobox.getSelectionModel().selectedItemProperty().addListener((r,o,newValue) -> {
//            if (newValue == null){
//                lblCmbBox.textProperty().set("選択なし");
//            } else {
//                lblCmbBox.textProperty().set(newValue.getTitle());
//            }
        });
    
//        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        

        chart.getData().add(series);
        
        
        AttackBoostBox.selectedProperty().bindBidirectional(attackBoost.activeProperty());
        AgitatorBox.selectedProperty().bindBidirectional(agitator.activeProperty());
        CounterstrikeBox.selectedProperty().bindBidirectional(counterstrike.activeProperty());
        CriticalBoostBox.selectedProperty().bindBidirectional(criticalBoost.activeProperty());
        CriticalDrawBox.selectedProperty().bindBidirectional(criticalDraw.activeProperty());
        CriticalEyeBox.selectedProperty().bindBidirectional(criticalEye.activeProperty());
        HeroicsBox.selectedProperty().bindBidirectional(heroics.activeProperty());
        LatentPowerBox.selectedProperty().bindBidirectional(latentPower.activeProperty());
        MaximumMightBox.selectedProperty().bindBidirectional(maximumMight.activeProperty());
        OffensiveGuardBox.selectedProperty().bindBidirectional(offensiveGuard.activeProperty());
        PeakPerformanceBox.selectedProperty().bindBidirectional(peakPerformance.activeProperty());
        PunishingDrawBox.selectedProperty().bindBidirectional(punishingDraw.activeProperty());
        ResentmentBox.selectedProperty().bindBidirectional(resentment.activeProperty());
        ResuscitateBox.selectedProperty().bindBidirectional(resuscitate.activeProperty());
        WeaknessExploitBox.selectedProperty().bindBidirectional(weaknessExploit.activeProperty());
        
        AgitatorBox.setSelected(true);
        AttackBoostBox.setSelected(true);
        CounterstrikeBox.setSelected(false);
        CriticalBoostBox.setSelected(true);
        CriticalDrawBox.setSelected(false);
        CriticalEyeBox.setSelected(true);
        HeroicsBox.setSelected(false);
        LatentPowerBox.setSelected(false);
        MaximumMightBox.setSelected(true);
        OffensiveGuardBox.setSelected(false);
        PeakPerformanceBox.setSelected(true);
        PunishingDrawBox.setSelected(false);
        ResentmentBox.setSelected(false);
        ResuscitateBox.setSelected(false);
        WeaknessExploitBox.setSelected(true);
  
        
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
        Weapon weapon1 = new Weapon("Sord", 200, 0);
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
        
//        Equipment equipment = new Equipment(weapon, helm3, chest, arm, waist, leg, charm);
//        equipmentData.add(equipment);
//        Equipment equipment1 = new Equipment(weapon1, helm3, chest, arm, waist, leg, charm);
//        equipmentData.add(equipment1);
        
        Simulator sim = new Simulator(equipmentData);
        sim.addHelm(helm3);
        sim.addChest(chest);
        sim.addArm(arm);
        sim.addWaist(waist);
        sim.addLeg(leg);
        sim.addCharm(charm);
        
        sim.run();
        
        
    
        
    }    
}
