package sunb.education.habbit.components;

import javax.persistence.*;

@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String text = "";

    @Column
    private Integer score = 100;

    public Rule() {
    }

    public Rule(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", score=" + score +
                '}';
    }
}
