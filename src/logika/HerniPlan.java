package logika;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * <p>
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny prostory,
 * propojuje je vzájemně pomocí východů
 * a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public class HerniPlan {

    private static ArrayList<Prostor> prostoryHry = new ArrayList<>();
    private Prostor aktualniProstor;

    public static ArrayList<Prostor> getProstoryHry(){
        return prostoryHry;
    }

    /**
     * Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     * Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        zalozProstoryHry();

    }

    /**
     * Vytváří jednotlivé prostory a propojuje je pomocí východů.
     * Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Batoh batoh = Batoh.getInstance();


        Prostor domecek = new Prostor("domeček", "Domeček, ve kterém bydlí Karkulka", new ArrayList<>(Arrays.asList(new Vec("klíče", true), new Vec("mobil", true), new Vec("stůl", false))), batoh);
        Prostor hory = new Prostor("hory", "Vysoké hory, ve kterých zimuje lesní zvěř. V létě zde chodí vesničané lovit", new ArrayList<>(Arrays.asList(new Vec("lebka zvířete", true), new Vec("krumpáč", true))), batoh);
        Prostor rekaJ = new Prostor("řeka jih", "Řeka. Ještě že karkulka umí plavat", new ArrayList<>(Arrays.asList(new Vec("rybářská síť", true))), batoh);
        Prostor kameni = new Prostor("kamenatý prostor", "Kamenaté hory s hlubokou propastí. Tudy to asi nepůjde", new ArrayList<>(Arrays.asList(new Vec("stará zbraň", true), new Vec("kamení", false))), batoh);
        Prostor les1 = new Prostor("les", "les s jahodami, malinami a pramenem vody", new ArrayList<>(Arrays.asList(new Vec("jahody", true), new Vec("maliny", true), new Vec("ptačí vejce", true))), batoh);
        Prostor opustenyDum = new Prostor("opuštěný dům", "Opuštěný dům, ve kterém straší",new ArrayList<>(Arrays.asList(new Vec("", false))), batoh);
        Prostor rekaS = new Prostor("řeka sever", "Řeka. Ještě že karkulka umí plavat", new ArrayList<>(Arrays.asList(new Vec("ryba", true), new Vec("perla", true))), batoh);
        Prostor vlk = new Prostor("vlk", "Hladový vlk bránící v přístupu k babiččinému domečku", new ArrayList<>( Arrays.asList(new Vec("vlčí trus", false))), batoh);

        Prostor chaloupka = new Prostor("chaloupka", "chaloupka, ve které bydlí babička Karkulky", new ArrayList<Vec>(Arrays.asList(new Vec("", false))), batoh);
        Prostor vesnice = new Prostor("vesnice", "Vesnice plná obyvatel. Jsou zde obchody, kde by se dalo koupit spoustu užitečných věcí na cestu",new ArrayList<>( Arrays.asList(new Vec(ZJ.MEC, true))), batoh);
        Prostor pole = new Prostor("pole", "Zlatavé pole plné pšenice. Na okraji se nachází pár stavenišť, ve kterých bydlí rolníci", new ArrayList<>(Arrays.asList(new Vec("vozík", false), new Vec("ponožky", true), new Vec("bič", true))), batoh);
        Prostor most = new Prostor("most", "Nedávno postavený most přes řeku. Donedávna přes ni ještě museli jezdit převozníci", new ArrayList<>(Arrays.asList(new Vec("vlajka království", false), new Vec("křesadlo", true))), batoh);
        Prostor les2 = new Prostor("les", "Prastarý, hustý les, plný zvířat. Téměř nedotčená příroda", new ArrayList<>(Arrays.asList(new Vec("houby", true), new Vec("šišky", true), new Vec("zajíc", false))), batoh);

        prostoryHry.add(domecek);
        prostoryHry.add(hory);
        prostoryHry.add(rekaJ);
        prostoryHry.add(kameni);
        prostoryHry.add(les1);
        prostoryHry.add(rekaS);
        prostoryHry.add(vesnice);
        prostoryHry.add(pole);
        prostoryHry.add(most);
        prostoryHry.add(les2);

        Random random = new Random();
        int randomLocZlato = random.nextInt(prostoryHry.size());
        int randomLocPrsten = random.nextInt(prostoryHry.size());
        Vec zlato = new Vec(ZJ.ZLATO,true);
        prostoryHry.get(randomLocZlato).vlozVec(zlato);
        Vec prsten = new Vec(ZJ.PRSTEN, true);
        prostoryHry.get(randomLocPrsten).vlozVec(prsten);

        // přiřazují se průchody mezi prostory (sousedící prostory)
        domecek.setVychod(hory);
        domecek.setVychod(les1);

        hory.setVychod(rekaJ);
        hory.setVychod(opustenyDum);
        hory.setVychod(domecek);

        rekaJ.setVychod(hory);
        rekaJ.setVychod(kameni);
        rekaJ.setVychod(rekaS);

        les1.setVychod(domecek);
        les1.setVychod(opustenyDum);
        les1.setVychod(vesnice);

        opustenyDum.setVychod(hory);
        opustenyDum.setVychod(rekaS);
        opustenyDum.setVychod(pole);
        opustenyDum.setVychod(les1);

        rekaS.setVychod(opustenyDum);
        rekaS.setVychod(vlk);
        rekaS.setVychod(rekaJ);

        vlk.setVychod(chaloupka);
        vlk.setVychod(kameni);
        vlk.setVychod(rekaS);
        vlk.setVychod(les2);

        vesnice.setVychod(les1);
        vesnice.setVychod(pole);

        pole.setVychod(vesnice);
        pole.setVychod(opustenyDum);
        pole.setVychod(most);

        most.setVychod(pole);
        most.setVychod(les2);

        les2.setVychod(most);
        les2.setVychod(vlk);

        kameni.setVychod(rekaJ);
        aktualniProstor = domecek;  // hra začíná v domečku
    }

    /**
     * Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     * @return aktuální prostor
     */

    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     * Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     * @param prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
    }

}
