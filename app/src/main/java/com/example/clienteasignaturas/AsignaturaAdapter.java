package com.example.clienteasignaturas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AsignaturaAdapter extends
        RecyclerView.
                Adapter<AsignaturaAdapter.AsignaturaViewHolder> {

    public static final String TAG_LOG = AsignaturaAdapter.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_NOMBRE = "NOMBRE";
    public static final String EXTRA_POSICION = "POSICION";

    private String queryUri = Contract.CONTENT_URI.toString();

    private static final String[] PROJECTION = new String[]{Contract.CONTENT_PATH};

    private String selectClause = null;
    private String selectArgs[] = null;
    private String sortOrder = "ASC";

    private  final LayoutInflater inflater;

    private Context context;

    class AsignaturaViewHolder extends RecyclerView.ViewHolder {
        public final TextView asignaturaItemView;
        Button btn_delete;
        Button btn_edit;

        public AsignaturaViewHolder(View itemView) {
            super(itemView);
            asignaturaItemView =
                    (TextView) itemView.findViewById(R.id.tv_nombre);

            btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
            btn_edit = (Button) itemView.findViewById(R.id.btn_edit);
        }
    }

    public AsignaturaAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public AsignaturaAdapter.AsignaturaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.asignatura_list_item,
                viewGroup, false);

        return new AsignaturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AsignaturaAdapter.AsignaturaViewHolder asignaturaViewHolder, int i) {

        final AsignaturaViewHolder holder = asignaturaViewHolder;

        String nombre = "";
        int id = -1;

        Cursor cursor = context.getContentResolver().query(
                Uri.parse(queryUri),
                null,
                null,
                null,
                sortOrder
        );

        if (cursor != null){
            if (cursor.moveToPosition(i)) {
                Log.d(TAG_LOG, "Posicion: " + i);
                int indexAsignatura =
                        cursor.getColumnIndex(Contract.Asignaturas.NOMBRE);

                nombre = cursor.getString(indexAsignatura);

                Log.d(TAG_LOG, "ASIGNATURA: " + nombre);

                holder.asignaturaItemView.setText(nombre);

                Log.d(TAG_LOG, "Base de datos: " + DatabaseUtils.dumpCursorToString(cursor));

                int indexId = cursor.getColumnIndex(Contract.Asignaturas.ID);
                id = cursor.getInt(indexId);

            } else {
                holder.asignaturaItemView.setText(R.string.error_asignatura);
            }
            cursor.close();
        } else {
            Log.d(TAG_LOG, "Error al cargar los datos: onBindViewHolder()");
        }

        final String finalNombre = nombre;
        final int finalId = id;
        asignaturaViewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNombreAcitivity.class);

                intent.putExtra(EXTRA_ID, finalId);
                intent.putExtra(EXTRA_POSICION, holder.getAdapterPosition());
                intent.putExtra(EXTRA_NOMBRE, finalNombre);

                ((Activity) context).startActivityForResult(
                        intent, MainActivity.NOMBRE_EDIT);
            }
        });

        asignaturaViewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectArgs = new String[]{String.valueOf(finalId)};

                int eliminado = context.getContentResolver().
                        delete(
                                Contract.CONTENT_URI,
                                Contract.CONTENT_PATH,
                                selectArgs
                        );
                Log.d(TAG_LOG, "btn_delete: " + eliminado);
                if (eliminado > 0) {
                    notifyItemRemoved(holder.getAdapterPosition());
                    //notifyItemRangeRemoved(holder.getAdapterPosition(),getItemCount());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Cursor cursor = context.getContentResolver().
                query(
                        Contract.ROW_COUNT_URI,
                        new String[]{"COUNT(*) AS count"},
                        selectClause,
                        selectArgs,
                        sortOrder
                );
        try{
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            Log.d(TAG_LOG, "COUNT getItemCount() " + count);
            cursor.close();
            return count;
        }catch (Exception e) {
            Log.d(TAG_LOG, "Error al realizar COUNT: " + e);
            return -1;
        }
    }
}
