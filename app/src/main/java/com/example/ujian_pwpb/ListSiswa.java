package com.example.ujian_pwpb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListSiswa extends ArrayAdapter<Siswa> {

    private Activity context;
    private List<Siswa> siswaList;

    public ListSiswa(Activity context, List<Siswa> siswaList){
        super(context, R.layout.list_view, siswaList);
        this.context = context;
        this.siswaList = siswaList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_view, null, true);

        TextView time = listViewItem.findViewById(R.id.time);
        TextView judul = listViewItem.findViewById(R.id.txt_judul);
        TextView deskripsi = listViewItem.findViewById(R.id.txt_deskrpsi);

        Siswa siswa = siswaList.get(position);

        time.setText(siswa.getTime());
        judul.setText(siswa.getJudul());
        deskripsi.setText(siswa.getDeskripsi());

        return listViewItem;
    }
}
