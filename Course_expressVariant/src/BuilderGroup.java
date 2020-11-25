import java.util.*;

public class BuilderGroup {
    // Внутренняя (основная) таблица (группа)  результатов соревнонований
    //поля (скрыты в классе)
    private final static String GROUP_FORMAT_STRING =
            "Результаты соревнований: %-s, %-5d строк"; //формат записи о таблице результатов
    private String name; //название таблицы
    private List<Builder> builders; // список записей о результатах соревнований

    // конструкторы
    public BuilderGroup() {
        name = "";  //без названия
        builders = new ArrayList<Builder>(); //создается пустой список
    }

    public BuilderGroup(String name) {
        this.name = name; //задается название группы
        builders = new ArrayList<Builder>(); //создается пустой список
    }

    public BuilderGroup(String name, List list) {
        this.name = name; //задается название группы
        builders = new ArrayList<Builder>(list); //создается на основе
        // существующего списка
    }

    //метод-сеттеры для private-полей
    public void setName(String name) {
        this.name = name;
    }

    public void setBuilders(List<Builder> builders) {
        this.builders = builders;
    }

    //методы-геттеры для private-полей
    public String getName() {
        return name;
    }

    public List<Builder> getBuilders() {
        return builders;
    }

    //Переопределяем метод toString класса Object
    //возвращает строку описания объекта Группа (таблица)
    // результатов соревнований:
    public String toString() {
        return String.format(GROUP_FORMAT_STRING, name, getBuilderNum());
    }

    //Запросы на вставку, удаление, изменение данных:
    public boolean AddBuilder(Builder build) {
        //Добавить результат в таблицу (группу) результатов
        //(результат нельзя добавить, если в группе уже есть
        // результат с таким же ключом)
        if (getBuilder(build) != null)
            return false; //дополнительная программная проверка уникальности ключа
        if (builders.add(build)) return true;
        else return false;
    }

    //одиночное удаление (по образцу)
    public boolean DelBuilder(Builder build) {
        //Удалить результат c заданным ключом из группы
        if (builders.remove(build)) return true;
        else return false;
    }

    //Групповое удаление (по условию)
    public boolean DelInpReadiness(String inpReadiness) { //IN DEV
        //удалить результаты, в которых число голов выше среднего
        return builders.removeAll(LessInpReadiness(inpReadiness).builders);
    }

    // Обновление (неключевого поля) по ключу
    public boolean UpdBuilderReadiness(Builder build) { //RENAME
        //изменить число голов в записи, выбранной по образцу res
        Builder b = getBuilder(build);
        if (b != null) {
            b.setReadiness(build.getReadiness());
            return true;
        }
        return false;
    }

    // запросы на выборку данных:
    // возвращает результат с заданным ключом:
    public Builder getBuilder(Builder build) {
        for (Builder b : builders)
            //сравнение ключей (определено в методе equals класса Result)
            if (b.equals(build)) return b; // если ключ найден
        return null; // если ключ не найден
    }

    public int getBuilderNum() {
        //Возвращает число результатов в группе
        return builders.size();
    }

    public BuilderGroup LessInpReadiness(String inpReadiness) {
        //вернуть записи о результатах, в которых число голов выше среднего
        int avg = Integer.parseInt(inpReadiness);
        BuilderGroup group = new BuilderGroup(name +
                ": результаты, в которых число голов выше среднего - " + avg);
        // конкатенация на неизменяемых строках - плохое решение,
        // покажем, как избавиться от нее, в методе between
        //для просмотра (перечисления) объектов списка используем цикл for-each
        for (Builder build : builders)
            if (build.getReadiness() < avg) group.AddBuilder(build);
        return group;
    }

    public BuilderGroup filterObjects(String filter) {
        //вернуть записи о результатах, в которых название команды
        //начинается с заданного буквосочетания (фильтрация данных)
        BuilderGroup group = new BuilderGroup(name +
                ": результаты для команд на \"" + filter + "\"");
        for (Builder build : builders)
            if (build.getObjectName().startsWith(filter)) group.AddBuilder(build);
        return group;
    }

    //Запросы на подведение итогов (итоговые расчеты)
    public int ObjectsNumber() {
        //общее число команд
        int size = builders.size();
        if (size == 0) return 0;
        Set<String> objectsS = new TreeSet<String>();
        for (Builder build : builders)
            objectsS.add(build.getObjectName()); //только разные
        return objectsS.size();
    }

    public List<TotalRecord> TotalSumCommandGoals_1() { //IN DEV
        //вернуть суммарное число голов для каждой команды
        //вариант 2 – ускоренный за счет удаления из набора
        //уже обработанных записей
        int n = builders.size();
        if (n == 0) return null;
        //Дублируем базовую таблицу (список), т.к. из базовой таблицы
        // записи удалять нельзя
        List<Builder> resultsTemp = new ArrayList<Builder>(); //создаем дубль
        resultsTemp.addAll(builders); //копируем ссылки на записи в список-дубль
        Set<String> commandsS = new TreeSet();
        for (Builder build : builders)
            commandsS.add(build.getObjectName()); //только разные названия команд
        List<String> commandsL = new ArrayList(commandsS); //для
        // индексирования
        int m = commandsL.size();
        String com;
        int sum;
        int temp = 0;
        List<TotalRecord> totRecList = new ArrayList<TotalRecord>();
        for (int i = 0; i < m; i++) {
            com = commandsL.get(i);
            sum = 0;
         /* Получаем итератор для списка-дубля, т.к. корректно
             удалять требуемые элементы из списка можно только
             используя в цикле метод remove() итератора.*/
            Iterator<Builder> iter = resultsTemp.iterator();
            while (iter.hasNext()) {
                temp = temp + 1;
                Builder build = iter.next();
                if (com.equals(build.getObjectName())) {
                    sum = sum + build.getReadiness(); // прибавили число голов,
                    // эта запись больше не нужна
                    iter.remove(); //удаляем запись  из временного набора
                }
            }
            totRecList.add(new TotalRecord(com, sum));
        }
        //System.out.println("Число повторений цикла:" + temp);
        return totRecList;
    }

    public BuilderGroup Sort(Comparator comp) { //coртировка студентов
        //по правилу, задаваемому компаратором comp
        BuilderGroup group = new BuilderGroup(name, builders);
        Collections.sort(group.builders, comp);
        return group;
    }
} //class
