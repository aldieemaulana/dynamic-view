package com.example.fdn_aldi.example;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int presCounter = 0;
    private int maxPresCounter = 0;
    private String[] keys = {"D", "O", "T", "G"};
    private String textAnswer = "DOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keys = shuffleArray(keys);

        for (String key : keys) {
            addButton(((LinearLayout) findViewById(R.id.layoutButton)), key, ((EditText) findViewById(R.id.editText)));
        }

        for (int i=0;i<textAnswer.length();i++) {
            addEditText(((LinearLayout) findViewById(R.id.layoutEdiText)), i);
        }

        maxPresCounter = textAnswer.length();

    }

    private String[] shuffleArray(String[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        return ar;
    }

    private void addButton(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        linearLayoutLayoutParams.rightMargin = 10;
        linearLayoutLayoutParams.leftMargin = 10;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutLayoutParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(this.getResources().getDrawable(R.drawable.shape));
        }else{
            textView.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shape));
        }

        textView.setTextColor(this.getResources().getColor(R.color.colorWhite));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(18);

        textView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
            if(presCounter < maxPresCounter) {
                if (presCounter == 0)
                    editText.setText("");

                editText.setText(editText.getText().toString() + text);
                textView.setEnabled(false);

                ((TextView)findViewById(presCounter)).setText(text);

                presCounter++;

                if(presCounter == maxPresCounter)
                    doValidate();
            }
            }

        });

        viewParent.addView(textView);
    }


    private void addEditText(LinearLayout viewParent, int id) {
        LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(150,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        linearLayoutLayoutParams.rightMargin = 10;
        linearLayoutLayoutParams.leftMargin = 10;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutLayoutParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(this.getResources().getDrawable(R.drawable.shape_edit));
        }else{
            textView.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shape_edit));
        }

        textView.setTextColor(this.getResources().getColor(R.color.colorBlack));
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setText(" ");
        textView.setTextSize(22);
        textView.setId(id);

        viewParent.addView(textView);
    }

    private void doValidate() {
        presCounter = 0;

        EditText editText = findViewById(R.id.editText);
        TextView textResult = findViewById(R.id.textResult);
        LinearLayout linearLayout = findViewById(R.id.layoutButton);
        LinearLayout linearLayoutEdit = findViewById(R.id.layoutEdiText);

        if(editText.getText().toString().equals(textAnswer)) {
            textResult.setText("Betul sekali!");
        }else{
            textResult.setText("Salah coy!");
            editText.setText("");
        }

        keys = shuffleArray(keys);

        linearLayoutEdit.removeAllViews();
        for (int i=0;i<textAnswer.length();i++) {
            addEditText(((LinearLayout) findViewById(R.id.layoutEdiText)), i);
        }

        linearLayout.removeAllViews();
        for (String key : keys) {
            addButton(linearLayout, key, editText);
        }
    }
}
