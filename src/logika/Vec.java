package logika;

public class Vec {
    private String nazev;
    private boolean prenositelna;


    public boolean isPrenositelna() {
        return prenositelna;
    }

    public Vec(String nazev, boolean prenositelna) {
        this.prenositelna = prenositelna;
        this.nazev = nazev;
    }


    public String getNazev(){
        return nazev;
    }

}
