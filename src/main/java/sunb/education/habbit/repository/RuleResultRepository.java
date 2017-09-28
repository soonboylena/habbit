package sunb.education.habbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunb.education.habbit.components.Rule;
import sunb.education.habbit.components.RuleResult;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RuleResultRepository extends JpaRepository<RuleResult, Long> {

    public List<RuleResult> findRuleResultByCheckDate(String date);
}
