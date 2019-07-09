package exam.server;


import javax.persistence.*;

@Entity
@Table(name = "test_paper")

public class Test_paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_paper_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private int duration;

    public int getId() {
        return id;
    }

    public void setId(int id){

        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public int getDuration(){
        return duration;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }


}
