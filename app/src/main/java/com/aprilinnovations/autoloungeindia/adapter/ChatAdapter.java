package com.aprilinnovations.autoloungeindia.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.activity.ChatActivity;
import com.aprilinnovations.autoloungeindia.datamodel.ChatModel;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.bumptech.glide.Glide;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    List<ChatModel> ChatList;
    Context context;

    private UniversalHelper helper;
    ProgressDialog progressDialog;
    private int responseCode;



    public ChatAdapter(List<ChatModel> ChatList, Context context) {
        this.ChatList = ChatList;
        this.context = context;

        helper = new UniversalHelper(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait for movement...");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_doctorChatMsg,tv_patientChatMsg;
        RelativeLayout rl_doctorChatMsg,rl_patientChatMsg,rl_patientChatImg,rl_doctorChatImg;
        ImageView iv_patientChatImg,iv_doctorChatImg;

        public MyViewHolder(View view) {
            super(view);
            tv_doctorChatMsg=view.findViewById(R.id.tv_doctorChatMsg);
            tv_patientChatMsg=view.findViewById(R.id.tv_patientChatMsg);
            rl_doctorChatMsg=view.findViewById(R.id.rl_doctorChatMsg);
            rl_doctorChatImg=view.findViewById(R.id.rl_doctorChatImg);
            iv_doctorChatImg=view.findViewById(R.id.iv_doctorChatImg);

            rl_patientChatMsg=view.findViewById(R.id.rl_patientChatMsg);
            rl_patientChatImg=view.findViewById(R.id.rl_patientChatImg);
            iv_patientChatImg=view.findViewById(R.id.iv_patientChatImg);
        }
    }



    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_layout, parent, false);

        return new ChatAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ChatAdapter.MyViewHolder holder, final int position) {

        final ChatModel model = ChatList.get(position);
        String message = model.getMessage();
        String prefrence = ChatList.get(position).getType();
        String hasImage = ChatList.get(position).getHasImage();
        final String image =ChatList.get(position).getImage();


        if (TextUtils.equals(prefrence,"0")) {
            holder.rl_patientChatMsg.setVisibility(View.GONE);

            if (hasImage.equals("true")){
                holder.rl_doctorChatImg.setVisibility(View.VISIBLE);
                holder.rl_doctorChatMsg.setVisibility(View.GONE);
            }else if (hasImage.equals("false")){
                holder.rl_doctorChatImg.setVisibility(View.GONE);
                holder.rl_doctorChatMsg.setVisibility(View.VISIBLE);
            }

            holder.rl_doctorChatImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChatActivity)context).makeImageFullScreen(image);
                }
            });

            holder.tv_doctorChatMsg.setText(message);

            Glide.with(context).load(image).into(holder.iv_doctorChatImg);

        }
        else if (TextUtils.equals(prefrence,"1")) {
            holder.rl_doctorChatMsg.setVisibility(View.GONE);


            holder.rl_patientChatImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChatActivity)context).makeImageFullScreen(image);
                }
            });
            if (hasImage.equals("true")){
                holder.rl_patientChatImg.setVisibility(View.VISIBLE);
                holder.rl_patientChatMsg.setVisibility(View.GONE);
            }else if (hasImage.equals("false")){
                holder.rl_patientChatMsg.setVisibility(View.VISIBLE);
                holder.rl_patientChatImg.setVisibility(View.GONE);
            }

            Glide.with(context).load(image).into(holder.iv_patientChatImg);
            holder.tv_patientChatMsg.setText(message);
        }

    }


    @Override
    public int getItemCount() {
        return ChatList.size();
    }
}