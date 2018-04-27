package com.example.bhadraother.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Settings extends Activity implements OnClickListener
{
    private DrawerLayout mDrawerLayout;
    /** Called when the activity is first created. */
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    Intent i;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = sp.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        Intent intent = null;
                        String item = (String) menuItem.getTitle();
                        switch (item) {
                            case "All Stories":
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                break;
                            case "Settings":
                                intent = new Intent(getApplicationContext(), Settings.class);
                                break;
                            case "Send in a Tip":
                                intent = new Intent(getApplicationContext(), MapsActivity.class);
                            case "Shake or Tap to Send Feedback":
                                intent = new Intent(getApplicationContext(), BugActivity.class);
                                break;
                            default:
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                        }
                        startActivity(intent);
                        return true;
                    }
                });
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.button1:
                edit.putInt("theme",0);
                startActivity(i);
                break;
            case R.id.button2:
                edit.putInt("theme",1);
                startActivity(i);
                break;
            case R.id.button3:
                edit.putInt("theme",2);
                startActivity(i);
                break;
            default:
                edit.putInt("theme",0);
        }
    }
}
