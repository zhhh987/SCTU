package com.haud.sctu;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneHolder> {
    private List<PhoneLog> phoneLogs = new ArrayList<>();
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private boolean selection_mode = false;


    @NonNull
    @Override
    public PhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_row,parent,false);
        return new PhoneHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneHolder holder, int position) {
        PhoneLog currentPhoneLog = phoneLogs.get(position);
        holder.textViewSid.setText(currentPhoneLog.getSid());
        holder.textViewDuration.setText(currentPhoneLog.getDuration());
        holder.textViewSimNumber.setText(currentPhoneLog.getSimCardNumber());
        Resources res = holder.itemView.getResources();

        if (currentPhoneLog.getIsSelected()) {
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }

        if (currentPhoneLog.getIsMissedCall()) {
            holder.imageViewIsMissedCall.setImageDrawable(res.getDrawable(R.drawable.ic_missed_call));
        } else {
            holder.imageViewIsMissedCall.setImageDrawable(res.getDrawable(R.drawable.ic_incoming_call));
        }
    }

    @Override
    public int getItemCount() {
        return phoneLogs.size();
    }

    public void setPhoneLogs(List<PhoneLog> phoneLogs) {
        this.phoneLogs = phoneLogs;
        notifyDataSetChanged();
    }

    public PhoneLog getPhoneLogAt(int position) {
        return phoneLogs.get(position);
    }

    class PhoneHolder extends RecyclerView.ViewHolder {
        private TextView textViewSid;
        private TextView textViewDuration;
        private TextView textViewSimNumber;
        private ImageView imageViewIsMissedCall;
        CardView cardView = (CardView) itemView.findViewById(R.id.incoming_call_cv);

        public PhoneHolder(View itemView) {
            super(itemView);
            textViewSid = itemView.findViewById(R.id.incoming_call_sid_tv);
            textViewDuration = itemView.findViewById(R.id.incoming_call_duration_tv);
            textViewSimNumber = itemView.findViewById(R.id.incoming_call_sim_number_tv);
            imageViewIsMissedCall = itemView.findViewById(R.id.is_missed_call_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (selection_mode) {
                        if (clickListener != null && position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(phoneLogs.get(position));
                            if (phoneLogs.get(position).getIsSelected()) {
                                phoneLogs.get(position).setIsSelected(false);
                            } else {
                                phoneLogs.get(position).setIsSelected(true);
                            }
                            notifyDataSetChanged();
                        }
                    } else {
                        // update logs
                        if (clickListener != null && position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(phoneLogs.get(position));
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(phoneLogs.get(position));
                        selection_mode = true;
                        phoneLogs.get(position).setIsSelected(true);
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PhoneLog phoneLog);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(PhoneLog phoneLog);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener ) {
        this.longClickListener = longClickListener;
    }
}
