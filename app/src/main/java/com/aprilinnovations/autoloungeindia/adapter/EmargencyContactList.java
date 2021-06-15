package com.aprilinnovations.autoloungeindia.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.datamodel.EmargencyContactModel;

import java.util.List;

public class EmargencyContactList extends RecyclerView.Adapter<EmargencyContactList.MyViewHolder> {
    List<EmargencyContactModel> emargencyContactModel;
    Context context;

    public EmargencyContactList(List<EmargencyContactModel> emargencyContactModel, Context context) {
        this.emargencyContactModel = emargencyContactModel;
        this.context = context;
    }

    @NonNull
    @Override
    public EmargencyContactList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.emargency_contact_list, viewGroup, false);

        return new EmargencyContactList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmargencyContactList.MyViewHolder holder, int position) {
         String contactNumber ="";
        EmargencyContactModel model = emargencyContactModel.get(position);
        holder.tv_contactName.setText(model.getContactName());
        contactNumber = model.getContactNiumber();
        final String finalContactNumber = contactNumber;
        holder.rv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + finalContactNumber));
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return emargencyContactModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_contactName;
        public RelativeLayout rv_call;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_contactName =itemView.findViewById(R.id.tv_contactName);
            rv_call =itemView.findViewById(R.id.rv_call);
        }
    }
}
