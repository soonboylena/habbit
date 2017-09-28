package sunb.education.habbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunb.education.habbit.components.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

}
