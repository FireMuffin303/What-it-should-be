![What it should be](https://imgur.com/jnnVrYd.png)
---

## About
A configurative Minecraft mod which focus on changing some vanilla features and gameplay to what it should be. (From Mod dev's perspective atleast...) Without adding any blocks, entities or items. 

---
## Features

**<p align="center" style="font-size:32px">--- Compass bar ---</p>**

<p align="center"> <img src="https://imgur.com/xqwOUfP.png" alt="Compass Bar informs directions and coordinates"> </p>

<p align="center"> 
  Holding Compass informs direction of player facing and player's current coordinates <b>without F3</b> 
</p> 
<p align="center">
  (include Recovery Compass)
</p>
<p align="center">
  Compass bar can read map in hands and show map's marker icon on the bar as well. but off by default due to keeping map reading gameplay.

```
Gamerule : showCompassGUI (On by default)
Gamerule : showTreasureInCompassGUI (Off by default)
```

```
Mod config contains precisive coordinates setting.
```

</p>

**<p align="center">--- Clock bar ---</p>**

<p align="center"> <img src="https://imgur.com/2PcrANT.png" alt="Clock bar informs in-game time and days"> </p>

<p align="center"> Holding Clock informs in-game time and days </p>

```
Gamerule : showClockGUI (On by default)
```

```
Mod config contains time format setting.
```

<p align="center">...</p>
<b><p align="center">(compatable with <a href="https://modrinth.com/mod/trinkets">Trinkets</a>)</p></b>

---

**<p align="center">--- Renameable Nametag ---</p>**
<p align="center"> 
  <img width="75%" src="https://imgur.com/ThZvSz6.png" alt="Compass Bar informs directions and coordinates"> </p>

<p align="center">
  Sneak right-click on Nametag to rename it without anvil. But still need enchantment cost to make anvil renaming method not completely pointless. It just you could rename it anywhere.
</p>

<b><p align="center">
  Enchantment cost can be adjust or disable by gamerule (renameNameTagCost)
</p></b>

---

<b><p align="center">--- More details Tooltips ---</p></b>
<p align="center"><img src="https://imgur.com/oN4jlyD.gif" alt="More items has detailed tooltips such Bee Nest or Bee Hive show how many bees and honey its contains or checking Axolotl in Bucket details"></p>

```
Can be toggle by mod config.
```

---

<b><p align="center">--- Bonemealable Crops ---</p></b>

- Bonemealable Sugar Cane
```
Gamerule : bonemealableSugarCane (On by default)
Gamerule : sugarCaneGrowHeight (Adjust sugar cane grow height, 3 by default)
```
- Bonemealable Netherwarts
```
Gamerule : bonemealableNetherwart (Off by default)
```
- Bonemealable Lily Pad
```
Gamerule : bonemealableLilyPad (Off by default)
```
---

<b><p align="center">--- Mob Changes ---</p></b>
- Adjusted Baby zombie hitbox to be larger and decrease max health from 20 to 14
  - Gamerule : easierBabyZombie (On by default)

<p align="center"> <img width="50%" src="https://imgur.com/XeY64q9.png" alt="Comparing Vanilla's Baby zombie hitbox to Mod's Baby zombie hitbox"> </p>

- Creeper Explosion Destroy block gamerules
```
Gamerule : creeperExplosionDestroyBlock (Off to stop Creeper explosion to destroy blocks, On by default)
```

- Turtle Barnacle
<p align="center"> 
  <img width="50%" src="https://imgur.com/yG0CPu2.gif" alt="Turtle has barnacle on their back. Use brush on them to clean and get turtle scute"> 
</p>

<p align="center">
  Turtle will get barnacle on their back after swim in water for 15-22 minutes, then player can use brush on them to clean it off. Turtle Scute drops when brushing.
</p>

<p align="center">
  Honeycomb can be use on turtle to stop getting barnacles.
</p>

```
Gamerule : turtleHasBarnacle (Toggle turtle barnacle, ON by default)
Gamerule : turtleBarnacleMinTime (Minimum time to turtle to get barnacle while swimming, 18000 ticks (15 minutes) by default)
Gamerule : turtleBarnacleMaxTime (Maximum time to turtle to get barnacle while swimming, 26400 ticks (22 minutes) by default)
```

- Armor Stand has arms
<p align="center">
  <img width="50%" src="https://imgur.com/eG9KZ8O.gif" alt="Placing Armor Stand will come with arms now.">
</p>
<p align="center">
  Placing Armor Stand will come with arms now.
</p>

```
Gamerule : spawnArmorStandWithArm (On by default)
```

- Shearable Item Frame
<p align="center">
  <img width="50%" src="https://imgur.com/0SIp9pA.gif" alt="Use shear on item-attached item frame to make item frame invisible">
</p>
<p align="center">
  Use shear on item-attached item frame to make item frame invisible
</p>

```
Gamerule : shearAbleItemFrame (On by default)
```
---
<b><p align="center">--- Recipe & Minor Chages ---</p></b>
<div align="center">
<b>Lodestone</b><br><br>
    <img width="50%" src="https://imgur.com/ZyoTL0t.png" alt="Lodestone Recipe change to Compass surrounding by chiseled stone bricks">
</div>

<br>

<div align="center">
<b>Composing</b><br><br>
Rotten Flesh & Poisonous Potato can be composed.
</div>

<br>

<div align="center">
<b>Cauldron Interaction</b><br><br>
Concrete Powder can be use on cauldron to turn an entire stack to Concrete block.
</div>

---

## Wiki (Coming soon.)
Official Wiki page will contains developer notes which contains reason and decision to feature changes.

---

## FAQ
- <b>Can I put this mod in modpack?</b> <br>
Feel free to put in any modpack. I would love to see the modpack too. <3
 
- <b>Will this mod port to 1.XX.X version?</b> <br>
When the time comes. Please wait.
  
- <b>Will this mod gonna get backport?</b> <br>
NO
  
- <b>Will this mod available on Forge/Neoforge?</b> <br>
Neoforge supports will be available once this mod update to 1.21. <br>
No Forge support in any circumstance.

---
## Donations
<div display="grid">
  <a href="https://ko-fi.com/J3J0D3YOD">
    <img src="https://ko-fi.com/img/githubbutton_sm.svg" alt="Kofi">
  </a>
  
  <a href="https://tipme.in.th/firemuffin303" >
    <img width="230px" src="https://imgur.com/uDYMDHd.png" alt="Tipme">
  </a>
</div>
