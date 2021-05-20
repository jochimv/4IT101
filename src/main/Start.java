/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package main;


import logika.*;

import uiText.TextoveRozhrani;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        Path path = FileSystems.getDefault().getPath("file.ser");
        if (Files.exists(path)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Byla nalezena uložená hra. Chcete ji načíst? (a/n)");
                if (scanner.hasNextLine()) {
                    odpoved = scanner.nextLine().toLowerCase();
                    if (odpoved.equals("a") || odpoved.equals("n")) {
                        break;
                    }
                }
            }
        }
        if (odpoved.equals("a")){
            try(ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path.toString())))){
                hra = (Hra) objectInputStream.readObject();
            } catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }

        TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
        textoveRozhrani.hraj();

    }
}
