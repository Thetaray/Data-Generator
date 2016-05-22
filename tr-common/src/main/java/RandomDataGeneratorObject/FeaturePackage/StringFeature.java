package RandomDataGeneratorObject.FeaturePackage;
/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 15/5/16
 */

import RandomDataGeneratorObject.Data;

public class StringFeature implements Feature {

    int length = 10;
    boolean withLowerLetter = true;
    boolean withUpperLetter = true;
    boolean withSpace = true;
    boolean withDigit = true;
    private char[] symbols;
    private char[] buf = new char[length];

    public StringFeature() {
        init();
    }

    private void init() {
        StringBuilder tmp = new StringBuilder();
        if (withDigit)
            for (char ch = '0'; ch <= '9'; ++ch)
                tmp.append(ch);
        if (withLowerLetter)
            for (char ch = 'a'; ch <= 'z'; ++ch)
                tmp.append(ch);
        if (withUpperLetter)
            for (char ch = 'A'; ch <= 'Z'; ++ch)
                tmp.append(ch);
        if (withSpace)
            tmp.append(' ');
        symbols = tmp.toString().toCharArray();

    }

    @Override
    public Object NextValue() throws Exception {
        for (int idx = 0; idx < buf.length; idx++)
            buf[idx] = symbols[Data.getRandom().nextInt(0, symbols.length - 1)];
        return new String(buf);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
        buf = new char[length];
    }

    public boolean isWithLowerLetter() {
        return withLowerLetter;
    }

    public void setWithLowerLetter(boolean withLowerLetter) {
        this.withLowerLetter = withLowerLetter;
    }

    public boolean isWithUpperLetter() {
        return withUpperLetter;
    }

    public void setWithUpperLetter(boolean withUpperLetter) {
        this.withUpperLetter = withUpperLetter;
    }

    public boolean isWithSpace() {
        return withSpace;
    }

    public void setWithSpace(boolean withSpace) {
        this.withSpace = withSpace;
    }

    public boolean isWithDigit() {
        return withDigit;
    }

    public void setWithDigit(boolean withDigit) {
        this.withDigit = withDigit;
    }
}