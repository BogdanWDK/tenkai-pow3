package org.l2jmobius.gameserver.events.instanced;

import org.l2jmobius.gameserver.instancemanager.ZoneManager;
import org.l2jmobius.gameserver.model.zone.type.TenkaiEventZone;
import org.l2jmobius.gameserver.util.Point3D;
import org.l2jmobius.gameserver.util.xml.XmlNode;

import java.util.ArrayList;

public class EventLocation {
    private final int id;
    private final String name;
    private final ArrayList<Point3D> spawns;
    private final int globalZ;
    private final int maxTeamPlayers;
    private final boolean hill;

    private TenkaiEventZone zone = null;

    public EventLocation(XmlNode node) {
        id = node.getInt("id");
        name = node.getString("name");
        globalZ = node.getInt("globalZ");
        maxTeamPlayers = node.getInt("maxTeamPlayers");
        hill = node.getBool("hill", false);

        spawns = new ArrayList<>();
        for (XmlNode subNode : node.getChildren()) {
            if (subNode.getName().equals("spawn")) {
                int x = subNode.getInt("x");
                int y = subNode.getInt("y");
                int z = subNode.getInt("z");
                spawns.add(new Point3D(x, y, z));
            }
        }
    }

    public int getMaxPlayers() {
        return maxTeamPlayers * spawns.size();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isHill() {
        return hill;
    }

    public int getGlobalZ() {
        return globalZ;
    }

    public int getTeamCount() {
        return spawns.size();
    }

    public int getMaxTeamPlayers() {
        return maxTeamPlayers;
    }

    public Point3D getSpawn(int id) {
        if (id < spawns.size()) {
            return spawns.get(id);
        }

        return new Point3D(0, 0, 0);
    }

    public TenkaiEventZone getZone() {
        if (zone == null) {
            zone = ZoneManager.getInstance().getZoneById(id + TenkaiEventZone.BASE_ID, TenkaiEventZone.class);
        }

        return zone;
    }
}
