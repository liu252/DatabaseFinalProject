public class MenuSystem
{
    private InputChecker ic = new InputChecker();
    private DBFunctions dbf = new DBFunctions();
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
            System.out.println("7: Quit"); //Quit
    
            System.out.print("Enter Menu Choice: ");
            int menuOptionChosen = ic.readInteger(7, 1);
    
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
                quit = true;
            }
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
    
    private void viewCustomHeroes()
    {
        System.out.println("1. Display All Custom Heroes");
        System.out.println("2. Display Specific Custom Hero");
        System.out.println("3. Return to Main Menu");
        int customHeroMenuSelection = ic.readInteger(3,1);
        if(customHeroMenuSelection == 1)
        {
            dbf.viewAllCustomHeroes();
        }
        if(customHeroMenuSelection == 2)
        {
            System.out.println("Select a saved hero to view info:");
            int viewSelection = dbf.displayAllCustomHeroesForSelection();
            dbf.viewCustomHero(viewSelection);
        }
    }
    
    private void updateCustomHero()
    {
        System.out.println("Select Character to Update: ");
        int updateSelection = dbf.displayAllCustomHeroesForSelection();
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
            
        }
        else if (selection == 5)
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
    
    private void deleteCustomHero()
    {
        int deleteSelection = dbf.displayAllCustomHeroesForSelection();
        dbf.deleteCustomHero(deleteSelection);
    }
    
    private void displayHeroCatalog()
    {
        System.out.println("1. Display Entire Hero Catalog");
        System.out.println("2. Display Hero Catalog by Weapon Type");
        System.out.println("3. Display Hero Catalog by Movement Type");
        System.out.println("4. Display Hero Catalog by Stats");
        System.out.println("5. Return to Main Menu");
        int heroCatalogMenuSelection = ic.readInteger(5,1);
        if (heroCatalogMenuSelection == 1)
        {
            dbf.viewHeroCatalog();
        }
        else if (heroCatalogMenuSelection == 2)
        {
            CustomHero weaponSelection = dbf.displayWeaponTypes();
            dbf.viewHeroCatalogByWeaponType(weaponSelection);
            
        }
        else if (heroCatalogMenuSelection == 3)
        {
            CustomHero movementSelection = dbf.displayMovementTypes();
            dbf.viewHeroCatalogByMovementType(movementSelection);
        }
        else if (heroCatalogMenuSelection == 4)
        {
            System.out.println("Please choose stat to compare with:");
            System.out.println("1. HP");
            System.out.println("2. ATK");
            System.out.println("3. SPD");
            System.out.println("4. DEF");
            System.out.println("5. RES");
            int heroCatalogStatSelection = ic.readInteger(5,1);
            System.out.println("Please choose an operator: "); //what is hits called
            System.out.println("1. Greater Than >");
            System.out.println("2. Less Than <");
            System.out.println("3. Equals To =");
            int heroCatalogSignSelection = ic.readInteger(3,1);
            System.out.print("Please enter a number to compare by: ");
            int heroCatalogStatNumber = ic.readInteger(0,0);
            if (heroCatalogStatSelection == 1)
            {
                if(heroCatalogSignSelection == 1)
                {
                    dbf.viewHeroCatalogByHPGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    dbf.viewHeroCatalogByHPLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    dbf.viewHeroCatalogByHPEqual(heroCatalogStatNumber);
                }
            }
            else if (heroCatalogStatSelection == 2)
            {
                if(heroCatalogSignSelection == 1)
                {
                    dbf.viewHeroCatalogByATKGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    dbf.viewHeroCatalogByATKLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    dbf.viewHeroCatalogByATKEqual(heroCatalogStatNumber);
                }
            }
            else if (heroCatalogStatSelection == 3)
            {
                if(heroCatalogSignSelection == 1)
                {
                    dbf.viewHeroCatalogBySPDGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    dbf.viewHeroCatalogBySPDLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    dbf.viewHeroCatalogBySPDEqual(heroCatalogStatNumber);
                }
            }
            else if (heroCatalogStatSelection == 4)
            {
                if(heroCatalogSignSelection == 1)
                {
                    dbf.viewHeroCatalogByDEFGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    dbf.viewHeroCatalogByDEFLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    dbf.viewHeroCatalogByDEFEqual(heroCatalogStatNumber);
                }
            }
            else if (heroCatalogStatSelection == 5)
            {
                if(heroCatalogSignSelection == 1)
                {
                    dbf.viewHeroCatalogByRESGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    dbf.viewHeroCatalogByRESLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    dbf.viewHeroCatalogByRESEqual(heroCatalogStatNumber);
                }
            }
        }
        
    }
    
    private void displayWeaponCatalog()
    {
        System.out.println("1. Display Entire Weapons Catalog");
        System.out.println("2. Display Weapons by Type");
        System.out.println("3. Display Strongest Weapons in Weapon Catalog");
        System.out.println("4. Display Strongest Weapons by Weapon Strength");
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
            dbf.viewStrongestWeaponInWeaponsCatalog();
        }
        else if (weaponsCatalogMenuSelection == 4)
        {
            System.out.print("Please enter the minimum strength required for weapon: ");
            int strength = ic.readInteger(0,0);
            dbf.viewStrongestWeaponInWeaponsCatalogByStrength(strength);
        }
    }
    
    
    
    


}
