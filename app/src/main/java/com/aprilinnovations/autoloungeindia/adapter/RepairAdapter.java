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

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.activity.ConfirmServiceActivity;
import com.aprilinnovations.autoloungeindia.datamodel.ServiceModel;

import java.util.List;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.MyViewHolder>  {

    List<ServiceModel> repairModels;
    Context context;

    public RepairAdapter(List<ServiceModel> repairModels, Context context) {
        this.repairModels = repairModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RepairAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_item, parent, false);

        return new RepairAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepairAdapter.MyViewHolder holder, int position) {

        ServiceModel model = repairModels.get(position);
        final String ServiceName = repairModels.get(position).getServiceName();
        final String ServiceType = repairModels.get(position).getServiceType();
        holder.tv_modelname.setText(model.getServiceName());
        holder.rv_modelitem.setOnClickListener(new View.OnClickListener() {
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
        return repairModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_modelname;
        public RelativeLayout rv_modelitem;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_modelname =(TextView)itemView.findViewById(R.id.tv_modelname);
            rv_modelitem =(RelativeLayout) itemView.findViewById(R.id.rv_modelitem);
        }
    }
}
