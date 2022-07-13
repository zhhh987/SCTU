package com.haud.sctu.helper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haud.sctu.R;
import com.haud.sctu.model.SmsLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class SmsByOaAdapter extends RecyclerView.Adapter<SmsByOaAdapter.SmsByOaHolder> {

    private List<SmsLog> smsLogs = new ArrayList<>();
    private OnOaItemClickListener oaClickListener;
    private OnOaItemLongClickListener oaLongClickListener;
    private boolean selection_mode = false;
    public List<String> receivedDates = new ArrayList<>();


    @NonNull
    @Override
    public SmsByOaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_by_oa_row,parent,false);
        return new SmsByOaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsByOaHolder holder, int position) {
        SmsLog currentSmsLog = smsLogs.get(position);

        holder.textViewSimSlot.setText(String.valueOf(currentSmsLog.getDestinationSimSlot()));
        holder.textViewMessage.setText(currentSmsLog.getBody());

        long millisecondsDateTime = currentSmsLog.getReceived();
        Date receiveDateTime = new Date(millisecondsDateTime);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH.mm");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");
        holder.textViewTime.setText(sdfTime.format(receiveDateTime));

        String formattedDate = sdfDate.format(receiveDateTime);
        if (receivedDates.contains(formattedDate)) {
            if (currentSmsLog.isSelected()) {
                holder.textViewDate.setVisibility(View.VISIBLE);
            } else {
                holder.textViewDate.setVisibility(View.GONE);
            }
        } else {
            holder.textViewDate.setVisibility(View.VISIBLE);
            receivedDates.add(formattedDate);
            holder.textViewDate.setText(formattedDate);
        }

        if (currentSmsLog.isUploaded()) {
            holder.imageViewTicks.setImageResource(R.drawable.ic_purple_ticks);
            holder.textViewSimSlot.setBackgroundResource(R.drawable.ic_purple_sim_card);
        } else {
            holder.imageViewTicks.setImageResource(R.drawable.ic_grey_ticks);
            holder.textViewSimSlot.setBackgroundResource(R.drawable.ic_grey_sim_card);
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

    public SmsLog getSmsLogAt(int position) {
        return smsLogs.get(position);
    }

    class SmsByOaHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;
        private TextView textViewTime;
        private ImageView imageViewTicks;
        private TextView textViewSimSlot;
        private TextView textViewDate;
        CardView cardView = (CardView) itemView.findViewById(R.id.sms_by_oa_card_view);

        public SmsByOaHolder(View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.message_body);
            textViewTime = itemView.findViewById(R.id.sms_row_time);
            imageViewTicks = itemView.findViewById(R.id.sms_row_ticks);
            textViewSimSlot = itemView.findViewById(R.id.sms_row_sim_number);
            textViewDate = itemView.findViewById(R.id.sms_by_oa_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (selection_mode) {
                        receivedDates.clear();
                        if (oaClickListener != null && position != RecyclerView.NO_POSITION) {
                            oaClickListener.onOaItemClick(smsLogs.get(position));
                            if (smsLogs.get(position).isSelected()) {
                                smsLogs.get(position).setSelected(false);
                            } else {
                                smsLogs.get(position).setSelected(true);
                            }
                            notifyDataSetChanged();
                        }
                    } else {
                        // update logs
                        if (oaClickListener != null && position != RecyclerView.NO_POSITION) {
                            oaClickListener.onOaItemClick(smsLogs.get(position));
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    receivedDates.clear();
                    int position = getAdapterPosition();
                    if (oaLongClickListener != null && position != RecyclerView.NO_POSITION) {
                        oaLongClickListener.onOaItemLongClick(smsLogs.get(position));
                        selection_mode = true;
                        smsLogs.get(position).setSelected(true);
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });

        }
    }

    public interface OnOaItemClickListener {
        void onOaItemClick(SmsLog smsLog);
    }

    public void setOnOaItemClickListener(OnOaItemClickListener oaClickListener) {
        this.oaClickListener = oaClickListener;
    }

    public interface OnOaItemLongClickListener {
        void onOaItemLongClick(SmsLog smsLog);
    }

    public void setOnOaItemLongClickListener(OnOaItemLongClickListener oaLongClickListener ) {
        this.oaLongClickListener = oaLongClickListener;
    }

}
