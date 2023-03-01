package com.example.testTextEditorKotlinApp

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var id: Int = 1
        var db: SQLiteDatabase = getBaseContext().openOrCreateDatabase("textDB.db", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS texts (textValue TEXT, amountSymbols INTEGER, amountWords INTEGER)");
        db.execSQL("INSERT INTO texts (textValue, amountSymbols, amountWords) VALUES ('Tom', 3, 1)")

        // объявление полей
        val info = findViewById<TextView>(R.id.textViewInfo)
        val text = findViewById<EditText>(R.id.editTextStringValue)
        var textChange: String = "";// для хранения предыдущего значения
        val amountSymbols = findViewById<TextView>(R.id.textViewAmountSymbolsValue)
        val amountWords = findViewById<TextView>(R.id.textViewAmountWordsValue)

        // объявление кнопок
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        val buttonInsert = findViewById<Button>(R.id.buttonInsert)

        buttonSave.setOnClickListener() {
            if (!text.text.toString().isEmpty()) {
                outputTextInfo(
                    text.text.toString(),
                    amountSymbols.text.toString().toInt(),
                    amountWords.text.toString().toInt(),
                    db, id )
                info.setText("Добавлено")
                id++
            } else
                info.setText("Поле текста пустое")
        }// сохранить текст

        buttonClear.setOnClickListener() {
            text.setText("")
            amountSymbols.setText("0")
            amountWords.setText("0")
            //SimpleStack.clearAll()
            info.setText("Очищено")
        }// стереть текст

        buttonInsert.setOnClickListener() {
            val input : ArrayList<String> = inputTextInfo(db)
            text.setText(input[0])
            amountSymbols.setText(input[1])
            amountWords.setText(input[2])
            info.setText("Возвращено")
        }// вставить последний сохраненный текст

        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            // что-то делаем после изменения
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // что-то делаем до изменения
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // что-то делаем при изменении // будем проверять изменилось ли значение, а именно текст
                if (!textChange.equals(s.toString())) {
                    textChange = s.toString()
                    amountSymbols.setText(textChange.length.toString())
                    amountWords.setText(countWords(textChange).toString())
                }
            }
        })// следим за изменениями в поле

    }

    private fun outputTextInfo(text: String, amountSymbols: Int, amountWords: Int, db: SQLiteDatabase, id: Int) {
        SimpleStack.addElement(TextInfo(text, amountSymbols, amountWords))

        //val query: Cursor = db.rawQuery("INSERT INTO texts (textValue, amountSymbols, amountWords) VALUES ('Tom2', 4, 1)", null)
        //query.close()
        //db.close()
    }// данные в базу

    private fun inputTextInfo(db: SQLiteDatabase): ArrayList<String> {
        val textInfo: TextInfo = SimpleStack.getLastElement()
        val text: String = textInfo.getText()
        val amountSymbols: String = textInfo.getAmountSymbols().toString()
        val amountWords: String = textInfo.getAmountWords().toString()

        val result : ArrayList<String> = arrayListOf()
        result.add(text)
        result.add(amountSymbols)
        result.add(amountWords)

        val query: Cursor = db.rawQuery("SELECT textValue, amountSymbols, amountWords FROM texts", null)
        while (query.moveToNext()) {
            val textV = query.getString(0)
            val sbls = query.getInt(1)
            val wrds = query.getInt(2)
            println(textV + " " + sbls + " " + wrds)
        }
        query.close()
        //db.close()

        return result;
    }// данные из базы

    private fun countWords(text: String): Int {
        var n: Int = 1
        for(i in 0..text.length - 2) {
            if (text[i] == ' ' && text[i + 1] == ' ')
                continue
            if (text[i] == ' ' && text[i + 1] != ' ')
                n++
        }
        return n
    }// количество слов в поле (в т.ч. несколько пробелов)

}