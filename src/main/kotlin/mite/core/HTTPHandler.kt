package mite.core

/**
 * A header writer that is also a body handler.
 *
 * This could obviously be one interface with optional methods rather than
 * three different interfaces. This way seems more natural, since most things
 * will either provide a header or a body. There are, however, occasionally
 * things like authorization that will need to do both.
 */
interface HTTPHandler : HTTPHeaderWriter, HTTPBodyHandler