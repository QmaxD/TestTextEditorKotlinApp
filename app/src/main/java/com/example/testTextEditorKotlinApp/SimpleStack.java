package com.example.testTextEditorKotlinApp;

import java.util.Stack;

public class SimpleStack {
    static Stack<TextInfo> stack = new Stack();

    public static TextInfo getLastElement () {
        if (!stack.empty()) {
            TextInfo last = stack.pop();
            return last;
        }
        return new TextInfo("Список пуст!", -1, -1);
    }

    public static void addElement (TextInfo newElement) {
        stack.addElement(newElement);
    }

    public static void clearAll () {
        stack.clear();
    }

}
