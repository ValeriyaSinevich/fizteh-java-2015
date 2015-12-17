package ru.fizteh.fivt.students.ValeriyaSinevich.miniORM;


import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class DatabaseService<T> {

    private static String username;

    private static String password;

    static final String REGEX = "[A-Za-z0-9_-]*";

    private Class<T> clazz;

    private String tableName;
    private Field[] fields;
    private int primaryKeyIndex = -1;
    private ArrayList<String> fieldsNames;

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";


    private void getFieldsNames() {
        fieldsNames = new ArrayList<>();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                fieldsNames.add(null);
            } else {
                if (column.name().length() == 0) {
                    fieldsNames.add(formatName(field.getName()));
                } else {
                    fieldsNames.add(column.name());
                }
            }
        }
    }

    public List<Field> checkGivenType(Class<T> givenClazz) {

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("no @Table annotation");
        }

        tableName = clazz.getAnnotation(Table.class).name();
        if (tableName.equals("")) {
            tableName = formatName(clazz.getSimpleName());
        }

        if (!validName(tableName)) {
            throw new IllegalArgumentException("Bad table name");
        }

        Set<String> names = new HashSet<>();
        List<Field> fieldsList = new ArrayList<>();
        for (Field f: clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Column.class)) {
                String name = getColumnName(f);
                if (name.equals("")) {
                    name = formatName(name);
                }
                names.add(name);
                if (!validName(name)) {
                    throw new IllegalArgumentException("Bad column name");
                }

                f.setAccessible(true);
                fieldsList.add(f);
                if (f.isAnnotationPresent(PrimaryKey.class)) {
                    if (primaryKeyIndex == -1) {
                        primaryKeyIndex = fieldsList.size() - 1;
                    } else {
                        throw new
                                IllegalArgumentException("@PrimaryKey must be unique");
                    }
                }
            } else if (f.isAnnotationPresent(PrimaryKey.class)) {
                throw new
                        IllegalArgumentException("no @Column");
            }
        }

        if (names.size() != fieldsList.size()) {
            throw new IllegalArgumentException("column names must be unique");
        }
        return null;
    }

    public DatabaseService(Class<T> givenClazz) {
        List<Field> fieldsList = checkGivenType(givenClazz);
        clazz = givenClazz;
        fields = new Field[fieldsList.size()];
        fields = fieldsList.toArray(fields);
        getFieldsNames();
    }

    private void setProperties(String properties) {
        Properties credits = new Properties();
        try (InputStream inputStream
                     = this.getClass().getResourceAsStream(properties)) {
            credits.load(inputStream);
        } catch (Exception e) {
            throw new IllegalArgumentException("wrong properties");
        }

        username = credits.getProperty("username");
        password = credits.getProperty("password");

    }

    public void createTable() throws DataBaseException {
        StringBuilder createBuilder = new StringBuilder();
        TypeConverter tp = new TypeConverter();
        for (int i = 0; i < fields.length; ++i) {
            if (i != 0) {
                createBuilder.append(", ");
            }
            String name = fieldsNames.get(i);
            if (name != null) {
                createBuilder.append(name).append(" ")
                        .append(tp.convertType(fields[i].getType()));
                if (i == primaryKeyIndex) {
                    createBuilder.append(" NOT NULL PRIMARY KEY");
                }
            }
        }

        try (Connection connection = getDBConnection()) {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS " + tableName
                    + "" +
                    "(" + createBuilder.toString() + ")");

        }
        catch (DataBaseException | SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }


    private static Connection getDBConnection() throws DataBaseException {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataBaseException(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public static Boolean validName(String name) {
        return name.matches(REGEX);
    }

    public final String formatName(String name) {
        return name.replaceAll("([^_A-Z])([A-Z])", "$1_$2");
    }

    String getColumnName(Field f) {
        String name = f.getAnnotation(Column.class).name();
        return name;
    }

    public String prepareInsertStmt(T newClazz) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ").append(tableName).append("(");
        for (int i = 0; i < fields.length; ++i) {
            if (i != 0) {
                queryBuilder.append(", ");
            }
            queryBuilder.append(getColumnName(fields[i])).append(" ");
        }
        queryBuilder.append(") VALUES(");
        for (int i = 0; i < fields.length; ++i) {
            if (i != 0) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");
        return queryBuilder.toString();
    }


    public void insert(T newClazz) throws DataBaseException {
        if (newClazz.getClass() != clazz) {
            throw new DataBaseException("wrong object");
        }
        PreparedStatement stmt = null;
        String insertStmt = prepareInsertStmt(newClazz);
        try (Connection connection = getDBConnection()) {
            stmt = connection.prepareStatement(insertStmt);
            for (int i = 0; i < fields.length; ++i) {
                stmt.setObject(i + 1, fields[i].get(newClazz));
            }
            stmt.execute();
            stmt.execute(insertStmt);
        }
        catch (SQLException | DataBaseException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <K> boolean delete(K key) throws DataBaseException {
        try {
            String name = fieldsNames.get(primaryKeyIndex);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM ")
                    .append(tableName)
                    .append(" WHERE ")
                    .append(name)
                    .append(" = ?");
            try (Connection connection = getDBConnection()) {
                PreparedStatement statement = connection.prepareStatement(stringBuilder.toString());
                statement.setObject(1, key);
                return (statement.executeUpdate() > 0);
            }
        } catch (SQLException | DataBaseException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public String prepareSelectStatement() throws DataBaseException {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ").append(tableName)
                .append(" WHERE ").append(fields[primaryKeyIndex].getName())
                .append(" = ?");
        return queryBuilder.toString();
    }

    public <K> List<T> queryById(K key) throws IllegalArgumentException,
            SQLException, DataBaseException {
        try (Connection connection = getDBConnection()) {
            try {
                PreparedStatement statement
                        = connection.prepareStatement(prepareSelectStatement());
                statement.setString(1, key.toString());

                ResultSet rs = statement.executeQuery();
                return createAnswer(rs);
            }
            catch (DataBaseException e) {
                throw e;
            }
        } catch (DataBaseException e) {
            throw e;
        }
    }

    private <T> List<T> createAnswer(final ResultSet rs) throws DataBaseException {
        try {
            List<T> answer = new LinkedList<>();
            while (rs.next()) {
                T record = (T) clazz.newInstance();
                for (int i = 0; i < fields.length; ++i) {
                    fields[i].setAccessible(true);
                    String name = fieldsNames.get(i);

                    if (name == null) {
                        continue;
                    }
                    fields[i].set(record, rs.getObject(name));
                }
                answer.add(record);
            }
            return answer;
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }


    public List<T> queryForAll() throws DataBaseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ").append(tableName);
        try (Connection connection = getDBConnection()) {
            PreparedStatement statement = connection.prepareStatement(stringBuilder.toString());
            ResultSet rs = statement.executeQuery();
            return createAnswer(rs);
        }
        catch (DataBaseException | SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public void dropTable() throws DataBaseException {
        try (Connection connection = getDBConnection()) {
            connection.createStatement().execute("DROP TABLE IF EXISTS " + tableName);
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
