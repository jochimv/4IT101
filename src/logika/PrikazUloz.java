package logika;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class PrikazUloz implements IPrikaz, Serializable {

    private Hra hra;
private HerniPlan herniPlan;

    public PrikazUloz(Hra hra, HerniPlan herniPlan){

        this.hra = hra;
        this.herniPlan = herniPlan;
    }


    private static final String NAZEV = "ulož";
    @Override
    public String provedPrikaz(String... parametry) {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("file.ser")))){

            objectOutputStream.writeObject(hra);

            return Barvy.ANSI_BLUE + "Hra uložena\n" + Barvy.ANSI_RESET + herniPlan.getAktualniProstor().dlouhyPopis()  ;

        } catch (IOException e){
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
