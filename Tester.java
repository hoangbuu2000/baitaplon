/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.baitaplon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Buu
 */
public class Tester {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/baitaplon", "root", "123456789");
        Statement stm = conn.createStatement();
        Statement stm1 = conn.createStatement();
        String query = "INSERT INTO practice(user_id, question_id, type, date_practice, quantity, score)"
                + "VALUES(?, ?, ?, ?, ?, ?)"; // Chen vao bang du lieu nhung cau hoi nguoi dung da luyen tap
        PreparedStatement stm2 = conn.prepareStatement(query);
        Statement stm3 = conn.createStatement();
        
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while(loop){
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Your chocie: ");
            String choice = scanner.nextLine();
            switch(choice){
                case "1":
                    while(true){
                        System.out.print("\nusername: ");
                        String user = scanner.nextLine();
                        System.out.print("password: ");
                        String pass = scanner.nextLine();
                        try{
                            User u1 = new User(user, pass);
                            ResultSet rs2 = stm3.executeQuery("SELECT u.id FROM user u WHERE u.username = '" + user + "'");
                            int id = 0;
                            while(rs2.next()){
                                id = rs2.getInt("id");
                            }
                            rs2.close();
                            System.out.println("\nHi, " + u1.getFullName());
                            System.out.println("What do you want to do?");
                            do{
                                System.out.println("1. Practice MultipleChoice Question");
                                System.out.println("2. Practice InComplete Question");
                                System.out.println("3. Practice Conversation Question");
                                System.out.print("Your choice: ");
                                String c = scanner.nextLine();
                                int i = 0;
                                switch(c){
                                    case "1":
                                        System.out.print("Number of question you want to practice: ");
                                        int n = scanner.nextInt();
                                        ResultSet rs = stm.executeQuery("SELECT m.id, m.content as question,"
                                                + " c.content as category, l.content as level"
                                                + " FROM multiplechoice m"
                                                + " INNER JOIN level l ON m.level_id = l.id"
                                                + " INNER JOIN category c ON m.category_id = c.id"
                                                + " WHERE m.incomplete_id IS NULL AND m.conversation_id IS NULL"
                                                + " ORDER BY RAND() LIMIT " + n);
                                        QuestionManagement questions = new QuestionManagement();
                                        int multipleChoiceId = 0;
                                        int[] questionId = new int[n]; // Mang luu tru cac id cau hoi
                                        int j = 0;
                                        while(rs.next()){ // Duyet tung cau hoi lay tu csdl
                                            Level level = new Level(rs.getString("level"));
                                            Category category = new Category(rs.getString("category"));
                                            Question q = new MultipleChoice(rs.getString("question"), level, category);
                                            multipleChoiceId = rs.getInt("id");
                                            questionId[j] = rs.getInt("id");
                                            j++;
                                            ResultSet rs1 = stm1.executeQuery("SELECT * FROM choice"
                                                    + " WHERE choice.multiplechoice_id = " + multipleChoiceId);
                                            while(rs1.next()){ // Duyet de lay cac cau tra loi add vao cau hoi
                                                Choice ch = new Choice(rs1.getString("content"), 
                                                        rs1.getBoolean("correct"), rs1.getString("note"));
                                                q.addChoice(ch);
                                            }
                                            questions.addQuestion(q);
                                            rs1.close();
                                        }
                                        rs.close();
                                        questions.practiceMultipleC(scanner, n);
                                        
                                        int multipleChoiceId1 = 0; // Duyet de lay id cac cau hoi
                                        for(int k = 0; k < questionId.length; k++){
                                            multipleChoiceId1 = questionId[k];
                                            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                                            String date = f.format(new Date());
                                            java.sql.Date sqlDate = new java.sql.Date(f.parse(date).getTime()); 
                                            stm2.setInt(1, id);
                                            stm2.setInt(2, multipleChoiceId1);
                                            stm2.setString(3, "multiplechoice");
                                            stm2.setDate(4, sqlDate);
                                            stm2.setInt(5, 1);
                                            stm2.setDouble(6, u1.getScore());
                                            stm2.execute(); 
                                        }
                                        
                                        
                                        break;
                                    case "2":
                                        break;
                                    case "3":
                                        break;
                                }
                                break;
                            }while(true);
                            break;
                        }
                        catch(Exception e){
                            System.err.println(e.getMessage());
                        }
                    }
                    break;
                case "2":
                    User u2 = User.register(scanner);
                    break;
                case "3":
                    loop = false;
                    break;
            }
        }
        
        stm.close();
        stm1.close();
        stm2.close();
        stm3.close();
        conn.close();
    }
}
