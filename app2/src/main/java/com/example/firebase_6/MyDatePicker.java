package com.example.firebase_6;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public  class MyDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    int pYear;
    int pDay;
    int pMonth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialogDatePicker = new DatePickerDialog(getActivity(), this, year, month, day);
        dialogDatePicker.getDatePicker().setSpinnersShown(true);
        dialogDatePicker.getDatePicker().setCalendarViewShown(false);
        return dialogDatePicker;
        // Create a new instance of DatePickerDialog and return it
        //return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        pYear = year;
        pDay = day;
        pMonth = month;
    }
}
