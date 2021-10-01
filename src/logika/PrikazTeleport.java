package logika;

import java.io.Serializable;
import java.util.Random;

public class PrikazTeleport implements IPrikaz, Serializable {
private static final String NAZEV = "teleport";
private HerniPlan herniPlan;
private Random random;

public PrikazTeleport(HerniPlan herniPlan){
    this.herniPlan = herniPlan;
    random = new Random();
}

    @Override
    public String provedPrikaz(String... parametry) {
        if (Batoh.getInstance().obsahujeVec("prsten")){
            int velikost = HerniPlan.getProstoryHry().size();
            int indexTeleportace = random.nextInt(velikost);
            Prostor prostorTeleportace = HerniPlan.getProstoryHry().get(indexTeleportace);
            herniPlan.setAktualniProstor(prostorTeleportace);

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
