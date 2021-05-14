package logika;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProstorTest {

    @Test
    public  void testLzeProjit() {
        Prostor prostor1 = new Prostor("hala", "vstupní hala budovy VŠE na Jižním městě", null);
        Prostor prostor2 = new Prostor("bufet", "bufet, kam si můžete zajít na svačinku", null);
        prostor1.setVychod(prostor2);
        prostor2.setVychod(prostor1);
        assertEquals(prostor2, prostor1.vratSousedniProstor("bufet"));
        assertEquals(null, prostor2.vratSousedniProstor("pokoj"));
    }

}