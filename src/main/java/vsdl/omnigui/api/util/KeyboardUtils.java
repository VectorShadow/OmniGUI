package vsdl.omnigui.api.util;

import static java.awt.event.KeyEvent.*;

public class KeyboardUtils {

    public static boolean andEquals(int value, int mask) {
        return (value & mask) == mask;
    }

    public static int toInt(int keyCode) {
        return toInt(keyCode, false);
    }
    public static int toInt(int keyCode, boolean allowHex) {
        return switch (keyCode) {
            case VK_0 -> 0;
            case VK_1 -> 1;
            case VK_2 -> 2;
            case VK_3 -> 3;
            case VK_4 -> 4;
            case VK_5 -> 5;
            case VK_6 -> 6;
            case VK_7 -> 7;
            case VK_8 -> 8;
            case VK_9 -> 9;
            case VK_A -> allowHex ? 10 : -1;
            case VK_B -> allowHex ? 11 : -1;
            case VK_C -> allowHex ? 12 : -1;
            case VK_D -> allowHex ? 13 : -1;
            case VK_E -> allowHex ? 14 : -1;
            case VK_F -> allowHex ? 15 : -1;
            default -> -1;
        };
    }
    public static Character toChar(int keyCode, int keyMod) {
        return toChar(keyCode, keyMod, true, true, true, true);
    }

    public static Character toChar(int keyCode, int keyMod, boolean isAlpha, boolean isSymbolic, boolean isNumeric, boolean isWhitepace) {
        return switch (keyCode) {
            case VK_A -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'A' : 'a' : null;
            case VK_B -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'B' : 'b' : null;
            case VK_C -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'C' : 'c' : null;
            case VK_D -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'D' : 'd' : null;
            case VK_E -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'E' : 'e' : null;
            case VK_F -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'F' : 'f' : null;
            case VK_G -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'G' : 'g' : null;
            case VK_H -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'H' : 'h' : null;
            case VK_I -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'I' : 'i' : null;
            case VK_J -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'J' : 'j' : null;
            case VK_K -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'K' : 'k' : null;
            case VK_L -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'L' : 'l' : null;
            case VK_M -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'M' : 'm' : null;
            case VK_N -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'N' : 'n' : null;
            case VK_O -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'O' : 'o' : null;
            case VK_P -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'P' : 'p' : null;
            case VK_Q -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'Q' : 'q' : null;
            case VK_R -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'R' : 'r' : null;
            case VK_S -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'S' : 's' : null;
            case VK_T -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'T' : 't' : null;
            case VK_U -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'U' : 'u' : null;
            case VK_V -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'V' : 'v' : null;
            case VK_W -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'W' : 'w' : null;
            case VK_X -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'X' : 'x' : null;
            case VK_Y -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'Y' : 'y' : null;
            case VK_Z -> isAlpha ? andEquals(keyMod, SHIFT_DOWN_MASK) ? 'Z' : 'z' : null;
            case VK_1 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '!' : null : isNumeric ? '1' : null;
            case VK_2 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '@' : null : isNumeric ? '2' : null;
            case VK_3 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '#' : null : isNumeric ? '3' : null;
            case VK_4 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '$' : null : isNumeric ? '4' : null;
            case VK_5 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '%' : null : isNumeric ? '5' : null;
            case VK_6 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '^' : null : isNumeric ? '6' : null;
            case VK_7 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '&' : null : isNumeric ? '7' : null;
            case VK_8 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '*' : null : isNumeric ? '8' : null;
            case VK_9 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? '(' : null : isNumeric ? '9' : null;
            case VK_0 -> andEquals(keyMod, SHIFT_DOWN_MASK) ? isSymbolic ? ')' : null : isNumeric ? '0' : null;
            case VK_PERIOD -> isSymbolic ? andEquals(keyMod, SHIFT_DOWN_MASK) ? '>' : '.' : null;
            case VK_COMMA -> isSymbolic ? andEquals(keyMod, SHIFT_DOWN_MASK) ? '<' : ',' : null;
            case VK_SLASH -> isSymbolic ? andEquals(keyMod, SHIFT_DOWN_MASK) ? '?' : '/' : null;
            case VK_SPACE -> isWhitepace ? ' ' : null;
            default -> null;
        };
    }
}
