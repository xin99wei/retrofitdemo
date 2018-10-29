package me.mikasa.retrofitdemo.bean;

public class Movie {
    private String posterUrl;
    private String rating;
    private String category;
    private String title;
    private String original_title;
    private String linkUrl;
    public void setPosterUrl(String s){
        this.posterUrl=s;
    }
    public String getPosterUrl(){
        return posterUrl;
    }
    public void setRating(String s){
        this.rating=s;
    }
    public String getRating(){
        return rating;
    }
    public void setCategory(String s){
        this.category=s;
    }
    public String getCategory(){
        return category;
    }
    public void setTitle(String s){
        this.title=s;
    }
    public String getTitle(){
        return title;
    }
    public void setOriginal_title(String s){
        this.original_title=s;
    }
    public String getOriginal_title(){
        return original_title;
    }
    public void setLinkUrl(String s){
        this.linkUrl=s;
    }
    public String getLinkUrl(){
        return linkUrl;
    }
}
