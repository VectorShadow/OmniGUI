package vsdl.omnigui.image;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public class InputEventUtils {

    public static boolean hasModifier(int mask, KeyEvent e) {
        return (e.getModifiersEx() & mask) == mask;
    }

    public static boolean isAlphaNumeric(KeyEvent e) {
        return toChar(e) != null;
    }

    public static Character toChar(KeyEvent e) {
        int code = e.getExtendedKeyCode();
        boolean isShift = hasModifier(SHIFT_DOWN_MASK, e);
        switch (code) {
            case VK_0: return isShift ? ')' : '0';
            case VK_1: return isShift ? '!' : '1';
            case VK_2: return isShift ? '@' : '2';
            case VK_3: return isShift ? '#' : '3';
            case VK_4: return isShift ? '$' : '4';
            case VK_5: return isShift ? '%' : '5';
            case VK_6: return isShift ? '^' : '6';
            case VK_7: return isShift ? '&' : '7';
            case VK_8: return isShift ? '*' : '8';
            case VK_9: return isShift ? '(' : '9';
            case VK_NUMPAD0: return '0';
            case VK_NUMPAD1: return '1';
            case VK_NUMPAD2: return '2';
            case VK_NUMPAD3: return '3';
            case VK_NUMPAD4: return '4';
            case VK_NUMPAD5: return '5';
            case VK_NUMPAD6: return '6';
            case VK_NUMPAD7: return '7';
            case VK_NUMPAD8: return '8';
            case VK_NUMPAD9: return '9';
            case VK_A: return isShift ? 'A' : 'a';
            case VK_B: return isShift ? 'B' : 'b';
            case VK_C: return isShift ? 'C' : 'c';
            case VK_D: return isShift ? 'D' : 'd';
            case VK_E: return isShift ? 'E' : 'e';
            case VK_F: return isShift ? 'F' : 'f';
            case VK_G: return isShift ? 'G' : 'g';
            case VK_H: return isShift ? 'H' : 'h';
            case VK_I: return isShift ? 'I' : 'i';
            case VK_J: return isShift ? 'J' : 'j';
            case VK_K: return isShift ? 'K' : 'k';
            case VK_L: return isShift ? 'L' : 'l';
            case VK_M: return isShift ? 'M' : 'm';
            case VK_N: return isShift ? 'N' : 'n';
            case VK_O: return isShift ? 'O' : 'o';
            case VK_P: return isShift ? 'P' : 'p';
            case VK_Q: return isShift ? 'Q' : 'q';
            case VK_R: return isShift ? 'R' : 'r';
            case VK_S: return isShift ? 'S' : 's';
            case VK_T: return isShift ? 'T' : 't';
            case VK_U: return isShift ? 'U' : 'u';
            case VK_V: return isShift ? 'V' : 'v';
            case VK_W: return isShift ? 'W' : 'w';
            case VK_X: return isShift ? 'X' : 'x';
            case VK_Y: return isShift ? 'Y' : 'y';
            case VK_Z: return isShift ? 'Z' : 'z';
            case VK_SPACE: return ' ';
            default: return null;
        }
    }
}
