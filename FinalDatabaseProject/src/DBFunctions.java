import com.mysql.fabric.xmlrpc.base.Value;

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
            boolean first = true;
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

    public void viewCustomHero()
    {
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
            cs = con.prepareCall("CALL SelectCustomHero(?)");
            cs.clearParameters();
            cs.setInt(1, customHeroes.get(selection - 1).getHeroID());

            rs = cs.executeQuery();


            while(rs.next())
            {
                System.out.println();
                System.out.println(rs.getString("hero_name"));

                PreparedStatement pst = con.prepareStatement("SELECT weapon_name FROM weapons WHERE weapon_ID = ?");
                pst.clearParameters(); pst.setInt(1, rs.getInt("weapon"));
                ResultSet rs2 = pst.executeQuery(); rs2.next();
                System.out.println(rs2.getString(1));

                pst = con.prepareStatement("SELECT assist_name FROM assists WHERE assist_ID = ?");
                pst.clearParameters(); pst.setInt(1, rs.getInt("assist_skill"));
                rs2 = pst.executeQuery(); rs2.next();
                System.out.println(rs2.getString(1));

                pst = con.prepareStatement("SELECT special_name FROM specials WHERE special_ID = ?");
                pst.clearParameters(); pst.setInt(1, rs.getInt("special_skill"));
                rs2 = pst.executeQuery(); rs2.next();
                System.out.println(rs2.getString(1));

                System.out.println("HP:  " + rs.getInt("HP"));
                System.out.println("ATK: " + rs.getInt("ATK"));
                System.out.println("SPD: " + rs.getInt("SPD"));
                System.out.println("DEF: " + rs.getInt("DEF"));
                System.out.println("RES: " + rs.getInt("RES"));

                pst = con.prepareStatement("SELECT slotA_name FROM slota_skills WHERE slotA_ID = ?");
                pst.clearParameters(); pst.setInt(1, rs.getInt("slotA_skill"));
                rs2 = pst.executeQuery(); rs2.next();
                System.out.println(rs2.getString(1));

                pst = con.prepareStatement("SELECT slotB_name FROM slotb_skills WHERE slotB_ID = ?");
                pst.clearParameters(); pst.setInt(1, rs.getInt("slotB_skill"));
                rs2 = pst.executeQuery(); rs2.next();
                System.out.println(rs2.getString(1));

                pst = con.prepareStatement("SELECT slotC_name FROM slotc_skills WHERE slotC_ID = ?");
                pst.clearParameters(); pst.setInt(1, rs.getInt("slotC_skill"));
                rs2 = pst.executeQuery(); rs2.next();
                System.out.println(rs2.getString(1));

                System.out.println();
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomHero()
    {
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
            PreparedStatement pst = con.prepareStatement("UPDATE customheroes SET isDeleted = 1 WHERE hero_ID = ?");
            pst.clearParameters();
            pst.setInt(1, customHeroes.get(selection - 1).getHeroID());
            pst.executeUpdate();

            System.out.println("Custom hero has been soft deleted.");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayHeroes()
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
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

