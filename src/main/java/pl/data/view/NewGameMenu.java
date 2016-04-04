package pl.data.view;

import java.awt.*;
import com.google.common.primitives.Ints;
import com.intellij.uiDesigner.core.*;
import pl.data.model.classes.*;
import pl.data.model.enums.AntState;
import pl.data.model.enums.BinaryState;
import pl.data.model.enums.QuadState;
import pl.data.model.enums.WireElectronState;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;
import pl.data.model.interfaces.CellStateFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by talik on 12.12.15.
 */
public class NewGameMenu extends JFrame implements ActionListener{
    private Automaton game;

    public NewGameMenu() {
        //gameOfLifeRadioButton = new JRadioButton();
        initComponents();
        gameOfLifeRadioButton.addActionListener(this);
        quadLifeRadioButton.addActionListener(this);
        wireWorldRadioButton.addActionListener(this);
        langtonAntRadioButton.addActionListener(this);
        oneDimensionRadioButton.addActionListener(this);
        slider.setMinorTickSpacing(6);
        slider.setMajorTickSpacing(10);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        //slider.setLabelTable(slider.createStandardLabels(40));
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                sliderValue.setText(Integer.toString(slider.getValue()));
            }
        });
    /**
     * sprawdzam który Automat jest wybrany i opcje,
     * jeśli losowo to tworze Mape z losowymi stanami,
     * */
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                    int sliderVal = slider.getValue();

                    int size = 600/sliderVal;
                    if(gameOfLifeRadioButton.isSelected())
                    {
                        CellStateFactory stateFactory;
                        MoorNieghborhood neighborStrategy = new MoorNieghborhood(size,size,wrapBox.isSelected());
                        if(randomCheckBox.isSelected())
                        {
                            Random generator = new Random();
                            Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

                            for(int i=0; i<size;i++)
                            {
                                for(int j=0; j<size;j++){
                                    tab.put(new Coords2D(i,j),BinaryState.values()[generator.nextInt(2)]);
                                }
                            }
                            stateFactory = new GeneralStateFactory(tab);
                        }
                        else
                            stateFactory = new UniformStateFactory(BinaryState.DEAD);

                        Integer ruleAlive = Ints.tryParse(ruleField.getText()) ;
                        Integer ruleDead = Ints.tryParse(ruleField1.getText());
                        if((ruleAlive == null) || (ruleDead == null))
                        {
                            game = new GameOfLife(stateFactory,neighborStrategy,size,size);
                        }
                        else
                        {
                            ArrayList<Integer> aliveList = new ArrayList<Integer>();
                            while(ruleAlive!=0)
                            {
                                aliveList.add(ruleAlive%10);
                                ruleAlive=ruleAlive/10;
                            }
                            int[] alive = new int[aliveList.size()];

                            for(int i=0;i<aliveList.size();i++){
                                alive[i] = aliveList.get(i);
                            }
                            ArrayList<Integer> deadList = new ArrayList<Integer>();
                            while(ruleDead!=0)
                            {
                                deadList.add(ruleDead%10);
                                ruleDead=ruleDead/10;
                            }
                            int[] dead = new int[deadList.size()];
                            for(int i=0;i<deadList.size();i++){
                                dead[i] = deadList.get(i);
                            }
                            game = new GameOfLife(stateFactory,neighborStrategy,size,size,alive,dead);
                        }

                    }
                    else if(quadLifeRadioButton.isSelected())
                    {
                        MoorNieghborhood neighborStrategy = new MoorNieghborhood(size,size,wrapBox.isSelected());
                        if(!randomCheckBox.isSelected())
                        {
                            game = new QuadLIfe(new UniformStateFactory(QuadState.DEAD),neighborStrategy,size,size);
                        }
                        else
                        {
                            Random generator = new Random();
                            Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();

                            for(int i=0; i<size;i++)
                            {
                                for(int j=0; j<size;j++){
                                    tab.put(new Coords2D(i,j),QuadState.values()[generator.nextInt(5)]);
                                }
                            }
                            GeneralStateFactory stateFactory = new GeneralStateFactory(tab);
                            game = new QuadLIfe(stateFactory,neighborStrategy,size,size);
                        }

                    }
                    else if(wireWorldRadioButton.isSelected()){
                        game = new WireWorld(new UniformStateFactory(WireElectronState.VOID),wrapBox.isSelected(),size,size);


                    }
                    else if(langtonAntRadioButton.isSelected())
                    {
                        game = new LangtonAnt(new UniformStateFactory(new LangtonCell(BinaryState.DEAD,0, AntState.NONE)),wrapBox.isSelected(),size,size);
                    }
                    else if(oneDimensionRadioButton.isSelected())
                    {
                        Integer rule = Ints.tryParse(ruleField.getText());
                        if((rule == null) || (rule>255))
                        {
                            rule = 30;
                        }
                        game = new OneDimension(new UniformStateFactory(BinaryState.DEAD),2*size,rule);
                        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
                        tab.put(new Coords1D(size),BinaryState.ALIVE);
                        game.insertStructure(tab);
                    }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("NewGameMenu");
        frame.setContentPane(new NewGameMenu().rootpanel);
        frame.setDefaultCloseOperation(0);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    /**
     * ustawiam odpowiednią widoczność dla różnych Automatów
     * np. nie ma przycisku Random w WireWorld i LangtonAnt
     *
     * */
    public void actionPerformed(ActionEvent actionEvent) {
        if(quadLifeRadioButton.isSelected())
        {
            rulePanel.setVisible(false);
            ruleField.setText("");
            ruleField1.setVisible(false);
            wrapBox.setVisible(true);
            randomCheckBox.setVisible(true);
        }
        if(gameOfLifeRadioButton.isSelected())
        {
            rulePanel.setVisible(true);
            randomCheckBox.setVisible(true);
            ruleField1.setVisible(true);
            ruleField.setText("");
            wrapBox.setVisible(true);
        }
        if(oneDimensionRadioButton.isSelected())
        {
            rulePanel.setVisible(true);
            //randomPanel.setVisible(false);
            randomCheckBox.setSelected(false);
            randomCheckBox.setSelected(false);
            ruleField1.setVisible(false);
            ruleField.setText("");
            wrapBox.setVisible(false);
            wrapBox.setSelected(false);
        }
        if(langtonAntRadioButton.isSelected() || wireWorldRadioButton.isSelected())
        {
            rulePanel.setVisible(false);
            randomCheckBox.setVisible(false);
            randomCheckBox.setSelected(false);
            ruleField.setText("");
            ruleField1.setVisible(false);
            wrapBox.setVisible(true);
        }

    }

    public JPanel getRootpanel(){
        return rootpanel;
    }

    public Automaton getGame(){
        return this.game;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        rootpanel = new JPanel();
        wordlChoicer = new JPanel();
        JPanel panel1 = new JPanel();
        gameOfLifeRadioButton = new JRadioButton();
        oneDimensionRadioButton = new JRadioButton();
        wireWorldRadioButton = new JRadioButton();
        langtonAntRadioButton = new JRadioButton();
        quadLifeRadioButton = new JRadioButton();
        additionOptionsPanel = new JPanel();
        sizePanel = new JPanel();
        JLabel label1 = new JLabel();
        Spacer vSpacer1 = new Spacer();
        slider = new JSlider();
        sliderValue = new JLabel();
        rulePanel = new JPanel();
        JLabel label2 = new JLabel();
        ruleField = new JTextField();
        ruleField1 = new JTextField();
        randomPanel = new JPanel();
        randomCheckBox = new JCheckBox();
        wrapBox = new JCheckBox();
        Spacer vSpacer2 = new Spacer();
        Spacer vSpacer3 = new Spacer();
        JPanel panel2 = new JPanel();
        buttonPanel = new JPanel();
        cancelButton = new JButton();
        confirmButton = new JButton();
        Spacer hSpacer1 = new Spacer();
        Spacer hSpacer2 = new Spacer();
        Spacer hSpacer3 = new Spacer();

        //======== rootpanel ========
        {



            rootpanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));

            //======== wordlChoicer ========
            {
                wordlChoicer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

                //======== panel1 ========
                {
                    panel1.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));

                    //---- gameOfLifeRadioButton ----
                    gameOfLifeRadioButton.setSelected(true);
                    gameOfLifeRadioButton.setText("GameOfLife");
                    panel1.add(gameOfLifeRadioButton, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                    //---- oneDimensionRadioButton ----
                    oneDimensionRadioButton.setSelected(false);
                    oneDimensionRadioButton.setText("OneDimension");
                    panel1.add(oneDimensionRadioButton, new GridConstraints(4, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                    //---- wireWorldRadioButton ----
                    wireWorldRadioButton.setText("WireWorld");
                    panel1.add(wireWorldRadioButton, new GridConstraints(2, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                    //---- langtonAntRadioButton ----
                    langtonAntRadioButton.setText("LangtonAnt");
                    panel1.add(langtonAntRadioButton, new GridConstraints(3, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                    //---- quadLifeRadioButton ----
                    quadLifeRadioButton.setText("QuadLife");
                    panel1.add(quadLifeRadioButton, new GridConstraints(1, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                }
                wordlChoicer.add(panel1, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
            }
            rootpanel.add(wordlChoicer, new GridConstraints(0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== additionOptionsPanel ========
            {
                additionOptionsPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));

                //======== sizePanel ========
                {
                    sizePanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));

                    //---- label1 ----
                    label1.setText("Rozmiar kom\u00f3rki");
                    sizePanel.add(label1, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                    sizePanel.add(vSpacer1, new GridConstraints(2, 0, 1, 2,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        null, null, null));

                    //---- slider ----
                    slider.setMaximum(60);
                    slider.setMinimum(3);
                    slider.setValue(3);
                    sizePanel.add(slider, new GridConstraints(1, 0, 1, 2,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                    //---- sliderValue ----
                    sliderValue.setText("");
                    sizePanel.add(sliderValue, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                }
                additionOptionsPanel.add(sizePanel, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== rulePanel ========
                {
                    rulePanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));

                    //---- label2 ----
                    label2.setText("Rule");
                    rulePanel.add(label2, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                    rulePanel.add(ruleField, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                    rulePanel.add(ruleField1, new GridConstraints(1, 1, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                }
                additionOptionsPanel.add(rulePanel, new GridConstraints(3, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== randomPanel ========
                {
                    randomPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));

                    //---- randomCheckBox ----
                    randomCheckBox.setText("Random");
                    randomPanel.add(randomCheckBox, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                    //---- wrapBox ----
                    wrapBox.setText("Zawijanie");
                    randomPanel.add(wrapBox, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                }
                additionOptionsPanel.add(randomPanel, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
                additionOptionsPanel.add(vSpacer2, new GridConstraints(2, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    null, null, null));
                additionOptionsPanel.add(vSpacer3, new GridConstraints(4, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    null, null, null));
            }
            rootpanel.add(additionOptionsPanel, new GridConstraints(0, 1, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== panel2 ========
            {
                panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));

                //======== buttonPanel ========
                {
                    buttonPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));

                    //---- cancelButton ----
                    cancelButton.setText("Cancel");
                    buttonPanel.add(cancelButton, new GridConstraints(0, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                    //---- confirmButton ----
                    confirmButton.setText("Confirm");
                    buttonPanel.add(confirmButton, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                    buttonPanel.add(hSpacer1, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK,
                        null, null, null));
                }
                panel2.add(buttonPanel, new GridConstraints(0, 1, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
                panel2.add(hSpacer2, new GridConstraints(0, 2, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    null, null, null));
                panel2.add(hSpacer3, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    null, null, null));
            }
            rootpanel.add(panel2, new GridConstraints(1, 0, 1, 2,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));
        }

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(gameOfLifeRadioButton);
        buttonGroup1.add(oneDimensionRadioButton);
        buttonGroup1.add(wireWorldRadioButton);
        buttonGroup1.add(langtonAntRadioButton);
        buttonGroup1.add(quadLifeRadioButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel rootpanel;
    private JPanel wordlChoicer;
    private JRadioButton gameOfLifeRadioButton;
    private JRadioButton oneDimensionRadioButton;
    private JRadioButton wireWorldRadioButton;
    private JRadioButton langtonAntRadioButton;
    private JRadioButton quadLifeRadioButton;
    private JPanel additionOptionsPanel;
    private JPanel sizePanel;
    private JSlider slider;
    private JLabel sliderValue;
    private JPanel rulePanel;
    private JTextField ruleField;
    private JTextField ruleField1;
    private JPanel randomPanel;
    private JCheckBox randomCheckBox;
    private JCheckBox wrapBox;
    private JPanel buttonPanel;
    public JButton cancelButton;
    public JButton confirmButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
