package com.example.ujian_pwpb;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputData extends AppCompatActivity {
    private EditText EdtJudul, EdtDeskripsi;
    private Button btnUpdate;

    Bundle bundle;

    DatabaseReference databaseSiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit");
        setContentView(R.layout.input_data);

        bundle = getIntent().getExtras();
        final String action = bundle.getString("Action");

        databaseSiswa = FirebaseDatabase.getInstance().getReference("Siswa");

        EdtJudul = (EditText)findViewById(R.id.edtJudul);
        EdtDeskripsi = (EditText)findViewById(R.id.edtDeskripsi);
        btnUpdate = (Button)findViewById(R.id.btn_update);

        if (action.equals("Update")){
            EdtJudul.setText(bundle.getString("Judul"));
            EdtDeskripsi.setText(bundle.getString("Deskripsi"));
            btnUpdate.setText("Update");
            getSupportActionBar().setTitle("Update Siswa");
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action.equals("Insert")){
                    addSiswa();
                }else if (action.equals("Update")){
                    String siswaId = bundle.getString("Id");
                    String judul = EdtJudul.getText().toString().trim();
                    String deskripsi = EdtDeskripsi.getText().toString().trim();

                    updateSiswa(siswaId, judul, deskripsi);
                }

            }
        });

    }
    public void addSiswa(){
        String judul = EdtJudul.getText().toString().trim();
        String deskripsi = EdtDeskripsi.getText().toString().trim();
        //fungsi waktu
        Date tanggal = Calendar.getInstance().getTime();
        System.out.println("Current time => " + tanggal);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String time = df.format(tanggal);

        if (!TextUtils.isEmpty(judul) && !TextUtils.isEmpty(deskripsi)){
            String id = databaseSiswa.push().getKey();

            Siswa siswa = new Siswa(id,judul,deskripsi,time);

            databaseSiswa.child(id).setValue(siswa);

            Toast.makeText(this, "Data Berhasil di tambahkan!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Masukkan data secara lengkap!", Toast.LENGTH_SHORT).show();
        }

    }
    private void updateSiswa(String id, String judul, String deskripsi){
        //input time
        Date tanggal = Calendar.getInstance().getTime();
        System.out.println("Current time => " + tanggal);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String time = df.format(tanggal);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("siswa").child(id);

        Siswa siswa = new Siswa(id,judul,deskripsi,time);

        databaseReference.setValue(siswa);
        Toast.makeText(this, "Catatan Berhasil diedit!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        bundle = getIntent().getExtras();
        String act = bundle.getString("Action");
        if(act.equals("Update")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        bundle = getIntent().getExtras();
        switch (item.getItemId()){
            case R.id.item_delete:
                deleteSiswa(bundle.getString("Id"));
                break;
            case android.R.id.home :
                super.onBackPressed();
                break;
        }
        return true;
    }
    private void deleteSiswa(String id){
        DatabaseReference dbSiswa = FirebaseDatabase.getInstance().getReference("siswa").child(id);

        dbSiswa.removeValue();
        Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
    }

}
