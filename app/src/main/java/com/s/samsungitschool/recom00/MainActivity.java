package com.s.samsungitschool.recom00;

import android.app.FragmentTransaction;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.MapFragment;
import com.s.samsungitschool.recom00.auth.EntryActivity;
import com.s.samsungitschool.recom00.fragments.AboutFragmentActivity;
import com.s.samsungitschool.recom00.fragments.AchievementsFragmentActivity;
import com.s.samsungitschool.recom00.fragments.ApplicationsFragmentActivity;
import com.s.samsungitschool.recom00.fragments.DraftFragmentActivity;
import com.s.samsungitschool.recom00.fragments.NewAppFragmentActivity;
import com.s.samsungitschool.recom00.fragments.NewsFragmentActivity;
import com.s.samsungitschool.recom00.fragments.ProfileFragmentActivity;
import com.s.samsungitschool.recom00.fragments.RatingFragmentActivity;
import com.s.samsungitschool.recom00.fragments.SendFragmentActivity;
import com.s.samsungitschool.recom00.fragments.SettingsFragmentActivity;
import com.s.samsungitschool.recom00.fragments.ShareFragmentActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProfileFragmentActivity profileFragmentActivity;
    NewAppFragmentActivity newAppFragmentActivity;
    MapFragment mapFragment;
    ApplicationsFragmentActivity applicationsFragmentActivity;
    DraftFragmentActivity draftFragmentActivity;
    RatingFragmentActivity ratingFragmentActivity;
    AchievementsFragmentActivity achievementsFragmentActivity;
    NewsFragmentActivity newsFragmentActivity;
    SettingsFragmentActivity settingsFragmentActivity;
    AboutFragmentActivity aboutFragmentActivity;
    ShareFragmentActivity shareFragmentActivity;
    SendFragmentActivity sendFragmentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.profileFragmentActivity = new ProfileFragmentActivity();
        this.newAppFragmentActivity = new NewAppFragmentActivity();
        this.mapFragment = new MapFragment();
        this.applicationsFragmentActivity = new ApplicationsFragmentActivity();
        this.draftFragmentActivity = new DraftFragmentActivity();
        this.ratingFragmentActivity = new RatingFragmentActivity();
        this.achievementsFragmentActivity = new AchievementsFragmentActivity();
        this.newsFragmentActivity = new NewsFragmentActivity();
        this.settingsFragmentActivity = new SettingsFragmentActivity();
        this.aboutFragmentActivity = new AboutFragmentActivity();
        this.shareFragmentActivity = new ShareFragmentActivity();
        this.sendFragmentActivity = new SendFragmentActivity();

        //Start work
        Intent i = new Intent(this, EntryActivity.class);
        startActivity(i);


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, this.profileFragmentActivity);

        setTitle(R.string.Profile);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (id == R.id.nav_profile) {
            fragmentTransaction.replace(R.id.container, this.profileFragmentActivity);
        }
        else if (id == R.id.nav_new_app) {
            fragmentTransaction.replace(R.id.container, this.newAppFragmentActivity);
        }
        else if (id == R.id.nav_map) {
            fragmentTransaction.replace(R.id.container, this.mapFragment);
        }
        else if (id == R.id.nav_applications) {
            fragmentTransaction.replace(R.id.container, this.applicationsFragmentActivity);
        }
        else if (id == R.id.nav_draft) {
            fragmentTransaction.replace(R.id.container, this.draftFragmentActivity);
        }
        else if (id == R.id.nav_rating) {
            fragmentTransaction.replace(R.id.container, this.ratingFragmentActivity);
        }
        else if (id == R.id.nav_achievements) {
            fragmentTransaction.replace(R.id.container, this.achievementsFragmentActivity);
        }
        else if (id == R.id.nav_news) {
            fragmentTransaction.replace(R.id.container, this.newsFragmentActivity);
        }
        else if (id == R.id.nav_settings) {
            fragmentTransaction.replace(R.id.container, this.settingsFragmentActivity);
        }
        else if (id == R.id.nav_about) {
            fragmentTransaction.replace(R.id.container, this.aboutFragmentActivity);
        }
        else if (id == R.id.nav_share) {
            fragmentTransaction.replace(R.id.container, this.shareFragmentActivity);
        }
        else if (id == R.id.nav_send) {
            fragmentTransaction.replace(R.id.container, this.sendFragmentActivity);
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

/*

public class MainActivity extends NetIntegrationActivity implements OnNavigationItemSelectedListener {
    AboutFragmentActivity aboutFragment;
    AchievementsFragmentActivity achievementsFragment;
    ConsoleFragment consoleFragment;
    EventsFragment eventsFragment;
    FeedbackFragment feedbackFragment;
    GratitudeFragment gratitudeFragment;
    public boolean mLocationPermissionGranted;
    MapFragment mapFragment;
    NewsFragmentActivity newsFragment;
    ProfileFragmentActivity profileFragmentActivity;
    ProgressFragment progressFragment;
    RatingFragmentActivity ratingFragment;
    SettingsFragmentActivity settingsFragment;
    ShareFragmentActivity shareFragment;
    TrainingFragment trainingFragment;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (this.user == null) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            this.mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 5);
        }
        this.aboutFragment = new AboutFragmentActivity();
        this.achievementsFragment = new AchievementsFragmentActivity();
        this.feedbackFragment = new FeedbackFragment();
        this.gratitudeFragment = new GratitudeFragment();
        this.mapFragment = new MapFragment();
        this.profileFragmentActivity = new ProfileFragmentActivity();
        this.progressFragment = new ProgressFragment();
        this.ratingFragment = new RatingFragmentActivity();
        this.consoleFragment = new ConsoleFragment();
        this.newsFragment = new NewsFragmentActivity();
        this.settingsFragment = new SettingsFragmentActivity();
        this.shareFragment = new ShareFragmentActivity();
        this.trainingFragment = new TrainingFragment();
        this.eventsFragment = new EventsFragment();
        FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.container, this.progressFragment);
        fragmentTransaction1.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(8388611)) {
            drawer.closeDrawer(8388611);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (id == R.id.nav_progress) {
            fragmentTransaction.replace(R.id.container, this.progressFragment);
        } else if (id == R.id.nav_map) {
            fragmentTransaction.replace(R.id.container, this.mapFragment);
        } else if (id == R.id.nav_rating) {
            fragmentTransaction.replace(R.id.container, this.ratingFragment);
        } else if (id == R.id.nav_profile) {
            fragmentTransaction.replace(R.id.container, this.profileFragmentActivity);
        } else if (id == R.id.nav_achievements) {
            fragmentTransaction.replace(R.id.container, this.achievementsFragment);
        } else if (id == R.id.nav_events) {
            fragmentTransaction.replace(R.id.container, this.eventsFragment);
        } else if (id == R.id.nav_training) {
            fragmentTransaction.replace(R.id.container, this.trainingFragment);
        } else if (id == R.id.nav_console) {
            fragmentTransaction.replace(R.id.container, this.consoleFragment);
        } else if (id == R.id.nav_news) {
            fragmentTransaction.replace(R.id.container, this.newsFragment);
        } else if (id == R.id.nav_settings) {
            fragmentTransaction.replace(R.id.container, this.settingsFragment);
        } else if (id == R.id.nav_share) {
            fragmentTransaction.replace(R.id.container, this.shareFragment);
        } else if (id == R.id.nav_feedback) {
            fragmentTransaction.replace(R.id.container, this.feedbackFragment);
        } else if (id == R.id.nav_about) {
            fragmentTransaction.replace(R.id.container, this.aboutFragment);
        }
        fragmentTransaction.commit();
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(8388611);
        return true;
    }

    public static void deleteCache(Context context) {
        try {
            deleteDir(context.getCacheDir());
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            for (String aChildren : dir.list()) {
                if (!deleteDir(new File(dir, aChildren))) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile() && dir.delete()) {
            return true;
        } else {
            return false;
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}

 */