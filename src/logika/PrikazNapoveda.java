package logika;

import java.io.Serializable;

/**
 * Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček
 * @version pro školní rok 2016/2017
 */
class PrikazNapoveda implements IPrikaz, Serializable {

    private static final String NAZEV = "pomoc";
    private SeznamPrikazu platnePrikazy;
    private HerniPlan herniPlan;


    /**
     * Konstruktor třídy
     *
     * @param platnePrikazy seznam příkazů,
     *                      které je možné ve hře použít,
     *                      aby je nápověda mohla zobrazit uživateli.
     */
    public PrikazNapoveda(SeznamPrikazu platnePrikazy, HerniPlan herniPlan) {
        this.platnePrikazy = platnePrikazy;
        this.herniPlan = herniPlan;
    }

    /**
     * Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     * vcelku primitivní zpráva a seznam dostupných příkazů.
     *
     * @return napoveda ke hre
     */
    @Override
    public String provedPrikaz(String[] parametry) {
        return Barvy.ANSI_BLUE + "Tvým úkolem je dovést Červenou Karkulku z domečku\n"
                + "až k babičce, která bydlí v chaloupce za lesem.\n"
                + "Můžeš zadat tyto příkazy: "
                + platnePrikazy.vratNazvyPrikazu() + Barvy.ANSI_RESET + "\n" + herniPlan.getAktualniProstor().dlouhyPopis();
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
