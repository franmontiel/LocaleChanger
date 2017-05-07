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
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.franmontiel.localechanger.ActivityRecreationHelper;
import com.franmontiel.localechanger.LocaleChanger;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.franmontiel.localechanger.sample.SampleApplication.SUPPORTED_LOCALES;

public class SampleActivity extends AppCompatActivity {

    @BindView(R.id.localeSpinner)
    Spinner localeSpinner;
    @BindView(R.id.currentLocale)
    TextView currentLocale;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.openNewScreen)
    Button openNewScreen;

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_sample);

        ButterKnife.bind(this);

        ArrayAdapter<Locale> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                SUPPORTED_LOCALES);
        localeSpinner.setAdapter(adapter);

        openNewScreen.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityRecreationHelper.onResume(this);

        currentLocale.setText(Locale.getDefault().toString());
        date.setText(DateProvider.provideSystemLocaleFormattedDate());
    }

    @Override
    protected void onDestroy() {
        ActivityRecreationHelper.onDestroy(this);
        super.onDestroy();
    }

    @OnClick(R.id.localeUpdate)
    void onUpdateLocaleClick() {
        LocaleChanger.setLocale((Locale) localeSpinner.getSelectedItem());
        ActivityRecreationHelper.recreate(this, true);
    }

    @OnClick(R.id.showDatePicker)
    void onShowDatePickerClick() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                SampleActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    @OnClick(R.id.openNewScreen)
    void onOpenNewScreenClick() {
        Intent intent = new Intent(this, SampleFragmentContainerActivity.class);
        startActivity(intent);
    }
}