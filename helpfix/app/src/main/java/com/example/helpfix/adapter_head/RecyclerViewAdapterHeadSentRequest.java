package com.example.helpfix.adapter_head;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.example.helpfix.R;

import java.util.List;

public class RecyclerViewAdapterHeadSentRequest extends RecyclerView.Adapter<RecyclerViewAdapterHeadSentRequest.ViewHolder > {
    Response.Listener<String> context;
    List<GetDataAdapterHead> mDataList;
    ImageLoader imageLoader1;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener =  listener;
    }
    public RecyclerViewAdapterHeadSentRequest(List<GetDataAdapterHead> getDataAdapter, Response.Listener<String> context){
        super();
        this.mDataList = getDataAdapter;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_sentrequest_head, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {
        GetDataAdapterHead getDataAdapter1 =  mDataList.get(position);
        Viewholder.view_issueid.setText(getDataAdapter1.getId_issue());
        Viewholder.view_Assessment_by.setText(getDataAdapter1.getAssessment_by());
        Viewholder.view_stapprove_date.setText(getDataAdapter1.getApprove_date());
        Viewholder.view_Create_by.setText(getDataAdapter1.getCreate_by());
        Viewholder.view_status.setText(getDataAdapter1.getStatus());
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view_issueid ,view_Assessment_by,view_stapprove_date,view_Create_by ,view_status;
        public ViewHolder(View itemView) {
            super(itemView);
            view_issueid = (TextView) itemView.findViewById(R.id.view_issueid) ;
            view_Assessment_by = (TextView) itemView.findViewById(R.id.view_Assessment_by) ;
            view_stapprove_date = (TextView) itemView.findViewById(R.id.view_stapprove_date) ;
            view_Create_by = (TextView) itemView.findViewById(R.id.view_Create_by) ;
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