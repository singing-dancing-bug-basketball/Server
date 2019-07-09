package exam.server;

import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "student_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;


    public String getId() {
        return id;
    }

    public void setId(String id){

        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

}
