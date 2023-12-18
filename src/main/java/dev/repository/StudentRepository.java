package dev.repository;

import dev.domain.Student;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Repository
public class StudentRepository {

    private final DataSource dataSource;

    public StudentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "students", null);

            if (!resultSet.next()) {
                // Table does not exist, create it
                createStudentsTable(connection);
            }
        } catch (SQLException e) {
            // Handle the exception (log or rethrow as needed)
//            logger.error("Error checking or creating table", e);
        }
    }

    private void createStudentsTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE students (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "date_of_birth DATE, " +
                    "gender VARCHAR(10), " +
                    "quota VARCHAR(50), " +
                    "country VARCHAR(255))");
        }
    }

    public void create(Student student) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // Check if the table exists, create it if not
            createTableIfNotExists();

            // Rest of the code for inserting data...
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students (id, name, email, date_of_birth, gender, quota, country) VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setDate(4, Date.valueOf(student.getDateOfBirth()));
            preparedStatement.setString(5, student.getGender());
            preparedStatement.setString(6, student.getQuota());
            preparedStatement.setString(7, student.getCountry());

            preparedStatement.execute();
        }
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, name, email, date_of_birth, gender, quota, country FROM students";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setEmail(resultSet.getString("email"));
                student.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
                student.setGender(resultSet.getString("gender"));
                student.setQuota(resultSet.getString("quota"));
                student.setCountry(resultSet.getString("country"));
                students.add(student);
            }
        }

        return students;
    }

    public Student findById(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM students WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Student student = new Student();
                        student.setId(resultSet.getInt("id"));
                        student.setName(resultSet.getString("name"));
                        student.setEmail(resultSet.getString("email"));
                        student.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
                        student.setGender(resultSet.getString("gender"));
                        student.setQuota(resultSet.getString("quota"));
                        student.setCountry(resultSet.getString("country"));
                        return student;
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    public void updateStudentById(int id, Student updatedStudent) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE students SET name=?, email=?, date_of_birth=?, gender=?, quota=?, country=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, updatedStudent.getName());
                preparedStatement.setString(2, updatedStudent.getEmail());
                preparedStatement.setDate(3, Date.valueOf(updatedStudent.getDateOfBirth()));
                preparedStatement.setString(4, updatedStudent.getGender());
                preparedStatement.setString(5, updatedStudent.getQuota());
                preparedStatement.setString(6, updatedStudent.getCountry());
                preparedStatement.setInt(7, id);
                preparedStatement.executeUpdate();
            }
        }
    }

    public void deleteById(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        }
    }

}
