/*
 * Copyright (c)  2017  Francisco José Montiel Navarro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.franmontiel.localechanger.sample;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.franmontiel.localechanger.LocaleChanger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.franmontiel.localechanger.sample.MyApplication.SUPPORTED_LOCALES;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.localeSpinner)
    Spinner localeSpinner;

    @BindView(R.id.currentLocale)
    TextView currentLocale;

    @BindView(R.id.date)
    TextView date;

    @OnClick(R.id.localeUpdate)
    void onUpdateLocaleClick() {
        LocaleChanger.setLocale((Locale) localeSpinner.getSelectedItem());
        recreate();
    }

    @OnClick(R.id.showDatePicker)
    void onShowDatePickerClick() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    }
                }, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ArrayAdapter<Locale> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                SUPPORTED_LOCALES);
        localeSpinner.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        currentLocale.setText(getString(R.string.current_locale, Locale.getDefault()));
        date.setText(SimpleDateFormat.getDateInstance(DateFormat.LONG).format(new Date()));
    }
}
