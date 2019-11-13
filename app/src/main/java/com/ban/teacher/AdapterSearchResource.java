package com.ban.teacher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSearchResource extends
        RecyclerView.Adapter<AdapterSearchResource.ViewHolder>{
    Context context;
    ArrayList<ListResource> resources;

    public AdapterSearchResource(Context context, ArrayList<ListResource> resources){
        this.context = context;
        this.resources = resources;
    }

    @NonNull
    @Override
    public AdapterSearchResource.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AdapterSearchResource.ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_assignment_recycleview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchResource.ViewHolder viewHolder, final int i) {
        viewHolder.resourceTitle.setText(resources.get(i).getA1_resource_title());
        viewHolder.resourceLink.setText(resources.get(i).getA2_resource_link());
    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView resourceTitle, resourceLink;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resourceTitle = itemView.findViewById(R.id.adapter_resource_recyclerview_title);
            resourceLink = itemView.findViewById(R.id.adapter_resource_recyclerview_link);
        }

    }

}
