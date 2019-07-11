package exam.server;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RecordMultiKeys implements Serializable{
    @Column(name = "student_id")
    private String student_id;
    @Column(name = "test_id")
    private int test_id;
    @Column(name = "question_id")
    private int question_id;



    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    @Override
    public boolean equals(Object obj){
        RecordMultiKeys c  = (RecordMultiKeys) obj;
        if(c.question_id==this.question_id&&c.test_id==this.question_id&&c.student_id.equals(this.student_id)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + this.test_id;
        result = result * 31 + this.question_id;
        result = result * 31 + this.student_id.hashCode();
        return result;
    }


}
