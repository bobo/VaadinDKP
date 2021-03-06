/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unknown.entity.character.windows;

import com.unknown.entity.dao.CharacterDAO;
import com.unknown.entity.database.CharacterDB;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author alde
 */
public class AddNewUserWindow extends Window {

        CharacterDAO characterDao;
        final TextField username;
        final TextField password;
        final TextField passwordCheck;

        public AddNewUserWindow() {
                this.username = new TextField("Username");
                this.password = new TextField("Password");
                this.passwordCheck = new TextField("Confirm Password");
                password.setSecret(true);
                passwordCheck.setSecret(true);
                username.setImmediate(true);
                password.setImmediate(true);
                passwordCheck.setImmediate(true);
                this.setCaption("Add User");
                this.center();
                this.addStyleName("opaque");
                this.getContent().setSizeUndefined();
                this.characterDao = new CharacterDB();
        }

        public void printInfo() {
                HorizontalLayout hzl = new HorizontalLayout();
                Button addButton = new Button("Add");
                Button cancelButton = new Button("Cancel");
                addButton.addListener(new AddButtonListener());
                cancelButton.addListener(new CancelButtonListener());

                addComponent(username);
                addComponent(password);
                addComponent(passwordCheck);

                hzl.addComponent(addButton);
                hzl.addComponent(cancelButton);
                addComponent(hzl);

        }

        private class CancelButtonListener implements ClickListener {

                @Override
                public void buttonClick(ClickEvent event) {
                        close();
                }
        }

        private class AddButtonListener implements ClickListener {

                @Override
                public void buttonClick(ClickEvent event) {
                        if (checkPassword()) {
                                String hashedpassword = hashPassword(password.getValue().toString());
                                int success = characterDao.addNewSiteUser(username.getValue().toString(), hashedpassword, 1);
                                System.out.println(success + " user added");
                        } else {
                                Label err = new Label("Passwords must match!");
                                err.addStyleName("error");
                                getWindow().addComponent(err);
                        }
                }

                public String hashPassword(String password) {
                        String hashword = null;
                        try {
                                MessageDigest md5 = MessageDigest.getInstance("MD5");
                                md5.update(password.getBytes());
                                BigInteger hash = new BigInteger(1, md5.digest());
                                hashword = hash.toString(16);
                                if (hashword.length() == 31) {
                                        hashword = "0" + hashword;
                                }
                        } catch (NoSuchAlgorithmException nsae) { nsae.printStackTrace();
                        }
                        return hashword;
                }

                private boolean checkPassword() {
                        return password.getValue().equals(passwordCheck.getValue());
                }
        }
}
