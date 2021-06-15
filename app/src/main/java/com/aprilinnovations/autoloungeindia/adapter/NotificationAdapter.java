package com.aprilinnovations.autoloungeindia.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.activity.NotificationDetails;
import com.aprilinnovations.autoloungeindia.datamodel.NotificationDataModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    List<NotificationDataModel> notificationDataModels;
    Context context;

    public NotificationAdapter(List<NotificationDataModel> notificationDataModels, Context context) {
        this.notificationDataModels = notificationDataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_item, viewGroup, false);

        return new NotificationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, final int position) {

        final NotificationDataModel model = notificationDataModels.get(position);
        holder.tv_date.setText(model.getDate());
        holder.tv_heading.setText(model.getHeading());
        holder.tv_discription.setText(model.getDescription());
        final String notificationId = notificationDataModels.get(position).getNotificationDataId();

        if (!TextUtils.isEmpty(model.getNotificationImage())) {
            Glide.with(context).load(notificationDataModels.get(position).getNotificationImage()).into(holder.iv_notification);
        }
        holder.card_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NotificationDetails.class);
                i.putExtra("notificationID",notificationId);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return notificationDataModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_date, tv_heading,tv_discription;
        public ImageView iv_notification;
        public CardView card_notification;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_heading =(TextView)itemView.findViewById(R.id.tv_heading);
            tv_date =(TextView)itemView.findViewById(R.id.tv_date);
            tv_discription =(TextView)itemView.findViewById(R.id.tv_discription);
            card_notification =(CardView) itemView.findViewById(R.id.card_notification);
            iv_notification =(ImageView) itemView.findViewById(R.id.iv_notification);
        }
    }
}
