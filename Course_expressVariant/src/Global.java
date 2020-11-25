import java.util.*;

public class Global {
    //Содержит глобальные (статические) переменные и методы программы,
//посредством которых осуществляется взаимодействие между
//внутренними представлениями базовой таблицы и результатов
// запросов и визуальной таблицей
    public static BuilderGroup table; //ссылка на основную таблицу (внутреннее
    //представление БД)
    public static List<Builder> builders; //ссылка на список, выводимый
    //в визуальный компонент JTable главного окна
    //(список результатов соревнований основной таблицы или список
    //результатов соревнований, возвращенный запросом на выборку
    //данных из основной таблицы)
    static BuilderTableModel tableModel; //модель данных для JTable

    public static void updateJTable(List<Builder> build) {
        //Обновить данные в визуальном компоненте JTable
        builders.clear();
        builders.addAll(build);
        tableModel.fireTableDataChanged();
    }
}
