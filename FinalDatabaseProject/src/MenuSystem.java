import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MenuSystem
{
    public InputChecker ic = new InputChecker();
    public DBFunctions dbf = new DBFunctions();
    public void startMenu()
    {
        boolean quit = false;
        while (!quit)
        {
            System.out.println("Welcome to the Fire Emblem Heroes Database!");
            System.out.println("1: Create a Custom Hero"); //Create
            System.out.println("2: Saved Custom Heroes"); //View all saved heroes.
            System.out.println("3: Delete Custom Heroes"); //Delete
            System.out.println("4:"); //Query
            System.out.println("5: Hero Lookup By Weapon Type"); //Query with Filter?
            System.out.println("6:"); //Comparisons
            System.out.println("7: Quit"); //Quit
    
            System.out.print("Enter Menu Choice: ");
            int menuOptionChosen = ic.readInteger(7, 1);
    
            if (menuOptionChosen == 1)
            {
                addHero();
            }
            else if (menuOptionChosen == 2)
            {
                System.out.println("Select a saved hero to view info:");
                dbf.viewCustomHero();
            }
            else if (menuOptionChosen == 3)
            {
                dbf.deleteCustomHero();
            }
            else if (menuOptionChosen == 4)
            {
        
            } else if (menuOptionChosen == 5)
            {
                heroLookUp();
        
            }
            else if (menuOptionChosen == 6)
            {
            
            }
            else if (menuOptionChosen == 7)
            {
                quit = true;
            }
        }
    }

    private void updateMenu()
    {

    }

    private void addHero()
    {
        System.out.println("Select Character:");
        CustomHero hero = dbf.chooseHeroFromDB();
        System.out.println("Select Weapon:");
        hero = dbf.chooseWeaponFromDB(hero);
        System.out.println("Select Assist Skill:");
        hero = dbf.chooseAssistFromDB(hero);
        System.out.println("Select Special Skill");
        hero = dbf.chooseSpecialFromDB(hero);
        System.out.println("Select A Slot Skill:");
        hero = dbf.chooseASkillFromDB(hero);
        System.out.println("Select B Slot Skill:");
        hero = dbf.chooseBSkillFromDB(hero);
        System.out.println("Select C Slot Skill:");
        hero = dbf.chooseCSkillFromDB(hero);
        dbf.saveCustomHero(hero);

        System.out.println("Your hero has been saved in the database.");
    }

    private void heroLookUp()
    {
        dbf.displayHeroes();
    }
}
