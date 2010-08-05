/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.character;

import com.unknown.entity.Role;
import com.unknown.entity.User;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author bobo
 */
public interface CharacterDAO {

    public List<User> getUsers();

    public Collection<User> getUsersWithRole(Role role);

}
