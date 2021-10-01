/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package main;


import logika.*;

import uiText.TextoveRozhrani;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru určenou k dalším úpravám a rozšiřování
 *
 * @author Jarmila Pavlíčková
 * @version ZS 2016/2017
 */
public class Start {
    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args) {
        Hra hra = new Hra();
        String odpoved = "";
        Path ulozenaHra = FileSystems.getDefault().getPath("file.ser");
        if (zkontrolujUlozenouHru(ulozenaHra)) {
            odpoved = naskenujOdpoved();
        }
        if (odpoved.equals("a")){
            hra = nactiUlozenouHru();
        }

        TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
        textoveRozhrani.hraj();

    }
    private static boolean zkontrolujUlozenouHru(Path cestaKSouboru){
        if (Files.exists(cestaKSouboru) && cestaKSouboru.endsWith("file.ser")){
            return true;
        } else{
            return false;
        }
    }
    private static String naskenujOdpoved(){
        String odpoved;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Byla nalezena uložená hra. Chcete ji načíst? (a/n)");
            if (scanner.hasNextLine()) {
                odpoved = scanner.nextLine().toLowerCase();
                if (odpoved.equals("a") || odpoved.equals("n")) {
                    return odpoved;
                }
            }
        }
    }
    private static Hra nactiUlozenouHru(){
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("file.ser")))){
            return (Hra) objectInputStream.readObject();
        } catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
