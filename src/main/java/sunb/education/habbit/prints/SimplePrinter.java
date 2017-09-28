package sunb.education.habbit.prints;

public class SimplePrinter {

    private static final int start = 5;

    private static final int length = 50;
    private static final int height = 3;
    private int baseLine = 60;

    private static final String hs = "==";
    private static final String vs = "|";

    private static final String cover = "X";

    private int percentLength;


    public SimplePrinter(int percent) {
        assert percent < 100;
        int percent1 = Math.max(10, percent);
        this.percentLength = percent1 * length / 100;
    }

    public String print() {
        StringBuilder sbd = new StringBuilder(2048);

        // 头一行
        // 前边空格
        sbd.append(blankRegion());
        for (int i = 0; i < length; i++) {
            sbd.append(hs);
        }
        sbd.append("\r\n");

        // 底下部分
        for (int row = 0; row < height; row++) {
            sbd.append(blankRegion());
            sbd.append(vs);
            for (int i = 1; i < length - 1; i++) {
                sbd.append(selectElement(i));
            }
            sbd.append(vs).append("\r\n");
        }

        sbd.append(blankRegion());
        for (int i = 0; i < length; i++) {
            sbd.append(hs);
        }
        sbd.append("\r\n");
        return sbd.toString();
    }

    private String selectElement(int i) {

        if (i == baseLine * length / 100) {
            return vs;
        }

        if (i < percentLength) {
            return cover;
        }

        //if (i < baseLine * length / 100) {
        //    return " ";
        //}

        return " ";
    }

    private String blankRegion() {
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < start; i++) {
            sbd.append(" ");
        }
        return sbd.toString();
    }
}
