package org.dj.onelogin;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Homework {
    public static void main(String[] args) {
        Homework homework = new Homework();

//        test(homework);
    }

    private static void test(Homework homework) {
        Fraction res;
        res = homework.calculateNum(" 1/2 + 3/4  ");
        assert(new Fraction(1, 1, 4).equals(res));
        res = homework.calculateNum("3_1/2 + 1/2");
        assert(new Fraction(4, 0, 1).equals(res));
        res = homework.calculateNum("-1/2 + 2/3");
        assert(new Fraction(0, 1, 6).equals(res));
        res = homework.calculateNum("1/2 * 3_3/4");
        assert(new Fraction(1, 7, 8).equals(res));
        res = homework.calculateNum("2_3/8 + 9/8");
        assert(new Fraction(3, 1, 2).equals(res));
        res = homework.calculateNum("1/2 / 3/8");
        assert(new Fraction(1, 1, 3).equals(res));
        res = homework.calculateNum("3_1/2 / 2_4/9");
        assert(new Fraction(1, 19, 44).equals(res));
    }

    Map<Character, Integer> procedenceMap = new HashMap<Character, Integer>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
    }};

    public Fraction calculateNum(String s) {
        s = s.trim();
        s.replace("\\(-", "(0-");
        s.replace("\\(\\+", "(0+");

        char[] cs = s.toCharArray();
        int n = s.length();
        Deque<Fraction> numStack = new LinkedList<>();
        numStack.push(Fraction.ZERO);
        Deque<Character> opStack = new LinkedList<>();

        for (int i = 0; i >= 0 && i < n; i++) {
            char c = cs[i];
            if (Character.isDigit(c)) {
                int end = s.indexOf(' ', i);
                if (end == -1) {
                    end = n;
                }
                Fraction fr = parseFraction(cs, i, end);
                numStack.push(fr);
                i = end - 1;
            } else if (c == ' ') {
                continue;
            } else {
                while (!opStack.isEmpty()) {
                    char prevOp = opStack.peek();
                    if (procedenceMap.get(prevOp) >= procedenceMap.get(c)) {
                        calc(numStack, opStack);
                    } else {
                        break;
                    }
                }
                opStack.push(c);
            }
        }

        while (!opStack.isEmpty()) {
            calc(numStack, opStack);
        }

        return numStack.peek();
    }

    private Fraction parseFraction(String s) {
        return parseFraction(s.toCharArray(), 0, s.length());
    }

    private Fraction parseFraction(char[] cs, int start, int end) {
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

        if (isFraction) {
            denominator = num;
        } else {
            whole = num;
            numerator = 0;
            denominator = 1;
        }

        return new Fraction(whole, numerator, denominator);
    }

    private void calc(Deque<Fraction> numStack, Deque<Character> opStack) {
        if (opStack.isEmpty() || numStack.isEmpty() || numStack.size() < 2) {
            return;
        }
        Fraction b = numStack.pop(), a = numStack.pop();
        char op = opStack.pop();

        Fraction ans = null;
        if (op == '+') {
            ans = a.add(b);
        } else if (op == '-') {
            ans = a.sub(b);
        } else if (op == '*') {
            ans = a.mul(b);
        } else if (op == '/') {
            ans = a.div(b);
        }
        numStack.push(ans);
    }
}
