/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author bobo
 */
public interface CharacherDAO {

    public List<User> getUsers();

    public Collection<User> getUsersWithRole(Role role);

}
