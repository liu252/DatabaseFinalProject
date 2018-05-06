import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MenuSystem
{
    public InputChecker ic = new InputChecker();
    public DBFunctions dbf = new DBFunctions();
    public void startMenu()
    {
        System.out.println("Welcome to the Fire Emblem Heroes Database!");
        System.out.println("1. Create a Custom Hero"); //Create
        System.out.println("2. Update Custom Hero"); //Update
        System.out.println("3. Delete Custom hero"); //Delete
        System.out.println("4."); //Query
        System.out.println("5."); //Query with Filter?
        System.out.println("6."); //Comparisons
        System.out.println("7."); //Quit
        
        System.out.print("Please Enter Menu Option: ");
        int menuOptionChosen = ic.readInteger(7, 1);
        if (menuOptionChosen == 1)
        {
            addMenu();
        }
        else if (menuOptionChosen == 2)
        {
            updateMenu();
        }
        else if (menuOptionChosen == 3)
        {
            deleteMenu();
        }
        else if (menuOptionChosen == 4)
        {
        
        }
        else if (menuOptionChosen == 5)
        {
        
        }
        else if (menuOptionChosen == 6)
        {
        
        }
        
        
        
    }
    
    private void deleteMenu()
    {
    
    }
    
    private void updateMenu()
    {
    
    }
    
    private void addMenu()
    {
        System.out.println("Please Select Character");
        CustomHero hero = dbf.chooseHeroFromDB();
        System.out.println("Please Select Weapon");
        hero = dbf.chooseWeaponFromDB(hero);
        System.out.println("Please Select Special Skill");
        
        
    }
    
}
