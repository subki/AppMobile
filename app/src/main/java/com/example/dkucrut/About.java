package com.example.dkucrut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class About extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupToolbar();
        navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                drawerLayout.closeDrawers();
                Anggota anggota = new Anggota();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(About.this, MainActivity.class));
                        Toast.makeText(About.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_about:
                        //startActivity(new Intent(About.this, About.class));
                        Toast.makeText(About.this, "About clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_member:
                        startActivity(new Intent(About.this, AllMember.class));
                        Toast.makeText(About.this, "Member clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_event:
                        startActivity(new Intent(About.this, AllKegiatan.class));
                        Toast.makeText(About.this, "Event clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(About.this, LoginActivity.class));
                        Toast.makeText(About.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
    }

    private void setupToolbar() {

        // findview toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // set toolbar ke dalam support action bar
        setSupportActionBar(toolbar);

        // enable home button untuk navigasi
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // mengeset icon untuk home button Toolbar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        // mengeset title/nama aplikasi
        getSupportActionBar().setTitle("DKucrut Community");

        // mengeset subtitle
        getSupportActionBar().setSubtitle("Satukan Persahabatan");

        // set logo toolbar
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // menghandle ketika tombol home diklik, Navigation View akan terbuka
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
