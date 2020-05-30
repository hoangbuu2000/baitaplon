/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.baitaplon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Buu
 */
public class QuestionManagement {
    private List<Question> questions = new ArrayList<>();
    
    public void addQuestion(Question q){
        this.getQuestions().add(q);
    }
    
    public List<Question> getMultipleChoiceQ(){
        List<Question> k = new ArrayList<>();
        for(Question q : getQuestions()){
            if(q instanceof MultipleChoice)
                k.add(q);
        }
        return k;
    }
    
    public List<Question> getInCompleteQ(){
        List<Question> k = new ArrayList<>();
        for(Question q : questions){
            if(q instanceof InComplete)
                k.add(q);
        }
        return k;
    }
    
    public List<Question> getConversationQ(){
        List<Question> k = new ArrayList<>();
        for(Question q : questions){
            if(q instanceof Conversation)
                k.add(q);
        }
        return k;
    }
    
    public void practiceMultipleC(Scanner scanner, int n){
        Collections.shuffle(this.getMultipleChoiceQ());
        List<Question> correctQ = new ArrayList<>();
        List<Question> inCorrectQ = new ArrayList<>();
        String[] notes = new String[100];
        String[] kws = new String[100];
        scanner.nextLine();
        double score = 0;
        for(int i = 0; i < n; i++){
            System.out.println(this.getMultipleChoiceQ().get(i));
            System.out.print("Answer: ");
            kws[i] = scanner.nextLine();
            
            MultipleChoice c = (MultipleChoice) this.getMultipleChoiceQ().get(i);
            for(int j = 0; j < c.getChoices().size(); j++){
                if(c.getChoices().get(j).isCorrect())
                    notes[i] =  String.format("The correct answer is: %s\nNote: %s\n", c.getLABELS()[j], c.getChoices().get(j).getNote());
            }
            
            if(this.getMultipleChoiceQ().get(i).checkAnswer(kws[i])){
                score = 1; // nguoi dung tra loi n cau hoi, moi cau dung duoc 1 diem
                correctQ.add(this.getMultipleChoiceQ().get(i));
            }
            else
                inCorrectQ.add(this.getMultipleChoiceQ().get(i));
            User.setScore(score);
        }
        
        System.out.println("\n========== RESULT ==========");
        for(int i = 0; i < n ;i++){
            System.out.println(this.getMultipleChoiceQ().get(i));
            System.out.println("The answer of user: " + kws[i].toUpperCase());
            System.out.println(notes[i]);
        }
    }
    
    public void practiceInComplete(Scanner scanner, Level lv){
        Collections.shuffle(this.getInCompleteQ());
        List<Question> correctQ = new ArrayList<>();
        List<Question> inCorrectQ = new ArrayList<>();
        String[] notes = new String[100];
        String[] kws = new String[100];
        List<Question> questionsByLevel = new ArrayList<>();
        for(Question q : this.getInCompleteQ()){
            if(q.getLevel().getContent().toUpperCase().equals(lv.getContent().toUpperCase()))
                questionsByLevel.add(q); //Xu ly ngoai le else
        }
        for(int i = 0; i < 1; i++){
            System.out.println(questionsByLevel.get(i));
            InComplete icp = (InComplete) questionsByLevel.get(i);
            int j = 0;
            for(MultipleChoice c : icp.getQuestions()){
                System.out.print("(" + (j + 1) + "): " + c + "Answer: ");
                kws[j] = scanner.nextLine();
                for(int k = 0; k < c.getChoices().size(); k++){
                    if(c.getChoices().get(k).isCorrect())
                        notes[j] =  String.format("The correct answer is: %s\nNote: %s\n", c.getLABELS()[k], c.getChoices().get(k).getNote());
                }
                if(c.checkAnswer(kws[j]))
                    correctQ.add(c);
                else
                    inCorrectQ.add(c);
                j++;
            }
        }
        for(int i = 0; i < 1; i++){
            System.out.println("\n========== RESULT ==========");
            System.out.println(questionsByLevel.get(i));
            InComplete icp = (InComplete) questionsByLevel.get(i);
            int j = 0;
            for(MultipleChoice c : icp.getQuestions()){
                System.out.printf("The correct answer for (%d):%sThe answer of user: %s\n%s\n",
                        (j+1), c, kws[j].toUpperCase(), notes[j]);
                j++;
            }
        }
    }
    
    public void practiceConversation(Scanner scanner, Level lv){
        Collections.shuffle(this.getConversationQ());
        List<Question> correctQ = new ArrayList<>();
        List<Question> inCorrectQ = new ArrayList<>();
        String[] notes = new String[100];
        String[] kws = new String[100];
        List<Question> questionsByLevel = new ArrayList<>();
        for(Question q : this.getConversationQ()){
            if(q.getLevel().getContent().toUpperCase().equals(lv.getContent().toUpperCase()))
                questionsByLevel.add(q); //Xu ly ngoai le else
        }
        for(int i = 0; i < 1; i++){
            System.out.println(questionsByLevel.get(i));
            Conversation cvs = (Conversation) questionsByLevel.get(i);
            int j = 0;
            for(MultipleChoice c : cvs.getQuestions()){
                System.out.print("(" + (j + 1) + "): " + c + "Answer: ");
                kws[j] = scanner.nextLine();
                for(int k = 0; k < c.getChoices().size(); k++){
                    if(c.getChoices().get(k).isCorrect())
                        notes[j] =  String.format("The correct answer is: %s\nNote: %s\n", c.getLABELS()[k], c.getChoices().get(k).getNote());
                }
                if(c.checkAnswer(kws[j]))
                    correctQ.add(c);
                else
                    inCorrectQ.add(c);
                j++;
            }
        }
        for(int i = 0; i < 1; i++){
            System.out.println("\n========== RESULT ==========");
            System.out.println(questionsByLevel.get(i));
            Conversation cvs = (Conversation) questionsByLevel.get(i);
            int j = 0;
            for(MultipleChoice c : cvs.getQuestions()){
                System.out.printf("The correct answer for (%d):%sThe answer of user: %s\n%s\n",
                        (j+1), c, kws[j].toUpperCase(), notes[j]);
                j++;
            }
        }
    }
    
    public List<Question> lookUpByContent(String c){
        List<Question> r = new ArrayList<>();
        for(Question q : this.questions){
            if(q.getContent().toUpperCase().equals(c.toUpperCase()))
                r.add(q);
        }
        return r;
    }
    
    public List<Question> lookUpByCate(String c){
        List<Question> r = new ArrayList<>();
        for(Question q : this.questions){
            if(q.getCategory().toString().equals(c.toUpperCase()))
                r.add(q);
        }
        return r;
    }
    
    public List<Question> lookUpByLevel(String lv){
        List<Question> r = new ArrayList<>();
        for(Question q : this.questions){
            if(q.getLevel().equals(lv.toUpperCase()))
                r.add(q);
        }
        return r;
    }

    /**
     * @return the questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    
}
