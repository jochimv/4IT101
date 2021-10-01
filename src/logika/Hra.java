package logika;

import java.io.Serializable;
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

public class Hra implements IHra, Serializable {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private Batoh batoh;
    private boolean konecHry = false;
    private Strážný strazny;
    private static boolean vlkPorazeny = false;

    /**
     * Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        batoh = Batoh.getInstance();
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy, herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazTeleport(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));

        platnePrikazy.vlozPrikaz(new PrikazSmaz(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazUloz(this, herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazSeber(herniPlan, batoh));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(herniPlan, batoh));
        platnePrikazy.vlozPrikaz(new PrikazKapacita(batoh, herniPlan));
        strazny = new Strážný();
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
        slovoPrikazu = zkontrolujHelp(slovoPrikazu);

        String[] slovaBezPrikazu = vratSlovaBezPrikazu(slova);

        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        zkontrolujStrazneho(aktualniProstor, strazny, slovoPrikazu, slova);

        String textKVypsani;

        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = "\n" + prikaz.provedPrikaz(slovaBezPrikazu);
            textKVypsani = zkontrolujOpustenyDum(herniPlan, textKVypsani);
            textKVypsani = zkontrolujVlka(herniPlan, batoh, vlkPorazeny, textKVypsani, aktualniProstor);

            String zaverecnyText = overZdaJeKonec();

            if (zaverecnyText != null) {
                textKVypsani = textKVypsani + zaverecnyText;
            }
        } else {
            textKVypsani = Barvy.ANSI_BLUE + "\nNevím co tím myslíš? Tento příkaz neznám.\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis();
        }
        return textKVypsani;
    }

    /**
     * metoda vezme všechny slova, které jsme napsali do terminálu, a vrátí druhé slovo a další (slova bez příkazu) k dalšímu zpracování
     */
    private static String[] vratSlovaBezPrikazu(String[] slova){
        String[] slovaBezPrikazu = new String[slova.length -1];
        for (int i = 0; i < slovaBezPrikazu.length; i++){
            slovaBezPrikazu[i] = slova[i+1];
        }
        return slovaBezPrikazu;
    }

    private static String zkontrolujVlka(HerniPlan herniPlan, Batoh batoh, boolean vlkPorazeny, String textKVypsani, Prostor aktualniProstor) {
        if (herniPlan.getAktualniProstor().getNazev().equals("vlk") && batoh.obsahujeVec("meč") && !vlkPorazeny) {
            Hra.vlkPorazeny = true;
            return textKVypsani + Barvy.ANSI_BLUE + "\n\nVlk je poražen a můžeme projít\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis();
        } else if (herniPlan.getAktualniProstor().getNazev().equals("vlk") && !batoh.obsahujeVec("meč") && !vlkPorazeny) {
            herniPlan.setAktualniProstor(aktualniProstor);
            return textKVypsani + Barvy.ANSI_RED + "\nVlk nás zahnal zpátky\n" + Barvy.ANSI_BLUE + "\nZměnil jsi prostor\n" + Barvy.ANSI_RESET + aktualniProstor.dlouhyPopis();
        } else {
            return textKVypsani;
        }
    }

    private static String zkontrolujOpustenyDum(HerniPlan herniPlan, String textKVypsani) {
        if (herniPlan.getAktualniProstor().getNazev().equals("opuštěný dům")) {
            textKVypsani += Barvy.ANSI_RED + "\nkarkulka se lekla a utekla" + Barvy.ANSI_RESET;
            Random r = new Random();
            int randomLoc = r.nextInt(4);
            ArrayList<Prostor> sousedni = new ArrayList<>();
            for (Prostor prostor : herniPlan.getAktualniProstor().getVychody()) {
                sousedni.add(prostor);
            }
            herniPlan.setAktualniProstor(sousedni.get(randomLoc));
            return textKVypsani + "\n\n" + Barvy.ANSI_BLUE + "Změnil jsi prostor\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis();
        } else {
            return textKVypsani;
        }
    }

    private static void zkontrolujStrazneho(Prostor aktualniProstor, Strážný strazny, String slovoPrikazu, String[] slova) {
        if ((strazny.getVesniceProstor() == aktualniProstor || strazny.getProstorMost() == aktualniProstor) && slovoPrikazu.equals("jdi") && aktualniProstor.vratSousedniProstor(slova[1]) != null) {
            strazny.tiskniNahodnouHadanku();
        }
    }

    private static String zkontrolujHelp(String slovo) {
        if (slovo.equals("nápověda") || slovo.equals("help")) {
            return "pomoc";
        } else {
            return slovo;
        }
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

