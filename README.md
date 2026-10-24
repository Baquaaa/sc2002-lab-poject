# Restaurant Rush (SC2002 group assignment)

A turn-based restaurant management game played on the command line. The player is the manager
and decides each turn what the Waiter and the Chef do.

**Repository:** https://github.com/manus369/sc2002-lab-poject

## Build and run (JDK 17 or newer, no network needed)

| | macOS / Linux / Git Bash | Windows cmd |
|---|---|---|
| Build | `./build.sh` | `build.bat` |
| Play | `./run.sh` | `run.bat` |
| Replay a saved run | `./run.sh --echo < runs/stage1-victory.txt` | `run.bat --echo < runs\stage1-victory.txt` |

Without the scripts: `javac -d out src/rush/*.java src/rush/*/*.java` then `java -cp out rush.Main`.

## How to play

Each turn the game shows the turn number, revenue, average satisfaction of paid customers, the
waiting queue, the tables (with each customer's satisfaction and order status) and the staff
tasks available. Then:

1. **Happy Hour** (Stage 3, asked until it is used): `y` / `n`.
2. **Host** (Stage 3): the number of a queued customer to seat, or `0` to wait.
3. **Waiter**: `1` seat, `2` take order, `3` serve, `0` wait, then the customer number.
4. **Chef**: the customer number whose order to cook one unit of, or `0` to wait.
5. The Cashier, eating, satisfaction loss and departures happen automatically.

An invalid choice is explained and asked again without using up the staff task.

## Stages

| Stage | Adds | Tag |
|---|---|---|
| 1 Basic operation | Regular customers, menu, Waiter, Chef, Cashier, tables, orders, waiting queue, full turn sequence, victory and defeat | `stage-1` |
| 2 Customer behaviour | VIP and Critic customers | `stage-2` |
| 3 New role and menu policy | Host, ComboMeal, Happy Hour | `stage-3` |

To play an earlier stage: `git checkout stage-1` (or `stage-2`), build and run, then switch back.

## Rules we defined

- **Food choice:** customer *n* orders dish number (*n* - 1) of the menu, wrapping around:
  C1 Salad, C2 Burger, C3 Pasta, C4 Salad, C5 Burger, C6 Pasta.
- **Customer types:**

  | Customer | Loss per waiting turn | Bill | Special behaviour |
  |---|---|---|---|
  | Regular | 8 | full price | - |
  | VIP | 5 | 10% off | - |
  | Critic | 12 | full price | always orders the dish with the most preparation units (Pasta) |

- **Line-up (Stage 2 onwards):** C1 Regular (turn 1), C2 Critic (4), C3 VIP (7), C4-C6 Regular (10, 13, 16).
- **Combo choice (Stage 3):** a customer whose satisfaction is still 90 or more when the order is
  taken upgrades to the first combo that contains their usual dish. The Critic never orders a combo.
- **Combos on the menu:** Salad + Burger ($21.60, 2 units) and Salad + Pasta ($24.30, 3 units).
- **Happy Hour:** VIP discount first, then 20% off, each rounded to the nearest cent; the price is
  locked when the order is taken.
- **Turn order:** arrivals, display, Happy Hour, Host, Waiter, Chef, Cashier, eating, satisfaction loss.
  The Cashier is handled before eating, so a customer who becomes READY_TO_PAY pays next turn at
  the earliest.

## Reproducible runs (test notes)

Run each script on the tag it belongs to (`stage1-*` on `stage-1`, and so on).

| Script | Outcome |
|---|---|
| `runs/stage1-victory.txt` | VICTORY - 5 paid, $66.00, avg 79.2 (also shows rejected inputs) |
| `runs/stage1-defeat.txt` | DEFEAT - 1 paid, $15.00 (C1 is forgotten and leaves, Chef idles) |
| `runs/stage2-victory.txt` | VICTORY - 5 paid, $67.20, avg 76.0 |
| `runs/stage2-defeat.txt` | DEFEAT - 3 paid, $34.20 (the Critic is left in the queue and walks out) |
| `runs/stage3-victory.txt` | VICTORY - 5 paid, $100.30, avg 80.4 (Host, combos, Happy Hour) |
| `runs/stage3-defeat.txt` | DEFEAT - 3 paid, $48.15 (Happy Hour wasted, Host unused, Critic walks out) |

The `.out` file next to each script is the transcript it produces.

## Stage 1 -> Stage 2

Compare: https://github.com/manus369/sc2002-lab-poject/compare/stage-1...stage-2

New files: `entity/VIPCustomer.java`, `entity/CriticCustomer.java`, `runs/stage2-*`.

| Changed file | Change | Why |
|---|---|---|
| `entity/Customer.java` | new method `applyDiscount(int)` (full price by default) | VIP pricing is decided by the customer object, which VIPCustomer overrides |
| `entity/Waiter.java` | the order price is `customer.applyDiscount(price)` | the price is locked when the order is taken |
| `entity/Menu.java` | new `getDishWithMostPrepUnits()` | used by the Critic's order preference |
| `control/GameController.java` | `createCustomer` makes C2 a Critic and C3 a VIP | set-up of the arrival line-up only |
| `boundary/ConsoleUI.java` | the queue shows each customer's loss per turn | so the player can see who is impatient |

Not changed: the turn steps, `Restaurant`, `Order`, `Chef`, `Cashier`, `Table`. The controller has
no type checks: each customer type only overrides `getSatisfactionLoss()`, `applyDiscount()` or
`chooseItem()`.

## Stage 2 -> Stage 3

Compare: https://github.com/manus369/sc2002-lab-poject/compare/stage-2...stage-3

New files: `entity/Host.java`, `entity/ComboMeal.java`, `entity/HappyHour.java`, `runs/stage3-*`.

| Changed file | Change | Why |
|---|---|---|
| `entity/Menu.java` | list of combos, `getCombos()`, `findComboWith(dish)` | the combos are registered on the menu |
| `entity/Customer.java` | `chooseItem` upgrades to a combo at satisfaction >= 90; new `recover(int)` | our documented combo rule; Happy Hour recovery changes the customer's satisfaction |
| `entity/Waiter.java` | `takeOrder` also takes the `HappyHour` and applies it after the customer's discount | the Happy Hour price is locked when the order is taken |
| `entity/Restaurant.java` | holds the `HappyHour`; new `recoverWaitingCustomers(int)` | the restaurant knows which customers are still waiting |
| `control/GameController.java` | Happy Hour and Host steps added to the turn | two new manager decisions |
| `boundary/ConsoleUI.java` | combos and Happy Hour in the welcome text, Happy Hour status, Host task line | the player must see the new options |

Not changed: `Order`, `Chef`, `Cashier`, `Table`, `Staff` and the customer subclasses. A
`ComboMeal` is a `MenuItem`, so ordering, cooking, serving and paying for a combo needed no new
code. The Stage 1 and 2 rules (satisfaction, eating, payment, abandonment) are the same code as
before.

## Design documents

| File | Contents |
|---|---|
| `docs/class-diagram.png` / `.svg` / `.puml` | Final class diagram (matches `stage-3`) with encapsulation, polymorphism and composition notes |
| `docs/sequence-diagram.png` / `.svg` / `.puml` | VIP customer from arrival to payment with Happy Hour, then the result check |
| `docs/report.pdf` (source `docs/report.html`) | Diagrams, design discussion, functional evidence, change analysis and reflections |
| `docs/video-plan.md` | Plan for the recorded video segments |

The diagrams are PlantUML: `java -jar plantuml.jar -tpng docs/*.puml` (needs Graphviz for the class diagram).

## Video timestamps

Video: `<link or file name>`

| # | Segment | Start |
|---|---|---|
| 1 | Game launching from the submitted project | `mm:ss` |
| 2 | Meaningful manager choices, incl. a constrained staff decision | `mm:ss` |
| 3 | A dish going READY -> SERVED -> payment | `mm:ss` |
| 4 | A VIP or Critic behaviour | `mm:ss` |
| 5 | A Stage 3 feature | `mm:ss` |
| 6 | One victory and one defeat | `mm:ss` |
| 7 | The stage tag comparisons and the diagrams | `mm:ss` |
