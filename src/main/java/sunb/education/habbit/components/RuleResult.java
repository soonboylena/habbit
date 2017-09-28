package sunb.education.habbit.components;


import javax.persistence.*;

@Entity
public class RuleResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Boolean didIt;

    @OneToOne(targetEntity = Rule.class)
    private Rule rule;

    @Column
    private int rtScore;

    @Column
    private String checkDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDidIt() {
        return didIt;
    }

    public void setDidIt(Boolean didIt) {
        this.didIt = didIt;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public int getRtScore() {
        return rtScore;
    }

    public void setRtScore(int rtScore) {
        this.rtScore = rtScore;
    }

    @Override
    public String toString() {
        return "RuleResult{" +
                "id=" + id +
                ", didIt=" + didIt +
                ", rule=" + rule +
                ", rtScore=" + rtScore +
                ", checkDate='" + checkDate + '\'' +
                '}';
    }
}
