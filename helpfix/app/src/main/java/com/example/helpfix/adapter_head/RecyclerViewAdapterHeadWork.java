package com.example.helpfix.adapter_head;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.helpfix.R;

import java.util.List;

public class RecyclerViewAdapterHeadWork extends RecyclerView.Adapter<RecyclerViewAdapterHeadWork.ViewHolder > {
    Response.Listener<String> context;
    List<GetDataAdapterHead> mDataList;

    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener =  listener;
    }
    public RecyclerViewAdapterHeadWork(List<GetDataAdapterHead> getDataAdapter, Response.Listener<String> context){
        super();
        this.mDataList = getDataAdapter;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_work_head, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {
        GetDataAdapterHead getDataAdapter1 =  mDataList.get(position);

        Viewholder.view_issueid.setText(getDataAdapter1.getId_issue());
        Viewholder.view_assessment_date.setText(getDataAdapter1.getAssessment_date());
        Viewholder.view_assessment_by.setText(getDataAdapter1.getAssessment_by());
        Viewholder.view_priority.setText(getDataAdapter1.getPriority());
        Viewholder.view_status.setText(getDataAdapter1.getStatus());

    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view_issueid ,view_assessment_date,view_assessment_by,view_priority , view_status;

        public ViewHolder(View itemView) {
            super(itemView);
            view_issueid = (TextView) itemView.findViewById(R.id.view_issueid) ;
            view_assessment_date = (TextView) itemView.findViewById(R.id.view_assessment_date) ;
            view_assessment_by = (TextView) itemView.findViewById(R.id.view_assessment_by) ;
            view_priority = (TextView) itemView.findViewById(R.id.view_priority) ;
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