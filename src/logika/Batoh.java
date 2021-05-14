package logika;

import java.util.ArrayList;
import java.util.List;

public class Batoh {
    public static final int KAPACITA = 4;
    private int aktualniPocetVeci = 0;
    private List<Vec> veci;


    public Batoh(){
        veci = new ArrayList<>();
    }


    public List<Vec> getVeci() {
        return veci;
    }

    public boolean pridejDoBatohu(Vec vec){
        aktualniPocetVeci++;
        return veci.add(vec);
    }


    public boolean odeberZBatohu(Vec vec){
        aktualniPocetVeci--;
        return veci.remove(vec);
    }

    public boolean odeberZBatohu(String vec){

        for (Vec vec1: veci){
            if (vec1.getNazev().equals(vec)){
                odeberZBatohu(vec1);
                return true;
            }
        }
        return false;
    }
    public Vec vratVec(String nazev){
        for (Vec vec: veci){
            if (vec.getNazev().equals(nazev)){
                return vec;
            }
        }
        return null;
    }

    public int getAktualniPocetVeci() {
        return aktualniPocetVeci;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("věci v batohu: ");
        for (Vec vec: veci){
            stringBuilder.append(vec.getNazev() + ", ");
        }

        return stringBuilder.substring(0, veci.size() == 0? stringBuilder.length() : stringBuilder.length() - 2);
    }

    public boolean obsahujeVec(String nazev){
        for (Vec vec: veci){
            if (vec.getNazev().equals(nazev)){
                return true;
            }
        }
        return false;
    }
}
