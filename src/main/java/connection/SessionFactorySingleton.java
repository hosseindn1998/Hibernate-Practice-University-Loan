package connection;

import Model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactorySingleton {

    private SessionFactorySingleton() {}

    private static class LazyHolder {
        static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            INSTANCE = new MetadataSources(registry)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Schedule.class)
                    .addAnnotatedClass(Room.class)
                    .addAnnotatedClass(Course.class)
                    .addAnnotatedClass(Professor.class)
                    .addAnnotatedClass(ProfessorCourse.class)
                    .addAnnotatedClass(Person.class)
                    .addAnnotatedClass(EvaluationType.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Department.class)
                    .addAnnotatedClass(CourseOccurrence.class)
                    .addAnnotatedClass(CourseEnrollmentEvaluation.class)
                    .addAnnotatedClass(CourseEnrollment.class)
                    .addAnnotatedClass(CourseDependency.class)
                    .addAnnotatedClass(CareerStatus.class)
                    .addAnnotatedClass(CareerLevel.class)
                    .addAnnotatedClass(CareerEnrollment.class)
                    .addAnnotatedClass(Career.class)
                    .addAnnotatedClass(AssistantProfessor.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
    }

    public static SessionFactory getInstance() {
        return LazyHolder.INSTANCE;
    }
}