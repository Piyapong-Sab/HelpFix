package com.example.helpfix.adapter_head;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helpfix.R;

import java.util.List;

public class RecyclerViewAdapterHeadSentApproval extends RecyclerView.Adapter<RecyclerViewAdapterHeadSentApproval.ViewHolder > {
    Context context;
    List<GetDataAdapterHead> mDataList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener =  listener;
    }
    public RecyclerViewAdapterHeadSentApproval(List<GetDataAdapterHead> getDataAdapter, Context context){
        super();
        this.mDataList = getDataAdapter;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_sentapprove, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {
        GetDataAdapterHead getDataAdapter1 =  mDataList.get(position);

        Viewholder.view_issueid.setText(getDataAdapter1.getId_issue());
        Viewholder.view_Assessment_by.setText(getDataAdapter1.getAssessment_by());
        Viewholder.view_Evaluate_date.setText(getDataAdapter1.getEvaluate_date());
        Viewholder.view_price.setText(getDataAdapter1.getPrice());
        Viewholder.view_statusapp.setText(getDataAdapter1.getStatus());
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view_issueid ,view_Assessment_by,view_Evaluate_date,view_price ,view_statusapp;
        public ViewHolder(View itemView) {
            super(itemView);
            view_issueid = (TextView) itemView.findViewById(R.id.view_issueid) ;
            view_Assessment_by = (TextView) itemView.findViewById(R.id.view_Assessment_by) ;
            view_Evaluate_date = (TextView) itemView.findViewById(R.id.view_Evaluate_date) ;
            view_price = (TextView) itemView.findViewById(R.id.view_price) ;
            view_statusapp = (TextView) itemView.findViewById(R.id.view_statusapp) ;

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