package io.github.xdgs.xdgscommons.api.utils;

public class HexUtils {
    public static String nToHex(int n) {
        return "#" + Integer.toHexString(n);
    }

    public static String rgbToHex(int r, int g, int b) {
        return nToHex(r) +
                (nToHex(g).replace("#", "")) +
                (nToHex(b).replace("#", ""));
    }

    public static int hexToInt(String hex) {
        return Integer.valueOf(hex, 16);
    }

    public static int[] hexToRGB(String hex) {
        hex = hex.replace("#", "");
        String rs = hex.substring(0, 2);
        String gs = hex.substring(2, 4);
        String bs = hex.substring(4, 6);
        int r = hexToInt(rs);
        int g = hexToInt(gs);
        int b = hexToInt(bs);
        return new int[]{r, g, b};
    }
}
