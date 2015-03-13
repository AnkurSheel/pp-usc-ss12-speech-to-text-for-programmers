# System Walkthrough #

The program is event-driven, with the event being voice recognition. There is an infinite loop that is picking up and checking sound from the microphone. When the loop identifies something that is present in the [grammar](Grammar.md), it passes it to a class that check how that command is to be applied.

Once the command is received by the back-end, the program decides what formatting is to be done. Formatting is pre-defined. If it is a tag, for example, indentation is handled by the system. Implicit punctuation is implemented at this level; that is, what is expected by the user is added automatically by the system.