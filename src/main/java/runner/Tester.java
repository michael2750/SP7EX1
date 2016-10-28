/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import entity.User;
import facades.UserFacade;

/**
 *
 * @author ejer
 */
public class Tester {

    public static void main(String[] args) {
        UserFacade UF = new UserFacade();
        User user = new User();
        user.setUserName("Swag");
        user.setPassword("1234");
        user.addRole("sej");
        UF.addUser(user);
        User user2 = UF.getUserByName("Swag");
        System.out.println(user2.getUserName());
        System.out.println(UF.authenticateUser("Swag", "1234"));
    }
}
