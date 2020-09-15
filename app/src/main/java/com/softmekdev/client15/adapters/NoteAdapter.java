package com.softmekdev.client15.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.softmekdev.client15.R;
import com.softmekdev.client15.models.Note;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {
    Context context;
    private OnItemClickedListener listener;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder noteHolder, int i, @NonNull Note note) {

        /*Bind data to views*/
        noteHolder.title.setText(note.getTitle());
        noteHolder.description.setText(note.getDescription());
        noteHolder.priority.setText(String.valueOf(note.getPriority()));
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*Tell the adapter which view to inflate*/
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_item, parent, false);
        return new NoteHolder(view);
    }

    public void setOnItemCickListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    /*Create an interface */
    public interface OnItemClickedListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    /*Create the ViewHolder*/
    class NoteHolder extends RecyclerView.ViewHolder {
        TextView title, description, priority;

        //constructor matching the super
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            /* Get The Views*/
            title = itemView.findViewById(R.id.title_textview);
            description = itemView.findViewById(R.id.description_textview);
            priority = itemView.findViewById(R.id.priority);

            /*Implement click listener*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }
                }
            });
        }


    }
}
