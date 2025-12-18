import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import model.Student;

public class UnitTests {

    @Test
    void testCorrectlyReadsStudents() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");

        Files.writeString(tempFile,
            """
            Student:Ivan
            Book1,1,2008
            Book2,2,2021
            Book3,3,1999
            Book4,4,2018
            Book5,5,2015
            
            Student:Dima
            Kotlin Basics,350,2019
            Java Basics,500,2016
            App Design Patterns,395,2004
            Docker Basics,280,2020
            Python Basics,450,2003
            """);

        List<Student> students = FileReader.readStudents(tempFile);

        assertEquals(2, students.size());

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testReadingStudentsErrorHandled() {
        Path nonExistingPath = Path.of("non_existent_file.txt");

        List<Student> students = FileReader.readStudents(nonExistingPath);

        assertNotNull(students);
        assertTrue(students.isEmpty());
    }
}
