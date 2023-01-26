package com.example.mobileapp2.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp2.Entities.Assessment;
import com.example.mobileapp2.R;

import java.util.List;


public class AllAssessmentAdapter extends RecyclerView.Adapter<AllAssessmentAdapter.AssessmentViewHolder> {


    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private final TextView startItemView;
        private final TextView endItemView;

        private AssessmentViewHolder(View itemview){
            super(itemview);
            assessmentItemView=itemview.findViewById(R.id.textViewassessmentlist);
            startItemView= itemview.findViewById(R.id.textViewassessmentstart);
            endItemView = itemview.findViewById(R.id.textViewassessmentend);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setClickable(false);
                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final LayoutInflater mInflater;

    public AllAssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public AllAssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.all_assessments_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String name = current.getAssessmentName();
            String start = current.getAssessmentStart();
            String end = current.getAssessmentEnd();

            holder.assessmentItemView.setText(name);
            holder.startItemView.setText(start);
            holder.endItemView.setText(end);

        }
        else{
            String noInfo = "No information";
            holder.assessmentItemView.setText(noInfo);
            holder.startItemView.setText(noInfo);
            holder.endItemView.setText(noInfo);
        }
    }

    @Override
    public int getItemCount(){
        return mAssessments.size();
    }

    public void setAssessments(List <Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }

}
