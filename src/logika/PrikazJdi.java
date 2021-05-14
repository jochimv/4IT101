package logika;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček
 * @version pro školní rok 2016/2017
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;

    /**
     * Konstruktor třídy
     *
     * @param plan herní plán, ve kterém se bude ve hře "chodit"
     */
    public PrikazJdi(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     * existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     * (východ) není, vypíše se chybové hlášení.
     *
     * @param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                  do kterého se má jít.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String[] parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return Barvy.ANSI_BLUE + "Kam mám jít? Musíš zadat jméno východu" + Barvy.ANSI_RESET + "\n" + plan.getAktualniProstor().dlouhyPopis();
        }
        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru

        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);
        if (sousedniProstor == null) {
            try {
                smer = parametry[0] + " " + parametry[1];
                sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);
            } catch(ArrayIndexOutOfBoundsException e){

            }
        }

        if (sousedniProstor == null) {

            return Barvy.ANSI_BLUE + "Tam se odsud jít nedá!" + Barvy.ANSI_RESET + "\n"+ plan.getAktualniProstor().dlouhyPopis() ;
        } else {
            plan.setAktualniProstor(sousedniProstor);
            return Barvy.ANSI_BLUE + "Změnil jsi prostor\n" + Barvy.ANSI_RESET + sousedniProstor.dlouhyPopis();
        }
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
