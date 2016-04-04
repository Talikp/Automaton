package pl.data.view;

import com.intellij.uiDesigner.core.*;
import pl.data.model.classes.*;
import pl.data.model.enums.BinaryState;
import pl.data.model.enums.QuadState;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

/**
 * Created by talik on 11.12.15.
 */
public class OptionPanel extends JPanel {
    private Automaton game;
    private boolean running = false;
    private GameComponent gameComponent;


    public OptionPanel() {
        //slider = new JSlider();
        //initComponents();

        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(1));

        /**
         * dodanie ActionListener do przycisku New, który otwiera okno wyboru z nowym Automatem, który zosate ustawiony po zamknięciu okna
         * jak również ustawienie odpowiedniej widoczności panelu z dodatkowymi strukturami w zależności od rodzaju Automatu
         *
         * */
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final JFrame frame = new JFrame();
                final NewGameMenu gameMenu = new NewGameMenu();
                JPanel root = gameMenu.getRootpanel();
                gameMenu.cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        frame.dispose();
                    }
                });
                gameMenu.confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        frame.dispose();
                    }
                });
                frame.setContentPane(root);
                frame.setDefaultCloseOperation(0);
                frame.setUndecorated(true);
                frame.setFocusable(true);
                frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
                frame.addWindowListener(new WindowListener() {
                    @Override
                    public void windowOpened(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowClosing(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        Automaton newGame = gameMenu.getGame();
                        if (newGame != null) {
                            game = gameMenu.getGame();
                            if (game instanceof LangtonAnt || game instanceof OneDimension) {
                                addingTabbedPanel.setVisible(false);
                            } else if (game instanceof GameOfLife) {
                                addingTabbedPanel.setVisible(true);
                                if (addingTabbedPanel.indexOfTab(gatesPanel.getName()) == 0)
                                    addingTabbedPanel.removeTabAt(0);
                                if (addingTabbedPanel.indexOfTab(gatesPanel.getName()) == 3)
                                    addingTabbedPanel.removeTabAt(3);
                                addingTabbedPanel.insertTab(spaceshipsPanel.getName(), null, spaceshipsPanel, null, 0);
                                addingTabbedPanel.insertTab(gunsPanel.getName(), null, gunsPanel, null, 1);
                                addingTabbedPanel.insertTab(pufferPanel.getName(), null, pufferPanel, null, 2);
                            } else if (game instanceof WireWorld) {
                                addingTabbedPanel.setVisible(true);
                                if (addingTabbedPanel.getTabCount() > 1) {
                                    addingTabbedPanel.removeTabAt(2);
                                    addingTabbedPanel.removeTabAt(1);
                                    addingTabbedPanel.removeTabAt(0);
                                }
                                addingTabbedPanel.addTab(gatesPanel.getName(), gatesPanel);
                            }
                            gameComponent.setGame(game);
                            gameComponent.firstPaint();
                        }
                    }

                    @Override
                    public void windowIconified(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowDeiconified(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowActivated(WindowEvent windowEvent) {

                    }

                    @Override
                    public void windowDeactivated(WindowEvent windowEvent) {

                    }
                });
                frame.pack();
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setLocation(200, 200);
            }
        });
        addingTabbedPanel.setVisible(false);
        gameComponent = new GameComponent();
        gamePanel.add(gameComponent, BorderLayout.CENTER);
        /**
         * dodanie MouseListener który dodaje structury
         */
        gameComponent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if ((game != null) && (running == false)) {
                    if (glider.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getGlider(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getGlider(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }

                    } else if (lightShip.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getLightSpaceShip(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getLightSpaceShip(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (loafer.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getLoafer(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getLoafer(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (seal.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getSeal(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getSeal(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (glidergun.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getGliderGun(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getGliderGun(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (blinker1.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getBlinker1(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getBlinker1(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (blinker2.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getBlinker2(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getBlinker2(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (puff_suppressor.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getSuppressor(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getSuppressor(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (pufferFish.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getPufferFish(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getPufferFish(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (gliderless_gun.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getGliderlessGun(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getGliderlessGun(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (bomber.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getBomber(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getBomber(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (factory_gun.isSelected()) {
                        if (game instanceof QuadLIfe) {
                            gameComponent.insertStructure(StructureAdd.getFactory(QuadState.DEAD, QuadState.BLUE), e.getX(), e.getY());
                        } else if (game instanceof GameOfLife) {
                            gameComponent.insertStructure(StructureAdd.getFactory(BinaryState.DEAD, BinaryState.ALIVE), e.getX(), e.getY());
                        }
                    } else if (gate_nand.isSelected()) {
                        if (game instanceof WireWorld) {
                            gameComponent.insertStructure(StructureAdd.getNAND(), e.getX(), e.getY());
                        }
                    } else if (gate_xor.isSelected()) {
                        if (game instanceof WireWorld) {
                            gameComponent.insertStructure(StructureAdd.getXor(), e.getX(), e.getY());
                        }
                    } else if (gate_counter.isSelected()) {
                        if (game instanceof WireWorld) {
                            gameComponent.insertStructure(StructureAdd.getCounter(), e.getX(), e.getY());
                        }
                    } else if (gate_not.isSelected()) {
                        if (game instanceof WireWorld) {
                            gameComponent.insertStructure(StructureAdd.getNot(), e.getX(), e.getY());
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        Enumeration<AbstractButton> buttons = buttonGroup1.getElements();

        while (buttons.hasMoreElements()) {
            AbstractButton abstractButton = buttons.nextElement();
            abstractButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    running = false;
                    startButton.setText("Start");
                    if(abstractButton.isSelected()){
                        abstractButton.setSelected(!abstractButton.isSelected());
                    }

                }
            });
        }


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!running) {
                    startButton.setText("Pause");
                    running = true;
                    buttonGroup1.clearSelection();
                    runGameLoop();
                } else {
                    startButton.setText("Start");
                    running = false;
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (game != null) {
                    gameComponent.setGame(game);
                    gameComponent.firstPaint();
                }
            }
        });


    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("OptionPanel");
        frame.setContentPane(new OptionPanel().getRootpanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setMinimumSize(new Dimension(600, 700));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    /**
     * stworzenie wątku z pętlą
     */
    public void runGameLoop() {
        Thread loop = new Thread() {
            public void run() {
                gameLoop();
            }
        };
        loop.start();
    }

    /**
     * pętla uruchomionego Automatu
     */
    private void gameLoop() {

        final double GAME_HERTZ = 30;
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;

        double lastUpdateTime = System.nanoTime();

        double lastRenderTime = System.nanoTime();

        final double TARGET_FPS = 30;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (running) {
            double now = System.nanoTime();

            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }


            if (game instanceof Automaton2Dim)
                gameComponent.paintR();
            lastRenderTime = now;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                lastSecondTime = thisSecond;
            }

            while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                Thread.yield();
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                now = System.nanoTime();
            }

        }
    }


    public JPanel getRootpanel() {
        return this.rootpanel;
    }

    public Automaton getGame() {
        return this.game;
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        rootpanel = new JPanel();
        gamePanel = new JPanel();
        option = new JPanel();
        speedPanel = new JPanel();
        slider = new JSlider();
        JLabel label1 = new JLabel();
        structurePanel = new JPanel();
        addingTabbedPanel = new JTabbedPane();
        spaceshipsPanel = new JPanel();
        JPanel panel1 = new JPanel();
        glider = new JToggleButton();
        lightShip = new JToggleButton();
        loafer = new JToggleButton();
        seal = new JToggleButton();
        gunsPanel = new JPanel();
        glidergun = new JToggleButton();
        gliderless_gun = new JToggleButton();
        bomber = new JToggleButton();
        factory_gun = new JToggleButton();
        pufferPanel = new JPanel();
        JPanel panel2 = new JPanel();
        blinker1 = new JToggleButton();
        puff_suppressor = new JToggleButton();
        pufferFish = new JToggleButton();
        blinker2 = new JToggleButton();
        gatesPanel = new JPanel();
        gate_nand = new JToggleButton();
        gate_counter = new JToggleButton();
        gate_not = new JToggleButton();
        gate_xor = new JToggleButton();
        newPanel = new JPanel();
        newButton = new JButton();
        buttonPanel = new JPanel();
        resetButton = new JButton();
        startButton = new JButton();
        JPanel panel3 = new JPanel();

        //======== rootpanel ========
        {

            // JFormDesigner evaluation mark
            rootpanel.setBorder(new CompoundBorder(
                    new TitledBorder(new EmptyBorder(0, 0, 0, 0),
                            "JFormDesigner Evaluation", TitledBorder.CENTER,
                            TitledBorder.BOTTOM, new Font("Dialog", Font.BOLD, 12),
                            Color.red), rootpanel.getBorder()));
            rootpanel.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    if ("border".equals(e.getPropertyName())) throw new RuntimeException();
                }
            });

            rootpanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

            //======== gamePanel ========
            {
                gamePanel.setLayout(new BorderLayout());
            }
            rootpanel.add(gamePanel, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    null, new Dimension(600, 600), null));

            //======== option ========
            {
                option.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));

                //======== speedPanel ========
                {
                    speedPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

                    //---- slider ----
                    slider.setMaximum(10);
                    slider.setMinimum(1);
                    slider.setName("Speed");
                    slider.setPaintLabels(true);
                    slider.setPaintTicks(true);
                    slider.setSnapToTicks(true);
                    slider.setValue(1);
                    slider.setValueIsAdjusting(true);
                    speedPanel.add(slider, new GridConstraints(1, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                    //---- label1 ----
                    label1.setRequestFocusEnabled(true);
                    label1.setText("Pr\u0119dko\u015b\u0107 generacji");
                    speedPanel.add(label1, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                }
                option.add(speedPanel, new GridConstraints(0, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                //======== structurePanel ========
                {
                    structurePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

                    //======== addingTabbedPanel ========
                    {
                        addingTabbedPanel.setName("");

                        //======== spaceshipsPanel ========
                        {
                            spaceshipsPanel.setName("Spaceships");
                            spaceshipsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

                            //======== panel1 ========
                            {
                                panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));

                                //---- glider ----
                                glider.setText("Glider");
                                panel1.add(glider, new GridConstraints(0, 0, 1, 1,
                                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        null, null, null));

                                //---- lightShip ----
                                lightShip.setText("Light ship");
                                panel1.add(lightShip, new GridConstraints(0, 1, 1, 1,
                                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        null, null, null));

                                //---- loafer ----
                                loafer.setText("Loafer");
                                panel1.add(loafer, new GridConstraints(1, 0, 1, 1,
                                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        null, null, null));

                                //---- seal ----
                                seal.setText("Seal");
                                panel1.add(seal, new GridConstraints(1, 1, 1, 1,
                                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        null, null, null));
                            }
                            spaceshipsPanel.add(panel1, new GridConstraints(0, 0, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));
                        }
                        addingTabbedPanel.addTab("Spaceships", spaceshipsPanel);

                        //======== gunsPanel ========
                        {
                            gunsPanel.setName("Guns");
                            gunsPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));

                            //---- glidergun ----
                            glidergun.setText("Gosper gun");
                            gunsPanel.add(glidergun, new GridConstraints(0, 0, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));

                            //---- gliderless_gun ----
                            gliderless_gun.setText("Gliderless gun");
                            gunsPanel.add(gliderless_gun, new GridConstraints(0, 1, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));

                            //---- bomber ----
                            bomber.setText("B-52 Bomber");
                            gunsPanel.add(bomber, new GridConstraints(1, 0, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));

                            //---- factory_gun ----
                            factory_gun.setText("Factory");
                            gunsPanel.add(factory_gun, new GridConstraints(1, 1, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));
                        }
                        addingTabbedPanel.addTab("Guns", gunsPanel);

                        //======== pufferPanel ========
                        {
                            pufferPanel.setName("Puffers");
                            pufferPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

                            //======== panel2 ========
                            {
                                panel2.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));

                                //---- blinker1 ----
                                blinker1.setText("Blinker1");
                                panel2.add(blinker1, new GridConstraints(0, 0, 1, 1,
                                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        null, null, null));

                                //---- puff_suppressor ----
                                puff_suppressor.setText("Puff Suppressor");
                                panel2.add(puff_suppressor, new GridConstraints(1, 0, 1, 1,
                                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        null, null, null));

                                //---- pufferFish ----
                                pufferFish.setText("Puffer fish");
                                panel2.add(pufferFish, new GridConstraints(1, 1, 1, 1,
                                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        null, null, null));

                                //---- blinker2 ----
                                blinker2.setText("Blinker2");
                                panel2.add(blinker2, new GridConstraints(0, 1, 1, 1,
                                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                        null, null, null));
                            }
                            pufferPanel.add(panel2, new GridConstraints(0, 0, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));
                        }
                        addingTabbedPanel.addTab("Puffers", pufferPanel);

                        //======== gatesPanel ========
                        {
                            gatesPanel.setName("Gates");
                            gatesPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));

                            //---- gate_nand ----
                            gate_nand.setText("NAND");
                            gatesPanel.add(gate_nand, new GridConstraints(1, 0, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));

                            //---- gate_counter ----
                            gate_counter.setText("Bin Counter");
                            gatesPanel.add(gate_counter, new GridConstraints(0, 0, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));

                            //---- gate_not ----
                            gate_not.setName("Gates");
                            gate_not.setText("NOT");
                            gatesPanel.add(gate_not, new GridConstraints(0, 1, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));

                            //---- gate_xor ----
                            gate_xor.setText("XOR");
                            gatesPanel.add(gate_xor, new GridConstraints(1, 1, 1, 1,
                                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                    null, null, null));
                        }
                        addingTabbedPanel.addTab("Gates", gatesPanel);
                    }
                    structurePanel.add(addingTabbedPanel, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            null, new Dimension(200, 100), null));
                }
                option.add(structurePanel, new GridConstraints(0, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, new Dimension(283, 113), null));

                //======== newPanel ========
                {
                    newPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

                    //---- newButton ----
                    newButton.setText("New");
                    newPanel.add(newButton, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                }
                option.add(newPanel, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                //======== buttonPanel ========
                {
                    buttonPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

                    //---- resetButton ----
                    resetButton.setHideActionText(false);
                    resetButton.setText("Reset");
                    buttonPanel.add(resetButton, new GridConstraints(1, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                    //---- startButton ----
                    startButton.setText("Start");
                    buttonPanel.add(startButton, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                }
                option.add(buttonPanel, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                //======== panel3 ========
                {
                    panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                option.add(panel3, new GridConstraints(0, 4, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
            }
            rootpanel.add(option, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
        }

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(glider);
        buttonGroup1.add(lightShip);
        buttonGroup1.add(loafer);
        buttonGroup1.add(seal);
        buttonGroup1.add(glidergun);
        buttonGroup1.add(gliderless_gun);
        buttonGroup1.add(bomber);
        buttonGroup1.add(factory_gun);
        buttonGroup1.add(blinker1);
        buttonGroup1.add(puff_suppressor);
        buttonGroup1.add(pufferFish);
        buttonGroup1.add(blinker2);
        buttonGroup1.add(gate_nand);
        buttonGroup1.add(gate_counter);
        buttonGroup1.add(gate_not);
        buttonGroup1.add(gate_xor);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel rootpanel;
    private JPanel gamePanel;
    private JPanel option;
    private JPanel speedPanel;
    private JSlider slider;
    private JPanel structurePanel;
    private JTabbedPane addingTabbedPanel;
    private JPanel spaceshipsPanel;
    private JToggleButton glider;
    private JToggleButton lightShip;
    private JToggleButton loafer;
    private JToggleButton seal;
    private JPanel gunsPanel;
    private JToggleButton glidergun;
    private JToggleButton gliderless_gun;
    private JToggleButton bomber;
    private JToggleButton factory_gun;
    private JPanel pufferPanel;
    private JToggleButton blinker1;
    private JToggleButton puff_suppressor;
    private JToggleButton pufferFish;
    private JToggleButton blinker2;
    private JPanel gatesPanel;
    private JToggleButton gate_nand;
    private JToggleButton gate_counter;
    private JToggleButton gate_not;
    private JToggleButton gate_xor;
    private JPanel newPanel;
    private JButton newButton;
    private JPanel buttonPanel;
    private JButton resetButton;
    private JButton startButton;
    private ButtonGroup buttonGroup1;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootpanel = new JPanel();
        rootpanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.setMaximumSize(new Dimension(1920, 1080));
        rootpanel.setMinimumSize(new Dimension(426, 81));
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(0, 0));
        rootpanel.add(gamePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(600, 600), null, 0, false));
        option = new JPanel();
        option.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.add(option, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        speedPanel = new JPanel();
        speedPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        option.add(speedPanel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        slider = new JSlider();
        slider.setMaximum(10);
        slider.setMinimum(1);
        slider.setName("Speed");
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setValue(1);
        slider.setValueIsAdjusting(true);
        speedPanel.add(slider, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setRequestFocusEnabled(true);
        label1.setText("Prędkość generacji");
        speedPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        structurePanel = new JPanel();
        structurePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        option.add(structurePanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(283, 113), null, 0, false));
        addingTabbedPanel = new JTabbedPane();
        addingTabbedPanel.setName("");
        structurePanel.add(addingTabbedPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 100), null, 0, false));
        spaceshipsPanel = new JPanel();
        spaceshipsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        spaceshipsPanel.setName("Spaceships");
        addingTabbedPanel.addTab("Spaceships", spaceshipsPanel);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        spaceshipsPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        glider = new JToggleButton();
        glider.setText("Glider");
        panel1.add(glider, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lightShip = new JToggleButton();
        lightShip.setText("Light ship");
        panel1.add(lightShip, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        loafer = new JToggleButton();
        loafer.setText("Loafer");
        panel1.add(loafer, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        seal = new JToggleButton();
        seal.setText("Seal");
        panel1.add(seal, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        gunsPanel = new JPanel();
        gunsPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        gunsPanel.setName("Guns");
        addingTabbedPanel.addTab("Guns", gunsPanel);
        glidergun = new JToggleButton();
        glidergun.setText("Gosper gun");
        gunsPanel.add(glidergun, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        gliderless_gun = new JToggleButton();
        gliderless_gun.setText("Gliderless gun");
        gunsPanel.add(gliderless_gun, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        bomber = new JToggleButton();
        bomber.setText("B-52 Bomber");
        gunsPanel.add(bomber, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        factory_gun = new JToggleButton();
        factory_gun.setText("Factory");
        gunsPanel.add(factory_gun, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pufferPanel = new JPanel();
        pufferPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        pufferPanel.setName("Puffers");
        addingTabbedPanel.addTab("Puffers", pufferPanel);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        pufferPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        blinker1 = new JToggleButton();
        blinker1.setText("Blinker1");
        panel2.add(blinker1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        puff_suppressor = new JToggleButton();
        puff_suppressor.setText("Puff Suppressor");
        panel2.add(puff_suppressor, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pufferFish = new JToggleButton();
        pufferFish.setText("Puffer fish");
        panel2.add(pufferFish, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        blinker2 = new JToggleButton();
        blinker2.setText("Blinker2");
        panel2.add(blinker2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        gatesPanel = new JPanel();
        gatesPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        gatesPanel.setName("Gates");
        addingTabbedPanel.addTab("Gates", gatesPanel);
        gate_nand = new JToggleButton();
        gate_nand.setText("NAND");
        gatesPanel.add(gate_nand, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        gate_counter = new JToggleButton();
        gate_counter.setText("Bin Counter");
        gatesPanel.add(gate_counter, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        gate_not = new JToggleButton();
        gate_not.setName("Gates");
        gate_not.setText("NOT");
        gatesPanel.add(gate_not, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        gate_xor = new JToggleButton();
        gate_xor.setText("XOR");
        gatesPanel.add(gate_xor, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        newPanel = new JPanel();
        newPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        option.add(newPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newButton = new JButton();
        newButton.setText("New");
        newPanel.add(newButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        option.add(buttonPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resetButton = new JButton();
        resetButton.setHideActionText(false);
        resetButton.setText("Reset");
        buttonPanel.add(resetButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setText("Start");
        buttonPanel.add(startButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        option.add(panel3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(glider);
        buttonGroup1.add(lightShip);
        buttonGroup1.add(loafer);
        buttonGroup1.add(seal);
        buttonGroup1.add(glidergun);
        buttonGroup1.add(blinker1);
        buttonGroup1.add(puff_suppressor);
        buttonGroup1.add(pufferFish);
        buttonGroup1.add(blinker2);
        buttonGroup1.add(gliderless_gun);
        buttonGroup1.add(bomber);
        buttonGroup1.add(factory_gun);
        buttonGroup1.add(gate_nand);
        buttonGroup1.add(gate_counter);
        buttonGroup1.add(gate_not);
        buttonGroup1.add(gate_xor);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootpanel;
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

