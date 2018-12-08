package com.android.example.bakingtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<Step> Steps;
    private final Context ThisContext;
    private final OnStepClickListener ThisOnStepClickListener;
    private int Position = 0;

    RecipeAdapter(Context context) {
        Steps = new ArrayList<>();
        ThisContext = context;
        ThisOnStepClickListener = (OnStepClickListener) context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView TextShortDescription;
        private final ImageView ImageDone;

        ViewHolder(ConstraintLayout view) {
            super(view);
            TextShortDescription = view.findViewById(R.id.text_short_description);
            ImageDone = view.findViewById(R.id.image_done);
            view.setOnClickListener(this);
        }

        TextView getTextShortDescription() {
            return TextShortDescription;
        }

        ImageView getImageDone() {
            return ImageDone;
        }

        @Override
        public void onClick(View view) {
            Position = getAdapterPosition();
            ThisOnStepClickListener.onStepClicked(Position);
            notifyDataSetChanged();
        }
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(ThisContext).inflate(R.layout.
                item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextShortDescription().setText(Steps.get(position).getShortDescription());
        ImageView imageDone = holder.getImageDone();
        if (ThisContext.getResources().getBoolean(R.bool.is_tablet)) if (position ==
                Position) imageDone.setVisibility(View.VISIBLE); else imageDone.
                setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return Steps.size();
    }

    void setSteps(ArrayList<Step> steps) {
        Steps = steps;
    }

    public interface OnStepClickListener {
        void onStepClicked(int position);
    }

    int getPosition() {
        return Position;
    }

    void setPosition(int position) {
        Position = position;
    }
}
