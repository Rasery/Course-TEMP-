public class Result implements Comparable<Result> {
    //запись внутренней таблицы
    //пол¤
    // формат строки, представл¤ющей запись о результате:
    private final static String REZ_FORMAT_STRING = "%10s | %10s | %8d |";
    //пол¤, составл¤ющие ключ записи о результате:
    private String command; //название команды
    private String championship; //название чемпионата
    //неключевое поле:
    private int goals; //число голов

    // конструктор без параметров
    public Result() {
        command = "";
        championship = "";
        goals = 0;
    }

    // конструктор с параметрами
    public Result(String command, String championship, int goals) {
        this.command = command;
        this.championship = championship;
        this.goals = goals;
    }

    //методы-геттеры
    public String getCommand() {
        return command;
    }

    public String getChampionship() {
        return championship;
    }

    public int getGoals() {
        return goals;
    }

    //методы-сеттеры
    public void setCоmmand(String command) {
        this.command = command;
    }

    public void setChampionship(String championship) {
        this.championship = championship;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    //ѕереопредел¤етс¤ метод toString класса Object
    //(возвращает строку описани¤ объекта)
    @Override
    public String toString() {
        return String.format(REZ_FORMAT_STRING, command, championship, goals);
    }

    //ѕереопредел¤етс¤ метод equals класса Object
    //(задает способ сравнени¤ объектов на равенство,
    //возвращает true, если запускающий объект равен объекту-параметру)
    @Override
    public boolean equals(Object ob) {
        if (ob == this) return true; // ссылки равны Ц один
        // и тот же объект
        if (ob == null) return false; //в метод передана null-ссылка
        if (getClass() != ob.getClass()) return false; //объекты разных классов
        Result rez = (Result) ob; // преобразуем Object в Rezalt
        //провер¤етс¤ равенство ключей текущей записи и записи-параметра
        return (command.equals(rez.command) &&
                championship.equals(rez.championship));
    }

    //ѕереопредел¤етс¤ метод hashCode класса Object
    //¬озвращает хэш-код объекта
    //(у равных объектов должны быть равные hash-коды)
    public int hashCode() {
        return 7 * command.hashCode() +
                13 * championship.hashCode() +
                17 * (new Integer(goals)).hashCode();
    }

    //ќпредел¤ем метод —оmpareTo интерфейса —omporable
    //ƒл¤ определени¤ пор¤дка (естественного) перечислени¤ элементов
    public int compareTo(Result rez) {
        //сравниваютс¤ только пол¤, составл¤ющие ключ!
        //по возрастанию названи¤ команды,
        //в рамках одной команды -
        //по возрастанию названи¤ чемпионата
        // сначала сравниваем названи¤ команд
        int c = command.compareTo(rez.command);
        if (c < 0) return -1;
        if (c > 0) return 1;
        //названи¤ команд равны (одна и та же команда)
        //сравниваем названи¤ чемпионатов
        c = championship.compareTo(rez.championship);
        if (c < 0) return -1;
        if (c > 0) return 1;
        return 0; //названи¤ чемпионатов равны   (этого не будет, т.к. ключи
        // разных строк Ѕƒ не могут быть равны друг другу)
    }
}



