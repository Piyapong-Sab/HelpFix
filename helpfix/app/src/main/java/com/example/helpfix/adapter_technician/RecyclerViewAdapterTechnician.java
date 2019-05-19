package com.example.helpfix.adapter_technician;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.example.helpfix.R;

import java.util.List;

public class RecyclerViewAdapterTechnician extends RecyclerView.Adapter<RecyclerViewAdapterTechnician.ViewHolder > {
    Response.Listener<String> context;
    List<GetDataAdapterTechnician> mDataList;
    ImageLoader imageLoader1;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener =  listener;
    }
    public RecyclerViewAdapterTechnician(List<GetDataAdapterTechnician> getDataAdapter, Response.Listener<String> context){
        super();
        this.mDataList = getDataAdapter;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_technicain, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {
        GetDataAdapterTechnician getDataAdapter1 =  mDataList.get(position);

        Viewholder.view_idissue.setText(getDataAdapter1.getId_issue());
        Viewholder.view_Assigner_by.setText(getDataAdapter1.getAssigner_by());
        Viewholder.view_Assessment_date.setText(getDataAdapter1.getAssessment_date());
        Viewholder.view_priority.setText(getDataAdapter1.getPriority());
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view_idissue ,view_Assigner_by,view_Assessment_date,view_priority;
        public ViewHolder(View itemView) {
            super(itemView);
            view_idissue = (TextView) itemView.findViewById(R.id.view_idissue) ;
            view_Assigner_by = (TextView) itemView.findViewById(R.id.view_Assigner_by) ;
            view_Assessment_date = (TextView) itemView.findViewById(R.id.view_Assessment_date) ;
            view_priority = (TextView) itemView.findViewById(R.id.view_priority) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mlistener != null  ){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mlistener.onItemClick( position);
                        }
                    }
                }
            });
        }
    }
}