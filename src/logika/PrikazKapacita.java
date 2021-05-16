package logika;


public class PrikazKapacita implements IPrikaz {
    private static final String NAZEV = "kapacita";
    private Batoh batoh;
    private HerniPlan herniPlan;


    public PrikazKapacita(Batoh batoh, HerniPlan herniPlan) {
        this.batoh = batoh;
        this.herniPlan = herniPlan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        return Barvy.ANSI_BLUE + "Do batohu se ještě vleze " + (batoh.KAPACITA - batoh.getVeci().size()) + " věci" + Barvy.ANSI_RESET + "\n" + herniPlan.getAktualniProstor().dlouhyPopis();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
