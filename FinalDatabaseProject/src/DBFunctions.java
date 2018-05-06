import com.mysql.fabric.xmlrpc.base.Value;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFunctions
{
    private static Connection con;
    
    public DBFunctions()
    {
        try
        {
            con = DBConfig.GetConnection();
            if(con.isClosed())
            {
                System.out.println("Connection was closed; creating new connection to MySQL.");
                con = DBConfig.GetConnection();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public InputChecker ic = new InputChecker();
    
    public CustomHero chooseHeroFromDB()
    {
        CustomHero hero = new CustomHero();
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT hero_ID, hero_name FROM herocatalog");
            ResultSet rs = pst.executeQuery();
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
                System.out.printf("%-5s%-22s\n", rs.getString("hero_ID") + ".", rs.getString("hero_name"));
            }
            int selection = ic.readInteger(max, min);
            pst = con.prepareStatement("SELECT * FROM herocatalog WHERE hero_ID = ?");
            pst.clearParameters();
            pst.setInt(1, selection);
            rs = pst.executeQuery();
            
            while(rs.next())
            {
                hero.setHeroID(rs.getInt("hero_ID"));
                hero.setHeroName(rs.getString("hero_name"));
                hero.setWeaponType(rs.getInt("weapon_type"));
                hero.setATK(rs.getInt("HP"));
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
    
    public CustomHero chooseWeaponFromDB(CustomHero heroStats)
    {
        CustomHero hero = heroStats;
        try
        {
            PreparedStatement pst = con.prepareStatement("SELECT weapon_ID, weapon_name FROM weapons WHERE weapon_Type = ?");
            pst.clearParameters();
            pst.setInt(1,hero.getWeaponType());
            ResultSet rs = pst.executeQuery();
            
            
            System.out.printf("%-5s%-22s\n", "#", "Weapon Name");
            
            while(rs.next())
            {
                System.out.printf("%-5s%-22s\n", rs.getString("weapon_ID") + ".", rs.getString("weapon_Name"));
            }
            
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    
    
        return hero;
    }
}
