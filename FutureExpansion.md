# Expansions #

This program was built using the [Sphinx-4](Sphinx4.md) concept of grammar files that contain pre-defined commands that the user can access. This makes for a modular approach that can be expanded to other languages and further commands.

A natural expansion in languages for this program would be to CSS and JavaScript because of their close association and interaction with HTML. CSS is scripting-based, so an appropriately complex grammar would be able to handle this well. JavaScript, however, because of its object-oriented approach, would be more difficult. Especially hard would be indicating overloaded functions, where recognizing the name of a function would still leave indeterminate the number and type of parameters.

It may also be possible to integrate another speech recognition engine for the actual text-based part of the program, where an individual could use another speech writer that is more suited than [Sphinx-4](Sphinx4.md) to recognizing entire sentences syntactically.