package exam.server;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class QuestionOwnerMultiKeys implements Serializable {
    @Column(name = "test_paper_id")
    private int test_paper_id;
    @Column(name = "question_id")
    private int question_id;


    public void setTest_paper_id(int test_paper_id) {
        this.test_paper_id = test_paper_id;
    }

    public int getTest_paper_id() {
        return test_paper_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    @Override
    public boolean equals(Object obj){
        QuestionOwnerMultiKeys c  = (QuestionOwnerMultiKeys) obj;
        if(c.getQuestion_id()==this.getQuestion_id()&&c.getTest_paper_id()==this.getTest_paper_id()){
            return true;
        }else {
            return false;
        }
     }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + this.getTest_paper_id();
        result = result * 31 + this.getQuestion_id();
        return result;
    }


}
