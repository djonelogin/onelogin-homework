package org.dj.onelogin;

import java.util.Objects;

public class Fraction {

    public static Fraction ZERO = new Fraction(0, 0, 1);

    public String str;
    public int whole;
    public int numerator;
    public int denominator;

    public Fraction(String str) {
        this.str = str;
    }

    public Fraction(int whole, int numerator, int denominator) {
        this.whole = whole;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Fraction from(int numerator, int denominator) {
        int whole = numerator / denominator;
        numerator %= denominator;

        int gcd = getGCD(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (numerator % denominator == 0) {
            denominator = 1;
        }

        return new Fraction(whole, numerator, denominator);
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
        int num = lcm / this.denominator * (this.whole * this.denominator + this.numerator)
                +
                lcm / other.denominator * (other.whole * other.denominator + other.numerator);
        int denom = lcm;
        return Fraction.from(num, denom);
    }

    public Fraction sub(Fraction other) {
        int lcm = getLCM(this.denominator, other.denominator);
        int num = lcm / this.denominator * (this.whole * this.denominator + this.numerator)
                -
                lcm / other.denominator * (other.whole * other.denominator + other.numerator);
        int denom = lcm;
        return Fraction.from(num, denom);
    }

    public Fraction mul(Fraction other) {
        int num = (this.whole * this.denominator + this.numerator)
                *
                (other.whole * other.denominator + other.numerator);
        int denom = this.denominator * other.denominator;
        return Fraction.from(num, denom);
    }

    public Fraction div(Fraction other) {
        int num = other.denominator * (this.whole * this.denominator + this.numerator);
        int denom = this.denominator * (other.whole * other.denominator + other.numerator);
        return Fraction.from(num, denom);
    }

    @Override
    public String toString() {
        return "Fraction{" +
                "str='" + str + '\'' +
                ", whole=" + whole +
                ", numerator=" + numerator +
                ", denominator=" + denominator +
                '}';
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
