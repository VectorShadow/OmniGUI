package vsdl.omnigui.image;

import vsdl.omnigui.image.source.TextImageSourceConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TextImager {

    private static final double FONT_FACTOR = 0.75;

    private static final String DEFAULT_FONT_NAME = Font.DIALOG;
    private static final int DEFAULT_FONT_STYLE = Font.PLAIN;

    private static final String SPC = " ";
    private static final String TAB = "\t";
    private static final String NL = "\n";
    private static final String BLANK = "_";

    private static final String TAB_ALIAS = "    ";

    private static String fontName = DEFAULT_FONT_NAME;
    private static int fontStyle = DEFAULT_FONT_STYLE;

    private static ArrayList<String> tokenize(String input) {
        StringTokenizer stringTokenizer = new StringTokenizer(input, SPC + TAB + NL, true);
        ArrayList<String> out = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            out.add(stringTokenizer.nextToken());
        }
        return out;
    }

    private static JLabel createRenderLabel(int height, Color fg, Color bg) {
        Font font = new Font(fontName, fontStyle, (int)(height * FONT_FACTOR));
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setFont(font);
        label.setBackground(bg);
        label.setForeground(fg);
        return label;
    }

    private static BufferedImage imageWord(String word, int lineHeight, Color fg, Color bg) {
        JLabel label = createRenderLabel(lineHeight, fg, bg);
        FontMetrics fm = label.getFontMetrics(label.getFont());
        int lineWidth = fm.stringWidth(word);
        BufferedImage result = new BufferedImage(lineWidth, lineHeight, BufferedImage.TYPE_INT_RGB);
        label.setSize(lineWidth, lineHeight);
        label.setText(word);
        label.paint(result.getGraphics());
        return result;
    }

    private static void composeImage(String text, int lineHeight, Color fg, Color bg, BufferedImage img) {
        ArrayList<String> allWords = tokenize(text);
        int lineIndex = 0;
        int pixelOffset = 0;
        for (String word : allWords) {
            if (word.equals(NL)) {
                ++lineIndex;
                pixelOffset = 0;
                continue;
            }
            if (word.equals(SPC) && pixelOffset == 0) {
                continue; //ignore leading spaces
            }
            BufferedImage wordImage = imageWord(word.equals(TAB) ? TAB_ALIAS : word, lineHeight, fg, bg);
            if (wordImage.getWidth() > img.getWidth()) {
                throw new IllegalArgumentException(
                        "Individual token " + word + " exceeds image width limit(" + img.getWidth() + ")."
                );
            }
            if (pixelOffset + wordImage.getWidth() > img.getWidth()) {
                ++lineIndex;
                pixelOffset = 0;
            }
            if ((lineIndex + 1) * lineHeight > img.getHeight()) return;
            ImageComposer.superimpose(wordImage, img, new Point(pixelOffset, (lineIndex * lineHeight)));
            pixelOffset += wordImage.getWidth();
        }
    }

    public static Dimension measureText(String text, int lineHeightInPixels) {
        JLabel label = createRenderLabel(lineHeightInPixels, null, null);
        FontMetrics fm = label.getFontMetrics(label.getFont());
        return new Dimension(fm.stringWidth(text), lineHeightInPixels);
    }

    public static Dimension measureEmptyField(int maximumCharacters, int lineHeightInPixels) {
        JLabel label = createRenderLabel(lineHeightInPixels, null, null);
        FontMetrics fm = label.getFontMetrics(label.getFont());
        return new Dimension((fm.stringWidth(BLANK) * maximumCharacters) + lineHeightInPixels, lineHeightInPixels);
    }

    public static BufferedImage imageEmptyField(int maximumCharacters, int lineHeightInPixels, Color fieldColor) {
        return image(
                BLANK.repeat(maximumCharacters),
                new TextImageSourceConfiguration(
                        lineHeightInPixels,
                        new Dimension(measureEmptyField(maximumCharacters, lineHeightInPixels).width, lineHeightInPixels),
                        fieldColor,
                        fieldColor

                )
        );
    }

    public static BufferedImage image(
            String text,
            TextImageSourceConfiguration config
    ) {
        BufferedImage result = new BufferedImage(
                config.getTextAreaDimension().width,
                config.getTextAreaDimension().height,
                BufferedImage.TYPE_INT_RGB
        );
        composeImage(text, config.getLineHeight(), config.getTextForegroundColor(), config.getTextBackgroundColor(), result);
        return result;
    }

    public static BufferedImage imageAsTile(
            char symbol,
            int tileDimension,
            Color foregroundColor,
            Color backgroundColor
    ) {
        JLabel label = createRenderLabel(tileDimension, foregroundColor, backgroundColor);
        label.setText("" + symbol);
        label.setSize(tileDimension, tileDimension);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        BufferedImage result = new BufferedImage(tileDimension, tileDimension, BufferedImage.TYPE_INT_RGB);
        label.paint(result.getGraphics());
        return result;
    }

    public static void resetFontName() {
        setFontName(DEFAULT_FONT_NAME);
    }

    public static void resetFontStyle() {
        setFontStyle(DEFAULT_FONT_STYLE);
    }

    public static void setFontName(String fontName) {
        TextImager.fontName = fontName;
    }

    public static void setFontStyle(int fontStyle) {
        TextImager.fontStyle = fontStyle;
    }
}
