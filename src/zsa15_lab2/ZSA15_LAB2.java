package zsa15_lab2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;


public class ZSA15_LAB2 {

    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/zsa_lab2?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            // Создание свойств соединения с базой данных
            Properties authorization = new Properties();
            authorization.put("user", "root"); // Зададим имя пользователя БД
            authorization.put("password", "root"); // Зададим пароль доступа в БД

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, authorization);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // Выполнение запроса к базе данных, получение набора данных
            ResultSet table = statement.executeQuery("SELECT * FROM microwave ");

            System.out.println("Начальная БД:");
            table.first(); // Выведем имена полей
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst(); // Выведем записи таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Введите параметры нового поля таблицы:");
            System.out.print("brand - ");
            String scannedBrand = sc.nextLine();
            System.out.print("model - ");
            String scannedModel = sc.nextLine();

            System.out.println("После добавления:");
            statement.execute("INSERT microwave(brand, model) VALUES ('" + scannedBrand + "', '" + scannedModel + "')");
            table = statement.executeQuery("SELECT * FROM microwave");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.println("Строку с каким id хотите удалить?");
            System.out.print("id - ");
            int scannedId = sc.nextInt();
            statement.execute("DELETE FROM microwave WHERE Id = " + scannedId);

            System.out.println("После удаления:");
            table = statement.executeQuery("SELECT * FROM microwave");
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            sc.nextLine();

            System.out.println("На что изменить в первую строку?");
            System.out.print("brand - ");
            String scannedBrandUp = sc.nextLine();
            System.out.print("model - ");
            String scannedModelUp = sc.nextLine();
            statement.executeUpdate("UPDATE microwave SET brand = '" + scannedBrandUp + "' WHERE Id = 1");
            statement.executeUpdate("UPDATE microwave SET model = '" + scannedModelUp + "' WHERE id = 1");
            System.out.println("После изменения:");
            table = statement.executeQuery("SELECT * FROM microwave");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.print("Значение фильтрации для поля brand - ");
            scannedBrand = sc.nextLine();
            System.out.print("Значение фильтрации для поля model - ");
            scannedModel = sc.nextLine();
            System.out.println("С фильтром:");
            table = statement.executeQuery("SELECT * FROM microwave WHERE brand = '" + scannedBrand + "' AND model = '" + scannedModel + "'");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.print("Поле и вид сортировки: ");
            System.out.print("Поле - ");
            String scannedColumn = sc.nextLine();
            System.out.print("1 - по убыв. 2 - по возраст.: ");
            int scannedV = sc.nextInt();
            System.out.println("После сортировки:");
            if (scannedV == 1) {
                table = statement.executeQuery("SELECT * FROM microwave ORDER BY " + scannedColumn + " DESC");
            } else {
                table = statement.executeQuery("SELECT * FROM microwave ORDER BY " + scannedColumn);
            }

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            if (table != null) {
                table.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            } // Отключение от базы данных

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

}
