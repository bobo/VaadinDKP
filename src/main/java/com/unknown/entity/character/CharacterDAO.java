/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.character;

import com.unknown.entity.Role;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author bobo
 */
public interface CharacterDAO {

    public List<Character> getUsers();

    public Collection<Character> getUsersWithRole(Role role);

}
