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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    private List<SmsLog> smsLogs = new ArrayList<>();
    private List<String> receivedDates = new ArrayList<>();


    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_search_results_row,parent,false);
        return new SearchHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        SmsLog currentSmsLog = smsLogs.get(position);
        holder.textViewMessage.setText(currentSmsLog.getBody());
        holder.textViewSimSlot.setText(String.valueOf(currentSmsLog.getDestinationSimSlot()));

        long millisecondsDateTime = currentSmsLog.getReceived();
        Date receiveDateTime = new Date(millisecondsDateTime);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH.mm");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");
        holder.textViewTime.setText(sdfTime.format(receiveDateTime));

        String formattedDate = sdfDate.format(receiveDateTime);

        /*
        if (receivedDates.contains(formattedDate)) {
            holder.textViewDate.setVisibility(View.GONE);
        } else {
            holder.textViewDate.setVisibility(View.VISIBLE);
            receivedDates.add(formattedDate);
            holder.textViewDate.setText(formattedDate);
        }

         */

        if (currentSmsLog.isUploaded()) {
            holder.imageViewTicks.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewTicks.setVisibility(View.GONE);
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

    class SearchHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;
        private TextView textViewTime;
        private ImageView imageViewTicks;
        private TextView textViewSimSlot;
        //private TextView textViewDate;
        CardView cardView = (CardView) itemView.findViewById(R.id.sms_search_card_view);

        public SearchHolder(View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.sms_search_message_body);
            textViewTime = itemView.findViewById(R.id.sms_search_message_time);
            imageViewTicks = itemView.findViewById(R.id.sms_search_ticks);
            textViewSimSlot = itemView.findViewById(R.id.sms_search_sim_number);
            //textViewDate = itemView.findViewById(R.id.sms_by_oa_date);
        }
    }
}
