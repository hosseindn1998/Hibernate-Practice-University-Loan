package Model;

import java.util.EnumSet;

public enum City {
    TEHRAN,
    RASHT, ESFAHAN, TABRIZ, SHIRAZ, AHWAZ, QOM, MASHHAD, KARAJ,
    Other;
    public static final EnumSet<City>level1= EnumSet.of(TEHRAN);
    public static final EnumSet<City>level2= EnumSet.of(RASHT, ESFAHAN, TABRIZ, SHIRAZ, AHWAZ, QOM, MASHHAD, KARAJ);


}
