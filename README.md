## Project Structure
### Model structure
#### Boss is used as an guideline / concept - not developed as of time of writing
```mermaid
classDiagram
direction TB
    class Combatant {
        <<interface>>
        +attack(Combatant)
        +defend()
        +offensiveAbility(Combatant)
        +defensiveAbility(Combatant)
        +isAlive() bool
        +rollDice(int, int) int
        +receiveDamage(double)
        +receiveHeal(double)
        +applyDoTEffect(int, int)
        +handleDoTEffect()
        +applyHoTEffect(int, int)
        +handleHoTEffect()
        +getName() String
    }

    class Character {
        <<abstract>>
        -maxHealth : int
        -name : String
        -health : int
        -defenseModifier : double
        -dotDamage : int
        -dotTicks : int
        -hotHealing : int
        -hotTicks : int
        +Character(String, int)
        +isAlive() bool
        +receiveDamage(double)
        +applyDoTEffect(int, int)
        +handleDoTEffect()
        +applyHoTEffect(int, int)
        +handleHoTEffect()
        +receiveDamage(int)
        +talkSmack() String
        +getNameOptions() String[]
    }

    class Boss {
        <<abstract>>
        -intimidationFactor : double
        -specialAttackPower : double
        -uniqueAbility : String
        +Boss(String, int)
        +performSpecialAttack(Combatant)
        +invokeUniqueAbility()
    }

    class Necromancer {
        -mana : int
        -souls : int
        +Necromancer(String, int, int)
        +attack(Combatant)
        +defend()
        +offensiveAbility(Combatant)
        +defensiveAbility(Combatant)
        +receiveDamage(int)
        +receiveHeal(double)
        +isAlive() bool
        +talkSmack() String
    }
        
    class Shaman {
        -regeneration : int
        +Shaman(String, int, int)
        +attack(Combatant)
        +defend()
        +offensiveAbility(Combatant)
        +defensiveAbility(Combatant)
        +receiveDamage(int)
        +receiveHeal(double)
        +talkSmack() String
        +goneBerserk() bool
    }
        
    class Warrior {
        -strength : int
        +Warrior(String, int, int)
        +attack(Combatant)
        +defend()
        +offensiveAbility(Combatant)
        +defensiveAbility(Combatant)
        +receiveDamage(int)
        +receiveHeal(double)
        +talkSmack() String
    }

    class BossOne {
        +BossOne(String, int, int)
    }

Combatant <|.. Character : implements 
Combatant <|.. Boss : implements (conceptually)

Character <|-- Necromancer : extends
Character <|-- Shaman : extends
Character <|-- Warrior : extends

Boss <|-- BossOne : extends (conceptually)
```

### View Structure
```mermaid
classDiagram
direction LR
    class Animations {
        +animateLine(int, String, int)
        +animateText(String, int)
    }

    class BattlePrompt {
        +versusIntroduction(Character)
        +displayAttack(Combatant, double, String)
        +actionPrompt(Character, Character)
    }

    class ConclusionDisplay {
        +fightConclusion(Character, Character)
    }

    class HealthDisplay {
        -getHealthColor(int, int) String
        +displayHealthBar(int, int)
        +healthStatus(Character, Character)
    }

    class MenuDisplay {
        +displayGameChoices()
    }

    class SettingsMenu {
    }
    
    Animations ..> Utils : uses >>
    BattlePrompt ..> Animations : uses >>
    ConclusionDisplay ..> Animations : uses >>
    HealthDisplay ..> Color : uses >>
    HealthDisplay ..> Utils : uses >>
    BattlePrompt ..> Color : uses >>
    ConclusionDisplay ..> Color : uses >>
```

### Example Flowchart: Combat Controller
```mermaid
flowchart LR
    start(Start takeTurn) --> alive{Check if activeCombatant is Alive}
    alive -->|Yes| handle-effects[Handle effect state, such as damage over time or crowd control effects]
    alive -->|No| end1(End Turn)
    handle-effects --> choice{Evaluate Choice}
    choice -->|Attack| attack[attack]
    choice -->|Defend| defend[defend]
    choice -->|Defensive Ability| defAbility[defensiveAbility]
    choice -->|Offensive Ability| offAbility[offensiveAbility]
    choice <-->|Invalid| invalid[Output: Invalid action. Please choose again.]
    attack --> end2(End Turn)
    defend --> end2
    defAbility --> end2
    offAbility --> end2
```

### Example Sequence Loop: Arena
```mermaid
sequenceDiagram
    participant Player as Player Character
    participant ArenaLoop
    participant BattlePrompt
    participant CombatController
    participant Animations
    participant HealthDisplay
    participant NPC as NPC Character

    ArenaLoop ->> Player: Instantiate
    ArenaLoop ->> NPC: Instantiate using getRandomEnemy()

    loop While both Player and NPC are alive
        ArenaLoop ->> BattlePrompt: actionPrompt(Player, NPC)
        BattlePrompt -->> Player: Display prompt
        Player -->> ArenaLoop: playerChoice
        ArenaLoop ->> CombatController: takeTurn(playerChoice, Player, NPC)
        CombatController -->> Player: Perform action
        CombatController -->> NPC: Apply effects of Player action
        ArenaLoop ->> Animations: sleep(1000ms)

        ArenaLoop ->> ArenaLoop: getNonPlayingCharacterChoice()
        ArenaLoop -->> NPC: npcChoice
        ArenaLoop ->> CombatController: takeTurn(npcChoice, NPC, Player)
        CombatController -->> NPC: Perform action
        CombatController -->> Player: Apply effects of NPC action
        ArenaLoop ->> Animations: sleep(1000ms)

        ArenaLoop ->> ArenaLoop: turnSummary(NPC, Player, roundNumber)
        ArenaLoop ->> Animations: animateLine(25, "🔄", 50)
        ArenaLoop ->> HealthDisplay: healthStatus(Player, NPC)
        HealthDisplay -->> Player: Display health
        HealthDisplay -->> NPC: Display health
        ArenaLoop ->> Animations: animateLine(25, "⎯", 100)
        ArenaLoop ->> Animations: sleep(500ms)
    end

```

### Utils Diagram
```mermaid
classDiagram
direction LR
    class Color {
        <<enum>>
        -RESET : String
        -RED : String
        -GREEN : String
        -YELLOW : String
        -BLUE : String
        +getCode() String
    }

    class GameConfig {
        <<Singleton>>
        -instance : GameConfig
        -soundEnabled : boolean
        -GameConfig()
        +getInstance() GameConfig
        +isSoundEnabled() boolean
        +setSoundEnabled(boolean)
        +toString() String
    }

    class Sounds {
        -config : GameConfig
        -soundMap : Map~String, String~
        -currentClip : Clip
        +playSound(String, boolean)
        +stopCurrentSound()
        +playOneShotSound(String)
        +isSoundEnabled() boolean
        +addSound(String, String)
    }

    class Utils {
        -randomlyGenerated : Random
        -getInput : Scanner
        +sleep(int)
        +getPlayerChoice() String
    }

    GameConfig --> Sounds : uses >>
    Color ..> Sounds : uses for output formatting
    Utils ..> Color : may use for output formatting
    Utils ..> GameConfig : may use for configuration settings
```