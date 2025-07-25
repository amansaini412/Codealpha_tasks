package INTERNSHIP;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("== STUDENT GRADE TRACKER ==");
        System.out.println("Enter number of Students:");
        int n = sc.nextInt();
        sc.nextLine();  //new line

        //Input student data
        //Take input for name
        for(int i=0; i<n; i++) {
            System.out.println("Enter Student Name:");
            String name = sc.nextLine();

            //Take input for grade
            System.out.println("Enter grade for " + name+ ":");
            int grade = sc.nextInt();
            sc.nextLine();
            students.add(new Student(name,grade));
        }

        // Calculates Stats
        int total = 0;
        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        String topStudent = "";
        String lowStudent = "";

        for (int i=0; i<students.size(); i++){
            Student s = students.get(i);
            total += s.grade;

            if(s.grade>highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if(s.grade < lowest){
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = (double) total/ students.size();

        // Summary Report
        System.out.println("Summary Report");
        System.out.println("Average Score: "+ average);
        System.out.println("Highest Score:" +highest + " by " + topStudent);
        System.out.println("Lowest Score:" + lowest + " by "+lowStudent);

        System.out.println("Student Details:");
        for(int i=0; i<students.size(); i++) {
            Student s = students.get(i);
            System.out.println(s.name+" "+s.grade);
        }
        sc.close();
    }
}
class Student {
    String name;
    int grade;

    Student (String name , int grade) {
        this.name = name;
        this.grade = grade;
    }
}