# kingdomino-specs
Repository for ECSE223 Kingdomino group project user stories and scenarios.
 * Cucumber feature files can be found in the _features/_ folder
 * Test files are in the _test/resources/_ folder
 * Text file with all the 48 dominoes is in the _main/resources/_ folder
 * Template for creating domino configurations is available here: [Kingdomino template](https://mcgill-my.sharepoint.com/:x:/g/personal/remi_carriere_mail_mcgill_ca/Ee7G9VJ8mWBAlf9ty3a1EC0BB8t82fOHDvcWI6yIoR3ZMw?e=hHRKh7)
 * Figures created during the design of F20-F24 features are accessible here: [F20-24](https://mcgill-my.sharepoint.com/:x:/g/personal/marton_bur_mail_mcgill_ca/ETi8s08pagxOhx-sg8XhKzYB9RLyUO74avsnUqttTTWvNA?e=Szoczr)

# Key features of the game:
  - Stage 1: Initialization and general game features
    - [0.25] Set game options
    - [0.5] Provide user profile
    - [x] Start a new game
    - [x] Browse domino pile (I believe we're only supposed to browse dominos that will be in the pile)
    - [x] Shuffle domino pile
    - [x] Load game
    - [x] Save game

  - Stage 2: Choose next domino
    - [x] Reveal next draft
    - [x] Order next draft of dominos
    - [x] Choose next domino

  - Stage 3: Place domino to kingdom
    - [x] Move current domino
    - [x] Rotate current domino
    - [x] Place domino
    - [x] Verify castle adjacency
    - [x] Verify neighbor adjacency
    - [x] Verify no overlapping
    - [x] Verify kingdom grid size
    - [0.99] Discard domino
    
  - Stage 4: Evaluate score
    - [0.5] Identify kingdom properties
    - [0.5] Calculate property score
    - [0.5] Calculate bonus scores
    - [0.5] Calculate player score
    - [0.5] Calculate ranking
    - [x] Resolve tiebreak
    
# TODO:
  - [ ] track score every turn (antonia)
  - [ ] game options (i.e. harmony) (eric)
