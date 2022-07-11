package com.haud.sctu.helper;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haud.sctu.model.CallLog;
import com.haud.sctu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallHolder> {
    private List<CallLog> callLogs = new ArrayList<>();
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private boolean selection_mode = false;
    private List<String> receivedDates = new ArrayList<>();


    @NonNull
    @Override
    public CallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_row,parent,false);
        return new CallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallHolder holder, int position) {
        CallLog currentCallLog = callLogs.get(position);
        holder.textViewSid.setText(currentCallLog.getSid());

        long millisecondsStartDateTime = currentCallLog.getStartDatetime();
        Date startDateTime = new Date(millisecondsStartDateTime);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH.mm");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");

        holder.textViewTime.setText(sdfTime.format(startDateTime));
        String formattedDate = sdfDate.format(startDateTime);
        if (receivedDates.contains(formattedDate)) {
            holder.textViewDate.setVisibility(View.GONE);
        } else {
            holder.textViewDate.setVisibility(View.VISIBLE);
            receivedDates.add(formattedDate);
            holder.textViewDate.setText(formattedDate);
        }

        if (currentCallLog.getCallType().equals("Incoming")) {
            holder.imageViewCallType.setImageResource(R.drawable.ic_incoming_call);
        } else if (currentCallLog.getCallType().equals("Missed")){
            holder.imageViewCallType.setImageResource(R.drawable.ic_missed_call);
        }

        if (currentCallLog.isCallLogUploaded()) {
            holder.imageViewCallTicks.setImageResource(R.drawable.ic_purple_ticks);
            holder.textViewSimNumber.setBackgroundResource(R.drawable.ic_purple_sim_card);
            if (currentCallLog.isCallRecordingFileUploaded()) {
                holder.imageViewCallRecording.setVisibility(View.VISIBLE);
                holder.imageViewCallRecording.setImageResource(R.drawable.ic_purple_mic);
            } else {
                holder.imageViewCallRecording.setVisibility(View.GONE);
            }
        } else {
            holder.imageViewCallTicks.setImageResource(R.drawable.ic_grey_ticks);
            holder.textViewSimNumber.setBackgroundResource(R.drawable.ic_grey_sim_card);
            if (currentCallLog.isCallRecordingFileUploaded()) {
                holder.imageViewCallRecording.setVisibility(View.VISIBLE);
                holder.imageViewCallRecording.setImageResource(R.drawable.ic_grey_mic);
            } else {
                holder.imageViewCallRecording.setVisibility(View.GONE);
            }
        }

        if (currentCallLog.isSelected()) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E0DCEC"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }
    }


    @Override
    public int getItemCount() {
        return callLogs.size();
    }

    public void setCallLogs(List<CallLog> callLogs) {
        this.callLogs = callLogs;
        notifyDataSetChanged();
    }

    public CallLog getCallLogAt(int position) {
        return callLogs.get(position);
    }

    class CallHolder extends RecyclerView.ViewHolder {
        private TextView textViewSid;
        private TextView textViewDate;
        private TextView textViewTime;
        private TextView textViewSimNumber;
        private ImageView imageViewCallType;
        private ImageView imageViewCallRecording;
        private ImageView imageViewCallTicks;
        CardView cardView = (CardView) itemView.findViewById(R.id.call_cv);

        public CallHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.call_row_date);
            textViewSid = itemView.findViewById(R.id.call_sid_tv);
            textViewTime = itemView.findViewById(R.id.call_time_tv);
            textViewSimNumber = itemView.findViewById(R.id.call_row_sim_number);
            imageViewCallType = itemView.findViewById(R.id.call_type_iv);
            imageViewCallRecording = itemView.findViewById(R.id.call_row_recording);
            imageViewCallTicks = itemView.findViewById(R.id.call_row_ticks);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (selection_mode) {
                        if (clickListener != null && position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(callLogs.get(position));
                            if (callLogs.get(position).isSelected()) {
                                callLogs.get(position).setSelected(false);
                            } else {
                                callLogs.get(position).setSelected(true);
                            }
                            notifyDataSetChanged();
                        }
                    } else {
                        // update logs
                        if (clickListener != null && position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(callLogs.get(position));
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (longClickListener != null && position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(callLogs.get(position));
                        selection_mode = true;
                        callLogs.get(position).setSelected(true);
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CallLog callLog);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(CallLog callLog);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener ) {
        this.longClickListener = longClickListener;
    }
}
