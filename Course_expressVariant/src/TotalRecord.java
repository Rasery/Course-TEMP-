public class TotalRecord {
    //запись таблицы итогового расчета
    private String str;
    private int num;

    public TotalRecord(String str, int num) {
        this.num = num;
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String toString() {
        return String.format("%s : %5d ", str, num);
    }

}

