# Yaml Config

## Base config creator

```java
public BaseConfig(JavaPlugin plugin, String path, boolean hasInResource, Configuration defaultConfig)
public BaseConfig(JavaPlugin plugin, String path, boolean hasInResource)
public BaseConfig(JavaPlugin plugin, String path)
public BaseConfig(JavaPlugin plugin, String path, Configuration defaultConfig)
```

{% hint style="info" %}
Options:

* plugin - your plugin instance
* path - config path (For example "lang.yml")
* hasInResource - if true, then when creating, if there is no file, it is copied from the resources of your plugin (resource with the same path)
* defaultConfig - default config data
{% endhint %}

## Reload/load

```java
BaseConfig config;
config.load();
```

## Save

```
BaseConfig config;
config.save();
```

## Get object of ?

```java
public Object get(String key)
public String getString(String key) //immediately colors the text
public Integer getInt(String key)
public Boolean getBoolean(String key)
public Map<String, Object> getMap(String key, Boolean b)
```

## Get list of ?

```java
public List getList(String path)
public List<String> getStringList(String key) //immediately colors the text
public List<Integer> getIntList(String key)
public List<Boolean> getBooleanList(String key)
```
