package com.example.mycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView solutionTv, resultTv;
    MaterialButton btnC, btnBrackOpen, btnBrackClose,
                   btnDiv, btnMult, btnPlus, btnMinus, btnEquals,
                   btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
                   btnAC, btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(btnC, R.id.button_c);
        assignId(btnBrackOpen, R.id.button_open_bracket);
        assignId(btnBrackClose, R.id.button_close_bracket);

        assignId(btnDiv, R.id.button_divide);
        assignId(btnMult, R.id.button_multiply);
        assignId(btnPlus, R.id.button_add);
        assignId(btnMinus, R.id.button_minus);
        assignId(btnEquals, R.id.button_equals);

        assignId(btn0, R.id.button_0);
        assignId(btn1, R.id.button_1);
        assignId(btn2, R.id.button_2);
        assignId(btn3, R.id.button_3);
        assignId(btn4, R.id.button_4);
        assignId(btn5, R.id.button_5);
        assignId(btn6, R.id.button_6);
        assignId(btn7, R.id.button_7);
        assignId(btn8, R.id.button_8);
        assignId(btn9, R.id.button_9);

        assignId(btnAC, R.id.button_ac);
        assignId(btnDot, R.id.button_dot);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton btn = (MaterialButton) view;
        String btnTxt = btn.getText().toString();

        if (btnTxt.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (btnTxt.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }

        String dataToCalculate = solutionTv.getText().toString();

        if (btnTxt.equals("c")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        } else {
            dataToCalculate = dataToCalculate + btnTxt;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Error")) {
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }
}