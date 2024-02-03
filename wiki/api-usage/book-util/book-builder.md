# Book Builder

## Creates BookBuilder

```java
public BookBuilder(ItemStack book)
```

## Set title

```java
public BookBuilder title(String title)
```

## Set author

```java
public BookBuilder author(String author)
```

## Set pages from a simple string

```java
public BookBuilder pagesRaw(String... pages)
```

## Set pages from a list of string

```java
public BookBuilder pagesRaw(List<String## pages)
```

## Set pages from a string array or list of string arrays

```java
public BookBuilder pages(BaseComponent[]... pages)
public BookBuilder pages(List<BaseComponent[]## pages)
```

## Set generation (ORIGINAL, COPY\_OF\_ORIGINAL, COPY\_OF\_COPY, TATTERED)

```java
public BookBuilder generation(BookMeta.Generation generation)
```

## Return ItemStack

```java
public ItemStack build()
```
