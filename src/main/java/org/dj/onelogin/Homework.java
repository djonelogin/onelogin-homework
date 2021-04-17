package org.dj.onelogin;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Homework {
    public static void main(String[] args) {
        Homework homework = new Homework();
        int result;
        result = homework.calculateNum("3+5+2*2");
        System.out.println(result);

//        Fraction res;
//        res = homework.calculate("any");
//        System.out.println(res);
    }

    Map<Character, Integer> procedenceMap = new HashMap<Character, Integer>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
    }};

    public int calculateNum(String s) {
        s.replace("\\(-", "(0-");
        s.replace("\\(\\+", "(0+");

        char[] cs = s.toCharArray();
        int n = s.length();
        Deque<Integer> numStack = new LinkedList<>();
        numStack.addLast(0);
        Deque<Character> opStack = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            char c = cs[i];
            if (Character.isDigit(c)) {
                int u = 0;
                int j = i;
                while (j < n && Character.isDigit(cs[j])) {
                    u = u * 10 + (cs[j] - '0');
                    j++;
                }
                numStack.push(u);
                i = j - 1;
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

    private void calc(Deque<Integer> numStack, Deque<Character> opStack) {
        if (opStack.isEmpty() || numStack.isEmpty() || numStack.size() < 2) {
            return;
        }
        int b = numStack.pop(), a = numStack.pop();
        char op = opStack.pop();

        int ans = 0;
        if (op == '+') {
            ans = a + b;
        } else if (op == '-') {
            ans = a - b;
        } else if (op == '*') {
            ans = a * b;
        } else if (op == '/') {
            ans = a / b;
        }
        numStack.push(ans);
    }

    public Fraction calculate(String input) {
        return new Fraction(3);
    }
}
