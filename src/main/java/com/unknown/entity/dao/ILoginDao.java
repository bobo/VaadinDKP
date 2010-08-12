/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.dao;

import com.unknown.entity.character.SiteUser;

/**
 *
 * @author bobo
 */
public interface ILoginDao {

	SiteUser checkLogin(String username, String password);

}
