package exam.server;
import javax.persistence.*;

@Entity
@Table(name = "record")
@IdClass(RecordMultiKeys.class)

public class Record {

    @Id
    @Column(name = "student_id")
    private String student_id;

    @Id
    @Column(name = "test_id")
    private int test_id;

    @Id
    @Column(name = "question_id")
    private int question_id;

    @ManyToOne
    @JoinColumn(name = "student_id",insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_id",insertable = false, updatable = false)
    private Test test;

    @ManyToOne
    @JoinColumn(name = "question_id",insertable = false, updatable = false)
    private Question question;

    @Column(name = "selection_id")
    private int selection_id;

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

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public int getSelection_id() {
        return selection_id;
    }

    public void setSelection_id(int selection_id) {
        this.selection_id = selection_id;
    }
}
