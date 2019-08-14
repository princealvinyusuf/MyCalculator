package com.intent.mycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String operand1, operand2;
    private static Operation operationType;
    private static boolean operandNew;
    TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_text = findViewById(R.id.tv_text);

        operationType = Operation.NONE;

        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);

        findViewById(R.id.btn_clear).setOnClickListener(this);

        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_multiplication).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String buttonText = ((Button) view).getText().toString();
        String textDisplay = tv_text.getText().toString();
        switch (view.getId()) {
            case R.id.btn_one:
            case R.id.btn_two:
            case R.id.btn_three:
            case R.id.btn_four:
            case R.id.btn_five:
            case R.id.btn_six:
            case R.id.btn_seven:
            case R.id.btn_eight:
            case R.id.btn_nine:
            case R.id.btn_zero:
                if (!operandNew) {

                    if (textDisplay.length() == 1 && textDisplay.equalsIgnoreCase(getString(R.string._0)))
                        tv_text.setText("");

                    tv_text.append(buttonText);
                } else {
                    tv_text.setText(buttonText);
                    operandNew = false;
                }
                break;

            case R.id.btn_dot:
                if (!operandNew) {

                    if (!textDisplay.contains(getString(R.string.dot))) {
                        if (textDisplay.length() == 0)
                            tv_text.append("0" + buttonText);
                        else
                            tv_text.append(buttonText);
                    }

                } else {
                    tv_text.setText("0" + buttonText);
                    operandNew = false;
                }
                break;

            case R.id.btn_clear:
                tv_text.setText("0");
                // Reset all operations and operands
                operand1 = operand2 = null;
                operationType = Operation.NONE;
                operandNew = false;
                break;

            case R.id.btn_minus:
            case R.id.btn_plus:
            case R.id.btn_divide:
            case R.id.btn_multiplication:
                if (!operandNew) {
                    operandNew = true;
                    performOperation(textDisplay);
                }
                if (operationType == Operation.NONE)
                    operand1 = textDisplay;
                setupOperator(buttonText);
                break;

            case R.id.btn_equal:
                if (operationType != Operation.NONE) {
                    performOperation(textDisplay);
                    operand1 = operand2 = null;
                    operationType = Operation.NONE;
                    operandNew = true;
                }
                break;
        }
    }

    private void setupOperator(String buttonText) {
        switch (buttonText) {
            case "+":
                operationType = Operation.ADD;
                break;
            case "-":
                operationType = Operation.SUBTRACT;
                break;
            case "*":
                operationType = Operation.MULTIPLY;
                break;
            case "/":
                operationType = Operation.DIVIDE;
                break;
            default:
                operationType = Operation.NONE;
                break;
        }
    }

    private void performOperation(String text) {
        if (operationType == Operation.NONE) {
            operand1 = text;
        } else {
            double operand_1 = Double.parseDouble(operand1);
            double operand_2 = Double.parseDouble(text);
            if (operationType == Operation.ADD) {
                operand_1 = operand_1 + operand_2;
            } else if (operationType == Operation.SUBTRACT) {
                operand_1 = operand_1 - operand_2;
            } else if (operationType == Operation.MULTIPLY) {
                operand_1 = operand_1 * operand_2;
            } else if (operationType == Operation.DIVIDE) {
                operand_1 = operand_1 / operand_2;
            }
            operand1 = String.valueOf(operand_1);
            operand_1 = operand_2 = Double.NaN;
            tv_text.setText(operand1);
        }
    }

    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, NONE
    }
}
