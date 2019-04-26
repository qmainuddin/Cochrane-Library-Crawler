package com.crawlers.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewModel {
    private String url;
    private String topic;
    private String title;
    private String author;
    private String date;

    @Override
    public String toString(){
        String ret = "";
        if(!url.isEmpty()){
            ret += url + "|";
        }
        if(!topic.isEmpty()){
            ret += topic + "|";
        }
        if ((!title.isEmpty())){
            ret += title + "|";
        }
        if(!author.isEmpty()){
            ret += author + "|";
        }
        if(!date.isEmpty()){
            ret += date;
        }
        if (ret.endsWith("|")){
            ret = ret.substring(0, ret.lastIndexOf("|"));
        }
        return ret;
    }
}
