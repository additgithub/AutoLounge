package com.aprilinnovations.autoloungeindia.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.activity.ConfirmServiceActivity;
import com.aprilinnovations.autoloungeindia.datamodel.ServiceModel;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder>  {

    List<ServiceModel> serviceModels;
    Context context;

    public ServiceAdapter(List<ServiceModel> serviceModels, Context context) {
        this.serviceModels = serviceModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.variant_item, parent, false);

        return new ServiceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.MyViewHolder holder, int position) {

        ServiceModel model = serviceModels.get(position);
        final String ServiceName = serviceModels.get(position).getServiceName();
        final String ServiceType = serviceModels.get(position).getServiceType();
        holder.tv_variantname.setText(model.getServiceName());
        holder.rv_variantitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ConfirmServiceActivity.class );
                i.putExtra("serviceName",ServiceName);
                i.putExtra("serviceType",ServiceType);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_variantname;
        public RelativeLayout rv_variantitem;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_variantname =(TextView)itemView.findViewById(R.id.tv_variantname);
            rv_variantitem =(RelativeLayout) itemView.findViewById(R.id.rv_variantitem);

        }
    }
}
