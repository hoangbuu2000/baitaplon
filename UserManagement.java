/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.baitaplon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Buu
 */
public class UserManagement {
    private List<User> users = new ArrayList<>();
    
    public void addUser(User u){
        this.getUsers().add(u);
    }
    
    public void removeUser(User u){
        this.getUsers().remove(u);
    }
    
    public void updateUser(User u, Scanner scanner) throws ParseException{
        System.out.println("Please enter infomation need to be updated!!");
        System.out.print("Full name: ");
        u.setFullName(scanner.nextLine());
        System.out.print("Password: ");
        u.setPassword(scanner.nextLine());
        System.out.print("Gender: ");
        u.setGender(scanner.nextLine());
        System.out.println("Date of birth(dd/mm/yyyy): ");
        String dob = scanner.nextLine();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        u.setDob(f.parse(dob));
        System.out.print("Country: ");
        u.setCountry(scanner.nextLine());
    }
    
    public void viewList(){
        for(User u : this.getUsers()){
            System.out.println(u);
        }
    }
    
    public List<User> lookUpByFullName(String fn){
        List<User> r = new ArrayList<>();
        for(User u : this.getUsers()){
            if(u.getFullName().toUpperCase().equals(fn.toUpperCase()))
                r.add(u);
        }
        return r;
    }
    
    public List<User> lookUpByGender(String g){
        List<User> r = new ArrayList<>();
        for(User u : this.getUsers()){
            if(u.getGender().toUpperCase().equals(g.toUpperCase()))
                r.add(u);
        }
        return r;
    }
    
    public List<User> lookUpByDOB(String dob) throws ParseException{
        List<User> r = new ArrayList<>();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        for(User u : this.getUsers()){
            if(u.getDob().equals(f.parse(dob)))
                r.add(u);
        }
        return r;
    }
    
    public List<User> lookUpByCountry(String c){
        List<User> r = new ArrayList<>();
        for(User u : this.getUsers()){
            if(u.getCountry().toUpperCase().equals(c.toUpperCase()))
                r.add(u);
        }
        return r;
    }

    /**
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
}
