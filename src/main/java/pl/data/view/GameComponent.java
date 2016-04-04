package pl.data.view;

import pl.data.model.classes.*;
import pl.data.model.enums.AntState;
import pl.data.model.enums.BinaryState;
import pl.data.model.enums.QuadState;
import pl.data.model.enums.WireElectronState;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by talik on 12.12.15.
 */

/**
 * główny komponent na którym rysuje Automat
 *
 *
 * */
public class GameComponent extends JComponent{
    public Automaton getGame() {
        return game;
    }

    private Automaton game;
    private Automaton oldgame;
    private int currentX;
    private int currentY;

    private BufferedImage bufferedImage;
    private BufferedImage antW;
    private BufferedImage antB;


    public void setGame(Automaton game) {
        this.game = game;
        this.oldgame = game;
        bufferedImage = null;
    }

    /**
     * konstruktor z dodanie MouseListener do zmiany stanów
     */
    public GameComponent(){
        setDoubleBuffered(true);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                if((game!=null) && (game instanceof Automaton2Dim)) {

                    int Xcoord = currentX*((Automaton2Dim) game).getWidth()/getSize().width;
                    int Ycoord = currentY*((Automaton2Dim) game).getHeight()/getSize().height;
                    Coords2D coord = new Coords2D(Xcoord,Ycoord);

                    CellState state = game.getCells().get(coord);
                    if(state!=null){
                        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
                        if(game instanceof QuadLIfe){
                            state = QuadState.values()[(((QuadState) state).ordinal()+1)%5];
                            tab.put(coord,state);
                            paintQL(tab);
                        }
                        else if(game instanceof GameOfLife){
                            state = BinaryState.values()[(((BinaryState) state).ordinal()+1)%2];
                            tab.put(coord,state);
                            paintGOL(tab);
                        }
                        else if(game instanceof WireWorld){
                            state = WireElectronState.values()[(((WireElectronState) state).ordinal()+1)%4];
                            tab.put(coord,state);
                            paintWW(tab);
                        }
                        else if(game instanceof LangtonAnt){
                            LangtonCell stateL = (LangtonCell) state;
                            AntState antState = AntState.values()[(stateL.antState.ordinal()+1)%5];
                            stateL = new LangtonCell(stateL.cellState,stateL.antId,antState);
                            tab.put(coord,stateL);
                            paintLA(tab);
                        }

                        game.insertStructure(tab);
                        repaint();
                    }

                }
            }

        });
    }

    /**
     * główna metoda w której wykonuje się nextState i rysowanie
     */
    public void paintR(){
        if(game!=null) {
            oldgame = game;
            game = game.nextState();
            imagePaint();
            if((game instanceof Automaton2Dim) && (oldgame instanceof Automaton2Dim)) {
                getGraphics().setColor(Color.BLACK);
                Map<CellCoordinates,CellState> tab = ((Automaton2Dim) game).diffrent((Automaton2Dim) oldgame);
                paint2D(tab);
            }

        }
        repaint();
    }

    /**
     * metoda do rysowania Automaty 2-wymiarowego
     * */
    private void paint2D(Map<CellCoordinates, CellState> tab) {
        if(game instanceof QuadLIfe)
        {
            paintQL(tab);
        }
        else if(game instanceof GameOfLife){
            paintGOL(tab);
        }
        else if(game instanceof WireWorld){
            paintWW(tab);
        }
        else if(game instanceof LangtonAnt){
            paintLA(tab);
        }
    }

    /**
     * tworzy bufferedImage na którym przedstawiony jest cały Automat
     * */
    public void imagePaint()
    {
        if((bufferedImage==null) && (game!=null))
        {
            if(game instanceof Automaton2Dim) {
                int width = ((Automaton2Dim) game).getWidth();
                int height = ((Automaton2Dim) game).getHeight();
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Map<CellCoordinates,CellState> tab = game.getCells();
                makeAnt();
                paint2D(tab);
            }
        }
    }

    /**
     * metoda do pierwszego rysowania przy załadowaniu nowego Automatu
     * */
    public void firstPaint(){
        if(game!=null){
            if(game instanceof Automaton2Dim)
                imagePaint();
            else
                paintOD();
            repaint();
        }
    }


    /**
     * głowna metoda do rysowania, która jest wywoływana podczas repaint
     */
    public void paintComponent(Graphics g){
        g.drawImage(bufferedImage,0,0,getSize().width,getSize().height,null);
        if(game instanceof Automaton2Dim)
            paintLine(g);
        if(game instanceof LangtonAnt)
            paintAnt(game.getCells(),(Graphics2D) g);
    }

    /**
     * metoda rysująca siatkę, dla Automatów 2-wymiarowych mniejszych równych jak 100x100
     *
     * */
    private void paintLine(Graphics g){
        if((game!=null) && (game instanceof Automaton2Dim)){
            int width = ((Automaton2Dim) game).getWidth();
            int height = ((Automaton2Dim) game).getHeight();
            Line2D line = new Line2D.Double();
            if(width<101){
                double x = getSize().getWidth()/width;
                double y = getSize().getHeight()/height;
                g.setColor(Color.BLACK);
                for(int i=1;i<width;i++){
                    line.setLine(i*x,0,x*i,getSize().getHeight());
                    ((Graphics2D)g).draw(line);
                }
                for (int j=0;j<height;j++){
                    line.setLine(0,j*y,getSize().getWidth(),y*j);
                    ((Graphics2D)g).draw(line);
                }
            }
        }

    }

    /** metoda do rysowania GameOfLife */
    private void paintGOL(Map<CellCoordinates, CellState> drawTab)
    {

        for(CellCoordinates cc: drawTab.keySet())
        {
            if(cc instanceof Coords2D)
            {
                if(drawTab.get(cc)!= BinaryState.DEAD){
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.BLACK.getRGB());
                }
                else {
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.WHITE.getRGB());
                }
            }
        }
    }

    /**
     * metoda do rysowania komórek LangtonAnt bez mrówek
     * */
    private void paintLA(Map<CellCoordinates, CellState> drawTab){
        for(CellCoordinates cc: drawTab.keySet())
        {
            if(cc instanceof Coords2D)
            {
                LangtonCell state = (LangtonCell) drawTab.get(cc);
                    if (state.cellState != BinaryState.DEAD)
                        bufferedImage.setRGB(((Coords2D) cc).getX(), ((Coords2D) cc).getY(), Color.BLACK.getRGB());
                    else
                        bufferedImage.setRGB(((Coords2D) cc).getX(), ((Coords2D) cc).getY(), Color.WHITE.getRGB());
            }
        }
    }

    /**
     * metoda do rysowania wszystkich mrówek, odpowiednio przeskalowanych i obróconych
     * */
    private void paintAnt(Map<CellCoordinates,CellState> drawTab,Graphics2D g){

        for(CellCoordinates cc: drawTab.keySet()){
            if(cc instanceof Coords2D){
                int widthR = getSize().width / ((Automaton2Dim) game).getWidth();
                int heightR = getSize().height / ((Automaton2Dim) game).getHeight();
                int x = ((Coords2D) cc).getX();
                int y = ((Coords2D) cc).getY();
                LangtonCell state = (LangtonCell)drawTab.get(cc);
                if(state.antState!=AntState.NONE){

                    AffineTransform at = new AffineTransform();
                    at.translate(widthR*x,heightR*y);
                    switch (state.antState){
                        case EAST:at.rotate(Math.PI/2);
                            at.translate(0,-heightR);
                            break;
                        case SOUTH: at.rotate(Math.PI);
                            at.translate(-widthR,-heightR);
                            break;
                        case WEST: at.rotate(-Math.PI/2);
                            at.translate(-widthR,0);
                            break;
                    }
                    at.scale(widthR/30,heightR/30);

                    if(state.cellState != BinaryState.DEAD){
                        g.drawImage(antW,at,null);
                    }
                    else{

                        g.drawImage(antB,at,null);
                    }
                }
            }
        }
    }

    /**
     * metoda do rysowania WireWorld
     * */
    private void paintWW(Map<CellCoordinates, CellState> drawTab)
    {
        for(CellCoordinates cc: drawTab.keySet())
        {
            if(cc instanceof Coords2D)
            {
                WireElectronState state = (WireElectronState) drawTab.get(cc);
                if(state == WireElectronState.VOID){
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.WHITE.getRGB());
                }
                else if(state == WireElectronState.WIRE){
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.YELLOW.getRGB());
                }
                else if(state == WireElectronState.ELECTRON_HEAD){
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.BLUE.getRGB());
                }
                else if(state == WireElectronState.ELECTRON_TAIL){
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.RED.getRGB());
                }
            }
        }
    }

    /**
     * metoda rysowania od razu całego Automatu 1-wymiarowego
     * */
    public void paintOD(){
        if(game!=null){
            int size = ((OneDimension )game).getSize()/2;

            if(bufferedImage==null){
                bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            }
            for(int i=0;i<size;i++){
                paint1D(game.getCells(),i);
                game = game.nextState();
            }
            repaint();
        }


    }

    private void paint1D(Map<CellCoordinates,CellState> drawTab,int y){
        int size = ((OneDimension)game).getSize();
        for(CellCoordinates cc: drawTab.keySet()){
            int x = ((Coords1D)cc).getX();
            int x_left = size/4;
            int x_right = size*3/4;
            if((cc instanceof Coords1D) &&(x>x_left) && (x<x_right)){
                BinaryState state = (BinaryState) drawTab.get(cc);
                if(state == BinaryState.ALIVE){
                    bufferedImage.setRGB(x-x_left,y, Color.BLACK.getRGB());
                }
                else
                    bufferedImage.setRGB(x-x_left,y, Color.WHITE.getRGB());
            }
        }

    }

    /**
     * metoda do rysowania QuadLife
     * */
    private void paintQL(Map<CellCoordinates, CellState> drawTab)
    {

        for(CellCoordinates cc: drawTab.keySet())
        {
            if(cc instanceof Coords2D)
            {
                QuadState state = (QuadState) drawTab.get(cc);
                if(state == QuadState.DEAD){
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.WHITE.getRGB());
                }
                else if(state == QuadState.RED)
                {
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.RED.getRGB());
                }
                else if(state == QuadState.GREEN)
                {
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.GREEN.getRGB());
                }
                else if(state == QuadState.BLUE)
                {
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.BLUE.getRGB());
                }
                else if(state == QuadState.YELLOW)
                {
                    bufferedImage.setRGB(((Coords2D) cc).getX(),((Coords2D) cc).getY(),Color.YELLOW.getRGB());
                }

            }
        }

    }

    /** metoda do stworzenia obrazów mrówek z pliku tekstowego*/
    private void makeAnt(){

        antB = new BufferedImage(30,30,BufferedImage.TYPE_INT_RGB);
        antW = new BufferedImage(30,30,BufferedImage.TYPE_INT_RGB);

        int x;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/ant.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='A'){
                        antB.setRGB(x,y,Color.BLACK.getRGB());
                        antW.setRGB(x,y,Color.WHITE.getRGB());
                    }
                    else
                    {
                        antW.setRGB(x,y,Color.BLACK.getRGB());
                        antB.setRGB(x,y,Color.WHITE.getRGB());
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

      /*".........A...........A........."
        ".........A...........A........."
        "..........A.........A.........."
        "...........A.......A..........."
        "............AAAAAAA............"
        "............A.AAA.A............"
        ".......A....AAAAAAA....A......."
        ".......A.....AAAAA.....A......."
        "........A.....AAA.....A........"
        ".........A....AAA....A........."
        "..........AAAAAAAAAAA.........."
        "...........AAAAAAAAA..........."
        "..............AAA.............."
        "..............AAA.............."
        "..........AAAAAAAAAAA.........."
        ".........AAAAAAAAAAAAA........."
        "........AA....AAA....AA........"
        ".......A......AAA......A......."
        "......A.......AAA.......A......"
        "...........AAAAAAAAA..........."
        "..........AAAAAAAAAAA.........."
        ".........AA...AAA...AA........."
        "........A.....AAA.....A........"
        ".......A.....AAAAA.....A......."
        "......A.....AAAAAAA.....A......"
        "............AAAAAAA............"
        "............AAAAAAA............"
        "............AAAAAAA............"
        ".............AAAAA............."
        "..............................."
        "..............................." */




    }


    /**
     * przekształca i rysuje dodaną strukturę, jeśli ta zmieści się w Automacie
     * */
    public void insertStructure(Map<CellCoordinates,CellState> tab,int currentX,int currentY){
        if((game!=null) && (game instanceof Automaton2Dim)){
            int Xcoord = currentX*((Automaton2Dim) game).getWidth()/getSize().width;
            int Ycoord = currentY*((Automaton2Dim) game).getHeight()/getSize().height;
            Map<CellCoordinates,CellState> structure = new HashMap<CellCoordinates,CellState>();
            for(CellCoordinates cc:tab.keySet()){
                Coords2D coords2D = new Coords2D(((Coords2D)cc).getX()+Xcoord,((Coords2D)cc).getY()+Ycoord);
                structure.put(coords2D,tab.get(cc));
            }
            if(game.insertStructure(structure))
                paint2D(structure);
            repaint();
        }
    }

}
