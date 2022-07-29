package com.example.prc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prc.Notifications.Token;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavView;
    private String currentUserId, mUID;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private DatabaseReference userRef, reference;
    private StorageReference userProfileImgRef;
    private String calledBy = "";

    Spinner spinner;
    Locale myLocale;
    String currentLanguage = "en", currentLang, currentPageId;

    ImageView findPeopleBtn;
    private Uri pickedImgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        initToolbar();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        currentUser = mAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("User");
        userProfileImgRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        initFab();
        initNavigation();

        findPeopleBtn = findViewById(R.id.find_people_btn);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView profileBtn = headerView.findViewById(R.id.nav_user_photo);
        TextView profileTxt = headerView.findViewById(R.id.nav_username);
        TextView profileSts = headerView.findViewById(R.id.nav_user_mail);

        mAuth = FirebaseAuth.getInstance();


        findPeopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent findPeopleIntent = new Intent(MainActivity2.this,FindPeopleActivity.class);
                //startActivity(findPeopleIntent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findPeopleIntent = new Intent(MainActivity2.this, ProfileActivity1.class);
                startActivity(findPeopleIntent);
            }
        });

        profileTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findPeopleIntent = new Intent(MainActivity2.this, ProfileActivity1.class);
                startActivity(findPeopleIntent);
            }
        });
        profileSts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findPeopleIntent = new Intent(MainActivity2.this, ProfileActivity1.class);
                startActivity(findPeopleIntent);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        updateNavHeader();
        checkUserStatus();
        updateToken(FirebaseInstanceId.getInstance().getToken());

        List<Map<String, String>> groupData = new ArrayList<>();
        Map<String, String> curGroupMap = new HashMap<>();
        groupData.add(curGroupMap);
        curGroupMap.put("DATA", "Languages");


        List<List<Map<String, String>>> childData = new ArrayList<>();
        List<Map<String, String>> children = new ArrayList<>();

        Map<String, String> curChildMap = new HashMap<>();
        children.add(curChildMap);
        curChildMap.put("DATA", "English");

        Map<String, String> curChildMap1 = new HashMap<>();
        children.add(curChildMap1);
        curChildMap1.put("DATA", "Marathi");

        Map<String, String> curChildMap2 = new HashMap<>();
        children.add(curChildMap2);
        curChildMap2.put("DATA", "Hindi");

        childData.add(children);

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                R.layout.item_layout_lang_title,
                new String[]{"DATA"},
                new int[]{R.id.tv_title},

                childData,
                R.layout.langview,
                new String[]{"DATA"},
                new int[]{R.id.tv_title}
        );

        ExpandableListView expandableListView = findViewById(R.id.evLanguages);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch (childPosition) {
                    case 0:
                        setLocale("en");
                        break;
                    case 1:
                        setLocale("mar");
                        break;
                    case 2:
                        setLocale("hi");
                        break;
                }
                return true;
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        int currentItemId = sharedPreferences.getInt("currentItem", 0);
        MenuItem currentItem = bottomNavView.getMenu().findItem(currentItemId);
        if (currentItem != null) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(currentItem.getItemId());
        }

    }

    public void setLocale (String localeName){
        currentLanguage = getIntent().getStringExtra(currentLang);
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            ((Resources) res).updateConfiguration(conf, dm);
            int currentItem = bottomNavView.getSelectedItemId();
            Intent refresh = new Intent(this, MainActivity.class);
            refresh.putExtra(currentLang, localeName);
            refresh.putExtra(currentPageId, currentItem);
            SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("currentItem", bottomNavView.getSelectedItemId());
            editor.apply();
            startActivity(refresh);
        } else {
            Toast.makeText(MainActivity2.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateToken (String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        reference.child(mUID).setValue(mToken);
    }

    private void checkUserStatus () {
        FirebaseUser user = mAuth.getCurrentUser();

        mUID = user.getUid();
        SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Current_USERID", mUID);
        editor.apply();
    }

    private void updateNavHeader () {


        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        View headerView = navigationView.getHeaderView(0);
                        TextView navUsername = headerView.findViewById(R.id.nav_username);
                        TextView navUserMail = headerView.findViewById(R.id.nav_user_mail);
                        ImageView navUserPhot = headerView.findViewById(R.id.nav_user_photo);
                        if (dataSnapshot.exists()) {
                            String imsgeDb = dataSnapshot.child("image").getValue().toString();
                            String nameDb = dataSnapshot.child("name").getValue().toString();
                            String bioDb = dataSnapshot.child("status").getValue().toString();

                            navUsername.setText(nameDb);
                            navUserMail.setText(bioDb);

                            Picasso.get().load(imsgeDb).transform(new CircleTransform()).into(navUserPhot);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    private void initToolbar () {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void initFab () {

        FloatingActionButton fab = findViewById(R.id.fab);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/palmistry-research-center.appspot.com/o/Profile%20Images%2FuX8AMqcRf8SMdK3h6tGBuTn40ar2?alt=media&token=b87cef8b-3daf-47f9-995c-593ffc94bdf4").transform(new CircleTransform()).into(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String listUserId = "uX8AMqcRf8SMdK3h6tGBuTn40ar2";
                //uX8AMqcRf8SMdK3h6tGBuTn40ar2
                if (currentUserId.equals(listUserId)) {
                    Intent findPeopleIntent = new Intent(MainActivity2.this, MessageActivity.class);
                    startActivity(findPeopleIntent);
                } else {
                    Intent findPeopleIntent = new Intent(MainActivity2.this, ChattingActivity.class);
                    findPeopleIntent.putExtra("visit_user_id", listUserId);
                    startActivity(findPeopleIntent);
                }
            }
        });

    }

    private void initNavigation () {

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavView = findViewById(R.id.bottom_nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_course, R.id.nav_contactus,
                R.id.nav_con, R.id.nav_send,
                R.id.bottom_home, R.id.bottom_consultation, R.id.bottom_courses, R.id.bottom_contact_us)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp () {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed () {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    private void showBothNavigation ( boolean isShowable){
        navigationView.setVisibility(isShowable ? View.VISIBLE : View.GONE);
        bottomNavView.setVisibility(isShowable ? View.VISIBLE : View.GONE);
    }

    private void showBottomNavigation ( boolean isShowable){
        bottomNavView.setVisibility(isShowable ? View.VISIBLE : View.GONE);
    }


    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item){
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_signout: {
                Intent loginActivity = new Intent(getApplicationContext(), MainActivity.class);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                FirebaseAuth.getInstance().signOut();
                startActivity(loginActivity);
                finish();
                break;
            }

            case R.id.nav_send: {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "This is Body";
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share using"));
                break;
            }

            case R.id.nav_home: {
                bottomNavView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.bottom_home);
                break;

            }

            case R.id.nav_con: {
                bottomNavView.getMenu().findItem(R.id.bottom_consultation).setChecked(true);
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.bottom_consultation);
                break;
            }

            case R.id.nav_course: {
                bottomNavView.getMenu().findItem(R.id.bottom_courses).setChecked(true);
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.bottom_courses);
                break;
            }

            case R.id.nav_contactus: {
                bottomNavView.getMenu().findItem(R.id.bottom_contact_us).setChecked(true);
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.bottom_contact_us);
                break;
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }


        @Override
        public String key() {
            return "circle";
        }
    }

    private void validateUser () {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Intent settingIntent = new Intent(MainActivity2.this, ProfileActivity1.class);
                    startActivity(settingIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkForReceivingCall () {
        userRef.child(currentUserId)
                .child("Ringing")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("ringing")) {
                            calledBy = dataSnapshot.child("ringing").getValue().toString();

                            //Intent callingIntent = new Intent(MainActivity2.this,CallingActivity.class);
                            //callingIntent.putExtra("visit_user_id",calledBy);
                            // startActivity(callingIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void updateUserStatus (String state){
        reference = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid());
        String saveCurrentTime, saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date", saveCurrentDate);
        onlineStateMap.put("state", state);
        reference.child("userState").updateChildren(onlineStateMap);
    }

    @Override
    protected void onStart () {
        super.onStart();
        validateUser();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}