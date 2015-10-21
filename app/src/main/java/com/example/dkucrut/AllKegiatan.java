package com.example.dkucrut;

/**
 * Created by subki on 10/13/2015.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

public class AllKegiatan extends ActionBarActivity implements OnItemClickListener {

    ListView listViewKegiatan;
    CustomHttpClient client;
    List<Kegiatan> listKegiatan;


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_event);

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

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(AllKegiatan.this, MainActivity.class));
                        Toast.makeText(AllKegiatan.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_about:
                        startActivity(new Intent(AllKegiatan.this, About.class));
                        Toast.makeText(AllKegiatan.this, "About clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_member:
                        startActivity(new Intent(AllKegiatan.this, AllMember.class));
                        Toast.makeText(AllKegiatan.this, "Member clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_event:
                        //startActivity(new Intent(AllKegiatan.this, AllKegiatan.class));
                        Toast.makeText(AllKegiatan.this, "Event clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(AllKegiatan.this, LoginActivity.class));
                        Toast.makeText(AllKegiatan.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });

        listViewKegiatan=(ListView) findViewById(R.id.listViewEvent);
        client=new CustomHttpClient();
        Gson gson=new Gson();

        try {
            String json=client.get("http://118.97.87.4/dkucrut/event.php");
            listKegiatan=gson.fromJson(json,
                    new TypeToken<List<Kegiatan>>(){}.getType());

            listViewKegiatan.setAdapter(new KegiatanAdapter(this, R.layout.list_event, listKegiatan));
            listViewKegiatan.setOnItemClickListener(this);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write here anything that you wish to do on click of FAB
                startActivity(new Intent(AllKegiatan.this, detail_event.class));
                Toast.makeText(AllKegiatan.this, "Create Event !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

        Intent intent=getIntent();
        id=intent.getIntExtra("ID", position+1);

        client=new CustomHttpClient();
        Gson gson=new Gson();

        try {
            String json=client.get("http://118.97.87.4/dkucrut/event_detail.php?id=" + id);
            Kegiatan kegiatan=gson.fromJson(json, Kegiatan.class);

            LinearLayout layoutInput = new LinearLayout(this);
            layoutInput.setOrientation(LinearLayout.VERTICAL);
            layoutInput.setPadding(10,5,5,3);

            // buat id tersembunyi di alertbuilder
            final TextView viewId = new TextView(this);
            viewId.setText(String.valueOf(kegiatan.getId()));
            viewId.setTextColor(Color.TRANSPARENT);
            layoutInput.addView(viewId);

            final TextView editEvent = new TextView(this);
            editEvent.setText(kegiatan.getEvent());
            editEvent.setTextColor(Color.parseColor("#FFFFFF"));
            layoutInput.addView(editEvent);

            final TextView editTanggal = new TextView(this);
            editTanggal.setText(kegiatan.getTanggal());
            editTanggal.setTextColor(Color.parseColor("#FFFFFF"));
            layoutInput.addView(editTanggal);

            final TextView editDesc = new TextView(this);
            editDesc.setText(kegiatan.getDesc());
            editDesc.setTextColor(Color.parseColor("#FFFFFF"));
            layoutInput.addView(editDesc);

            AlertDialog.Builder builderEvent = new AlertDialog.Builder(this);
            builderEvent.setIcon(R.drawable.ic_event);
            builderEvent.setTitle("Detail Event");
            builderEvent.setView(layoutInput);

            builderEvent.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }

            });

            builderEvent.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupToolbar() {

        // findview toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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