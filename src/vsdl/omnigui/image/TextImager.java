package vsdl.omnigui.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.StringTokenizer;

//TODO - dynamically discover the appropriate image width for each character! 
// Otherwise kerning will look awful as in early VA...

public class TextImager {

    private static final String DEFAULT_FONT_NAME = Font.DIALOG;
    private static final int DEFAULT_FONT_STYLE = Font.PLAIN;

    public static BufferedImage fromChar(
            char symbol,
            int fontSize,
            Color foreground,
            Color background,
            boolean formatAsText
    ) {
        return fromChar(
                symbol,
                new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, fontSize),
                foreground,
                background,
                formatAsText
        );
    }

    public static BufferedImage fromChar(
            char symbol,
            Font font,
            Color foreground,
            Color background,
            boolean formatAsText
    ) {
        final JLabel L = getRenderLabelBySize(font.getSize(), formatAsText);
        L.setFont(font);
        L.setBackground(background);
        L.setForeground(foreground);
        L.setText("" + symbol);
        final BufferedImage B = new BufferedImage(L.getWidth(), L.getHeight(), BufferedImage.TYPE_INT_RGB);
        L.paint(B.getGraphics());
        return B;
    }

    public static BufferedImage fromString(
            String text,
            int fontSize,
            int maxHeightInPixels,
            int maxWidthInPixels,
            Color foreground,
            Color background
    ) {
        return fromString(
                text,
                new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, fontSize),
                maxHeightInPixels,
                maxWidthInPixels,
                foreground,
                background
        );
    }

    public static BufferedImage fromString(
            String text,
            Font font,
            int maxHeightInPixels,
            int maxWidthInPixels,
            Color foreground,
            Color background
    ) {
        int charHeight = getPixelsByFontSize(font.getSize());
        int charWidth = getFormattedWidth(charHeight);
        int maxCharsPerLine = maxWidthInPixels / charWidth;
        int maxLines = maxHeightInPixels / charHeight;
        if (maxCharsPerLine == 0)
            throw new IllegalArgumentException("Font size is too large for allotted width(" + maxWidthInPixels + ")");
        if (maxLines == 0)
            throw new IllegalArgumentException("Font size is too large for allotted height(" + maxHeightInPixels + ")");
        BufferedImage result = new BufferedImage(
          charWidth * maxCharsPerLine,
          charHeight * maxLines,
          BufferedImage.TYPE_INT_RGB
        );
        ArrayList<String> tokens = tokenize(text);
        int charIndex = 0;
        int lineIndex = 0;
        int tokenIndex = 0;
        String nextToken;
        while (lineIndex < maxLines && tokenIndex < tokens.size()) {
            //get the next token
            nextToken = tokens.get(tokenIndex++);
            //check for tab, convert to four spaces
            if (nextToken.equals("\t")) {
                nextToken = "    ";
            }
            //check for new line
            else if (nextToken.equals("\n")) {
                ++lineIndex;
                continue;
            }
            //check for line wrap
            if (nextToken.length() + charIndex > maxCharsPerLine) {
                charIndex = 0; //reset the charIndex
                ++lineIndex; //increment the lineIndex
                --tokenIndex; //decrement the tokenIndex - we need to parse this token again on the next line.
                continue;
            }
            //image each char and copy it to the result
            for (char c : nextToken.toCharArray()) {
                BufferedImage charImage = fromChar(c, font, foreground, background, true);
                for (int w = 0; w < charWidth; ++w) {
                    for (int h = 0; h < charHeight; ++h) {
                        result.setRGB(
                                (charIndex * charWidth) + w,
                                (lineIndex * charHeight) + h,
                                charImage.getRGB(w, h)
                        );
                    }
                }
                ++charIndex;
            }
        }
        return result;
    }

    private static ArrayList<String> tokenize(String s) {
        StringTokenizer stringTokenizer = new StringTokenizer(s, " \t\n", true);
        ArrayList<String> out = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            out.add(stringTokenizer.nextToken());
        }
        return out;
    }

    private static int getPixelsByFontSize(int fontSize) {
        return (int)((double)fontSize * (4.0 / 3.0));
    }

    private static int getFormattedWidth(int heightInPixels) {
        return (int)((double)heightInPixels * (9.0 / 16.0));
    }

    private static JLabel getRenderLabelBySize(int fontSize, boolean formatAsText) {
        JLabel ret = new JLabel();
        ret.setHorizontalAlignment(SwingConstants.CENTER);
        ret.setOpaque(true);
        int h = getPixelsByFontSize(fontSize);
        int w = formatAsText ? getFormattedWidth(h) : h;
        ret.setSize(w, h);
        return ret;
    }
}
