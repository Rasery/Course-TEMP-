import java.util.*;

public class CompIdAscReadinessDesc implements Comparator {
    public int compare(Object ob1, Object ob2) {
        Builder build1 = (Builder) ob1;
        Builder build2 = (Builder) ob2;
        if (build1.getBuilderId() < build2.getBuilderId()) return -1;
        else if (build1.getBuilderId() > build2.getBuilderId()) return 1;
        else if (build1.getReadiness() < build2.getReadiness()) return 1;
        else if (build1.getReadiness() == build2.getReadiness()) return 0;
        else return -1;
    }
}