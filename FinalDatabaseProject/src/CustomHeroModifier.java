import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomHeroModifier
{
    private static DBFunctions dbf = new DBFunctions();
    private static Connection con = dbf.con;
    
    private InputChecker ic = new InputChecker();
    
    public CustomHero chooseHeroFromDB()
    {
        CustomHero hero = new CustomHero();
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalog()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-5s%-15s%-15s%-15s%-5s%-5s%-5s%-5s%-5s\n","#", "Hero Name", "Weapon Type", "Movement Type", "HP", "ATK", "SPD", "DEF", "RES");
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
                System.out.printf("%-5s%-15s%-15s%-15s%-5s%-5s%-5s%-5s%-5s\n", rs.getString("hero_ID"),rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }
            System.out.print("Enter Character Choice: ");
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
    
    public CustomHero chooseWeaponFromDB(CustomHero hero)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayWeaponByType(?)");
            cs.clearParameters();
            cs.setInt(1, hero.getWeaponType());
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-5s%-20s%-15s%-20s%-20s\n","#", "Weapon Name", "Weapon Type", "Weapon Strength", "Weapon Effect");
            int min = 1;
            int max = 0;
            List<CustomHero> customHeroes = new ArrayList<CustomHero>();
            while(rs.next())
            {
                CustomHero heroForList = new CustomHero();
                heroForList.setWeaponID(rs.getInt("weapon_ID"));
                heroForList.setWeaponATK(rs.getInt("weapon_strength"));
                heroForList.setWeaponSPDModifier(rs.getInt("weapon_spd_modifier"));
                customHeroes.add(heroForList);
                max++;
                System.out.printf("%-5s%-20s%-15s%-20s%-20s\n", max + ".",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }
            System.out.print("Enter Weapon Choice: ");
            int selection = ic.readInteger(max, min);
            hero.setWeaponID(customHeroes.get(selection - 1).getWeaponID());
            hero.setATK(hero.getATK() + customHeroes.get(selection - 1).getWeaponATK());
            if(customHeroes.get(selection - 1).getWeaponSPDModifier() != 0)
            {
                hero.setSPD(hero.getSPD() + customHeroes.get(selection - 1).getWeaponSPDModifier());
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return hero;
    }
    
    public CustomHero chooseAssistFromDB(CustomHero hero)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayAssists()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-5s%-22s%-22s\n", "#", "Assist Name","Description");
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
                System.out.printf("%-5s%-22s%-22s\n", rs.getString("assist_ID") + ".", rs.getString("assist_name"), rs.getString("assist_description"));
            }
            System.out.print("Enter Assist Skill Choice: ");
            int selection = ic.readInteger(max, min);
            hero.setAssistID(selection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return hero;
    }
    
    public CustomHero chooseSpecialFromDB(CustomHero hero)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplaySpecials()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-5s%-22s%-15s%-22s\n", "#", "Special Name", "Cool Down", "Description");
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
                System.out.printf("%-5s%-22s%-15s%-22s\n", rs.getString("special_ID") + ".", rs.getString("special_name"), rs.getString("cooldown"), rs.getString("special_description"));
            }
            System.out.print("Enter Special Skill Choice: ");
            int selection = ic.readInteger(max, min);
            hero.setSpecialID(selection);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return hero;
    }
    
    public CustomHero chooseASkillFromDB(CustomHero hero)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayASkills()");
            ResultSet rs = cs.executeQuery();
            
            System.out.printf("%-5s%-22s%-15s%-15s%-15s%-15s%-15s\n", "#", "Skill Name", "HP Modifier","ATK Modifier", "SPD Modifier", "DEF Modifier", "RES Modifier");
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
                System.out.printf("%-5s%-22s%-15s%-15s%-15s%-15s%-15s\n", rs.getString("slotA_ID") + ".", rs.getString("slotA_name"),rs.getString("hp_modifier"), rs.getString("atk_modifier"), rs.getString("spd_modifier"), rs.getString("def_modifier"), rs.getString("res_modifier"));
            }
            System.out.print("Enter Slot A Skill Choice: ");
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
    
    public CustomHero chooseBSkillFromDB(CustomHero hero)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayBSkills()");
            ResultSet rs = cs.executeQuery();
            
            System.out.printf("%-5s%-22s%-22s\n", "#", "Skill Name", "Description");
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
                System.out.printf("%-5s%-22s%-22s\n", rs.getString("slotB_ID") + ".", rs.getString("slotB_name"), rs.getString("slotB_description"));
            }
            System.out.print("Enter Slot B Skill Choice: ");
            int selection = ic.readInteger(max, min);
            hero.setSlotBSkill(selection);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return hero;
    }
    
    public CustomHero chooseCSkillFromDB(CustomHero hero)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayCSkills()");
            ResultSet rs = cs.executeQuery();
            
            System.out.printf("%-5s%-22s%-22s\n", "#", "Skill Name", "Description");
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
                System.out.printf("%-5s%-22s%-22s\n", rs.getString("slotC_ID") + ".", rs.getString("slotC_name"),rs.getString("slotC_description"));
            }
            System.out.print("Enter Slot C Skill Choice: ");
            int selection = ic.readInteger(max, min);
            hero.setSlotCSkill(selection);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return hero;
    }
    
    public void saveCustomHero(CustomHero hero)
    {
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
    
    public void viewAllCustomHeroes()
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayAllCustomHeroes()");
            ResultSet rs = cs.executeQuery();
            printCustomHeroResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int displayAllCustomHeroesForSelection()
    {
        int heroID = 0;
        
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayCustomHeroes()");
            ResultSet rs = cs.executeQuery();
            
            System.out.printf("%-5s%-22s\n", "#", "Hero Name");
            int min = 1;
            int max = 0;
            
            List<CustomHero> customHeroes = new ArrayList<CustomHero>();
            
            while(rs.next())
            {
                CustomHero heroForList = new CustomHero();
                heroForList.setHeroID(rs.getInt("hero_ID"));
                heroForList.setHeroName(rs.getString("hero_name"));
                customHeroes.add(heroForList);
                max++;
                System.out.printf("%-5s%-22s\n", max + ".", rs.getString("hero_name"));
            }
            max++;
            System.out.println(max + ". Return To Main Menu");
            System.out.print("Enter Custom Hero Choice: ");
            int selection = ic.readInteger(max, min);
            if (selection != max)
            {
                heroID = customHeroes.get(selection - 1).getHeroID();
            }
            else
            {
                heroID = -1;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return heroID;
    }
    
    public void viewCustomHero(int heroID)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayCustomHeroesWithDetails(?)");
            cs.clearParameters();
            cs.setInt(1, heroID);
            ResultSet rs = cs.executeQuery();
            printCustomHeroResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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
                hero.setSPD(rs.getInt("SPD"));
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
    
    public CustomHero adjustWeaponModifierStats(CustomHero hero)
    {
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT weapon_ID, weapon_strength,weapon_spd_modifier FROM weapons WHERE weapon_ID = ?");
            pst.clearParameters();
            pst.setInt(1,hero.getWeaponID());
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                int weaponSPDModifier = rs.getInt("weapon_spd_modifier");
                if(weaponSPDModifier != 0)
                {
                    int heroSPD = hero.getSPD();
                    hero.setSPD(heroSPD - rs.getInt("weapon_spd_modifier"));
                }
                hero.setATK(hero.getATK() - rs.getInt("weapon_strength"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return hero;
    }
    
    public CustomHero adjustASlotSkillStats(CustomHero hero)
    {
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM slota_skills WHERE slotA_ID = ?");
            pst.clearParameters();
            pst.setInt(1,hero.getSlotASkill());
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                hero.setATK(hero.getATK() - rs.getInt("atk_modifier"));
                hero.setSPD(hero.getSPD() - rs.getInt("spd_modifier"));
                hero.setDEF(hero.getDEF() - rs.getInt("def_modifier"));
                hero.setRES(hero.getRES() - rs.getInt("res_modifier"));
                hero.setHP(hero.getHP() - rs.getInt("hp_modifier"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return hero;
    }
    
    public void updateCustomHero(CustomHero hero)
    {
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
            cs.execute();
        }
        catch (SQLException e)
        {
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
    
    private void printCustomHeroResults(ResultSet rs)
    {
        try
        {
            System.out.printf("%-15s%-20s%-15s%-15s%-5s%-5s%-5s%-5s%-10s%-20s%-20s%-20s\n", "Hero", "Weapon", "Assist Skill", "Special", "HP", "ATK", "SPD", "DEF", "RES", "Slot A", "SlotB", "SlotC");
            
            while(rs.next())
            {
                System.out.printf("%-15s%-20s%-15s%-15s%-5s%-5s%-5s%-5s%-10s%-20s%-20s%-20s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
