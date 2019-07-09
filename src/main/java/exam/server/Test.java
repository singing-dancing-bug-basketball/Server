package exam.server;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "test_paper_id")
    private Test_paper test_paper;

    @Column(name = "title")
    private String title;

    @Column(name = "start_time")
    private Date start_time;


    @Column(name = "end_time")
    private Date end_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Test_paper getTest_paper(){
        return  test_paper;
    }
    public void setTest_paper(Test_paper test_paper){
        this.test_paper = test_paper;
    }
    public String getTitle(){
        return this.title;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}
