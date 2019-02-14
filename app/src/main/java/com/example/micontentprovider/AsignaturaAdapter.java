package com.example.micontentprovider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AsignaturaAdapter extends
        RecyclerView.
                Adapter<AsignaturaAdapter.AsignaturaViewHolder> {

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

    @NonNull
    @Override
    public AsignaturaAdapter.AsignaturaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AsignaturaAdapter.AsignaturaViewHolder asignaturaViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
