package com.example.micontentprovider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
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
            if (cursor.moveToFirst()) {
                int indexAsignatura =
                        cursor.getColumnIndex(Contract.Asignaturas.NOMBRE);
                nombre = cursor.getString(indexAsignatura);
                holder.asignaturaItemView.setText(nombre);

            } else {
                holder.asignaturaItemView.setText(R.string.error_asignatura);
            }
            cursor.close();
        } else {
            Log.d(TAG_LOG, "Error al cargar los datos: onBindViewHolder()");
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
