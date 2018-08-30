import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogViewer
{
    private static DBFunctions dbf = new DBFunctions();
    private static Connection con = dbf.con;
    
    private InputChecker ic = new InputChecker();
    
    public void viewEntireWeaponsCatalog()
    {
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM weaponscatalog");
            ResultSet rs = pst.executeQuery();
            printWeaponCatalogResults(rs);
            rs = pst.executeQuery();
            dbf.saveToCSV(rs);
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
            int max = 0;
            
            List<CustomHero> customHeroes = new ArrayList<CustomHero>();
            
            while(rs.next())
            {
                CustomHero heroForList = new CustomHero();
                heroForList.setWeaponType(rs.getInt("weapontypeID"));
                customHeroes.add(heroForList);
                max++;
                System.out.printf("%-5s%-22s\n", max + ".", rs.getString("weapon_type_name"));
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
            printWeaponCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewStrongestWeaponInWeaponsCatalog()
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayStrongestWeapons()");
            ResultSet rs = cs.executeQuery();
            printWeaponCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void viewStrongestWeaponInWeaponsCatalogByStrength(int strength)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayStrongestWeaponsByStrength(?)");
            cs.clearParameters();
            cs.setInt(1, strength);
            ResultSet rs = cs.executeQuery();
            printWeaponCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
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
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByWeaponType(CustomHero weaponSelection)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByWeaponType(?)");
            cs.clearParameters();
            cs.setInt(1, weaponSelection.getWeaponType());
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public CustomHero displayMovementTypes()
    {
        CustomHero userSelection = new CustomHero();
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM movement_types");
            ResultSet rs = pst.executeQuery();
            
            System.out.println("Please select the movement type you would like to display");
            int min = 1;
            int max = 0;
            
            List<CustomHero> customHeroes = new ArrayList<CustomHero>();
            
            while(rs.next())
            {
                CustomHero heroForList = new CustomHero();
                heroForList.setMovementType(rs.getInt("movement_type_ID"));
                customHeroes.add(heroForList);
                max++;
                System.out.printf("%-5s%-22s\n", max + ".", rs.getString("movement_type_name"));
            }
            int selection = ic.readInteger(max, min);
            userSelection.setMovementType(customHeroes.get(selection - 1).getMovementType());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return userSelection;
    }
    
    public void viewHeroCatalogByMovementType(CustomHero movementSelection)
    {
        System.out.println(movementSelection.getMovementType());
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByMovementType(?)");
            cs.clearParameters();
            cs.setInt(1, movementSelection.getMovementType());
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByHPGreater(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByHPGreater(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByHPEqual(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByHPEqual(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByHPLess(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByHPLess(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByATKGreater(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByATKGreater(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByATKEqual(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByATKEqual(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByATKLess(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByATKLess(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogBySPDGreater(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogBySPDGreater(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogBySPDEqual(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogBySPDEqual(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogBySPDLess(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogBySPDLess(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByDEFGreater(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByDEFGreater(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByDEFEqual(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByDEFEqual(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByDEFLess(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByATKLess(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void viewHeroCatalogByRESGreater(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByRESGreater(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByRESEqual(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByRESEqual(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewHeroCatalogByRESLess(int heroCatalogStatNumber)
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayHeroCatalogByRESLess(?)");
            cs.clearParameters();
            cs.setInt(1,heroCatalogStatNumber);
            ResultSet rs = cs.executeQuery();
            printHeroCatalogResults(rs);
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewAssistSkills()
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayAssists()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-22s%-22s\n", "Assist Name", "Description");
            while (rs.next())
            {
                System.out.printf("%-22s%-22s\n", rs.getString("assist_name"), rs.getString("assist_description"));
            }
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewSpecialSkills()
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplaySpecials()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-22s%-15s%-22s\n",  "Special Name", "Cool Down", "Description");
            while(rs.next())
            {
                System.out.printf("%-22s%-15s%-22s\n", rs.getString("special_name"), rs.getString("cooldown"), rs.getString("special_description"));
            }
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewSlotASkills()
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayASkills()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-22s%-15s%-15s%-15s%-15s%-15s\n", "Skill Name", "HP Modifier","ATK Modifier", "SPD Modifier", "DEF Modifier", "RES Modifier");
            while(rs.next())
            {
                System.out.printf("%-22s%-15s%-15s%-15s%-15s%-15s\n",  rs.getString("slotA_name"),rs.getString("hp_modifier"), rs.getString("atk_modifier"), rs.getString("spd_modifier"), rs.getString("def_modifier"), rs.getString("res_modifier"));
            }
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewSlotBSkills()
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayBSkills()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-22s%-22s\n", "#", "Skill Name", "Description");
            while(rs.next())
            {
                System.out.printf("%-22s%-22s\n", rs.getString("slotB_name"), rs.getString("slotB_description"));
            }
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewSlotCSkills()
    {
        try
        {
            CallableStatement cs = con.prepareCall("CALL DisplayCSkills()");
            ResultSet rs = cs.executeQuery();
            System.out.printf("%-22s%-22s\n", "Skill Name", "Description");
            while(rs.next())
            {
                System.out.printf("%-22s%-22s\n",rs.getString("slotC_name"),rs.getString("slotC_description"));
            }
            rs = cs.executeQuery();
            dbf.saveToCSV(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    
    private void printHeroCatalogResults(ResultSet rs)
    {
        try
        {
            System.out.printf("%-15s%-15s%-15s%-5s%-5s%-5s%-5s%-5s\n", "Hero Name", "Weapon Type", "Movement Type", "HP", "ATK", "SPD", "DEF", "RES");
            
            while (rs.next())
            {
                System.out.printf("%-15s%-15s%-15s%-5s%-5s%-5s%-5s%-5s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    private void printWeaponCatalogResults(ResultSet rs)
    {
        try
        {
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
