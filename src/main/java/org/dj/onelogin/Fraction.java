package org.dj.onelogin;

import java.util.Objects;

public class Fraction {
    public static Fraction ZERO = new Fraction(0, 0, 1);

    public int whole;
    public int numerator;
    public int denominator;

    public Fraction(int numerator, int denominator) {
        this(0, numerator, denominator);
    }

    public Fraction(int whole, int numerator, int denominator) {
        this.whole = whole;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    private Fraction simplify() {
        whole += numerator / denominator;
        numerator %= denominator;

        int gcd = getGCD(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (numerator % denominator == 0) {
            denominator = 1;
        }

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
        int num = lcm / this.denominator * getImproperNumerator(this)
                + lcm / other.denominator * getImproperNumerator(other);
        int denom = lcm;
        return new Fraction(num, denom).simplify();
    }

    private int getImproperNumerator(Fraction other) {
        return other.whole * other.denominator + other.numerator;
    }

    public Fraction sub(Fraction other) {
        int lcm = getLCM(this.denominator, other.denominator);
        int num = lcm / this.denominator * getImproperNumerator(this)
                - lcm / other.denominator * getImproperNumerator(other);
        int denom = lcm;
        return new Fraction(num, denom).simplify();
    }

    public Fraction mul(Fraction other) {
        int num = getImproperNumerator(this) * getImproperNumerator(other);
        int denom = this.denominator * other.denominator;
        return new Fraction(num, denom).simplify();
    }

    public Fraction div(Fraction other) {
        int num = other.denominator * getImproperNumerator(this);
        int denom = this.denominator * getImproperNumerator(other);
        return new Fraction(num, denom).simplify();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (numerator != 0) {
            sb.append(numerator).append('/').append(denominator);
        }

        if (whole != 0) {
            if (numerator != 0) {
                sb.insert(0, '_');
            }
            sb.insert(0, whole);
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
        return whole == fraction.whole && numerator == fraction.numerator && denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(whole, numerator, denominator);
    }
}
