package com.example.testTextEditorKotlinApp;

public class TextInfo {
    String text;
    int amountSymbols;
    int amountWords;

    public TextInfo(String text, int amountSymbols, int amountWords) {
        this.text = text;
        this.amountSymbols = amountSymbols;
        this.amountWords = amountWords;
    }

    public String getText() {
        return text;
    }

    public int getAmountSymbols() {
        return amountSymbols;
    }

    public int getAmountWords() {
        return amountWords;
    }

}
