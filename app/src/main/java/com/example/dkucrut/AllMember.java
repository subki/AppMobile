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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

public class AllMember extends ActionBarActivity implements OnItemClickListener {

    ListView listViewAnggota;
    CustomHttpClient client;
    List<Anggota> listAnggota;


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_member);

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
                        startActivity(new Intent(AllMember.this, MainActivity.class));
                        Toast.makeText(AllMember.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_about:
                        startActivity(new Intent(AllMember.this, About.class));
                        Toast.makeText(AllMember.this, "About clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_member:
                        //startActivity(new Intent(AllMember.this, Member.class));
                        Toast.makeText(AllMember.this, "Member clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_event:
                        startActivity(new Intent(AllMember.this, AllKegiatan.class));
                        Toast.makeText(AllMember.this, "Event clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(AllMember.this, LoginActivity.class));
                        Toast.makeText(AllMember.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });

        listViewAnggota=(ListView) findViewById(R.id.listViewProduct);
        client=new CustomHttpClient();
        Gson gson=new Gson();

        try {
            String json=client.get("http://118.97.87.4/dkucrut/");
            listAnggota=gson.fromJson(json,
                    new TypeToken<List<Anggota>>(){}.getType());

            listViewAnggota.setAdapter(new MemberAdapter(this, R.layout.list_member, listAnggota));
            listViewAnggota.setOnItemClickListener(this);
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
                tambahBiodata();
                // Code to Add an item with default animation
                //int index = mAdapter.getItemCount();
                //DataObject obj = new DataObject("Some Primary Text " + index,
                //        "Secondary " + index);
                //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);
                //Ends Here
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
        id=intent.getIntExtra ("ID", position+1);

        client=new CustomHttpClient();
        Gson gson=new Gson();

        try {
            String json=client.get("http://118.97.87.4/dkucrut/detail.php?id="+id);
            Anggota anggota=gson.fromJson(json, Anggota.class);

            LinearLayout layoutInput = new LinearLayout(this);
            layoutInput.setOrientation(LinearLayout.VERTICAL);

            // buat id tersembunyi di alertbuilder
            final TextView viewId = new TextView(this);
            viewId.setText(String.valueOf(anggota.getId()));
            viewId.setTextColor(Color.TRANSPARENT);
            layoutInput.addView(viewId);

            final EditText editNama = new EditText(this);
            editNama.setText(anggota.getNama());
            editNama.setTextColor(Color.parseColor("#FFFFFF"));
            layoutInput.addView(editNama);

            final EditText editAlamat = new EditText(this);
            editAlamat.setText(anggota.getAlamat());
            editAlamat.setTextColor(Color.parseColor("#FFFFFF"));
            layoutInput.addView(editAlamat);

            AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
            builderEditBiodata.setIcon(R.drawable.ic_people);
            builderEditBiodata.setTitle("Detail Members");
            builderEditBiodata.setView(layoutInput);

            builderEditBiodata.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }

            });

            builderEditBiodata.show();

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


    private void tambahBiodata() {
        /* layout akan ditampilkan pada AlertDialog */
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final EditText editNama = new EditText(this);
        editNama.setHint("Nama");
        editNama.setTextColor(Color.parseColor("#FFFFFF"));
        layoutInput.addView(editNama);

        final EditText editAlamat = new EditText(this);
        editAlamat.setHint("Alamat");
        editAlamat.setTextColor(Color.parseColor("#FFFFFF"));
        layoutInput.addView(editAlamat);

        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        builderInsertBiodata.setIcon(R.drawable.ic_add_member);
        builderInsertBiodata.setTitle("Add Member");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = editNama.getText().toString();
                String alamat = editAlamat.getText().toString();

                System.out.println("Nama : " + nama + " Alamat : " + alamat);
                Login login = new Login();
                String laporan = login.insertBiodata(nama, alamat);

                Toast.makeText(AllMember.this, laporan, Toast.LENGTH_SHORT).show();

    /* restart acrtivity */
                finish();
                startActivity(getIntent());
            }

        });

        builderInsertBiodata.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderInsertBiodata.show();
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