package com.example.fdn_aldi.example;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int presCounter = 0;
    private int lifeCounter = 3;
    private int maxPresCounter = 0;
    private char[] keys;
    private String textAnswer;
    private int[] life = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        life = new int[] {R.id.life1, R.id.life2, R.id.life3};
        setQuestion("Dog");

    }

    private void setQuestion(String answer) {
        LinearLayout linearLayout = findViewById(R.id.layoutButton);
        LinearLayout linearLayoutEdit = findViewById(R.id.layoutEdiText);

        linearLayout.removeAllViews();
        linearLayoutEdit.removeAllViews();

        textAnswer = answer.toUpperCase();

        maxPresCounter = textAnswer.length();
        keys = (textAnswer + (char)(new Random().nextInt(25) + 'A') + (char)(new Random().nextInt(25) + 'A')).toUpperCase().toCharArray();
        keys = shuffleArray(keys);

        for (char key : keys) {
            addButton(linearLayout, key, ((EditText) findViewById(R.id.editText)));
        }

        for (int i=0;i<textAnswer.length();i++) {
            addEditText(linearLayoutEdit, i);
        }
    }

    private char[] shuffleArray(char[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            char a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        return ar;
    }

    private void addButton(LinearLayout viewParent, final char text, final EditText editText) {
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
        textView.setText(String.valueOf(text));
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(18);

        textView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
            if(presCounter < maxPresCounter) {
                if (presCounter == 0) {
                    editText.setText("");
                }

                editText.setText(editText.getText().toString() + String.valueOf(text));
                textView.setEnabled(false);

                ((TextView)findViewById(presCounter)).setText(String.valueOf(text));

                ++presCounter;

                if(presCounter == maxPresCounter) {
                    doValidate();
                }
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

        if(editText.getText().toString().equals(textAnswer)) {
            textResult.setText("Betul sekali!");
            setQuestion("Cat");
        }else{
            textResult.setText("Salah coy!");
            editText.setText("");

            --lifeCounter;
            findViewById(life[lifeCounter]).setVisibility(View.GONE);

            if(lifeCounter == 0) {
                textResult.setText("Mati coy!");
                doReset();
            }else{
                setQuestion("Dog");
            }
        }

    }

    private void doReset() {
        lifeCounter = 3;
        setQuestion("Cat");
        for (int i: life) {
            findViewById(i).setVisibility(View.VISIBLE);
        }
    }
}
