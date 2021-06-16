package com.aprilinnovations.autoloungeindia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.datamodel.Model;
import com.aprilinnovations.autoloungeindia.activity.SelectVarientActivity;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.MyViewHolder>  {

    List<Model> models;
    Context context;
    UniversalHelper helper;



    public ModelAdapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
        this.helper = new UniversalHelper(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_item, parent, false);

        return new ModelAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Light.ttf");
        holder.tv_modelname.setTypeface(createFromAsset);

        final Model model = models.get(position);
        holder.tv_modelname.setText(model.getModel());
        holder.rv_modelitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,SelectVarientActivity.class );
                //i.putExtra("modelName", model.getModel());
                helper.savePreferences("carModel", model.getModel());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
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
