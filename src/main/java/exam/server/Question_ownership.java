package exam.server;

import javax.persistence.*;


@Entity
@Table(name = "question_ownership")
public class Question_ownership {


    @EmbeddedId
    private QuestionOwnerMultiKeys questionOwnerMultiKeys;

    //创建外键
    @ManyToOne
    @JoinColumn(name = "test_paper_id",insertable = false, updatable = false)
    private Test_paper test_paper;

    @ManyToOne
    @JoinColumn(name = "question_id",insertable = false, updatable = false)
    private Question question;

    @Column(name = "score")
    private int score;

    public QuestionOwnerMultiKeys getQuestionOwnerMultiKeys() {
        return questionOwnerMultiKeys;
    }

    public void setQuestionOwnerMultiKeys(QuestionOwnerMultiKeys questionOwnerMultiKeys) {
        this.questionOwnerMultiKeys = questionOwnerMultiKeys;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Test_paper getTest_paper() {
        return test_paper;
    }

    public void setTest_paper(Test_paper test_paper) {
        this.test_paper = test_paper;
    }
}
