package logika;

import java.util.Random;

public class PrikazTeleport implements IPrikaz {
private static final String NAZEV = "teleport";
private HerniPlan herniPlan;
private Random random;

public PrikazTeleport(HerniPlan herniPlan){
    this.herniPlan = herniPlan;
    random = new Random();
}

    @Override
    public String provedPrikaz(String... parametry) {
        if (Batoh.getInstance().obsahujeVec(ZJ.PRSTEN)){
            int velikost = HerniPlan.getProstoryHry().size();
            int indexTeleportace = random.nextInt(velikost);
            Prostor prostorTeleportace = HerniPlan.getProstoryHry().get(indexTeleportace);
            herniPlan.setAktualniProstor(prostorTeleportace);

            /*int indexPrstenu = random.nextInt(velikost);
            Prostor prostorPrstenu = HerniPlan.getProstoryHry().get(indexPrstenu);
            Vec prsten = Batoh.getInstance().vratVec("prsten");
            Batoh.getInstance().odeberZBatohu("prsten");
            prostorPrstenu.vlozVec(prsten);*/

            return Barvy.ANSI_BLUE + "Teleportace proběhla úspěšně\n" + Barvy.ANSI_RESET+ herniPlan.getAktualniProstor().dlouhyPopis();

        } else {
            return Barvy.ANSI_BLUE + "K teleportu potřebuješ prsten.\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis() ;
        }
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
