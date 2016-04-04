package pl.data.view;

import pl.data.model.classes.Coords2D;
import pl.data.model.enums.WireElectronState;
import pl.data.model.interfaces.CellCoordinates;
import pl.data.model.interfaces.CellState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Clasa wykorzystywana do pobierania odpowiednich struktur z plików tekstowych
 */
public class StructureAdd {

    public static Map<CellCoordinates,CellState> getGlider(CellState state1,CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates, CellState>();
        tab.put(new Coords2D(0,0), state1);
        tab.put(new Coords2D(1,0), state1);
        tab.put(new Coords2D(1,1), state1);
        tab.put(new Coords2D(0,2), state1);

        tab.put(new Coords2D(2,0), state2);
        tab.put(new Coords2D(0,1), state2);
        tab.put(new Coords2D(2,1), state2);
        tab.put(new Coords2D(1,2), state2);
        tab.put(new Coords2D(2,2), state2);
        return tab;
    }


    public static Map<CellCoordinates,CellState> getLightSpaceShip(CellState state1,CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        tab.put(new Coords2D(0,1), state1);
        tab.put(new Coords2D(0,3), state1);
        tab.put(new Coords2D(1,0), state1);
        tab.put(new Coords2D(1,1), state1);
        tab.put(new Coords2D(1,2), state1);
        tab.put(new Coords2D(2,0), state1);
        tab.put(new Coords2D(2,1), state1);
        tab.put(new Coords2D(2,2), state1);
        tab.put(new Coords2D(3,1), state1);
        tab.put(new Coords2D(3,2), state1);
        tab.put(new Coords2D(4,0), state1);

        tab.put(new Coords2D(0,0), state2);
        tab.put(new Coords2D(0,2), state2);
        tab.put(new Coords2D(1,3), state2);
        tab.put(new Coords2D(2,3), state2);
        tab.put(new Coords2D(3,0), state2);
        tab.put(new Coords2D(3,3), state2);
        tab.put(new Coords2D(4,1), state2);
        tab.put(new Coords2D(4,2), state2);
        tab.put(new Coords2D(4,3), state2);

        return tab;

    }


    public static Map<CellCoordinates,CellState> getLoafer(CellState state1, CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;
        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/loafer.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='A'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;

    }

    public static Map<CellCoordinates,CellState> getSeal(CellState state1,CellState state2)
    {
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;
        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/seal.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='A'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getGliderGun(CellState state1, CellState state2)
    {
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;
        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/glidergun.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='A'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getBlinker1(CellState state1, CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;
        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/blinker1.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='O'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    protected static Map<CellCoordinates,CellState> getBlinker2(CellState state1,CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;
        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/blinker2.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='O'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getSuppressor(CellState state1, CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;
        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/puff_suppressor.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='O'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getPufferFish(CellState state1,CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/pufferfish1.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='A'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }


    public static Map<CellCoordinates,CellState> getGliderlessGun(CellState state1,CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/gliderless.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='O'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getBomber(CellState state1, CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/bomber.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='O'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getFactory(CellState state1, CellState state2){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/factory.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='O'){
                        tab.put(new Coords2D(x,y),state2);
                    }
                    else
                    {
                        tab.put(new Coords2D(x,y),state1);
                    }

                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getNAND(){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/gates/nand.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='W'){
                        tab.put(new Coords2D(x,y), WireElectronState.WIRE);
                    }
                    else if(a=='H')
                    {
                        tab.put(new Coords2D(x,y),WireElectronState.ELECTRON_HEAD);
                    }
                    else if(a=='T'){
                        tab.put(new Coords2D(x,y),WireElectronState.ELECTRON_TAIL);
                    }
                    else {
                        tab.put(new Coords2D(x, y), WireElectronState.VOID);
                    }
                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getXor(){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/gates/xor.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='W'){
                        tab.put(new Coords2D(x,y), WireElectronState.WIRE);
                    }
                    else if(a=='H')
                    {
                        tab.put(new Coords2D(x,y),WireElectronState.ELECTRON_HEAD);
                    }
                    else if(a=='T'){
                        tab.put(new Coords2D(x,y),WireElectronState.ELECTRON_TAIL);
                    }
                    else {
                        tab.put(new Coords2D(x, y), WireElectronState.VOID);
                    }
                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getCounter(){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/gates/counter.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='W'){
                        tab.put(new Coords2D(x,y), WireElectronState.WIRE);
                    }
                    else if(a=='H')
                    {
                        tab.put(new Coords2D(x,y),WireElectronState.ELECTRON_HEAD);
                    }
                    else if(a=='T'){
                        tab.put(new Coords2D(x,y),WireElectronState.ELECTRON_TAIL);
                    }
                    else {
                        tab.put(new Coords2D(x, y), WireElectronState.VOID);
                    }
                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static Map<CellCoordinates,CellState> getNot(){
        Map<CellCoordinates,CellState> tab = new HashMap<CellCoordinates,CellState>();
        int x = 0;
        int y = 0;

        String filePath = new File("").getAbsolutePath();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(filePath + "/src/structures/gates/not1.txt")));
            while(in.hasNext()){
                x = 0;
                String znak = in.next();
                for(char a:znak.toCharArray()){

                    if(a=='W'){
                        tab.put(new Coords2D(x,y), WireElectronState.WIRE);
                    }
                    else if(a=='H')
                    {
                        tab.put(new Coords2D(x,y),WireElectronState.ELECTRON_HEAD);
                    }
                    else if(a=='T'){
                        tab.put(new Coords2D(x,y),WireElectronState.ELECTRON_TAIL);
                    }
                    else {
                        tab.put(new Coords2D(x, y), WireElectronState.VOID);
                    }
                    x++;
                }

                y++;
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return tab;
    }

}
