package com.example.helpfix.adapter_head;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.helpfix.R;

import java.util.List;

public class RecyclerViewAdapterHeadAssignment extends RecyclerView.Adapter<RecyclerViewAdapterHeadAssignment.ViewHolder > {
    Context context;
    List<GetDataAdapterHead> mDataList;
    ImageLoader imageLoader1;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener =  listener;
    }
    public RecyclerViewAdapterHeadAssignment(List<GetDataAdapterHead> getDataAdapter, Context context){
        super();
        this.mDataList = getDataAdapter;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_ass_head, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {
        GetDataAdapterHead getDataAdapter1 =  mDataList.get(position);
        Viewholder.view_idissue.setText(getDataAdapter1.getId_issue());
        Viewholder.view_problem.setText(getDataAdapter1.getProblem());
        Viewholder.view_recorddate.setText(getDataAdapter1.getRecordDate());
        Viewholder.view_status.setText(getDataAdapter1.getStatus());
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view_idissue ,view_problem,view_recorddate,view_status;
        public ViewHolder(View itemView) {
            super(itemView);
            view_idissue = (TextView) itemView.findViewById(R.id.view_idissue) ;
            view_problem = (TextView) itemView.findViewById(R.id.view_problem) ;
            view_recorddate = (TextView) itemView.findViewById(R.id.view_recorddate) ;
            view_status = (TextView) itemView.findViewById(R.id.view_status) ;

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