package sk.uniza.fri.nacitavanie;

public enum TypVrchola {
    NESPECIFIKOVANY(0),
    ZAKAZNIK(1),
    STREDISKO(2);

    private final int cislo;

    TypVrchola(int cislo) {
        this.cislo = cislo;
    }

    public int getCislo() {
        return cislo;
    }

    public static TypVrchola zCisla(int cislo) {
        for (TypVrchola typ : values()) {
            if (typ.getCislo() == cislo) {
                return typ;
            }
        }
        return NESPECIFIKOVANY;
    }
}
