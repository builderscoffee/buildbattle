package eu.builderscoffee.expresso.utils.blocks;

import lombok.Getter;

import java.util.Arrays;

public class LogFacing {

    /***
     * Retourne le LogType via le shortId(Data) d'un block
     * @param shortId
     * @return
     */
    public static LogType getLogTypeByShort(int shortId) {
        return Arrays.stream(LogType.values())
                .findFirst()
                .filter(logType -> logType.getShortIdFacingWestEast() == shortId || logType.getShortIdFacingNorthSouth() == shortId)
                .get();
    }

    /***
     * On récupere l'ancienne data du block poser & la nouvelle data du block à poser
     * On converti l'ancienne data dans le nouveaux type de block en indiquant la BlockFace
     * du nouveaux block et on la retourne
     * @param shortId
     * @return
     */
    public static int ConvertLogType(int shortId) {

        return 0;
    }

    public enum LogType {
        OAK(0, 4, 8),
        SPRUCE(1, 5, 9),
        BIRCH(2, 6, 10),
        JUNGLE(3, 7, 11);

        @Getter
        public int Id;
        @Getter
        public int shortIdFacingWestEast;
        @Getter
        public int shortIdFacingNorthSouth;

        LogType(int id, int shortIdFacingWestEast, int shortIdFacingNorthSouth) {
            this.Id = id;
            this.shortIdFacingWestEast = shortIdFacingWestEast;
            this.shortIdFacingNorthSouth = shortIdFacingNorthSouth;
        }
    }
}
