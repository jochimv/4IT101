package logika;

import java.util.*;

public class Stážný {

    private ArrayList<Prostor> prostory;
    private Random random;
    private Prostor vesniceProstor;
    private ArrayList<Hadanka> hadanky;
    private Prostor prostorMost;

    public Stážný() {
        random = new Random();
        prostory = HerniPlan.getProstoryHry();
        for (Prostor prostor : prostory) {
            if (prostor.getNazev().equals("vesnice")) {
                vesniceProstor = prostor;
            } else if (prostor.getNazev().equals("most")) {
                prostorMost = prostor;
            }
        }
        hadanky = new ArrayList<>();
        hadanky.add(new Hadanka("a", "Jakou velikost má byte?\n\ta) 8 bit\n\tb) 16 bit\n\tc) 32 bit\n\td) 64 bit"));
        hadanky.add(new Hadanka("b", "Jaká je základní hodnota pro short datový typ?\n\ta) 0.0\n\tb) 0\n\tc) null\n\td) nedefinován"));
        hadanky.add(new Hadanka("c", "Co umožňuje polymorfismus?\n\ta) přepsat obsah zdeděné metody pro objekty podtřídy\n\tb) umožňuje vytvářet více metod se stejným jménem za podmínky rozdílných argumentů\n\tc) \"vyšší\" referenci ukazovat na všechny nižší objekty\n\td) vytvářet více objektů stejného typu"));
        hadanky.add(new Hadanka("a", "Co umožňuje overriding?\n\ta) přepsat obsah zdeděné metody pro objekty podtřídy\n\tb) umožňuje vytvářet více metod se stejným jménem za podmínky rozdílných argumentů\n\tc) \"vyšší\" referenci ukazovat na všechny nižší objekty\n\td) vytvářet více objektů stejného typu"));
        hadanky.add(new Hadanka("b", "Co umožňuje overloading?\n\ta) přepsat obsah zdeděné metody pro objekty podtřídy\n\tb) umožňuje vytvářet více metod se stejným jménem za podmínky rozdílných argumentů\n\tc) \"vyšší\" referenci ukazovat na všechny nižší objekty\n\td) vytvářet více objektů stejného typu"));
        hadanky.add(new Hadanka("d", "Jaký je rozdíl mezi abstraktní třídou a interface?\n\ta) žádný\n\tb) abstraktní třída je plně abstraktní, interface je pouze částečně abstraktní\n\tc) třída může implementovat více abstraktních tříd, ale pouze jedno interface\n\td) abstraktní třída má část metod abstraktních, a část konkrétních, interface má všechny metody abstraktní"));
        hadanky.add(new Hadanka("d", "lambda výrazy:\n\ta) znemožňují ukládání proměnných do cache CPU, čímž zabraňují potenciálním problémům při multithreadingu\n\tb) synchronizaci metod\n\tc) vrácení nemodifikovatelných kolekcí\n\td) pohodlnější instancování interface a abstraktních tříd"));
        hadanky.add(new Hadanka("a", "Když před proměnnou napíšeme slovo volatile:\n\ta) znemožňíme její uložení v cache CPU, čímž zabraníme potenciálním problémům při multithreadingu\n\tb) synchronizaci metod\n\tc) vrácení nemodifikovatelných kolekcí\n\td) pohodlnější instancování interface a abstraktních tříd"));
        hadanky.add(new Hadanka("c", "Jaké tvrzení je nepravdivé o heap?\n\ta) ukládá objekty\n\tb) objekty jsou z heap mazány pomocí garbage collectoru, když nemají žádnou referenci\n\tc) ukládá reference na objekty\n\td) vyžádá si část RAM s každým vytvořeným objektem"));
        hadanky.add(new Hadanka("c", "Jaké klíčové slovo slouží pro ukončení smyčky?\n\ta) switch\n\tb) continue\n\tc) break\n\td) false"));


    }

    public Prostor getVesniceProstor() {
        return vesniceProstor;
    }

    public Prostor getProstorMost() {
        return prostorMost;
    }

    public Hadanka tiskniNahodnouHadanku() {
        Scanner scanner = new Scanner(System.in);
        Hadanka nahodna = hadanky.get(random.nextInt(hadanky.size()));
        System.out.println(Barvy.ANSI_BLUE + "Při odchodu jsi strážného. Aby jsi mohl projít dál, musíš mu odpovědět na jeho otázku. Strážný se tě ptá:\n" + nahodna.getZneni() + Barvy.ANSI_RESET);
        if (scanner.hasNextLine()) {
            String odpoved = scanner.nextLine().toLowerCase();
            if ((odpoved.equals("a") || odpoved.equals("b") || odpoved.equals("c") || odpoved.equals("d")) && odpoved.equals(nahodna.getOdpoved())) {
                System.out.println(Barvy.ANSI_BLUE + "Správně! Strážný tě pouští z dál" + Barvy.ANSI_RESET);
                return null;
            } else {
                System.out.println(Barvy.ANSI_BLUE + "Špatná odpověď\n" + Barvy.ANSI_RESET);
                tiskniNahodnouHadanku();
            }
        }
        return null;
    }
}
