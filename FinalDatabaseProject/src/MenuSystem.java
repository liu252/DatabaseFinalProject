public class MenuSystem
{
    private InputChecker ic = new InputChecker();
    private CustomHeroModifier cmh = new CustomHeroModifier();
    private CatalogViewer cv = new CatalogViewer();
    public void startMenu()
    {
        boolean quit = false;
        while (!quit)
        {
            System.out.println("_______________________________________________________________________________________");
            System.out.println("Welcome to the Fire Emblem Heroes Database!");
            System.out.println("1: Create a Custom Hero"); //Create
            System.out.println("2: View Custom Heroes"); //View all saved heroes.
            System.out.println("3: Update Custom Heroes"); //Delete
            System.out.println("4: Delete Custom Heroes"); //Update
            System.out.println("5: Display Hero Catalog"); //Query with Filter?
            System.out.println("6: Display Weapon Catalog");
            System.out.println("7: Display Skills Catalog");
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
                displaySkillCatalog();
            }
            else if (menuOptionChosen == 8)
            {
                System.out.println("Thank you for using the Fire Emblem Heroes Database! We hope you have a good day!");
                quit = true;
            }
        }
    }
    
    
    private void addCustomHero()
    {
        System.out.println("_______________________________________________________________________________________");
        System.out.println("Create Custom Hero Menu: Please choose the specific specifications for your hero!");
        System.out.println("Select Character:");
        CustomHero hero = cmh.chooseHeroFromDB();
        System.out.println("Select Weapon:");
        hero = cmh.chooseWeaponFromDB(hero);
        System.out.println("Select Assist Skill:");
        hero = cmh.chooseAssistFromDB(hero);
        System.out.println("Select Special Skill");
        hero = cmh.chooseSpecialFromDB(hero);
        System.out.println("Select A Slot Skill:");
        hero = cmh.chooseASkillFromDB(hero);
        System.out.println("Select B Slot Skill:");
        hero = cmh.chooseBSkillFromDB(hero);
        System.out.println("Select C Slot Skill:");
        hero = cmh.chooseCSkillFromDB(hero);
        cmh.saveCustomHero(hero);
        
        System.out.println("Your hero has been saved in the database.");
    }
    
    private void viewCustomHeroes()
    {
        System.out.println("_______________________________________________________________________________________");
        System.out.println("View Custom Heroes Menu: Please choose an option from the menu below!");
        System.out.println("1. Display All Custom Heroes");
        System.out.println("2. Display Specific Custom Hero");
        System.out.println("3. Return to Main Menu");
        System.out.println("Enter Menu Choice: ");
        int customHeroMenuSelection = ic.readInteger(3,1);
        if(customHeroMenuSelection == 1)
        {
            cmh.viewAllCustomHeroes();
        }
        if(customHeroMenuSelection == 2)
        {
            System.out.println("Select a saved hero to view info:");
            int viewSelection = cmh.displayAllCustomHeroesForSelection();
            if (viewSelection != -1)
            {
                cmh.viewCustomHero(viewSelection);
            }
        }
        
    }
    
    private void updateCustomHero()
    {
        System.out.println("_______________________________________________________________________________________");
        System.out.println("Update Custom Heroes Menu: Please Select a Character to Update!");
        int updateSelection = cmh.displayAllCustomHeroesForSelection();
        if (updateSelection != -1)
        {
            CustomHero hero = cmh.findCustomHeroForUpdate(updateSelection);
            System.out.println("What attribute would you like to update?");
            System.out.println("1. Change Weapon");
            System.out.println("2. Change Assist Skill");
            System.out.println("3. Change Special Skill");
            System.out.println("4. Change A Slot Skill");
            System.out.println("5. Change B Slot Skill");
            System.out.println("6. Change C Slot Skill");
            System.out.println("7. Do Nothing");
            int selection = ic.readInteger(7, 1);
            boolean cancelUpdate = false;
            if (selection == 1)
            {
                hero = cmh.adjustWeaponModifierStats(hero);
                hero = cmh.chooseWeaponFromDB(hero);
            } else if (selection == 2)
            {
                hero = cmh.chooseAssistFromDB(hero);
            } else if (selection == 3)
            {
                hero = cmh.chooseSpecialFromDB(hero);
        
            } else if (selection == 4)
            {
                hero = cmh.adjustASlotSkillStats(hero);
                hero = cmh.chooseASkillFromDB(hero);
        
            } else if (selection == 5)
            {
                hero = cmh.chooseBSkillFromDB(hero);
        
            } else if (selection == 6)
            {
                hero = cmh.chooseCSkillFromDB(hero);
            } else if (selection == 7)
            {
                cancelUpdate = true;
            }
            if (!cancelUpdate)
            {
                cmh.updateCustomHero(hero);
                System.out.println("Hero Update Successful!");
            } else
            {
                System.out.println("Hero Update Canceled");
            }
        }
    }
    
    private void deleteCustomHero()
    {
        System.out.println("_______________________________________________________________________________________");
        System.out.println("Delete Custom Hero Menu: Please choose a hero from below to delete!");
        int deleteSelection = cmh.displayAllCustomHeroesForSelection();
        if(deleteSelection != -1)
        {
            cmh.deleteCustomHero(deleteSelection);
        }
    }
    
    private void displayHeroCatalog()
    {
        System.out.println("_______________________________________________________________________________________");
        System.out.println("Display Hero Catalog Menu: Please choose an option from the menu below!");
        System.out.println("1. Display Entire Hero Catalog");
        System.out.println("2. Display Hero Catalog by Weapon Type");
        System.out.println("3. Display Hero Catalog by Movement Type");
        System.out.println("4. Display Hero Catalog by Stats");
        System.out.println("5. Return to Main Menu");
        int heroCatalogMenuSelection = ic.readInteger(5,1);
        if (heroCatalogMenuSelection == 1)
        {
            cv.viewHeroCatalog();
        }
        else if (heroCatalogMenuSelection == 2)
        {
            CustomHero weaponSelection = cv.displayWeaponTypes();
            cv.viewHeroCatalogByWeaponType(weaponSelection);
            
        }
        else if (heroCatalogMenuSelection == 3)
        {
            CustomHero movementSelection = cv.displayMovementTypes();
            cv.viewHeroCatalogByMovementType(movementSelection);
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
                    cv.viewHeroCatalogByHPGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    cv.viewHeroCatalogByHPLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    cv.viewHeroCatalogByHPEqual(heroCatalogStatNumber);
                }
            }
            else if (heroCatalogStatSelection == 2)
            {
                if(heroCatalogSignSelection == 1)
                {
                    cv.viewHeroCatalogByATKGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    cv.viewHeroCatalogByATKLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    cv.viewHeroCatalogByATKEqual(heroCatalogStatNumber);
                }
            }
            else if (heroCatalogStatSelection == 3)
            {
                if(heroCatalogSignSelection == 1)
                {
                    cv.viewHeroCatalogBySPDGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    cv.viewHeroCatalogBySPDLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    cv.viewHeroCatalogBySPDEqual(heroCatalogStatNumber);
                }
            }
            else if (heroCatalogStatSelection == 4)
            {
                if(heroCatalogSignSelection == 1)
                {
                    cv.viewHeroCatalogByDEFGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    cv.viewHeroCatalogByDEFLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    cv.viewHeroCatalogByDEFEqual(heroCatalogStatNumber);
                }
            }
            else if (heroCatalogStatSelection == 5)
            {
                if(heroCatalogSignSelection == 1)
                {
                    cv.viewHeroCatalogByRESGreater(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 2)
                {
                    cv.viewHeroCatalogByRESLess(heroCatalogStatNumber);
                }
                else if (heroCatalogSignSelection == 3)
                {
                    cv.viewHeroCatalogByRESEqual(heroCatalogStatNumber);
                }
            }
        }
        
    }
    
    private void displayWeaponCatalog()
    {
        System.out.println("_______________________________________________________________________________________");
        System.out.println("Display Weapons Catalog Menu: Please choose an option from the menu below!");
        System.out.println("1. Display Entire Weapons Catalog");
        System.out.println("2. Display Weapons by Type");
        System.out.println("3. Display Strongest Weapons in Weapon Catalog");
        System.out.println("4. Display Strongest Weapons by Weapon Strength");
        System.out.println("5. Return to Main Menu");
        int weaponsCatalogMenuSelection = ic.readInteger(5,1);
        if (weaponsCatalogMenuSelection == 1)
        {
            cv.viewEntireWeaponsCatalog();
        }
        else if (weaponsCatalogMenuSelection == 2)
        {
            CustomHero weaponTypeSelection = cv.displayWeaponTypes();
            cv.viewWeaponsCatalogByType(weaponTypeSelection);
        }
        else if (weaponsCatalogMenuSelection == 3)
        {
            cv.viewStrongestWeaponInWeaponsCatalog();
        }
        else if (weaponsCatalogMenuSelection == 4)
        {
            System.out.print("Please enter the minimum strength required for weapon: ");
            int strength = ic.readInteger(0,0);
            cv.viewStrongestWeaponInWeaponsCatalogByStrength(strength);
        }
    }
    
    private void displaySkillCatalog()
    {
        System.out.println("_______________________________________________________________________________________");
        System.out.println("Display Skill Catalog Menu: Please choose an option from the menu below!");
        System.out.println("1. Display Assist Skills");
        System.out.println("2. Display Special Skills");
        System.out.println("3. Display Slot A Skills");
        System.out.println("4. Display Slot B Skills");
        System.out.println("5. Display Slot C Skills");
        System.out.println("6. Return to Main Menu");
        int skillsCatalogMenuSelection = ic.readInteger(6,1);
        if(skillsCatalogMenuSelection == 1)
        {
            cv.viewAssistSkills();
        }
        else if (skillsCatalogMenuSelection == 2)
        {
            cv.viewSpecialSkills();
        }
        else if (skillsCatalogMenuSelection == 3)
        {
            cv.viewSlotASkills();
        }
        else if (skillsCatalogMenuSelection == 4)
        {
            cv.viewSlotBSkills();
        }
        else if (skillsCatalogMenuSelection == 5)
        {
            cv.viewSlotCSkills();
        }
    }
    
}
