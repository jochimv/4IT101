package logika;

import java.io.Serializable;

public class Hadanka implements Serializable {
    private String odpoved;
    private String zneni;

    public Hadanka(String odpoved, String zneni){
        this.odpoved = odpoved;
        this.zneni = zneni;
    }

    public String getOdpoved() {
        return odpoved;
    }

    public String getZneni() {
        return zneni;
    }
}
