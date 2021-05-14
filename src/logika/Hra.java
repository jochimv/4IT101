package logika;

import java.util.ArrayList;
import java.util.Random;

/**
 * Třída Hra - třída představující logiku adventury.
 * <p>
 * Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan, která inicializuje mistnosti hry
 * a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 * Vypisuje uvítací a ukončovací text hry.
 * Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private Batoh batoh;
    private boolean konecHry = false;

    /**
     * Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        batoh = Batoh.getInstance();
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));

        platnePrikazy.vlozPrikaz(new PrikazSeber(herniPlan, batoh));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(herniPlan, batoh));
        platnePrikazy.vlozPrikaz(new PrikazKapacita(batoh, herniPlan));
    }

    /**
     * Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return Barvy.ANSI_BLUE + "Vítejte!\n" +
                "Toto je příběh o Červené Karkulce, babičce a vlkovi.\n" +
                "Napište 'pomoc', pokud si nevíte rady, jak hrát dál.\n" + Barvy.ANSI_RESET +
                "\n" +
                herniPlan.getAktualniProstor().dlouhyPopis();
    }

    /**
     * Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        return Barvy.ANSI_BLUE + "Dík, že jste si zahráli. Ahoj." + Barvy.ANSI_RESET;
    }

    /**
     * Vrací true, pokud hra skončila.
     */
    public boolean konecHry() {
        return konecHry;
    }

    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     * Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     * Pokud ano spustí samotné provádění příkazu.
     *
     * @param radek text, který zadal uživatel jako příkaz do hry.
     * @return vrací se řetězec, který se má vypsat na obrazovku
     */
    public String zpracujPrikaz(String radek) {
        String[] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        if (slovoPrikazu.equals("nápověda") || slovoPrikazu.equals("help")) {
            slovoPrikazu = "pomoc";
        }
        String[] parametry = new String[slova.length - 1];
        for (int i = 0; i < parametry.length; i++) {
            parametry[i] = slova[i + 1];
        }

        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        String textKVypsani;
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = "\n"+  prikaz.provedPrikaz(parametry);
            if (slovoPrikazu.equals("pomoc") || slovoPrikazu.equals("polož") || slovoPrikazu.equals("seber")) {
                textKVypsani += "\n" + herniPlan.getAktualniProstor().dlouhyPopis();
            }
            if (herniPlan.getAktualniProstor().getNazev().equals("opuštěný dům")) {
                textKVypsani += Barvy.ANSI_RED + "\nkarkulka se lekla a utekla" + Barvy.ANSI_RESET;
                Random r = new Random();
                int randomLoc = r.nextInt(4);
                ArrayList<Prostor> sousedni = new ArrayList<>();
                for (Prostor prostor : herniPlan.getAktualniProstor().getVychody()) {
                    sousedni.add(prostor);
                }
                herniPlan.setAktualniProstor(sousedni.get(randomLoc));
                textKVypsani += "\n\n"+ Barvy.ANSI_BLUE + "Změnil jsi prostor\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis();
            }
            if (herniPlan.getAktualniProstor().getNazev().equals("vlk") && batoh.obsahujeVec("meč")) {
                textKVypsani += Barvy.ANSI_BLUE + "\n\nvlk je poražen a můžeme projít\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis();
            } else if (herniPlan.getAktualniProstor().getNazev().equals("vlk") && !batoh.obsahujeVec("meč")) {
                herniPlan.setAktualniProstor(aktualniProstor);
                textKVypsani += Barvy.ANSI_RED +"\nvlk nás zahnal zpátky\n" + Barvy.ANSI_BLUE + "\nZměnil jsi prostor\n" + Barvy.ANSI_RESET + aktualniProstor.dlouhyPopis();

            }
            String zaverecnyText = overZdaJeKonec();

            if (zaverecnyText != null) {
                textKVypsani = textKVypsani + zaverecnyText;
            }
        } else {
            textKVypsani = Barvy.ANSI_BLUE + "\nNevím co tím myslíš? Tento příkaz neznám.\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis() ;
        }
        return textKVypsani;
    }

    private String overZdaJeKonec() {
        if (herniPlan.getAktualniProstor().getNazev().equals("chaloupka")) {
            this.setKonecHry(true);
            return Barvy.ANSI_BLUE + "Karkulka donesla košík babičce a tím hra končí." + Barvy.ANSI_RESET;
        }
        return null;
    }


    /**
     * Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     * mohou ji použít i další implementace rozhraní Prikaz.
     *
     * @param konecHry hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }

    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     * @return odkaz na herní plán
     */
    public HerniPlan getHerniPlan() {
        return herniPlan;
    }

}

