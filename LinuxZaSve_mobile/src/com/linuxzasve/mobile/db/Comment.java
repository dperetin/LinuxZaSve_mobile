package com.linuxzasve.mobile.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "COMMENT")
public class Comment extends Model{
    @Column(name = "NAME")
    public String name;

    @Column(name = "EMAIL")
    public String email;

    public Comment(){
        super();
    }
    public Comment(String name, String email){
        super();
        this.name = name;
        this.email = email;
    }

    public static List<Comment> all() {
        return new Select()
                .from(Comment.class)
                .execute();
    }
}
