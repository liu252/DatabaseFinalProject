import com.mysql.fabric.xmlrpc.base.Value;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.ArrayList;
import java.util.List;

public class DBFunctions
{
    private static Connection con;

    public DBFunctions() {
        try {
            con = DBConfig.getConnection();
            if(con.isClosed()) {
                System.out.println("Connection was closed; creating new connection to MySQL.");
                con = DBConfig.getConnection();
            }
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }


    public InputChecker ic = new InputChecker();

    public CustomHero chooseHeroFromDB()
    {
        CustomHero hero = new CustomHero();
        try
        {
            CallableStatement cs = con.prepareCall("CALL ShowHeroRoster()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-5s%-22s\n", "#", "Hero Name");
            boolean first = true;
            int min = 0;
            int max = 0;
            while(rs.next())
            {
                if(first)
                {
                    min = rs.getInt("hero_ID");
                    max = rs.getInt("hero_ID") - 1 ;
                    first = false;
                }
                max++;
                System.out.printf("%-5s%-22s\n", rs.getInt("hero_ID") + ".", rs.getString("hero_name"));
            }
            int selection = ic.readInteger(max, min);
            cs = con.prepareCall("CALL SelectHero(?)");
            cs.clearParameters();
            cs.setInt(1, selection);
            rs = cs.executeQuery();

            while(rs.next())
            {
                hero.setHeroID(rs.getInt("hero_ID"));
                hero.setHeroName(rs.getString("hero_name"));
                hero.setWeaponType(rs.getInt("weapon_type"));
                hero.setHP(rs.getInt("HP"));
                hero.setATK(rs.getInt("ATK"));
                hero.setDEF(rs.getInt("DEF"));
                hero.setSPD(rs.getInt("SPD"));
                hero.setRES(rs.getInt("RES"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return hero;
    }

    public CustomHero chooseWeaponFromDB(CustomHero heroInfo)
    {
        CustomHero hero = heroInfo;
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayWeaponByType(?)");
            cs.clearParameters();
            cs.setInt(1, hero.getWeaponType());
            ResultSet rs = cs.executeQuery();

            System.out.printf("%-5s%-22s\n", "#", "Weapon Name");
            int min = 1;
            int max = 1;

            List<CustomHero> customHeroes = new ArrayList<CustomHero>();

            while(rs.next())
            {
                CustomHero heroForList = new CustomHero();
                heroForList.setWeaponID(rs.getInt("weapon_ID"));
                heroForList.setWeaponATK(rs.getInt("weapon_strength"));
                customHeroes.add(heroForList);
                System.out.printf("%-5s%-22s\n", max++ + ".", rs.getString("weapon_name"));
            }

            int selection = ic.readInteger(max, min);
            hero.setWeaponID(customHeroes.get(selection - 1).getWeaponID());
            hero.setATK(hero.getATK() + customHeroes.get(selection - 1).getWeaponATK());

            if(hero.getWeaponID() == 2 || hero.getWeaponID() == 16 || hero.getWeaponID() == 33) {
                hero.setSPD(hero.getSPD() - 5);
            }

            while(rs.next())
            {
                hero.setWeaponID(rs.getInt("weapon_ID"));
                hero.setATK(hero.getATK() + rs.getInt("weapon_strength"));
                if(rs.getInt("weapon_ID") == 2 || rs.getInt("weapon_ID") == 16 || rs.getInt("weapon_ID") == 33)
                {
                    hero.setSPD(hero.getSPD() - 5);
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return hero;
    }

    public CustomHero chooseAssistFromDB(CustomHero heroInfo)
    {
        CustomHero hero = heroInfo;
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayAssists()");
            ResultSet rs = cs.executeQuery();

            System.out.printf("%-5s%-22s\n", "#", "Assist Name");
            boolean first = true;
            int min = 0;
            int max = 0;

            while(rs.next())
            {
                if(first)
                {
                    min = rs.getInt("assist_ID");
                    max = rs.getInt("assist_ID") - 1 ;
                    first = false;
                }
                max++;
                System.out.printf("%-5s%-22s\n", rs.getString("assist_ID") + ".", rs.getString("assist_name"));
            }

            int selection = ic.readInteger(max, min);
            hero.setAssistID(selection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return hero;
    }

    public CustomHero chooseSpecialFromDB(CustomHero heroInfo)
    {
        CustomHero hero = heroInfo;
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplaySpecials()");
            ResultSet rs = cs.executeQuery();

            System.out.printf("%-5s%-22s\n", "#", "Special Name");
            boolean first = true;
            int min = 0;
            int max = 0;

            while(rs.next())
            {
                if(first)
                {
                    min = rs.getInt("special_ID");
                    max = rs.getInt("special_ID") - 1 ;
                    first = false;
                }
                max++;
                System.out.printf("%-5s%-22s\n", rs.getString("special_ID") + ".", rs.getString("special_name"));
            }

            int selection = ic.readInteger(max, min);
            hero.setSpecialID(selection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return hero;
    }

    public CustomHero chooseASkillFromDB(CustomHero heroInfo)
    {
        CustomHero hero = heroInfo;
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayASkills()");
            ResultSet rs = cs.executeQuery();

            System.out.printf("%-5s%-22s\n", "#", "Skill Name");
            boolean first = true;
            int min = 0;
            int max = 0;

            while(rs.next())
            {
                if(first)
                {
                    min = rs.getInt("slotA_ID");
                    max = rs.getInt("slotA_ID") - 1 ;
                    first = false;
                }
                max++;
                System.out.printf("%-5s%-22s\n", rs.getString("slotA_ID") + ".", rs.getString("slotA_name"));
            }
            int selection = ic.readInteger(max, min);
            hero.setSlotASkill(selection);

            cs = con.prepareCall("CALL SelectASkill(?)");
            cs.clearParameters();
            cs.setInt(1, selection);
            rs = cs.executeQuery();

            while(rs.next())
            {
                hero.setATK(hero.getATK() + rs.getInt("atk_modifier"));
                hero.setSPD(hero.getSPD() + rs.getInt("spd_modifier"));
                hero.setDEF(hero.getDEF() + rs.getInt("def_modifier"));
                hero.setRES(hero.getRES() + rs.getInt("res_modifier"));
                hero.setHP(hero.getHP() + rs.getInt("hp_modifier"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return hero;
    }

    public CustomHero chooseBSkillFromDB(CustomHero heroInfo)
    {
        CustomHero hero = heroInfo;
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayBSkills()");
            ResultSet rs = cs.executeQuery();

            System.out.printf("%-5s%-22s\n", "#", "Skill Name");
            boolean first = true;
            int min = 0;
            int max = 0;

            while(rs.next())
            {
                if(first)
                {
                    min = rs.getInt("slotB_ID");
                    max = rs.getInt("slotB_ID") - 1 ;
                    first = false;
                }
                max++;
                System.out.printf("%-5s%-22s\n", rs.getString("slotB_ID") + ".", rs.getString("slotB_name"));
            }

            int selection = ic.readInteger(max, min);
            hero.setSlotBSkill(selection);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return hero;
    }

    public CustomHero chooseCSkillFromDB(CustomHero heroInfo)
    {
        CustomHero hero = heroInfo;
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayCSkills()");
            ResultSet rs = cs.executeQuery();

            System.out.printf("%-5s%-22s\n", "#", "Skill Name");
            boolean first = true;
            int min = 0;
            int max = 0;

            while(rs.next())
            {
                if(first)
                {
                    min = rs.getInt("slotC_ID");
                    max = rs.getInt("slotC_ID") - 1 ;
                    first = false;
                }
                max++;
                System.out.printf("%-5s%-22s\n", rs.getString("slotC_ID") + ".", rs.getString("slotC_name"));
            }

            int selection = ic.readInteger(max, min);
            hero.setSlotCSkill(selection);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return hero;
    }

    public void saveCustomHero(CustomHero heroInfo)
    {
        CustomHero hero = heroInfo;
        try
        {
            CallableStatement cs = con.prepareCall("CALL SaveCustomHero(?,?,?,?,?,?,?,?,?,?,?,?)");
            cs.clearParameters();
            cs.setString(1, hero.getHeroName());
            cs.setInt(2, hero.getWeaponID());
            cs.setInt(3, hero.getAssistID());
            cs.setInt(4, hero.getSpecialID());
            cs.setInt(5, hero.getHP());
            cs.setInt(6, hero.getATK());
            cs.setInt(7, hero.getSPD());
            cs.setInt(8, hero.getDEF());
            cs.setInt(9, hero.getRES());
            cs.setInt(10, hero.getSlotASkill());
            cs.setInt(11, hero.getSlotBSkill());
            cs.setInt(12, hero.getSlotCSkill());
            cs.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewCustomHero(int heroID)
    {
        try
        {

            CallableStatement cs = con.prepareCall("CALL DisplayCustomHeroesWithDetails(?)");
            cs.clearParameters();
            cs.setInt(1, heroID);

            ResultSet rs = cs.executeQuery();
    
            System.out.printf("%-15s%-15s%-15s%-15s%-5s%-5s%-5s%-5s%-10s%-20s%-20s%-20s\n", "Hero", "Weapon", "Assist Skill", "Special", "HP", "ATK", "SPD", "DEF", "RES", "Slot A", "SlotB", "SlotC");

            while(rs.next())
            {
                System.out.printf("%-15s%-15s%-15s%-15s%-5s%-5s%-5s%-5s%-10s%-20s%-20s%-20s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomHero(int heroID)
    {
        try
        {
            PreparedStatement pst = con.prepareStatement("UPDATE customheroes SET isDeleted = 1 WHERE hero_ID = ?");
            pst.clearParameters();
            pst.setInt(1, heroID);
            pst.executeUpdate();

            System.out.println("Custom hero has been soft deleted.");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayHeroesByWeaponType()
    {
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM weapon_types");
            ResultSet rs = pst.executeQuery();

            System.out.printf("%-5s%-22s\n", "#", "Weapon Type");
            boolean first = true;
            int min = 0;
            int max = 0;

            while(rs.next())
            {
                if(first)
                {
                    min = rs.getInt("weapontypeID");
                    max = rs.getInt("weapontypeID") - 1 ;
                    first = false;
                }
                max++;
                System.out.printf("%-5s%-22s\n", rs.getString("weapontypeID") + ".", rs.getString("weapon_type_name"));
            }

            int selection = ic.readInteger(max, min);


            pst = con.prepareStatement("SELECT hero_ID, hero_name FROM herocatalog WHERE weapon_type = ?");
            pst.clearParameters();
            pst.setInt(1, selection);
            rs = pst.executeQuery();

            min = 1;
            max = 1;

            List<CustomHero> customHeroes = new ArrayList<CustomHero>();

            while(rs.next())
            {
                CustomHero heroForList = new CustomHero();
                heroForList.setHeroID(rs.getInt("hero_ID"));
                heroForList.setHeroName(rs.getString("hero_name"));
                customHeroes.add(heroForList);
                System.out.printf("%-5s%-22s\n", max++ + ".", rs.getString("hero_name"));
            }

            selection = ic.readInteger(max, min);
            pst = con.prepareStatement("SELECT * FROM herocatalog WHERE hero_ID = ?");
            pst.clearParameters();
            pst.setInt(1, customHeroes.get(selection - 1).getHeroID());
            rs = pst.executeQuery();

            while(rs.next())
            {
                System.out.println("Hero: " + rs.getString("hero_name"));
                System.out.print("HP:" + rs.getInt("HP"));
                System.out.print(" ATK:" + rs.getInt("ATK"));
                System.out.print(" SPD:" + rs.getInt("SPD"));
                System.out.print(" DEF:" + rs.getInt("DEF"));
                System.out.print(" RES:" + rs.getInt("RES"));
                System.out.println();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int displayAllCustomHeroes()
    {
        int heroID = 0;

        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayCustomHeroes()");
            ResultSet rs = cs.executeQuery();

            System.out.printf("%-5s%-22s\n", "#", "Hero Name");
            int min = 1;
            int max = 1;

            List<CustomHero> customHeroes = new ArrayList<CustomHero>();

            while(rs.next())
            {
                CustomHero heroForList = new CustomHero();
                heroForList.setHeroID(rs.getInt("hero_ID"));
                heroForList.setHeroName(rs.getString("hero_name"));
                customHeroes.add(heroForList);
                System.out.printf("%-5s%-22s\n", max++ + ".", rs.getString("hero_name"));
            }

            int selection = ic.readInteger(max, min);

            heroID = customHeroes.get(selection - 1).getHeroID();



        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return heroID;
    }

    public CustomHero findCustomHeroForUpdate(int updateSelection)
    {
        CustomHero hero = new CustomHero();

        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM customheroes WHERE hero_ID = ?");
            pst.clearParameters();
            pst.setInt(1,updateSelection);
            ResultSet rs = pst.executeQuery();

            while (rs.next())
            {
                hero.setHeroID(rs.getInt("hero_ID"));
                hero.setHeroName(rs.getString("hero_name"));
                hero.setWeaponID(rs.getInt("weapon"));
                hero.setAssistID(rs.getInt("assist_skill"));
                hero.setSpecialID(rs.getInt("special_skill"));
                hero.setHP(rs.getInt("HP"));
                hero.setATK(rs.getInt("ATK"));
                hero.setDEF(rs.getInt("DEF"));
                hero.setRES(rs.getInt("RES"));
                hero.setSlotASkill(rs.getInt("slotA_skill"));
                hero.setSlotBSkill(rs.getInt("slotB_skill"));
                hero.setSlotCSkill(rs.getInt("slotC_skill"));
            }

            pst = con.prepareStatement("SELECT weapon_type FROM weapons WHERE weapon_ID = ?");
            pst.clearParameters();
            pst.setInt(1, hero.getWeaponID());
            rs = pst.executeQuery();

            while (rs.next())
            {
                hero.setWeaponType(rs.getInt("weapon_type"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return hero;
    }

    public void updateCustomHero(CustomHero heroInfo)
    {
        CustomHero hero = heroInfo;
        try
        {
            CallableStatement cs = con.prepareCall("CALL UpdateCustomHero(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            cs.clearParameters();
            cs.setString(1, hero.getHeroName());
            cs.setInt(2, hero.getWeaponID());
            cs.setInt(3, hero.getAssistID());
            cs.setInt(4, hero.getSpecialID());
            cs.setInt(5, hero.getHP());
            cs.setInt(6, hero.getATK());
            cs.setInt(7, hero.getSPD());
            cs.setInt(8, hero.getDEF());
            cs.setInt(9, hero.getRES());
            cs.setInt(10, hero.getSlotASkill());
            cs.setInt(11, hero.getSlotBSkill());
            cs.setInt(12, hero.getSlotCSkill());
            cs.setInt(13, hero.getHeroID());
            cs.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    
    public void viewHeroCatalog()
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalog()");
            ResultSet rs = cs.executeQuery();
    
            System.out.printf("%-15s%-15s%-15s%-5s%-5s%-5s%-5s%-5s\n", "Hero Name", "Weapon Type", "Movement Type", "HP", "ATK", "SPD", "DEF", "RES");
            
            while(rs.next())
            {
                System.out.printf("%-15s%-15s%-15s%-5s%-5s%-5s%-5s%-5s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewEntireWeaponsCatalog()
    {
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM weaponscatalog");
            ResultSet rs = pst.executeQuery();
    
            System.out.printf("%-20s%-15s%-20s%-20s\n", "Weapon Name", "Weapon Type", "Weapon Strength", "Weapon Effect");
            
            while(rs.next())
            {
                System.out.printf("%-20s%-15s%-20s%-20s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public CustomHero displayWeaponTypes()
    {
        CustomHero userSelection = new CustomHero();
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM weapon_types");
            ResultSet rs = pst.executeQuery();
            
            System.out.println("Please select the weapon type you would like to display");
            int min = 1;
            int max = 1;
    
            List<CustomHero> customHeroes = new ArrayList<CustomHero>();
    
            while(rs.next())
            {
                CustomHero heroForList = new CustomHero();
                heroForList.setWeaponType(rs.getInt("weapontypeID"));
                customHeroes.add(heroForList);
                System.out.printf("%-5s%-22s\n", max++ + ".", rs.getString("weapon_type_name"));
            }
    
            int selection = ic.readInteger(max, min);
            userSelection.setWeaponType(customHeroes.get(selection - 1).getWeaponType());
            
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return userSelection;
    
    }
    
    public void viewWeaponsCatalogByType(CustomHero weaponTypeSelection)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayWeaponsCatalogByType(?)");
            cs.clearParameters();
            cs.setInt(1,weaponTypeSelection.getWeaponType());
            ResultSet rs = cs.executeQuery();
        
            System.out.printf("%-20s%-15s%-20s%-20s\n", "Weapon Name", "Weapon Type", "Weapon Strength", "Weapon Effect");
        
            while(rs.next())
            {
                System.out.printf("%-20s%-15s%-20s%-20s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
