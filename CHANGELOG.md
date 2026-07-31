# Version 2.0.2
- **Fixed:** [#6](https://codeberg.org/jolkert/deselect/issues/6) Attemping to retreive an item from a Create toolbox
while deselcted causes a server crash

# Version 2.0.1
## Bugfixes
- **Fixed:** [#5](https://codeberg.org/jolkert/deselect/commit/00fd3e0d9731b8d7f0fdaaa44ff329b78d6ad4a1) Hand uses attributes of first hotbar slot for attacks and durability usage

# Version 2.0.0
## Additions
- Now available on Fabric and Neoforge on 1.21.1

## Bugfixes
- **Fixed:** Breaking blocks with empty hand while deselected uses the properties and durability of the tool that was held
prior to deselecting hotbar (this was an incredibly stupid issue, and i have no idea what compelled me to implement it like
that in the first place)

## Misc.
- Dropped support for 1.20.1 and 1.21.4

# Verson 1.1.2
## Bugfixes
- **Fixed:** Game crashes upon attemping to break a block in survival mode while deselected
## Compatibility
- Now compatible with [Slot Cycler](https://modrinth.com/mod/slot-cycler)

# Version 1.1.1
## Bugfixes
- **Fixed:** Hotbar selection box renders far to the left of the player's
hotbar on small GUI scales

# Version 1.1.0
## Bugfixes
- **Fixed:** Game crashes when pressing the pickblock key in creative while deselected
- **Fixed:** ItemStack in offhand disappears upon pressing the
swap hands key while deselectd

# Version 1.0.0
- Added basic functionality