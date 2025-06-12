package com.example.mymental;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private final List<String> buttonLabels;
    private final Activity activity;

    public ButtonAdapter(List<String> buttonLabels, Activity activity) {
        this.buttonLabels = buttonLabels;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_button, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        String label = buttonLabels.get(position);
        holder.button.setText(label);

        holder.button.setOnClickListener(v -> {
            if (label.equals("Camera")) {
                Intent intent = new Intent(activity, CameraActivity.class);
                activity.startActivity(intent);
            } else {
                // Handle other buttons if needed
            }
        });

    }

    @Override
    public int getItemCount() {
        return buttonLabels.size();
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.itemButton);
        }
    }
}
