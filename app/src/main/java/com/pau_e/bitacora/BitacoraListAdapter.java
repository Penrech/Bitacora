package com.pau_e.bitacora;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by pau_e on 06/03/2018.
 */

public class BitacoraListAdapter extends ArrayAdapter<logItem> {

    public BitacoraListAdapter(@NonNull Context context, int resource, @NonNull List<logItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1. Crear un nou View si és necessari (no cal si convertView no és null)
        View root = convertView; //arrel d'un item de la llista
        if (root == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            root = inflater.inflate(R.layout.log_item, parent, false);
        }

        TextView fecha = root.findViewById(R.id.fecha);
        TextView hora = root.findViewById(R.id.hora);
        TextView log = root.findViewById(R.id.logEntry);
        logItem item = getItem(position);

        fecha.setText(item.getDateString());
        hora.setText(item.getHourString());
        log.setText(item.getLog());


        return root;

    }
}
