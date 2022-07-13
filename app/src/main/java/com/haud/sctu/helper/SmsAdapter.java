package com.haud.sctu.helper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.haud.sctu.R;
import com.haud.sctu.model.SmsLog;
import com.haud.sctu.viewmodel.SmsViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsHolder> {
    private List<SmsLog> smsLogs = new ArrayList<>();
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private boolean selection_mode = false;
    public List<String> allSmsOa = new ArrayList<>();

    @NonNull
    @Override
    public SmsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_row,parent,false);
        return new SmsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsHolder holder, int position) {
        SmsLog currentSmsLog = smsLogs.get(position);
        String currentOa = currentSmsLog.getOa();
        holder.textViewSid.setText(currentOa);
        holder.textViewContent.setText(currentSmsLog.getBody());

        long millisecondsDateTime = currentSmsLog.getReceived();
        Date receivedDate = new Date(millisecondsDateTime);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        holder.textViewDateTime.setText(dateFormat.format(receivedDate));

        if (allSmsOa.contains(currentOa)) {
            if (currentSmsLog.isSelected()) {
                holder.cardView.setVisibility(View.VISIBLE);
            } else {
                holder.cardView.setVisibility(View.GONE);
            }
        } else {
            allSmsOa.add(currentOa);
            holder.cardView.setVisibility(View.VISIBLE);
        }

        if (currentSmsLog.isSelected()) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E0DCEC"));
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

    class SmsHolder extends RecyclerView.ViewHolder {
        private TextView textViewDateTime;
        private TextView textViewSid;
        private TextView textViewContent;
        private TextView textViewSmsBySidCount;
        CardView cardView = (CardView) itemView.findViewById(R.id.sms_cv);

        public SmsHolder(View itemView) {
            super(itemView);
            textViewDateTime = itemView.findViewById(R.id.sms_date_tv);
            textViewSid = itemView.findViewById(R.id.sms_sid_tv);
            textViewContent = itemView.findViewById(R.id.sms_content_tv);
            textViewSmsBySidCount = itemView.findViewById(R.id.total_sms_by_sid_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (selection_mode) {
                        allSmsOa.clear();
                        if (clickListener != null && position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(smsLogs.get(position));
                            if (smsLogs.get(position).isSelected()) {
                                smsLogs.get(position).setSelected(false);
                            } else {
                                smsLogs.get(position).setSelected(true);
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
                    allSmsOa.clear();
                    int position = getAdapterPosition();
                    if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(smsLogs.get(position));
                        selection_mode = true;
                        smsLogs.get(position).setSelected(true);
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
