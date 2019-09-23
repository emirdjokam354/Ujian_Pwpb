package com.example.ujian_pwpb;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    DatabaseReference databaseSiswa;
    private ImageButton btn_tambah;
    private ListView listViewSiswa;
    private List<Siswa> siswaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Notes");

        databaseSiswa = FirebaseDatabase.getInstance().getReference("Siswa");

        btn_tambah = (ImageButton)findViewById(R.id.btn_tambah);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(HomeActivity.this, InputData.class);
                pindah.putExtra("Action","Insert");
                startActivity(pindah);
            }
        });

        listViewSiswa = findViewById(R.id.listView);
        siswaList = new ArrayList<>();

       listViewSiswa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               Siswa siswa = siswaList.get(i);

               showUpdateDialog(siswa.getSiswaId(), siswa.getJudul(), siswa.getDeskripsi());
               return true;
           }
       });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseSiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                siswaList.clear();

                for (DataSnapshot siswaSnapshot : dataSnapshot.getChildren()){
                    Siswa siswa = siswaSnapshot.getValue(Siswa.class);

                    siswaList.add(siswa);
                }
                ListSiswa adapter = new ListSiswa(HomeActivity.this, siswaList);
                listViewSiswa.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void  showUpdateDialog(final String siswaId, final String judul, final String deskripsi){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.choose_option, null);
        final Button btnUpdate = dialogView.findViewById(R.id.btnEdit);
        final Button btnHapus = dialogView.findViewById(R.id.btnHapus);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Pilih Aksi :"+judul);

        final AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(HomeActivity.this, InputData.class);
                update.putExtra("Action", "Update");
                update.putExtra("Id", siswaId);
                update.putExtra("Judul", judul);
                update.putExtra("Deskripsi", deskripsi);

                startActivity(update);

                alertDialog.dismiss();
            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSiswa(siswaId);
            }
        });

    }
    private void deleteSiswa(String id){
        DatabaseReference dbSiswa = FirebaseDatabase.getInstance().getReference("siswa").child(id);

        dbSiswa.removeValue();
        Toast.makeText(this,"Data Berhasil di Hapus!", Toast.LENGTH_SHORT).show();
    }
}
