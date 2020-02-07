package edu.fje.dam2.abel.swappuzzle;

public class ComprovaPosicions {

    public static boolean comprovaEsquerra(Integer pos, Integer buit) {

        if (pos != 0 && pos != 3 && pos != 6) {

            if (pos - 1 == buit) {
                return true;
            } else
                return false;
        } else {
            return false;
        }

    }

    public static boolean comprovaDreta(Integer pos, Integer buit) {

        if (pos != 2 && pos != 5 && pos != 8) {

            if (pos + 1 == buit) {
                return true;
            } else
                return false;
        } else {
            return false;
        }

    }

    public static boolean comprovaAmunt(Integer pos, Integer buit) {

        if (pos != 0 && pos != 1 && pos != 2) {

            if (pos - 3 == buit) {
                return true;
            } else
                return false;
        } else {
            return false;
        }

    }

    public static boolean comprovaAvall(Integer pos, Integer buit) {

        if (pos != 6 && pos != 7 && pos != 8) {

            if (pos + 3 == buit) {
                return true;
            } else
                return false;
        } else {
            return false;
        }

    }
}
