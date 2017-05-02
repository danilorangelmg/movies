package com.br.movies.view.components;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by danilorangel on 27/04/17.
 */

public class ValueEditText extends EditText {

    private boolean update;

    public ValueEditText(Context context) {
        super(context);
    }

    public ValueEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTextValue(CharSequence text) {
        update = true;
        super.setText(text);
        setSelection(getText().length());

    }

    @Override
    public void addTextChangedListener(final TextWatcher watcher) {
        TextWatcher watcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                watcher.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                watcher.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (update) {
                    update = false;
                    return;
                }
                watcher.afterTextChanged(s);

            }
        };
        super.addTextChangedListener(watcher1);
    }
}
