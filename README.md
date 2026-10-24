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

1. **Waiter**: `1` seat, `2` take order, `3` serve, `0` wait, then the customer number.
2. **Chef**: the customer number whose order to cook one unit of, or `0` to wait.
3. The Cashier, eating, satisfaction loss and departures happen automatically.

An invalid choice is explained and asked again without using up the staff task.

## Rules we defined

- **Food choice:** customer *n* orders dish number (*n* - 1) of the menu, wrapping around:
  C1 Salad, C2 Burger, C3 Pasta, C4 Salad, C5 Burger, C6 Pasta.
- **Turn order:** arrivals, display, Waiter, Chef, Cashier, eating, satisfaction loss.
  The Cashier is handled before eating, so a customer who becomes READY_TO_PAY pays next turn at
  the earliest.

## Reproducible runs (test notes)

| Script | Outcome |
|---|---|
| `runs/stage1-victory.txt` | VICTORY - 5 paid, $66.00, avg 79.2 (also shows rejected inputs) |
| `runs/stage1-defeat.txt` | DEFEAT - 1 paid, $15.00 (C1 is forgotten and leaves, Chef idles) |

The `.out` file next to each script is the transcript it produces.
