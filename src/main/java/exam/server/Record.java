package exam.server;
import javax.persistence.*;

@Entity
@Table(name = "record")
public class Record {

    @EmbeddedId
    private RecordMultiKeys recordMultiKeys;

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


    public RecordMultiKeys getRecordMultiKeys() {
        return recordMultiKeys;
    }

    public void setRecordMultiKeys(RecordMultiKeys recordMultiKeys) {
        this.recordMultiKeys = recordMultiKeys;
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
