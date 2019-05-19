package com.example.helpfix.adapter_manager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helpfix.R;

import java.util.List;

public class RecyclerViewAdapterManager extends RecyclerView.Adapter<RecyclerViewAdapterManager.ViewHolder > {
    private Context context;
    private List<GetDataAdapterManager> mDataList;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener =  listener;
    }
    public RecyclerViewAdapterManager(List<GetDataAdapterManager> getDataAdapter, Context context){
        super();
        this.mDataList = getDataAdapter;
        this.context = context;
    }
    @Override
    public RecyclerViewAdapterManager.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_manager, parent, false);
        RecyclerViewAdapterManager.ViewHolder viewHolder = new RecyclerViewAdapterManager.ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewAdapterManager.ViewHolder Viewholder, int position) {
        GetDataAdapterManager currentItem =  mDataList.get(position);

        Viewholder.view_issueid.setText(currentItem.getId_issue());
        Viewholder.view_staprove.setText(currentItem.getSent_approve_by());
        Viewholder.view_stapprove_date.setText(currentItem.getApprove_date());
        Viewholder.view_price.setText(currentItem.getPrice());
        Viewholder.view_statusapp.setText(currentItem.getApprove_status());
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view_issueid ,view_staprove ,view_stapprove_date,view_price , view_statusapp;

        public ViewHolder(View itemView) {
            super(itemView);

            view_issueid = (TextView) itemView.findViewById(R.id.view_issueid) ;
            view_staprove = (TextView) itemView.findViewById(R.id.view_staprove) ;
            view_stapprove_date = (TextView) itemView.findViewById(R.id.view_stapprove_date) ;
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
