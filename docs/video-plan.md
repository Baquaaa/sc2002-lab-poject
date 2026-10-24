# Video plan (about 15 minutes)

Each member explains a part they understand. Fill in the timestamps in the README after recording.

| # | Segment | What to show | Script / file | Presenter |
|---|---|---|---|---|
| 1 | Game launching | `./build.sh` then `./run.sh` on the `stage-3` tag; read the welcome screen | - | [name] |
| 2 | Meaningful manager choices | Turn 10 of `stage3-victory`: the Waiter can only serve C3 *or* seat C4 - the Host seats C4 so the Waiter can serve. Show an invalid choice being rejected (`stage1-victory` turn 1) | `runs/stage3-victory.txt`, `runs/stage1-victory.txt` | [name] |
| 3 | READY -> SERVED -> payment | Follow C3 in `stage1-victory`, turns 7-12 (same as the brief's Section 3.3 example) | `runs/stage1-victory.out` | [name] |
| 4 | VIP or Critic behaviour | `stage2-victory`: Critic orders Pasta and loses 12 per turn; VIP pays $16.20. `stage2-defeat`: the Critic walks out | `runs/stage2-*.txt` | [name] |
| 5 | Stage 3 feature | Happy Hour on turn 7 of `stage3-victory`: VIP combo $24.30 -> $21.87 -> $17.50, recovery on turn 8; Host seating | `runs/stage3-victory.txt` | [name] |
| 6 | Victory and defeat | Replay `stage3-victory` and `stage3-defeat` (scroll to the result) | `runs/stage3-*.txt` | [name] |
| 7 | Stage tags and diagrams | GitHub compare links `stage-1...stage-2` and `stage-2...stage-3`; walk through the class and sequence diagrams | README, `docs/` | [name] |

Replay command: `./run.sh --echo < runs/<script>.txt` (on Windows: `run.bat --echo < runs\<script>.txt`).
