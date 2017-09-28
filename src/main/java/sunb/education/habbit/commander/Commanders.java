package sunb.education.habbit.commander;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import sunb.education.habbit.components.Rule;
import sunb.education.habbit.components.RuleResult;
import sunb.education.habbit.prints.SimplePrinter;
import sunb.education.habbit.services.DatabaseService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@ShellComponent()
public class Commanders {

    private static final String userName = "test";

    @Autowired
    private DatabaseService dbService;

    @ShellMethod("欢迎")
    public String hello() {

        int hour = LocalDateTime.now().getHour();

        String timePerim;
        if (hour >= 5 && hour < 10) {
            timePerim = "早上";
        } else if (hour >= 10 && hour < 14) {
            timePerim = "中午";
        } else {
            timePerim = "下午";
        }

        String dataString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日,")) + LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.CHINA);

        return String.format("\r\n%s好,%s。今天是%s。\r\n想看看今天的成绩吗?请输入'today'。或者输入'help'看所有的命令。\r\n", timePerim, userName, dataString);
    }

    @ShellMethod(value = "查看今天情况", group = "today", prefix = "-")
    public String today(@ShellOption(defaultValue = "no") String grade) {
        List<RuleResult> ruleResults = dbService.findResult(LocalDate.now().toString());
        if (ruleResults == null || ruleResults.isEmpty()) {
            return "今天还没有评分吧...想要现在开始吗? 可以输入 'ls' 来查看可选的评分项";
        }


        ruleResults.sort(Comparator.comparingInt(o -> (o.getDidIt() ? -1 : 1)));
        StringBuilder sbf = new StringBuilder();

        for (int i = 0; i < ruleResults.size(); i++) {
            RuleResult ruleResult = ruleResults.get(i);
            sbf.append(i + 1).append(".")
                    .append("[").append(ruleResult.getDidIt() ? " Yes :) " : " No  :( ").append("]")
                    .append(" ").append(ruleResult.getRule().getText())
                    .append("\r\n");
        }

        if (grade.equalsIgnoreCase("yes")) {
            sbf.append("---------------今日评分---------------");
            sbf.append("\r\n");
            sbf.append(grade(ruleResults));
            sbf.append("\r\n");
        }


        return sbf.toString();
    }

    private String grade(List<RuleResult> ruleResults) {
        if (ruleResults.size() < 3) {
            return " 评分项太少了，还是等评分项更多的时候再来吧";
        }

        int total = 0;
        int myScore = 0;
        for (RuleResult ruleResult : ruleResults) {
            total += ruleResult.getRtScore();
            myScore += (ruleResult.getDidIt() ? ruleResult.getRtScore() : -1 * ruleResult.getRtScore());
        }
        if (total == 0) {
            return "出了一点小问题，没办法评分。。等我修理一下，明天再来吧";
        }

        int scorePercent = myScore * 100 / total;

        SimplePrinter sp = new SimplePrinter(scorePercent);

        StringBuilder sbd = new StringBuilder(sp.print());
        sbd.append("\r\n");
        sbd.append("..................................................");
        sbd.append("\r\n");
        sbd.append(scorePercent < 60 ? "不达标 ：(" : "达标了！ :）");

        return sbd.toString();
    }

    @ShellMethod(key = "td add", value = "给今天加一项", group = "today", prefix = "-")
    public String todayAdd(long id, String did) {
        if (!did.equals("y") && !did.equals("n")) {
            return "我不认识这个命令....提示： td add [123] [y/n] 格式";
        }

        RuleResult result = dbService.addResult(LocalDate.now().toString(), id, did.equals("y"));

        if (result == null) {
            return "失败了。请确认评分项id是否存在";
        }

        return "成功了！" + result;
    }

    @ShellMethod("列出可用的评分项")
    public String ls() {
        List<Rule> rules = dbService.lsRules();
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            sbd.append("    ").append(i + 1).append(".");
            sbd.append("[").append(rule.getId()).append("]");
            sbd.append(rule.getText()).append(",");
            sbd.append("\r\n");
        }
        sbd.append("如果想要增加一个评分项，请使用'add'命令");
        return sbd.toString();
    }

    @ShellMethod("增加一个评分项")
    public String add(String text) {
        if (text == null || text.trim().equals("")) {
            return "失败了!需要加点说明";
        }
        Rule rules = dbService.addRule(text);
        return String.format("成功！\r\n    %s", rules);
    }

}
