package exam.server;
import javax.persistence.*;
@Entity
@Table(name = "question")

public class Question {

    @Id
    @Column(name = "question_id")
    private Integer id;

    @Column(name = "stem")
    private String stem;

    @Column(name = "selection_id")
    private Integer selection_id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public  String getStem(){

        return  stem;
    }

    public void setStem(String stem){
        this.stem = stem;
    }

    public  Integer getQustion_id(){

        return selection_id;

    }

    public void setSelection_id(Integer selection_id){
        this.selection_id = selection_id;
    }


}
