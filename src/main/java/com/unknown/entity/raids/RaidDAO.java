/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity.raids;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alde
 */

public interface RaidDAO {

    public List<Raid> getRaids();
    public List<String> getRaidZoneList();
    public int addNewRaid(String zone, String comment, String date);

    public int doRaidUpdate(Raid raid, String raidzoneName, String raidcomment, String raiddate) throws SQLException;
}
