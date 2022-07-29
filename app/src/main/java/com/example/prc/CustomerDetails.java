package com.example.prc;

import android.app.DatePickerDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.prc.Notifications.Client;
import com.example.prc.Notifications.Data;
import com.example.prc.Notifications.MyResponse;
import com.example.prc.Notifications.Sender;
import com.example.prc.Notifications.Token;
import com.example.prc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomerDetails extends AppCompatActivity {


    TextView date,time,datem;

    DatePickerDialog datePickerDialog,datePickerDialog2;

    Spinner genderSpinner,spinner_marriage;

    RadioGroup radioGroup;
    RadioButton radioButton;

    EditText name,emailAddress,mobile,birthPlace,stateCountry,anySpecify;

    Intent intent;

    private String savedCurrentTime, saveCurrentDate;


    DatabaseReference reference , rootRef;
    private String messageReceiverID, messageSenderId;
    FirebaseUser fuser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        radioGroup = findViewById(R.id.rg1);

        spinner_marriage = findViewById(R.id.spinner_marriage);

        ArrayAdapter<CharSequence> adapterMarriage = ArrayAdapter.createFromResource(this,R.array.Maritalarray, android.R.layout.simple_spinner_item);
        adapterMarriage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_marriage.setAdapter(adapterMarriage);

        genderSpinner = findViewById(R.id.genderSpinner);

        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this,R.array.Genderarray, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapterGender);

        rootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        messageSenderId = mAuth.getCurrentUser().getUid();

        name = findViewById(R.id.textPersonName);
        emailAddress = findViewById(R.id.textEmailAddress);
        mobile = findViewById(R.id.textPhone);
        birthPlace = findViewById(R.id.textBirthplace);
        stateCountry = findViewById(R.id.textCountry);
        anySpecify = findViewById(R.id.textSpecify);


        final String[] Cname = new String[1];
        final String[] CemailAddress = new String[1];
        final String[] Cmobile = new String[1];
        final String[] CbirthPlace = new String[1];
        final String[] CstateCountry = new String[1];
        final String[] CanySpecify = new String[1];
        final String[] Cdate = new String[1];
        final String[] Ctime = new String[1];
        final String[] Cdatem = new String[1];
        final String[] cStudWork = new String[1];
        final String[] Cmarriage = new String[1];
        final String[] Cgender = new String[1];
        final String[] Plus = new String[1];
        intent = getIntent();
        final String userid ="uX8AMqcRf8SMdK3h6tGBuTn40ar2";
        messageReceiverID = userid;

        Button b2 = findViewById(R.id.btnback);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button b1 = findViewById(R.id.btnsubmit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMMM dd,yyyy");
                saveCurrentDate = currentDate.format(calendar.getTime());
                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
                savedCurrentTime = currentTime.format(calendar.getTime());

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                cStudWork[0] = radioButton.getText().toString();
                Cdatem[0] = datem.getText().toString();
                Ctime[0] = time.getText().toString();
                Cdate[0] = date.getText().toString();
                CanySpecify[0] = anySpecify.getText().toString();
                CstateCountry[0] = stateCountry.getText().toString();
                CbirthPlace[0] = birthPlace.getText().toString();
                Cmobile[0] = mobile.getText().toString();
                CemailAddress[0] = emailAddress.getText().toString();
                Cname[0] = name.getText().toString();
                Cgender[0] = genderSpinner.getSelectedItem().toString();
                Cmarriage[0] = spinner_marriage.getSelectedItem().toString();

                if (Cname[0].isEmpty()) {
                    name.setError("Name is required");
                    name.requestFocus();
                    return;
                }if (CemailAddress[0].isEmpty()) {
                    emailAddress.setError("Email is required");
                    emailAddress.requestFocus();
                    return;
                }if (Cmobile[0].isEmpty()) {
                    mobile.setError("Mobile number is required");
                    mobile.requestFocus();
                    return;
                }if (Cdate[0].isEmpty()) {
                    date.setError("Birth date is required");
                    date.requestFocus();
                    return;
                }if (Ctime[0].isEmpty()) {
                    time.setError("Birth time is required");
                    time.requestFocus();
                    return;
                }if (Cgender[0].equals("Choose Gender")) {
                    Toast.makeText(CustomerDetails.this,"Gender is required",Toast.LENGTH_SHORT).show();
                    genderSpinner.requestFocus();
                    return;
                }if (CbirthPlace[0].isEmpty()) {
                    birthPlace.setError("Birth place is required");
                    birthPlace.requestFocus();
                    return;
                }if (CstateCountry[0].isEmpty()) {
                    stateCountry.setError("Please enter your State and Country is required");
                    stateCountry.requestFocus();
                    return;
                }if (Cmarriage[0].equals("Marrital Status")) {
                    Toast.makeText(CustomerDetails.this,"Marrital Status is required",Toast.LENGTH_SHORT).show();
                    spinner_marriage.requestFocus();
                    return;
                }if (cStudWork[0].isEmpty())
                {
                    radioButton.setError("Choose any one field Student Or Working");
                    radioButton.requestFocus();
                    return;
                }if (CanySpecify[0].isEmpty()) {
                    anySpecify.setError("Occupation is required");
                    anySpecify.requestFocus();
                    return;
                }

                    Plus[0]= "Name:"+Cname[0]+"\n" +
                         "Email:"+CemailAddress[0]+"\n"+
                         "Mobile Number:"+Cmobile[0]+"\n"+
                         "Birth Place:"+CbirthPlace[0]+"\n"+
                         "State&Country:"+CstateCountry[0]+"\n"+
                         "Business:"+CanySpecify[0]+"\n"+
                         "Date of Birth:"+Cdate[0] +"\n"+
                         "Time of Birth:"+Ctime[0]+"\n"+
                         "Marriage Date:"+Cdatem[0]+"\n"+
                         "I am Currently:"+cStudWork[0]+"\n"+
                         "Marriage Status:"+Cmarriage[0]+"\n"+
                         "Gender:"+Cgender[0]+"\n";
                Toast.makeText(CustomerDetails.this,""+Plus[0],Toast.LENGTH_SHORT).show();

                sendMessage(fuser.getUid(),userid,Plus[0]);

                Intent findPeopleIntent = new Intent(CustomerDetails.this, scan_palm.class);
                startActivity(findPeopleIntent);

            }
        });

        datem=findViewById(R.id.datemarri);
        datem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog2 = new DatePickerDialog(CustomerDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                datem.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog2.show();
                Cdatem[0] = datem.getText().toString();
            }


        });


        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CustomerDetails.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                Ctime[0] = time.getText().toString();
            }
        });



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CustomerDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                Cdate[0] = date.getText().toString();
            }
        });



    }

    public void checkButton(View v)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);


    }
    private void sendMessage(String sender, final String receiver, final String message)
    {

        String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
        String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

        DatabaseReference userMessageKeyRef = rootRef.child("Messages").child(messageSenderId)
                .child(messageSenderRef).child(messageReceiverID).push();
        String messagePushId = userMessageKeyRef.getKey();
        Map messageTextBody = new HashMap();
        messageTextBody.put("message",message);
        messageTextBody.put("type","text");
        messageTextBody.put("from",sender);
        messageTextBody.put("to",receiver);
        messageTextBody.put("messageId",messagePushId);
        messageTextBody.put("time",savedCurrentTime);
        messageTextBody.put("date",saveCurrentDate);

        Map messageBodyDetails = new HashMap();
        messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageTextBody);
        messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageTextBody);

        rootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(CustomerDetails.this,"Message Sent",Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(CustomerDetails.this,"Error",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }



}