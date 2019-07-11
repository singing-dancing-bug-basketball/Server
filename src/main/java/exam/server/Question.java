package exam.server;
import javax.persistence.*;
@Entity
@Table(name = "question")

public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int id;

    @Column(name = "stem")
    private String stem;

    @Column(name = "selection_id")
    private int selection_id;

    @Column(name = "content")
    private String content;



    public Integer getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public  String getStem(){
        return  stem;
    }

    public void setStem(String stem){
        this.stem = stem;
    }

    public int getSelection_id() {
        return selection_id;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSelection_id(int selection_id) {
        this.selection_id = selection_id;
    }
}
