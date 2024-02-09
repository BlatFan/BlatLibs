# GUI

## Constructors

```java
public GUI(JavaPlugin plugin, int size, String title)
```

{% hint style="info" %}
Options:

* plugin - your plugin instance
* size - gui size (may be 5, 9, 18, 27, 36, 45, 54)
* title - gui title
{% endhint %}



```java
public GUI(JavaPlugin plugin, int size, String title, Player.. player)
```

{% hint style="info" %}
Options:

* plugin - your plugin instance
* size - gui size (may be 5, 9, 18, 27, 36, 45, 54)
* title - gui title
* player - players for whom the GUI will now open
{% endhint %}



## Title

Set title

```java
public void setTitle(String title)
```

Get title

```java
public String getTitle()
```

## Item

Set item

```java
public void setItem(int slot, ItemStack item, ClickAction action)
```

{% hint style="info" %}
Options:

* slot - slot in which the item will be
* item - ItemStack&#x20;
* action - click action
{% endhint %}

Add item on free slot

```java
public void addItem(ItemStack item, ClickAction action)
```

{% hint style="info" %}
Options:

* item - ItemStack&#x20;
* action - click action
{% endhint %}

## Set Close Action

```java
public void setCloseAction(CloseAction closeAction)
```

## Set Border

```java
public void setBorder(ItemStack item, BorderStyle style)
```

{% hint style="info" %}
Options:

* item - border item
* style - border style
{% endhint %}

```java
public enum BorderStyle {
   FULL,
   VERTICAL,
   TOP_ONLY,
   BOTTOM_ONLY,
   LEFT_ONLY,
   RIGHT_ONLY,
   HORIZONTAL;
}
```

## Set Background

Places on all free item slots

```java
public void setBackground(ItemStack item)
```

## Player

Open GUI for player

```java
public void addPlayer(Player player)
```

Close GUI for player

```java
public void removePlayer(Player player)
```

Returns all players using the GUI

```java
public ArrayList<Player> getPlayers()
```

## Get Size

Return GUI size

```java
public int getSize()
```
