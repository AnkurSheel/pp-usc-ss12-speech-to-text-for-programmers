# Interaction #

The primary mode of interacting with the program is by speech. In HTML, scripting is achieved through the use of tags, such as `<HTML>` or `<P>`. These tags are opened by the command `open` followed by one of the [acceptable tags](Tags.md).

Once a tag is open, another tag may be opened to nest the tags. For example a `<BOLD>` tag may be nested inside an `<OL>` tag.

When inside a tag, the actual text that will be displayed is indicated by the user by spelling the contents letter by letter. A letter preceded by `capital` will be capitalized. When a space is desired, it is indicated by saying `command space`.

When a tag is complete, the end tag is indicated by saying `finish` followed by the desired [acceptable tag](Tags.md).

There are also some words that are pre-set in the grammar of the program, which may be printed by simply saying them.

Commands, which are listed [here](Commands.md), must be preceded by the `command` keyword.

# Specification #

The specification of the grammar follows the Java Speech API Grammar Format, which is specified [here](http://java.sun.com/products/java-media/speech/forDevelopers/JSGF/index.html).