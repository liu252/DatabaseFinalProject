create procedure DisplayASkills ()
SELECT slotA_ID, slotA_name FROM slota_skills;

create procedure DisplayAssists ()
SELECT assist_ID, assist_name FROM assists;

create procedure DisplayBSkills ()
SELECT slotB_ID, slotB_name FROM slotb_skills;

create procedure DisplayCSkills ()
SELECT slotC_ID, slotC_name FROM slotc_skills;

create procedure DisplayCustomHeroes ()
SELECT hero_ID, hero_name FROM customheroes;

create procedure DisplaySpecials ()
SELECT special_ID, special_name FROM specials;

create procedure DisplayWeaponByType (IN id int)
SELECT weapon_ID, weapon_name FROM weapons WHERE weapon_type = id;

create procedure SaveCustomHero (IN heroname varchar(100), IN weaponID int, IN assistID int, IN specialID int, IN newHP int, IN newATK int, IN newSPD int, IN newDEF int, IN newRES int, IN slotA_ID int, IN slotB_ID int, IN slotC_ID int)
INSERT INTO customheroes(hero_name, weapon, assist_skill, special_skill, HP, ATK, SPD, DEF, RES, slotA_skill, slotB_skill, slotC_skill)
      VALUES(heroname, weaponID, assistID, specialID, newHP, newATK, newSPD, newDEF, newRES, slotA_ID, slotB_ID, slotC_ID);

create procedure SelectASkill (IN ID int)
SELECT * FROM slota_skills WHERE slotA_ID = ID;

create procedure SelectCustomHero (IN ID int)
SELECT * FROM customheroes WHERE hero_ID = ID;

create procedure SelectHero (IN id int)
SELECT * FROM herocatalog WHERE hero_ID = id;

create procedure SelectWeapon (IN id int)
SELECT * FROM weapons WHERE weapon_ID = id;

create procedure ShowHeroRoster ()
SELECT hero_ID, hero_name FROM herocatalog;

