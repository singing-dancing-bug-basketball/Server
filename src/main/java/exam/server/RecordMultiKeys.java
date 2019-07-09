package exam.server;


import java.io.Serializable;

public class RecordMultiKeys implements Serializable{
    private String student_id;
    private int test_id;
    private int question_id;


    public RecordMultiKeys(int test_id,int question_id,String student_id){
        this.question_id = question_id;
        this.test_id = test_id;
        this.student_id = student_id;
    }

}
