# StressCounter
Stress counter made to aid in tracking certain variables and performing calculations.

This GUI is used to track a unique system created by a group of friends to be used when engaging in certain games. It is made toe mulate the stress system from a semi popular game "Darkest Dungeon".

The app works as follows:

Up to 4 players are tracked at a time. At any time a player's "stress" is tracked(the value in the spinner below their name).

"Age" determines a player's maximum stress, and criteria for changing it are determined by the users. The caps for each age are as follows:

Fedual: 20
Castle: 40
Imperial: 60

Players gain stress by certain actions they determine, and increment the values manually based on their own discretion.

Once a player reaches 1/2 of the maximum stress, a "resolve test" is initiated in which they experience either an "affliction" or a "virtue". Afflictions are negative, and set the character's status to whatever negative effect was randomly chosen. Virtues are positive effects and change their status to the randomly chosed positive effect, as well as make the player immune to heart attacks(discussed later). Both virtues and afflictions prevent further resolve tests until a player is reset.

"Heart attacks" occur when the player reaches maximum stress, and incurs an immediate(usually fatal) penalty once activated, determined by the user.

The application can save to a text file and loads the latest save automatically on startup.

Other features(e.g. team decrement, reset all) are self explanatory.

Development has since ceased on this project as there is no longer a target audience. Intended features included:

Setting character name/condition/stress
Linking of "active" checkboxes and "active players" spinner to lower redundancy
Multiple save profiles
Manual loading
