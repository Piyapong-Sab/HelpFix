package com.example.helpfix.adapter_user;

public class GetDataAdapterUser {
    public String PicIssue;
    public String Idissue;
    public String RecordDate;
    public String Problem;
    public String Status;
    public String Place;
    public String Level;

    public String getPicIssue(){
        return PicIssue;
    }
    public void setPicIssue(String PicIssue) {
        this.PicIssue = PicIssue;
    }
    public String getIdissue (){
        return Idissue;
    }
    public void setIdissue(String IdUser){
        this.Idissue = IdUser;
    }
    public void setRecordDate(String RecordDate){
        this.RecordDate = RecordDate;
    }
    public String getRecordDate (){
        return RecordDate;
    }
    public void setProblem(String Problrm){
        this.Problem = Problrm;
    }
    public String getProblem (){
        return Problem;
    }
    public void setStatus (String Status){
        this.Status = Status;
    }
    public String getStatus(){
        return Status;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}

