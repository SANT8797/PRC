package com.example.prc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prc.Notifications.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UpiPaymentActivity extends AppCompatActivity implements PaymentStatusListener {

    private ImageView imageView;

    private TextView statusView;

    private Button payButton,ContinueButton;
    Boolean notify = false;
    private String savedCurrentTime, saveCurrentDate;
    private TextView fieldPayeeVpa;
    private EditText fieldPayeeName;
    private EditText fieldTransactionId;
    private EditText fieldTransactionRefId;
    private EditText fieldDescription;
    private EditText fieldAmount;
    APIService apiService;
    DatabaseReference reference , rootRef;
    private String messageReceiverID, messageSenderId,currentUserId;
    FirebaseUser fuser;
    Intent intent;
    private FirebaseAuth mAuth;
    String details;
    private EasyUpiPayment mEasyUpiPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_payment);
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);

        rootRef = FirebaseDatabase.getInstance().getReference();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        intent = getIntent();
        final String userid ="uX8AMqcRf8SMdK3h6tGBuTn40ar2";
        messageReceiverID = userid;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        savedCurrentTime = currentTime.format(calendar.getTime());
        initViews();

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
               
            }
        });
        ContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   final String listUserId = "uX8AMqcRf8SMdK3h6tGBuTn40ar2";
                //uX8AMqcRf8SMdK3h6tGBuTn40ar2
                if (currentUserId.equals(listUserId)) {
                    Intent findPeopleIntent = new Intent(UpiPaymentActivity.this, MessageActivity.class);
                    startActivity(findPeopleIntent);
                } else {
                    Intent findPeopleIntent = new Intent(UpiPaymentActivity.this, ChattingActivity.class);
                    findPeopleIntent.putExtra("visit_user_id", listUserId);
                    startActivity(findPeopleIntent);
                }
            }
        });
    }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
        statusView = findViewById(R.id.textView_status);
        payButton = findViewById(R.id.button_pay);
        ContinueButton = findViewById(R.id.button_Continue);
        fieldPayeeVpa = findViewById(R.id.field_vpa);
        fieldPayeeName = findViewById(R.id.field_name);
        fieldTransactionId = findViewById(R.id.field_transaction_id);
        fieldTransactionRefId = findViewById(R.id.field_transaction_ref_id);
        fieldDescription = findViewById(R.id.field_description);
        fieldAmount = findViewById(R.id.field_amount);

        String transactionId = "TID" + System.currentTimeMillis();
        fieldTransactionId.setText(transactionId);
        fieldTransactionRefId.setText(transactionId);

    }

    private void pay() {
        String payeeVpa = fieldPayeeVpa.getText().toString();
        String payeeName = fieldPayeeName.getText().toString();
        String transactionId = fieldTransactionId.getText().toString();
        String transactionRefId = fieldTransactionRefId.getText().toString();
        String description = fieldDescription.getText().toString();
        String amount = fieldAmount.getText().toString();


        // START PAYMENT INITIALIZATION
        mEasyUpiPayment = new EasyUpiPayment.Builder()
                .with(this)
                .setPayeeVpa(payeeVpa)
                .setPayeeName(payeeName)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionRefId)
                .setDescription(description)
                .setAmount(amount
                        +".")
                .build();

        // Register Listener for Events
        mEasyUpiPayment.setPaymentStatusListener(this);



        // START PAYMENT
        mEasyUpiPayment.startPayment();


    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        Log.d("TransactionDetails", transactionDetails.toString());
        String userid ="uX8AMqcRf8SMdK3h6tGBuTn40ar2";
        statusView.setText(transactionDetails.toString());
        details = (String) statusView.getText();
        sendMessage(fuser.getUid(),userid,details);
    }

    /* private void sendMessage(String sender, final String receiver, final String detail)  {

        String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
        String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

        DatabaseReference userMessageKeyRef = rootRef.child("Messages").child(messageSenderId)
                .child(messageSenderRef).child(messageReceiverID).push();
        String messagePushId = userMessageKeyRef.getKey();
        Map messageTextBody = new HashMap();
        messageTextBody.put("message",detail);
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
                    Toast.makeText(UpiPaymentActivity.this,"Message Sent",Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(UpiPaymentActivity.this,"Error",Toast.LENGTH_SHORT).show();

                }
            }
        });



        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("User").child(sender);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Contacts contacts = dataSnapshot.getValue(Contacts.class);
                if (notify){

                    sendNotification(receiver,contacts.getName(),detail);
                }
                notify = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification(final String receiver,final String name, final String detail) {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Token token = ds.getValue(Token.class);
                    Data data = new Data(fuser.getUid(),R.drawable.ic_stat_name, name+": "+detail,"New Message",
                            receiver);
                    Sender sender = new Sender(data,token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200 ){
                                        if (response.body().success == 1){
                                            Toast.makeText(UpiPaymentActivity.this,"Send",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    @Override
    public void onTransactionSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_success);
        String userid ="uX8AMqcRf8SMdK3h6tGBuTn40ar2";
        details = (String) statusView.getText();
        sendMessage(fuser.getUid(),userid,details);

    }

    @Override
    public void onTransactionSubmitted() {
        Toast.makeText(this, "Pending | Submitted", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_success);
        String userid ="uX8AMqcRf8SMdK3h6tGBuTn40ar2";
        details = (String) statusView.getText();
        sendMessage(fuser.getUid(),userid,details);
    }

    @Override
    public void onTransactionFailed() {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_failed);
        String userid ="uX8AMqcRf8SMdK3h6tGBuTn40ar2";
        details = (String) statusView.getText();
        sendMessage(fuser.getUid(),userid,details);
    }

    @Override
    public void onTransactionCancelled() {

        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_failed);
        String userid ="uX8AMqcRf8SMdK3h6tGBuTn40ar2";
        details = (String) statusView.getText();
        sendMessage(fuser.getUid(),userid,details);
    }

    @Override
    public void onAppNotFound() {
        Toast.makeText(this, "App Not Found", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEasyUpiPayment.detachListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void sendMessage(String sender, final String receiver, final String detail)
    {

        String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
        String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

        DatabaseReference userMessageKeyRef = rootRef.child("Messages").child(messageSenderId)
                .child(messageSenderRef).child(messageReceiverID).push();
        String messagePushId = userMessageKeyRef.getKey();
        Map messageTextBody = new HashMap();
        messageTextBody.put("message",detail);
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
                    Toast.makeText(UpiPaymentActivity.this,"Message Sent",Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(UpiPaymentActivity.this,"Error",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}