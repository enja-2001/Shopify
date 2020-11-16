package com.example.firebase_6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    //TextView tvDate;
    //TextView tvTime;
    Button butNext;
    Button butPrev;

    int DD=-1;
    int MM=-1;
    int YY=-1;

    TextView tvDD;TextView tvMM;TextView tvYY;
    TextView tvHH;TextView tvMI;TextView tvAMPM;

    MyOrder ob;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        //tvDate=(TextView)findViewById(R.id.tvDate);
        //tvTime=(TextView)findViewById(R.id.tvTime);

        tvDD=(TextView)findViewById(R.id.tvDD);
        tvMM=(TextView)findViewById(R.id.tvMM);
        tvYY=(TextView)findViewById(R.id.tvYY);

        tvHH=(TextView)findViewById(R.id.tvHH);
        tvMI=(TextView)findViewById(R.id.tvMI);
        tvAMPM=(TextView)findViewById(R.id.tvAMPM);


        butNext=(Button)findViewById(R.id.buttonDateTimeNext);
        butPrev=(Button)findViewById(R.id.buttonDateTimeBack);

         ob=(MyOrder) getIntent().getSerializableExtra("obj");


        /*tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDate();

            }
        });*/

        /*tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();

            }
        });*/

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tvYY.getText().toString().isEmpty() || tvMM.getText().toString().isEmpty() ||tvDD.getText().toString().isEmpty() ||tvHH.getText().toString().isEmpty() ||tvMI.getText().toString().isEmpty() ||tvAMPM.getText().toString().isEmpty())
                    Toast.makeText(DateTimeActivity.this, "All the fields are required!", Toast.LENGTH_SHORT).show();

                else {

                    ob.scheduledDate=tvDD.getText().toString()+" "+tvMM.getText().toString()+","+tvYY.getText().toString();
                    ob.scheduledTime=tvHH.getText().toString()+":"+tvMI.getText().toString()+" "+tvAMPM.getText().toString();

                    Intent intent = new Intent(DateTimeActivity.this, PaymentActivity.class);
                    intent.putExtra("obj",ob);

                    startActivity(intent);
                }

            }
        });
        butPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DateTimeActivity.this,PlaceOrderAddress.class);
                startActivity(intent);

            }
        });


        tvDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDD();

            }
        });
        tvMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMM();

            }
        });
        tvYY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showYY();

            }
        });

        tvHH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHH();

            }
        });
        tvMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMI();

            }
        });
        tvAMPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAMPM();

            }
        });



    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        //Log.i("value is",""+newVal);

    }

    /*public void showDate()
    {

        final AlertDialog.Builder d = new AlertDialog.Builder(DateTimeActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.date_picker_dialog, null);
        d.setTitle("Select Date");
        //d.setMessage("You have been working since the year");
        d.setView(dialogView);

        final NumberPicker numberPickerYear = (NumberPicker) dialogView.findViewById(R.id.dialog_year_picker);
        numberPickerYear.setMinValue(Calendar.getInstance().get(Calendar.YEAR));//myCalendar.get(Calendar.YEAR)
        numberPickerYear.setMaxValue(Calendar.getInstance().get(Calendar.YEAR)+1);
        numberPickerYear.setWrapSelectorWheel(false);
        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });





        final NumberPicker numberPickerMonth = (NumberPicker) dialogView.findViewById(R.id.dialog_month_picker);
        //numberPickerMonth.setMinValue(Calendar.getInstance().get(Calendar.MONTH));//myCalendar.get(Calendar.YEAR)
        //numberPickerMonth.setMaxValue(12);

        //numberPickerMonth.setMinValue(Calendar.getInstance().get(Calendar.MONTH));
        //numberPickerMonth.setMaxValue(Calendar.getInstance().get(Calendar.MONTH)+11);
        //numberPickerMonth.set

        numberPickerMonth.setMinValue(0);
        numberPickerMonth.setMaxValue(11);

        String monthArr[]={ "January", "February", "March","April","May","June","July","August","September","October","November","December" };
        numberPickerMonth.setDisplayedValues( monthArr );

        numberPickerMonth.setWrapSelectorWheel(false);
        numberPickerMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });*/




        /*Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add( Calendar.MONTH, -6 ); // Subtract 6 months
        int minDate = (int)c.getTime().getTime(); // Twice!*/


        /*final NumberPicker numberPickerDay = (NumberPicker) dialogView.findViewById(R.id.dialog_day_picker);
        //----numberPickerDay.setMinValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));//myCalendar.get(Calendar.YEAR)
        //numberPickerDay.setMinValue(minDate);
        //numberPickerDay.setMaxValue(minDate+5);

        //Calendar.getInstance().get(Calendar.)

        //-----numberPickerDay.setMaxValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1);
        numberPickerDay.setMinValue(1);

            //numberPickerDay.setMaxValue(30);

        numberPickerDay.setMaxValue(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));


        //numberPickerDay.setDisplayedValues( new String[] { "Belgium", "France", "United Kingdom" } );

        numberPickerDay.setWrapSelectorWheel(false);



        numberPickerDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });



        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());
                tvDate.setText(numberPickerDay.getValue()+" "+monthArr[numberPickerMonth.getValue()]+" , "+numberPickerYear.getValue());

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }*/

    /*public void showTime()
    {

        final AlertDialog.Builder d = new AlertDialog.Builder(DateTimeActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.date_picker_dialog, null);
        d.setTitle("Select Time");
        //d.setMessage("You have been working since the year");
        d.setView(dialogView);

        final NumberPicker numberPickerAMPM = (NumberPicker) dialogView.findViewById(R.id.dialog_year_picker);

        numberPickerAMPM.setMinValue(0);
        numberPickerAMPM.setMaxValue(1);

        String arrAMPM[]={"AM","PM"};

        numberPickerAMPM.setDisplayedValues( arrAMPM );

        numberPickerAMPM.setWrapSelectorWheel(false);
        numberPickerAMPM.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });





        final NumberPicker numberPickerMinute = (NumberPicker) dialogView.findViewById(R.id.dialog_month_picker);
        //numberPickerMonth.setMinValue(Calendar.getInstance().get(Calendar.MONTH));//myCalendar.get(Calendar.YEAR)
        //numberPickerMonth.setMaxValue(12);

        //numberPickerMonth.setMinValue(Calendar.getInstance().get(Calendar.MONTH));
        //numberPickerMonth.setMaxValue(Calendar.getInstance().get(Calendar.MONTH)+11);
        //numberPickerMonth.set

        numberPickerMinute.setMinValue(0);
        numberPickerMinute.setMaxValue(59);


       //-- String monthArr[]={ "January", "February", "March","April","May","June","July","August","September","October","November","December" };
       //--- numberPickerMinute.setDisplayedValues( monthArr );

        numberPickerMinute.setWrapSelectorWheel(false);
        numberPickerMinute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });*/




        /*Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add( Calendar.MONTH, -6 ); // Subtract 6 months
        int minDate = (int)c.getTime().getTime(); // Twice!*/


        /*final NumberPicker numberPickerHour = (NumberPicker) dialogView.findViewById(R.id.dialog_day_picker);
        //----numberPickerDay.setMinValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));//myCalendar.get(Calendar.YEAR)
        //numberPickerDay.setMinValue(minDate);
        //numberPickerDay.setMaxValue(minDate+5);

        //Calendar.getInstance().get(Calendar.)

        //-----numberPickerDay.setMaxValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1);
        numberPickerHour.setMinValue(0);

        numberPickerHour.setMaxValue(12);

        //numberPickerDay.setDisplayedValues( new String[] { "Belgium", "France", "United Kingdom" } );

        numberPickerHour.setWrapSelectorWheel(false);



        numberPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });



        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());

                String hour=""+numberPickerHour.getValue();;
                String min=""+numberPickerMinute.getValue();;
                String ampm="";

                if(numberPickerHour.getValue()<=9)
                    hour="0"+numberPickerHour.getValue();

                if(numberPickerMinute.getValue()<=9)
                    min="0"+numberPickerMinute.getValue();

                if(numberPickerAMPM.getValue()==0)
                    ampm="AM";
                else if(numberPickerAMPM.getValue()==1)
                    ampm="PM";

                    tvTime.setText(hour+":"+min+" "+ampm);

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }*/






    public void showYY()
    {

        final AlertDialog.Builder d = new AlertDialog.Builder(DateTimeActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        d.setTitle("Select Year");
        //d.setMessage("You have been working since the year");
        d.setView(dialogView);

        final NumberPicker numberPickerYear = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPickerYear.setMinValue(Calendar.getInstance().get(Calendar.YEAR));//myCalendar.get(Calendar.YEAR)
        numberPickerYear.setMaxValue(Calendar.getInstance().get(Calendar.YEAR)+1);
        numberPickerYear.setWrapSelectorWheel(false);
        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });





        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());
                YY=numberPickerYear.getValue();
                tvYY.setText(""+numberPickerYear.getValue());
                tvMM.setText("");
                tvDD.setText("");

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }


    public void showMM()
    {
        if(YY==-1){
            Toast.makeText(this, "Select year first", Toast.LENGTH_SHORT).show();
            return;
        }

        final AlertDialog.Builder d = new AlertDialog.Builder(DateTimeActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        d.setTitle("Select Month");
        //d.setMessage("You have been working since the year");
        d.setView(dialogView);

        final NumberPicker numberPickerYear = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);

        numberPickerYear.setMinValue(0);
        numberPickerYear.setMaxValue(11);

        String monthArr[]={"January", "February", "March","April","May","June","July","August","September","October","November","December" };
        numberPickerYear.setDisplayedValues(monthArr);
        numberPickerYear.setWrapSelectorWheel(false);
        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });





        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());
                MM=numberPickerYear.getValue();
                tvMM.setText(""+monthArr[numberPickerYear.getValue()]);
                tvDD.setText("");

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }


    public void showDD()
    {
        if(YY==-1){
            Toast.makeText(this, "Select year first", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(MM==-1){
            Toast.makeText(this, "Select month first", Toast.LENGTH_SHORT).show();
            return;
        }

        final AlertDialog.Builder d = new AlertDialog.Builder(DateTimeActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        d.setTitle("Select Day");
        //d.setMessage("You have been working since the year");
        d.setView(dialogView);

        int max=0;//getLastDayOfMonth(""+MM+"/"+YY);

        if(MM==1){
            if(isLeapYear(YY))
                max=29;
            else
                max=28;
        }

        else if(MM==0 || MM==2 || MM==4|| MM==6|| MM==7|| MM==9||MM==11)
            max=31;
        else
            max=30;


        final NumberPicker numberPickerYear = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPickerYear.setMinValue(1);//myCalendar.get(Calendar.YEAR)
        numberPickerYear.setMaxValue(max);
        numberPickerYear.setWrapSelectorWheel(false);
        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });





        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());
                DD=numberPickerYear.getValue();
                if(DD<=9)
                    tvDD.setText("0"+numberPickerYear.getValue());
                else
                    tvDD.setText(""+numberPickerYear.getValue());

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }



    private boolean isLeapYear(int year){
        if(year%4==0 ){
            if(year%400==0)
                return true;
            else if(year%100==0)
                return false;
            else
                return true;
        }
        else
            return false;
        }


    private void showHH(){

        final AlertDialog.Builder d = new AlertDialog.Builder(DateTimeActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        d.setTitle("Select Hour");
        //d.setMessage("You have been working since the year");
        d.setView(dialogView);

        final NumberPicker numberPickerYear = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPickerYear.setMinValue(1);//myCalendar.get(Calendar.YEAR)
        numberPickerYear.setMaxValue(12);
        numberPickerYear.setWrapSelectorWheel(false);
        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });





        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());
                //YY=numberPickerYear.getValue();
                if(numberPickerYear.getValue()<=9)
                    tvHH.setText("0"+numberPickerYear.getValue());
                else
                    tvHH.setText(""+numberPickerYear.getValue());
                //tvMM.setText("");
                //tvDD.setText("");

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }



    private void showMI(){

        final AlertDialog.Builder d = new AlertDialog.Builder(DateTimeActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        d.setTitle("Select Minute");
        //d.setMessage("You have been working since the year");
        d.setView(dialogView);

        final NumberPicker numberPickerYear = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPickerYear.setMinValue(0);//myCalendar.get(Calendar.YEAR)
        numberPickerYear.setMaxValue(11);

        String arrMI[]={"0","5","10","15","20","25","30","35","40","45","50","55"};

        numberPickerYear.setDisplayedValues( arrMI );
        numberPickerYear.setWrapSelectorWheel(false);
        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });





        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());
                //YY=numberPickerYear.getValue();
                if(numberPickerYear.getValue()<=1)
                    tvMI.setText("0"+arrMI[numberPickerYear.getValue()]);

                 else

                    tvMI.setText(arrMI[numberPickerYear.getValue()]);

                //tvMM.setText("");
                //tvDD.setText("");

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }

    private void showAMPM(){

        final AlertDialog.Builder d = new AlertDialog.Builder(DateTimeActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        d.setTitle("Select AM/PM");
        //d.setMessage("You have been working since the year");
        d.setView(dialogView);

        final NumberPicker numberPickerYear = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
        numberPickerYear.setMinValue(0);//myCalendar.get(Calendar.YEAR)
        numberPickerYear.setMaxValue(1);

        String arrAMPM[]={"AM","PM"};

        numberPickerYear.setDisplayedValues( arrAMPM );
        numberPickerYear.setWrapSelectorWheel(false);
        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //Log.d(TAG, "onValueChange: ");
            }
        });





        d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d(TAG, "onClick: " + numberPicker.getValue());
                //YY=numberPickerYear.getValue();
                if(numberPickerYear.getValue()==0)
                    tvAMPM.setText("AM");
                else if(numberPickerYear.getValue()==1)
                    tvAMPM.setText("PM");

                //tvMM.setText("");
                //tvDD.setText("");

                //Toast.makeText(AddWorkersActivity2.this, numberPicker.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }


}





