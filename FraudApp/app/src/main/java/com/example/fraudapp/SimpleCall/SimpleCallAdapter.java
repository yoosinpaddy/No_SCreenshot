package com.example.fraudapp.SimpleCall;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fraudapp.VoipCall.VoipCallAdapter;
import com.example.fraudapp.R;
import com.example.fraudapp.db.entity.SimpleCallEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

// adapter to show list of simple calls
public class SimpleCallAdapter extends ListAdapter<SimpleCallEntity, SimpleCallAdapter.MyViewHolder> {
    private static final String TAG = VoipCallAdapter.class.getSimpleName();

    private Context context;
    private SimpleCallAdapter.SimpleCallAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.note)
        TextView note;

        @BindView(R.id.dot)
        TextView dot;

        @BindView(R.id.timestamp)
        TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getNote(getLayoutPosition()).getId(), getLayoutPosition());
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongClick(getNote(getLayoutPosition()).getId(), getLayoutPosition());
                    return true;
                }
            });
        }
    }

    public SimpleCallAdapter(Context context, SimpleCallAdapter.SimpleCallAdapterListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public SimpleCallAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new SimpleCallAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleCallAdapter.MyViewHolder holder, int position) {
        SimpleCallEntity note = getNote(position);
        if (note != null) {
            holder.note.setText(note.getNote());
            // Displaying dot from HTML character code
            holder.dot.setText(Html.fromHtml("&#8226;"));

            // Changing dot color to random color
            holder.dot.setTextColor(getRandomMaterialColor("400"));

            // Formatting and displaying timestamp
            holder.timestamp.setText(formatDate(note.getTimestamp()));
        }
    }

    public SimpleCallEntity getNote(int position) {
        return getItem(position);
    }

    private static final DiffUtil.ItemCallback<SimpleCallEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<SimpleCallEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull SimpleCallEntity oldNote, @NonNull SimpleCallEntity newNote) {
                    return oldNote.getId() == newNote.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull SimpleCallEntity oldNote, @NonNull SimpleCallEntity newNote) {
                    return oldNote.getId() == newNote.getId() && oldNote.getNote().equals(newNote.getNote());
                }
            };

    /**
     * Chooses random color defined in res/array.xml
     */
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(Date timestamp) {
        try {
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(timestamp);
        } catch (Exception e) {
            // TODO - handle exception
            e.printStackTrace();
        }

        return "";
    }

    public interface SimpleCallAdapterListener {
        void onClick(int simpleCallId, int position);

        void onLongClick(int simpleCallId, int position);
    }
}
