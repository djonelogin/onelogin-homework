package org.dj.onelogin;

import java.util.Objects;

import static java.lang.Math.*;

public class Fraction {
    public static final Fraction ZERO = new Fraction(0, 1);

    public int numerator;
    public int denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Fraction parseFraction(char[] cs, int start, int end) {
        int whole = 0, numerator = 0, denominator = 0;
        int num = 0;
        boolean isFraction = false;
        for (int i = start; i < end; i++) {
            char c = cs[i];
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '/') {
                isFraction = true;

                numerator = num;
                num = 0;
            } else if (c == '_') {
                whole = num;
                num = 0;
            }
        }

        if (isFraction) { // in case: "2"
            denominator = num;
        } else {
            whole = num;
            numerator = 0;
            denominator = 1;
        }

        numerator = whole * denominator + numerator;
        return new Fraction(numerator, denominator);
    }

    private Fraction simplify() {
        int gcd = getGCD(abs(numerator), abs(denominator));
        numerator /= gcd;
        denominator /= gcd;

        return this;
    }

    private static int getGCD(int a, int b) {
        if (a == 0) {
            return b;
        }
        return getGCD(b % a, a);
    }

    private int getLCM(int a, int b) {
        return (a / getGCD(a, b)) * b;
    }

    public Fraction add(Fraction other) {
        int lcm = getLCM(this.denominator, other.denominator);
        int num = lcm / this.denominator * this.numerator
                + lcm / other.denominator * other.numerator;
        int denom = lcm;
        return new Fraction(num, denom).simplify();
    }

    public Fraction sub(Fraction other) {
        int lcm = getLCM(this.denominator, other.denominator);
        int num = lcm / this.denominator * this.numerator
                - lcm / other.denominator * other.numerator;
        int denom = lcm;
        return new Fraction(num, denom).simplify();
    }

    public Fraction mul(Fraction other) {
        int num = this.numerator * other.numerator;
        int denom = this.denominator * other.denominator;
        return new Fraction(num, denom).simplify();
    }

    public Fraction div(Fraction other) {
        int num = other.denominator * this.numerator;
        int denom = this.denominator * other.numerator;
        return new Fraction(num, denom).simplify();
    }

    @Override
    public String toString() {
        int whole = abs(numerator) / denominator;
        int num = abs(numerator) % denominator;

        StringBuffer sb = new StringBuffer();
        if (num != 0) {
            sb.append(num).append('/').append(denominator);
        }

        if (whole != 0) {
            if (num != 0) {
                sb.insert(0, '_');
            }
            sb.insert(0, whole);
        }
        if (numerator < 0) {
            sb.insert(0, '-');
        }

        if (sb.length() == 0) {
            sb.append(0);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return numerator == fraction.numerator && denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}
