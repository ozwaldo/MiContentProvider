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

public class AsignaturaAdapter extends RecyclerView.Adapter<AsignaturaAdapter.AsinaturaViewHolder> {

    public static final String TAG_LOG = AsignaturaAdapter.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_NOMBRE = "NOMBRE";
    public static final String EXTRA_POSICION = "POSICION";

    private String queryUri = Contract.CONTENT_URI.toString();

    private static final String[] projection = new String[]{
            Contract.CONTENT_PATH};

    private String selectionClause = null;
    private String selectionArgs[] = null;
    private String sortOrder = "ASC";

    private final LayoutInflater inflater;
    private Context context;


    class AsinaturaViewHolder extends RecyclerView.ViewHolder {
        public final TextView asignaturaItemView;
        Button btn_delete;
        Button btn_edit;

        public AsinaturaViewHolder(View itemView) {
            super(itemView);
            asignaturaItemView = (TextView) itemView.findViewById(R.id.tv_nombre);
            btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
            btn_edit = (Button) itemView.findViewById(R.id.btn_edit);
        }
    }

    public AsignaturaAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AsinaturaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.asignatura_item, viewGroup,
                false);
        return new AsinaturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsinaturaViewHolder asinaturaViewHolder, int i) {
        final AsinaturaViewHolder holder = asinaturaViewHolder;

        String nombre = "";
        int id = -1;

        Cursor cursor =
                context.getContentResolver().query(
                        Uri.parse(queryUri),
                        null,
                        null,
                        null,
                        sortOrder
                );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int indexAsignatura =
                        cursor.getColumnIndex(Contract.Asignaturas.NOMBRE);
                nombre = cursor.getString(indexAsignatura);
                holder.asignaturaItemView.setText(nombre);
                int indexId = cursor.getColumnIndex(Contract.Asignaturas.ID);
                id = cursor.getInt(indexId);
            } else {
                holder.asignaturaItemView.setText(R.string.error_asignatura);
            }
            cursor.close();
        } else {
            Log.d(TAG_LOG, "Error onBindViewHolder");
        }
    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
