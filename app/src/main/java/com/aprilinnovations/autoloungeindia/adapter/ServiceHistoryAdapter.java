package com.aprilinnovations.autoloungeindia.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.activity.ChatActivity;
import com.aprilinnovations.autoloungeindia.activity.RequestReceivedActivity;
import com.aprilinnovations.autoloungeindia.datamodel.ServiceHistoryModel;

import java.util.List;

public class ServiceHistoryAdapter extends RecyclerView.Adapter<ServiceHistoryAdapter.MyViewHolder>  {

    List<ServiceHistoryModel> serviceHistoryModels;
    Context context;

    public ServiceHistoryAdapter(List<ServiceHistoryModel> serviceHistoryModels, Context context) {
        this.serviceHistoryModels = serviceHistoryModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item, parent, false);

        return new ServiceHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHistoryAdapter.MyViewHolder holder, final int position) {

        final ServiceHistoryModel model = serviceHistoryModels.get(position);


        holder.tv_type.setText(model.getType());
        holder.tv_date.setText(serviceHistoryModels.get(position).getDate());
        holder.tv_ongoing.setText(model.getStatus());
        final String UserServiceChatId = model.getUserServiceChatId();

        if (TextUtils.equals(model.getType(), "Servicing")){

            holder.tv_status.setText("Service status");
            holder.ll_card.setBackground(context.getResources().getDrawable(R.drawable.blue_card));

        }

        else {

            holder.tv_status.setText("Repair status");
            holder.ll_card.setBackground(context.getResources().getDrawable(R.drawable.green_card));
        }

        final String serviceType = model.getStatus();

        holder.ll_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (serviceType.equalsIgnoreCase("RECEIVED")){
                    Intent intent = new Intent(context, RequestReceivedActivity.class);
                    context.startActivity(intent);
                }else if(serviceType.equalsIgnoreCase("PENDING")){
                    Toast.makeText(context,"You'll get a confirmation from our side soon.", Toast.LENGTH_SHORT).show();
                }
                else if (serviceType.equalsIgnoreCase("ACCEPTED") || serviceType.equals("ONGOING")|| serviceType.equals("COMPLETED") || serviceType.equals("REJECTED") ){
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("activity","serviceHistory");
                    intent.putExtra("userChatId",UserServiceChatId);
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceHistoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_type, tv_date, tv_status, tv_ongoing;
        public LinearLayout ll_card;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_type =(TextView)itemView.findViewById(R.id.tv_type);
            tv_date =(TextView)itemView.findViewById(R.id.tv_date);
            tv_status =(TextView)itemView.findViewById(R.id.tv_status);
            tv_ongoing =(TextView)itemView.findViewById(R.id.tv_ongoing);
            ll_card =(LinearLayout) itemView.findViewById(R.id.ll_card);

        }
    }


}
