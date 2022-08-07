package model;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Note> notes = Note.getAll();
    private OnItemClickListener itemClickListener;
    private Fragment fragment;
    private int menuPosition;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ListAdapter(List<Note> notes, Fragment fragment){
        this.notes = notes;
        this.fragment = fragment;
    }

    private void registerContextMenu(View itemView){
        if (fragment != null){
            fragment.registerForContextMenu(itemView);
        }

    }

    public int getMenuPosition() {
        return menuPosition;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView cardTitle;
        private TextView cardText;
        private TextView cardDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.card_title);
            cardText = itemView.findViewById(R.id.card_text);
            cardDate = itemView.findViewById(R.id.card_date);
            registerContextMenu(itemView);

            cardTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                     itemClickListener.onItemClick(view, position);
                }
            });
            cardTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu(200,100);
                    return true;
                }
            });
        }

        public void setData(int position){
            if(notes.get(0) != null){
                cardTitle.setText(notes.get(position).getNoteTitle());
                cardText.setText(notes.get(position).getNoteText());
                cardDate.setText((notes.get(position).getNoteCompDate()));
            }
        }

    }

}
