package com.haud.sctu;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsHolder> {
    private List<SmsLog> smsLogs = new ArrayList<>();
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private boolean selection_mode = false;

    @NonNull
    @Override
    public SmsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_row,parent,false);
        return new SmsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsHolder holder, int position) {
        SmsLog currentSmsLog = smsLogs.get(position);
        holder.textViewDate.setText(currentSmsLog.getDate());
        holder.textViewTime.setText(currentSmsLog.getTime());
        holder.textViewInfo.setText(currentSmsLog.getInfo());

        if (currentSmsLog.getIsSelected()) {
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return smsLogs.size();
    }

    public void setSmsLogs(List<SmsLog> smsLogs) {
        this.smsLogs = smsLogs;
        notifyDataSetChanged();
    }

    public SmsLog getSmsLogAt(int position) {
        return smsLogs.get(position);
    }

    class SmsHolder extends RecyclerView.ViewHolder {
        private TextView textViewDate;
        private TextView textViewTime;
        private TextView textViewInfo;
        CardView cardView = (CardView) itemView.findViewById(R.id.card_view);

        public SmsHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.logs_date_tv);
            textViewTime = itemView.findViewById(R.id.logs_time_tv);
            textViewInfo = itemView.findViewById(R.id.logs_info_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (selection_mode) {
                        if (clickListener != null && position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(smsLogs.get(position));
                            if (smsLogs.get(position).getIsSelected()) {
                                smsLogs.get(position).setIsSelected(false);
                            } else {
                                smsLogs.get(position).setIsSelected(true);
                            }
                            notifyDataSetChanged();
                        }
                    } else {
                        // update logs
                        if (clickListener != null && position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(smsLogs.get(position));
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(smsLogs.get(position));
                        selection_mode = true;
                        smsLogs.get(position).setIsSelected(true);
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(SmsLog smsLog);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(SmsLog smsLog);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener ) {
        this.longClickListener = longClickListener;
    }
}
