/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author bobo
 */
public class FakeDB implements CharacterDAO {

    @Override
    public List<User> getUsers() {
    return Arrays.asList(
//	    new User("nils", Role.rogue,0, 100),
//	    new User("kalle", Role.druid,10,4),
//    	    new User("kalle2", Role.druid,100,56),
//    	    new User("kalle3", Role.mage,45,12),
//    	    new User("kalle4", Role.warrior,46,453),
//    	    new User("kalle5", Role.paladin,9876,1));
    );
}
    @Override
    public Collection<User> getUsersWithRole(final Role role) {
    return Collections2.filter(getUsers(), new HasRolePredicate(role));
    }

    private static class HasRolePredicate implements Predicate<User> {

	private final Role role;

	public HasRolePredicate(Role role) {
	    this.role = role;
	}

	@Override
	public boolean apply(User user) {
	    return user.getRole().equals(role);
	}
    }

}

