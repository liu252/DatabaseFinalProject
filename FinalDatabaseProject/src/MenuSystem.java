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
            System.out.println("2: View Custom Heroes"); //View all saved heroes.
            System.out.println("3: Update Custom Heroes"); //Delete
            System.out.println("4: Delete Custom Heroes"); //Update
            System.out.println("5: Display Hero Catalog"); //Query with Filter?
            System.out.println("6: Display Weapon Catalog");
            System.out.println("7: Hero Lookup by Weapon Type"); //Comparisons
            System.out.println("8: Quit"); //Quit
    
            System.out.print("Enter Menu Choice: ");
            int menuOptionChosen = ic.readInteger(8, 1);
    
            if (menuOptionChosen == 1)
            {
                addCustomHero();
            }
            else if (menuOptionChosen == 2)
            {
                viewCustomHeroes();
            }
            else if (menuOptionChosen == 3)
            {
                updateCustomHero();
                
            }
            else if (menuOptionChosen == 4)
            {
                deleteCustomHero();
        
            }
            else if (menuOptionChosen == 5)
            {
                displayHeroCatalog();
        
            }
            else if (menuOptionChosen == 6)
            {
                displayWeaponCatalog();
            }
            else if (menuOptionChosen == 7)
            {
                heroLookUpByWeaponType();
            }
            else if (menuOptionChosen == 8)
            {
                quit = true;
            }
        }
    }
    
    private void displayWeaponCatalog()
    {
        System.out.println("1. Display Entire Weapons Catalog");
        System.out.println("2. Display Weapons by Type");
        System.out.println("3. Display Strongest Weapons");
        System.out.println("4. Display Strongest Weapons by Type");
        System.out.println("5. Return to Main Menu");
        int weaponsCatalogMenuSelection = ic.readInteger(5,1);
        if (weaponsCatalogMenuSelection == 1)
        {
            dbf.viewEntireWeaponsCatalog();
        }
        else if (weaponsCatalogMenuSelection == 2)
        {
            CustomHero weaponTypeSelection = dbf.displayWeaponTypes();
            dbf.viewWeaponsCatalogByType(weaponTypeSelection);
        }
        else if (weaponsCatalogMenuSelection == 3)
        {
    
        }
        else if (weaponsCatalogMenuSelection == 4)
        {
    
        }
    }
    
    private void displayHeroCatalog()
    {
        dbf.viewHeroCatalog();
    }
    
    private void viewCustomHeroes()
    {
        System.out.println("Select a saved hero to view info:");
        int viewSelection = dbf.displayAllCustomHeroes();
        dbf.viewCustomHero(viewSelection);
    }
    
    private void deleteCustomHero()
    {
        int deleteSelection = dbf.displayAllCustomHeroes();
        dbf.deleteCustomHero(deleteSelection);
    }
    
    private void updateCustomHero()
    {
        System.out.println("Select Character to Update: ");
        int updateSelection = dbf.displayAllCustomHeroes();
        CustomHero hero = dbf.findCustomHeroForUpdate(updateSelection);
        System.out.println("What attribute would you like to update?");
        System.out.println("1. Change Weapon");
        System.out.println("2. Change Assist Skill");
        System.out.println("3. Change Special Skill");
        System.out.println("4. Change A Slot Skill");
        System.out.println("5. Change B Slot Skill");
        System.out.println("6. Change C Slot Skill");
        System.out.println("7. Do Nothing");
        int selection = ic.readInteger(7,1);
        boolean cancelUpdate = false;
        if (selection == 1)
        {
            hero = dbf.chooseWeaponFromDB(hero);
        }
        else if (selection == 2)
        {
            hero = dbf.chooseAssistFromDB(hero);
        }
        else if (selection == 3)
        {
            hero = dbf.chooseSpecialFromDB(hero);
        
        }
        else if (selection == 4)
        {
            hero = dbf.chooseASkillFromDB(hero);
        
        } else if (selection == 5)
        {
            hero = dbf.chooseBSkillFromDB(hero);
        
        }
        else if (selection == 6)
        {
            hero = dbf.chooseCSkillFromDB(hero);
        }
        else if (selection == 7)
        {
            cancelUpdate = true;
        }
        
        if (!cancelUpdate)
        {
            dbf.updateCustomHero(hero);
            System.out.println("Hero Update Successful!");
        }
        else
        {
            System.out.println("Hero Update Canceled");
        }
        
    }

    private void addCustomHero()
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

    private void heroLookUpByWeaponType()
    {
        dbf.displayHeroesByWeaponType();
        
    }
}
