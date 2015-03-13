# Introduction #

Our task for the 2010 SS12 competition was to implement speech-to-text software optimized for programmers.

We chose to implement a speech-to-text interface optimized for coding in HTML. HTML is an good language for this task for a number of reasons. The first and most important is accessibility. In our largely internet-driven culture, the ability to make web sites enables a person to communicate with the world.

The second reason is implementation. HTML is a relatively straight-forward language, with a limited vocabulary, which makes implementation simple without requiring a lot of special commands in the speech-to-text. Another advantage is the scalability. The natural progression from HTML would be to implement CSS and Javascript. Both of these languages are relatively simple to implement; Javascript is a simple language and uses dynamic typing, which makes it very easy for a user to learn and use without making many mistakes. CSS would be similarly easy to implement because it has a limited vocabulary.

Our software is based off of the [Sphinx-4](Sphinx4.md) speech recognition system, written in Java through a collaboration of Carnegie Mellon, Sun Microsystems, Mitsubishi Electric Research Labs, Hewlett Packard, UCSC, and MIT. We chose [Sphinx-4](Sphinx4.md) because our entire team is familiar with Java, which is a major advantage, and [Sphinx-4](Sphinx4.md) offered the most flexibility of the speech-to-text suites available open source.

On top of the Sphinx-4 system we have implemented our own Java application.


# Pages #

  * [Implementation](Implementation.md)
  * [Grammar](Grammar.md)
  * [Features](Features.md)
  * [Future Expansion](FutureExpansion.md)