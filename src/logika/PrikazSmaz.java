package logika;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class PrikazSmaz implements IPrikaz{
private static final String NAZEV = "smaž";
public HerniPlan herniPlan;

public PrikazSmaz(HerniPlan herniPlan){
    this.herniPlan = herniPlan;
}
    @Override
    public String provedPrikaz(String... parametry) {
    String textKVraceni;
    Path path = FileSystems.getDefault().getPath("file.ser");

    if (Files.exists(path)){
        try {
            Files.delete(path);
        } catch (FileNotFoundException e){

        } catch (IOException e){
            e.printStackTrace();
        }
        textKVraceni = Barvy.ANSI_BLUE + "Záloha smazána\n"+ Barvy.ANSI_RESET;
    } else{
        textKVraceni = Barvy.ANSI_BLUE + "Záloha nenalezena\n" + Barvy.ANSI_RESET;
    }

        return textKVraceni + herniPlan.getAktualniProstor().dlouhyPopis();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
