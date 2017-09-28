package sunb.education.habbit.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sunb.education.habbit.components.Rule;
import sunb.education.habbit.components.RuleResult;
import sunb.education.habbit.repository.RuleRepository;
import sunb.education.habbit.repository.RuleResultRepository;

import java.util.List;

@Service
public class DatabaseService {

    private final RuleRepository rulesRepository;

    private final RuleResultRepository resultRepository;

    @Autowired
    public DatabaseService(RuleRepository rulesRepository, RuleResultRepository resultRepository) {
        this.rulesRepository = rulesRepository;
        this.resultRepository = resultRepository;
    }

    public Rule addRule(String text) {
        Rule rules = new Rule(text);
        return rulesRepository.save(rules);
    }

    public List<Rule> lsRules() {
        return rulesRepository.findAll();
    }

    public List<RuleResult> findResult(String date) {
        return resultRepository.findRuleResultByCheckDate(date);
    }

    @Transactional
    public RuleResult addResult(String date, Long ruleId, boolean isDid) {

        Rule rule = findRule(ruleId);
        if (rule == null) {
            //throw new NoThisRuleException();
            return null;
        }

        RuleResult rr = new RuleResult();
        rr.setCheckDate(date);
        rr.setDidIt(isDid);
        rr.setRule(rule);
        rr.setRtScore(rule.getScore());
        RuleResult save = resultRepository.saveAndFlush(rr);

        Integer score = rule.getScore();
        rule.setScore(Math.max(0, isDid ? score - 10 : score + 10));
        rulesRepository.save(rule);

        return save;
    }

    public Rule findRule(long id) {
        return rulesRepository.getOne(id);
    }

}
