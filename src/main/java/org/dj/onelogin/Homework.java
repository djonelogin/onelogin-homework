package org.dj.onelogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Homework {
    public static void main(String[] args) throws IOException {
        Homework homework = new Homework();

        test(homework);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("? ");
            String tx = br.readLine();
            System.out.println("= " + homework.calculate(tx));
        }
    }

    private static void test(Homework homework) {
        Fraction res;

        res = homework.calculate("");
        assert ("0".equals(res.toString()));
        res = homework.calculate(" -1/2  ");
        assert ("-1/2".equals(res.toString()));
        res = homework.calculate(" -1/2 * 3  ");
        assert ("-1_1/2".equals(res.toString()));
        res = homework.calculate(" 1/2 + 3/4  ");
        assert ("1_1/4".equals(res.toString()));
        res = homework.calculate("3_1/2 + 1/2");
        assert ("4".equals(res.toString()));
        res = homework.calculate("-1/2 + 2/3");
        assert ("1/6".equals(res.toString()));
        res = homework.calculate("1/2 * 3_3/4");
        assert ("1_7/8".equals(res.toString()));
        res = homework.calculate("2_3/8 + 9/8");
        assert ("3_1/2".equals(res.toString()));
        res = homework.calculate("1/2 / 3/8");
        assert ("1_1/3".equals(res.toString()));
        res = homework.calculate("3_1/2 / 2_4/9");
        assert ("1_19/44".equals(res.toString()));
        res = homework.calculate(" 1/2 + 3/4  * 2 ");
        assert ("2".equals(res.toString()));
        res = homework.calculate(" 1/2 / 1/3");
        assert ("1_1/2".equals(res.toString()));
        res = homework.calculate(" 1/2 / 1/3 + 4/9  * 2 ");
        assert ("2_7/18".equals(res.toString()));
        res = homework.calculate(" 1/2 + 1/3 * 4/9  - 2 ");
        assert ("-1_19/54".equals(res.toString()));
        res = homework.calculate("1/2 * 8");
        assert ("4".equals(res.toString()));
        res = homework.calculate("1_1/5 / 3");
        assert ("2/5".equals(res.toString()));
        res = homework.calculate("2_1/5 - 1/5");
        assert ("2".equals(res.toString()));
    }

    Map<Character, Integer> procedenceMap = new HashMap<Character, Integer>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
    }};

    public Fraction calculate(String s) {
        s = s.trim();

        char[] cs = s.toCharArray();
        int n = s.length();
        Deque<Fraction> numStack = new LinkedList<>();
        numStack.push(Fraction.ZERO);
        Deque<Character> opStack = new LinkedList<>();

        for (int i = 0; i >= 0 && i < n; i++) {
            char c = cs[i];
            if (Character.isDigit(c)) { // push a fraction
                int end = s.indexOf(' ', i);
                if (end == -1) {
                    end = n;
                }
                numStack.push(Fraction.parseFraction(cs, i, end));
                i = end - 1;
            } else if (c == ' ') {
                continue;
            } else { // handle math operator
                while (!opStack.isEmpty()) {
                    char prevOp = opStack.peek();
                    if (!shouldCalculate(c, prevOp)) {
                        break;
                    }

                    calc(numStack, opStack);
                }
                opStack.push(c);
            }
        }

        // finish calc if there's remaining
        while (!opStack.isEmpty()) {
            calc(numStack, opStack);
        }

        return numStack.peek();
    }

    private boolean shouldCalculate(char c, char prevOp) {
        return procedenceMap.get(prevOp) >= procedenceMap.get(c);
    }

    private void calc(Deque<Fraction> numStack, Deque<Character> opStack) {
        if (opStack.isEmpty() || numStack.isEmpty() || numStack.size() < 2) {
            return;
        }
        Fraction b = numStack.pop(), a = numStack.pop();
        char op = opStack.pop();

        if (op == '+') {
            numStack.push(a.add(b));
        } else if (op == '-') {
            numStack.push(a.sub(b));
        } else if (op == '*') {
            numStack.push(a.mul(b));
        } else if (op == '/') {
            numStack.push(a.div(b));
        }
    }
}
